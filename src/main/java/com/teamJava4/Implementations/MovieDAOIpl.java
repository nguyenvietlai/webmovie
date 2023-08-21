package com.teamJava4.Implementations;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.TypedQuery;

import com.teamJava4.Entity.Movie;
import com.teamJava4.User.DAO.MovieDAO;
import com.teamJava4.User.DAO.SystemDAO;

public class MovieDAOIpl extends SystemDAO<Movie> implements MovieDAO<Movie, Long>{

	@Override
	public List<Movie> findAll() {
		return super.findAll(Movie.class);
	} 
	@Override
	public Boolean create(Movie entity) {
		entity.setIs_movie(false);
		entity.setName_movie(null);
		entity.setMovie_img(null);
		entity.setCreateBy(new Timestamp(System.currentTimeMillis()));
		entity.setMovie_desc(null);
		return super.create(entity);
	}

	@Override
	public List<Movie> findByCategory(Movie movie) {
		String jpql = "select o from Movie o where o.category.category_id=?0";
		return super.findMany(Movie.class, jpql, movie.getCategory().getCategory_id());
	}
	
	@Override
	public List<Movie> findByMovie() {
		try {
			String jpql = "select new Report(o.name_movie, count(o), o.Movie_img)"
					+ " from Movie o group by o.name_movie";
			TypedQuery<Movie> query = ENTITY_MANAGER.createQuery(jpql, Movie.class);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) {
		
		List<Movie> list = new MovieDAOIpl().findByMovie();
		System.out.println(list.size());
	}

	
}
