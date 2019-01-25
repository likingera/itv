package com.itv.model;

/**
 * @author Likin Gera
 *
 */
public class Offer {

	private int units;
	private String itemName;
	private int price;
	
	public Offer() {
		super();
	}
	
	public Offer(String itemName,int units,int price) {
		super();
		this.units = units;
		this.itemName = itemName;
		this.price = price;
	}
	
	
	
	
	public int getUnits() {
		return units;
	}
	public void setUnits(int units) {
		this.units = units;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
}
