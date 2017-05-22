package com.ponomaryov.groups.services;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ponomaryov.groups.domain.Group;
import com.ponomaryov.groups.repository.GroupRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    public Group getGroupById(String id) {
        return groupRepository.findOne(id);
    }

    public Collection<Group> getAllGroups() {
        return groupRepository.findAll(new Sort("name"));
    }
    
    @Transactional
    public void delete(String id) {
        groupRepository.delete(id);
    }

    @Transactional
    public Group save(Group group) {
    	Group g = groupRepository.save(group);
    	log.info("Group {} has been saved", g);
        return groupRepository.save(group);
    }
    
    @Transactional
    public Group update(Group group) {
    	Group gr = getGroupById(group.getId());
    	if (gr == null) {
    		throw new NoSuchElementException(String.format("Group=%s not found", group.getId()));
    	}
    	
    	if (group.getName() != null)
    		gr.setName(group.getName());
    	
    	if (group.getDescription()!=null)
    		gr.setDescription(group.getDescription());
    	
        return groupRepository.save(gr);
    }
}
