package com.modeling.demo.controllers;

import com.modeling.demo.domains.ChargeRequest;
import com.modeling.demo.domains.Order;
import com.modeling.demo.domains.OrderStatus;
import com.modeling.demo.dto.UserWithOrderDto;
import com.modeling.demo.repos.OrderRepos;
import com.modeling.demo.service.AnalysisService;
import com.modeling.demo.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpSession;

@Controller
public class ChargeController {
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @Autowired
    private AnalysisService analysisService;

    @Autowired
    private OrderRepos orderRepos;

    @Autowired
    private StripeService paymentsService;

    @PostMapping("/charge")
    public String charge(HttpSession httpSession,ChargeRequest chargeRequest, @SessionAttribute(value = "order", required = false) Order order, Model model)//Пропадает сессионный атрибут
            throws StripeException {
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = paymentsService.charge(chargeRequest);
        model.addAttribute("id", charge.getId());
        model.addAttribute("status", charge.getStatus());
        System.out.println(charge.getStatus());
        model.addAttribute("chargeId", charge.getId());
        model.addAttribute("balance_transaction", charge.getBalanceTransaction());
        if (charge.getStatus().contains("succeeded"))
        {
//            order.deleteProducts();
            order.setOrderStatus(OrderStatus.PAY);
            analysisService.sendAnalysis(order);
            orderRepos.save(order);
            order = new Order();
            httpSession.setAttribute("order",order);
//            model.addAttribute("order", order);
            model.addAttribute("succeededPay","succeededPay");
            model.addAttribute("userWithOrderDto", new UserWithOrderDto());
            model.addAttribute("page","order");
            return "main";
        }

        model.addAttribute("amount",charge.getAmount());
        model.addAttribute("stripePublicKey", stripePublicKey);
        boolean Cur = LocaleContextHolder.getLocale().equals("ru");
        model.addAttribute("currency", ChargeRequest.Currency.EUR);
        return "result";
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}