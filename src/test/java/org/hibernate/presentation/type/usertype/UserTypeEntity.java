package org.hibernate.presentation.type.usertype;

import org.hibernate.annotations.Type;
import org.hibernate.presentation.type.Payload;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Demonstrates the use of `@Type` (`UserType`)
 */
@Entity
public class UserTypeEntity {
	@Id
	private Integer id;
	private String name;
	@Type( PayloadUserType.class )
	private Payload payload;

	private UserTypeEntity() {
		// for Hibernate use
	}

	public UserTypeEntity(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public UserTypeEntity(Integer id, String name, Payload payload) {
		this.id = id;
		this.name = name;
		this.payload = payload;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Payload getPayload() {
		return payload;
	}

	public void setPayload(Payload widget) {
		this.payload = widget;
	}
}