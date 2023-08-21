<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<div class="container">
		<div class="row">
			<div class="col-lg-12">
				<div class="containerVideo">
					<iframe id="frameVideo" allow="fullscreen;"
						class="containerVideo-iframe"
						src="https://www.youtube.com/embed/${video.video_url}"></iframe>
				</div>
				<div class="anime__details__episodes">
					<div class="section-title">
						<h5>List Name</h5>
					</div>
					<c:forEach items="${listVideo}" var="item" varStatus="loop">
						<button onclick="LoadVideoByVideoID('${item.video_id}')"
							class="btn btn-light">Ep ${loop.index+1}</button>
					</c:forEach>


				</div>
			</div>
		</div>

	</div>
	<div class="container">
		<div class="row ">
			<div class="col-lg-8 col-md-8">
				<div class="anime__details__review">
					<div class="section-title">
						<h5>Reviews</h5>
					</div>
					<c:forEach items="${listComment}" var="comment">
						<div class="anime__review__item">
							<div class="anime__review__item__pic">
								<img
									src="${pageContext.request.contextPath}/Template/Users/img/anime/review-1.jpg"
									alt="">
							</div>
							<div class="anime__review__item__text">
								<h6>
									${comment.user.customer_name } - <span>1 Hour ago</span>
								</h6>
								<c:if test="${comment.user.customer_id.equals(sessionScope.user.customer_id)}">
										<div class="commentTools float-right">
											<button style="cursor: pointer; " class="bg-dark">
												<i class="fa-solid fa-pen text-primary p-2"
													></i>
											</button>
											<button class="bg-dark" style="cursor: pointer" onclick="deleteComment(${comment.comment_id}, this)">
												<i class="fa-solid fa-trash text-warning p-2"
													></i>
											</button>
										</div>
									</c:if>
							</div>
						</div>
					</c:forEach>
				</div>
				<div class="anime__details__form">
					<div class="section-title">
						<h5>Your Comment</h5>
					</div>
					<form id="commentForm">
						<textarea name="comment" placeholder="Your Comment" required></textarea>
							<input type="hidden" id="videoID" name="videoID"
								value="${video.video_id}">
						<button type="button" onclick="addComment()">
							<i class="fa fa-location-arrow"></i> Review
						</button>
					</form>
				</div>
			</div>
		</div>
</div>
