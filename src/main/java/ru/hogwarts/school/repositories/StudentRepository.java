package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.hogwarts.school.model.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {


    List<Student> findByAgeBetween(int minAge, int maxAge);

    List<Student> findAllByFaculty_Id(long id);

    Student findStudentById(long id);

    @Query(value = "select count(*) from student", nativeQuery = true)
    Integer getNumberOfStudents();

    @Query(value = "select avg(age) from student", nativeQuery = true)
    Double getAverageAge();

    @Query(value = "select * from student order by id desc limit 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}
