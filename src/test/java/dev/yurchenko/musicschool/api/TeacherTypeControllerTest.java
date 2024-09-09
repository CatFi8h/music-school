package dev.yurchenko.musicschool.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import dev.yurchenko.musicschool.api.model.request.TeacherTypeRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherTypeCreatedResponseDto;
import dev.yurchenko.musicschool.api.model.response.TeacherTypeResponseDto;
import dev.yurchenko.musicschool.service.impl.TeacherTypeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TeacherTypeController.class)
class TeacherTypeControllerTest {
	@MockBean
	private TeacherTypeServiceImpl teacherTypeService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test()
	public void testCreateTeacherType() throws Exception {
		
		TeacherTypeRequestDto teacherRequestDto = new TeacherTypeRequestDto("Type name");
		long teacherTypeId = 1L;
		when(teacherTypeService.createTeacherType(any())).thenReturn(new TeacherTypeCreatedResponseDto(teacherTypeId));
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String str = objectWriter.writeValueAsString(teacherRequestDto);
		
		mockMvc.perform(post("/teacher/type")
				                .contentType(MediaType.APPLICATION_JSON)
				                .content(str))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id").value(teacherTypeId));
	}
	
	@Test
	public void testGetAllTeacherTypes() throws Exception {
		when(teacherTypeService.getAllTeacherTypes(any(Pageable.class)))
				.thenReturn(new PageImpl<>(Collections.singletonList(getTeacherTypeResponseDto())));
		
		mockMvc.perform(get("/teacher/types")
				                .contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content[0].id").value(getTeacherTypeResponseDto().getId()))
				.andExpect(jsonPath("$.content[0].name").value(getTeacherTypeResponseDto().getName()));
	}
	
	private TeacherTypeResponseDto getTeacherTypeResponseDto() {
		return new TeacherTypeResponseDto(1L, "TypeName");
	}
}