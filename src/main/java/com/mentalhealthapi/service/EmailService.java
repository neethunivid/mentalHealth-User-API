package com.mentalhealthapi.service;
import java.util.*;
import java.util.Map;
import java.io.File;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.mentalhealthapi.dto.DiagnoseDataDTO;
import com.mentalhealthapi.dto.InquiryDTO;
import com.mentalhealthapi.dto.JobformDTO;
import com.mentalhealthapi.model.GrantRequest;
import com.mentalhealthapi.model.GrantRequest2;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.ReservationDetails;
import com.mentalhealthapi.model.ReservationRegion;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;


@Service
public class EmailService {

    private JavaMailSender mailSender;
    private TemplateEngine templateEngine;

    @Autowired
    public void MailService(JavaMailSender mailSender, TemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    public void sendMemberEmail(String recipientEmail, String name,Member member,String pref,String job) throws MessagingException {
        // Create the MIME message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Set recipient, subject, and email content
        helper.setTo(recipientEmail);
        helper.setSubject("Member Registration Confirmation");
     
        // Prepare the context with data for the template
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("name2", member.getName2());
        context.setVariable("namek", member.getNamek());
        context.setVariable("namek2", member.getName2());
        context.setVariable("memberid", member.getMemberid());
        context.setVariable("password", member.getPassword());
        context.setVariable("sex", member.getSex()==2?"女性":"男");
        context.setVariable("dob", member.getDob());
        context.setVariable("job", job);
        context.setVariable("zipcode", member.getZipcode());
        context.setVariable("pref", pref);
        context.setVariable("address1", member.getAddress1());
        context.setVariable("address2", member.getAddress2());
        context.setVariable("address3", member.getAddress3());
        context.setVariable("tel", member.getTel());
        context.setVariable("mob", member.getMob());
        context.setVariable("workplace", member.getWorkplace());
        context.setVariable("howfound", member.getHowfound());
        context.setVariable("email_pc", member.getEmail_pc());
        context.setVariable("email_mob", member.getEmail_mob());       
        context.setVariable("mailmagazine", member.getMailmagazine()==1?"購読":"中止");
        // Generate the HTML content using the template and context
        String htmlContent = templateEngine.process("member-registration", context);
        helper.setText(htmlContent, true);

        // Send the email
        mailSender.send(message);
    }

    public void sendMemberRegistrationMail(String recipientEmail, String name,String room,Member member) throws MessagingException {
    	 MimeMessage message = mailSender.createMimeMessage();
         MimeMessageHelper helper = new MimeMessageHelper(message, true);

         // Set recipient, subject, and email content
         //helper.setTo(recipientEmail);
         helper.setFrom("joseishinseikanri@mental-health.org");
         helper.setTo("bijila@nivid.co");
         helper.setSubject("Member Registration Confirmation");

         // Prepare the context with data for the template
         Context context = new Context();
         context.setVariable("name", name);
         context.setVariable("name2", member.getName2());
         context.setVariable("username", member.getMemberid());         
         context.setVariable("password", maskString(member.getPassword()));
         context.setVariable("room", room);
      // Generate the HTML content using the template and context
         String htmlContent = templateEngine.process("member-approve", context);
         helper.setText(htmlContent, true);

         // Send the email
         mailSender.send(message);
         
    }
    
    private static String maskString(String input) {
        if (input != null && !input.isEmpty()) {
            int maskLength = Math.max(input.length() - 2, 0);
            String mask = "*".repeat(maskLength);
            return input.substring(0, 2) + mask;
        }
        return input;
    }
    
    
    
    public void sendEmailWithAttachments(String[] to,String[] cc, String subject, String text, List<String> attachmentFilePaths)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("remitha@nivid.co");
        //helper.setTo(to);
        helper.setBcc(cc);
        helper.setSubject(subject);
        helper.setText(text);

        for (String filePath : attachmentFilePaths) {
            File file = new File(filePath);
            helper.addAttachment(file.getName(), file);
        }

        mailSender.send(message);
    }
   
    
    public void sendNewsletterEmail(String recipientEmail, String subject, String body, boolean isHtml) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, isHtml);

            mailSender.send(messageHelper.getMimeMessage());
            System.out.println("Email sent successfully to: " + recipientEmail);
        } catch (Exception e) {
            System.err.println("Failed to send email to: " + recipientEmail);
            e.printStackTrace();
        }
    }
    
    
    public void sendMaganizeMail(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom("MHAdmin <MHAdmin>");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            // Handle the exception
        }
    }
    
    public void sendAdminMail(String[] to,String[] cc, String subject, String text)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("bijila@nivid.co");
        //helper.setTo(to);
        helper.setBcc(cc);
        helper.setSubject(subject);
        helper.setText(text);      
        mailSender.send(message);
    }
    
    public void sendReservationAddMail(ReservationDetails resdet,String date1, String time1, String region) throws MessagingException {
   	 MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Set recipient, subject, and email content
        //helper.setTo(recipientEmail);
        helper.setTo("bijila@nivid.co");
        helper.setSubject("【キャンセルのご案内】無料カウンセリング相談");
        
        String sex = resdet.getGender();
        String gender;

        if ("F".equals(sex)) {
            gender = "女性";
        } else {
            gender = "男";
        }       
        // Prepare the context with data for the template
        Context context = new Context();
        context.setVariable("name", resdet.getPersonName());
        context.setVariable("surname", resdet.getSurname());
        context.setVariable("gender",gender);
        context.setVariable("age",resdet.getAge());
        context.setVariable("job", resdet.getJob());
        context.setVariable("zip", resdet.getPostalcode());
        context.setVariable("town", resdet.getTown());
        context.setVariable("state", resdet.getPref());
        context.setVariable("address", resdet.getAddress());
        context.setVariable("phone", resdet.getTelephone());
        context.setVariable("email", resdet.getEmail());
        context.setVariable("type_of_counselling",resdet.getType_of_counselling());
        context.setVariable("time", time1);
        context.setVariable("date", date1);
        context.setVariable("region", region);      
       
        
     // Generate the HTML content using the template and context
        String htmlContent = templateEngine.process("reservation-mail", context);
        helper.setText(htmlContent, true);

        // Send the email
        mailSender.send(message);
        
   }

	public void sendReservationUserMail(ReservationDetails resdet,String date1, String time1) throws MessagingException {
   	 MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        // Set recipient, subject, and email content
        //helper.setTo(recipientEmail);
        helper.setTo("bijila@nivid.co");
        helper.setSubject("【キャンセルのご案内】無料カウンセリング相談");
        // Prepare the context with data for the template
        Context context = new Context();
        context.setVariable("name", resdet.getPersonName());
        context.setVariable("surname", resdet.getSurname());        
        context.setVariable("time", time1);
        context.setVariable("date", date1);
       
        
     // Generate the HTML content using the template and context
        String htmlContent = templateEngine.process("reservation-usermail", context);
        helper.setText(htmlContent, true);

        // Send the email
        mailSender.send(message);
        
   }

	
	public void sendReservationCancelMail(ReservationDetails resdet) throws MessagingException {
	   	 MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);

	        // Set recipient, subject, and email content
	        //helper.setTo(recipientEmail);
	        helper.setTo("bijila@nivid.co");
	        helper.setSubject("【キャンセルのご案内】無料カウンセリング相談");
	        // Prepare the context with data for the template
	        Context context = new Context();
	        context.setVariable("name", resdet.getPersonName());
	        context.setVariable("surname", resdet.getSurname());           
	        
	     // Generate the HTML content using the template and context
	        String htmlContent = templateEngine.process("reservation-cancel", context);
	        helper.setText(htmlContent, true);

	        // Send the email
	        mailSender.send(message);
	        
	   }
	public void sendDiagnoseEmail(String recipientEmail, String subject, String body, boolean isHtml) {
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject(subject);
            messageHelper.setText(body, isHtml);

            mailSender.send(messageHelper.getMimeMessage());
            System.out.println("Diagnose Email sent successfully to: " + recipientEmail);
        } catch (Exception e) {
            System.err.println("Failed to send diagnose email to: " + recipientEmail);
            e.printStackTrace();
        }
    }

	public String sendInquiryMail(InquiryDTO request) {
		String subject="お問合せ";
		String to = "bijila@nivid.co";
		//string to admin email;
		StringBuilder body = new StringBuilder();
        body.append("お問い合わせ内容\n");
        body.append("■お名前: ").append(request.getName()).append("\n");
        body.append("■ご職業: ").append(request.getJob()).append("\n");
        body.append("■年齢: ").append(request.getAge()).append("\n");
        body.append("■メールアドレス: ").append(request.getEmail()).append("\n");
        body.append("■内容: ").append(request.getComment()).append("\n");
        
		try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailSender.createMimeMessage(), true);
            messageHelper.setTo(to);         
            messageHelper.setSubject(subject);
            messageHelper.setText(body.toString(), false);

            mailSender.send(messageHelper.getMimeMessage());           
        } catch (Exception e) {           
            e.printStackTrace();
        }

		return "true";
		
	}

	public String sendJobform(JobformDTO request)
            throws MessagingException {
		 String subject="";
		 StringBuilder mailData = new StringBuilder();
		    mailData.append("この度は当財団へのご応募、ありがとうございます。\n");
	        mailData.append("ご応募は確かに受付致しましたので、後ほど、事務局の方からメールまたはお電話（メールが届かない場合等）でご連絡致しますので、よろしくお願い致します。\n\n");
	        mailData.append("＜ご応募内容＞\n");
	        mailData.append("■お名前：   \n");
	        mailData.append(request.getName()).append(" ").append(request.getName2()).append("\n \n");
	        mailData.append("■ふりがな： \n");
	        mailData.append(request.getNamek1()).append(" ").append(request.getNamek2()).append("\n \n");
	        mailData.append("■性別： \n");
	        mailData.append(request.getSex()).append("\n \n");
	        mailData.append("■職業： \n");
	        mailData.append(request.getJob()).append("\n \n");
	        mailData.append("■生年月日： \n");
	        mailData.append(request.getDob()).append("\n \n");
	        mailData.append("■Email： \n");
	        mailData.append(request.getEmail()).append("\n \n");
	        mailData.append("■電話・携帯番号： \n");
	        mailData.append(request.getPhone()).append("\n \n");
	        mailData.append("■添付書類： \n");
	        mailData.append(request.getDocPath()).append("\n \n");
	        mailData.append(" \n \n");
	        mailData.append("＜送信先・お問合せ＞ \n");
	        mailData.append("〒530-0057 \n");
	        mailData.append("大阪市北区曾根崎2-5-10梅田パシフィックビル7F \n");
	        mailData.append("(公財）メンタルヘルス岡本記念財団事務局 \n");
	        mailData.append("HP:https://www.mental-health.org \n");
	        mailData.append("Email:mentalzaidan@mental-health.org \n");
	        mailData.append("TEL:06-6809-1211 FAX:06-6809-1233 \n \n");
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo("bijila@nivid.co");          
        helper.setSubject(subject);
        helper.setText(mailData.toString());        
      //  File file = new File(request.getDocPath());
       // helper.addAttachment(file.getName(), file);
        mailSender.send(message);
        return " Mail send Sucessfully";
    }
	public String sendDiagnoseUserEmail(DiagnoseDataDTO request) {
		String subject="MH-SelfCheck";
		String to = request.getEmail();
		StringBuilder mailData = new StringBuilder();
        mailData.append("自己診断チェックの結果内容のお知らせ\n");
        Map<Integer, String> typeMap = Map.of(
        	    1, "森田神経質度のチェック",
        	    2, "対人恐怖度チェック",
        	    3, "パニック障害チェック",
        	    4, "うつ病チェック",
        	    5, "強迫性障害チェック"
        	);

        	int type = request.getType(); 
        	mailData.append("1. 自己診断チェック名\n");
        	mailData.append(typeMap.get(type));
        	List<String> selectedOptions = new ArrayList<>();
            for (int talkTypeId : request.getTalkType()) {
                switch (talkTypeId) {
                    case 1:
                        selectedOptions.add("症状について詳しく知りたい");
                        break;
                    case 2:
                        selectedOptions.add("治療方法を知りたい");
                        break;
                    case 3:
                        selectedOptions.add("治療機関を知りたい");
                        break;
                    case 4:
                        selectedOptions.add("その他 \n\t"+request.getOther());         
                        break;
                }
            }
            StringBuilder selectedStr = new StringBuilder();
            int counter = 1;
            for (String opt : selectedOptions) {
                selectedStr.append("  ").append(counter).append(") ").append(opt).append("\n");
                counter++;
            }
            System.out.println(selectedStr);
        mailData.append("\n2. 診断結果\n");
        mailData.append(request.getDiagnoseResult()).append("\n");
        mailData.append("\n3. 相談内容\n");
        mailData.append(selectedStr).append("\n"); 
        mailData.append("\nお名前\n");
        mailData.append(request.getName()).append("\n"); 
        mailData.append("\nE-mail\n");
        mailData.append(request.getEmail()).append("\n"); 
        mailData.append("\nご利用機器\n");
        mailData.append(request.getDeviceData()).append("\n\n\n\n"); 
        mailData.append("＜送信先・お問合せ＞ \n");
        mailData.append("〒530-0057 \n");
        mailData.append("大阪市北区曾根崎2-5-10梅田パシフィックビル7F \n");
        mailData.append("(公財）メンタルヘルス岡本記念財団事務局 \n");
        mailData.append("HP:https://www.mental-health.org \n");
        mailData.append("Email:mentalzaidan@mental-health.org \n");
        mailData.append("TEL:06-6809-1211 FAX:06-6809-1233 \n \n");
        
		try {
			MimeMessage message = mailSender.createMimeMessage();
		    MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(to);         
            messageHelper.setSubject(subject);
            messageHelper.setText(mailData.toString(), false);
            mailSender.send(messageHelper.getMimeMessage());           
        } catch (Exception e) {           
            e.printStackTrace();
        }

		return "true";
		
	}
	 public void sendSeminarMail(String to,String from,String[] cc, String subject, String text)
	            throws MessagingException {

	        MimeMessage message = mailSender.createMimeMessage();
	        MimeMessageHelper helper = new MimeMessageHelper(message, true);
	      
	        helper.setTo(to);
	        helper.setFrom(from);
	        helper.setSubject(subject);
	        helper.setText(text);  
	        helper.setBcc(cc);
	        mailSender.send(message);
	    }

	public String sendUserEditMail(Member request,String email,String[] cc) {
		   String subject = "会員登録情報の修正";
		    String to = email; 

		    StringBuilder body = new StringBuilder();
		    body.append(request.getName()).append(" ").append(request.getName2()).append("　様\n\n");
		    body.append("いつも、当メンタルヘルス財団の「心の体験フォーラム」をご利用いただき、誠にありがとうございます。\n");
		    body.append("さてご登録いただいておりましたあなたの会員登録情報は、下記の項目が修正されましたのでご連絡致します。\n");
		    body.append("体験フォーラムのご利用は、修正登録後の内容が必要となりますのでご注意ください。\n");
		    body.append("よろしくお願い致します。\n\n");
		    body.append("＜会員登録の修正情報＞\n");
		    Context context = new Context();
	        context.setVariable("name", request.getName());
	        context.setVariable("name2", request.getName2());
	        context.setVariable("namek", request.getNamek());
	        context.setVariable("namek2", request.getName2());
	        context.setVariable("memberid", request.getMemberid());
	        context.setVariable("dob", request.getDob());
	        context.setVariable("password", request.getPassword());
	        context.setVariable("sex", request.getSex()==2?"女性":"男");
	        context.setVariable("zipcode", request.getZipcode());
	        //context.setVariable("pref", pref);
	        context.setVariable("address1", request.getAddress1());
	        context.setVariable("address2", request.getAddress2());
	        context.setVariable("address3", request.getAddress3());
	        context.setVariable("tel", request.getTel());
	        context.setVariable("mob", request.getMob());
	        context.setVariable("workplace", request.getWorkplace());
	        context.setVariable("howfound", request.getHowfound());
	        context.setVariable("email_pc", request.getEmail_pc());
	        context.setVariable("email_mob", request.getEmail_mob());       
	        context.setVariable("mailmagazine", request.getMailmagazine()==1?"購読":"中止");
	        


		try {
			 MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(to);         
            messageHelper.setSubject(subject);
            messageHelper.setBcc(cc);
            messageHelper.setText(body.toString(), false);
            String htmlContent = templateEngine.process("member-edit", context);
            messageHelper.setText(htmlContent, true);
            mailSender.send(message);           
        } catch (Exception e) {           
            e.printStackTrace();
        }

		return "Mail Send";
	}

    public String sendUserDeletedMail(Member request,String email,String[] cc){

        String subject = "会員登録情報の修正";
        String to = email;

        StringBuilder body = new StringBuilder();
        body.append(request.getName()).append(" ").append(request.getName2()).append("　様\n\n");
        body.append("これまで当メンタルヘルス財団の「心の体験フォーラム」のご利用、誠にありがとうございました。");
        body.append("この度、会員の退会処理及び会員登録情報を削除致しましたので、ご連絡致します。\n" );
        body.append("今後とも、当財団のホームページのご利用、よろしくお願い致します。\n\n");

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setBcc(cc);
            messageHelper.setText(body.toString(), false);
            Context context = new Context();
            String htmlContent = templateEngine.process("member-delete", context );
            messageHelper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Mail Send";
    }
	public String sendUserSlotReservationEmail(ReservationDetails reservationDetails, ReservationRegion region,String time1, LocalDate date1) {       
        String subject ="【仮予約のご案内】無料カウンセリング相談"  ; 
        String to ="bijila@nivid.co";
        String cc="";
        String sex = reservationDetails.getGender();
        String gender;

        if ("F".equals(sex)) {
            gender = "女性";
        } else {
            gender = "男";
        }       
        // Prepare the context with data for the template
        Context context = new Context();
        context.setVariable("name",reservationDetails.getPersonName() );
        context.setVariable("surname", reservationDetails.getSurname());
        context.setVariable("gender",gender);
        context.setVariable("age",reservationDetails.getAge());
        context.setVariable("job", reservationDetails.getJob());
        context.setVariable("zip", reservationDetails.getPostalcode());
        context.setVariable("town", reservationDetails.getTown());
        context.setVariable("state", reservationDetails.getPref());
        context.setVariable("address", reservationDetails.getAddress());
        context.setVariable("phone", reservationDetails.getTelephone());
        context.setVariable("email", reservationDetails.getEmail());
        context.setVariable("type_of_counselling",reservationDetails.getType_of_counselling());
        context.setVariable("time", time1);
        context.setVariable("date", date1);
        context.setVariable("region", region);      
    
		try {
			 MimeMessage message = mailSender.createMimeMessage();
           MimeMessageHelper messageHelper = new MimeMessageHelper(message, true);
           messageHelper.setTo(to);         
           messageHelper.setSubject(subject);
          // messageHelper.setBcc(cc);         
           String htmlContent = templateEngine.process("user-reservationSlot", context);
           messageHelper.setText(htmlContent, true);
           mailSender.send(message);           
       } catch (Exception e) {           
           e.printStackTrace();
       }

		return "Mail Send";
	}

	

}
