package com.PlaceFinder.CollegeProject.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PlaceFinder.CollegeProject.Model.Photos;
import com.PlaceFinder.CollegeProject.Model.Place;
@Repository
public interface PhotosRepository extends JpaRepository<Photos, Integer>{

	List<Photos> getByPlace(Place place);

}
