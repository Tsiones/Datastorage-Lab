package se.github.datalab.repository.jpastorage;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import se.github.datalab.model.Id;
import se.github.datalab.repository.StorageRepository;
import se.github.datalab.service.BasicLogger;

public abstract class JpaAbstractRepository<E extends Id> implements StorageRepository<E>
{
	protected static BasicLogger logger = new BasicLogger("Jpa log");

	static
	{
		logger.doConsoleOutput(false);
		logger.log(Level.INFO, "Logging started!");
	}

	protected EntityManagerFactory factory;
	protected Class<E> entityClass;

	public JpaAbstractRepository(EntityManagerFactory factory, Class<E> entityClass)
	{
		this.factory = factory;
		this.entityClass = entityClass;
	}

	@Override
	public E update(E entity)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			manager.getTransaction().begin();
			if (entity.hasId())
			{
				return manager.merge(entity);
			}
			manager.persist(entity);
			manager.getTransaction().commit();
			return entity;
		}
		catch (Exception e)
		{
			logger.log(e, "test");
		}
		finally
		{
			manager.close();
		}
		return entity;

	}

	@Override
	public E remove(E entity)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			manager.getTransaction().begin();
			manager.remove(manager.contains(entity) ? entity : manager.merge(entity));
			manager.getTransaction().commit();
			return entity;
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
	public E getById(Long id)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			return manager.find(entityClass, id);
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

	public List<E> query(String queryName, Class<E> entityClass)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			manager.getTransaction().begin();
			TypedQuery<E> query = manager.createNamedQuery(queryName, entityClass);
			List<E> result = query.getResultList();
			manager.getTransaction().commit();
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
