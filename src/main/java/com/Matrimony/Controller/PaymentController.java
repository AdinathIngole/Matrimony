package com.Matrimony.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Matrimony.Entities.PaymentData;
import com.Matrimony.Service.PaymentDataService;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	@Autowired
	private PaymentDataService paymentService;
	
	

	@PostMapping("/createOrder")
	public ResponseEntity<PaymentData> createOrder(@RequestBody PaymentData paymentOrder) throws Exception{
		PaymentData createOrder = paymentService.createOrder(paymentOrder);
		return new ResponseEntity<PaymentData>(createOrder, HttpStatus.CREATED);		
	}
	
	@PostMapping("/verifyPayment")
    public ResponseEntity<String> verifyPayment(@RequestBody PaymentData request) {
        boolean isVerified = paymentService.verifyPayment(request.getRzpPaymentId(), request.getRzpOrderId(), request.getRzpSignature());
        if (isVerified) {
            return ResponseEntity.ok("Payment verified successfully");
        } else {
            return ResponseEntity.status(400).body("Payment verification failed");
        }
    }
	
	@GetMapping("/getAllUsers")
	public List<PaymentData> getAllDoners(){
		return paymentService.getAllUsers();
	}
	
}
