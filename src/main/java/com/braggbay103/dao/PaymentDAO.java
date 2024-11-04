package com.braggbay103.dao;

import java.util.List;

import com.braggbay103.dao.GenericDAO;
import com.braggbay103.domain.Payment;





public interface PaymentDAO extends GenericDAO<Payment, Integer> {
  
	List<Payment> findAll();
	






}


