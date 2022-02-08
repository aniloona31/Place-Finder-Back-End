package com.PlaceFinder.CollegeProject.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.PlaceFinder.CollegeProject.Dto.PlaceDto;
import com.PlaceFinder.CollegeProject.Dto.PlaceResponse;
import com.PlaceFinder.CollegeProject.Exception.SpringException;
import com.PlaceFinder.CollegeProject.Model.Place;
import com.PlaceFinder.CollegeProject.Model.SubCategory;
import com.PlaceFinder.CollegeProject.Repository.PlaceRepository;
import com.PlaceFinder.CollegeProject.Repository.SubCategoryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PlaceService {

	private final PlaceRepository placeRepository;
	private final SubCategoryRepository subCategoryRepository;
	
	public void save(PlaceDto placeDto) throws IOException {
		Place place = placeRepository.getByPlaceName(placeDto.getPlaceName());
		SubCategory subCategory = subCategoryRepository.findByCategory(placeDto.getSubCategory()).orElseThrow(()-> new SpringException("category doesn't exist"));
		if(place != null) {
			throw new SpringException("place already present");
		}
		else {
			placeRepository.save(MapPlaceDtoToPlace(placeDto,subCategory));
		}
		
		
	}
	
	private Place MapPlaceDtoToPlace(PlaceDto placeDto,SubCategory subCategory) throws IOException{
		return Place.builder()
				.address(placeDto.getAddress())
				.budget(placeDto.getBudget())
				.descripton(placeDto.getDescription())
				.placeName(placeDto.getPlaceName())
				.main_image(placeDto.getImage())
				.subCategory(subCategory)
				.users(null)
				.likes(0)
				.disLikes(0)
				.build();
	}
	
	//Map place to placeResponse
	private PlaceResponse MapPlaceToPlaceResponse(Place place) {
		return PlaceResponse.builder()
		.address(place.getAddress())
		.description(place.getDescripton())
		.budget(place.getBudget())
		.placeId(place.getPlaceId())
		.likes(place.getLikes())
		.image(place.getMain_image())
		.subCategory(place.getSubCategory().getCategory())
		.placeName(place.getPlaceName())
		.disLikes(place.getDisLikes())
		.build();
	}
	
	public PlaceResponse getPlace(Integer placeId) {
		Place place = placeRepository.findById(placeId).orElseThrow(()-> new SpringException("place not found"));
		return MapPlaceToPlaceResponse(place);
	}
	
	public List<PlaceResponse> getAllPlaces(){
		List<Place> places = placeRepository.findAll();
		//System.out.println(places);
		return places.stream()
				.map(this :: MapPlaceToPlaceResponse)
				.collect(Collectors.toList());

	}
	
	//get places of a category
	public List<PlaceResponse> getAllPlacesOfCategory(String category) throws SpringException{
		//check if given Category exist
		SubCategory categoryCheck = subCategoryRepository.getByCategory(category);
		if(categoryCheck == null) {
			//System.out.println(categoryCheck);
			throw new SpringException("category doesn't exist");
		}
		return placeRepository.getBySubCategory(categoryCheck)
				.stream()
				.map(this :: MapPlaceToPlaceResponse)
				.collect(Collectors.toList());
	}
	
}
