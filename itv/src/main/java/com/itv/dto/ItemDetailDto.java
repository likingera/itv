package com.itv.dto;

/**
 * @author Likin Gera
 *
 */
public class ItemDetailDto {
	
	private Long id;
	private String itemName;
	private int unitPrice;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}

}
