package com.test.controller;

import com.test.beana.Students;
import com.test.service.StudentService;
import com.test.service.StudentServiceImpl;
import com.test.tools.JDBCTools;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {
    private StudentService studentService = new StudentServiceImpl();
    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {
        ServletContext servletContext = this.getServletContext();
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode("HTML");
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            super.service(req, resp);
        } finally {
            try {
                JDBCTools.freeConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if (action == null) action = "list";

        switch (action) {
            case "list":
                listStudents(request, response);
                break;
            case "new":
                showAddStudentForm(request, response);
                break;
            case "edit":
                editStudent(request, response);
                break;
            case "delete":
                deleteStudent(request, response);
                break;
            case "search":
                searchStudents(request, response);
                break;
            default:
                listStudents(request, response);
                break;
        }
    }

    private void listStudents(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Students> students = studentService.getAllStudents();
        request.setAttribute("students", students);
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
        templateEngine.process("showallstudents", context, response.getWriter());
    }

    private void showAddStudentForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
        templateEngine.process("addstudent", context, response.getWriter());
    }

    private void editStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int uid = Integer.parseInt(request.getParameter("uid"));
        Students student = studentService.getStudentById(uid);
        if (student == null) {
            response.sendRedirect("students?action=list");
            return;
        }
        request.setAttribute("student", student);
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
        templateEngine.process("editstudent", context, response.getWriter());
    }

    private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int uid = Integer.parseInt(request.getParameter("uid"));
        studentService.deleteStudent(uid);
        response.sendRedirect("students?action=list");
    }

    private void searchStudents(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String keyword = request.getParameter("keyword");
        List<Students> allStudents = studentService.getAllStudents();
        List<Students> result;
        if (keyword == null || keyword.trim().isEmpty()) {
            result = allStudents;
        } else {
            String lower = keyword.trim().toLowerCase();
            result = allStudents.stream()
                    .filter(s -> s.getUname() != null && s.getUname().toLowerCase().contains(lower))
                    .toList();
        }
        request.setAttribute("students", result);
        WebContext context = new WebContext(request, response, getServletContext(), request.getLocale());
        templateEngine.process("showallstudents", context, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");
        if ("update".equals(action)) {
            updateStudent(request, response);
        } else if ("add".equals(action)) {
            addStudent(request, response);
        }
    }

    private void addStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uname = request.getParameter("uname");
        String gender = request.getParameter("gender");
        int uage = Integer.parseInt(request.getParameter("uage"));
        Students student = new Students(0, uname, gender, uage);
        studentService.addStudent(student);
        response.sendRedirect("students?action=list");
    }

    private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int uid = Integer.parseInt(request.getParameter("uid"));
        String uname = request.getParameter("uname");
        String gender = request.getParameter("gender");
        int uage = Integer.parseInt(request.getParameter("uage"));
        Students student = new Students(uid, uname, gender, uage);
        studentService.updateStudent(student);
        response.sendRedirect("students?action=list");
    }
}
