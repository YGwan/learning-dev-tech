package org.example;

import java.util.ArrayList;
import java.util.List;
import org.example.aop.TransactionAspect;
import org.example.entity.User;
import org.example.service.MyTransactionalService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@Import(TransactionAspect.class)
@SpringBootApplication
public class TransactionApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TransactionApplication.class);
        application.setAdditionalProfiles("dev");

        ApplicationContext ac = SpringApplication.run(TransactionApplication.class, args);
        List<User> users = new ArrayList<>();
        users.add(new User(1L, "YongGwan", 26));
        users.add(new User(2L, "A", 27));
        users.add(new User(3L, "jinHwan", 27));

        MyTransactionalService myTransactionalService = ac.getBean(MyTransactionalService.class);
        myTransactionalService.useAbstractTrans(users);
    }
}