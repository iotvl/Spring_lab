package com.pavelchak.Repository;

import com.pavelchak.domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
