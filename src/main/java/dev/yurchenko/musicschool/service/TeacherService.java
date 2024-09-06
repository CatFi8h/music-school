package dev.yurchenko.musicschool.service;

import dev.yurchenko.musicschool.api.model.request.TeacherRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface TeacherService {
	String createTeacher(TeacherRequestDto requestDto);
}
