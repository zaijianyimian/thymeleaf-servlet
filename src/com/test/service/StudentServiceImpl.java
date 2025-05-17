package com.test.service;

import com.test.beana.Students;
import com.test.dao.StudentDao;
import com.test.dao.StudentDaoImpl;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private StudentDao studentDao = new StudentDaoImpl();

    @Override
    public void addStudent(Students student) {
        studentDao.addStudent(student);
    }

    @Override
    public Students getStudentById(int uid) {
        return studentDao.getStudentById(uid);
    }

    @Override
    public List<Students> getAllStudents() {
        return studentDao.getAllStudents();
    }

    @Override
    public void updateStudent(Students student) {
        studentDao.updateStudent(student);
    }

    @Override
    public void deleteStudent(int uid) {
        studentDao.deleteStudent(uid);
    }
}
