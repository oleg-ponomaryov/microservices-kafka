package com.ponomaryov.groups;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.support.GenericMessage;

import com.ponomaryov.groups.bindings.GroupSink;
import com.ponomaryov.groups.services.GroupService;
import com.ponomaryov.commonbeans.Group;

@EnableBinding(GroupSink.class)
@Slf4j
public class GroupConsumer {

	@Autowired
	private GroupService groupService;
	
    @ServiceActivator(inputChannel = GroupSink.INPUT)
    public void receiveGroup(GenericMessage<Group> message) {
        log.info("Message: {} has been received------>>>>", message.getPayload());
        
        Group input = message.getPayload();
        if (input!=null) {
        	com.ponomaryov.groups.domain.Group group = new com.ponomaryov.groups.domain.Group();
        	group.setName(input.getName());
        	group.setDescription(input.getDescription());
        	groupService.save(group);
        }
    }
}