package com.kodilla.kodillajms.controller;

import com.kodilla.kodillajms.domain.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @PostMapping
    public void processOrder(@RequestBody Order order) {
        jmsTemplate.convertAndSend("order-queue", order);
    }
}
