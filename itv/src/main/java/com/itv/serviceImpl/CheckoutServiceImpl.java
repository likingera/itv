package com.itv.serviceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itv.dto.ItemDetailDto;
import com.itv.exception.InvalidOfferException;
import com.itv.exception.UnknownItemException;
import com.itv.model.Checkout;
import com.itv.model.Offer;
import com.itv.model.Response;
import com.itv.service.CheckoutService;
import com.itv.service.DbService;


/**
 * @author Likin Gera
 *
 */
@Service
public class CheckoutServiceImpl implements CheckoutService {
	
	@Autowired
	DbService dbservice;
	
	
	
	public int calculateTotalBill(Checkout checkout) {
		
		 int totalBill = 0;
		
		Map<String,Integer> itemsPurchased = new HashMap<String,Integer>();
		
		checkout.getItemsPurchased().getItems().forEach(item->{
			if(itemsPurchased.containsKey(item)) {
				itemsPurchased.put(item, itemsPurchased.get(item)+1);
			} else {
				itemsPurchased.put(item, 1);
			}
		});
		

		List<ItemDetailDto> itemDetails = dbservice.getItemPrice(itemsPurchased.keySet());
		
		//itemsPurchased.forEach((item,count)->{
		
		for (Map.Entry<String, Integer> entry : itemsPurchased.entrySet()) {
			
			String item = entry.getKey();
			
			int count = entry.getValue();
		
			//itemDetails.forEach(itemDetailDto->{
			
			for(ItemDetailDto itemDetailDto : itemDetails) {
				
				if(itemDetailDto.getItemName().equals(item)) {
					
					Offer itemOffer = null;
					
					if(checkout.getSpecialPricing()!= null 
							&& checkout.getSpecialPricing().getOffers() != null  
							&& checkout.getSpecialPricing().getOffers().size()>0) {
					

						
						itemOffer = checkout.getSpecialPricing().getOffers().stream().
								filter(offer->offer.getItemName().equals(item)).
								findFirst().
								orElse(null);
						
					}
					
					if(itemOffer != null && count>=itemOffer.getUnits()) {
						totalBill = totalBill + (count%itemOffer.getUnits())*itemDetailDto.getUnitPrice() + (count/itemOffer.getUnits())*itemOffer.getPrice();
					} else {
						totalBill = totalBill + count*itemDetailDto.getUnitPrice();
					}
					
					break;
					
				}
				
			}
			//});
		}
			
		//});

		return totalBill;
	}
	
	public boolean validateInputData(Checkout checkout) throws InvalidOfferException,UnknownItemException {
		
		boolean result = true;
		List<String> items = dbservice.getAllItems();
		List<String> itemsPurchased = checkout.getItemsPurchased().getItems();
		
		for(String itemPurchased : itemsPurchased) {
			if(!items.contains(itemPurchased)) {
				//result = false;
				//break;
				throw new UnknownItemException();
			}
		}
		
		if(result) {
			if(checkout.getSpecialPricing() != null 
					&& checkout.getSpecialPricing().getOffers() != null 
					&& checkout.getSpecialPricing().getOffers().size()>0) {
				
				List<Offer> offers = checkout.getSpecialPricing().getOffers();
				
				for(Offer offer :offers) {
					if(offer.getPrice()<=0) {
						//result = false;
						//break;
						throw new InvalidOfferException();
					}	
				}
				
			}
		}
		return result;
		
	}
	
	public Response generateResponse(boolean validationResult, int totalBill) {

		Response response = new Response();
		String status= validationResult ? "SUCCESS":"FAIL";
		response.setResult(status);
		response.setTotalBill(totalBill);
		
		return response;
	}

}
