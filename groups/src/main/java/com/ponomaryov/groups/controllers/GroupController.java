package com.ponomaryov.groups.controllers;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ponomaryov.groups.domain.Group;
import com.ponomaryov.groups.services.GroupService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;
    
    /*** For large number here should be paging ***/
    @RequestMapping(value = "/groups/all", method=RequestMethod.GET)
    public @ResponseBody Collection<Group> getAllGroups() {
        return groupService.getAllGroups();
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value="/groups/{id}")
    public @ResponseBody void delete(@PathVariable String id) {
        groupService.delete(id);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/groups/{id}")
    public @ResponseBody Group update(@PathVariable String id, @RequestBody Group group) {
      return groupService.update(group);
    }
    
    @RequestMapping(method=RequestMethod.GET, value="/groups/{id}")
    public @ResponseBody Group getGroup(@PathVariable String id) {

        Group group = groupService.getGroupById(id);
        if (group != null) {
                return group;
        }
        else {
                throw new NoSuchElementException(String.format("Group=%s not found", id));
        }
    }
  }
