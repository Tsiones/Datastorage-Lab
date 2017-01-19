package se.github.datalab.repository.jpastorage;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import se.github.datalab.model.Product;
import se.github.datalab.repository.ProductRepository;
import se.github.datalab.statuses.ProductStatus;

public class JpaProductRepository extends JpaAbstractRepository<Product> implements ProductRepository
{

	public JpaProductRepository(EntityManagerFactory factory)
	{
		super(factory, Product.class);
	}

	@Override
	public Collection<Product> getAll()
	{
		return query("Product.GetAll", Product.class);
	}

	@Override
	public Collection<Product> getByName(String name)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			TypedQuery<Product> query = manager.createNamedQuery("Product.GetProduct", Product.class);
			query.setParameter("name", "%" + name + "%");
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
	public Collection<Product> getByStatus(ProductStatus status)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			TypedQuery<Product> result = manager.createNamedQuery("Product.GetByStatus", Product.class);
			result.setParameter("status", status.ordinal());
			return result.getResultList();
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
	public Collection<Product> getByCost(double price)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			TypedQuery<Product> result = manager.createNamedQuery("Product.GetByCost", Product.class);
			result.setParameter("price", price);
			return result.getResultList();
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
