package org.hibernate.presentation.type;

/**
 * A wrapper for JSON payload.
 */
public class Payload {
	private final String payloadJson;

	public Payload(String payloadJson) {
		this.payloadJson = payloadJson;
	}

	public String getPayloadJson() {
		return payloadJson;
	}
}
