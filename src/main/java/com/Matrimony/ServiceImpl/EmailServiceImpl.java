package com.Matrimony.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.Matrimony.Entities.PaymentData;
import com.Matrimony.Service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendEmail(String to, String subject, String message) {

		SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

		simpleMailMessage.setTo(to);
		simpleMailMessage.setSubject(subject);
		simpleMailMessage.setText(message);
		mailSender.send(simpleMailMessage);

	}

	@Override
	public void sendPaymentDetailsEmail(String to, PaymentData paymentData) {

		String subject = "Payment Received";
		String body = "You have received a new User Payment with the following details:\n\n" + "Name: "
				+ paymentData.getName() + "\n" + "Email: " + paymentData.getEmail() + "\n" + "Contact: "
				+ paymentData.getContact() + "\n" + "Amount: " + paymentData.getAmount() + "\n" + "Transaction ID: "
				+ paymentData.getRzpOrderId() + "\n\n" + "Thank you!";
		sendEmail(to, subject, body);

	}

	@Override
	public void sendUserEmail(PaymentData paymentData, String receiverName) {
		String subject = "Payment Successful";
		String body = "Thank you for your Subcriptions!\n\n" + "Receiver: " + receiverName + "\n" + "Amount: "
				+ paymentData.getAmount() + "\n" + "Transaction ID: " + paymentData.getRzpPaymentId() + "\n\n"
				+ "Your support is greatly appreciated!";
		sendEmail(paymentData.getEmail(), subject, body);
	}

}
