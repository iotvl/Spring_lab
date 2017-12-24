package com.pavelchak.domain;

import com.pavelchak.DTO.EntityInterface;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "student", schema = "mydb", catalog = "")
public class Student implements EntityInterface {
    private int id;
    private String name;
    private String surname;
    private Group group;
    private List<Teacher> teachers;

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public Student() {
    }

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "Surname", nullable = false, length = 45)
    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student that = (Student) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id", nullable = false)
    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    @ManyToMany(mappedBy = "students")
    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }


    public void addTeacherEntity(Teacher teacher) {
        if(!getTeachers().contains(teacher)){
            getTeachers().add(teacher);
        }
        if(!teacher.getStudents().contains(this)){
            teacher.getStudents().add(this);
        }
    }
}
