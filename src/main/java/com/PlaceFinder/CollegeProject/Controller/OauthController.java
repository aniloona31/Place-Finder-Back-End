package com.PlaceFinder.CollegeProject.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import com.google.api.client.json.jackson2.JacksonFactory;
import com.PlaceFinder.CollegeProject.Dto.LoginRequest;
import com.PlaceFinder.CollegeProject.Dto.RegisterRequest;
//import com.PlaceFinder.CollegeProject.Dto.TokenDto;
//import com.PlaceFinder.CollegeProject.Dto.googlLoginDto;
import com.PlaceFinder.CollegeProject.Dto.googleLoginDto;
//import com.PlaceFinder.CollegeProject.Exception.SpringException;
import com.PlaceFinder.CollegeProject.Model.User;
import com.PlaceFinder.CollegeProject.Service.AuthService;
//import com.PlaceFinder.CollegeProject.Service.UserDetailsServiceImpl;
import com.PlaceFinder.CollegeProject.jwt.JwtResponse;



@RestController
@RequestMapping("/Placefinder/oauth")
public class OauthController {
	
	@Value("${google.clientId}")
	private String googleClientId;
	
	@Value("${secretPsw}")
	private String secretPsw;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthService authService;
	
	@PostMapping
	public ResponseEntity<JwtResponse> googleLogin(@RequestBody googleLoginDto payload) throws Exception{
		User user = new User();
        if(authService.existsEmail(payload.getEmail())) {
        	user = authService.getByEmail(payload.getEmail());
        }
        else {
        	user = saveUser(payload.getEmail(),payload.getUsername());
        }
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(user.getUsername());
        loginRequest.setPassword(secretPsw);
//        System.out.println(loginRequest);
        JwtResponse jwtResponse = authService.login(loginRequest);
//        System.out.println("response is this "+ jwtResponse);
        return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.OK);
	}
	
	private User saveUser(String email,String username)throws Exception {
		RegisterRequest registerRequest = new RegisterRequest();
		registerRequest.setEmail(email);
		registerRequest.setPassword(passwordEncoder.encode(secretPsw));
		registerRequest.setUsername(username);
		return authService.registerGoogle(registerRequest);
	}
}
