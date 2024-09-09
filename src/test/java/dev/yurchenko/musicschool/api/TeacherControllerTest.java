package dev.yurchenko.musicschool.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherResponseDto;
import dev.yurchenko.musicschool.service.TeacherServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TeacherController.class)
class TeacherControllerTest {
	
	@MockBean
	private TeacherServiceImpl teacherService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test()
	public void testCreateTeacher() throws Exception {
		
		TeacherRequestDto teacherRequestDto = new TeacherRequestDto("name", "surname", 1L, "email@email.com", "12353243");
		long teacherId = 1L;
		when(teacherService.createTeacher(any())).thenReturn(teacherId);
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String str = objectWriter.writeValueAsString(teacherRequestDto);
		
		mockMvc.perform(post("/teacher")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(str))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(teacherId));
	}
	
	@Test()
	public void testGetAllTeachers() throws Exception {
		
		when(teacherService.getAllTeachers(any()))
				.thenReturn(new PageImpl<>(Collections.singletonList(getTeacherResponseDto())));
		
		mockMvc.perform(get("/teachers")
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].id").value(getTeacherResponseDto().getId()))
				.andExpect(jsonPath("$.content[0].firstName").value(getTeacherResponseDto().getFirstName()))
				.andExpect(jsonPath("$.content[0].lastName").value(getTeacherResponseDto().getLastName()))
				.andExpect(jsonPath("$.content[0].email").value(getTeacherResponseDto().getEmail()))
				.andExpect(jsonPath("$.content[0].phone").value(getTeacherResponseDto().getPhone()))
				.andExpect(jsonPath("$.content[0].type").value(getTeacherResponseDto().getType()));
		
		
	}
	
	@Test()
	public void testGetTeacherByID() throws Exception {
		
		when(teacherService.getTeacherById(anyLong()))
				.thenReturn(Optional.of(getTeacherResponseDto()));
		
		mockMvc.perform(get("/teacher/{id}", getTeacherResponseDto().getId())
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(getTeacherResponseDto().getId()))
				.andExpect(jsonPath("$.firstName").value(getTeacherResponseDto().getFirstName()))
				.andExpect(jsonPath("$.lastName").value(getTeacherResponseDto().getLastName()))
				.andExpect(jsonPath("$.email").value(getTeacherResponseDto().getEmail()))
				.andExpect(jsonPath("$.phone").value(getTeacherResponseDto().getPhone()))
				.andExpect(jsonPath("$.type").value(getTeacherResponseDto().getType()));
	}
	
	@Test
	public void testUpdateTeacher() throws Exception {
		TeacherRequestDto teacherRequestDto = new TeacherRequestDto("name", "surname", 1L, "email@email.com", "12353243");
		long teacherId = 1L;
		
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String str = objectWriter.writeValueAsString(teacherRequestDto);
	
		when(teacherService.updateTeacher(anyLong(), any())).thenReturn(teacherId);
		
		mockMvc.perform(put("/teacher/{id}", getTeacherResponseDto().getId())
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(str))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(getTeacherResponseDto().getId()));
	 
	}
	
	public TeacherResponseDto getTeacherResponseDto() {
		
		return new TeacherResponseDto(1L,
				"firstName",
				"lastName",
				"Type name",
				"email@email.com",
				"123455");
	}
}