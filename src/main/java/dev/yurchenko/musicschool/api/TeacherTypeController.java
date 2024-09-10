package dev.yurchenko.musicschool.api;

import dev.yurchenko.musicschool.api.model.request.TeacherTypeRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherTypeCreatedResponseDto;
import dev.yurchenko.musicschool.service.TeacherTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/teacher")
public class TeacherTypeController {
	
	private final TeacherTypeService teacherTypeService;
	
	@GetMapping("/types")
	public ResponseEntity<?> getTeacherTypes(Pageable pageable) {
		return ResponseEntity.ok(teacherTypeService.getAllTeacherTypes(pageable));
	}
	
	@PostMapping("/type")
	public ResponseEntity<?> createTeacherType(@RequestBody @Valid TeacherTypeRequestDto teacherTypeRequestDto) {
		TeacherTypeCreatedResponseDto teacherTypeResponseDto = teacherTypeService.createTeacherType(teacherTypeRequestDto);
		return ResponseEntity.ok(teacherTypeResponseDto);
	}
}
