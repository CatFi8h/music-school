package dev.yurchenko.musicschool.api.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TeacherRequestDto {
	@NotBlank(message = "Name is required")
	@Size(min = 3, max = 20, message = "The username must be from 3 to 20 characters.")
	private String name;
	@Size(min = 0, max = 20, message = "The surname must be from 3 to 20 characters.")
	private  String surname;
	@NotBlank(message="Type is required")
	private  String type;
	@NotBlank(message="Email is required")
	@Email(message = "The email is not a valid email.")
	private  String email;
	@NotBlank(message="Phone is required")
	private  String phone;
	private Boolean isAdmin;
	
	public TeacherRequestDto(String name, String surname, String type, String email, String phone) {
		this.name = name;
		this.surname = surname;
		this.type = type;
		this.email = email;
		this.phone = phone;
	}
}
