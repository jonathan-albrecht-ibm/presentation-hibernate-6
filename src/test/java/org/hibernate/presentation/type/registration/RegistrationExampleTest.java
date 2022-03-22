package org.hibernate.presentation.type.registration;

import org.hibernate.presentation.type.Payload;

import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DomainModel( annotatedClasses = { Payload.class, RegistrationEntity.class } )
@SessionFactory
public class RegistrationExampleTest {
	@Test
	public void test(SessionFactoryScope scope) {
		{
			// load it
			scope.inTransaction( (session) -> {
				final RegistrationEntity  entity = session.byId( RegistrationEntity .class ).load( 1 );
				assertThat( entity ).isNotNull();
				assertThat( entity.getPayload() ).isNotNull();
				assertThat( entity.getPayload().getPayloadJson() ).isEqualTo( "abc" );
			} );
		}

		{
			// restrict based on it
			scope.inTransaction( (session) -> {
				final String hql = "select e from RegistrationEntity e where e.payload = :payload";
				final RegistrationEntity  entity = session
						.createSelectionQuery( hql, RegistrationEntity .class )
						.setParameter( "payload", new Payload("abc" ) )
						.getSingleResultOrNull();
				assertThat( entity ).isNotNull();
				assertThat( entity.getPayload() ).isNotNull();
				assertThat( entity.getPayload().getPayloadJson() ).isEqualTo( "abc" );
			} );
		}
	}

	@BeforeEach
	public void createData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			session.persist( new RegistrationEntity ( 1, "my entity", new Payload("abc" ) ) );
		} );
	}

	@AfterEach
	public void dropData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			session.createMutationQuery( "delete RegistrationEntity" ).executeUpdate();
		} );
	}
}
