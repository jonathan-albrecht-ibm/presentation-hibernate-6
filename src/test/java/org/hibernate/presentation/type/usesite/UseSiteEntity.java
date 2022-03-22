package org.hibernate.presentation.type.usesite;

import org.hibernate.annotations.JavaType;
import org.hibernate.presentation.type.Payload;
import org.hibernate.presentation.type.PayloadJavaType;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * Demonstrates the use of `@JavaType`
 */
@Entity
public class UseSiteEntity {
	@Id
	private Integer id;
	private String name;
	@JavaType( PayloadJavaType.class )
	private Payload payload;

	private UseSiteEntity() {
		// for Hibernate use
	}

	public UseSiteEntity(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public UseSiteEntity(Integer id, String name, Payload payload) {
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