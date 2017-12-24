package com.pavelchak.service;

import com.pavelchak.Repository.StudentRepository;
import com.pavelchak.Repository.TeacherRepository;
import com.pavelchak.domain.Student;
import com.pavelchak.domain.Teacher;
import com.pavelchak.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@Service
public class TeacherService {
    @Autowired
    TeacherRepository teacherRepository;

    @Autowired
    StudentRepository studentRepository;

    public Set<Teacher> getTeachersByStudentId(Long person_id) throws NoSuchPersonException {
//        Student Student = studentRepository.findOne(person_id);//1.5.9
        Student Student = studentRepository.findById(person_id).get();//2.0.0.M7
        if (Student == null) throw new NoSuchPersonException();
        return (Set<Teacher>) Student.getTeachers();
    }

    public Teacher getTeacher(Long teacher_id) throws NoSuchBookException {
//        Teacher teacher = teacherRepository.findOne(teacher_id);//1.5.9
        Teacher teacher = teacherRepository.findById(teacher_id).get();//2.0.0.M7
        if (teacher == null) throw new NoSuchBookException();
        return teacher;
    }

    public List<Teacher> getAllTeachers() {
        return teacherRepository.findAll();
    }

    @Transactional
    public void createTeacher(Teacher teacher) {
        teacherRepository.save(teacher);
    }

    @Transactional
    public void updateTeacher(Teacher uTeacher, Long teacher_id) throws NoSuchBookException {
//        Teacher teacher = teacherRepository.findOne(teacher_id);//1.5.9
        Teacher teacher = teacherRepository.findById(teacher_id).get();//2.0.0.M7
        if (teacher == null) throw new NoSuchBookException();
        //update
        teacher.setName(uTeacher.getName());
        teacher.setSurname(uTeacher.getSurname());
        teacher.setSubject(uTeacher.getSubject());
    }

    @Transactional
    public void deleteTeacher(Long teacher_id) throws NoSuchBookException, ExistsPersonForBookException {
//        Teacher teacher = teacherRepository.findOne(teacher_id);//1.5.9
        Teacher teacher = teacherRepository.findById(teacher_id).get();//2.0.0.M7

        if (teacher == null) throw new NoSuchBookException();
        if (teacher.getStudents().size() != 0) throw new ExistsPersonForBookException();
        teacherRepository.delete(teacher);
    }

}
