package com.ponomaryov.dispatcher.controllers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ponomaryov.commonbeans.Group;
import com.ponomaryov.dispatcher.bindings.GroupSource;

@Slf4j
@RestController
@EnableBinding(GroupSource.class)
@RequestMapping("/groups")
public class GroupCreateController {

    private GroupSource groupSource;

    @Autowired
    public GroupCreateController(final GroupSource source) {
        this.groupSource = source;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Group create(@RequestBody Group group) {
    	groupSource.output().send(MessageBuilder.withPayload(group).build());
        log.info("Group {} has been created:", group);
        return group;
    }
}
