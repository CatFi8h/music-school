package dev.yurchenko.musicschool.service;

import dev.yurchenko.musicschool.api.model.request.CreateStudentRequestDto;
import dev.yurchenko.musicschool.api.model.response.StudentProcessedIdResponseDto;
import dev.yurchenko.musicschool.api.model.response.StudentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {
	
	
	StudentProcessedIdResponseDto createStudent(CreateStudentRequestDto requestDto);
	
	Page<StudentResponseDto> getAllStudents(Pageable pageable);
	
	StudentResponseDto getStudentById(Long id);
	
	StudentProcessedIdResponseDto updateStudent(Long id, CreateStudentRequestDto requestDto);
	
	void removeStudent(Long id);
}
