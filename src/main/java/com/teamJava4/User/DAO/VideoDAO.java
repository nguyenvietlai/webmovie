package com.teamJava4.User.DAO;

import java.util.List;

public interface VideoDAO <E, K>{
	Boolean create(E entity);
	E findByID(K key);
	List<E> findAll();
	List<E> findByCategory(E entity);
	List<E> findAllVideosByTypes(E entity); // Find all movie for all the series
	List<E> findAllVideoByMovies(E entity); // Find all video for one series
	List<E> findTopVideoByView();
}
