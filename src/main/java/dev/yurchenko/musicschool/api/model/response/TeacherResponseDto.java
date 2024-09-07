package dev.yurchenko.musicschool.api.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class TeacherResponseDto {
	private Long id;
	private String name;
	private String surname;
	private String type;
	private String email;
	private String phone;
}
