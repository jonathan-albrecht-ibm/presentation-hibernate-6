package org.hibernate.presentation.type;

import java.sql.Types;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.BasicJavaType;
import org.hibernate.type.descriptor.jdbc.JdbcType;
import org.hibernate.type.descriptor.jdbc.JdbcTypeIndicators;

/**
 * Describes the Java aspects of the custom Payload class
 */
public class PayloadJavaType implements BasicJavaType<Payload> {
	@Override
	public Class<Payload> getJavaTypeClass() {
		return Payload.class;
	}

	@Override
	public Payload fromString(CharSequence state) {
		return state == null ? null : new Payload(state.toString() );
	}

	@Override
	public String toString(Payload value) {
		return value == null ? null : value.getPayloadJson();
	}

	@Override
	public <X> X unwrap(Payload value, Class<X> type, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}

		if ( String.class.equals( type ) ) {
			return (X) toString( value );
		}

		if ( byte[].class.equals( type ) ) {
			// can be byte[] if mapping as JSON against databases which
			// do not support a proper JSON type.
			return (X) toString( value ).getBytes();
		}

		throw new RuntimeException( "Unexpected unwrap type : " + type.getTypeName() );
	}

	@Override
	public Payload wrap(Object value, WrapperOptions options) {
		if ( value == null ) {
			return null;
		}

		if ( value instanceof String ) {
			return new Payload((String) value );
		}

		if ( value instanceof byte[] ) {
			return new Payload(new String((byte[]) value ) );
		}

		throw new RuntimeException( "Unexpected wrap value : " + value );
	}

	@Override
	public JdbcType getRecommendedJdbcType(JdbcTypeIndicators indicators) {
		return indicators.getTypeConfiguration()
				.getJdbcTypeRegistry()
				.getDescriptor( Types.VARCHAR );
	}
}
