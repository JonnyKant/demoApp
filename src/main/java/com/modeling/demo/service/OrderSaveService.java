package com.modeling.demo.service;

import com.modeling.demo.domains.Order;
import com.modeling.demo.domains.User;
import com.modeling.demo.dto.UserWithOrderDto;
import com.modeling.demo.repos.OrderRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class OrderSaveService {
    @Autowired
    private OrderRepos orderRepos;

    @Autowired
    MyUserDetailsService userService;

    public void setOrderInAuthUser(Order order, HttpSession httpSession){

        MyUserPrincipal myUserPrincipal= (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = myUserPrincipal.getUser();
        order.setUser(user);
        orderRepos.save(order);
        httpSession.setAttribute("order",new Order());

    }
    public void setOrderNoAuthUser(Order order, UserWithOrderDto userWithOrderDto, HttpSession httpSession){

        httpSession.setAttribute("order",new Order());
    }
}
