package com.payday.domain;

import java.nio.charset.Charset;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Entry(base = "ou=people", objectClasses = { "person", "inetOrgPerson", "top" })
public class User {
	@Id
	private Name id;

	private @Attribute(name = "uid") String username;
	private @Attribute(name = "cn") String fullname;
	private @Attribute(name = "userPassword", type = Attribute.Type.BINARY) byte[] password;
	@DnAttribute(value = "ou", index = 0)
	private String department;

	public Name getId() {
		return id;
	}

	public void setId(Name id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password.getBytes(Charset.forName("UTF-8"));
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", fullname=" + fullname + ", password="
				+ new String(password) + ", department= " + department + " ]";
	}

}
