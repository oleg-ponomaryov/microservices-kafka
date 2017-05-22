package com.ponomaryov.groups.bindings;

import static org.mockito.Mockito.mock;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.config.GlobalChannelInterceptor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ChannelInterceptor;

@SpringBootApplication
@EnableBinding(Sink.class)
@Import(MockBinderConfiguration.class)
public class Consumer {

	@ServiceActivator(inputChannel = Sink.INPUT)
	public void groupSink(Message<?> message) {
	}

	@GlobalChannelInterceptor
	@Bean
	public ChannelInterceptor globalChannelInterceptor() {
		return mock(ChannelInterceptor.class);
	}
}
