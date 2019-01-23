package com.itv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itv.model.Checkout;
import com.itv.model.Response;
import com.itv.service.CheckoutService;

@RestController
@SpringBootApplication
public class CheckoutController {
	
	@Autowired
	CheckoutService checkoutservice;
	

	@RequestMapping(value ="/checkout",method=RequestMethod.GET)
	public Response getResponse(Checkout checkout) {
		
		//validate input data
		boolean validationResult = checkoutservice.validateInputData(checkout);
		
		
		//calculate total bill if validation succeeds
		int totalBill = 0;
		if(validationResult)
			totalBill = checkoutservice.calculateTotalBill(checkout);
		
		
		//generate response
		Response response = checkoutservice.generateResponse(validationResult, totalBill);
			
		return response;
	}
	
	public static void main(String args[]){
	    SpringApplication.run(CheckoutController.class, args);
	}
	
	
}
