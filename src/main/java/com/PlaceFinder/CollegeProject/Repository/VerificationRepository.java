package com.PlaceFinder.CollegeProject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PlaceFinder.CollegeProject.Model.VerificationToken;


@Repository
public interface VerificationRepository extends JpaRepository<VerificationToken, Integer>{

	VerificationToken getByToken(String token);

}
