package se.github.datalab.repository;

import java.util.Collection;

import se.github.datalab.model.User;
import se.github.datalab.statuses.UserStatus;

public interface UserRepository extends StorageRepository<User>
{
	Collection<User> getAll();

	Collection<User> getByStatus(UserStatus status);

	Collection<User> getByEmail(String email);

	Collection<User> getByUsername(String username);

}
