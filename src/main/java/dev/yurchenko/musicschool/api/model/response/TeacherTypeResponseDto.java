package dev.yurchenko.musicschool.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeacherTypeResponseDto {
	Long id;
	String name;
}
