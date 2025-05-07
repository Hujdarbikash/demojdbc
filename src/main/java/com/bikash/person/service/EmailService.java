package com.bikash.person.service;

import com.bikash.person.enums.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {

    @Autowired
    private   JavaMailSender javaMailSender ;

    @Autowired
    private  TemplateEngine  templateEngine;


    public   void sendEmail(String to , String subject, String body) throws MessagingException {
        MimeMessage message =  this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
//        ClassPathResource image = new ClassPathResource("static/logo.png");
//
//
//        helper.addInline("logoImage",image);

        javaMailSender.send(message);
    }



    @Async
    public  void sendEmailWithTemplate(String to , String subject , String body, EmailTemplate template, Context context)
    {

        try
        {
        MimeMessage message = this.javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        String html = this.templateEngine.process(template.getFileName(),context);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(html,true);

            javaMailSender.send(message);
        }catch (MessagingException e)
        {
            System.out.println(e.getMessage());
        }

        System.out.println("first email is sent ");
    }

}
