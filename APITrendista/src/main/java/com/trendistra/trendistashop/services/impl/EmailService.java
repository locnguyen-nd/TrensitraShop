package com.trendistra.trendistashop.services.impl;

import com.trendistra.trendistashop.entities.auth.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String  sender;
    /**
     * Gửi email xác minh đến người dùng.
     *
     * @param user Đối tượng UserEntity chứa thông tin người dùng, bao gồm email, tên người dùng, và mã xác minh.
     * @return Thông báo trạng thái việc gửi email, "Email Sent" nếu gửi thành công,
     *         hoặc "Error while Sending Mail" nếu có lỗi xảy ra.
     */
    public  String  sendMail(UserEntity user) {
        String subject = "Verify your email";
        String senderName = "CMP Shop";
        String mailContent = "Hello " + user.getUsername() + ",\n\n";
        mailContent += "Your verification code is: " + user.getVerificationCode() + ",\n\n";
        mailContent += "Please enter this code to verify your email.,\n\n";
        mailContent += "Best regards,\n";
        mailContent += senderName;
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject(subject);
            mailMessage.setText(mailContent);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
        return "Email Sent";
    }
}
