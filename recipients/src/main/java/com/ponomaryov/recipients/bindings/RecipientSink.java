package com.ponomaryov.recipients.bindings;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

import com.ponomaryov.commonbeans.Constants;

public interface RecipientSink {

	String INPUT = Constants.RECIPIENT_SINK_INPUT;

	@Input(RecipientSink.INPUT)
	SubscribableChannel input();
}
