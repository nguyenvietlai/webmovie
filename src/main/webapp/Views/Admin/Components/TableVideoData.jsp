<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${listVideo != null}">
	<c:forEach items="${listVideo}" var="video">
		<tr>
			<th scope="row">${video.video_id}</th>
			<td>${video.video_title}</td>
			<td>${video.video_url}</td>
			<td>${video.descriptions}</td>
			<td>${video.createBy}</td>
			<td>${video.updateBy}</td>
			<td>${video.view_count}</td>
			<td>${video.view_share}</td>
			<td>${video.isActive}</td>
			<td>${video.movie.movie_id}</td>
			<td><button type="button" class="btn btn-info">Edit</button></td>
		</tr>
	</c:forEach>
</c:if>