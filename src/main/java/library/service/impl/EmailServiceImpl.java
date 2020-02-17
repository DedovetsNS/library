package library.service.impl;

import library.dto.MessageDto;
import library.service.EmailService;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    private final AmqpTemplate template;
    private final AmqpAdmin admin;

    @Autowired
    public EmailServiceImpl(AmqpTemplate template, AmqpAdmin admin) {
        this.template = template;
        this.admin = admin;
    }

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        MessageDto message = new MessageDto(to, subject, text);
        admin.declareQueue(new Queue("aaa"));
        template.convertSendAndReceive("query-library-mail-send", message);
    }
}



