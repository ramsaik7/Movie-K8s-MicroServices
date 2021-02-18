package com.mindtree.ratingdataservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindtree.ratingdataservice.entity.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating,Integer>{

}
