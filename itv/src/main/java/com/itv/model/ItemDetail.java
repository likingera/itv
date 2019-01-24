package com.itv.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table (name="ITEMDETAILS")
public class ItemDetail {
	
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="ITEMNAME")
	private String itemName;
	
	@Column(name="UNITPRICE")
	private int unitPrice;
	
	public ItemDetail() {
		//super();
	}
	
	public ItemDetail(String itemName, int unitPrice) {
		super();
		this.itemName = itemName;
		this.unitPrice = unitPrice;
	}
	public ItemDetail(Long id, String itemName, int unitPrice) {
		super();
		this.id = id;
		this.itemName = itemName;
		this.unitPrice = unitPrice;
	}
	
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
