package com.pavelchak.service;

import com.pavelchak.Repository.*;
import com.pavelchak.domain.*;
import com.pavelchak.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    TeacherRepository teacherRepository;

    public List<Student> getStudentbyGroupId(Long group_id) throws NoSuchCityException {
//        Group group = groupRepository.findOne(group_id);//1.5.9
        Group group = groupRepository.findById(group_id).get();//2.0.0.M7
        if (group == null) throw new NoSuchCityException();
        return (List<Student>) group.getStudents();
    }

    public Student getStudent(Long student_id) throws NoSuchPersonException {
//        Student Student = studentRepository.findOne(student_id);//1.5.9
        Student Student = studentRepository.findById(student_id).get();//2.0.0.M7
        if (Student == null) throw new NoSuchPersonException();
        return Student;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Set<Student> getStudentsByTeacherId(Long teacher_id) throws NoSuchBookException {
//        Teacher teacher = teacherRepository.findOne(teacher_id);//1.5.9
        Teacher teacher = teacherRepository.findById(teacher_id).get();//2.0.0.M7
        if (teacher == null) throw new NoSuchBookException();
        return (Set<Student>) teacher.getStudents();
    }

    @Transactional
    public void createStudent(Student Student, Long group_id) throws NoSuchCityException {
        if (group_id > 0) {
//            Group group = groupRepository.findOne(group_id);//1.5.9
            Group group = groupRepository.findById(group_id).get();//2.0.0.M7

            if (group == null) throw new NoSuchCityException();
            Student.setGroup(group);
        }
        studentRepository.save(Student);
    }

    @Transactional
    public void updateStudent(Student uStudent, Long student_id, Long group_id) throws NoSuchCityException, NoSuchPersonException {
//        Group group = groupRepository.findOne(group_id);//1.5.9
        Group group = groupRepository.findById(group_id).get();//2.0.0.M7

        if (group_id > 0) {
            if (group == null) throw new NoSuchCityException();
        }
//        Student Student = studentRepository.findOne(student_id);//1.5.9
        Student Student = studentRepository.findById(student_id).get();//2.0.0.M7
        if (Student == null) throw new NoSuchPersonException();
        //update
        Student.setSurname(uStudent.getSurname());
        Student.setName(uStudent.getName());
        if (group_id > 0) Student.setGroup(group);
        else Student.setGroup(null);
        studentRepository.save(Student);
    }

    @Transactional
    public void deleteStudent(Long student_id) throws NoSuchPersonException, ExistsBooksForPersonException {
//        Student Student = studentRepository.findOne(student_id);//1.5.9
        Student Student = studentRepository.findById(student_id).get();//2.0.0.M7
        if (Student == null) throw new NoSuchPersonException();
        if (Student.getTeachers().size() != 0) throw new ExistsBooksForPersonException();
        studentRepository.delete(Student);
    }

    @Transactional
    public void addTeacherForStudent(Long student_id, Long teacher_id)
            throws NoSuchPersonException, NoSuchBookException, AlreadyExistsBookInPersonException, BookAbsentException {
//        Student Student = studentRepository.findOne(student_id);//1.5.9
        Student Student = studentRepository.findById(student_id).get();//2.0.0.M7
        if (Student == null) throw new NoSuchPersonException();
//        Teacher teacher = teacherRepository.findOne(teacher_id);//1.5.9
        Teacher teacher = teacherRepository.findById(teacher_id).get();//2.0.0.M7
        if (teacher == null) throw new NoSuchBookException();
        if (Student.getTeachers().contains(teacher) == true) throw new AlreadyExistsBookInPersonException();
        Student.getTeachers().add(teacher);
        studentRepository.save(Student);
    }

    @Transactional
    public void removeTeacherForStudent(Long student_id, Long teacher_id)
            throws NoSuchPersonException, NoSuchBookException, PersonHasNotBookException {
//        Student Student = studentRepository.findOne(student_id);//1.5.9
        Student Student = studentRepository.findById(student_id).get();//2.0.0.M7
        if (Student == null) throw new NoSuchPersonException();
//        Teacher teacher = teacherRepository.findOne(teacher_id);//1.5.9
        Teacher teacher = teacherRepository.findById(teacher_id).get();//2.0.0.M7
        if (teacher == null) throw new NoSuchBookException();
        if (Student.getTeachers().contains(teacher) == false) throw new PersonHasNotBookException();
        Student.getTeachers().remove(teacher);
        studentRepository.save(Student);
    }


}
