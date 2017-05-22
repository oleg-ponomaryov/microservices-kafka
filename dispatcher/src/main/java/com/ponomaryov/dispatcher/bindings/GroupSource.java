package com.ponomaryov.dispatcher.bindings;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

import com.ponomaryov.commonbeans.Constants;

public interface GroupSource {

	  String OUTPUT = Constants.GROUP_SOURCE_OUTPUT;

	  @Output(GroupSource.OUTPUT)
	  MessageChannel output();
}