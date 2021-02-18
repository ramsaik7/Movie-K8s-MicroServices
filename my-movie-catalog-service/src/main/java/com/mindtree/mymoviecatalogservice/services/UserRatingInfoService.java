package com.mindtree.mymoviecatalogservice.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mindtree.mymoviecatalogservice.entity.Rating;
import com.mindtree.mymoviecatalogservice.entity.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserRatingInfoService {

	@Autowired
	RestTemplate restTemplate;
	
	@HystrixCommand(fallbackMethod = "getUserRatingFallBack")
	public UserRating getUserRating(int userId) {
		 UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + userId, UserRating.class);
		 return userRating;
	}
	
	public UserRating getUserRatingFallBack(int userId) {
		 return new UserRating(0,"None",null);
		 
	}
	
	@HystrixCommand(fallbackMethod = "getUserRatingFallBack")
	public UserRating getAddUser(UserRating rating) {
		 UserRating userRating = restTemplate.postForObject("http://ratings-data-service/ratingsdata/adduser/",rating ,UserRating.class);
		 return userRating;
	}
	
	
}
