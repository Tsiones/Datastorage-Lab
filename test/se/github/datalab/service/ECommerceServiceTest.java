package se.github.datalab.service;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import se.github.datalab.model.Order;
import se.github.datalab.model.Product;
import se.github.datalab.model.User;
import se.github.datalab.repository.OrderRepository;
import se.github.datalab.repository.ProductRepository;
import se.github.datalab.repository.UserRepository;
import se.github.datalab.repository.jpastorage.JpaOrderRepository;
import se.github.datalab.repository.jpastorage.JpaProductRepository;
import se.github.datalab.repository.jpastorage.JpaUserRepository;
import se.github.datalab.statuses.ProductStatus;
import se.github.datalab.statuses.UserStatus;

public class ECommerceServiceTest
{
	private static EntityManagerFactory factory;
	private static ECommerceService eCom;
	private static UserRepository userRepo;
	private static ProductRepository prodRepo;
	private static OrderRepository orderRepo;

	private User validUser1 = new User("trapLord", "123456", "asapMOB@rock.y");

	private Product validProduct1 = new Product("EverGreen", "Fertilizer", 14.95);

	private Order validOrder1 = new Order();

	@BeforeClass
	public static void init()
	{
		factory = Persistence.createEntityManagerFactory("test");

		userRepo = new JpaUserRepository(factory);
		prodRepo = new JpaProductRepository(factory);
		orderRepo = new JpaOrderRepository(factory);

		eCom = new ECommerceService(userRepo, prodRepo, orderRepo);
	}

	@Test
	public void canRetrieveUserById()
	{
		User validUser2 = new User("rickyTang", "loves2spooge", "melenti@soittotale.fin");
		userRepo.update(validUser2);
		assertThat(eCom.user.getBy(validUser2.getId()), equalTo(validUser2));
	}

	@Test
	public void canRetrieveProductById()
	{
		Product validProduct2 = new Product("ViberMaster", "Muscular relaxation", 66.69);
		prodRepo.update(validProduct2);
		assertThat(eCom.product.getBy(validProduct2.getId()), equalTo(validProduct2));
	}

	@Test
	public void canRetrieveOrderById()
	{
		validOrder1.setBuyer(validUser1);
		validOrder1.addProduct(validProduct1);

		prodRepo.update(validProduct1);
		userRepo.update(validUser1);
		orderRepo.update(validOrder1);

		assertThat(eCom.order.getBy(validOrder1.getId()), equalTo(validOrder1));
	}

	@Test
	public void canAddUserAndRetrieveByUsername()
	{
		userRepo.update(validUser1);

		assertThat(eCom.user.getBy("trapLord").get(0), equalTo(validUser1));
	}

	@Test
	public void canAddProductAndRetrieveByName()
	{
		prodRepo.update(validProduct1);

		assertThat(eCom.product.getBy("Ever").get(0), equalTo(validProduct1));
	}

	@Test
	public void canAddOrderAndRetrieveOrderByUser()
	{
		prodRepo.update(validProduct1);
		validOrder1.addProduct(validProduct1);

		userRepo.update(validUser1);

		validOrder1.setBuyer(validUser1);
		orderRepo.update(validOrder1);

		assertThat(eCom.order.getBy(validUser1).get(0), equalTo(validOrder1));
	}

	@Test
	public void canRetrieveUserByEmail()
	{
		userRepo.update(validUser1);
		assertThat(eCom.user.getByEmail("asap").get(0), equalTo(validUser1));
	}

	@Test
	public void canRetrieveUserByStatus()
	{
		User validUser2 = new User("johnDoe", "654321", "johnny@doe.com");
		eCom.user.changeStatus(validUser2, UserStatus.GUEST);
		assertThat(eCom.user.getBy(UserStatus.GUEST).get(0), equalTo(validUser2));
	}

	@Test
	public void canRetrieveProductByStatus()
	{
		eCom.product.changeStatus(validProduct1, ProductStatus.OUT_OF_STOCK);
		assertThat(eCom.product.getBy(ProductStatus.OUT_OF_STOCK).get(0), equalTo(validProduct1));
	}

	@Test
	public void canRetrieveProductByCost()
	{
		validProduct1.setPrice(500);
		prodRepo.update(validProduct1);
		assertThat(eCom.product.getBy(500).get(0), equalTo(validProduct1));
	}

	@Test
	public void canRetrieveOrderByTotalCost()
	{
		Product validProduct2 = new Product("WD-40", "Water-Displacer batch no. 40", 4.95);
		Product validProduct3 = new Product("SilverSurfer", "Duct tape", 0.95);
		Order validOrder2 = new Order(validProduct2, validProduct3);
		User validUser2 = new User("jossWhedon", "123456", "iGotAWeird@name.com");
		validUser2.addOrder(validOrder2);

		userRepo.update(validUser2);
		prodRepo.update(validProduct2);
		prodRepo.update(validProduct3);
		orderRepo.update(validOrder2);
		//4.95+0.95=5.9
		assertThat(eCom.order.getBy(5.9).get(0), equalTo(validOrder2));

		/*
		 * System.out.println(eCom.product.getAll());
		 * System.out.println(eCom.user.getAll());
		 * System.out.println(eCom.order.getAll());
		 */
	}

	@AfterClass
	public static void exit()
	{
		factory.close();
	}

}
