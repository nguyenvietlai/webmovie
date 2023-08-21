package com.teamJava4.User.Controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.teamJava4.Entity.Report;
import com.teamJava4.Entity.User;
import com.teamJava4.Entity.Video;
import com.teamJava4.Implementations.ReportDAOIpl;
import com.teamJava4.Implementations.UserDAOIpl;
import com.teamJava4.Implementations.VideoDAOIpl;

/**
 * Servlet implementation class Login_LogOut_Regist_ForgetController
 */
@WebServlet({"/anime-logout", "/anime-login", "/anime-forget", "/anime-sign-up"})
public class Login_LogOut_Regist_ForgetController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAOIpl daoUser = new UserDAOIpl();
	private VideoDAOIpl daoVideo = new VideoDAOIpl();
	private ReportDAOIpl daoReport = new ReportDAOIpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login_LogOut_Regist_ForgetController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();

		String path = request.getServletPath();
		switch (path) {
		case "/anime-login":
			doGetLogin(request, response);
			break;
		case "/anime-logout":
			doGetLogOut(request, response, session);
			break;
		case "/anime-sign-up":
		
			doGetSignUp(request, response);
			break;
		case "/anime-forget":
			doGetForgetPassword(request, response);
			break;
		
		default:
			break;
		}
	}
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getServletPath();
		HttpSession session = request.getSession();
		switch (path) {

		case "/anime-login":
			doPostLogin(request, response, session);
			break;
		case "/anime-sign-up":
			doPostSignUp(request, response);
			break;
		case "/anime-forget":
			doPostForget(request, response);
			break;
		default:
			break;
		}
	}
	
	
	private void doGetLogOut(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		request.setAttribute("Account", true);
		session.invalidate();
		getHome(request, response);
	}

	
	
	private void doGetLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("Account", true);
		Cookie[] cookies = request.getCookies();
		String email = "";
		String password = "";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("email")) {
				email = cookie.getValue();
				if (cookie.getName().equals("password")) {
					password = cookie.getValue();
				}
			}
		}
		request.setAttribute("email", email);
		request.setAttribute("password", password);
		request.getRequestDispatcher("/Views/User/anime-login.jsp").forward(request, response);

	}
	
	private void doGetSignUp(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("Account", true);
		request.getRequestDispatcher("/Views/User/anime-sign-up.jsp").forward(request, response);

	}
	
	private void doGetForgetPassword(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/Views/User/anime-forgot-password.jsp").forward(request, response);

	}


	private void doPostLogin(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		String passwords = request.getParameter("passwords");
		User user1 = new User();
		user1.setEmail(email);
		user1.setPasswords(passwords);
		User user = daoUser.login(user1);
		if (user != null) {
			System.out.println(user.toString());
			if (user.getIsAdmin() == false) {
				session.setAttribute("user", user);
				getHome(request, response);
			} else {
				session.setAttribute("admin", user);
				response.sendRedirect("AdminHome");
			}
		} else {
			request.setAttribute("msg", "Login failed");
			request.getRequestDispatcher("/Views/User/anime-login.jsp").forward(request, response);
		}
	}
	
	private void doPostSignUp(HttpServletRequest request, HttpServletResponse response) {
		String customer_name = request.getParameter("customer_name");
		String passwords = request.getParameter("passwords");
		String email = request.getParameter("email");

		User user = new User();
		user.setEmail(email);
		user.setCustomer_name(customer_name);
		user.setPasswords(passwords);
		user.setIsAdmin(false);
		user.setIsActive(true);
		User user1 = daoUser.findByEmail(user);
		if (user1 == null) {
			if (daoUser.create(user)) {
				Cookie cookieEmail = new Cookie("email", email);
				Cookie cookiePassword = new Cookie("password", passwords);
				cookieEmail.setMaxAge(60);
				cookiePassword.setMaxAge(60);
				response.addCookie(cookieEmail);
				response.addCookie(cookiePassword);
				response.setStatus(201);
			} else {
				response.setStatus(400);
			}

		} else {
			response.setStatus(400);
		}

	}
	
	private void doPostForget(HttpServletRequest request, HttpServletResponse response) {
		String email = request.getParameter("email");
		User user = new User();
		user.setEmail(email);
		User user1 = daoUser.findByEmail(user);
		if (user1 == null) {
			response.setStatus(400);
		} else {
			user1.setPasswords("123456");
			sendEmail(user1);
			daoUser.update(user1);
			response.setStatus(201);
		}

	}
	
	private void sendEmail(User user) {
		String emailTo = user.getEmail();
		String subject = "Reset password from ANIME WEB";
		String content = "Hello " + user.getCustomer_name() + "\n Your new password is: 123456";

		Properties properties = new Properties();
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.setProperty("mail.transport.protocol", "smtp");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.starttls.port", "587");
		properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		final String fromEmail = "nganvps24932@fpt.edu.vn";
		// Mat khai email cua ban
		final String password = "cvgqxgbvpsztowwz";
		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(fromEmail, password);
			}
		});
		MimeMessage msg = new MimeMessage(session);
		// set message headers
		try {
			msg.setFrom(new InternetAddress(fromEmail));
			msg.setSubject(subject, "UTF-8");
			msg.setText(content, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, emailTo);
			msg.setReplyTo(msg.getFrom());
			Transport.send(msg);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getHome(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Video>listVideo = daoVideo.findTopVideoByView();
		List<Report>listMovie = daoReport.findAll();
		request.setAttribute("listVideo", listVideo);
		request.setAttribute("listMovie", listMovie);
		request.setAttribute("Home", true);
		request.getRequestDispatcher("/Views/User/anime-home.jsp").forward(request, response);
	}


}
