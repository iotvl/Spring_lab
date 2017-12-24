package com.pavelchak.DTO.impl;

import com.pavelchak.DTO.DTO;
import com.pavelchak.controller.TeacherController;
import com.pavelchak.domain.Student;
import com.pavelchak.exceptions.NoSuchPersonException;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class StudentDTO extends DTO<Student> {
    public StudentDTO(Student Student, Link link) throws NoSuchPersonException {
        super(Student, link);
        add(linkTo(methodOn(TeacherController.class).getTeachersByStudentID((long) getEntity().getId())).withRel("teachers"));
    }

    public int getStudentId() {
        return getEntity().getId();
    }

    public String getSurname() {
        return getEntity().getSurname();
    }

    public String getName() {
        return getEntity().getName();
    }


    public String getGroup() {
        if(getEntity().getGroup()==null) return "";
        return getEntity().getGroup().getName();
    }


}
