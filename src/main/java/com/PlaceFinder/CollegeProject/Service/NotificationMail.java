package com.PlaceFinder.CollegeProject.Service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class NotificationMail {
	private String body;
	private String subject;
	private String recipent;
	//private String from = "placefinder22@gmail.com";
}
