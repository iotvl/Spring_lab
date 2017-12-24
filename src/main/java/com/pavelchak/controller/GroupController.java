package com.pavelchak.controller;

import com.pavelchak.DTO.DTOBuilder;
import com.pavelchak.DTO.impl.GroupDTO;
import com.pavelchak.domain.Group;
import com.pavelchak.exceptions.ExistsPersonsForCityException;
import com.pavelchak.exceptions.NoSuchCityException;
import com.pavelchak.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@RestController
public class GroupController {
    @Autowired
    GroupService groupService;

    @GetMapping(value = "/api/group")
    public ResponseEntity<List<GroupDTO>> getAllGroups() {
        List<Group> groupList = groupService.getAllGroups();
        Link link = linkTo(methodOn(GroupController.class).getAllGroups()).withSelfRel();
        List<GroupDTO> groups = DTOBuilder.buildDtoListForCollection(groupList, GroupDTO.class, link);
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping(value = "/api/group/{group_id}")
    public ResponseEntity<GroupDTO> getGroup(@PathVariable Long group_id) throws NoSuchCityException {
        Group group = groupService.getGroup(group_id);
        Link link = linkTo(methodOn(GroupController.class).getGroup(group_id)).withSelfRel();
        GroupDTO groupDTO = DTOBuilder.buildDtoForEntity(group,GroupDTO.class, link);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/group/{group_id}")
    public  ResponseEntity<GroupDTO> addGroup(@RequestBody Group newGroup) throws NoSuchCityException {
        groupService.createGroup(newGroup);
        Link link = linkTo(methodOn(GroupController.class).getGroup((long) newGroup.getId())).withSelfRel();
        GroupDTO groupDTO = DTOBuilder.buildDtoForEntity(newGroup,GroupDTO.class, link);
        return new ResponseEntity<>(groupDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/group/{group_id}")
    public  ResponseEntity<GroupDTO> updateGroup(@RequestBody Group ucity, @PathVariable Long group_id) throws NoSuchCityException {
        groupService.updateGroup(ucity, group_id);
        Group group = groupService.getGroup(group_id);
        Link link = linkTo(methodOn(GroupController.class).getGroup(group_id)).withSelfRel();
        GroupDTO groupDTO = DTOBuilder.buildDtoForEntity(group,GroupDTO.class, link);
        return new ResponseEntity<>(groupDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/group/{group_id}")
    public  ResponseEntity deleteGroup(@PathVariable Long group_id) throws NoSuchCityException, ExistsPersonsForCityException {
        groupService.deleteGroup(group_id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
