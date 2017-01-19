package se.github.datalab.service;

import java.util.ArrayList;
import java.util.List;

import se.github.datalab.exception.IllegalOrderException;
import se.github.datalab.exception.IllegalProductException;
import se.github.datalab.exception.IllegalUserException;
import se.github.datalab.model.Order;
import se.github.datalab.model.Product;
import se.github.datalab.model.User;
import se.github.datalab.repository.OrderRepository;
import se.github.datalab.repository.ProductRepository;
import se.github.datalab.repository.UserRepository;
import se.github.datalab.statuses.OrderStatus;
import se.github.datalab.statuses.ProductStatus;
import se.github.datalab.statuses.UserStatus;

public class ECommerceService
{
	private UserRepository userRepo;
	private ProductRepository prodRepo;
	private OrderRepository orderRepo;

	public final UserCategory user = new UserCategory();
	public final OrderCategory order = new OrderCategory();
	public final ProductCategory product = new ProductCategory();

	// TODO: Check doc file to see which methods/operations eCom needs to have
	// toward the database
	//
	public ECommerceService(UserRepository userRepo, ProductRepository prodRepo, OrderRepository orderRepo)
	{
		this.userRepo = userRepo;
		this.prodRepo = prodRepo;
		this.orderRepo = orderRepo;
	}

	public class UserCategory// ----------- USER---------
	{
		public User add(User user) throws IllegalUserException
		{
			if (user.getUsername().isEmpty())
			{
				throw new IllegalUserException("Username invalid!");
			}
			return userRepo.update(user);
		}

		public User remove(User user)
		{
			return userRepo.remove(user);
		}

		public User getBy(Long id)
		{
			return userRepo.getById(id);
		}

		public List<User> getAll()
		{
			return new ArrayList<>(userRepo.getAll());
		}

		public List<User> getBy(String username)
		{
			return new ArrayList<>(userRepo.getByUsername(username));
		}

		public List<User> getByEmail(String email)
		{
			return new ArrayList<>(userRepo.getByEmail(email));
		}

		public List<User> getBy(UserStatus status)
		{
			return new ArrayList<>(userRepo.getByStatus(status));
		}

		public void changeStatus(User user, UserStatus status)
		{
			user.setUserStatus(status);
			userRepo.update(user);
		}
	}

	public class OrderCategory // -------------ORDER---------
	{

		public Order add(Order order) throws IllegalOrderException
		{
			if (order.getProducts().isEmpty())
			{
				throw new IllegalOrderException("Cannot add empty order");
			}
			return orderRepo.update(order);
		}

		public Order remove(Order order)
		{
			return orderRepo.remove(order);
		}

		public Order getBy(Long id)
		{
			return orderRepo.getById(id);
		}

		public List<Order> getAll()
		{
			return new ArrayList<>(orderRepo.getAll());
		}

		public List<Order> getBy(User user)
		{
			return new ArrayList<>(orderRepo.getByUser(user));
		}

		public List<Order> getBy(OrderStatus status)
		{
			return new ArrayList<>(orderRepo.getByStatus(status));
		}

		public List<Order> getBy(double price)
		{
			return new ArrayList<>(orderRepo.getByTotalCost(price));
		}

		public void changeStatus(Order order, OrderStatus status)
		{
			order.setOrderStatus(status);
			orderRepo.update(order);
		}
	}

	public class ProductCategory // ------------PRODUCT------------
	{
		public Product add(Product product) throws IllegalProductException
		{
			if (product.getName().isEmpty())
			{
				throw new IllegalProductException("Product name invalid!");
			}
			return prodRepo.update(product);
		}

		public Product remove(Product product)
		{
			return prodRepo.remove(product);
		}

		public Product getBy(Long id)
		{
			return prodRepo.getById(id);
		}

		public List<Product> getAll()
		{
			return new ArrayList<>(prodRepo.getAll());
		}

		public List<Product> getBy(String name)
		{
			return new ArrayList<>(prodRepo.getByName(name));
		}

		public List<Product> getBy(ProductStatus status)
		{
			return new ArrayList<>(prodRepo.getByStatus(status));
		}

		public List<Product> getBy(double price)
		{
			return new ArrayList<>(prodRepo.getByCost(price));
		}

		public void changeStatus(Product product, ProductStatus status)
		{
			product.setProductStatus(status);
			prodRepo.update(product);
		}
	}
}
