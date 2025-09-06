package com.trendistra.trendistashop.services.impl.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.trendistra.trendistashop.entities.user.UserEntity;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String  sender;

    @Value("${frontend.dev.url}")
    private String frontendUrl;

    @Value("${spring.mail.backup-email}")
    private String backupEmail;

    /**
     * Gửi email xác minh đến người dùng.
     *
     * @param user Đối tượng UserEntity chứa thông tin người dùng, bao gồm email, tên người dùng, và mã xác minh.
     * @return Thông báo trạng thái việc gửi email, "Email Sent" nếu gửi thành công,
     *         hoặc "Error while Sending Mail" nếu có lỗi xảy ra.
     */
    public  String  sendMail(UserEntity user) {
        String subject = "Verify your email";
        String senderName = "Trendista Shop";
        String mailContent = "Hello " + user.getUsername() + ",\n\n";
        mailContent += "Your verification code is: " + user.getVerificationCode() + ",\n\n";
        mailContent += "Please enter this code to verify your email.,\n\n";
        mailContent += "Best regards,\n";
        mailContent += senderName;
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(sender);
            mailMessage.setTo(new String[]{user.getEmail(), backupEmail});
            mailMessage.setSubject(subject);
            mailMessage.setText(mailContent);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            return "Error while Sending Mail";
        }
        return "Email Sent";
    }

    public String sendVerificationEmail(UserEntity user, String verificationToken) {
        String subject = "Xác thực email của bạn";
        String mailContent = getString(user, verificationToken);
        String backupEmail = "hoainamadm@gmail.com";
        
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(sender);
            helper.setTo(new String[]{user.getEmail(), backupEmail});
            helper.setSubject(subject);
            helper.setText(mailContent, true); // true để enable HTML
            
            javaMailSender.send(message);
            return "Email Sent";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "Error while Sending Mail: " + e.getMessage();
        }
    }

    private String getString(UserEntity user, String verificationToken) {
        String senderName = "Trendista Shop";
        String verificationLink = String.format("%s/email-verify?token=%s&email=%s",
                frontendUrl, verificationToken, user.getEmail());

        return String.format("""
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Email Verification</title>
                </head>
                <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;">
                    <div style="max-width: 600px; margin: 0 auto; padding: 20px;">
                        <div style="background-color: #ffffff; padding: 40px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1);">
                            <h1 style="color: #333333; margin-bottom: 20px; text-align: center;">Xác thực Email</h1>
                            
                            <p style="color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 30px;">
                                Xin chào <strong>%s</strong>,
                            </p>
                            
                            <p style="color: #666666; font-size: 16px; line-height: 1.5; margin-bottom: 30px;">
                                Vui lòng bấm vào nút bên dưới để xác thực email của bạn:
                            </p>
                            
                            <div style="text-align: center; margin-bottom: 10px;">
                                <a href="%s" 
                                   style="display: inline-block; 
                                          background-color: #73c6d9; 
                                          color: white; 
                                          padding: 10px 30px; 
                                          text-decoration: none; 
                                          border-radius: 5px; 
                                          font-size: 16px;
                                          font-weight: bold;">
                                    Xác thực Email
                                </a>
                            </div>
                            
                            <p style="color: #666666; font-size: 14px; line-height: 1.5; margin-bottom: 20px;">
                                Link này sẽ hết hạn sau 10 phút.
                            </p>
                            
                            <div style="border-top: 1px solid #eeeeee; margin-top: 30px; padding-top: 20px;">
                                <p style="color: #999999; font-size: 14px; margin: 0;">
                                    Trân trọng,<br>
                                    <strong style="color: #333333;">%s</strong>
                                </p>
                            </div>
                        </div>
                    </div>
                </body>
                </html>
                """, user.getUsername(), verificationLink, senderName);
    }
}
