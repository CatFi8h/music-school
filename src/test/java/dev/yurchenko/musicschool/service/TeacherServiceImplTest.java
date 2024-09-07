package dev.yurchenko.musicschool.service;

import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherResponseDto;
import dev.yurchenko.musicschool.repository.TeacherRepository;
import dev.yurchenko.musicschool.repository.entities.TeacherEntity;
import dev.yurchenko.musicschool.repository.entities.TeacherTypeEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
		when(teacherRepository.save(any(TeacherEntity.class)))
				.thenReturn(teacherEntity);
		
		Long teacherId = teacherService.createTeacher(teacherRequestDto);
		
		assertNotNull(teacherId);
	}
	
	@Test
	void testGetAllTeachers() {
		TeacherRequestDto teacherReqDto = new TeacherRequestDto("name", "surname", "vocal", "email@email.com", "12353243");
		
		ArrayList<TeacherEntity> teacherEntities = new ArrayList<>();
		teacherEntities.add(getTeacherEntity());
		when(teacherRepository.findAll(any(Pageable.class)))
				.thenReturn(new PageImpl<>(teacherEntities));
		PageRequest pageRequest = PageRequest.of(1, 10);
		Page<TeacherResponseDto> teachers = teacherService.getAllTeachers(pageRequest);
		assertNotNull(teachers);
		assertNotNull(teachers.getContent());
	}
	
	@Test
	public void testGetTeacherById() {
		TeacherEntity teacherEntity = getTeacherEntity();
		when(teacherRepository.findById(anyLong()))
				.thenReturn(Optional.of(teacherEntity));
		
		Optional<TeacherResponseDto> teacherById = teacherService.getTeacherById(teacherEntity.getId());
		assertTrue(teacherById.isPresent());
		TeacherResponseDto teacherResponseDto = teacherById.get();
		assertEquals(teacherEntity.getId(), teacherResponseDto.getId());
		assertEquals(teacherEntity.getName(), teacherResponseDto.getName());
		assertEquals(teacherEntity.getSurname(), teacherResponseDto.getSurname());
		assertEquals(teacherEntity.getEmail(), teacherResponseDto.getEmail());
		assertEquals(teacherEntity.getPhone(), teacherResponseDto.getPhone());
		assertEquals(teacherEntity.getType().getName(), teacherResponseDto.getType());
	}
	
	private TeacherTypeEntity getTeacherTypeEntity() {
		TeacherTypeEntity type = new TeacherTypeEntity();
		type.setId(1L);
		type.setName("Type_name");
		return type;
	}
	
	private TeacherEntity getTeacherEntity() {
		TeacherEntity teacher = new TeacherEntity();
		teacher.setId(1L);
		teacher.setName("name");
		teacher.setSurname("surname");
		teacher.setEmail("email@email.com");
		teacher.setType(getTeacherTypeEntity());
		teacher.setPhone("12353243");
		return teacher;
	}
	
}