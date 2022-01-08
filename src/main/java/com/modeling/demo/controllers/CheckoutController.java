package com.modeling.demo.controllers;

import com.modeling.demo.domains.ChargeRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

//@SessionAttribute("")
@Controller
public class CheckoutController {

    @Value("${STRIPE_PUBLIC_KEY}")
    private String stripePublicKey;

    @RequestMapping("/checkout")
    public String checkout(Model model, @SessionAttribute(value = "amount") String amount) {
        if (Integer.parseInt(amount)!=0){
        model.addAttribute("amount",amount);
        model.addAttribute("stripePublicKey", stripePublicKey);
            boolean Cur = LocaleContextHolder.getLocale().getDisplayName().equalsIgnoreCase("русский");
            model.addAttribute("currency", Cur ? ChargeRequest.Currency.RUB : ChargeRequest.Currency.EUR);
        model.addAttribute("page","checkout");
        return "main";
        }
        model.addAttribute("page","order");
        return "main";
    }
}