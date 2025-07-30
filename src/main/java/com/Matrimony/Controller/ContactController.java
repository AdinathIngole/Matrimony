package com.Matrimony.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Matrimony.Entities.ContactUs;
import com.Matrimony.Service.ContactService;
import com.Matrimony.Service.EmailService;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@Autowired
	private EmailService emailService;
	
	@PostMapping("save-contact")
	public ContactUs saveContactList(@RequestBody ContactUs contactus) {
		
		emailService.sendEmail("adiingole1006@gmail.com", "New Enquiry Received ", "Dear Vivek Naik ,\n\n"
				+ "I hope this email finds you well. \n"
				+ "We have received a new enquiry with the following details: \n\n" + "Enquiry Details: \n\n" + "Name:"
				+ contactus.getName() + "\nEmail : " + contactus.getEmail() + "\nPhone Number: "
				+ contactus.getMobile() + "\nMessage : "
				+ contactus.getMessage()
				+ "\n\nPlease review the enquiry at your earliest convenience and take the necessary actions.\r\n"
				+ "If you need any further information, please let me know. \n\n" + "Thank you. \n "
				+ "\nBest regards, \n" + contactus.getName() + "\n" + contactus.getEmail() + "\n"
				+ contactus.getMobile());
		
		 emailService.sendEmail(contactus.getEmail(), "Thank You for Contacting Risto Ki Dor... : ", "Dear "
					+ contactus.getName()
					+ "\nThank you for choosing Risto Ki Dor . We have received your enquiry and appreciate you reaching out to us. \r\n\n"
					+ "Our team is currently reviewing your message, and we will get back to you as soon as possible. \r\n"
					+ "If your enquiry is urgent, please feel free to call us at 9834030441.\r\n" + "\r\n"
					+ "In the meantime, if you have any additional information or questions, do not hesitate to reply to this email."
					+ "\r\n" + "Thank you for your patience, and we look forward to assisting you. \r\n\n"
					+ "Best regards,\r\n" + "Vivek Naik\r\n" + "Founder\r\n" + "Risto Ki Dor\r\n"
					+ "9834030441\r\n" + "http://www.ristokidor.in");
		 
		return contactService.saveContactList(contactus);
	}

}
