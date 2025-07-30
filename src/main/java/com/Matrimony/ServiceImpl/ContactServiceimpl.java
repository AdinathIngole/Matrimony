package com.Matrimony.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Matrimony.Entities.ContactUs;
import com.Matrimony.Repository.ContactRepository;
import com.Matrimony.Service.ContactService;

@Service
public class ContactServiceimpl implements ContactService{
	
	@Autowired
	private ContactRepository contactRepository;

	@Override
	public ContactUs saveContactList(ContactUs contactList) {
		
		return contactRepository.save(contactList);
	}

}
