package com.itv.service;

import com.itv.exception.UnknownItemException;
import com.itv.model.Checkout;
import com.itv.model.Response;
import com.itv.exception.InvalidOfferException;

/**
 * @author Likin Gera
 *
 */
public interface CheckoutService {
	
	public int calculateTotalBill(Checkout checkout);

	public boolean validateInputData(Checkout checkout) throws InvalidOfferException,UnknownItemException;
	
	public Response generateResponse(boolean result ,int totalBill);
	
	

}
