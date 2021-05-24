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
.btn{
	width: 100%;
}
.row{
	margin: 3%;
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
		<% pageContext.setAttribute("today", java.time.LocalDate.now()); %>
		<% pageContext.setAttribute("now", java.time.LocalTime.now()); %>
		<div id="moviecardscontainer" class="container">
			<h1> Welcome, Admin! </h1>
			<div>
				<form action="/admin/newMovieNewScreening" method="POST">
					<div class="container-fluid">
						<div class="row">
							<div class="col-6">
								<div class="form-group">
									<label for="title">Movie Title</label>
									<input id="title" class="form-control col-md-12" type="text" name="title" required>
								</div>
								<div class="form-group">
									<label for="summary">Summary</label>
									<textArea id="summary" class="form-control col-md-12" type="text" name="summary" required>
									</textArea>
								</div>
								<div class="form-group">
									<label for="directors">Directors</label>
									<input id="directors" class="form-control col-md-12" type="text" name="directors" required>
								</div>
								<div class="form-group">
									<label for="actors">Actors</label>
									<input id="actors" class="form-control col-md-12" type="text" name="actors" required>
								</div>
							</div>
							<div class="col-6">
								<div class="form-group">
									<label for="runningTime">Running Time</label>
									<input id="runningTime" class="form-control col-md-12" type="text" name="runningTime" required>
								</div>
								<div class="form-group">
									<label for="posterLink">Poster Link</label>
									<input id="posterLink" class="form-control col-md-12" type="text" name="posterLink" required>
								</div>
								<div class="form-group">
									<label for="trailerLink">Trailer Link</label>
									<input id="trailerLink" class="form-control col-md-12" type="text" name="trailerLink" required>
								</div>
								<div class="form-group">
									<label for="schedule">Schedule (date and time):</label>
									<input type="datetime-local" class="form-control col-md-12" id="schedule" name="schedule" required>
								</div>
								<div class="form-group">
									<input type="radio" id="cinema1" name="cinema" value="1"checked>
									<label for="cinema1">Cinema 1</label>
									<input type="radio" id="cinema2" name="cinema" value="2">
									<label for="cinema2">Cinema 2</label>
									<input type="radio" id="cinema3" name="cinema" value="3">
									<label for="cinema3">Cinema 3</label>
								</div>
							</div>
						</div>
						<div class="row">
							<button class="btn btn-primary">Submit</button>
							<p class="error-message"> ${errorMessage} </p>
						</div>
					</div>
				</form>
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
