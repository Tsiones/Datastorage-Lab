package se.github.datalab.repository.jpastorage;

import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import se.github.datalab.model.Order;
import se.github.datalab.model.User;
import se.github.datalab.repository.OrderRepository;
import se.github.datalab.statuses.OrderStatus;

public class JpaOrderRepository extends JpaAbstractRepository<Order> implements OrderRepository
{

	public JpaOrderRepository(EntityManagerFactory factory)
	{
		super(factory, Order.class);
	}

	@Override
	public Collection<Order> getAll()
	{
		return query("Orders.GetAll", Order.class);
	}

	@Override
	public Collection<Order> getByStatus(OrderStatus status)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			TypedQuery<Order> query = manager.createNamedQuery("Orders.GetByStatus", Order.class);
			query.setParameter("status", status.ordinal());
			List<Order> result = query.getResultList();
			return result;
		}
		catch (Exception e)
		{
			logger.log(e);
		}
		finally
		{
			manager.close();
		}
		return null;
	}

	@Override
	public Collection<Order> getByTotalCost(double price)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			TypedQuery<Order> query = manager.createNamedQuery("Orders.GetByTotalCost", Order.class);
			query.setParameter("cost", price);
			return query.getResultList();
		}
		catch (Exception e)
		{
			logger.log(e);
		}
		finally
		{
			manager.close();
		}
		return null;
	}

	@Override
	public Collection<Order> getByUser(User user)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			TypedQuery<Order> query = manager.createNamedQuery("Orders.GetByUser", Order.class);
			query.setParameter("buyer", user);
			List<Order> result = query.getResultList();
			return result;
		}
		catch (Exception e)
		{
			logger.log(e);
		}
		finally
		{
			manager.close();
		}
		return null;
	}

}
