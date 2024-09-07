package dev.yurchenko.musicschool.service;

import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherResponseDto;
import dev.yurchenko.musicschool.repository.TeacherRepository;
import dev.yurchenko.musicschool.repository.entities.TeacherEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeacherServiceImpl implements TeacherService {
	
	private final TeacherRepository teacherRepository;
	
	@Override
	public Long createTeacher(TeacherRequestDto requestDto) {
		TeacherEntity entity = mapTeacherRequestDtoToEntity(requestDto);
		java.util.Date timeNow = Date.from(Instant.now());
		entity.setCreatedAt(new Date(timeNow.getTime()));
		entity.setUpdatedAt(new Date(timeNow.getTime()));
		TeacherEntity save = teacherRepository.save(entity);
		
		return save.getId();
	}
	
	@Override
	public Page<TeacherResponseDto> getAllTeachers(Pageable pageable) {
		return teacherRepository.findAll(pageable)
		.map(this::mapEntityToTeacherResponseDto);
	}
	
	@Override
	public Optional<TeacherResponseDto> getTeacherById(Long id) {
		return teacherRepository.findById(id)
		.map(this::mapEntityToTeacherResponseDto);
	}
	
	private TeacherEntity mapTeacherRequestDtoToEntity(TeacherRequestDto requestDto) {
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setName(requestDto.getName());
		teacherEntity.setSurname(requestDto.getSurname());
		teacherEntity.setEmail(requestDto.getEmail());
		teacherEntity.setPhone(requestDto.getPhone());
		teacherEntity.setIsAdmin(requestDto.getIsAdmin());
		return teacherEntity;
	}
	
	private TeacherResponseDto mapEntityToTeacherResponseDto(TeacherEntity teacherEntity) {
		return TeacherResponseDto.builder()
				.id(teacherEntity.getId())
				.name(teacherEntity.getName())
				.surname(teacherEntity.getSurname())
				.email(teacherEntity.getEmail())
				.phone(teacherEntity.getPhone())
				.type(teacherEntity.getType().getName())
				.build();
	}
}
