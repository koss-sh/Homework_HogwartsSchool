package ru.hogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
public class FacultyService {

    private final static Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;
    private final StudentRepository studentRepository;


    public FacultyService(FacultyRepository facultyRepository, StudentRepository studentRepository) {
        this.facultyRepository = facultyRepository;
        this.studentRepository = studentRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Creating faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }
    public Faculty findFacultyById(long id){
        logger.debug("Getting faculty with id: {}", id);
        return facultyRepository.findById(id).get();
    }

    public Faculty editFaculty(Faculty faculty) {
        logger.warn("Editing faculty: {}", faculty);
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        logger.warn("Deleting faculty: {}", id);
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> getFacultiesByColor(String color) {
        logger.debug("Getting faculties with color: {}", color);
        return facultyRepository.findByColorLike(color);
    }

    public List<Faculty> findByNameOrColorIgnoreCase(String nameOrColor){
        logger.debug("Getting faculty, whose name or color is {}", nameOrColor);
        return facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(nameOrColor, nameOrColor);
    }

    public List<Student> findStudentsByFaculty(long facultyId){
        logger.debug("Getting all students of faculty: {}", facultyId);
        return studentRepository.findAllByFaculty_Id(facultyId);
    }

    public String getLongestName() {
        return facultyRepository.findAll().stream()
                .map(faculty -> faculty.getName())
                .max(Comparator.comparing(name -> name.length()))
                .get();
    }

    public Integer sum() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        long finish = System.currentTimeMillis();
        System.out.println("Executing time:" + (finish - start));
        return sum;
    }

    public Integer sum_parallel() {
        long start = System.currentTimeMillis();
        int sum = Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, Integer::sum);
        long finish = System.currentTimeMillis();
        System.out.println("Executing time with parallel stream:" + (finish - start));
        return sum;
    }

}