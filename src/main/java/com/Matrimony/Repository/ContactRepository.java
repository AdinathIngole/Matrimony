package com.Matrimony.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Matrimony.Entities.ContactUs;

@Repository
public interface ContactRepository extends JpaRepository<ContactUs, Long>{
	
	

}
