package com.pavelchak.controller;

import com.pavelchak.DTO.DTOBuilder;
import com.pavelchak.DTO.impl.TeacherDTO;

import com.pavelchak.domain.Teacher;
import com.pavelchak.exceptions.*;
import com.pavelchak.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @GetMapping(value = "/api/teacher/student/{student_id}")
    public ResponseEntity<List<TeacherDTO>> getTeachersByStudentID(@PathVariable Long student_id) throws NoSuchPersonException {
        Set<Teacher> teacherList = teacherService.getTeachersByStudentId(student_id);
        Link link = linkTo(methodOn(TeacherController.class).getAllTeachers()).withSelfRel();
        List<TeacherDTO> studentDTO = DTOBuilder.buildDtoListForCollection(teacherList, TeacherDTO.class, link);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/teacher/{teacher_id}")
    public ResponseEntity<TeacherDTO> getTeacher(@PathVariable Long teacher_id) throws NoSuchBookException {
        Teacher teacher = teacherService.getTeacher(teacher_id);
        Link link = linkTo(methodOn(TeacherController.class).getTeacher(teacher_id)).withSelfRel();
        TeacherDTO teacherDTO = DTOBuilder.buildDtoForEntity(teacher, TeacherDTO.class, link);
        return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/teacher")
    public ResponseEntity<List<TeacherDTO>> getAllTeachers()  {
        List<Teacher> teacherList = teacherService.getAllTeachers();
        Link link = linkTo(methodOn(TeacherController.class).getAllTeachers()).withSelfRel();
        List<TeacherDTO> studentDTO = DTOBuilder.buildDtoListForCollection(teacherList, TeacherDTO.class, link);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/teacher")
    public  ResponseEntity<TeacherDTO> addTeacher(@RequestBody Teacher newTeacher) throws NoSuchBookException {
        teacherService.createTeacher(newTeacher);
        Link link = linkTo(methodOn(TeacherController.class).getTeacher((long) newTeacher.getId())).withSelfRel();
        TeacherDTO teacherDTO = DTOBuilder.buildDtoForEntity(newTeacher,TeacherDTO.class, link);
        return new ResponseEntity<>(teacherDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/teacher/{teacher_id}")
    public  ResponseEntity<TeacherDTO> updateTeacher(@RequestBody Teacher uTeacher, @PathVariable Long teacher_id) throws NoSuchBookException {
        teacherService.updateTeacher(uTeacher,teacher_id);
        Teacher teacher = teacherService.getTeacher(teacher_id);
        Link link = linkTo(methodOn(TeacherController.class).getTeacher(teacher_id)).withSelfRel();
        TeacherDTO teacherDTO = DTOBuilder.buildDtoForEntity(teacher,TeacherDTO.class, link);
        return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/teacher/{teacher_id}")
    public  ResponseEntity deleteTeacher(@PathVariable Long teacher_id) throws ExistsPersonForBookException, NoSuchBookException {
        teacherService.deleteTeacher(teacher_id);
        return new ResponseEntity(HttpStatus.OK);
    }

}
