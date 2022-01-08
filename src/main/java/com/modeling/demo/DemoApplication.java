package com.modeling.demo;

//import com.modeling.demo.service.DataLoadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DemoApplication {
//    @Autowired
//    DataLoadService dataLoadService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
//    @PostConstruct
//    public void init() throws Exception {
//        new Thread(() -> {
//            try {
//                dataLoadService.DataLoad();
//            }
//            catch (Exception e){
//               e.fillInStackTrace();
//            }
//        }).start();
//    }

}
