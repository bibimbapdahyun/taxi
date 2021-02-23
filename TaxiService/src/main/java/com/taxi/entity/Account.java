package com.taxi.entity;

import java.io.Serializable;
import java.util.Objects;

public class Account implements Serializable {

	private static final long serialVersionUID = 236414962097068202L;

	private int id;
	private String login;
	private String password;
	private Role role;
	private String mail;
	private String name;
	private String surname;
	private Gender gender;

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public int hashCode() {
		return Objects.hash(gender, id, login, mail, name, password, role, surname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return Objects.equals(gender, other.gender) && id == other.id && Objects.equals(login, other.login)
				&& Objects.equals(mail, other.mail) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password) && Objects.equals(role, other.role)
				&& Objects.equals(surname, other.surname);
	}

	@Override
	public String toString() {
		return "Account [login=" + login + ", role=" + role + ", mail=" + mail + ", name=" + name + ", surname="
				+ surname + "gender=" + gender + "]";
	}

}
