package dev.yurchenko.musicschool.api;

import dev.yurchenko.musicschool.api.model.request.CreateStudentRequestDto;
import dev.yurchenko.musicschool.api.model.response.StudentProcessedIdResponseDto;
import dev.yurchenko.musicschool.api.model.response.StudentResponseDto;
import dev.yurchenko.musicschool.service.StudentService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class StudentController {
	
	private final StudentService studentService;
	
	@PostMapping("/student")
	public ResponseEntity<?> createStudent(@RequestBody @Valid CreateStudentRequestDto createStudentRequestDto) {
		StudentProcessedIdResponseDto student = studentService.createStudent(createStudentRequestDto);
		return ResponseEntity.ok(student);
	}
	
	@GetMapping("/students")
	public ResponseEntity<?> getAllStudents(Pageable pageable) {
		Page<StudentResponseDto> allStudents = studentService.getAllStudents(pageable);
		return ResponseEntity.ok(allStudents);
	}
	
	@GetMapping("/student/{id}")
	public ResponseEntity<?> getStudentById(@PathVariable Long id) {
		StudentResponseDto studentById = studentService.getStudentById(id);
		return ResponseEntity.ok(studentById);
	}
}
