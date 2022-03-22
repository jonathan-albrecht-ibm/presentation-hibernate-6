package org.hibernate.presentation.type.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.presentation.type.Payload;
import org.hibernate.usertype.UserType;

public class PayloadUserType implements UserType<Payload> {
	@Override
	public int getSqlType() {
		return Types.VARCHAR;
	}

	@Override
	public Class<Payload> returnedClass() {
		return Payload.class;
	}

	@Override
	public boolean equals(Payload x, Payload y) {
		return Objects.equals( x, y );
	}

	@Override
	public int hashCode(Payload x) {
		return Objects.hashCode( x );
	}

	@Override
	public Payload nullSafeGet(
			ResultSet rs,
			int position,
			SharedSessionContractImplementor session,
			Object owner) throws SQLException {
		final String state = rs.getString( position );
		return state == null || rs.wasNull()
				? null
				: new Payload( state );
	}

	@Override
	public void nullSafeSet(
			PreparedStatement st,
			Payload value,
			int index,
			SharedSessionContractImplementor session) throws SQLException {
		if ( value == null ) {
			st.setString( index, null );
		}
		else {
			st.setString( index, value.getPayloadJson() );
		}
	}

	@Override
	public Payload deepCopy(Payload value) {
		// the internal state is immutable
		return value;
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Override
	public Serializable disassemble(Payload value) {
		return value == null ? null : value.getPayloadJson();
	}

	@Override
	public Payload assemble(Serializable cached, Object owner) {
		return cached == null ? null : new Payload( (String) cached );
	}

	@Override
	public Payload replace(Payload detached, Payload managed, Object owner) {
		return detached;
	}
}
