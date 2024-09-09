package dev.yurchenko.musicschool.service;

import dev.yurchenko.musicschool.api.model.request.TeacherTypeRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherTypeCreatedResponseDto;
import dev.yurchenko.musicschool.api.model.response.TeacherTypeResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TeacherTypeService {
	TeacherTypeCreatedResponseDto createTeacherType(TeacherTypeRequestDto requestDto);
	
	TeacherTypeResponseDto getTeacherTypeById(Long id);
	
	Page<TeacherTypeResponseDto> getAllTeacherTypes(Pageable pageable);
	
	void deleteTeacherType(Long id);
}
