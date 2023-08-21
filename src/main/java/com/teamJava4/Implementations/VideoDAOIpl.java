package com.teamJava4.Implementations;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.TypedQuery;

import com.teamJava4.Entity.Movie;
import com.teamJava4.Entity.Video;
import com.teamJava4.User.DAO.SystemDAO;
import com.teamJava4.User.DAO.VideoDAO;

public class VideoDAOIpl extends SystemDAO<Video> implements VideoDAO<Video, Long> {

	@Override
	public Video findByID(Long key) {
		// TODO Auto-generated method stub
		return super.findById(Video.class, key);
	}

	@Override
	public List<Video> findAll() {
		return super.findAll(Video.class);
	}

	@Override
	public Boolean create(Video entity) {
		entity.setCreateBy(new Timestamp(System.currentTimeMillis()));
		entity.setIsActive(true);
		entity.setUpdateBy(null);
		entity.setView_count(0L);
		entity.setView_share(0L);
		return super.create(entity);
	}

	@Override
	public List<Video> findByCategory(Video video) {
		String jpql = "select o from Video o where o.movie.category.category_id=?0 and o.movie.is_movie=?1";
		return super.findMany(Video.class, jpql, video.getMovie().getCategory().getCategory_id(),
				video.getMovie().getIs_movie());
	}

	@Override
	public List<Video> findAllVideosByTypes(Video entity) {
		try {
			String jpql = "select o from Video o where o.movie.is_movie=?0";
			TypedQuery<Video> query = ENTITY_MANAGER.createQuery(jpql, Video.class);
			query.setParameter(0, entity.getMovie().getIs_movie());
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Video> findAllVideoByMovies(Video entity) {
		String jpql = "select o from Video o where o.movie.is_movie= true and o.movie.movie_id=?0";
		return super.findMany(Video.class, jpql, entity.getMovie().getMovie_id());
	}

	@Override
	public List<Video> findTopVideoByView() {
		String jpql = "select o from Video o where o.view_count > 500 and o.movie.is_movie = false";
		return super.findMany(Video.class, jpql);
	}

	public static void main(String[] args) {

		Video v = new VideoDAOIpl().findByID(11L);

		System.out.println(v.toString());
		List<Video> list = new VideoDAOIpl().findByCategory(v);
		System.out.println(list.size());

	}

}
