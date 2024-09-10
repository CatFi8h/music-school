package dev.yurchenko.musicschool.api.model.response;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class StudentResponseDto {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private Date createdAt;
	private Date updatedAt;
}
