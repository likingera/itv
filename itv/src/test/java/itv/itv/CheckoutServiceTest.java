package itv.itv;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import org.hamcrest.core.Is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.itv.dto.ItemDetailDto;
import com.itv.model.Checkout;
import com.itv.model.ItemsPurchased;
import com.itv.model.Offer;
import com.itv.model.Response;
import com.itv.model.SpecialPricing;
import com.itv.service.CheckoutService;
import com.itv.service.DbService;
import com.itv.serviceImpl.CheckoutServiceImpl;

public class CheckoutServiceTest {
	
	@InjectMocks
	CheckoutService checkoutService = new CheckoutServiceImpl();
	
	@Mock
	DbService dbService;
	
	

//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//	}

	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		List<String> items = new ArrayList<String>();
		items.add("A");
		items.add("B");
		items.add("C");
		items.add("D");
		Mockito.when(dbService.getAllItems()).thenReturn(items);
		
		List<ItemDetailDto> ItemDetailsDto = new ArrayList<ItemDetailDto>();
		ItemDetailDto itemDetailDto1 = new ItemDetailDto();
		itemDetailDto1.setItemName("A");
		itemDetailDto1.setUnitPrice(45);
		ItemDetailsDto.add(itemDetailDto1);
		
		ItemDetailDto itemDetailDto2 = new ItemDetailDto();
		itemDetailDto2.setItemName("B");
		itemDetailDto2.setUnitPrice(30);
		ItemDetailsDto.add(itemDetailDto2);
		
		ItemDetailDto itemDetailDto3 = new ItemDetailDto();
		itemDetailDto3.setItemName("C");
		itemDetailDto3.setUnitPrice(20);
		ItemDetailsDto.add(itemDetailDto3);
		
		ItemDetailDto itemDetailDto4 = new ItemDetailDto();
		itemDetailDto4.setItemName("D");
		itemDetailDto4.setUnitPrice(15);
		ItemDetailsDto.add(itemDetailDto4);
		
		Mockito.when(dbService.getItemPrice(Mockito.anySet())).thenReturn(ItemDetailsDto);
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCalculateTotalBill() {
		Checkout checkout = new Checkout();
		ItemsPurchased itemsPurchased = new ItemsPurchased();
		List<String> items = new ArrayList<String>();
		items.add("A");
		items.add("B");
		items.add("C");
		items.add("A");
		items.add("A");
		items.add("C");
		items.add("D");
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

		int totalBill = checkoutService.calculateTotalBill(checkout);
		
		assertThat(totalBill, is(215));
	}

	@Test
	public void testValidateInputData_ValidData() {
		
		Checkout checkout = new Checkout();
		ItemsPurchased itemsPurchased = new ItemsPurchased();
		List<String> items = new ArrayList<String>();
		items.add("A");
		items.add("B");
		items.add("C");
		items.add("A");
		items.add("A");
		items.add("C");
		items.add("D");
		itemsPurchased.setItems(items);
		checkout.setItemsPurchased(itemsPurchased);
		
		boolean result = checkoutService.validateInputData(checkout);
		assertTrue(result);
	}
	
	@Test
	public void testValidateInputData_InvalidDataUnknownItem() {
		
		Checkout checkout = new Checkout();
		ItemsPurchased itemsPurchased = new ItemsPurchased();
		List<String> items = new ArrayList<String>();
		items.add("a");
		itemsPurchased.setItems(items);
		checkout.setItemsPurchased(itemsPurchased);
		
		boolean result = checkoutService.validateInputData(checkout);
		assertFalse(result);
	}
	
	
	@Test
	public void testValidateInputData_InvalidDataZeroSpecialOffer() {
		
		Checkout checkoutinvalid = new Checkout();
		ItemsPurchased itemsPurchased = new ItemsPurchased();
		List<String> items = new ArrayList<String>();
		items.add("A");
		itemsPurchased.setItems(items);
		checkoutinvalid.setItemsPurchased(itemsPurchased);
		SpecialPricing invalidspecialPricing = new SpecialPricing();
		List<Offer> invalidoffers = new ArrayList<Offer>();
		Offer invalidoffer = new Offer();
		invalidoffer.setItemName("A");
		invalidoffer.setPrice(0);
		invalidoffer.setUnits(1);
		invalidoffers.add(invalidoffer);
		invalidspecialPricing.setOffers(invalidoffers);
		checkoutinvalid.setSpecialPricing(invalidspecialPricing);
		
		boolean result = checkoutService.validateInputData(checkoutinvalid);
		assertFalse(result);
	}

	@Test
	public void testGenerateResponse_NotNull() {
		
		Response response = checkoutService.generateResponse(false, 0);
		
		assertThat(response, is(notNullValue()));
	}
	
	
	@Test
	public void testGenerateResponse_FailResponse() {
		
		Response response = checkoutService.generateResponse(false, 0);
		
		assertThat(response.getResult(),is("FAIL"));
		
		assertEquals(0,response.getTotalBill());
	}
	
	
	@Test
	public void testGenerateResponse_SuccessResponse() {
		
		Response response = checkoutService.generateResponse(true, 10);
		
		assertThat(response.getResult(),is("SUCCESS"));
		
		assertEquals(10,response.getTotalBill());
		
		assertTrue(response.getTotalBill()>0);
	}

}