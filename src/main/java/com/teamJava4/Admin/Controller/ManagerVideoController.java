package com.teamJava4.Admin.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.teamJava4.Entity.Categories;
import com.teamJava4.Entity.Movie;
import com.teamJava4.Entity.Video;
import com.teamJava4.Implementations.MovieDAOIpl;
import com.teamJava4.Implementations.VideoDAOIpl;
import com.teamJava4.User.Utils.GetEntityByBeanIUtil;

/**
 * Servlet implementation class ManagerVideoController
 */
@WebServlet(urlPatterns = { "/AdminHome", "/ManagerVideoController", "/admin/manager-movie","/get-movie-info" })
public class ManagerVideoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VideoDAOIpl daoVideo = new VideoDAOIpl();
	private MovieDAOIpl daoMovie = new MovieDAOIpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset = UTF-8");
		String path = request.getServletPath();
		switch (path) {
		case "/AdminHome":
			request.setAttribute("movies",daoMovie.findAll());
			List<Video> listVideo = daoVideo.findAll();
			
			request.setAttribute("listVideo", listVideo);
			request.setAttribute("movieVideo", daoMovie.findAll());
			request.getRequestDispatcher("/Views/Admin/managerVideos.jsp").forward(request, response);
			break;
		case "/get-movie-info":// localhost/MovieWebsite/get-movie-info?q=return&id={1}
			String id = request.getParameter("id");
			String q = request.getParameter("q");
			if(q.equals("return")) {
				response.setContentType("application/json");
				response.setCharacterEncoding("UTF-8");
				Movie movie = daoMovie.findById(Movie.class, Long.valueOf(id));
				System.out.println(movie.toString());
				if(movie !=null) {
					JSONObject jsondata = new JSONObject();
					jsondata.put("name", movie.getName_movie());
					jsondata.put("image", movie.getMovie_img());
					jsondata.put("category", movie.getCategory().getCategory_id());
					jsondata.put("description", movie.getMovie_desc());

					response.getWriter().write(jsondata.toString());
		
				}else {
					response.setStatus(400);
				}
			}
	
			break;
		case "/ManagerVideoController":
			doGetManagerVideo(request, response);
			break;
		default:

		}

	}

	protected void doGetManagerVideo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Video> listVideo = daoVideo.findAll();
		System.out.println(listVideo.size());
		request.setAttribute("listVideo", listVideo);
		request.getRequestDispatcher("/Views/Admin/Components/TableVideoData.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getServletPath();
		switch (path) {
		case "/admin/manager-movie": {
			String action = request.getParameter("action");
			if (action.equals("create")) {
				if (createVideo(request, response)) {
					response.setStatus(200);
					doGetManagerVideo(request, response);
				} else {
					response.setStatus(400);
				}

			}

			break;
		}
		case "/AdminHome": {

			break;
		}

		default:

		}
	}

	public boolean createVideo(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Video video = GetEntityByBeanIUtil.getEntity(Video.class, request);
		String cateId = request.getParameter("categories");
		Categories cate = new Categories();
		cate.setCategory_id(Long.valueOf(cateId));
		Movie movie = new Movie();
		movie.setCategory(cate);
		if (daoMovie.create(movie)) {
			video.setMovie(movie);
			if (daoVideo.create(video)) {
				return true;
			}
		}
		return false;

	}

}