package com.jitunayak.kafkasample1.controllers;

import com.jitunayak.kafkasample1.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @PostMapping(path = "send/user/", produces = MediaType.APPLICATION_JSON_VALUE)
    public void sendUserData(@RequestBody User user){
        template.send("userdetails", user);
    }
}
