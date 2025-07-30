package com.Matrimony.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.Matrimony.Entities.PaymentData;
import com.Matrimony.Repository.PaymentDataRepository;
import com.Matrimony.Service.EmailService;
import com.Matrimony.Service.PaymentDataService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;

@Service
public class PaymentDataServiceImpl implements PaymentDataService{
	
	@Autowired
	private PaymentDataRepository paymentRepository;
	
	@Autowired
	private EmailService emailService;
	
	@Value("${razorpay.key.id}")
	private String razorPayKey;
	
	@Value("${razorpay.secret.key}")
	private String razorPaySecretKey;
	
	private RazorpayClient razorpayClient;
	
	private final String PayTo = "RistoKiDor";

	@Override
	public PaymentData createOrder(PaymentData paymentData) throws Exception {
		
		try {
			JSONObject orderRequest = new JSONObject();
			
			orderRequest.put("amount", paymentData.getAmount() * 100);
			orderRequest.put("currency", "INR");
			orderRequest.put("receipt", paymentData.getEmail());
			
			this.razorpayClient = new RazorpayClient(razorPayKey, razorPaySecretKey);
			
			Order razorPayOrder = razorpayClient.orders.create(orderRequest);
			
			paymentData.setRzpOrderId(razorPayOrder.get("id"));
			paymentData.setRzpPaymentId(razorPayOrder.get("payment_id"));
			paymentData.setRzpSignature(razorPayOrder.get("status"));
			
			paymentRepository.save(paymentData);
			return paymentData;
		} catch (RazorpayException e) {
			
			e.printStackTrace();
			throw new RuntimeException("Error creating order with Razorpay: " +e.getMessage());
		}
		
	}

	@Override
	public boolean verifyPayment(String rzpPaymentId, String rzpOrderId, String rzpSignature) {
		try {

			Map<String, String> params = new HashMap<>();
	        params.put("razorpay_payment_id", rzpPaymentId);
	        params.put("razorpay_order_id", rzpOrderId);
	        params.put("razorpay_signature", rzpSignature);

	        JSONObject options = new JSONObject(params);
	        
	        boolean isVerifyPaymentSignature = Utils.verifyPaymentSignature(options, razorPaySecretKey);

	        if (isVerifyPaymentSignature) {
	            // Update the status in the database
	           PaymentData paymentData = paymentRepository.findByRzpOrderId(rzpOrderId);
	         
	            if (paymentData != null) {
	            	paymentData.setRzpPaymentId(rzpPaymentId);
	            	paymentData.setRzpSignature("Paid");
	               
	                paymentRepository.save(paymentData);//save updated status in databases
	                
	                // Send email to the donor
	                emailService.sendUserEmail(paymentData, PayTo);
	                 // Send email to the receiver with donation details
	                emailService.sendPaymentDetailsEmail("adiingole1006@gmail.com", paymentData);
	            }
	        }
	        return isVerifyPaymentSignature;
		} catch (Exception e) {
			 return false;
		}
	}

	@Override
	public List<PaymentData> getAllUsers() {
		List<PaymentData> allUsers = paymentRepository.findAll();
		return allUsers;
	}

}
