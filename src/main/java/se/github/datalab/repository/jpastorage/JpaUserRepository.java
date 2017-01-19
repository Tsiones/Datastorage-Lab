package se.github.datalab.repository.jpastorage;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import se.github.datalab.model.User;
import se.github.datalab.repository.UserRepository;
import se.github.datalab.statuses.UserStatus;

public class JpaUserRepository extends JpaAbstractRepository<User> implements UserRepository
{

	public JpaUserRepository(EntityManagerFactory factory)
	{
		super(factory, User.class);
	}

	@Override
	public Collection<User> getAll()
	{
		return query("User.GetAll", User.class);
	}

	@Override
	public Collection<User> getByEmail(String email)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			TypedQuery<User> query = manager.createNamedQuery("User.GetByEmail", User.class);
			query.setParameter("email", "%" + email + "%");
			return query.getResultList();
		}
		catch (IllegalArgumentException e)
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
	public Collection<User> getByUsername(String username)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			TypedQuery<User> query = manager.createNamedQuery("User.GetByUsername", User.class);
			query.setParameter("username", "%" + username + "%");
			return query.getResultList();
		}
		catch (IllegalArgumentException e)
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
	public Collection<User> getByStatus(UserStatus status)
	{
		EntityManager manager = factory.createEntityManager();
		try
		{
			TypedQuery<User> query = manager.createNamedQuery("User.GetByStatus", User.class);
			query.setParameter("status", status.ordinal());
			return query.getResultList();
		}
		catch (IllegalArgumentException e)
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
