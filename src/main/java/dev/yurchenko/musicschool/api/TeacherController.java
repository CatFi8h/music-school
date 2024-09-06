package dev.yurchenko.musicschool.api;

import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import dev.yurchenko.musicschool.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController()
public class TeacherController {
	
	private final TeacherService teacherService;
	
	@PostMapping("/teacher")
	public ResponseEntity<?> createNewTeacher(@Valid @RequestBody TeacherRequestDto requestDto) {
		teacherService.createTeacher(requestDto);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/teachers")
	public ResponseEntity<?> getAllTeachers() {
		System.out.println("Teachers");
		return ResponseEntity.ok().build();
	}

}
