package dev.yurchenko.musicschool.repository;

import dev.yurchenko.musicschool.repository.entities.TeacherTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherTypeRepository extends JpaRepository<TeacherTypeEntity, Long> {
}
