package com.PlaceFinder.CollegeProject.jwt;

import java.time.Instant;
import java.util.List;
//import java.util.Set;

import com.PlaceFinder.CollegeProject.Dto.PlaceResponse;
import com.PlaceFinder.CollegeProject.Model.Place;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JwtResponse {
	private String token;
	private String username;
	private Instant expiryTime;
	private Integer userId;
//	private List<Integer> likedPlaces;
}
