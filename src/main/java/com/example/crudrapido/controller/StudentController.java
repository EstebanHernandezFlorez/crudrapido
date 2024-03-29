package com.example.crudrapido.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

import com.example.crudrapido.entity.Student;
import com.example.crudrapido.service.StudentService;

@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    // lista
    @GetMapping({ "/students", "/" })
    public String getAllStudents(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("students", studentService.getStudents());

        return "students"; // nos retorna al archivo students
    }

    // crear
    @GetMapping("/students/create")
    public String formCreateStudent(Model modelo) {
        Student student = new Student();
        modelo.addAttribute("student", student);
        return "crear_student";
    }

    @PostMapping("/students")
    public String saveStudent(@ModelAttribute("student") Student student) {
        studentService.saveOrUpdate(student);
        return "redirect:/students";
    }

    @GetMapping("/students/update/{id}")
    public String formUpdateStudent(@PathVariable Long id, Model modelo) {
        modelo.addAttribute("student", studentService.getStudent(id));
        return "editar_student";
    }

    // actualizar
    @PostMapping("/students/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute("student") Student student,
            Model modelo) {
        Student studentExistente = studentService.getStudent(id);
        studentExistente.setStudentId(id);
        studentExistente.setFirstName(student.getFirstName());
        studentExistente.setLastName(student.getLastName());
        studentExistente.setEmail(student.getEmail());

        studentService.saveOrUpdate(studentExistente);
        return "redirect:/students";
    }

    // eliminar
    @GetMapping("/students/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.delete(id);
        return "redirect:/students";
    }

    // buscar
    @PostMapping("/students/search")
    public String searchStudent(@ModelAttribute("student") Student student, Model model) {
        Student studentAlone = new Student();
        try {
            Student findStudent = studentService.getStudent(student.getStudentId());
            if (findStudent != studentAlone) {
                model.addAttribute("student", findStudent);
                return "students";
            } else {
                return "students";
            }
        } catch (Exception e) {
            // return ("Error: " + e);
            return "students";
        }
    }

}