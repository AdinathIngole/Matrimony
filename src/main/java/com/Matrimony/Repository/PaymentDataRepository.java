package com.Matrimony.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Matrimony.Entities.PaymentData;

@Repository
public interface PaymentDataRepository extends JpaRepository<PaymentData, Long>{
 
	public PaymentData findByRzpOrderId(String rzpOrderId);
}
