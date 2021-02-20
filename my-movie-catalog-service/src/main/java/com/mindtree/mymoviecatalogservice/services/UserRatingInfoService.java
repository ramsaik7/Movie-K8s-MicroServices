package com.mindtree.mymoviecatalogservice.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mindtree.mymoviecatalogservice.entity.Rating;
import com.mindtree.mymoviecatalogservice.entity.UserRating;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UserRatingInfoService {

	@Autowired
	RestTemplate restTemplate;
	
	//private final String  movieUrl="http://movie-info-service/movies/";
	
	private final String  ratingUrl="http://rating-data-service/ratingsdata/";
	
	@HystrixCommand(fallbackMethod = "getUserRatingFallBack")
	public UserRating getUserRating(int userId) {
		 UserRating userRating = restTemplate.getForObject(ratingUrl+userId, UserRating.class);
		 return userRating;
	}
	
	public UserRating getUserRatingFallBack(int userId) {
		 return new UserRating(0,"None",null);
		 
	}
	
	@HystrixCommand(fallbackMethod = "getUserRatingFallBack")
	public UserRating getAddUser(UserRating rating) {
		 UserRating userRating = restTemplate.postForObject(ratingUrl,rating ,UserRating.class);
		 return userRating;
	}
	
	
}
