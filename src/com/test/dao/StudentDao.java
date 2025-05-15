package com.test.dao;

import com.test.beana.Students;
import java.util.List;

public interface StudentDao {
    void addStudent(Students student);
    Students getStudentById(int uid);
    List<Students> getAllStudents();
    void updateStudent(Students student);
    void deleteStudent(int uid);
}
