package com.modeling.demo.controllers;

import com.modeling.demo.domains.AnalysisStatus;
import com.modeling.demo.domains.Order;
import com.modeling.demo.domains.Product;
import com.modeling.demo.dto.UserWithOrderDto;
import com.modeling.demo.repos.OrderRepos;
import com.modeling.demo.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
public class ShowOrdersController {
    @Autowired
    OrderRepos orderRepos;


    @Autowired
    private AnalysisService analysisService;

    @GetMapping("/showOrder")
    public String ShowOrder(
            Model model)
    {
        Iterable<Order> orders = orderRepos.findAllByAnalysisStatus(AnalysisStatus.NO_ANALYSIS);
        model.addAttribute("orders",orders);
        model.addAttribute("userWithOrderDto", new UserWithOrderDto());
        model.addAttribute("page", "showOrders");
        return "main";
    }

    @GetMapping("/showOrder/{order}")
    public String showOrder(@PathVariable Order order,
            Model model)
    {
        model.addAttribute("order",order);
        model.addAttribute("page", "editOrder/id");
        return "main";
    }
    @GetMapping("/sendOrder/{order}")
    public String sendOrder(@PathVariable Order order,
                            Model model)
    {
        if (order.checkAllAnalysis()) {
            analysisService.sendAnalysis(order);
            model.addAttribute("successSend", "successSend");
            order.setAnalysisStatus(AnalysisStatus.YES_ANALYSIS);
        }
        else {
            model.addAttribute("unsuccessSend", "unsuccessSend");
        }

        model.addAttribute("order",order);
        model.addAttribute("page", "editOrder/id");
        return "main";
    }
}
