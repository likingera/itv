package itv.itv;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.IfProfileValue;
import org.springframework.test.context.junit4.SpringRunner;

import com.itv.controller.CheckoutController;
import com.itv.dto.ItemDetailDto;
import com.itv.model.Checkout;
import com.itv.model.ItemsPurchased;
import com.itv.model.Offer;
import com.itv.model.Response;
import com.itv.model.SpecialPricing;
import com.itv.service.DbService;



/**
 * @author Likin Gera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,classes = CheckoutController.class)
@ComponentScan("com.itv.*")
@IfProfileValue(name="integerationtests", value="integration")
public class IntegerationTest {
	
	Logger LOG = LoggerFactory.getLogger(IntegerationTest.class);
	
	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	DbService dbservice;
	
	@LocalServerPort
	private int port;
	
	ItemDetailDto itemDetailA = new ItemDetailDto();
	ItemDetailDto itemDetailB = new ItemDetailDto();
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		
		itemDetailA.setId(1l);
		itemDetailA.setItemName("A");
		itemDetailA.setUnitPrice(50);
		
		itemDetailB.setId(2l);
		itemDetailB.setItemName("B");
		itemDetailB.setUnitPrice(30);
		dbservice.add(itemDetailA);
		dbservice.add(itemDetailB);
		
	}

	@After
	public void tearDown() throws Exception {
		
		LOG.info("teardowncalled");
		
		dbservice.deleteAll();

	}

	@Test
	public void testService_200status() { 
		
		Checkout checkout = new Checkout();
		
		ItemsPurchased itemsPurchased = new  ItemsPurchased();
		
		List<String> items = Stream.of("A").collect(Collectors.toList());
		
		itemsPurchased.setItems(items);
		
		checkout.setItemsPurchased(itemsPurchased);
		
		ResponseEntity<Response>  response = restTemplate.postForEntity("http://localhost:"+this.port+"/checkout", checkout ,Response.class);
		
		LOG.info("Response code is "+response.getStatusCodeValue());
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
	}
	
	
	@Test
	public void testService_outputNotNull() { 
		
		Checkout checkout = new Checkout();
		
		ItemsPurchased itemsPurchased = new  ItemsPurchased();
		
		List<String> items = Stream.of("A").collect(Collectors.toList());
		
		itemsPurchased.setItems(items);
		
		checkout.setItemsPurchased(itemsPurchased);
		
		ResponseEntity<Response>  response = restTemplate.postForEntity("http://localhost:"+this.port+"/checkout", checkout ,Response.class);
		
		LOG.info("Response body is "+response.getBody().getResult());
		
		assertNotNull(response.getBody().getResult());
		
	}
	
	
	
	@Test
	public void testService_checkBillWithoutSpecialOffer() { 
		
		Checkout checkout = new Checkout();
		
		ItemsPurchased itemsPurchased = new  ItemsPurchased();
		
		List<String> items = Stream.of("A", "B", "A", "A", "B").collect(Collectors.toList());
		
		itemsPurchased.setItems(items);
		
		checkout.setItemsPurchased(itemsPurchased);
		
		ResponseEntity<Response>  response = restTemplate.postForEntity("http://localhost:"+this.port+"/checkout", checkout ,Response.class);
		
		LOG.info("totalbill is "+response.getBody().getTotalBill());
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		assertNotNull(response.getBody().getResult());
		
		assertThat(response.getBody().getResult(), equalTo("SUCCESS"));
		
		assertThat(response.getBody().getTotalBill(), equalTo(210));
		
		
	}
	
	
	@Test
	public void testService_checkBillWithSpecialOffer() { 
		
		Checkout checkout = new Checkout();
		
		ItemsPurchased itemsPurchased = new  ItemsPurchased();
		
		List<String> items = Stream.of("A", "B", "A", "A", "B", "B", "A").collect(Collectors.toList());
		
		itemsPurchased.setItems(items);
		
		checkout.setItemsPurchased(itemsPurchased);
		
		SpecialPricing specialPricing = new SpecialPricing();
		
		Offer offerA = new Offer("A",3,130);
		
		Offer offerB = new Offer("B",2,30);
		
		List<Offer> offers = Stream.of(offerA, offerB).collect(Collectors.toList());
		
		specialPricing.setOffers(offers);
		
		checkout.setSpecialPricing(specialPricing);
		
		ResponseEntity<Response>  response = restTemplate.postForEntity("http://localhost:"+this.port+"/checkout", checkout ,Response.class);
		
		LOG.info("totalbill is "+response.getBody().getTotalBill());
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		assertNotNull(response.getBody().getResult());
		
		assertThat(response.getBody().getResult(), equalTo("SUCCESS"));
		
		assertThat(response.getBody().getTotalBill(), equalTo(240));
		
		
	}
	
	
	@Test
	public void testService_UnknownItem() { 
		
		Checkout checkout = new Checkout();
		
		ItemsPurchased itemsPurchased = new  ItemsPurchased();
		
		List<String> items = Stream.of("A", "B", "C").collect(Collectors.toList());
		
		itemsPurchased.setItems(items);
		
		checkout.setItemsPurchased(itemsPurchased);
		
		SpecialPricing specialPricing = new SpecialPricing();
		
		Offer offerA = new Offer("A",3,130);
		
		Offer offerB = new Offer("B",2,30);
		
		List<Offer> offers = Stream.of(offerA, offerB).collect(Collectors.toList());
		
		specialPricing.setOffers(offers);
		
		checkout.setSpecialPricing(specialPricing);
		
		ResponseEntity<Response>  response = restTemplate.postForEntity("http://localhost:"+this.port+"/checkout", checkout ,Response.class);
		
		LOG.info("totalbill is "+response.getBody().getTotalBill());
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		assertNotNull(response.getBody().getResult());
		
		assertThat(response.getBody().getResult(), equalTo("UNKNOWN_ITEM"));
		
		assertThat(response.getBody().getTotalBill(), equalTo(0));
		
		
	}
	
	
	@Test
	public void testService_ZeroOffer() { 
		
		Checkout checkout = new Checkout();
		
		ItemsPurchased itemsPurchased = new  ItemsPurchased();
		
		List<String> items = Stream.of("A", "B").collect(Collectors.toList());
		
		itemsPurchased.setItems(items);
		
		checkout.setItemsPurchased(itemsPurchased);
		
		SpecialPricing specialPricing = new SpecialPricing();
		
		Offer offerA = new Offer("A",1,0);
		
		Offer offerB = new Offer("B",2,30);
		
		List<Offer> offers = Stream.of(offerA, offerB).collect(Collectors.toList());
		
		specialPricing.setOffers(offers);
		
		checkout.setSpecialPricing(specialPricing);
		
		ResponseEntity<Response>  response = restTemplate.postForEntity("http://localhost:"+this.port+"/checkout", checkout ,Response.class);
		
		LOG.info("totalbill is "+response.getBody().getTotalBill());
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		assertNotNull(response.getBody().getResult());
		
		assertThat(response.getBody().getResult(), equalTo("INVALID_OFFER"));
		
		assertThat(response.getBody().getTotalBill(), equalTo(0));
		
		
	}

}
