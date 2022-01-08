package com.modeling.demo.controllers;

import com.modeling.demo.domains.*;
import com.modeling.demo.dto.UserWithOrderDto;
import com.modeling.demo.repos.OrderRepos;
import com.modeling.demo.repos.ProductRepos;
import com.modeling.demo.service.AuthenticatedService;
import com.modeling.demo.service.MyUserDetailsService;
import com.modeling.demo.service.MyUserPrincipal;
import com.modeling.demo.service.OrderSaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@SessionAttributes("amount")
public class OrderController {
    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @Autowired
    OrderSaveService orderSaveService;

    @Autowired
    ProductRepos productRepos;

    @Autowired
    OrderRepos orderRepos;

    @Autowired
    AuthenticatedService authenticatedService;

    @Autowired
    MyUserDetailsService userService;

    @GetMapping("/order")
    public String order(
            @SessionAttribute(value = "order", required = false) //Параметр для игнорирования пустого атрибута в сессии
                    Order order,
            Model model)
    {
//        if (!authenticatedService.isAuthenticated(SecurityContextHolder.getContext().getAuthentication())){
//
//        }

        if (order != null) {
            model.addAttribute("order", order);
        }
        model.addAttribute("lang", LocaleContextHolder.getLocale());
        model.addAttribute("userWithOrderDto", new UserWithOrderDto());
        model.addAttribute("page", "order");
        return "main";
    }

    @PostMapping("/noAuthOrder")
    public String noAuthOrder(@ModelAttribute String amount,
                              @SessionAttribute(value = "order", required = false) Order order,
                              @ModelAttribute("userWithOrderDto") @Valid UserWithOrderDto userWithOrderDto, // Можно удалить result?
                              BindingResult result,
                              Model model) {
        if(!result.hasErrors() && !order.getProducts().isEmpty() && userWithOrderDto!=null) {
            try {
                User user  = userService.registerNewUserAccountInForm(userWithOrderDto);
                order.setUser(user);
                orderRepos.save(order);
                order.setOrderStatus(OrderStatus.NOPAY);
            } catch (AuthenticationServiceException n) {
                model.addAttribute("page", "order");
                model.addAttribute("exists", "exists");
                return "main";
            }
            model.addAttribute("page","checkout");
            model.addAttribute("order",order);
            model.addAttribute("stripePublicKey", stripePublicKey);
            boolean Cur = LocaleContextHolder.getLocale().getDisplayName().equalsIgnoreCase("русский");
            model.addAttribute("currency", Cur ? ChargeRequest.Currency.RUB : ChargeRequest.Currency.EUR);

            if (order.getAmount()!=null){
                amount = String.valueOf(order.getAmount());
                model.addAttribute("amount",Integer.parseInt(amount)*100);
            }
            return "main";
        }
        if (order.getProducts().isEmpty())
        {
            model.addAttribute("emptyOrder","emptyOrder");
        }
        model.addAttribute("lang",LocaleContextHolder.getLocale());
        model.addAttribute("page","order");
        return "main";
    }

    @GetMapping("/authOrder")
    public String AuthOrder(@ModelAttribute String amount,
                            @SessionAttribute(value = "order", required = false)
                                    Order order,
                            Model model){
        if (!order.getProducts().isEmpty()){
            MyUserPrincipal myUserPrincipal= (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = myUserPrincipal.getUser();
            order.setUser(user);
            order.setOrderStatus(OrderStatus.NOPAY);
            orderRepos.save(order);
        }
        model.addAttribute("lang",LocaleContextHolder.getLocale());
        model.addAttribute("stripePublicKey", stripePublicKey);
        boolean Cur = LocaleContextHolder.getLocale().getDisplayName().equalsIgnoreCase("русский");
        System.out.println(LocaleContextHolder.getLocale().getDisplayName());
//        model.addAttribute("currency", Cur ? ChargeRequest.Currency.RUB : ChargeRequest.Currency.EUR);
        model.addAttribute(ChargeRequest.Currency.EUR);
        model.addAttribute("page","checkout");

        if (order.getAmount()!=null){
            order.setOrderStatus(OrderStatus.NOPAY);
            amount = String.valueOf(order.getAmount());
            model.addAttribute("amount",Integer.parseInt(amount)*100);
        }
        return "main";
    }


    @GetMapping ("/delInOrder")
    public String addInOrder(
            @SessionAttribute(value = "order", required = false) Order order,
            @RequestParam(value = "product") Product product, Model model)
    {
        if(order != null) {
            order.removeProduct(product);
            orderRepos.save(order);
            model.addAttribute("order", order);
        }
        model.addAttribute("lang",LocaleContextHolder.getLocale());
        model.addAttribute("userWithOrderDto", new UserWithOrderDto());
        model.addAttribute("page", "order");
        return "main";
    }
    @Bean
    @ModelAttribute("amount")
    public String CreateAmountAttribute() {
        return "0";
    }
}