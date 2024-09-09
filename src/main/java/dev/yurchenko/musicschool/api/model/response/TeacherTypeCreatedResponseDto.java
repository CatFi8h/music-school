package dev.yurchenko.musicschool.api.model.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Builder
@Getter
@Setter
public class TeacherTypeCreatedResponseDto {
	Long id;
	
	public TeacherTypeCreatedResponseDto(Long id) {
		this.id = id;
	}
}
