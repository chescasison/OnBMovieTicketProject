<%@ include file="/WEB-INF/views/_taglibs.jspf" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge"><%--

	The above 2 meta tags *must* come first in the head;
	any other head content must come *after* these tags

--%>
<title>Confirmation of Booking</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link href="<c:url value='/favicon.ico' />" rel="icon" type="image/png" />
<link type="text/css" rel="stylesheet" href="<c:url value="/webjars/bootstrap/css/bootstrap.min.css" />" />
<style type="text/css">
/* Provide space for fixed-top navbar */
body {
  padding-top: 4.5rem;
  min-height: 50rem;
}

.confirmation{
	border: 2px solid black;
	padding: 5%;
}

.confirmation-body{
	background-color: #f6f6f6;
	height: 60vh;
	padding: 5%;
}

table, tr, td{
	border: 1px solid 	#d0d0d0;
	padding: 2%;
}

td{
	width: 30vh;
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
	<main class="container">
		<div class="container confirmation">
			<div class="row">
				<div class="col-12">
					<h3> RESERVATION SUMMARY </h3>
					<br>
				</div>
			</div>
			<div class="row">
				<div class="container confirmation-body">
					<div class="row">
						<div class="col-1"></div>
						<div class="col-4">
							<img class="card-img-top" src="${movieScreening.movie.posterLink}" alt="Card image cap" style="height: 43vh">
						</div>
						<div class="col-1"></div>
						<div class="col-6">
							<form action="/confirmAndReserve/${movieScreening.id}" method="POST">
							
							<table>
								<tr>
									<td colspan="2">
										<h5> Title: ${movieScreening.movie} </h5>
									</td>
								</tr>
								<tr>
									<td>
										<h5> Ticket/s: </h5>
									</td>
									<td>
										<h5>
										<%
											List<String> seatNumbers = (List<String>) request.getAttribute("seatNumbers");
											int size = seatNumbers.size();
										%>
										
										<%= size %>
										<input type="text" name="seatNumbers" value="<%= seatNumbers %>" hidden/>
										</h5>
									</td>
								</tr>
								<tr>
									<td>
										<h5> Seat/s: </h5>
									</td>
									<td>
										<h5> <c:forEach items="${seatNumbers}" var="seatNumber"> <span> ${seatNumber} </span>  </c:forEach> </h5>
									</td>
								</tr>
								<tr>
									<td>
										<h5> Cinema: </h5>
									</td>
									<td>
										<h5> ${movieScreening.cinema} </h5>
									</td>
								</tr>
								<tr>
									<td>
										<h5> Date: </h5>
									</td>
									<td>
										<h5> ${movieScreening.scheduleDate}  </h5>
									</td>
								</tr>
								<tr>
									<td>
										<h5> Time: </h5>
									</td>
									<td>
										<h5> ${movieScreening.scheduleTime} </h5>
									</td>
								</tr>
							</table>
							<br>
							<button type="submit" class="btn btn-primary"> Confirm and Print Ticket</button>
							<p class="error-message"> ${errorMessage} </p>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
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
