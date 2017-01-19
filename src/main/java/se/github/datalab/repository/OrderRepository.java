package se.github.datalab.repository;

import java.util.Collection;

import se.github.datalab.model.Order;
import se.github.datalab.model.User;
import se.github.datalab.statuses.OrderStatus;

public interface OrderRepository extends StorageRepository<Order>
{
	Collection<Order> getAll();

	Collection<Order> getByStatus(OrderStatus status);

	Collection<Order> getByTotalCost(double price);

	Collection<Order> getByUser(User user);
}
