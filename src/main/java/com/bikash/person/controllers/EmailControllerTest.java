package com.bikash.person.controllers;

import com.bikash.person.enums.EmailTemplate;
import com.bikash.person.models.Department;
import com.bikash.person.models.Employee;
import com.bikash.person.service.EmailService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/email")
public class EmailControllerTest {

    private final EmailService emailService;

    public EmailControllerTest(EmailService emailService) {
        this.emailService = emailService;
    }


    @GetMapping("/send")
    public void sendEmail() {
        try {
            this.emailService.sendEmail("hujdarbikash000@gmail.com", "TestMail", "This is a testing of mail ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to send email" + e.getMessage());
        }

    }

    @GetMapping("/send/template")
    public void sendEmailWithTemplate() {
        try {
            Context context = new Context();

            Employee employee = new Employee();
            employee.setName("TEstMail");
            employee.setEmail("hujdarbikash000@gmail.com");
            Department department = new Department();
            department.setDepartmentId(1l);
            department.setDepartmentName("IT");
            employee.setDepartment(department);
            context.setVariable("employee",employee);


            emailService.sendEmailWithTemplate("hujdarbikash000@gmail.com","Test TEmplate","this is test body",EmailTemplate.WELCOME,context);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Failed to send email" + e.getMessage());
        }

    }


}
