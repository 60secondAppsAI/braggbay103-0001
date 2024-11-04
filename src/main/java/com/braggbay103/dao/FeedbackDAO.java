package com.braggbay103.dao;

import java.util.List;

import com.braggbay103.dao.GenericDAO;
import com.braggbay103.domain.Feedback;





public interface FeedbackDAO extends GenericDAO<Feedback, Integer> {
  
	List<Feedback> findAll();
	






}


