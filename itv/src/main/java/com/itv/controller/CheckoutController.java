package com.itv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itv.exception.InvalidOfferException;
import com.itv.exception.UnknownItemException;
import com.itv.model.Checkout;
import com.itv.model.Response;
import com.itv.service.CheckoutService;

/**
 * @author Likin Gera
 *
 */
@RestController
@SpringBootApplication
@ComponentScan("com.itv.*")
public class CheckoutController {
	
	@Autowired
	CheckoutService checkoutservice;
	

	@RequestMapping(value ="/checkout",method=RequestMethod.POST,produces={MediaType.APPLICATION_JSON_VALUE},consumes={MediaType.APPLICATION_JSON_VALUE})
	public Response getResponse(@RequestBody Checkout checkout) throws InvalidOfferException,UnknownItemException {
		
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
	
	
	@ExceptionHandler(UnknownItemException.class)
	public Response unknowItemExceptionHandler() {
		
		Response response = new Response();
		response.setResult("UNKNOWN_ITEM");
		return response;
	}
	
	@ExceptionHandler(InvalidOfferException.class)
	public Response invalidOfferExceptionHandler() {
		
		Response response = new Response();
		response.setResult("INVALID_OFFER");
		return response;
	}
	
	
}
