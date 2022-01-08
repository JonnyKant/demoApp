package com.modeling.demo.repos;

import com.modeling.demo.domains.AnalysisStatus;
import com.modeling.demo.domains.Order;
import com.modeling.demo.domains.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepos extends CrudRepository<Order , Long> {
    List<Order> findAllByOrderStatus(String name);
    List<Order> findAllByAnalysisStatus(AnalysisStatus analysisStatus);
}
