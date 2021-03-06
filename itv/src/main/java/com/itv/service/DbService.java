package com.itv.service;

import java.util.List;
import java.util.Set;
import com.itv.dto.ItemDetailDto;

/**
 * @author Likin Gera
 *
 */
public interface DbService {
	
	List<String> getAllItems();
	
	List<ItemDetailDto> getItemPrice(Set<String> items);

	void add(ItemDetailDto itemDetail);
	
	void deleteAll();
}
