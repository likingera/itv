package com.itv.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itv.model.Response;

@RestController
@SpringBootApplication
public class CheckoutController {

	@RequestMapping(value ="/checkout",method=RequestMethod.GET)
	public Response getResponse() {
		return null;
	}
	
	public static void main(String args[]){
	    SpringApplication.run(CheckoutController.class, args);
	}
	
	
}
