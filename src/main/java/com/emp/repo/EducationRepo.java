package com.emp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp.entity.Education;
@Repository
public interface EducationRepo extends JpaRepository<Education, Long>{

}
