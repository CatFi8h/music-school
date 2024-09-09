package dev.yurchenko.musicschool.service;

import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface TeacherService {
	Long createTeacher(TeacherRequestDto requestDto);
	
	Page<TeacherResponseDto> getAllTeachers(Pageable pageable);
	
	Optional<TeacherResponseDto> getTeacherById(Long id);
	
	Long updateTeacher(Long id, TeacherRequestDto requestDto);
}
