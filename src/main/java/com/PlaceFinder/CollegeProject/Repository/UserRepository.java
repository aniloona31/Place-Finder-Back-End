package com.PlaceFinder.CollegeProject.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PlaceFinder.CollegeProject.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User , Integer>{

	User getByUsername(String username);

	User getByEmail(String email);

	Optional<User> findByUsername(String username);

	boolean existsByEmail(String email);

}
