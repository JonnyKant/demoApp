package com.modeling.demo.controllers;

import com.modeling.demo.domains.Order;
import com.modeling.demo.domains.OrderStatus;
import com.modeling.demo.domains.Product;
import com.modeling.demo.domains.User;
import com.modeling.demo.repos.OrderRepos;
import com.modeling.demo.repos.ProductRepos;
import com.modeling.demo.service.AuthenticatedService;
import com.modeling.demo.service.MyUserPrincipal;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.DataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
@SessionAttributes("order")
public class DiplomController {
    @Autowired
    AuthenticatedService authenticatedService;

    @Autowired
    private ProductRepos productRepos;

    @Autowired
    private OrderRepos orderRepos;

    @GetMapping("/catalog")
    public String catalog(Model model, @ModelAttribute String lang){

        model.asMap().forEach((k,v)-> System.out.println(k.toString() + v.toString()));
        Iterable<Product> products = productRepos.findAll();
        model.addAttribute("products",products);
        System.out.println(LocaleContextHolder.getLocale());
        model.addAttribute("lang",LocaleContextHolder.getLocale());
        model.addAttribute("count", new AtomicInteger(0));
        model.addAttribute("page", "catalog");
        return "main";
    }
    @GetMapping("/catalogSearch")
    public String catalogSearch(Model model, @RequestParam String search){
        if (search.length()>0) {
            Iterable<Product> products = productRepos.findByNameContainingIgnoreCase(search);
            model.addAttribute("products",products);
        }
        else
        {
            Iterable<Product> products = productRepos.findAll();
            model.addAttribute("products",products);
        }
        model.addAttribute("lang",LocaleContextHolder.getLocale());
        model.addAttribute("count", new AtomicInteger(0));
        model.addAttribute("page", "catalog");
        return "main";
    }
    @RequestMapping(value = "/addInOrder" , method = RequestMethod.POST)
    public ResponseEntity<Object> addInOrder(@RequestParam(name = "product") String lol, @ModelAttribute Order order){
//        Product product = productRepos.findById(Long.parseLong(id.toString())).get();
        orderRepos.save(order);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Bean
    @ModelAttribute("order")
    public Order CreateOrderAttribute() {
        Order order = new Order();
        if (authenticatedService.isAuthenticated(SecurityContextHolder.getContext().getAuthentication())){
            MyUserPrincipal myUserPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            order.setUser(myUserPrincipal.getUser());
        }
        order.setOrderStatus(OrderStatus.OPEN);
        orderRepos.save(order);
        return order;
//    return myUserPrincipal.getUser().getLastOrder();
    }
}
