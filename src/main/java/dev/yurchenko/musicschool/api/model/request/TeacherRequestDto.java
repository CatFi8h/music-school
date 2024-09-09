package dev.yurchenko.musicschool.api.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TeacherRequestDto {
	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 20, message = "The username must be from 3 to 20 characters.")
	private String firstName;
	@Size(max = 20, message = "The surname must be from 3 to 20 characters.")
	private String lastName;
	@NotNull(message = "Type is required")
	private Long type;
	@NotBlank(message = "Email is required")
	@Email(message = "The email is not a valid email.")
	private String email;
	@NotBlank(message = "Phone is required")
	private String phone;
	private Boolean isAdmin;
	
	public TeacherRequestDto(String firstName, String lastName, Long type, String email, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.type = type;
		this.email = email;
		this.phone = phone;
	}
}
