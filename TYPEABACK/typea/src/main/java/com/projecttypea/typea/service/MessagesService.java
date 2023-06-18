package com.projecttypea.typea.service;

import java.time.LocalDate;
import java.util.List;

import com.projecttypea.typea.bean.Messages;
import com.projecttypea.typea.dao.MessagesDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagesService {

    @Autowired
    MessagesDao mssgDao;

    public List<Messages> findAll() {
        return mssgDao.findAll();
    }

    public int addMessg(Messages mssg) {
        try {
            mssg.setTimeSent(LocalDate.now());
            mssgDao.save(mssg);
            return 1;
        } catch (Exception e) {
            return -1;
        }
    }

}
