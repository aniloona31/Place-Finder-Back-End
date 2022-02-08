package com.PlaceFinder.CollegeProject.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PlaceFinder.CollegeProject.Model.Place;
import com.PlaceFinder.CollegeProject.Model.Review;


@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
	List<Review> findByPlace(Place place);
}
