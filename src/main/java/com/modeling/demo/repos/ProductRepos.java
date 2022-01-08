package com.modeling.demo.repos;

import com.modeling.demo.domains.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepos extends CrudRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

}
