package com.itv.service;

import com.itv.model.Checkout;
import com.itv.model.Response;

/**
 * @author Likin Gera
 *
 */
public interface CheckoutService {
	
	public int calculateTotalBill(Checkout checkout);

	public boolean validateInputData(Checkout checkout);
	
	public Response generateResponse(boolean result ,int totalBill);
	
	

}
