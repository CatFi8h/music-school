package dev.yurchenko.musicschool.api;

import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherCreatedResponseDto;
import dev.yurchenko.musicschool.api.model.response.TeacherResponseDto;
import dev.yurchenko.musicschool.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


@RequiredArgsConstructor
@RestController()
public class TeacherController {
	
	private final TeacherService teacherService;
	
	@PostMapping("/teacher")
	public ResponseEntity<?> createNewTeacher(@Valid @RequestBody TeacherRequestDto requestDto) {
		
		Long teacherId = teacherService.createTeacher(requestDto);
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
				       .body(new TeacherCreatedResponseDto(teacherId));
	}
	
	@GetMapping("/teachers")
	public ResponseEntity<?> getAllTeachers(Pageable pageable) {
		Page<TeacherResponseDto> allTeachers = teacherService.getAllTeachers(pageable);
		return ResponseEntity.ok(allTeachers);
	}
	
	@GetMapping("/teacher/{id}")
	public ResponseEntity<?> getTeacher(@PathVariable Long id) {
		Optional<TeacherResponseDto> teacherById = teacherService.getTeacherById(id);
		if (teacherById.isPresent()) {
		
			return ResponseEntity.ok().body(teacherById.get());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/teacher/{id}")
	public ResponseEntity<?> updateTeacher(@PathVariable Long id, @RequestBody TeacherRequestDto requestDto) {
		Long teacherId = teacherService.updateTeacher(id, requestDto);
		return ResponseEntity.ok(new TeacherCreatedResponseDto(teacherId));
	}
	
}
