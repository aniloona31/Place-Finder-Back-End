package com.PlaceFinder.CollegeProject.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.PlaceFinder.CollegeProject.Dto.PlaceDto;
import com.PlaceFinder.CollegeProject.Dto.PlaceResponse;
import com.PlaceFinder.CollegeProject.Exception.SpringException;
import com.PlaceFinder.CollegeProject.Service.AuthService;
import com.PlaceFinder.CollegeProject.Service.PlaceService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/place")
@AllArgsConstructor
public class PlaceController {
	
	private final PlaceService placeService;
	private final AuthService authService;
	
	@PostMapping
	public ResponseEntity<String> save(@RequestBody PlaceDto placeDto) throws IOException{
		placeService.save(placeDto);
		return new ResponseEntity<String>("saved",HttpStatus.OK);
	}
	
	@GetMapping("/by-id/{id}")
	public ResponseEntity<PlaceResponse> getPlace(@PathVariable Integer id){
		return new ResponseEntity<PlaceResponse>(placeService.getPlace(id),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<PlaceResponse>> getAllPlaces(){
		return new ResponseEntity<List<PlaceResponse>>(placeService.getAllPlaces(),HttpStatus.OK);
	}
	
	@GetMapping("/{category}")
	public ResponseEntity<List<PlaceResponse>> getAllPlacesOfCategory(@PathVariable String category) throws SpringException{
		return new ResponseEntity<List<PlaceResponse>>(placeService.getAllPlacesOfCategory(category),HttpStatus.OK);
	}
	
	@PutMapping("/add-to-wishlist/{placeId}")
	public ResponseEntity<String> addToWishlist(@PathVariable Integer placeId){
		return new ResponseEntity<String>(authService.addToWishlist(placeId),HttpStatus.OK);
	}
	
	@DeleteMapping("/remove-from-wishlist/{placeId}")
	public ResponseEntity<String> removeFromWishlist(@PathVariable Integer placeId){
		return new ResponseEntity<String>(authService.removeFromWishlist(placeId),HttpStatus.OK);
	}
}
