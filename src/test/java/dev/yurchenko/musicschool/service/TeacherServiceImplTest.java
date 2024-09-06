package dev.yurchenko.musicschool.service;

import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import dev.yurchenko.musicschool.repository.TeacherRepository;
import dev.yurchenko.musicschool.repository.entities.TeacherEntity;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class TeacherServiceImplTest {
	
	@Autowired
	private TeacherService teacherService;
	@MockBean
	private TeacherRepository teacherRepository;
	
	@Test
	public void testServiceCreateTeacher() {
		TeacherRequestDto teacherRequestDto = new TeacherRequestDto("name", "surname", "vocal", "email@email.com", "12353243");
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setId(1L);
		when(teacherRepository.save(ArgumentMatchers.any()))
				.thenReturn(teacherEntity);
		
		Long teacherId = teacherService.createTeacher(teacherRequestDto);
		
		assertNotNull(teacherId);
	}
}