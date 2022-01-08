package com.modeling.demo.service;

import com.modeling.demo.domains.AnalysisStatus;
import com.modeling.demo.domains.Order;
import com.modeling.demo.repos.OrderRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

@Service
public class AnalysisService {

    @Autowired
    OrderRepos orderRepos;

    @Autowired
    private MailSender mailSender;

    public void sendAnalysis(Order order){
        if(order.checkAllAnalysis()){
            try {
                mailSender.send("analysisFiles","yourAnalysisFiles", order);
            } catch (MessagingException e) {
                System.out.println("Ошибка отправки сообщения");
                e.printStackTrace();
            }
            order.setAnalysisStatus(AnalysisStatus.YES_ANALYSIS);
        }
        else {
            order.setAnalysisStatus(AnalysisStatus.NO_ANALYSIS);
        }
        orderRepos.save(order);
    }

}
