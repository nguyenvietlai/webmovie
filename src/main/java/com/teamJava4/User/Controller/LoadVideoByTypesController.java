package com.teamJava4.User.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.teamJava4.Entity.Categories;
import com.teamJava4.Entity.Comments;
import com.teamJava4.Entity.Movie;
import com.teamJava4.Entity.Report;
import com.teamJava4.Entity.Video;
import com.teamJava4.Implementations.CateDAOIpl;
import com.teamJava4.Implementations.CommentDAOIpl;
import com.teamJava4.Implementations.MovieDAOIpl;
import com.teamJava4.Implementations.ReportDAOIpl;
import com.teamJava4.Implementations.VideoDAOIpl;

/**
 * Servlet implementation class LoadVideoByCateController
 */
@WebServlet({ "/anime-watching", "/anime-all-products", "/LoadEpisodeMovies", "/LoadSeriesMovies",
		"/LoadVideoSeriesByVideoID", "/FilterVideosByCate" })
public class LoadVideoByTypesController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VideoDAOIpl daoVideo = new VideoDAOIpl();
	private ReportDAOIpl daoReport = new ReportDAOIpl();
	private CateDAOIpl daoCate = new CateDAOIpl();
	private MovieDAOIpl daoMovie = new MovieDAOIpl();
	private CommentDAOIpl daoComment = new CommentDAOIpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoadVideoByTypesController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String path = request.getServletPath();
		switch (path) {
		case "/anime-watching":
			doGetWatching(request, response);
			break;
		case "/anime-all-products":
			doGetAllProducts(request, response);
			break;
		case "/LoadEpisodeMovies":
			doGetLoadEpisodeMovies(request, response);
			break;
		case "/LoadSeriesMovies":
			doGetLoadSeriesMovies(request, response);
			break;
		case "/LoadVideoSeriesByVideoID":
			doGetLoadVideoSeriesByVideoID(request, response);
			break;
		case "/FilterVideosByCate":
			doFilterVideoByCate(request, response);
			break;
		default:
			break;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private void doGetWatching(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("Categories", true);
		String vID = request.getParameter("videoID");
		String mID = request.getParameter("movieID");
		if (vID != null) {
			Video video = daoVideo.findById(Video.class, Long.valueOf(vID));
			if (video != null) {
				request.setAttribute("video", video);
				request.getRequestDispatcher("/Views/User/anime-watching-movies.jsp").forward(request, response);
			}
		}
		if (mID != null) {
			Movie movie = daoMovie.findById(Movie.class, Long.valueOf(mID));
			if (movie != null) {
				Video video = new Video();
				video.setMovie(movie);
				List<Video> listVideo = daoVideo.findAllVideoByMovies(video);
				Comments cm = new Comments();
				cm.setVideo(listVideo.get(0));
				List<Comments> listComment = daoComment.findAllByVideoID(cm);
				request.setAttribute("listVideo", listVideo);
				request.setAttribute("listComment", listComment);

			}
			request.getRequestDispatcher("/Views/User/anime-watching-series.jsp").forward(request, response);
		}

	}

	private void doGetAllProducts(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("Categories", true);
		String status = request.getParameter("status");
		List<Categories> listCate = daoCate.findAllCategories();
		request.setAttribute("listCate", listCate);
		request.setAttribute("status", status);
		request.getRequestDispatcher("/Views/User/anime-all-products.jsp").forward(request, response);

	}

	private void doGetLoadSeriesMovies(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Report> listMovie = daoReport.findAll();
		request.setAttribute("listMovie", listMovie);
		request.getRequestDispatcher("/Views/User/anime-items.jsp").forward(request, response);
	}

	private void doGetLoadEpisodeMovies(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Movie movie = new Movie();
		Video video = new Video();
		movie.setIs_movie(false);
		video.setMovie(movie);
		List<Video> listVideo = daoVideo.findAllVideosByTypes(video);
		request.setAttribute("listVideo", listVideo);
		request.getRequestDispatcher("/Views/User/anime-items.jsp").forward(request, response);
	}

	private void doGetLoadVideoSeriesByVideoID(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String vID = request.getParameter("videoID");
		if (vID != null) {
			Video video = daoVideo.findByID(Long.valueOf(vID));
			if (video != null) {
				Comments cm = new Comments();
				cm.setVideo(video);
				List<Comments> listComment = daoComment.findAllByVideoID(cm);
				List<Video> listVideo = daoVideo.findAllVideoByMovies(video);
				request.setAttribute("listVideo", listVideo);
				request.setAttribute("listComment", listComment);
				request.setAttribute("video", video);
				request.getRequestDispatcher("/Views/User/anime-comment-series.jsp").forward(request, response);
			}

		} else {
			response.setStatus(400);
		}

	}

	private void doFilterVideoByCate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long value = Long.valueOf(request.getParameter("value"));
		String types = request.getParameter("types");
		System.out.println(value);
	
		if (types.equals("movie")) {
			if (value == 0) {
				doGetLoadEpisodeMovies(request, response);
			} else {
				Categories cate = new Categories();
				cate.setCategory_id(value);
				Movie movie = new Movie();
				movie.setIs_movie(false);
				movie.setCategory(cate);
				Video video = new Video();
				video.setMovie(movie);
				List<Video> listVideo = daoVideo.findByCategory(video);
				request.setAttribute("listVideo", listVideo);
				request.getRequestDispatcher("/Views/User/anime-items.jsp").forward(request, response);
			}
		} else if (types.equals("series")) {
			if (value == 0) {
				doGetLoadSeriesMovies(request, response);
			} else {
				List<Report> list = daoReport.findAll();
				List<Report> listMovie = new ArrayList<>();
				for (Report report : list) {
					if(report.getMovie().getCategory().getCategory_id() == value) {
						listMovie.add(report);
					}
				}
				request.setAttribute("listMovie", listMovie);
				request.getRequestDispatcher("/Views/User/anime-items.jsp").forward(request, response);
			}
		}

	}

}
