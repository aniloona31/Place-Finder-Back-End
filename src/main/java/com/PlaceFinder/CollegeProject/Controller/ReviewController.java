package com.PlaceFinder.CollegeProject.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PlaceFinder.CollegeProject.Dto.ReviewDto;
import com.PlaceFinder.CollegeProject.Dto.ReviewRequest;
import com.PlaceFinder.CollegeProject.Service.ReviewService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/place/review")
public class ReviewController {
	
	private final ReviewService reviewService;
	
	@PostMapping
	public ResponseEntity<ReviewDto> addReview(@RequestBody ReviewRequest reviewDto){
		return new ResponseEntity<ReviewDto>(reviewService.save(reviewDto),HttpStatus.OK);
	}
	@GetMapping("/{placeId}")
	public ResponseEntity<List<ReviewDto>> getReviewsOfPlace(@PathVariable Integer placeId){
		return new ResponseEntity<List<ReviewDto>> (reviewService.getReviewsOfPlace(placeId),HttpStatus.OK);
	}
	
//	@GetMapping("/{reviewId}")
//	public ResponseEntity<ReviewDto> getReview(@PathVariable Integer reviewId){
//		return new ResponseEntity<ReviewDto>(reviewService.getReview(reviewId),HttpStatus.OK);
//	}
	
	@DeleteMapping("/{reviewId}")
	public ResponseEntity<String> deleteReview(@PathVariable Integer reviewId){
		reviewService.deleteReview(reviewId);
		return new ResponseEntity<String>("Deleted",HttpStatus.OK);
	}
}
