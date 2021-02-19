package com.taxi.entity;

import java.io.Serializable;
import java.util.Objects;

public class CarType implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String type;
	private int startPrice;
	private int pricePerKm;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}

	public int getPricePerKm() {
		return pricePerKm;
	}

	public void setPricePerKm(int pricePerKm) {
		this.pricePerKm = pricePerKm;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, pricePerKm, startPrice, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarType other = (CarType) obj;
		return id == other.id && pricePerKm == other.pricePerKm && startPrice == other.startPrice
				&& Objects.equals(type, other.type);
	}

	@Override
	public String toString() {
		return "\nCarType [id=" + id + ", type=" + type + ", startPrice=" + startPrice + ", pricePerKm=" + pricePerKm
				+ "]";
	}

}
