package com.PlaceFinder.CollegeProject.Repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.PlaceFinder.CollegeProject.Model.Question;
import com.PlaceFinder.CollegeProject.Model.User;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{

	List<Question> findByUser(User user);

	List<Question> findByResolved(boolean b);

	Question getByQuestion(String question);

}
