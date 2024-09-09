package dev.yurchenko.musicschool.service;

import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherResponseDto;
import dev.yurchenko.musicschool.repository.TeacherRepository;
import dev.yurchenko.musicschool.repository.TeacherTypeRepository;
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
	@MockBean
	private TeacherTypeRepository teacherTypeRepository;
	
	@Test
	public void testServiceCreateTeacher() {
		TeacherRequestDto teacherRequestDto = new TeacherRequestDto("name", "surname", 1L, "email@email.com", "12353243");
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setId(1L);
		when(teacherRepository.save(any(TeacherEntity.class)))
				.thenReturn(teacherEntity);
		when(teacherTypeRepository.findById(anyLong()))
				.thenReturn(Optional.of(new TeacherTypeEntity()));
		
		Long teacherId = teacherService.createTeacher(teacherRequestDto);
		
		assertNotNull(teacherId);
	}
	
	@Test
	void testGetAllTeachers() {
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
		assertEquals(teacherEntity.getFirstName(), teacherResponseDto.getFirstName());
		assertEquals(teacherEntity.getLastName(), teacherResponseDto.getLastName());
		assertEquals(teacherEntity.getEmail(), teacherResponseDto.getEmail());
		assertEquals(teacherEntity.getPhone(), teacherResponseDto.getPhone());
		assertEquals(teacherEntity.getType().getTypeName(), teacherResponseDto.getType());
	}
	
	@Test
	public void testUpdateTeacherById() {
		TeacherRequestDto teacherRequestDto = new TeacherRequestDto("name", "surname", 1L, "email@email.com", "12353243");
		TeacherEntity teacherEntity = getTeacherEntity();
		
		when(teacherRepository.findById(anyLong()))
				.thenReturn(Optional.of(teacherEntity));
		when(teacherRepository.save(any(TeacherEntity.class)))
				.thenReturn(teacherEntity);
		when(teacherTypeRepository.findById(anyLong()))
				.thenReturn(Optional.of(getTeacherTypeEntity()));
		
		Long teacherId = teacherService.updateTeacher(1L, teacherRequestDto);
		assertNotNull(teacherId);
		
	}
	
	private TeacherTypeEntity getTeacherTypeEntity() {
		TeacherTypeEntity type = new TeacherTypeEntity();
		type.setId(1L);
		type.setTypeName("Type_name");
		return type;
	}
	
	private TeacherEntity getTeacherEntity() {
		TeacherEntity teacher = new TeacherEntity();
		teacher.setId(1L);
		teacher.setFirstName("name");
		teacher.setLastName("surname");
		teacher.setEmail("email@email.com");
		teacher.setType(getTeacherTypeEntity());
		teacher.setPhone("12353243");
		return teacher;
	}
	
}