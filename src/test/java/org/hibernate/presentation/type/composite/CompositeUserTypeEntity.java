package org.hibernate.presentation.type.composite;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.CompositeType;

import java.time.YearMonth;

/**
 * Demonstrates the use of `@Type` (`UserType`)
 */
@Entity
public class CompositeUserTypeEntity {
	@Id
	private Integer id;
	private String name;
	@CompositeType( YearMonthCompositeUserType.class )
	@AttributeOverride(name = "year", column = @Column(name = "the_year"))
	@AttributeOverride(name = "month", column = @Column(name = "the_month"))
	private YearMonth yearMonth;

	private CompositeUserTypeEntity() {
		// for Hibernate use
	}

	public CompositeUserTypeEntity(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public CompositeUserTypeEntity(Integer id, String name, YearMonth yearMonth) {
		this.id = id;
		this.name = name;
		this.yearMonth = yearMonth;
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

	public YearMonth getYearMonth() {
		return yearMonth;
	}

	public void setYearMonth(YearMonth yearMonth) {
		this.yearMonth = yearMonth;
	}
}