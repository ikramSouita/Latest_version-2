package com.projecttypea.typea.ws;

import java.util.List;

import javax.validation.Valid;

import com.projecttypea.typea.bean.Messages;
import com.projecttypea.typea.service.MessagesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")

public class MessagesWS {
    @Autowired
    MessagesService messageService;

    @GetMapping("/admin/messages")
    public List<Messages> findAll() {
        return messageService.findAll();
    }

    @PostMapping("/allusers/contact")
    public int addMessg(@RequestBody Messages mssg) {
        return messageService.addMessg(mssg);
    }

}
