package com.teamJava4.User.Controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.teamJava4.Entity.Comments;
import com.teamJava4.Entity.Movie;
import com.teamJava4.Entity.Video;
import com.teamJava4.Implementations.CommentDAOIpl;
import com.teamJava4.Implementations.MovieDAOIpl;
import com.teamJava4.Implementations.VideoDAOIpl;

/**
 * Servlet implementation class DetailController
 */
@WebServlet("/anime-detail")
public class DetailController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private VideoDAOIpl daoVideo = new VideoDAOIpl();
	private MovieDAOIpl daoMovie = new MovieDAOIpl();
	private CommentDAOIpl daoComment = new CommentDAOIpl();
	/**
     * @see HttpServlet#HttpServlet()
     */
    public DetailController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String path = request.getServletPath();
		if(path.contains("anime-detail")) {
			doGetDetail(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	private void doGetDetail(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String vID = request.getParameter("videoID");
		String mID = request.getParameter("movieID");
		List<Comments> listComment = null;
		Comments comment = new Comments();
		request.setAttribute("Categories", true);
		if (vID != null) {
			Long videoID = Long.valueOf(vID);
			Video video = daoVideo.findById(Video.class, videoID);
			if (video != null) {
				request.setAttribute("video", video);
				comment.setVideo(video);
				listComment = daoComment.findAllByVideoID(comment);
				request.setAttribute("listComment", listComment);
				System.out.println(listComment.toString());
			}
		}
		if (mID != null) {
			Long movieID = Long.valueOf(mID);
			Movie movie = daoMovie.findById(Movie.class, movieID);
			if (movie != null) {
				request.setAttribute("movie", movie);
			}
		}
		request.getRequestDispatcher("/Views/User/anime-details.jsp").forward(request, response);
	}
	
}
