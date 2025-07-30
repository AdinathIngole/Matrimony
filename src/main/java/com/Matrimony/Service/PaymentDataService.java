package com.Matrimony.Service;

import java.util.List;

import com.Matrimony.Entities.PaymentData;


public interface PaymentDataService {

	public PaymentData createOrder(PaymentData paymentData) throws Exception;
	public boolean verifyPayment(String rzpPaymentId, String rzpOrderId, String rzpSignature);
	public List<PaymentData> getAllUsers();
}
