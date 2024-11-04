package com.braggbay103.dao;

import java.util.List;

import com.braggbay103.dao.GenericDAO;
import com.braggbay103.domain.Order;





public interface OrderDAO extends GenericDAO<Order, Integer> {
  
	List<Order> findAll();
	






}


