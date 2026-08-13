package com.mentalhealthapi.service;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.SeminarRequestDTO;
import com.mentalhealthapi.dto.TourJobRequestDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class TourJobService {
	private EmailService emailService;
	public TourJobService(EmailService emailService) {
		this.emailService=emailService;
	}

	public String sendJobEmail(TourJobRequestDTO request) {
		try {
			String content = "Dear " + request.getName() + "様\n\n" +
	                "この度は森田正馬没後80年記念・宿泊・オプショナルツアーにお申し込みいただき、ありがとうございます。" +
	                "ご希望の内容を承りましたので、詳細のご案内は、東武トップツアーズ株式会社・高知支店の担当より、代表申込み者さま宛に、郵送あるいはメール等で、改めてご案内させていただきますので、よろしくお願い致します。\n\n" +
	                "※尚、キャンセルされる場合いは、その期日によりキャンセル料がかかりますので、ご注意下さい。詳しくは下記の署名のアドレス又はご連絡先までお問い合わせ下さい。\n\n" +
	                "■代表者氏名： " + request.getNameOfMainPerson() + "\n\n" +
	                "■ふりがな： " + request.getFurigana() + "\n\n" +
	                "■性別： " + (request.getSex().equals("M") ? "男性" : "女性") + "\n\n" +
	                "■ご住所： " + request.getAddress() + "\n\n" +
	                // ... continue populating the email body with the remaining fields
	                "■宿泊人数： " + request.getNumberOfParticipants() + "人\n\n" +
	                (request.isOtherNameO() ? "■宿泊者氏名： " + request.getOtherName() + "\n\n" : "") +
	                "**************************************************************************\n" +
	                "東武トップツアーズ株式会社　高知支店\n" +
	                "（担当：森本・坂東）\n" +
	                "〒780-0822\n" +
	                "高知県はりまや町2-2-11　富士火災高知ビル7F\n" +
	                "TEL：088-875-2171　FAX：088-825-0170\n" +
	                "E-mail：80nen-kochi@tobutoptours.co.jp\n" +
	                "営業時間：9時00分～18時00分\n" +
	                "（土・日・祝日・12/30～1/3は休業）\n" +
	                "**************************************************************************\n";
	        
			//need to update cc as per mental admin
			//mentalzaidan@mental-health.org,80nen-kochi@tobutoptours.co.jp,mnewton@ops.net
			String [] cc= {"bijila@nivid.co","neethu@nivid.co"};
			emailService.sendSeminarMail(request.getEmail(), request.getEmail(),cc, "セミナー参加の申込みの件", content);
			return "Mail Send";
		}catch(Exception exp)
		{
			return exp.getMessage();
		}
	}
}
