package se.github.datalab.repository;

import se.github.datalab.model.Id;

public interface StorageRepository<E extends Id>
{
	/**
	 * Persists Entity
	 * 
	 * @param entity
	 * @return
	 */
	E update(E entity);

	/**
	 * Removes entity
	 * 
	 * @param entity
	 * @return
	 */
	E remove(E entity);

	/**
	 * Retrieves entity by specifying id
	 * 
	 * @param id
	 * @return
	 */
	E getById(Long id);
}
