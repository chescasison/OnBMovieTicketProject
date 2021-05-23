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
		<div class="container-fluid">
			<div class="row">
				<div class="col-sm-6">
					<div class="row">
						<iframe width="100%" height="350" src="${movie.trailerLink}"></iframe>
					</div>
				</div>
				<div class="col-sm-6">
					<p> 
						<span style="font-size: 2em; font-weight: bold"> ${movie} </span>
						<br>
						<span style="color: #A9A9A9">${movie.runningTime}mins | Rated G </span>
						<br>
						<br>
						<span> Directed by ${movie.directors} </span>
						<br>
						<span> Starring ${movie.actors} </span>
						<br>
						<br>
						<h5> Synopsis </h5>
						<span> Starring ${movie.summary} </span>
					</p>
					<br>
				</div>
			</div>
			<div class="row" style="height: 50vh;">
				<div class="col-6 h-100 card" style="padding-top: 2%; overflow-y:auto;">
							<h5>Showing Today</h5>
							<c:forEach items="${today_screenings}" var="screening">
									<div class="card" style="width: 18rem; background: 	#f9f6f7; margin-bottom: 1%;">
									    <div class="card-body" >
											<div> 
												<h6 class="card-title">Cinema ${screening.cinema}</h6>
												<h6 class="card-title">${screening.scheduleTime}</h6>
											</div>
											<div>
												<a href="<c:url value="/reserveSeat/${screening.id} "/>" class="btn btn-primary">View Seats</a>
											</div>
										</div>
									</div>
							</c:forEach>
						</div>
				<div class="col-sm-6 card" style="padding-top: 2%; overflow-y:auto;">
					<h5>Next Screening</h5>
					<c:forEach items="${soon_screenings}" var="screening">
					<div class="col-3" style="margin-bottom: 1%;">
						<div class="card h-100" style="width: 18rem; background: #f9f6f7; ">
						  <div class="card-body" >
							  <div> 
								<h6 class="card-title">Cinema ${screening.cinema}</h6>
								<h6 class="card-title">${screening.scheduleDate}</h6>
								<h6 class="card-title">${screening.scheduleTime}</h6>
							  </div>
							</div>
						</div>
					</div>
					</c:forEach>
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
