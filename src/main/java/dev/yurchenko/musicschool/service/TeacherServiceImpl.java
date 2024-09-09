package dev.yurchenko.musicschool.service;

import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherResponseDto;
import dev.yurchenko.musicschool.repository.TeacherRepository;
import dev.yurchenko.musicschool.repository.TeacherTypeRepository;
import dev.yurchenko.musicschool.repository.entities.TeacherEntity;
import dev.yurchenko.musicschool.repository.entities.TeacherTypeEntity;
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
	private final TeacherTypeRepository teacherTypeRepository;
	
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
	
	@Override
	public Long updateTeacher(Long id, TeacherRequestDto requestDto) {
		Optional<TeacherEntity> teacherOpt = teacherRepository.findById(id);
		if (teacherOpt.isPresent()) {
			TeacherEntity teacherEntity = teacherOpt.get();
			teacherEntity.setUpdatedAt(new Date(System.currentTimeMillis()));
			if (requestDto.getType() != null) {
				Optional<TeacherTypeEntity> typeOpt = teacherTypeRepository.findById(requestDto.getType());
				typeOpt.ifPresent(teacherEntity::setType);
			}
			if (requestDto.getFirstName() != null) {
				teacherEntity.setFirstName(requestDto.getFirstName());
			}
			if (requestDto.getLastName() != null) {
				teacherEntity.setLastName(requestDto.getLastName());
			}
			if (requestDto.getEmail() != null) {
				teacherEntity.setEmail(requestDto.getEmail());
			}
			if (requestDto.getPhone() != null) {
				teacherEntity.setPhone(requestDto.getPhone());
			}
			TeacherEntity saved = teacherRepository.save(teacherEntity);
			return saved.getId();
		}
		throw new IllegalArgumentException("Teacher with id " + id + " not found");
	}
	
	private TeacherEntity mapTeacherRequestDtoToEntity(TeacherRequestDto requestDto) {
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setFirstName(requestDto.getFirstName());
		teacherEntity.setLastName(requestDto.getLastName());
		teacherEntity.setEmail(requestDto.getEmail());
		teacherEntity.setPhone(requestDto.getPhone());
		teacherEntity.setIsAdmin(requestDto.getIsAdmin());
		return teacherEntity;
	}
	
	private TeacherResponseDto mapEntityToTeacherResponseDto(TeacherEntity teacherEntity) {
		return TeacherResponseDto.builder()
				       .id(teacherEntity.getId())
				       .firstName(teacherEntity.getFirstName())
				       .lastName(teacherEntity.getLastName())
				       .email(teacherEntity.getEmail())
				       .phone(teacherEntity.getPhone())
				       .type(teacherEntity.getType().getName())
				       .build();
	}
}
