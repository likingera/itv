package com.itv.model;




/**
 * @author Likin Gera
 *
 */
public class Checkout {
	
	
	
	private ItemsPurchased itemsPurchased;
	
	private SpecialPricing specialPricing;
	

	public SpecialPricing getSpecialPricing() {
		return specialPricing;
	}

	public void setSpecialPricing(SpecialPricing specialPricing) {
		this.specialPricing = specialPricing;
	}

	public ItemsPurchased getItemsPurchased() {
		return itemsPurchased;
	}

	public void setItemsPurchased(ItemsPurchased itemsPurchased) {
		this.itemsPurchased = itemsPurchased;
	}

}
