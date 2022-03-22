package org.hibernate.presentation.type.composite;

import org.hibernate.presentation.type.Payload;
import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.YearMonth;

import static org.assertj.core.api.Assertions.assertThat;

@DomainModel( annotatedClasses = { Payload.class, CompositeUserTypeEntity.class } )
@SessionFactory
public class CompositeUserTypeExampleTest {
	@Test
	public void test(SessionFactoryScope scope) {
		{
			// load it
			scope.inTransaction( (session) -> {
				final CompositeUserTypeEntity entity = session.byId(CompositeUserTypeEntity.class ).load(1 );
				assertThat( entity ).isNotNull();
				assertThat( entity.getYearMonth() ).isNotNull();
				assertThat( entity.getYearMonth() ).isEqualTo( YearMonth.of(2022, 3 ) );
			} );
		}

		{
			// restrict based on it
			scope.inTransaction( (session) -> {
				final String hql = "select e from CompositeUserTypeEntity e where e.yearMonth.year = 2022 or e.yearMonth = :yearMonth";
				final CompositeUserTypeEntity entity = session
						.createSelectionQuery( hql, CompositeUserTypeEntity.class )
						.setParameter( "yearMonth", YearMonth.of(2022, 3 ) )
						.getSingleResultOrNull();
				assertThat( entity ).isNotNull();
				assertThat( entity.getYearMonth() ).isNotNull();
				assertThat( entity.getYearMonth() ).isEqualTo( YearMonth.of(2022, 3 ) );
			} );
		}
	}

	@BeforeEach
	public void createData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			session.persist( new CompositeUserTypeEntity(1, "my entity", YearMonth.of(2022, 3 ) ) );
		} );
	}

	@AfterEach
	public void dropData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			session.createMutationQuery( "delete CompositeUserTypeEntity" ).executeUpdate();
		} );
	}
}
