package com.PlaceFinder.CollegeProject.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
//import java.util.HashSet;
import java.util.Optional;
//import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PlaceFinder.CollegeProject.Dto.LoginRequest;
import com.PlaceFinder.CollegeProject.Dto.PlaceResponse;
//import com.PlaceFinder.CollegeProject.Dto.PlaceResponse;
import com.PlaceFinder.CollegeProject.Dto.RegisterRequest;
import com.PlaceFinder.CollegeProject.Exception.SpringException;
import com.PlaceFinder.CollegeProject.Model.Place;
//import com.PlaceFinder.CollegeProject.Model.Place;
import com.PlaceFinder.CollegeProject.Model.User;
import com.PlaceFinder.CollegeProject.Model.VerificationToken;
import com.PlaceFinder.CollegeProject.Repository.PlaceRepository;
//import com.PlaceFinder.CollegeProject.Repository.PlaceRepository;
import com.PlaceFinder.CollegeProject.Repository.UserRepository;
import com.PlaceFinder.CollegeProject.Repository.VerificationRepository;
import com.PlaceFinder.CollegeProject.jwt.JwtResponse;
import com.PlaceFinder.CollegeProject.jwt.JwtUtil;
//import java.util.Set;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final VerificationRepository verificationRepository;
	private final PasswordEncoder passwordEncoder;
	private final MailService mailService;
	private final UserDetailsServiceImpl userDetailsService;
	private final JwtUtil jwtProvider;
	private final AuthenticationManager authenticationManager;
	private final PlaceRepository placeRepository;
	
	public JwtResponse login(LoginRequest loginRequest) {
		User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(()->new SpringException("user doesn't exist"));
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(user.getUsername()); 
		String token = jwtProvider.generateToken(userDetails);
		//System.out.println(user.getLikedPlaces().size());
		//List<Integer> likedPlaces = user.getLikedPlaces().stream().map(this::MapPlaceToPlaceId).collect(Collectors.toList());
		//System.out.println(likedPlaces);
		return JwtResponse.builder()
				.token(token)
				.username(loginRequest.getUsername())
				.userId(user.getUserId())
				.expiryTime(Instant.now().plusMillis(jwtProvider.getExpirationTime()) )
				.build();
		
	}

	private PlaceResponse MapPlaceToPlaceId(Place place) {
		return PlaceResponse.builder()
				.address(place.getAddress())
				.description(place.getDescripton())
				.budget(place.getBudget())
				.disLikes(place.getDisLikes())
				.likes(place.getLikes())
				.image(place.getMain_image())
				.placeId(place.getPlaceId())
				.placeName(place.getPlaceName())
				.build();
	}
	
	public User register(RegisterRequest registerRequest) throws Exception{
		User user = userRepository.getByUsername(registerRequest.getUsername());
		if(user != null) {
			throw new SpringException("Username is already taken!");
		}
		user = null;
		user = userRepository.getByEmail(registerRequest.getEmail());
		if(user != null) {
			throw new SpringException("Account is already linked to email");
		}
		
		User newUser = new User();
		newUser.setEmail(registerRequest.getEmail());
		newUser.setEnabled(false);
		newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		newUser.setInstantCreated(Instant.now());
		//newUser.setProfilePic();
		newUser.setLikedPlaces(new ArrayList<Place>());
		newUser.setUsername(registerRequest.getUsername());
		
		userRepository.save(newUser);
		
		String token = generateVerificationToken(newUser);
		mailService.sendMail(new NotificationMail("Thank you for signing up to Place Finder, " +
				"please click on the below url to activate your account : " +
				"http://localhost:8080/PlaceFinder/auth/verify-account/" + token,"Please, Activate Your Account.",newUser.getEmail()));
		return newUser;
	}

	private String generateVerificationToken(User newUser) {
		String token = UUID.randomUUID().toString();
		VerificationToken verify = new VerificationToken();
		verify.setToken(token);
		verify.setUser(newUser);
		verificationRepository.save(verify);
		return token;
	}
	public boolean existsEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	@Transactional
	public String verifyUser(String token) {
		VerificationToken verificationToken = verificationRepository.getByToken(token);
		if(verificationToken == null) {
			throw new SpringException("wrong verification token provided...");
		}
		Optional<User> user = userRepository.findByUsername(verificationToken.getUser().getUsername());
		user.orElseThrow(()-> new SpringException("user doesn't exist..."));
		user.get().setEnabled(true);
		userRepository.save(user.get());
		return "verified";
	}
	
	@Transactional(readOnly = true)
	public User getCurrentUser() throws SpringException {
		User user = userRepository.findByUsername(getUsername()).orElseThrow(()-> new SpringException("User Not Found!!"));
        
        return user;
	}
	
	public String getUsername() {
		   SecurityContext context = SecurityContextHolder.getContext();
		   Authentication authentication = context.getAuthentication();
		   if (authentication == null)
		     return null;
		   Object principal = authentication.getPrincipal();
		   if (principal instanceof UserDetails) {
		     return ((UserDetails) principal).getUsername();
		   } else {
		     return principal.toString();
		   }
	}

	public User getByEmail(String email) {
		return userRepository.getByEmail(email);
	}
	
	public User registerGoogle(RegisterRequest registerRequest) {
		User newUser = new User();
		newUser.setEmail(registerRequest.getEmail());
		newUser.setEnabled(true);
		System.out.println(registerRequest.getPassword());
		newUser.setPassword(passwordEncoder.encode("iamgroot"));
		newUser.setInstantCreated(Instant.now());
		//newUser.setProfilePic();
		newUser.setUsername(registerRequest.getUsername());
		
		userRepository.saveAndFlush(newUser);
		return newUser;
	}
	
	@Transactional
	public String addToWishlist(Integer placeId) {
		User user = getCurrentUser();
		//System.out.println(user);
		Place place = placeRepository.getById(placeId);
		//PlaceResponse placeResp = placeService.MapPlaceToPlaceResponse(place);
		List<Place> likedPlaces = user.getLikedPlaces();
		
		likedPlaces.add(place);
		user.setLikedPlaces(likedPlaces);
		userRepository.save(user);
	
		
		return "added";
	}
	
	@Transactional
	public String removeFromWishlist(Integer placeId) {
		User user = getCurrentUser();
		Place place = placeRepository.getById(placeId);
		List<Place> likedPlaces = user.getLikedPlaces();
		likedPlaces.remove(place);
		user.setLikedPlaces(likedPlaces);
		
		userRepository.save(user);
		
		return "removed";
	}

	public List<PlaceResponse> getWishlist() {
		return getCurrentUser().getLikedPlaces()
				.stream()
				.map(this::MapPlaceToPlaceId)
				.collect(Collectors.toList());
	}
	
	
}
