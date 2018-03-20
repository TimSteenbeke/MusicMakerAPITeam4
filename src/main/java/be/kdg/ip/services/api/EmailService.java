package be.kdg.ip.services.api;

import be.kdg.ip.domain.Mail;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendSimpleMessage(final Mail mail);
}
