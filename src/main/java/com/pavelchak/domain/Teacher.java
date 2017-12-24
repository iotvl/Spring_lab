package com.pavelchak.domain;

import com.pavelchak.DTO.EntityInterface;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "teacher", schema = "mydb", catalog = "")
public class Teacher implements EntityInterface {
    private int id;
    private String name;
    private String surname;
    private String subject;
    private List<Student> students;

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

    @Basic
    @Column(name = "Subject", nullable = false, length = 45)
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teacher that = (Teacher) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (surname != null ? !surname.equals(that.surname) : that.surname != null) return false;
        if (subject != null ? !subject.equals(that.subject) : that.subject != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        result = 31 * result + (subject != null ? subject.hashCode() : 0);
        return result;
    }

    @ManyToMany
    @JoinTable(name = "teacher_has_student", catalog = "", schema = "mydb", joinColumns = @JoinColumn(name = "Teacher_id", referencedColumnName = "id", nullable = false), inverseJoinColumns = @JoinColumn(name = "Student_id", referencedColumnName = "id", nullable = false))
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
