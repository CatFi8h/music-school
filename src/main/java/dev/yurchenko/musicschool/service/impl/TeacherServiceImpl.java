package dev.yurchenko.musicschool.service.impl;

import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherResponseDto;
import dev.yurchenko.musicschool.repository.TeacherRepository;
import dev.yurchenko.musicschool.repository.TeacherTypeRepository;
import dev.yurchenko.musicschool.repository.entities.TeacherEntity;
import dev.yurchenko.musicschool.repository.entities.TeacherTypeEntity;
import dev.yurchenko.musicschool.service.TeacherService;
import dev.yurchenko.musicschool.service.mapper.TeacherMapper;
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
	private final TeacherMapper teacherMapper;
	
	@Override
	public Long createTeacher(TeacherRequestDto requestDto) {
		TeacherEntity entity = teacherMapper.mapTeacherRequestDtoToEntity(requestDto);
		java.util.Date timeNow = Date.from(Instant.now());
		entity.setCreatedAt(new Date(timeNow.getTime()));
		entity.setUpdatedAt(new Date(timeNow.getTime()));
		Optional<TeacherTypeEntity> typeOpt = teacherTypeRepository.findById(requestDto.getType());
		if (typeOpt.isEmpty()) {
			throw new IllegalArgumentException("Teacher type not found");
		}
		entity.setType(typeOpt.get());
		entity.setIsAdmin(false);
		TeacherEntity save = teacherRepository.save(entity);
		
		return save.getId();
	}
	
	@Override
	public Page<TeacherResponseDto> getAllTeachers(Pageable pageable) {
		return teacherRepository.findAll(pageable)
				       .map(teacherMapper::mapEntityToTeacherResponseDto);
	}
	
	@Override
	public Optional<TeacherResponseDto> getTeacherById(Long id) {
		return teacherRepository.findById(id)
				       .map(teacherMapper::mapEntityToTeacherResponseDto);
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
	
	
}
