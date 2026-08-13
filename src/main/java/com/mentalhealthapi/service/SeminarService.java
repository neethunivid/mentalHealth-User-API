package com.mentalhealthapi.service;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.SeminarRequestDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SeminarService {
	private EmailService emailService;
	public SeminarService(EmailService emailService) {
		this.emailService=emailService;
	}

	public String sendSeminarApplication(SeminarRequestDTO request) {
		try {
			String name = request.getName1() + " " + request.getName2();
			String content = 
			    "test テスト\n" +
			    "---------\n" +
			    name + "  様\n" +
			    "\n" +
			    "\n" +
			    "この度は当財団セミナーへのご参加希望\n" +
			    "をいただき、誠にありがとうございます。\n" +
			    "参加日時に会場にてお待ち申しあげます.\n" +
			    "\n" +
			    "参加希望セミナー: " + request.getSeminarName() + "\n" +
			    "名前(漢字) : " + request.getName1() + " " + request.getName2() + "\n" +
			    "ふりがな : " + request.getNamek1() + " " + request.getNamek2() + "\n" +
			    "性別 : " + request.getSex() + "\n" +
			    "職業 : "+ request.getJob() +"\n" +
			    "職種また役職 : "+ request.getJobTitle() +"\n" +
			    "勤め先・学校 : "+ request.getSchool() +"\n" +
			    "年齢 : "+ request.getAge() +"\n" +
			    "メールアドレス : "+ request.getEmail() +"\n" +
			    "参加人数 : "+ request.getParticipants() +"\n" +
			    "参加理由 : \n" +
			     request.getReason() +"\n" +
			    "\n" +
			    "セミナーで質問したいこと、取り上げてほしいこと :\n" +
			    "\n" +
			    request.getTopic() +"\n" +
			    "\n" +
			    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
			    "〒541-0056大阪市中央区久太郎町1-9-18三栄ビル7F\n" +
			    "財団法人メンタルヘルス岡本記念財団\n" +
			    "セミナー 事務局\n" +
			    "http://www.mental-health.org\n" +
			    "ad@mental-health.org\n" +
			    "TEL:06-6262-6862  FAX:06-6947-6805\n" +
			    "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +   
			    "\n" +
			    request.getEmail();
			//need to update cc as per mental admin

			String [] cc= {"bijila@nivid.co","neethu@nivid.co"};
		
		emailService.sendSeminarMail(request.getEmail(), request.getEmail(),cc, "セミナー参加の申込みの件", content);
		return "Mail Send";
		}catch(Exception exp)
		{
			return exp.getMessage();
		}
	}
}
