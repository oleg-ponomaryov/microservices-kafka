package com.ponomaryov.groups.bindings;

import org.mockito.Mockito;
import org.springframework.cloud.stream.binder.Binder;
import org.springframework.context.annotation.Bean;

class MockBinderConfiguration {

	@Bean
	public Binder<?, ?, ?> binder() {
		return Mockito.mock(Binder.class);
	}
}
