package com.ponomaryov.dispatcher.bindings;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

import com.ponomaryov.commonbeans.Constants;

public interface RecipientSource {

	  String OUTPUT = Constants.RECIPIENT_SOURCE_OUTPUT;

	  @Output(RecipientSource.OUTPUT)
	  MessageChannel output();
}