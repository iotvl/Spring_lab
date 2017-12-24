package com.pavelchak.DTO.impl;

import com.pavelchak.DTO.DTO;
import com.pavelchak.controller.StudentController;
import com.pavelchak.domain.Group;
import com.pavelchak.exceptions.NoSuchCityException;
import com.pavelchak.exceptions.NoSuchPersonException;
import org.springframework.hateoas.Link;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


public class GroupDTO extends DTO<Group> {
    public GroupDTO(Group group, Link link) throws NoSuchCityException, NoSuchPersonException {
        super(group, link);
        add(linkTo(methodOn(StudentController.class).getStudentsByGroupId((long) getEntity().getId())).withRel("students"));
    }

    public int getGroupId() { return getEntity().getId(); }

    public String getName() {
        return getEntity().getName();
    }
}
