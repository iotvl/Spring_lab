package com.pavelchak.DTO.impl;

import com.pavelchak.DTO.DTO;
import com.pavelchak.controller.StudentController;
import com.pavelchak.domain.Teacher;
import com.pavelchak.exceptions.NoSuchBookException;
import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class TeacherDTO extends DTO<Teacher> {
    public TeacherDTO(Teacher teacher, Link link) throws NoSuchBookException {
        super(teacher, link);
        add(linkTo(methodOn(StudentController.class).getStudentsByTeacherId((long) getEntity().getId())).withRel("students"));
    }

    public int getTeacherId() {
        return getEntity().getId();
    }

    public String getTeacherName() {
        return getEntity().getName();
    }

    public String getTeacherSurname() {
        return getEntity().getSurname();
    }

    public String getTeacherSubject() {
        return getEntity().getSubject();
    }

}
