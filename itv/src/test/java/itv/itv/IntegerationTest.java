package itv.itv;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.net.URI;

import static org.hamcrest.CoreMatchers.*;

import org.hamcrest.core.IsNull;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.itv.controller.CheckoutController;
import com.itv.model.Response;



@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT,classes = CheckoutController.class)
public class IntegerationTest {
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@LocalServerPort
	private int port;
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testService_200status() { 
		
		
		
		ResponseEntity<Response>  response = restTemplate.getForEntity("http://localhost:"+this.port+"/checkout", Response.class);
		
		System.out.println(response.getStatusCodeValue());
		
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
	}
	
	
	@Test
	public void testService_outputnotnull() { 
		
		
		
		ResponseEntity<Response>  response = restTemplate.getForEntity("http://localhost:"+this.port+"/checkout", Response.class);
		
		System.out.println(response.getBody());
		
		assertNotNull(response.getBody());
		
	}

}
