package itv.itv;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.itv.controller.CheckoutController;
import com.itv.model.Checkout;
import com.itv.model.ItemsPurchased;
import com.itv.model.Offer;
import com.itv.model.Response;
import com.itv.model.SpecialPricing;
import com.itv.service.CheckoutService;

public class CheckoutControllerTest {

	@InjectMocks
	CheckoutController controller;
	
	@Mock
	CheckoutService checkoutService;
	
	Checkout checkout;
	
	Checkout checkoutinvalid;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		
	}
	
	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		Response failResponse  = new Response();
		failResponse.setResult("FAIL");
		failResponse.setTotalBill(0);
		Mockito.when(checkoutService.generateResponse(false, 0)).thenReturn(failResponse);
		
		Response successResponse  = new Response();
		successResponse.setResult("SUCCESS");
		successResponse.setTotalBill(50);
		Mockito.when(checkoutService.generateResponse(true, 50)).thenReturn(successResponse);
		
		checkout = new Checkout();
		
		ItemsPurchased itemsPurchased = new ItemsPurchased();
		List<String> items = new ArrayList<String>();
		items.add("A");
		itemsPurchased.setItems(items);
		
		checkout.setItemsPurchased(itemsPurchased);
		
		SpecialPricing specialPricing = new SpecialPricing();
		List<Offer> offers = new ArrayList<Offer>();
		Offer offer = new Offer();
		offer.setItemName("A");
		offer.setPrice(130);
		offer.setUnits(3);
		offers.add(offer);
		specialPricing.setOffers(offers);
		checkout.setSpecialPricing(specialPricing);
		
		Mockito.when(checkoutService.validateInputData(checkout)).thenReturn(true);
		
		Mockito.when(checkoutService.calculateTotalBill(checkout)).thenReturn(50);
		
		
		
		checkoutinvalid = new Checkout();
		
		SpecialPricing invalidspecialPricing = new SpecialPricing();
		List<Offer> invalidoffers = new ArrayList<Offer>();
		Offer invalidoffer = new Offer();
		invalidoffer.setItemName("A");
		invalidoffer.setPrice(0);
		invalidoffer.setUnits(1);
		invalidoffers.add(invalidoffer);
		invalidspecialPricing.setOffers(invalidoffers);
		checkoutinvalid.setSpecialPricing(invalidspecialPricing);
		
		Mockito.when(checkoutService.validateInputData(checkoutinvalid)).thenReturn(false);
		
		Mockito.when(checkoutService.calculateTotalBill(checkoutinvalid)).thenReturn(0);
	}
	
	
	@Test
	public void testResponseNotNull() {
		
		 
		Response response = controller.getResponse(checkout);
		
		assertNotNull(response);
			
	}
	
	
	@Test
	public void testResponseResult_Pass() {
		
		Response response = controller.getResponse(checkout);
		
		assertThat(response.getTotalBill(), is(50));
		
		assertThat(response.getResult(), is("SUCCESS"));
		
			
	}
	
	
	@Test
	public void testResponseResult_Fail() {
		
		
		Response response = controller.getResponse(checkoutinvalid);
		
		assertThat(response.getResult(), is("FAIL"));
		
		assertThat(response.getTotalBill(), is(0));
		
			
	}
	
	
	@Test
	public void testInput_Pass() {
		
		assertThat(checkout.getItemsPurchased(), is(notNullValue()));
		
		assertTrue(checkout.getItemsPurchased().getItems().size()>0);
		
			
	}
	
	
	
	@Test
	public void testInput_specialPricingFail() {
		
		assertTrue(checkout.getSpecialPricing().getOffers().get(0).getPrice()>0);
		
			
	}
	
	
	
}
