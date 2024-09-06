package dev.yurchenko.musicschool.repository;

import dev.yurchenko.musicschool.repository.entities.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {

}
