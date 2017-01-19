package se.github.datalab.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import se.github.datalab.statuses.OrderStatus;

@NamedQueries(value = {
		@NamedQuery(name = "Orders.GetAll", query = "SELECT o FROM Order o"),
		@NamedQuery(name = "Orders.GetByStatus", query = "SELECT o FROM Order o WHERE o.orderStatus = :status"),
		@NamedQuery(name = "Orders.GetByTotalCost", query = "SELECT o FROM Order o WHERE o.orderCost = :cost"),
		@NamedQuery(name = "Orders.GetByUser", query = "SELECT o FROM Order o WHERE o.buyer = :buyer")
})
@Entity
@Table(name = "Orders")
public class Order extends Id
{
	@OneToMany(fetch = FetchType.EAGER)
	private Collection<Product> products;

	@ManyToOne(targetEntity = User.class)
	private User buyer;

	private double orderCost;

	@Column(name = "order_status", nullable = false)
	private int orderStatus;

	public Order()
	{
		products = new ArrayList<>();
	}

	public Order(Product... products)
	{
		this();
		for (Product product : products)
		{
			addProduct(product);
		}
	}

	public OrderStatus getOrderStatus()
	{
		return OrderStatus.values()[orderStatus];
	}

	public void setOrderStatus(OrderStatus status)
	{
		this.orderStatus = status.ordinal();
	}

	public Collection<Product> getProducts()
	{
		return new ArrayList<>(products);
	}

	public Order addProduct(Product product)
	{
		products.add(product);
		orderCost += product.getPrice();
		return this;
	}

	public double getOrderCost()
	{
		return orderCost;
	}

	public User getBuyer()
	{
		return buyer;
	}

	public void setBuyer(User buyer)
	{
		this.buyer = buyer;
	}

	@Override
	public String toString()
	{
		return id + ":" + products.toString() + ":" + orderCost + ":" + orderStatus + ":" + buyer.getUsername();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buyer == null) ? 0 : buyer.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (other == null)
		{
			return false;
		}
		if (other instanceof Order)
		{
			Order otherOrder = (Order) other;
			return getProducts().equals(otherOrder.getProducts())
					&& getBuyer().equals(otherOrder.getBuyer());
		}
		return false;
	}

}
