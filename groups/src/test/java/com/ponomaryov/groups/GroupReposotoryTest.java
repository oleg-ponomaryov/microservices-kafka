package com.ponomaryov.groups;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

import java.lang.reflect.Field;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.annotation.Bindings;
import org.springframework.cloud.stream.binding.InputBindingLifecycle;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ponomaryov.groups.bindings.Consumer;
import com.ponomaryov.groups.domain.Group;
import com.ponomaryov.groups.repository.GroupRepository;
import com.ponomaryov.groups.services.GroupService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GroupReposotoryTest {

	public static final Message<?> TEST_MESSAGE = MessageBuilder.withPayload(
			"group").build();

	@Autowired
	ChannelInterceptor channelInterceptor;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	GroupService groupService;

	@Autowired
	@Bindings(Consumer.class)
	public Sink groupSink;

	@Autowired
    private ConfigurableApplicationContext applicationContext ;
	
	@After
	public void tearDown() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		groupRepository.deleteAll();
		InputBindingLifecycle  ch = (InputBindingLifecycle) applicationContext.getBean("inputBindingLifecycle");
		Class <?> cl = ch.getClass();
		Field field = cl.getDeclaredField("running");
        field.setAccessible(true);
        field.setBoolean(ch, false);
	}
	
	
	@Test
	public void testRepository() {
		assertNotNull(groupRepository);
		assertNotNull(groupService);

		Group g0 = new Group();
		g0.setName("group1");
		g0.setDescription("group1 Description");
		groupRepository.save(g0);
		
		Group g1 = new Group();
		g1.setName("group2");
		g1.setDescription("group2 Description");
		groupRepository.save(g1);

		Group g2 = new Group();
		g2.setName("group3");
		g2.setDescription("group3 Description");
		groupRepository.save(g2);
		
		assertEquals(groupService.getAllGroups().size(),3);
		
		groupService.delete(g0.getId());

		assertEquals(groupService.getAllGroups().size(),2);

	}

	@Test
	public void testBoundChannelsIntercepted() {
		groupSink.input().send(TEST_MESSAGE);
		verify(channelInterceptor).preSend(TEST_MESSAGE, groupSink.input());
		verifyNoMoreInteractions(channelInterceptor);
	}
}
