package com.PlaceFinder.CollegeProject.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.PlaceFinder.CollegeProject.Dto.QuestionDto;
import com.PlaceFinder.CollegeProject.Dto.QuestionsResp;
//import com.PlaceFinder.CollegeProject.Dto.QuestionsResponse;
import com.PlaceFinder.CollegeProject.Exception.SpringException;
import com.PlaceFinder.CollegeProject.Model.Question;
import com.PlaceFinder.CollegeProject.Model.User;
import com.PlaceFinder.CollegeProject.Repository.QuestionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class QuestionService {
	
	private final QuestionRepository questionRepository;
	private final AuthService authService;
	//private final AuthService authService;
	
	
	public QuestionsResp postQuestion(QuestionDto questionDto) {
		Question question = MapQuestionDtoToQuestion(questionDto);
		questionRepository.save(question);
		return MapQuestionToQuestionDto(question);
	}
	
	private Question MapQuestionDtoToQuestion(QuestionDto questionDto){
		return Question.builder()
				.askedDate(Instant.now())
				.question(questionDto.getQuestion())
				.user(authService.getCurrentUser())
				.resolved(false)
				.build();
	}

	private QuestionsResp MapQuestionToQuestionDto(Question question) {
		return QuestionsResp.builder()
				.askedDate(question.getAskedDate())
				.question(question.getQuestion())
				.userId(question.getUser().getUserId())
				.questionId(question.getQuestionId())
				.resolved(question.isResolved())
				.username(question.getUser().getUsername())
				.build();
	}
	
	public List<QuestionsResp> getQuestionsOfCurrentUser() {
		//User user1 = authService.getCurrentUser();
		User user = authService.getCurrentUser();
//		if(user1.getUsername() != user2.getUsername()) {
//			throw new SpringException("questions not accessible");
//		}
		return questionRepository.findByUser(user)
				.stream()
				.map(this::MapQuestionToQuestionDto)
				.collect(Collectors.toList());
	}

	public List<QuestionsResp> getAllQuestions() {
		return questionRepository.findByResolved(false)
				.stream()
				.map(this :: MapQuestionToQuestionDto)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void resolveQuestion(QuestionDto questionDto) {
		Question question = questionRepository.getByQuestion(questionDto.getQuestion());
		if(question.getUser().getUsername() != authService.getCurrentUser().getUsername()) {
			throw new SpringException("this question can't be resolved by you...");
		}
		if(question.isResolved() == true) {
			throw new SpringException("question already resolved...");
		}
		question.setResolved(true);
		questionRepository.save(question);
	}
}
