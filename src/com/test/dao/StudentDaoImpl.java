package com.test.dao;

import com.test.beana.Students;

import java.util.List;

public class StudentDaoImpl extends BaseDao implements StudentDao {

    @Override
    public void addStudent(Students student) {
        String sql = "INSERT INTO students(name, gender, age) VALUES(?,?,?)";
        update(sql, student.getUname(), student.getGender(), student.getUage());
    }

    @Override
    public Students getStudentById(int uid) {
        String sql = "SELECT id AS uid, name AS uname, gender, age AS uage FROM students WHERE id=?";
        return getBean(Students.class, sql, uid);
    }

    @Override
    public List<Students> getAllStudents() {
        String sql = "SELECT id AS uid, name AS uname, gender, age AS uage FROM students";
        return getList(Students.class, sql);
    }

    @Override
    public void updateStudent(Students student) {
        String sql = "UPDATE students SET name=?, gender=?, age=? WHERE id=?";
        update(sql, student.getUname(), student.getGender(), student.getUage(), student.getUid());
    }

    @Override
    public void deleteStudent(int uid) {
        String sql = "DELETE FROM students WHERE id=?";
        update(sql, uid);
    }
}
