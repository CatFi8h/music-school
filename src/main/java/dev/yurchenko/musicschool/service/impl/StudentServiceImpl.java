package dev.yurchenko.musicschool.service.impl;

import dev.yurchenko.musicschool.api.model.request.CreateStudentRequestDto;
import dev.yurchenko.musicschool.api.model.response.StudentProcessedIdResponseDto;
import dev.yurchenko.musicschool.api.model.response.StudentResponseDto;
import dev.yurchenko.musicschool.repository.StudentRepository;
import dev.yurchenko.musicschool.repository.entities.StudentEntity;
import dev.yurchenko.musicschool.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
	private final StudentRepository studentRepository;
	
	
	@Override
	public StudentProcessedIdResponseDto createStudent(CreateStudentRequestDto requestDto) {
		StudentEntity save = studentRepository.save(mapStudentCreateDtoToEntity(requestDto));
		return new StudentProcessedIdResponseDto(save.getId());
	}
	
	@Override
	public Page<StudentResponseDto> getAllStudents(Pageable pageable) {
		Page<StudentEntity> all = studentRepository.findAll(pageable);
		return all.map(this::mapStudentEntityToResponseDto);
	}
	
	@Override
	public StudentResponseDto getStudentById(Long id) {
		Optional<StudentEntity> studentEntity = studentRepository.findById(id);
		if (studentEntity.isPresent()) {
			return mapStudentEntityToResponseDto(studentEntity.get());
		}
		throw new IllegalArgumentException("Student with id " + id + " not found");
	}
	
	@Override
	public StudentProcessedIdResponseDto updateStudent(Long id, CreateStudentRequestDto requestDto) {
		Optional<StudentEntity> studentOpt = studentRepository.findById(id);
		studentOpt.ifPresent(student -> {
			updateEntityFromDto(requestDto, student);
			student.setUpdatedAt(Date.valueOf(LocalDate.now()));
			studentRepository.save(student);
		});
		if(studentOpt.isPresent()) {
			return new StudentProcessedIdResponseDto(studentOpt.get().getId());
		}
		throw new IllegalArgumentException("Student with id " + id + " not found");
	}
	
	@Override
	public void removeStudent(Long id) {
		studentRepository.deleteById(id);
	}
	
	private void updateEntityFromDto(CreateStudentRequestDto requestDto, StudentEntity student) {
		if (requestDto.getFirstName() != null) {
			student.setFirstName(requestDto.getFirstName());
		}
		if (requestDto.getLastName() != null) {
			student.setLastName(requestDto.getLastName());
		}
		if (requestDto.getEmail() != null) {
			student.setEmail(requestDto.getEmail());
		}
		if (requestDto.getPhone() != null) {
			student.setPhone(requestDto.getPhone());
		}
	}
	
	private StudentEntity mapStudentCreateDtoToEntity(CreateStudentRequestDto requestDto) {
		StudentEntity studentEntity = new StudentEntity();
		studentEntity.setFirstName(requestDto.getFirstName());
		studentEntity.setLastName(requestDto.getLastName());
		studentEntity.setEmail(requestDto.getEmail());
		studentEntity.setPhone(requestDto.getPhone());
		studentEntity.setCreatedAt(Date.valueOf(LocalDate.now()));
		studentEntity.setUpdatedAt(Date.valueOf(LocalDate.now()));
		return studentEntity;
	}
	
	private StudentResponseDto mapStudentEntityToResponseDto(StudentEntity studentEntity) {
		return StudentResponseDto.builder()
				       .id(studentEntity.getId())
				       .firstName(studentEntity.getFirstName())
				       .lastName(studentEntity.getLastName())
				       .email(studentEntity.getEmail())
				       .phone(studentEntity.getPhone())
				       .createdAt(studentEntity.getCreatedAt())
				       .updatedAt(studentEntity.getUpdatedAt())
				       .build();
	}
}
