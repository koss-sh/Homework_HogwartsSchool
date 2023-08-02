--Составить первый JOIN-запрос, чтобы получить информацию обо всех студентах
-- (достаточно получить только имя и возраст студента) школы Хогвартс вместе с названиями факультетов.

--Составить второй JOIN-запрос, чтобы получить только тех студентов,у которых есть аватарки.

select student.name, student.age
from student
left join faculty f on student.faculty_id = f.id

select student.name, student.age
from student
left join avatar a on student.id = a.student_id;