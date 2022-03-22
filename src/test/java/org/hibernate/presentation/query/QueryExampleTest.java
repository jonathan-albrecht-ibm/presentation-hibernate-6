package org.hibernate.presentation.query;

import java.util.List;

import org.hibernate.presentation.model.Forum;
import org.hibernate.presentation.model.Post;
import org.hibernate.query.SelectionQuery;

import org.hibernate.testing.orm.junit.DomainModel;
import org.hibernate.testing.orm.junit.SessionFactory;
import org.hibernate.testing.orm.junit.SessionFactoryScope;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Demonstrate some query features
 */
@DomainModel( annotatedClasses = { Forum.class, Post.class } )
@SessionFactory
public class QueryExampleTest {
	@Test
	public void demonstrateMultipleQueryRoot(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			final SelectionQuery<String> query = session.createSelectionQuery(
				"(select p.title from Post p where p.title ilike '%!' " +
					"union all " +
					"select f.name from Forum f) order by 1 limit 1",
				String.class
			);
			final List<String> results = query.list();
			assertThat( results ).hasSize( 1 );
			assertThat( results.get( 0 ) ).isEqualTo( "first forum" );
		} );	}

	@BeforeEach
	public void createData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			final Forum forum = new Forum( 1, "first forum" );
			session.persist( forum );

			session.persist( new Post( 1, "Welcome!", forum ) );
			session.persist( new Post( 2, "Hi!", forum ) );
		} );
	}

	@AfterEach
	public void dropData(SessionFactoryScope scope) {
		scope.inTransaction( (session) -> {
			session.createMutationQuery( "delete Post" ).executeUpdate();
			session.createMutationQuery( "delete Forum" ).executeUpdate();
		} );
	}
}
