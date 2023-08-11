package ru.hogwarts.school;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controllers.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class SchoolApplicationFacultyControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @SpyBean
    private FacultyService facultyService;

    @SpyBean
    private StudentService studentService;

    @SpyBean
    private AvatarService avatarService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void createFacultyTest() throws Exception {
        Long id = 1L;
        String name = "w";
        String color = "white";
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));

    }

    @Test
    public void getFacultyInfo() throws Exception {
        Long id = 1L;
        String name = "w";
        String color = "white";
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.findById(ArgumentMatchers.any(Long.class))).thenReturn(Optional.of(faculty));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void editFacultyTest() throws Exception {
        Long id = 1L;
        String name = "w";
        String color = "white";
        JSONObject facultyObject = new JSONObject();
        facultyObject.put("name", name);
        facultyObject.put("color", color);

        Faculty faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColor(color);

        when(facultyRepository.save(ArgumentMatchers.any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    public void deleteFacultyTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void findFacultiesByColorTest() throws Exception {
        Faculty faculty1 = new Faculty(1, "w", "white");
        Faculty faculty2 = new Faculty(2, "b", "black");

        List<Faculty> list = List.of(faculty1, faculty2);

        when(facultyRepository.findByColorLike(anyString())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/color/white")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(faculty1.getId()))
                .andExpect(jsonPath("$.[0].name").value(faculty1.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty1.getColor()))
                .andExpect(jsonPath("$.[1].id").value(faculty2.getId()))
                .andExpect(jsonPath("$.[1].name").value(faculty2.getName()))
                .andExpect(jsonPath("$.[1].color").value(faculty2.getColor()));
    }

    @Test
    public void findByNameOrColorIgnoreCase() throws Exception {
        Faculty faculty1 = new Faculty(1, "w", "white");
        Faculty faculty2 = new Faculty(2, "wh", "white");

        List<Faculty> list = List.of(faculty1, faculty2);

        when(facultyRepository.findByNameIgnoreCaseOrColorIgnoreCase(anyString(), anyString())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty?nameOrColor=green")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(faculty1.getId()))
                .andExpect(jsonPath("$.[0].name").value(faculty1.getName()))
                .andExpect(jsonPath("$.[0].color").value(faculty1.getColor()))
                .andExpect(jsonPath("$.[1].id").value(faculty2.getId()))
                .andExpect(jsonPath("$.[1].name").value(faculty2.getName()))
                .andExpect(jsonPath("$.[1].color").value(faculty2.getColor()));
    }

    @Test
    public void findStudentsByFacultyTest() throws Exception {
        Faculty faculty1 = new Faculty(1, "w", "white");
        Faculty faculty2 = new Faculty(2, "b", "black");
        Student student1 = new Student(1, "Adam", 20, faculty1);
        Student student2 = new Student(2, "Eva", 20, faculty1);
        Student student3 = new Student(3, "Harry", 18, faculty2);

        List<Student> list = List.of(student1, student2);

        when(studentRepository.findAllByFaculty_Id(anyLong())).thenReturn(list);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/1/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(student1.getId()))
                .andExpect(jsonPath("$.[0].name").value(student1.getName()))
                .andExpect(jsonPath("$.[0].age").value(student1.getAge()))
                .andExpect(jsonPath("$.[1].id").value(student2.getId()))
                .andExpect(jsonPath("$.[1].name").value(student2.getName()))
                .andExpect(jsonPath("$.[1].age").value(student2.getAge()));
    }
}