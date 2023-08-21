package com.teamJava4.User.DAO;

import java.util.List;

public interface MovieDAO <E, K>{
	Boolean create(E entity);
	List<E> findAll();
	List<E> findByCategory(E entity);
	List<E> findByMovie();
}
