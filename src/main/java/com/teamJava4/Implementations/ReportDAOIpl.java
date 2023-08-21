package com.teamJava4.Implementations;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import com.teamJava4.Entity.Report;
import com.teamJava4.User.DAO.ReportDAO;
import com.teamJava4.User.DAO.SystemDAO;

public class ReportDAOIpl extends SystemDAO<Report> implements ReportDAO<Report, Long> {

	@Override
	public List<Report> findAll() {
		try {
			String jpql = "select new Report(o.movie.name_movie, count(o), o.movie.Movie_img, o.movie)"
					+ " from Video o where o.movie.is_movie=true group by o.movie.name_movie, o.movie.Movie_img, o.movie";	
			TypedQuery<Report> query = ENTITY_MANAGER.createQuery(jpql, Report.class);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}
	}


	public static void main(String[] args) {
		List<Report> list = new ReportDAOIpl().findAll();
		List<Report> list2 = new ArrayList<>();
		for (Report report : list) {
			if(report.getMovie().getCategory().getCategory_id() == 1) {
				list2.add(report);
			}
		}
		System.out.println(list2.size());
		System.out.println(list.get(0).getEpisode());
		
	}
	
}
