package com.braggbay103.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;



import com.braggbay103.dao.GenericDAO;
import com.braggbay103.service.GenericService;
import com.braggbay103.service.impl.GenericServiceImpl;
import com.braggbay103.dao.ItemDAO;
import com.braggbay103.domain.Item;
import com.braggbay103.dto.ItemDTO;
import com.braggbay103.dto.ItemSearchDTO;
import com.braggbay103.dto.ItemPageDTO;
import com.braggbay103.dto.ItemConvertCriteriaDTO;
import com.braggbay103.dto.common.RequestDTO;
import com.braggbay103.dto.common.ResultDTO;
import com.braggbay103.service.ItemService;
import com.braggbay103.util.ControllerUtils;





@Service
public class ItemServiceImpl extends GenericServiceImpl<Item, Integer> implements ItemService {

    private final static Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Autowired
	ItemDAO itemDao;

	


	@Override
	public GenericDAO<Item, Integer> getDAO() {
		return (GenericDAO<Item, Integer>) itemDao;
	}
	
	public List<Item> findAll () {
		List<Item> items = itemDao.findAll();
		
		return items;	
		
	}

	public ResultDTO addItem(ItemDTO itemDTO, RequestDTO requestDTO) {

		Item item = new Item();

		item.setItemId(itemDTO.getItemId());


		item.setTitle(itemDTO.getTitle());


		item.setDescription(itemDTO.getDescription());


		item.setStartingPrice(itemDTO.getStartingPrice());


		item.setCurrentPrice(itemDTO.getCurrentPrice());


		item.setEndTime(itemDTO.getEndTime());


		LocalDate localDate = LocalDate.now();
		ZoneId defaultZoneId = ZoneId.systemDefault();
		Date date = Date.from(localDate.atStartOfDay(defaultZoneId).toInstant());

		item = itemDao.save(item);
		
		ResultDTO result = new ResultDTO();
		return result;
	}
	
	public Page<Item> getAllItems(Pageable pageable) {
		return itemDao.findAll(pageable);
	}

	public Page<Item> getAllItems(Specification<Item> spec, Pageable pageable) {
		return itemDao.findAll(spec, pageable);
	}

	public ResponseEntity<ItemPageDTO> getItems(ItemSearchDTO itemSearchDTO) {
	
			Integer itemId = itemSearchDTO.getItemId(); 
 			String title = itemSearchDTO.getTitle(); 
 			String description = itemSearchDTO.getDescription(); 
     			String sortBy = itemSearchDTO.getSortBy();
			String sortOrder = itemSearchDTO.getSortOrder();
			String searchQuery = itemSearchDTO.getSearchQuery();
			Integer page = itemSearchDTO.getPage();
			Integer size = itemSearchDTO.getSize();

	        Specification<Item> spec = Specification.where(null);

			spec = ControllerUtils.andIfNecessary(spec, itemId, "itemId"); 
			
			spec = ControllerUtils.andIfNecessary(spec, title, "title"); 
			
			spec = ControllerUtils.andIfNecessary(spec, description, "description"); 
			
			
			
 			

		if (searchQuery != null && !searchQuery.isEmpty()) {
			spec = spec.and((root, query, cb) -> cb.or(

             cb.like(cb.lower(root.get("title")), "%" + searchQuery.toLowerCase() + "%") 
             , cb.like(cb.lower(root.get("description")), "%" + searchQuery.toLowerCase() + "%") 
		));}
		
		Sort sort = Sort.unsorted();
		if (sortBy != null && !sortBy.isEmpty() && sortOrder != null && !sortOrder.isEmpty()) {
			if (sortOrder.equalsIgnoreCase("asc")) {
				sort = Sort.by(sortBy).ascending();
			} else if (sortOrder.equalsIgnoreCase("desc")) {
				sort = Sort.by(sortBy).descending();
			}
		}
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Item> items = this.getAllItems(spec, pageable);
		
		//System.out.println(String.valueOf(items.getTotalElements()) + " total ${classNamelPlural}, viewing page X of " + String.valueOf(items.getTotalPages()));
		
		List<Item> itemsList = items.getContent();
		
		ItemConvertCriteriaDTO convertCriteria = new ItemConvertCriteriaDTO();
		List<ItemDTO> itemDTOs = this.convertItemsToItemDTOs(itemsList,convertCriteria);
		
		ItemPageDTO itemPageDTO = new ItemPageDTO();
		itemPageDTO.setItems(itemDTOs);
		itemPageDTO.setTotalElements(items.getTotalElements());
		return ResponseEntity.ok(itemPageDTO);
	}

	public List<ItemDTO> convertItemsToItemDTOs(List<Item> items, ItemConvertCriteriaDTO convertCriteria) {
		
		List<ItemDTO> itemDTOs = new ArrayList<ItemDTO>();
		
		for (Item item : items) {
			itemDTOs.add(convertItemToItemDTO(item,convertCriteria));
		}
		
		return itemDTOs;

	}
	
	public ItemDTO convertItemToItemDTO(Item item, ItemConvertCriteriaDTO convertCriteria) {
		
		ItemDTO itemDTO = new ItemDTO();
		
		itemDTO.setItemId(item.getItemId());

	
		itemDTO.setTitle(item.getTitle());

	
		itemDTO.setDescription(item.getDescription());

	
		itemDTO.setStartingPrice(item.getStartingPrice());

	
		itemDTO.setCurrentPrice(item.getCurrentPrice());

	
		itemDTO.setEndTime(item.getEndTime());

	

		
		return itemDTO;
	}

	public ResultDTO updateItem(ItemDTO itemDTO, RequestDTO requestDTO) {
		
		Item item = itemDao.getById(itemDTO.getItemId());

		item.setItemId(ControllerUtils.setValue(item.getItemId(), itemDTO.getItemId()));

		item.setTitle(ControllerUtils.setValue(item.getTitle(), itemDTO.getTitle()));

		item.setDescription(ControllerUtils.setValue(item.getDescription(), itemDTO.getDescription()));

		item.setStartingPrice(ControllerUtils.setValue(item.getStartingPrice(), itemDTO.getStartingPrice()));

		item.setCurrentPrice(ControllerUtils.setValue(item.getCurrentPrice(), itemDTO.getCurrentPrice()));

		item.setEndTime(ControllerUtils.setValue(item.getEndTime(), itemDTO.getEndTime()));



        item = itemDao.save(item);
		
		ResultDTO result = new ResultDTO();
		return result;
	}

	public ItemDTO getItemDTOById(Integer itemId) {
	
		Item item = itemDao.getById(itemId);
			
		
		ItemConvertCriteriaDTO convertCriteria = new ItemConvertCriteriaDTO();
		return(this.convertItemToItemDTO(item,convertCriteria));
	}







}
