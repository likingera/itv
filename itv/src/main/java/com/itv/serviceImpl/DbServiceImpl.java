package com.itv.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.itv.dto.ItemDetailDto;
import com.itv.model.ItemDetail;
import com.itv.service.DbService;

@Repository
public class DbServiceImpl implements DbService{
	
	
	@PersistenceContext
	private EntityManager entityManager;
	
	public List<String> getAllItems() {
		
		List<String> itemNames = new ArrayList<String>();
		
		String query = " From ItemDetail ";
		
		List<ItemDetail> itemDetails = entityManager.createQuery(query).getResultList();
		
		itemDetails.forEach(item -> {  
			
			itemNames.add(item.getItemName());
			
		});
		
		return itemNames;
	}
	
	public List<ItemDetailDto> getItemPrice(Set<String> items) {
		
		List<ItemDetailDto> itemDetailsDto = new ArrayList<ItemDetailDto>();
		
		String query = " From ItemDetail item where item.itemName in(:items) ";
		
		List<ItemDetail> itemDetails = entityManager.createQuery(query).setParameter("items", items).getResultList();
		
		itemDetails.forEach(item -> {  
			
			ItemDetailDto itemDetailDto= new ItemDetailDto();
			itemDetailDto.setItemName(item.getItemName());
			itemDetailDto.setUnitPrice(item.getUnitPrice());
			itemDetailsDto.add(itemDetailDto);
			
		});
		
		return itemDetailsDto;
	}

}
