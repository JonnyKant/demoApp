package com.modeling.demo.domains;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//@JsonDeserialize(using = ProductDeserializer.class)
@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String image;
    private String name;
    private Integer price;
    private String description;
    private String analysis;

    @ManyToMany(mappedBy = "Products",fetch = FetchType.LAZY) //Указываем имя поля которое отображает взаимосвязь
    private List<Order> Orders = new ArrayList<>();

    public Product(String Image, String Name, Integer Price, String Description) {
        this.image = Image;
        this.name = Name;
        this.price = Price;
        this.description = Description;
    }

    public Product(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOrder(Order order) {
        Orders.add(order);
    }
    public List<Order> getOrders() {
        return Orders;
    }
    private Order getOrder(int index){
        return Orders.get(index);
    }
    public void setOrders(List<Order> products) {
        Orders = products;
    }


    @Override
    public String toString() {
        return "Product{" +
                "Id=" + id +
                ", Hashcode='" + hashCode() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(image, product.image) &&
                Objects.equals(name, product.name) &&
                Objects.equals(price, product.price) &&
                Objects.equals(description, product.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, image, name, price, description);
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
    public boolean checkAnalysis(){
        return (analysis != null && !analysis.isEmpty());
    }
}
