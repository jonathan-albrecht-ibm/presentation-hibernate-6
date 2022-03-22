package org.hibernate.presentation.type.sqltypes;

import org.hibernate.presentation.type.Payload;

import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@DomainModel( annotatedClasses = { Payload.class, SqlTypesEntity.class } )
@SessionFactory
public class SqlTypesExampleTest {
	@Test
	public void test(SessionFactoryScope scope) {
		{
			// load it
			scope.inTransaction( (session) -> {
				final SqlTypesEntity entity = session.byId(SqlTypesEntity.class ).load(1 );
				assertThat( entity ).isNotNull();
				assertThat( entity.getPayload() ).isNotNull();
				assertThat( entity.getPayload().get( "name" ) ).isEqualTo( "abc" );
				assertThat( entity.getPayload().get( "type" ) ).isEqualTo( "A" );
			} );
		}

		{
			// restrict based on it
			scope.inTransaction( (session) -> {
				final String hql = "select e from SqlTypesEntity e where e.payload = :payload";
				final SqlTypesEntity entity = session
						.createSelectionQuery( hql, SqlTypesEntity.class )
						.setParameter("payload", Map.of( "type", "A", "name", "abc" ) )
						.getSingleResultOrNull();
				assertThat( entity ).isNotNull();
				assertThat( entity.getPayload() ).isNotNull();
				assertThat( entity.getPayload().get( "name" ) ).isEqualTo( "abc" );
				assertThat( entity.getPayload().get( "type" ) ).isEqualTo( "A" );
			} );
		}
	}

	@BeforeEach
	public void createData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			session.persist( new SqlTypesEntity(1, "my entity", Map.of("name", "abc", "type", "A" ) ) );
		} );
	}

	@AfterEach
	public void dropData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			session.createMutationQuery( "delete SqlTypesEntity" ).executeUpdate();
		} );
	}
}
