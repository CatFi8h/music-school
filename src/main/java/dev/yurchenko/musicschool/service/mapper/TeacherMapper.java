package dev.yurchenko.musicschool.service.mapper;

import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import dev.yurchenko.musicschool.api.model.response.TeacherResponseDto;
import dev.yurchenko.musicschool.repository.entities.TeacherEntity;
import org.springframework.stereotype.Component;

@Component
public class TeacherMapper {
	
	public TeacherEntity mapTeacherRequestDtoToEntity(TeacherRequestDto requestDto) {
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setFirstName(requestDto.getFirstName());
		teacherEntity.setLastName(requestDto.getLastName());
		teacherEntity.setEmail(requestDto.getEmail());
		teacherEntity.setPhone(requestDto.getPhone());
		teacherEntity.setIsAdmin(requestDto.getIsAdmin());
		return teacherEntity;
	}
	
	public TeacherResponseDto mapEntityToTeacherResponseDto(TeacherEntity teacherEntity) {
		return TeacherResponseDto.builder()
				       .id(teacherEntity.getId())
				       .firstName(teacherEntity.getFirstName())
				       .lastName(teacherEntity.getLastName())
				       .email(teacherEntity.getEmail())
				       .phone(teacherEntity.getPhone())
				       .type(teacherEntity.getType().getTypeName())
				       .build();
	}
}
