package dev.yurchenko.musicschool.service.impl;


import dev.yurchenko.musicschool.api.model.request.TeacherTypeRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherTypeCreatedResponseDto;
import dev.yurchenko.musicschool.api.model.response.TeacherTypeResponseDto;
import dev.yurchenko.musicschool.repository.TeacherTypeRepository;
import dev.yurchenko.musicschool.repository.entities.TeacherTypeEntity;
import dev.yurchenko.musicschool.service.TeacherTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeacherTypeServiceImpl implements TeacherTypeService {
	
	private final TeacherTypeRepository teacherTypeRepository;
	
	@Override
	public TeacherTypeCreatedResponseDto createTeacherType(TeacherTypeRequestDto requestDto) {
		TeacherTypeEntity typeEntity = new TeacherTypeEntity();
		typeEntity.setTypeName(requestDto.getTypeName());
		TeacherTypeEntity save = teacherTypeRepository.save(typeEntity);
		return new TeacherTypeCreatedResponseDto(save.getId());
	}
	@Override
	public TeacherTypeResponseDto getTeacherTypeById(Long id) {
		Optional<TeacherTypeEntity> typeById = teacherTypeRepository.findById(id);
		if (typeById.isPresent()) {
			TeacherTypeEntity teacherType = typeById.get();
			return TeacherTypeResponseDto
					       .builder()
					       .id(teacherType.getId())
					       .name(teacherType.getTypeName())
					       .build();
		}
		throw new IllegalArgumentException("Teacher type not found");
	}
	@Override
	public Page<TeacherTypeResponseDto> getAllTeacherTypes(Pageable pageable) {
		Page<TeacherTypeEntity> all = teacherTypeRepository.findAll(pageable);
		return all.map(e -> new TeacherTypeResponseDto(e.getId(), e.getTypeName()));
	}
	
	@Override
	public void deleteTeacherType(Long id) {
		teacherTypeRepository.deleteById(id);
	}
}
