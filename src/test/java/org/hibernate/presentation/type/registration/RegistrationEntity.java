package org.hibernate.presentation.type.registration;

import org.hibernate.annotations.JavaTypeRegistration;
import org.hibernate.presentation.type.Payload;
import org.hibernate.presentation.type.PayloadJavaType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Demonstrates the use of `@JavaTypeRegistration`
 */
@Entity
@JavaTypeRegistration( javaType = Payload.class, descriptorClass = PayloadJavaType.class )
public class RegistrationEntity {
	@Id
	private Integer id;
	private String name;
	private Payload payload;

	private RegistrationEntity() {
		// for Hibernate use
	}

	public RegistrationEntity(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public RegistrationEntity(Integer id, String name, Payload payload) {
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