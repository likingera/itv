package itv.itv;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.itv.dto.ItemDetailDto;
import com.itv.exception.InvalidOfferException;
import com.itv.exception.UnknownItemException;
import com.itv.model.Checkout;
import com.itv.model.ItemsPurchased;
import com.itv.model.Offer;
import com.itv.model.Response;
import com.itv.model.SpecialPricing;
import com.itv.service.CheckoutService;
import com.itv.service.DbService;
import com.itv.serviceImpl.CheckoutServiceImpl;

/**
 * @author Likin Gera
 *
 */
public class CheckoutServiceTest {
	
	@InjectMocks
	CheckoutService checkoutService = new CheckoutServiceImpl();
	
	@Mock
	DbService dbService;
	


	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		List<String> items = Stream.of("A", "B", "C", "D").collect(Collectors.toList());
		
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
		List<String> items = Stream.of("A", "B", "C", "A", "A", "C","D").collect(Collectors.toList());
		
		itemsPurchased.setItems(items);
		checkout.setItemsPurchased(itemsPurchased);
		SpecialPricing specialPricing = new SpecialPricing();
		List<Offer> offers = new ArrayList<Offer>();
		Offer offer = new Offer("A",3,130);
		
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
		List<String> items = Stream.of("A", "B", "C", "A", "A", "C","D").collect(Collectors.toList());
		itemsPurchased.setItems(items);
		checkout.setItemsPurchased(itemsPurchased);
		try {
			boolean result = checkoutService.validateInputData(checkout);
			assertTrue(result);
		}catch(Exception e) {}
		
	}
	
	@Test(expected = UnknownItemException.class )
	public void testValidateInputData_InvalidDataUnknownItem() throws InvalidOfferException,UnknownItemException {
		
		Checkout checkout = new Checkout();
		ItemsPurchased itemsPurchased = new ItemsPurchased();
		List<String> items = new ArrayList<String>();
		items.add("a");
		itemsPurchased.setItems(items);
		checkout.setItemsPurchased(itemsPurchased);
		
		checkoutService.validateInputData(checkout);
		//assertFalse(result);
	}
	
	
	@Test(expected = InvalidOfferException.class )
	public void testValidateInputData_InvalidDataZeroSpecialOffer() throws InvalidOfferException,UnknownItemException {
		
		Checkout checkoutinvalid = new Checkout();
		ItemsPurchased itemsPurchased = new ItemsPurchased();
		List<String> items = new ArrayList<String>();
		items.add("A");
		itemsPurchased.setItems(items);
		checkoutinvalid.setItemsPurchased(itemsPurchased);
		SpecialPricing invalidspecialPricing = new SpecialPricing();
		List<Offer> invalidoffers = new ArrayList<Offer>();
		Offer invalidoffer = new Offer("A",1,0);
		invalidoffers.add(invalidoffer);
		invalidspecialPricing.setOffers(invalidoffers);
		checkoutinvalid.setSpecialPricing(invalidspecialPricing);
		
		checkoutService.validateInputData(checkoutinvalid);
		//assertFalse(result);
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
