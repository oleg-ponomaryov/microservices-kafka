package com.ponomaryov.groups;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.stream.annotation.Bindings;
import org.springframework.cloud.stream.binding.InputBindingLifecycle;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.ponomaryov.groups.bindings.Consumer;
import com.ponomaryov.groups.domain.Group;
import com.ponomaryov.groups.repository.GroupRepository;
import com.ponomaryov.groups.services.GroupService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = GroupsMain.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.yml")
public class GroupControllerTest {

	public static final Message<?> TEST_MESSAGE = MessageBuilder.withPayload("group").build();

	@Autowired
	ChannelInterceptor channelInterceptor;

	@Autowired
	GroupService groupService;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	@Bindings(Consumer.class)
	public Sink groupSink;

	@Autowired
	private TestRestTemplate template;

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
	public void getAllGroups() throws Exception {

		Group g = new Group();
		g.setName("Group1");
		g.setDescription("Group1's Description ");
		groupService.save(g);

		Group g1 = new Group();
		g1.setName("Group2");
		g1.setDescription("Group2's Description ");
		groupService.save(g1);

		ResponseEntity<String> response = template.getForEntity("/groups/all", String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		List<Group> groups = convertJsonToGroups(response.getBody());
		assertEquals(groups.size(), 2);
	}

	@Test
	public void updateGroup() throws Exception {
		Group g = new Group();
		g.setName("Group 1");
		g.setDescription("Group1 description");
		groupService.save(g);

		g.setName("Group for update");

		String resourceUrl = "/groups/" + g.getId();
		HttpEntity<Group> requestUpdate = new HttpEntity<>(g, null);
		ResponseEntity<Group> response = template.exchange(resourceUrl, HttpMethod.PUT, requestUpdate, Group.class);

		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getName(), "Group for update");
	}

	@Test
	public void deleteGroup() throws Exception {

		Group g = new Group();
		g.setName("Group1");
		g.setDescription("Group1's Description ");
		groupService.save(g);

		Group g1 = new Group();
		g1.setName("Group2");
		g1.setDescription("Group2's Description ");
		groupService.save(g1);

		ResponseEntity<String> response = template.getForEntity("/groups/all", String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		List<Group> groups = convertJsonToGroups(response.getBody());
		assertEquals(groups.size(), 2);

		String resourceUrl = "/groups/" + g1.getId();
		HttpEntity<String> requestDelete = new HttpEntity<>(g.getId(), null);
		template.exchange(resourceUrl, HttpMethod.DELETE, requestDelete, Void.class);

		response = template.getForEntity("/groups/all", String.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);

		groups = convertJsonToGroups(response.getBody());
		assertEquals(groups.size(), 1);
	}

	private List<Group> convertJsonToGroups(String json) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, TypeFactory.defaultInstance().constructCollectionType(List.class, Group.class));
	}
}
