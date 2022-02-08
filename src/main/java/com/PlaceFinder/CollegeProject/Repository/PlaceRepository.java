package com.PlaceFinder.CollegeProject.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PlaceFinder.CollegeProject.Model.Place;
import com.PlaceFinder.CollegeProject.Model.SubCategory;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Integer>{

	Optional<Place> findByPlaceName(String placeName);

	List<Place> getBySubCategory(SubCategory subCategory);

	Place getByPlaceName(String placeName);



}
