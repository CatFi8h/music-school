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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
		
		TeacherRequestDto teacherRequestDto = new TeacherRequestDto("name", "surname", "vocal", "email@email.com", "12353243");
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
				.thenReturn(new PageImpl(Collections.singletonList(getTeacherResponseDto())));
		
		mockMvc.perform(get("/teachers")
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].id").value(getTeacherResponseDto().getId()))
				.andExpect(jsonPath("$.content[0].name").value(getTeacherResponseDto().getName()))
				.andExpect(jsonPath("$.content[0].surname").value(getTeacherResponseDto().getSurname()))
				.andExpect(jsonPath("$.content[0].email").value(getTeacherResponseDto().getEmail()))
				.andExpect(jsonPath("$.content[0].phone").value(getTeacherResponseDto().getPhone()))
				.andExpect(jsonPath("$.content[0].type").value(getTeacherResponseDto().getType()));
		
		
	}
	
	public TeacherResponseDto getTeacherResponseDto() {
		TeacherResponseDto teacherResponseDto = new TeacherResponseDto(1L,
				"name",
				"surname",
				"Type name",
				"email@email.com",
				"123455");
				
		return teacherResponseDto;
	}
}