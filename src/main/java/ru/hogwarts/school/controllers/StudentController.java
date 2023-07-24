package ru.hogwarts.school.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RequestMapping("/student")
@RestController
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @GetMapping("{studentId}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable long studentId) {
        Student student = studentService.findStudentById(studentId);
        if(student == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(student);
    }

    @PutMapping()
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{studentId}")
    public ResponseEntity<Void> deleteStudent(@PathVariable long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/age/{studentAge}")
    public ResponseEntity getStudentByAge(@PathVariable int studentAge) {
        Collection<Student> studentsByAge= studentService.getStudentByAge(studentAge);
        if(studentsByAge == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentsByAge);
    }

    @GetMapping()
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam int minAge, @RequestParam int maxAge) {
        Collection<Student> students = studentService.findByAgeBetween(minAge, maxAge);
        if(students == null) {
            return ResponseEntity.notFound() .build();
        }
        return ResponseEntity.ok(students);
    }

    @GetMapping("{studentId}/faculty")
    public Faculty findFacultyByStudent(@PathVariable long studentId){
        return studentService.findFacultyByStudent(studentId);
    }
}
