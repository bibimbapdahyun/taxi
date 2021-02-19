package com.taxi.entity;

import java.io.Serializable;
import java.util.Objects;

public class TripState implements Serializable {

	private static final long serialVersionUID = 8102752412045949280L;

	int id;
	String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TripState other = (TripState) obj;
		return id == other.id && Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "TripState [id=" + id + ", name=" + name + "]";
	}

}
