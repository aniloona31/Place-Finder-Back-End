package com.PlaceFinder.CollegeProject.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PlaceFinder.CollegeProject.Model.SubCategory;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer>{

	Optional<SubCategory> findByCategory(String category);

	SubCategory getByCategory(String category);

}
