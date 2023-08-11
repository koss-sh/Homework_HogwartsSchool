package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;
    private final FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }


    public Student addStudent(Student student) {
        logger.info("Creating student: {}", student);
        return studentRepository.save(student);
    }

    public Student findStudentById(long id){
        logger.debug("Getting student with id: {}", id);
        return studentRepository.findById(id).get();
    }

    public Student editStudent(Student student) {
        logger.warn("Editing student: {}", student);
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        logger.warn("Deleting student with id: {}", id);
        studentRepository.deleteById(id);
    }

    public Collection<Student> getStudentByAge(int age) {
        logger.debug("Getting students with age: {}", age);
        return studentRepository.findAll().stream()
                .filter(student -> student.getAge() == age)
                .collect(Collectors.toList());
    }

    public List<Student> findByAgeBetween (int minAge, int maxAge) {
        logger.debug("Getting students with age from {} to {}", minAge, maxAge);
        return studentRepository.findByAgeBetween(minAge, maxAge);
    }

    public Faculty findFacultyByStudent(long studentId) {
        logger.debug("Getting faculty by student with id: {}", studentId);
        return studentRepository.findStudentById(studentId).getFaculty();
    }

    public Integer getNumberOfStudents() {
        logger.debug("Getting total students' number");
        return studentRepository.getNumberOfStudents();
    }

    public Double getAverageAge() {
        logger.debug("Getting students' average age");
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        logger.debug("Getting last 5 created students");
        return studentRepository.getLastFiveStudents();
    }

}