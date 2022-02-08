package com.PlaceFinder.CollegeProject.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PlaceFinder.CollegeProject.Model.Place;
import com.PlaceFinder.CollegeProject.Model.User;
import com.PlaceFinder.CollegeProject.Model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Integer>{

	List<Vote> getByUser(User user);

	Vote getByPlaceAndUser(Place place, User user);

}
