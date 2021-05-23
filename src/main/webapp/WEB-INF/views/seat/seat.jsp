<%@ include file="/WEB-INF/views/_taglibs.jspf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page import="java.util.*" %>
<%@ page import="movietickets.domain.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge"><%--

	The above 2 meta tags *must* come first in the head;
	any other head content must come *after* these tags

--%>
<title>Choose your seat</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link href="<c:url value='/favicon.ico' />" rel="icon" type="image/png" />
<link type="text/css" rel="stylesheet" href="<c:url value="/webjars/bootstrap/css/bootstrap.min.css" />" />
<style type="text/css">
/* Provide space for fixed-top navbar */
body {
  padding-top: 4.5rem;
  min-height: 50rem;
}

input{
  display: none;
}

label {
  display: inline;
  text-align: center;
  cursor: pointer
  width: 2vw
}

label.movieDetails{
	display: inline-block;
	 width: 140px;
	 text-align: right;
}

input[type="text"]{
	font-size: 1.8em;
}

textarea{
	font-size: 1.8em;
	border: none;
}

input:checked ~ label {
  background: green;
  color: #fff;
}
table, th, td {
  height: 1vh;
  width: 1vw;
  
  text-align: center;
  vertical-align: middle;
}

td.seat{
  border: 1px solid black;
}

tr.title{
	border: 1px solid black;
}

.seatLayout{
	padding: 3%;
}

.center{
	margin: auto;
	width: 100%;
}

.error-message{
	color: red;
}
</style>
</head>
<body>
	<nav class="navbar navbar-expand-md navbar-dark bg-dark fixed-top">
		<div class="container">
			<a class="navbar-brand" href="#">Movie Tickets</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar" aria-controls="navbars" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div id="navbar" class="collapse navbar-collapse">
				<ul class="navbar-nav ml-auto">
					<li class="nav-item active"><a class="nav-link" href="<c:url value="/" />">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="<c:url value="/about" />">About</a></li>
					<li class="nav-item"><a class="nav-link" href="<c:url value="/contact" />">Contact</a></li>
				</ul>
			</div><!--/.nav-collapse -->
		</div>
	</nav>
	<main class="container-fluid">
		<form action="/reserveSeat/${movieScreening.id}"  method="POST">
			<div class="container">
				<div class="row">
					<div class="col-3 center">
						<img class="card-img-top" src="${movieScreening.movie.posterLink}" alt="Card image cap" style="height: 80%;">
					</div>
					<div class="col-3 center">
						<div class="form-group row">
							<label for="movieTitle" class="col-sm-3 col-form-label center">Movie Title</label>
							<div class="col-sm-8">
							  <textarea type="text" rows="4" cols="15" readonly id="movieTitle">
							  ${movieScreening.movie}
							  </textarea>
							</div>
						</div>
						<div class="form-group row">
							<label for="cinema" class="col-sm-3 col-form-label center">Cinema</label>
							<div class="col-sm-8">
							  <input type="text" readonly class="form-control-plaintext" id="cinema" value="${movieScreening.cinema}">
							</div>
						</div>
						<div class="form-group row">
							<label for="date" class="col-sm-3 col-form-label center">Date</label>
							<div class="col-sm-8">
							  <input type="text" readonly class="form-control-plaintext" id="date" value="${movieScreening.scheduleDate}">
							</div>
						</div>
						<div class="form-group row">
							<label for="time" class="col-sm-3 col-form-label center">Time</label>
							<div class="col-sm-8">
							  <input type="text" readonly class="form-control-plaintext" id="time" value="${movieScreening.scheduleTime}">
							</div>
						</div>
						<div>
							<button class="btn btn-primary"> Get ticket </button>
						</div>
					</div>
					<div class="col-6">
						<div class="seatLayout center">
								<br>
								<table class="center">
									<tr class="title">
										<td colspan="20"> <h3>S C R E E N</h3> </td>
									</tr>
									<tr>
										<td colspan="20"> 
											<input type="checkbox" name="seat" value="-" style="border: 1px solid black; height: 3vh; width: 4vh; margin: 0%;" disabled/>
											<label style="color:white;">OO</label> 
										</td>
									</tr>
								<%
									int rows = 19; 
									int cols = 20; 
								   
									List<List<Integer>> a = new ArrayList<List<Integer>>();
									a.add(Arrays.asList(0,1,2,4,15,17,18,19)); // index 0 row A
									a.add(Arrays.asList(0,1,4,15,18,19)); // index 1 row B
									a.add(Arrays.asList(0,4,15,19)); // index 2 row C
									a.add(Arrays.asList(4,15)); // index 3 row D
									a.add(Arrays.asList(4,15)); // index 4 row E
									a.add(Arrays.asList(4,15)); // index 4 row F
									a.add(Arrays.asList(4,15)); // index 4 row G
									a.add(Arrays.asList(4,15)); // index 4 row H
									a.add(Arrays.asList(4,15)); // index 4 row I
									a.add(Arrays.asList(4,15)); // index 4 row J
									a.add(new ArrayList<>());
									a.add(Arrays.asList(4,15)); // index 4 row K
									a.add(Arrays.asList(4,15)); // index 4 row L
									a.add(Arrays.asList(4,15)); // index 4 row M
									a.add(Arrays.asList(4,15)); // index 4 row N
									a.add(Arrays.asList(4,5,6,7,8,9,10,11,12,13,14,15)); // index 4 row O
									a.add(Arrays.asList(4,15));// index 4 row P
									a.add(Arrays.asList(4,15)); // index 4 row Q
									a.add(Arrays.asList(4,15)); // index 4 row R
									a.add(Arrays.asList(4,15)); // index 4 row S
									
									Map<Integer, Character> rowLetters = new HashMap<>();
									char ch = 'A';
									for (Integer i=0; i < 19; i++){
										rowLetters.put(i, ch);
										ch += 1;
									}
									
									List<ReservedSeat> reservedSeats = (List<ReservedSeat>) request.getAttribute("reservedSeats");
									Long movieScreeningId = (Long) request.getAttribute("movieScreeningId");
									
									Character rowLetter;
									String seatNumber;
									for (int i=0; i < rows; i++){
										%>
										<tr>
										<%
										for (Integer j=0; j < cols; j++) {
												if (a.get(i).contains(j) || a.get(i).isEmpty()) { 
												%>
													<td>
														<input type="checkbox" name="seat" value="-" style="border: 1px solid black; height: 3vh; width: 4vh; margin: 0%;" disabled/>
														<label style="color:white;">OO</label>
													</td>
												<% } else { 
													rowLetter = rowLetters.get(i);
													Integer num = j + 1;
													seatNumber = rowLetter + num.toString();
													
													if (reservedSeats.contains(new ReservedSeat(new MovieScreening(movieScreeningId), String.valueOf(rowLetter), num))) {
														%>
														<td class="seat" style="background-color: red; color: white;">
															<input type="checkbox" id="<%= seatNumber %>" name="seat"   value="<%= seatNumber %>" style="border: 1px solid black; height: 3vh; width: 4vh; margin: 0%;" disabled/>
															<label for="<%= seatNumber %>"><%= seatNumber %></label>
														</td>
														<%
													} else {
														%>
														<td class="seat">
															<input type="checkbox" id="<%= seatNumber %>" name="seat"   value="<%= seatNumber %>" style="border: 1px solid black; height: 3vh; width: 4vh; margin: 0%;"/>
															<label for="<%= seatNumber %>"><%= seatNumber %></label>
														</td>
														<%
													}
												}
										}%>
										</tr>
										
										<%
									}
								%>
							</table>
						</div>
					</div>
				</div>
			</div>
		</form>
	</main>
		<footer class="footer">
			<div class="container">
				<p class="text-muted">&copy; Orange and Bronze Software Labs <tags:formatLocalDate pattern="YYYY" /></p>
			</div>
		</footer>
	<%-- Bootstrap core JavaScript ==================================================
		Placed at the end of the document so the pages load faster --%>
<script src="<c:url value='/webjars/jquery/jquery.min.js' />"></script>
<script src="<c:url value='/webjars/popper.js/umd/popper.min.js' />"></script>
<script src="<c:url value='/webjars/bootstrap/js/bootstrap.min.js' />"></script>
</body>
</html>
