package itv.itv;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.itv.dto.ItemDetailDto;
import com.itv.model.ItemDetail;
import com.itv.service.DbService;
import com.itv.serviceImpl.DbServiceImpl;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=DbServiceImpl.class)
@DataJpaTest
@EnableAutoConfiguration
@EntityScan("com.itv.model")
public class DbServiceTest {
	
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private DbService dbService = new DbServiceImpl();

	

	@Before
	public void setUp() throws Exception {
		
		MockitoAnnotations.initMocks(this);
		
		ItemDetail itemA = new ItemDetail();
		itemA.setId(1l);
		itemA.setItemName("A");
		itemA.setUnitPrice(50);
		
		entityManager.persistAndFlush(itemA);
		
		ItemDetail itemB = new ItemDetail();
		itemB.setId(2l);
		itemB.setItemName("B");
		itemB.setUnitPrice(30);
		
		entityManager.persistAndFlush(itemB);
	}

	
	
	

	@Test
	public void testgetAllItems() {
		
		List<String>items = dbService.getAllItems();
		assertTrue(items.size()>0);
		assertThat(items.size(), is(2));
	}
	
	
	@Test
	public void testgetItemPrice() {

		Set<String> items= new HashSet<String>();
		items.add("A");
		items.add("B");
		items.add("C");
		
		List<ItemDetailDto> itemDetails = dbService.getItemPrice(items);
		assertTrue(itemDetails.size()>0);
		assertThat(itemDetails.size(), is(2));
	}

}
