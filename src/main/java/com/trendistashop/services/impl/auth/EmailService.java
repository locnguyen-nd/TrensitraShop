package com.trendistashop.services.impl.auth;

import com.trendistashop.dto.response.DiscountResult;
import com.trendistashop.entities.user.Order;
import com.trendistashop.entities.user.OrderItem;
import com.trendistashop.enums.OrderStatus;
import com.trendistashop.services.impl.order.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.trendistashop.entities.user.UserEntity;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String  sender;

    @Value("${frontend.dev.url}")
    private String frontendUrl;

    @Value("${spring.mail.backup-email}")
    private String backupEmail;
    private final ApplicationContext context;

    /**
     * Gửi email xác minh đến người dùng.
     *
     * @param user Đối tượng UserEntity chứa thông tin người dùng, bao gồm email, tên người dùng, và mã xác minh.
     * @return Thông báo trạng thái việc gửi email, "Email Sent" nếu gửi thành công,
     *         hoặc "Error while Sending Mail" nếu có lỗi xảy ra.
     */
    public String sendMail(UserEntity user) {
        String subject = "Verify your email";
        String senderName = "Trendista Shop";
        String mailContent = "Hello " + user.getLastName() + " " + user.getFirstName() + ",\n\n";
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

    public void sendVerificationEmail(UserEntity user, String verificationToken) {
        String subject = "Xác thực email của bạn";
        String mailContent = buildVerificationEmailHtml(user, verificationToken);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(new String[]{user.getEmail(), backupEmail});
            helper.setSubject(subject);
            helper.setText(mailContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private String buildVerificationEmailHtml(UserEntity user, String verificationToken) {
        String senderName = "Trendista Shop";
        String verificationLink = String.format("%s/email-verify?token=%s&email=%s",
                frontendUrl, verificationToken, user.getEmail());

        return String.format("""
            <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Xác thực Email</title>
            </head>
            <body style="margin: 0; padding: 0; background-color: #f4f7fa; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;">
                <table border="0" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f4f7fa; padding: 20px 0;">
                    <tr>
                        <td align="center">
                            <table border="0" cellpadding="0" cellspacing="0" width="600" style="max-width: 600px; width: 100%%;">
                                <!-- Header -->
                                <tr>
                                    <td align="center" style="padding: 40px 20px 20px 20px;">
                                        <h1 style="color: #73c6d9; font-size: 32px; margin: 0; font-weight: bold;">%s</h1>
                                    </td>
                                </tr>
                                
                                <!-- Main Content -->
                                <tr>
                                    <td bgcolor="#ffffff" style="border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                                        <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                                            <tr>
                                                <td style="padding: 40px 30px;">
                                                    <h2 style="color: #333333; font-size: 24px; margin: 0 0 20px 0; font-weight: 600;">Xác thực Email của bạn</h2>
                                                    
                                                    <p style="color: #555555; font-size: 16px; line-height: 24px; margin: 0 0 20px 0;">
                                                        Xin chào <strong style="color: #333333;">%s</strong>,
                                                    </p>
                                                    
                                                    <p style="color: #555555; font-size: 16px; line-height: 24px; margin: 0 0 30px 0;">
                                                        Cảm ơn bạn đã đăng ký tài khoản tại Trendista Shop. Vui lòng nhấn vào nút bên dưới để xác thực địa chỉ email của bạn và kích hoạt tài khoản.
                                                    </p>
                                                    
                                                    <!-- Button -->
                                                    <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                                                        <tr>
                                                            <td align="center" style="padding: 0 0 30px 0;">
                                                                <table border="0" cellpadding="0" cellspacing="0">
                                                                    <tr>
                                                                        <td align="center" bgcolor="#73c6d9" style="border-radius: 6px;">
                                                                            <a href="%s" target="_blank" style="display: inline-block; padding: 16px 40px; font-size: 16px; color: #ffffff; text-decoration: none; font-weight: bold;">
                                                                                Xác thực Email
                                                                            </a>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    
                                                    <!-- Info Box -->
                                                    <table border="0" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #fff8e1; border-left: 4px solid #ffa726; border-radius: 4px;">
                                                        <tr>
                                                            <td style="padding: 15px 20px;">
                                                                <p style="color: #f57c00; font-size: 14px; line-height: 20px; margin: 0;">
                                                                    <strong>⏱️ Lưu ý:</strong> Link xác thực này sẽ hết hạn sau <strong>10 phút</strong>.
                                                                </p>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    
                                                    <p style="color: #777777; font-size: 14px; line-height: 20px; margin: 20px 0 0 0;">
                                                        Nếu bạn không thực hiện đăng ký này, vui lòng bỏ qua email này.
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                
                                <!-- Footer -->
                                <tr>
                                    <td align="center" style="padding: 30px 20px;">
                                        <p style="color: #999999; font-size: 14px; line-height: 20px; margin: 0 0 10px 0;">
                                            Trân trọng,<br/>
                                            <strong style="color: #333333;">Đội ngũ %s</strong>
                                        </p>
                                        <p style="color: #999999; font-size: 12px; line-height: 18px; margin: 0;">
                                            © 2025 Trendista Shop. All rights reserved.
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """, senderName, user.getUsername(), verificationLink, senderName);
    }

    public void sendOrderSuccessEmail(Order order) {
        OrderService orderService = context.getBean(OrderService.class);
        String subject = "Đơn hàng #" + order.getOrderCode() + " đã thanh toán thành công!";

        try {
            BigDecimal subtotal = order.getOrderItems().stream()
                    .map(oi -> oi.getItemPrice().multiply(BigDecimal.valueOf(oi.getQuantity())))
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            DiscountResult discountResult = orderService.recalculateDiscountsFromOrder(order, subtotal);
            BigDecimal discountAmount = discountResult.getDiscountAmount();
            BigDecimal shippingFee = discountResult.getShippingFee();

            String htmlContent = buildOrderSuccessHtml(order, frontendUrl, subtotal, discountAmount, shippingFee);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(sender);
            helper.setTo(new String[]{order.getUser().getEmail(), backupEmail});
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            log.info("Gửi email thanh toán thành công đến {} cho đơn hàng #{}", order.getUser().getEmail(), order.getOrderCode());
            javaMailSender.send(message);
        } catch (Exception e) {
            log.error("Lỗi gửi email thanh toán thành công", e);
        }
    }

    private String buildOrderSuccessHtml(Order order, String appUrl, BigDecimal subtotal,
                                         BigDecimal discountAmount, BigDecimal shippingFee) {
        String orderLink = appUrl + "/order/" + order.getId();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        StringBuilder itemsHtml = new StringBuilder();
        for (OrderItem oi : order.getOrderItems()) {
            BigDecimal itemTotal = oi.getItemPrice().multiply(BigDecimal.valueOf(oi.getQuantity()));
            itemsHtml.append(String.format("""
                <tr>
                    <td style="padding: 15px 10px; border-bottom: 1px solid #eeeeee; color: #333333; font-size: 14px;">
                        <strong>%s</strong>
                    </td>
                    <td align="center" style="padding: 15px 10px; border-bottom: 1px solid #eeeeee; color: #666666; font-size: 14px;">
                        %d
                    </td>
                    <td align="right" style="padding: 15px 10px; border-bottom: 1px solid #eeeeee; color: #666666; font-size: 14px;">
                        %,.0fđ
                    </td>
                    <td align="right" style="padding: 15px 10px; border-bottom: 1px solid #eeeeee; color: #333333; font-size: 14px;">
                        <strong>%,.0fđ</strong>
                    </td>
                </tr>
                """,
                    escapeHtml(oi.getProduct().getName()),
                    oi.getQuantity(),
                    oi.getItemPrice().doubleValue(),
                    itemTotal.doubleValue()
            ));
        }

        return String.format("""
            <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Đơn hàng thành công</title>
            </head>
            <body style="margin: 0; padding: 0; background-color: #f4f7fa; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;">
                <table border="0" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f4f7fa; padding: 20px 0;">
                    <tr>
                        <td align="center">
                            <table border="0" cellpadding="0" cellspacing="0" width="600" style="max-width: 600px; width: 100%%;">
                                <!-- Success Header -->
                                <tr>
                                    <td align="center" bgcolor="#73c6d9" style="padding: 40px 20px; border-radius: 8px 8px 0 0;">
                                        <h1 style="color: #ffffff; font-size: 32px; margin: 0 0 10px 0; font-weight: bold;">✓ Thanh toán thành công!</h1>
                                        <p style="color: #ffffff; font-size: 16px; margin: 0; opacity: 0.95;">
                                            Cảm ơn bạn đã mua hàng tại Trendista Shop
                                        </p>
                                    </td>
                                </tr>
                                
                                <!-- Main Content -->
                                <tr>
                                    <td bgcolor="#ffffff" style="border-radius: 0 0 8px 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                                        <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                                            <tr>
                                                <td style="padding: 30px;">
                                                    <p style="color: #555555; font-size: 16px; line-height: 24px; margin: 0 0 20px 0;">
                                                        Xin chào <strong style="color: #333333;">%s</strong>,
                                                    </p>
                                                    
                                                    <p style="color: #555555; font-size: 16px; line-height: 24px; margin: 0 0 30px 0;">
                                                        Đơn hàng của bạn đã được thanh toán thành công. Chúng tôi sẽ xử lý và giao hàng trong thời gian sớm nhất.
                                                    </p>
                                                    
                                                    <!-- Order Info Box -->
                                                    <table border="0" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f8f9fa; border-radius: 6px; margin-bottom: 30px;">
                                                        <tr>
                                                            <td style="padding: 20px;">
                                                                <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                                                                    <tr>
                                                                        <td style="padding-bottom: 10px;">
                                                                            <p style="color: #666666; font-size: 14px; margin: 0;">Mã đơn hàng</p>
                                                                            <p style="color: #333333; font-size: 18px; font-weight: bold; margin: 5px 0 0 0;">#%s</p>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td style="padding-top: 10px; border-top: 1px solid #e0e0e0;">
                                                                            <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                                                                                <tr>
                                                                                    <td width="50%%">
                                                                                        <p style="color: #666666; font-size: 14px; margin: 0;">Phương thức thanh toán</p>
                                                                                        <p style="color: #333333; font-size: 14px; font-weight: 600; margin: 5px 0 0 0;">%s</p>
                                                                                    </td>
                                                                                    <td width="50%%" align="right">
                                                                                        <p style="color: #666666; font-size: 14px; margin: 0;">Ngày đặt hàng</p>
                                                                                        <p style="color: #333333; font-size: 14px; font-weight: 600; margin: 5px 0 0 0;">%s</p>
                                                                                    </td>
                                                                                </tr>
                                                                            </table>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    
                                                    <!-- Order Items -->
                                                    <h3 style="color: #333333; font-size: 18px; margin: 0 0 20px 0; font-weight: 600;">Chi tiết đơn hàng</h3>
                                                    
                                                    <table border="0" cellpadding="0" cellspacing="0" width="100%%" style="margin-bottom: 20px;">
                                                        <tr>
                                                            <th align="left" style="padding: 12px 10px; background-color: #f8f9fa; color: #666666; font-size: 13px; font-weight: 600; border-bottom: 2px solid #e0e0e0;">Sản phẩm</th>
                                                            <th align="center" style="padding: 12px 10px; background-color: #f8f9fa; color: #666666; font-size: 13px; font-weight: 600; border-bottom: 2px solid #e0e0e0;">SL</th>
                                                            <th align="right" style="padding: 12px 10px; background-color: #f8f9fa; color: #666666; font-size: 13px; font-weight: 600; border-bottom: 2px solid #e0e0e0;">Đơn giá</th>
                                                            <th align="right" style="padding: 12px 10px; background-color: #f8f9fa; color: #666666; font-size: 13px; font-weight: 600; border-bottom: 2px solid #e0e0e0;">Thành tiền</th>
                                                        </tr>
                                                        %s
                                                    </table>
                                                    
                                                    <!-- Summary -->
                                                    <table border="0" cellpadding="0" cellspacing="0" width="100%%" style="margin-top: 20px;">
                                                        <tr>
                                                            <td align="right" colspan="3" style="padding: 10px; color: #666666; font-size: 14px;">Tạm tính:</td>
                                                            <td align="right" style="padding: 10px; color: #333333; font-size: 14px; font-weight: 600; min-width: 100px;">%,.0fđ</td>
                                                        </tr>
                                                        <tr>
                                                            <td align="right" colspan="3" style="padding: 10px; color: #666666; font-size: 14px;">Giảm giá:</td>
                                                            <td align="right" style="padding: 10px; color: #e74c3c; font-size: 14px; font-weight: 600;">-%,.0fđ</td>
                                                        </tr>
                                                        <tr>
                                                            <td align="right" colspan="3" style="padding: 10px; color: #666666; font-size: 14px; border-bottom: 1px solid #e0e0e0;">Phí vận chuyển:</td>
                                                            <td align="right" style="padding: 10px; color: #333333; font-size: 14px; font-weight: 600; border-bottom: 1px solid #e0e0e0;">%,.0fđ</td>
                                                        </tr>
                                                        <tr>
                                                            <td align="right" colspan="3" style="padding: 15px 10px; color: #333333; font-size: 16px; font-weight: bold;">TỔNG CỘNG:</td>
                                                            <td align="right" style="padding: 15px 10px; color: #27ae60; font-size: 20px; font-weight: bold;">%,.0fđ</td>
                                                        </tr>
                                                    </table>
                                                    
                                                    <!-- Button -->
                                                    <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                                                        <tr>
                                                            <td align="center" style="padding: 30px 0 20px 0;">
                                                                <table border="0" cellpadding="0" cellspacing="0">
                                                                    <tr>
                                                                        <td align="center" bgcolor="#73c6d9" style="border-radius: 6px;">
                                                                            <a href="%s" target="_blank" style="display: inline-block; padding: 16px 40px; font-size: 16px; color: #ffffff; text-decoration: none; font-weight: bold;">
                                                                                Xem chi tiết đơn hàng
                                                                            </a>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    
                                                    <p style="color: #777777; font-size: 14px; line-height: 20px; margin: 20px 0 0 0; text-align: center;">
                                                        Nếu bạn có bất kỳ câu hỏi nào, vui lòng liên hệ với chúng tôi.
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                
                                <!-- Footer -->
                                <tr>
                                    <td align="center" style="padding: 30px 20px;">
                                        <p style="color: #999999; font-size: 14px; line-height: 20px; margin: 0 0 10px 0;">
                                            Cảm ơn bạn đã tin tưởng và mua sắm tại<br/>
                                            <strong style="color: #333333;">Trendista Shop</strong>
                                        </p>
                                        <p style="color: #999999; font-size: 12px; line-height: 18px; margin: 0;">
                                            © 2025 Trendista Shop. All rights reserved.
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """,
                order.getUser().getFirstName() + " " + order.getUser().getLastName(),
                order.getOrderCode(),
                order.getPaymentMethod().name(),
                order.getOrderDate().format(dateFormatter),
                itemsHtml.toString(),
                subtotal.doubleValue(),
                discountAmount.doubleValue(),
                shippingFee.doubleValue(),
                order.getTotalAmount().doubleValue(),
                orderLink
        );
    }

    private String escapeHtml(String input) {
        if (input == null) return "";
        return input.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

    public String sendOrderCancelledEmail(Order order, String paymentLink) {
        String subject = "Đơn hàng #" + order.getOrderCode() + " đã bị hủy – Thanh toán lại ngay!";

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(sender);
            helper.setTo(new String[]{order.getUser().getEmail(), backupEmail});
            helper.setSubject(subject);
            helper.setText(buildOrderCancelledHtml(order, paymentLink), true);

            javaMailSender.send(message);
            return "Email hủy đơn + link thanh toán lại đã gửi";
        } catch (MessagingException e) {
            return "Lỗi gửi email: " + e.getMessage();
        }
    }

    private String buildOrderCancelledHtml(Order order, String paymentLink) {
        String cancelReason = order.getOrderStatus() == OrderStatus.CANCELLED ?
                "bạn đã hủy thanh toán" : "đơn hàng đã hết hạn thanh toán";

        return String.format("""
            <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
            <html xmlns="http://www.w3.org/1999/xhtml">
            <head>
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <title>Đơn hàng bị hủy</title>
            </head>
            <body style="margin: 0; padding: 0; background-color: #f4f7fa; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;">
                <table border="0" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f4f7fa; padding: 20px 0;">
                    <tr>
                        <td align="center">
                            <table border="0" cellpadding="0" cellspacing="0" width="600" style="max-width: 600px; width: 100%%;">
                                <!-- Header -->
                                <tr>
                                    <td align="center" bgcolor="#e74c3c" style="padding: 40px 20px; border-radius: 8px 8px 0 0;">
                                        <h1 style="color: #ffffff; font-size: 32px; margin: 0 0 10px 0; font-weight: bold;">⚠️ Đơn hàng bị hủy</h1>
                                        <p style="color: #ffffff; font-size: 16px; margin: 0; opacity: 0.95;">
                                            Nhưng bạn vẫn có thể thanh toán lại
                                        </p>
                                    </td>
                                </tr>
                                
                                <!-- Main Content -->
                                <tr>
                                    <td bgcolor="#ffffff" style="border-radius: 0 0 8px 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.1);">
                                        <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                                            <tr>
                                                <td style="padding: 30px;">
                                                    <p style="color: #555555; font-size: 16px; line-height: 24px; margin: 0 0 20px 0;">
                                                        Xin chào <strong style="color: #333333;">%s</strong>,
                                                    </p>
                                                    
                                                    <!-- Alert Box -->
                                                    <table border="0" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #fff3cd; border-left: 4px solid #ffc107; border-radius: 4px; margin-bottom: 25px;">
                                                        <tr>
                                                            <td style="padding: 20px;">
                                                                <p style="color: #856404; font-size: 15px; line-height: 22px; margin: 0;">
                                                                    Đơn hàng <strong>#%s</strong> của bạn đã bị hủy do <strong>%s</strong>.
                                                                </p>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    
                                                    <p style="color: #555555; font-size: 16px; line-height: 24px; margin: 0 0 25px 0;">
                                                        Chúng tôi đã hoàn lại số lượng sản phẩm vào kho. <strong style="color: #333333;">Bạn có thể thanh toán lại ngay bây giờ</strong> để tiếp tục đơn hàng này!
                                                    </p>
                                                    
                                                    <!-- Info Box -->
                                                    <table border="0" cellpadding="0" cellspacing="0" width="100%%" style="background-color: #f8f9fa; border-radius: 6px; margin-bottom: 25px;">
                                                        <tr>
                                                            <td style="padding: 20px;">
                                                                <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                                                                    <tr>
                                                                        <td>
                                                                            <p style="color: #666666; font-size: 14px; margin: 0 0 5px 0;">⏰ Thời gian hết hạn thanh toán:</p>
                                                                            <p style="color: #e74c3c; font-size: 18px; font-weight: bold; margin: 0;">%s</p>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    
                                                    <p style="color: #777777; font-size: 14px; line-height: 20px; margin: 0 0 30px 0;">
                                                        <strong>Lưu ý:</strong> Nếu bạn không thanh toán trong thời gian trên, đơn hàng sẽ bị hủy vĩnh viễn và bạn cần đặt hàng lại từ đầu.
                                                    </p>
                                                    
                                                    <!-- Button -->
                                                    <table border="0" cellpadding="0" cellspacing="0" width="100%%">
                                                        <tr>
                                                            <td align="center" style="padding: 10px 0 20px 0;">
                                                                <table border="0" cellpadding="0" cellspacing="0">
                                                                    <tr>
                                                                        <td align="center" bgcolor="#73c6d9" style="border-radius: 6px;">
                                                                            <a href="%s" target="_blank" style="display: inline-block; padding: 16px 40px; font-size: 16px; color: #ffffff; text-decoration: none; font-weight: bold;">
                                                                                💳 Thanh toán ngay
                                                                            </a>
                                                                        </td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                    
                                                    <p style="color: #777777; font-size: 14px; line-height: 20px; margin: 20px 0 0 0; text-align: center;">
                                                        Cần hỗ trợ? Liên hệ với chúng tôi bất cứ lúc nào.
                                                    </p>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                
                                <!-- Footer -->
                                <tr>
                                    <td align="center" style="padding: 30px 20px;">
                                        <p style="color: #999999; font-size: 14px; line-height: 20px; margin: 0 0 10px 0;">
                                            Cảm ơn bạn đã quan tâm đến<br/>
                                            <strong style="color: #333333;">Trendista Shop</strong>
                                        </p>
                                        <p style="color: #999999; font-size: 12px; line-height: 18px; margin: 0;">
                                            © 2025 Trendista Shop. All rights reserved.
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </body>
            </html>
            """,
                order.getUser().getFirstName() + " " + order.getUser().getLastName(),
                order.getOrderCode(),
                cancelReason,
                order.getExpiredAt().format(DateTimeFormatter.ofPattern("HH:mm, dd/MM/yyyy")),
                paymentLink
        );
    }
}