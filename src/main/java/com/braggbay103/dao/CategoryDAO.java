package com.braggbay103.dao;

import java.util.List;

import com.braggbay103.dao.GenericDAO;
import com.braggbay103.domain.Category;





public interface CategoryDAO extends GenericDAO<Category, Integer> {
  
	List<Category> findAll();
	






}


