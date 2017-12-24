package com.pavelchak.service;

import com.pavelchak.Repository.GroupRepository;
import com.pavelchak.Repository.StudentRepository;
import com.pavelchak.domain.Group;
import com.pavelchak.exceptions.ExistsPersonsForCityException;
import com.pavelchak.exceptions.NoSuchCityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class GroupService {
    @Autowired
    GroupRepository groupRepository;
    private boolean ascending;

    @Autowired
    StudentRepository studentRepository;

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public Group getGroup(Long group_id) throws NoSuchCityException {
//        Group group =groupRepository.findOne(group_id);//1.5.9
        Group group = groupRepository.findById(group_id).get();//2.0.0.M7
        if (group == null) throw new NoSuchCityException();
        return group;
    }

    @Transactional
    public void createGroup(Group group) {
        groupRepository.save(group);
    }

    @Transactional
    public void updateGroup(Group uGroup, Long group_id) throws NoSuchCityException {
//        Group group = groupRepository.findOne(group_id);//1.5.9
        Group group = groupRepository.findById(group_id).get();//2.0.0.M7

        if (group == null) throw new NoSuchCityException();
        group.setName(uGroup.getName());
        groupRepository.save(group);
    }

    @Transactional
    public void deleteGroup(Long group_id) throws NoSuchCityException, ExistsPersonsForCityException {
//        Group group = groupRepository.findOne(group_id);//1.5.9
        Group group = groupRepository.findById(group_id).get();//2.0.0.M7
        if (group == null) throw new NoSuchCityException();
        if (group.getStudents().size() != 0) throw new ExistsPersonsForCityException();
        groupRepository.delete(group);
    }

}
