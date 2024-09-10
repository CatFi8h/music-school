package dev.yurchenko.musicschool.api.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateStudentRequestDto {
	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 20, message = "The username must be from 3 to 20 characters.")
	private String firstName;
	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 20, message = "The username must be from 3 to 20 characters.")
	private String lastName;
	@NotBlank(message = "Email is required")
	@Email(message = "The email is not a valid email.")
	private String email;
	@NotBlank(message = "Phone is required")
	private String phone;
}
