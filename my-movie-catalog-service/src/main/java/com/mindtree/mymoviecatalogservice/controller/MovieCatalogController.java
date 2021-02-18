package com.mindtree.mymoviecatalogservice.controller;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.mindtree.mymoviecatalogservice.entity.UserRating;
import com.mindtree.mymoviecatalogservice.services.MovieInfoService;
import com.mindtree.mymoviecatalogservice.services.UserRatingInfoService;

import com.mindtree.mymoviecatalogservice.entity.CatalogItem;
import com.mindtree.mymoviecatalogservice.entity.Movie;
import com.mindtree.mymoviecatalogservice.entity.MovieAvgRating;
import com.mindtree.mymoviecatalogservice.entity.Rating;
import com.mindtree.mymoviecatalogservice.entity.RatingList;


@RestController
@RequestMapping("/catalog")
@CrossOrigin
public class MovieCatalogController {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired 
	private MovieInfoService movieInfoService;
	
	@Autowired
	private UserRatingInfoService userRatingInfoService;
	
    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") int userId) {

        UserRating userRating = userRatingInfoService.getUserRating(userId);
        return userRating.getRatings().stream()
                .map(rating -> movieInfoService.getCatalogItem(rating))
                .collect(Collectors.toList());
    }
    
    @GetMapping("/allmovies")
    public List<MovieAvgRating> getMovieCatalog() {
        return movieInfoService.getMovieCatalog();           
    }
    
    @GetMapping("/allRating")
    public List<Rating> getMovieRating() {
   // RatingList ratingList = restTemplate.getForObject("http://ratings-data-service/ratingsdata/rating", RatingList.class);
    ResponseEntity<List<Rating>> rateResponse = restTemplate.exchange("http://ratings-data-service/ratingsdata/rating", HttpMethod.GET,null,new ParameterizedTypeReference<List<Rating>>() {
	});
    	
    return rateResponse.getBody();
    }
    
    
    @PostMapping("/addmovie")
    public Movie addMovie(@RequestBody Movie movie) {
    	System.err.println(movie.getName());
    	return movieInfoService.addMovie(movie);
    }
    /* @GetMapping("/demo")
    public UserRating getMovie() {
    	 UserRating userRating = restTemplate.getForObject("http://ratings-data-service/ratingsdata/user/" + "foo", UserRating.class);
    	//Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + "100", Movie.class);
		return userRating;
    	
    }*/

}
