package com.Matrimony.Service;

import com.Matrimony.Entities.PaymentData;

public interface EmailService {

	 void sendEmail(String to, String subject, String message);
	 public void sendPaymentDetailsEmail(String to, PaymentData paymentData);
	 public void sendUserEmail(PaymentData paymentData, String receiverName);

}
