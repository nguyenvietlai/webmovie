package com.teamJava4.User.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.teamJava4.Entity.Movie;
import com.teamJava4.Entity.Report;
import com.teamJava4.Entity.Video;
import com.teamJava4.Implementations.MovieDAOIpl;
import com.teamJava4.Implementations.ReportDAOIpl;
import com.teamJava4.Implementations.VideoDAOIpl;

@WebServlet(urlPatterns = { "/Homess.php"})
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private VideoDAOIpl daoVideo = new VideoDAOIpl();
	private ReportDAOIpl daoReport = new ReportDAOIpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String path = request.getServletPath();
		if(path.contains("Homess.php")) {
			doGetHome(request, response);
		}
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	}


	private void doGetHome(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Video>listVideo = daoVideo.findTopVideoByView();
		List<Report>listMovie = daoReport.findAll();
		request.setAttribute("listVideo", listVideo);
		request.setAttribute("listMovie", listMovie);
		request.setAttribute("Home", true);
		request.getRequestDispatcher("/Views/User/anime-home.jsp").forward(request, response);
	}

	

	
	

	

	

	
}
