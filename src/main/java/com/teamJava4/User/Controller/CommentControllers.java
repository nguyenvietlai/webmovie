package com.teamJava4.User.Controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.teamJava4.Entity.Comments;
import com.teamJava4.Entity.User;
import com.teamJava4.Entity.Video;
import com.teamJava4.Implementations.CommentDAOIpl;
import com.teamJava4.Implementations.VideoDAOIpl;

/**
 * Servlet implementation class CommentControllers
 */
@WebServlet({ "/anime-addComment", "/anime-deleteComment", "/anime-addCommentSeries" })
public class CommentControllers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CommentDAOIpl daoComment = new CommentDAOIpl();
	private VideoDAOIpl daoVideo = new VideoDAOIpl();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CommentControllers() {
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
		request.setCharacterEncoding("utf-8");
		String path = request.getServletPath();
		if (path.contains("anime-deleteComment")) {
			doGetDeleteComment(request, response);
		} else if (path.contains("anime-addCommentSeries")) {
			doAddCommentSeries(request, response);
		}

	}

	private void doGetDeleteComment(HttpServletRequest request, HttpServletResponse response) {
		Long commentID = Long.valueOf(request.getParameter("commentID"));
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			Comments commentCheck = new Comments();
			commentCheck.setUser(user);
			commentCheck.setComment_id(commentID);
			Comments comment = daoComment.findCommentByID(commentCheck);
			if (comment != null) {
				if (daoComment.delete(comment)) {
					response.setStatus(201);
				} else {
					response.setStatus(400);
				}
			} else {
				response.setStatus(400);
			}
		} else {
			response.setStatus(400);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset = UTF-8");
		String path = request.getServletPath();
		System.out.println("Post path: " + path);
		if (path.contains("anime-addComment")) {
//			System.out.println("aaaaaaaaaaaa");
			doPostComment(request, response);
		}

	}

	private void doAddCommentSeries(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		String comment = request.getParameter("comment");
		String vID = request.getParameter("videoID");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String msgAddComment ="";
		if (user != null) {
			if (comment.trim().length() > 0 && vID != null) {
				Video video = daoVideo.findByID(Long.valueOf(vID));
				if (video != null) {
					Comments cm = new Comments();
					cm.setContent(comment);
					cm.setUser(user);
					cm.setVideo(video);
					cm.setIsActive(true);
					cm.setCreated_at(new Timestamp(System.currentTimeMillis()));
					cm.setUpdate_by(null);
					if (daoComment.create(cm)) {
						List<Comments> listComment = daoComment.findAllByVideoID(cm);
						List<Video> listVideo = daoVideo.findAllVideoByMovies(video);
						request.setAttribute("video", video);
						request.setAttribute("listVideo", listVideo);
						request.setAttribute("listComment", listComment);
						
					}
				}
			}
		}else {
			msgAddComment = "Please login";
		}
		request.setAttribute("msgAddComment", msgAddComment);
		request.getRequestDispatcher("/Views/User/anime-comment-series.jsp").forward(request, response);

	}

	private void doPostComment(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String comment = request.getParameter("comment");
		String vID = request.getParameter("videoID");
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		if (user != null) {
			if (comment.trim().length() > 0) {

				Video video = daoVideo.findByID(Long.valueOf(vID));
				if(video != null) {
					Comments cm = new Comments();
					cm.setContent(comment);
					cm.setUser(user);
					cm.setVideo(video);
					cm.setIsActive(true);
					cm.setCreated_at(new Timestamp(System.currentTimeMillis()));
					cm.setUpdate_by(null);
					if (daoComment.create(cm)) {
						JSONObject JsonCM = new JSONObject();
						JsonCM.put("commentID", cm.getComment_id());
						JsonCM.put("content", cm.getContent());
						JsonCM.put("comment_user", cm.getUser().getCustomer_name());
						response.setStatus(201);
						response.getWriter().write(JsonCM.toString());
					} else {
						response.setStatus(400);
					}
				}
				
			} else {
				response.setStatus(400);
			}
		} else {
			response.setStatus(400);
		}
	}
}
