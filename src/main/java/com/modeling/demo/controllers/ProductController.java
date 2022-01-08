package com.modeling.demo.controllers;

import com.modeling.demo.domains.Order;
import com.modeling.demo.domains.Product;
import com.modeling.demo.repos.ProductRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Controller
public class ProductController {

    @Autowired
    ProductRepos productRepos;

    @GetMapping("/addProduct")
    public String addProductForm(Model model){
        model.addAttribute("page","addProduct");
        return "main";
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestParam("name") String name,
                             @RequestParam("description") String description,
                             @RequestParam("price") String price,
                             @RequestParam("image") MultipartFile image,
                             Model model){
        if(name!=null && !name.isEmpty() && description!=null && !description.isEmpty() && price!=null && !price.isEmpty()){
            String uuid = UUID.randomUUID().toString();
            uuid = uuid + "." +image.getOriginalFilename();
            try {
                System.out.println(System.getProperty("user.dir"));
                image.transferTo(new File(System.getProperty("user.dir")+"/images/"+uuid));
            } catch (IOException e) {
                e.printStackTrace();
            }
            productRepos.save(new Product(uuid,name, Integer.parseInt(price),description));
        }

        model.addAttribute("page","addProduct");
        return "main";
    }

    @GetMapping("/editProduct")
    public String editProducts(Model model, @ModelAttribute String lang) {
        Iterable<Product> products = productRepos.findAll();
        model.addAttribute("products", products);
        model.addAttribute("page", "editProduct");
        return "main";
    }

    @GetMapping("/editProduct/{product}")
    public String editProduct(@PathVariable Product product, Model model){
        model.addAttribute("product",product);
        model.addAttribute("page","editProduct/id");
        return "main";
    }

    @PostMapping("/editProduct")
    public String editProduct(@RequestParam("name") String name,
                              @RequestParam("description") String description,
                              @RequestParam("price") String price,
                              @RequestParam("image") MultipartFile image,
                              @RequestParam("product") Product product,
                              @RequestParam("analysis") MultipartFile analysis,
                              Model model){
        if(name!=null && !name.isEmpty() && description!=null && !description.isEmpty() && price!=null && !price.isEmpty()){
            product.setName(name);
            product.setDescription(description);
            product.setPrice(Integer.parseInt(price));
        }
        if (!image.isEmpty()){
            String uuid = UUID.randomUUID().toString();
            uuid = uuid + "." +image.getOriginalFilename();
            try {
                System.out.println(System.getProperty("user.dir"));
                image.transferTo(new File(System.getProperty("user.dir")+"/images/"+uuid));
            } catch (IOException e) {
                e.printStackTrace();
            }
            product.setImage(uuid);
        }
        if (!analysis.isEmpty()){
            String uuid = UUID.randomUUID().toString();
            uuid = uuid + "." +analysis.getOriginalFilename();
            try {
                System.out.println(System.getProperty("user.dir"));

                analysis.transferTo(new File(System.getProperty("user.dir")+"/analysis/"+uuid));
            } catch (IOException e) {
                e.printStackTrace();
            }
            product.setAnalysis(uuid);
        }
        productRepos.save(product);
        model.addAttribute("product",product);
        model.addAttribute("page","editProduct/id");
        return "main";
    }
}
