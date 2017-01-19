package se.github.datalab.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import se.github.datalab.statuses.ProductStatus;

@NamedQueries(value = {
		@NamedQuery(name = "Product.GetAll", query = "SELECT p FROM Product p"),
		//		@NamedQuery(name = "Product.GetProductById", query = "SELECT p FROM Product WHERE p.Id = :id"),
		@NamedQuery(name = "Product.GetProduct", query = "SELECT p FROM Product p WHERE p.name LIKE :name ORDER BY p.name ASC"),
		@NamedQuery(name = "Product.GetByStatus", query = "SELECT p FROM Product p WHERE p.productStatus = :status"), // ? for ordinals
		@NamedQuery(name = "Product.GetByCost", query = "SELECT p FROM Product p WHERE p.price LIKE :price")
})
@Entity
public class Product extends Id
{
	@Column(nullable = false)
	private String name;
	private String description;
	private double price;
	@Column(name = "product_status", nullable = false)
	private int productStatus;

	protected Product()
	{
	}

	public Product(String name, String description, double price)
	{
		this.name = name;
		this.description = description;
		this.price = price;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	public String getName()
	{
		return name;
	}

	public ProductStatus getProductStatus()
	{
		return ProductStatus.values()[productStatus];
	}

	public void setProductStatus(ProductStatus productStatus)
	{
		this.productStatus = productStatus.ordinal();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Product other = (Product) obj;
		if (name == null)
		{
			if (other.name != null) return false;
		}
		else if (!name.equals(other.name)) return false;
		return true;
	}

	@Override
	public String toString()
	{
		return id + ":" + name + ":" + price + ":" + productStatus;
	}
}
