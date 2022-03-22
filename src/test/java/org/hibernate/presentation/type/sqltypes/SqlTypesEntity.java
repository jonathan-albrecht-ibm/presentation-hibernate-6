package org.hibernate.presentation.type.sqltypes;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.net.InetAddress;
import java.util.Map;
import java.util.UUID;

/**
 * Demonstrates the use of `SqlTypes` and other new type mappings
 */
@Entity
public class SqlTypesEntity {
	@Id
	private Integer id;
	private String name;
	private UUID uuid;;
	private InetAddress inetAddress;
	@JdbcTypeCode( SqlTypes.JSON )
	private Map<String, String> payload;

	private SqlTypesEntity() {
		// for Hibernate use
	}

	public SqlTypesEntity(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public SqlTypesEntity(Integer id, String name, Map<String, String> payload) {
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

	public Map<String, String> getPayload() {
		return payload;
	}

	public void setPayload(Map<String, String> widget) {
		this.payload = widget;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public InetAddress getInetAddress() {
		return inetAddress;
	}

	public void setInetAddress(InetAddress inetAddress) {
		this.inetAddress = inetAddress;
	}
}