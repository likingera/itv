package com.itv.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ItemDetails {
	
	@Id
	private int id;
	private String itemName;
	private int unitPrice;
	public int getId() {
		return id;
	}
	public void setId(int id) {
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
