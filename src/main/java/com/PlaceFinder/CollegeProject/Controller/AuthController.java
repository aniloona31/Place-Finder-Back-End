package com.PlaceFinder.CollegeProject.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PlaceFinder.CollegeProject.Dto.LoginRequest;
import com.PlaceFinder.CollegeProject.Dto.PlaceResponse;
import com.PlaceFinder.CollegeProject.Dto.RegisterRequest;
import com.PlaceFinder.CollegeProject.Model.User;
import com.PlaceFinder.CollegeProject.Service.AuthService;
import com.PlaceFinder.CollegeProject.jwt.JwtResponse;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/PlaceFinder/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody LoginRequest loginRequest){
		return new ResponseEntity<JwtResponse>(authService.login(loginRequest),HttpStatus.OK);
	}
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody RegisterRequest registerRequest) throws Exception {
		//System.out.println("signup called");
		return new ResponseEntity<User>(authService.register(registerRequest),HttpStatus.CREATED);
	}
	
	@PostMapping("/verify-account/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token){
		return new ResponseEntity<String>(authService.verifyUser(token),HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/current-user")
	public ResponseEntity<User> getCurrentUser(){
		return new ResponseEntity<User>(authService.getCurrentUser(),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<PlaceResponse>> getWishlist(){
		return new ResponseEntity<List<PlaceResponse>>(authService.getWishlist(),HttpStatus.OK);
	}
}
