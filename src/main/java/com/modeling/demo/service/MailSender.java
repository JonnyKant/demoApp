package com.modeling.demo.service;

import com.modeling.demo.controllers.ErrorController;
import com.modeling.demo.domains.AnalysisStatus;
import com.modeling.demo.domains.Order;
import com.modeling.demo.domains.Product;
import com.modeling.demo.repos.OrderRepos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.apache.commons.io.FilenameUtils;

import javax.activation.DataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class MailSender {

    @Autowired
    JavaMailSender javaMailSender;

    private static Logger logger = LoggerFactory.getLogger(MailSender.class);
    @Value("${spring.mail.username}")
    private String username;

    public void sendEmail(String to, String subject, String text){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        javaMailSender.send(simpleMailMessage);
    }
//    public void send(Order order, String subject, String text) {
//        try {
//
//            final ByteArrayOutputStream stream = createInMemoryDocument("Analysis");
//            final InputStreamSource attachment = new ByteArrayResource(stream.toByteArray());
//
//            sendMimeMessageWithAttachments(subject, username, order.getUserEmail(), attachment, text);
//        } catch (IOException | MailException | MessagingException e) {
//            logger.warn(e.getMessage(), e);
//        }
//    }
public void send(String subject, String text, Order order) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(subject);
        helper.setFrom(username);
        helper.setTo(order.getUserEmail());
        helper.setText(text, false);
//        ByteArrayOutputStream stream;
//        InputStreamSource attachment;
        try {
            for(Product product : order.getProducts()) {
                Path path = Paths.get(System.getProperty("user.dir")+"/analysis/"+product.getAnalysis());
                String extension = FilenameUtils.getExtension(path.toString());
                byte[] content = Files.readAllBytes(path);
//                stream = createInMemoryDocument("Analysis");
                DataSource attachment = new ByteArrayDataSource(content, "application/octet-stream");
//                attachment = new ByteArrayResource(content);
                helper.addAttachment("analysis." + extension, attachment);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    javaMailSender.send(message);

}
}
