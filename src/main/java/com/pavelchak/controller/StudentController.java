package com.pavelchak.controller;

import com.pavelchak.DTO.DTOBuilder;
import com.pavelchak.DTO.impl.StudentDTO;
import com.pavelchak.domain.Student;
import com.pavelchak.exceptions.*;
import com.pavelchak.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping(value = "/api/student/group/{group_id}")
    public ResponseEntity<List<StudentDTO>> getStudentsByGroupId(@PathVariable Long group_id) throws NoSuchCityException, NoSuchPersonException {
        List<Student> studentList = studentService.getStudentbyGroupId(group_id);

        Link link = linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel();

        List<StudentDTO> studentDTO = DTOBuilder.buildDtoListForCollection(studentList, StudentDTO.class, link);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/student/{student_id}")
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long student_id) throws NoSuchPersonException {
        Student Student = studentService.getStudent(student_id);
        Link link = linkTo(methodOn(StudentController.class).getStudent(student_id)).withSelfRel();
        StudentDTO studentDTO = DTOBuilder.buildDtoForEntity(Student, StudentDTO.class, link);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/api/student")
    public ResponseEntity<List<StudentDTO>> getAllStudents() {
        List<Student> studentList = studentService.getAllStudents();
        Link link = linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel();
        List<StudentDTO> cities = DTOBuilder.buildDtoListForCollection(studentList, StudentDTO.class, link);
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

    @GetMapping(value = "/api/student/teacher/{teacher_id}")
    public ResponseEntity<List<StudentDTO>> getStudentsByTeacherId(@PathVariable Long teacher_id) throws NoSuchBookException {
        Set<Student> studentList = studentService.getStudentsByTeacherId(teacher_id);
        Link link = linkTo(methodOn(StudentController.class).getAllStudents()).withSelfRel();
        List<StudentDTO> studentDTO = DTOBuilder.buildDtoListForCollection(studentList, StudentDTO.class, link);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/api/student/group/{group_id}")
    public  ResponseEntity<StudentDTO> addStudent(@RequestBody Student newStudent, @PathVariable Long group_id)
            throws NoSuchCityException, NoSuchPersonException {
        studentService.createStudent(newStudent, group_id);
        Link link = linkTo(methodOn(StudentController.class).getStudent((long) newStudent.getId())).withSelfRel();
        StudentDTO studentDTO = DTOBuilder.buildDtoForEntity(newStudent,StudentDTO.class, link);
        return new ResponseEntity<>(studentDTO, HttpStatus.CREATED);
    }

    @PutMapping(value = "/api/student/{student_id}/group/{group_id}")
    public  ResponseEntity<StudentDTO> updateStudent(@RequestBody Student uStudent,
                                                    @PathVariable Long student_id, @PathVariable Long group_id)
            throws NoSuchCityException, NoSuchPersonException {
        studentService.updateStudent(uStudent, student_id, group_id);
        Student Student = studentService.getStudent(student_id);
        Link link = linkTo(methodOn(StudentController.class).getStudent(student_id)).withSelfRel();
        StudentDTO studentDTO = DTOBuilder.buildDtoForEntity(Student,StudentDTO.class, link);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/student/{student_id}")
    public  ResponseEntity deleteStudent(@PathVariable Long student_id) throws NoSuchPersonException, ExistsBooksForPersonException {
        studentService.deleteStudent(student_id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(value = "/api/student/{student_id}/teacher/{teacher_id}")
    public  ResponseEntity<StudentDTO> addTeacherForStudent(@PathVariable Long student_id, @PathVariable Long teacher_id)
            throws NoSuchPersonException, NoSuchBookException, AlreadyExistsBookInPersonException, BookAbsentException {
        studentService.addTeacherForStudent(student_id,teacher_id);
        Student Student = studentService.getStudent(student_id);
        Link link = linkTo(methodOn(StudentController.class).getStudent(student_id)).withSelfRel();
        StudentDTO studentDTO = DTOBuilder.buildDtoForEntity(Student,StudentDTO.class, link);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @DeleteMapping(value = "/api/student/{student_id}/teacher/{teacher_id}")
    public  ResponseEntity<StudentDTO> removeTeacherForStudent(@PathVariable Long student_id, @PathVariable Long teacher_id)
            throws NoSuchPersonException, NoSuchBookException, PersonHasNotBookException {
        studentService.removeTeacherForStudent(student_id,teacher_id);
        Student Student = studentService.getStudent(student_id);
        Link link = linkTo(methodOn(StudentController.class).getStudent(student_id)).withSelfRel();
        StudentDTO studentDTO = DTOBuilder.buildDtoForEntity(Student,StudentDTO.class, link);
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

}
