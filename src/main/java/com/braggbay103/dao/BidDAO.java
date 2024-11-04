package com.braggbay103.dao;

import java.util.List;

import com.braggbay103.dao.GenericDAO;
import com.braggbay103.domain.Bid;





public interface BidDAO extends GenericDAO<Bid, Integer> {
  
	List<Bid> findAll();
	






}


