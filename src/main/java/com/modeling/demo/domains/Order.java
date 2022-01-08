package com.modeling.demo.domains;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "OrdersOfShop")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private OrderStatus orderStatus;
    private AnalysisStatus analysisStatus;
    private String description;
    private String paymentDescription;
    private Integer amount;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "Order_Product",
            joinColumns = { @JoinColumn(name = "OrdersOfShop_id") }, //Устанавливаем сторону физического владения в бд
            inverseJoinColumns = { @JoinColumn(name = "product_id") })
    private List<Product> Products = new ArrayList<>();
    @ManyToOne //Устанавливаем сторону владения
    @JoinColumn(name = "user_id") //Устанавливаем сторону физического владения в бд
    private User user;

    public Order(){};

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Product> getProducts() {
        return Products;
    }

    public void setProducts(List<Product> products) {
        Products = products;
    }

    public void setProduct(Product p) {
//        new Exception().printStackTrace();
        synchronized (this) {
            Products.add(p);
            p.setOrder(this);
        }
    }
    public void removeProduct(Product product) {
        synchronized (this){
            Products.remove(product);
        }
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus paymentStatus) {
        this.orderStatus = paymentStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public Integer getAmount() {
        if(!getProducts().isEmpty())
        {
            return (int) getProducts().stream().map(Product::getPrice).mapToDouble(Double::valueOf).sum();
        }
        return 0;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
    public String getUserEmail(){
        {
            if(user != null){
                if(user.getEmail() != null && !user.getEmail().isEmpty())
                    return user.getEmail();
            }
        }
        return "noEmail";
    }
    public void deleteProducts() {
        synchronized (this) {
            Products.clear();
        }
    }
    public boolean checkAllAnalysis(){
        if(!Products.isEmpty()){
            for(Product product : Products){
                if (!product.checkAnalysis()){
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public AnalysisStatus getAnalysisStatus() {
        return analysisStatus;
    }

    public void setAnalysisStatus(AnalysisStatus analysisStatus) {
        this.analysisStatus = analysisStatus;
    }
}
