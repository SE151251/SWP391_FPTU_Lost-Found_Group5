/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SendMail;
import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
/**
 *
 * @author LENOVO
 */
//xuggbomysecmyskh -- Đây pass của ứng dụng được tạo để bởi gmail để dùng chức năng send mail
//       String myAccountEmail="lostfoundsystemswp391@gmail.com"; -- Đây là tài khoản gmail được dùng để thực hiện chức năng send mail
//        String password="SWP391@LostAndFound@Group5"; -- Password của email
public class JavaMailUtil {
public static void sendMail(String receiverEmail, String reason) {

        final String username = "lostfoundsystemswp391@gmail.com";// Hãy thay thế bằng tài khoản gmail của bạn
        final String password = "xuggbomysecmyskh"; // Thay đổi mật khẩu theo gmail của bạn đã tạo 

        Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true"); //TLS
        
        Session session = Session.getInstance(prop,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("lostfoundsystemswp391@gmail.com"));// Hãy thay thế bằng tài khoản gmail của bạn giống ở trên
            
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(receiverEmail)
            );
            message.setSubject("Annoucement From Lost&Found System");
//            message.setText("Dear You,"
//                    + "\n\n Please do not spam my email!");
           message.setText(reason);

            Transport.send(message);

            //System.out.println("Done");

        } catch (MessagingException e) {
            //e.printStackTrace();
            System.out.println("Error at JavaMailUtil in package SendMail. Please check your account and password again. Send mail fail!");
        }
    }
}
