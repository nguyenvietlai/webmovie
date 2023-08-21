<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<table class="table p-3 mt-2 w-100">
	<thead>
		<tr>
			<th scope="col">movie_id</th>

			<th scope="col">name_movie</th>
			<th scope="col">category_id</th>
			<th scope="col">Movie_img</th>
			<th scope="col">createBy</th>
			<th scope="col">movie_desc</th>
		</tr>
	</thead>
	<tbody>
		<c:if test="${movieVideo != null }">
			<c:forEach items="${movieVideo}" var="movie">
				<tr>
					<td>${movie.movie_id }</td>


					<td>${movie.name_movie }</td>
				
					<td>${movie.category.category_id }</td>
					<td>${movie.movie_img }</td>
					<td>${movie.createBy }</td>
					<td>${movie.movie_desc }</td>

					<td><button type="button" class="btn btn-danger"
													data-toggle="modal" data-target="#episode"
													id="ButtonOfSelectTag" onclick="addDataToModal('${movie.movie_id}')">Edit</button><td>
				</tr>
			</c:forEach>
			
		</c:if>
		
	</tbody>
</table>