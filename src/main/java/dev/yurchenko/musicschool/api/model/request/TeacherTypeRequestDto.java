package dev.yurchenko.musicschool.api.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TeacherTypeRequestDto {
	@NotBlank(message = "Type Name is required")
	private String typeName;
	
	
}
