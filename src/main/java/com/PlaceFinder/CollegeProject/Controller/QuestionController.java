package com.PlaceFinder.CollegeProject.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.PlaceFinder.CollegeProject.Dto.QuestionDto;
import com.PlaceFinder.CollegeProject.Dto.QuestionsResp;
//import com.PlaceFinder.CollegeProject.Dto.QuestionsResponse;
import com.PlaceFinder.CollegeProject.Service.QuestionService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/questions")
public class QuestionController {
	
	private final QuestionService questionService;
	
	@PostMapping
	public ResponseEntity<QuestionsResp> postQuestion(@RequestBody QuestionDto questionDto){
		return new ResponseEntity<QuestionsResp>(questionService.postQuestion(questionDto),HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<QuestionsResp>> getQuestionsOfCurrentUser(){
		return new ResponseEntity<List<QuestionsResp>>(questionService.getQuestionsOfCurrentUser(),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<QuestionsResp>> getAllQuestions(){
		return new ResponseEntity<List<QuestionsResp>>(questionService.getAllQuestions(),HttpStatus.OK);
	}
	
	@PostMapping("/resolve")
	public ResponseEntity<String> ResolveQuestion(@RequestBody QuestionDto questionDto){
		questionService.resolveQuestion(questionDto);
		return new ResponseEntity<String>("resolved",HttpStatus.OK);
	}
	
}
