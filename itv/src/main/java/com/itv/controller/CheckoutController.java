package com.itv.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itv.model.Response;

@RestController

public class CheckoutController {

	@RequestMapping(value ="/checkout",method=RequestMethod.GET)
	public Response getResponse() {
		return null;
	}
	
	
}
