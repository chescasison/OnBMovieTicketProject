<%@ include file="/WEB-INF/views/_taglibs.jspf" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="x-ua-compatible" content="ie=edge"><%--

	The above 2 meta tags *must* come first in the head;
	any other head content must come *after* these tags

--%>
<title>Welcome - Movie Tickets</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link href="<c:url value='/favicon.ico' />" rel="icon" type="image/png" />
<link type="text/css" rel="stylesheet" href="<c:url value="/webjars/bootstrap/css/bootstrap.min.css" />" />
<style type="text/css">
/* Provide space for fixed-top navbar */
body {
  padding-top: 4.5rem;
  min-height: 50rem;
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
		<!--<h1>Welcome</h1>
		<p>
		<% pageContext.setAttribute("today", java.time.LocalDate.now()); %>
		Today is <time datetime="${today}"><tags:formatLocalDate pattern="FULL" value="${today}" /></time>.
		<% pageContext.setAttribute("now", java.time.LocalTime.now()); %>
		And the time now is <time datetime="${now}"><tags:formatLocalTime pattern="SHORT" value="${now}" /></time>.
		</p>-->
		<!--<p><a class="btn btn-primary btn-lg" href="#" role="button">Let's create something awesome!</a></p>-->
		<div id="moviecardscontainer" class="container">
			<div class="row">
				<c:forEach items="${movieList}" var="movie">
					<div class="col-3">
						<div class="card h-100" style="width: 18rem;">
						  <img class="card-img-top" src="${movie.posterLink}" alt="Card image cap" style="height: 43vh">
						  <div class="card-body" >
							  <div> 
								<h5 class="card-title">${movie.title}</h5>
							  </div>
							  <div style=" height: 20vh;">
								<p class="card-text" style="text-overflow: ellipsis;"> ${movie.summary}</p>
							  </div>
						  </div>
						  <div style="margin: 5%">
							<center>
								<a href="<c:url value="/screenings/${movie.id} "/>" class="btn btn-primary">View Screening</a>
								<br>
							</center>
						  </div>
						</div>
					</div>
				</c:forEach>
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
