## O&B Java Web Application Development Guidelines

These minimal set of guidelines provide a common way in developing Java-based web applications. Note that the other *best practices* that have been introduced and taught in training courses also apply.

1. URL mapping
2. View template location
3. Post-Redirect-Get pattern
4. HTML &lt;form&gt; submit and cancel buttons
5. Browser/Client-side libraries
6. Stylesheets at the top, scripts at the bottom
7. Logging
8. Support internationalization/localization (I18N/L10N)
9. Java Persistence API (JPA)
10. Always stream, never keep fully in-memory

### URL Mapping

These are the *preferred* style of mappings for web/MVC applications. The table below uses `Member` as the sample entity/resource. Replace this with the entity/resource you are handling (e.g. customer, account). Note how the *plural* form is used in the path (e.g. customers, accounts), and in the controller class.

| HTTP Verb/Method | Path                 | Controller#Method          | Used to       
|------------------|----------------------|----------------------------|------------------------
| GET              | `/members`           | `MembersController#index`  | display a list of all members (*must support pagination*), query parameters can be used to filter or search
| GET              | `/members/{id}`      | `MembersController#show`   | display an existing member (with given unique identifier)
| GET              | `/members?create`    | `MembersController#create` | return an HTML form for creating/adding a new member
| POST             | `/members`           | `MembersController#save`   | create/add new member (as submitted HTML form from `/members?create`)
| GET              | `/members/{id}?edit` | `MembersController#edit`   | return an HTML form for editing an existing member (with given unique identifier)
| PUT              | `/members/{id}`      | `MembersController#update` | update an existing member (as submitted HTML form from `/members/{id}?edit`)
| DELETE           | `/members/{id}`      | `MembersController#delete` | delete an existing member with given unique identifier (i.e. cancel your membership)

There are cases when a controller may *not* follow the above pattern. For example, a `HomePageController` may simply retrieve from the database to display a dashboard or a welcome message, and does *not* display a paginated list of "home pages" for users to add, update, and remove. In these cases, suffix the controller name with `PageController` (e.g. `WelcomePageController`, `SignUpPageController`).

Please see [sample controller and test](controller.md).

However, most browsers *do not* support methods other than `GET` and `POST` when it comes to submitting HTML forms. The work-around for this is to use a hidden input named "\_method", which is set to reflect the desired method. This is typically implemented via a servlet filter. See [HiddenHttpMethodFilter](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/filter/HiddenHttpMethodFilter.html) for further details.

```html
<!-- in response to /members/51203?edit -->
<form action="/members/51203" method="POST">
	<input type="hidden" name="_method" value="PUT" />
	<!-- the rest of the form -->
</form>
```

This is made easier with Spring's JSP Form Tag Library (`spring-form.tld`).

```jsp
<!-- in response to /members/51203?edit -->
<form:form action="/members/51203" method="PUT" modelAttribute="…">
	<!-- Spring will automatically add the hidden input field for PUT -->
	<!-- the rest of the form -->
</form:form>
```

### View Template Locations 

The preferred way is to place JSPs under `/WEB-INF/views/{entity-or-resource-in-plural-form}`. For example, if we take *members* (from the [URL mapping conventions](#url-mapping) section), the view templates should be found under <code style="white-space: nowrap">/WEB-INF/views/members</code>. This way, the views cannot be accessed directly without going through the proper controllers (to prepare the model objects that the view renders).

| HTTP Verb/Method | Path                 | Controller#Method          | Uses view (assuming JSP)    
|------------------|----------------------|----------------------------|------------------------
| GET              | `/members`           | `MembersController#index`  | `/WEB-INF/views/members/index.jsp`
| GET              | `/members/{id}`      | `MembersController#show`   | `/WEB-INF/views/members/show.jsp`
| GET              | `/members?create`    | `MembersController#create` | `/WEB-INF/views/members/create.jsp` (when applicable, use common `_form.jsp`)
| POST             | `/members`           | `MembersController#save`   | 
| GET              | `/members/{id}?edit` | `MembersController#edit`   | `/WEB-INF/views/members/edit.jsp` (when applicable, use common `_form.jsp`)
| PUT              | `/members/{id}`      | `MembersController#update` | 
| DELETE           | `/members/{id}`      | `MembersController#delete` | 

The common `_form.jsp` and its usage looks like this.

```jsp
<%-- _form.jsp --%>
<form:form method="${param.method}" action="${param.action}" modelAttribute="member">
	<div class="form-group">
		<form:label path="..." />
		<form:input path="..." cssClass="form-control" />
	</div>
	<%-- add more inputs/buttons as needed --%>
</form:form>
```

```jsp
<%-- create.jsp --%>
<jsp:include page="_form.jsp">
	<jsp:param name="method" value="post" />
	<jsp:param name="action" value="/members" />
	<%-- add more parameters as needed --%>
</jsp:include>
```

```jsp
<%-- edit.jsp --%>
<jsp:include page="_form.jsp">
	<jsp:param name="method" value="put" />
	<jsp:param name="action" value="/members/${member.id}" />
	<%-- add more parameters as needed --%>
</jsp:include>
```

When using Apache Tiles, place the `tiles.xml` configuration under `/WEB-INF/views`. For example:

```
src/main/webapp/WEB-INF/views
	/_layouts
		/default
			/*.jsp
			/tiles.xml (optional)
	/_layouts
		/admin
			/*.jsp
			/tiles.xml (optional)
	/authors
		/*.jsp
		/tiles.xml (optional)
	/books
		/*.jsp
		/tiles.xml (optional)
	/members
		/*.jsp
		/tiles.xml (optional)
	/tiles.xml (required)
```

Note how the layout template JSPs are all placed under `_layouts` (prefixed with underscore `_` to avoid collision with a resource name). It is possible to have more than one layout.

It is *recommended* to support several `tiles.xml` files (to minimize code change conflicts). To do so, use `**` in the definintions configuration (see below).

```java
@Configuration
public class /* ... */ {

	@Bean
	public TilesViewResolver tilesViewResolver() {
		return new TilesViewResolver();
	}

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("/WEB-INF/views/**/tiles.xml"); // <-- to include tiles.xml files under subfolders
		return tilesConfigurer;
	}

}
```

While keeping multiple `tiles.xml` files is recommended, it may not be necessary with the use of wildcards. By default, [Apache Tiles supports wildcards in definition names](https://tiles.apache.org/framework/tutorial/advanced/wildcard.html). Wilcards help a lot in writing less code to declare your definitions. The configuration below makes it unnecessary to add new definitions all the time.

```xml
<!-- /WEB-INF/views/tiles.xml -->
<tiles-definitions>
	<definition name="default" template="/WEB-INF/views/_layouts/default/index.jsp">
		<!-- ... -->
	</definition>
	<definition name="*/*" extends="default"><!-- e.g. members/index -->
		<put-attribute name="titleKey" value="{1}.{2}.title" /><!-- e.g. members.index.title, members.create.title -->
		<put-attribute name="body" value="/WEB-INF/views/{1}/{2}.jsp" />
		<!-- {1},{2} correspond to the 1st and 2nd "*" of "*/*" request. -->
	</definition>
	<definition name="*/*/*" extends="default">
		<put-attribute name="titleKey" value="{1}.{2}.{3}.title" />
		<put-attribute name="body" value="/WEB-INF/views/{1}/{2}/{3}.jsp" />
		<!-- {1},{2},{3} correspond to the 1st, 2nd, and 3rd "*" of "*/*/*" request. -->
	</definition>
	<!-- more than three "*" is not impossible; but strongly discouraged -->
	<definition name="admin" template="/WEB-INF/views/_layouts/admin/index.jsp">
		<!-- ... -->
	</definition>
	<definition name="admin/*/*" extends="admin">
		<put-attribute name="titleKey" value="{1}.{2}.title" />
		<put-attribute name="body" value="/WEB-INF/views/admin/{1}/{2}.jsp" />
		<!-- {1},{2} correspond to the 1st and 2nd "*" of "*/*" request. -->
	</definition>
</tiles-definitions>
```

And here's the template JSP can look like. It's good practice to keep things parameterized (e.g. `styles`, `scripts`). Note how the tools with proper technique increase productivity (i.e. reduce repetitions, focus on what really matters).

```jsp
<%-- /WEB-INF/views/_layouts/default/index.jsp --%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<tiles:importAttribute name="titleKey" ignore="true" />
	<title><spring:message code="${titleKey}" /> - <spring:message code="app.name" /></title>
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<link rel="icon" href="<c:url value='/favicon.ico' />" type="image/png" />
	<tiles:importAttribute name="styles" ignore="true" />
	<c:forEach var="style" items="${styles}">
	<link rel="stylesheet" href="<c:url value="${style}"/>" />
	</c:forEach>
</head>
<body>
	<tiles:insertAttribute name="header" ignore="true" />
	<tiles:insertAttribute name="nav" ignore="true" />
	<tiles:insertAttribute name="body" />
	<tiles:insertAttribute name="footer" ignore="true" />
	<tiles:importAttribute name="scripts" ignore="true" />
<c:forEach var="script" items="${scripts}">
<script src="<c:url value="${script}"/>"></script>
</c:forEach>
</body>
</html>
```

For JSP views, it is preferred to use custom tags to replace scripting elements or often-repeated elements in presentation pages. JSP custom tags used to be quite difficult to write. But with the arrival of *tag files* in JSP 2.0 in 2005, there is a better, faster and easier way to build custom tags. The standard location for custom JSP 2 tag files is `/WEB-INF/tags`. Place the tag files under a subfolder, and *use the subfolder name as the prefix* (or namespace). For example,

```
/WEB-INF/tags
    /menu
        category.tag
        item.tag
        menu.tag
    /util
        pagination.tag
```

```jsp
<%@ taglib tagdir="/WEB-INF/tags/menu" prefix="menu" %>
<%@ taglib tagdir="/WEB-INF/tags/util" prefix="util" %>
...
<menu:menu ...>
    <menu:category ...>
        <menu:item ... />
        <menu:item ... />
    </menu:category>
    <menu:category ...>
        <menu:item ... />
    </menu:category>
</menu:menu>
...
<util:pagination page="..." url="..." />
```

Given the above conventions, the resulting folder structure is shown below (with example files).

```
/WEB-INF
    /tags
        /menu
            category.tag
            item.tag
            menu.tag
        /util
            pagination.tag
    /views
        /members
            index.jsp
```

### Post-Redirect-Get Pattern

When processing `POST` requests, the *preferred* way of handling is to respond with a redirect to a `GET`. This follows the [post-redirect-get](http://en.wikipedia.org/wiki/Post/Redirect/Get) pattern to prevent duplicate form submissions when the user refreshes the response of the original HTTP POST.

Note that form submissions (via `POST`/`PUT`/`DELETE`) that *result into errors* should still provide a response to the original request (do not redirect). When the form submission succeeds, then redirect to a `GET`. 

### HTML `<form>` Submit and Cancel Buttons

The *preferred* way is to use links for form buttons that do not require the form to be submitted.

```html
<form action="/photos/51203" method="POST">
	<input type="hidden" name="_method" value="PUT" />
	<!-- input fields -->
	<input class="btn btn-success" type="submit" value="Save changes" />
	<!-- <input type="submit" value="Discard changes" /> -->
	<a class="btn btn-secondary" href="/photos">Discard changes</a>
</form>
```

Don't worry! The CSS can (and should) display the "cancel" link to look like a button.

### Browser/Client-side Libraries (e.g. jQuery and Bootstrap)

The *preferred* way is to use [WebJars](http://www.webjars.org/). These are mapped via `/webjars` in the URL, and then located at `META-INF/resources/webjars` in the classpath. For other static application-specific resources (e.g. images, JavaScript, CSS), use the common resource locations provided by Spring Boot auto-configuration.

### Stylesheets at the Top, Scripts at the Bottom

A typical HTML template would look something like:

```jsp
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>...</title>
<meta name="viewport" content="width=device-width,initial-scale=1">
<link href="<c:url value='/favicon.ico' />" rel="icon" type="image/png" />
<link href="<c:url value='/webjars/bootstrap/.../css/bootstrap.min.css' />" rel="stylesheet" type="text/css" media="all" />
...
</head>
<body>
<nav class="navbar">
	<div class="container">&hellip;</div>
</nav>
<div class="container" role="main">
...
</div><%--
Bootstrap core JavaScript ==================================================
Placed at the end of the document so the pages load faster --%>
<script src="<c:url value='/webjars/jquery/.../jquery.min.js' />"></script>
<script src="<c:url value='/webjars/bootstrap/.../js/bootstrap.min.js' />"></script><%--
  Include all compiled plug-ins (below), or include individual files as needed
--%>
</body>
</html>
```

Note the following:

1. The charset declaration `<meta charset="utf-8">` *must* come first in the head. Please refer to [HTML5 boilerplate](https://html5boilerplate.com/) for more information.
2. Stylesheets at the top to make the pages *appear* to load faster.
3. <a href="https://developer.yahoo.com/performance/rules.html#js_bottom">Script (`<script>`) tags are placed at the end of the document</a>, so the pages *appear* to load faster.
4. Browser/client-side libraries are under the `/webjars` path.

### Logging

The *preferred* approach to logging is to use [Simple Logging Facade for Java (SLF4J)](http://www.slf4j.org/) with [Logback](http://logback.qos.ch/) as implementation.

### Internalization/Localization (I18N/L10N)

It is a good practice to use `<fmt:message key="...">` or `<spring:message code="...">` tags for static text/labels in dynamic HTML pages. When displaying data, it would be good to format them appropriately (e.g. add thousand and decimal separators to numeric values).

While there is no right or wrong way of naming keys/codes, it helps to follow a naming convention that prevents collision. Here is a suggested naming convention.

For entity/resource, use `[entity/resource].[field].label`. For example:

```properties
Member.label=Member
Member.plural.label=Members
Member.name.label=Name
#Member.name.input.placeholder=
Member.birthDate.label=Date of birth
#Member.birthDate.input.placeholder=
```

And in the view, it would be used as:

```jsp
<form:form id="_member" ... modelAttribute="member">
	<div class="form-group">
		<form:label for="_member_name"><spring:message code="Member.name.label" /></form:label>
		<form:input id="_member_name" path="name" cssClass="form-control" cssErrorClass="form-control is-invalid" />
		<form:errors id="_member_name_error" path="name" cssClass="invalid-feedback" element="div" />
	</div>
</form:form>
```

For validation error messages, use `[@annotation|typeMismatch|required].object.field`. For example:

```java
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Past;
...
public class Member {
	@NotBlank private String name;
	@Past private LocalDate birthDate;
	// getters and setters
}
```

```properties
Member.name.label=Name
Member.birthDate.label=Date of birth
# ...
# Keep error messages together with the labels
NotBlank.member.name=Name must not be blank
Past.member.birthDate=Date of birth must be in the past
```

Please refer to [DefaultMessageCodesResolver](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/DefaultMessageCodesResolver.html) for more information on the codes that are generated for message lookup. Favor using JSR-303/JSR-349 (Bean Validation API) over creating custom implementations of Spring&apos;s `Validator` interface. Spring Framework 3.x introduced support for Bean&nbsp;Validation&nbsp;1.0&nbsp;(JSR-303), and 4.x introduced support for Bean&nbsp;Validation&nbsp;1.1&nbsp;(JSR-349), by adapting it to Spring&apos;s `Validator` interface. For general information on JSR-303/JSR-349, see the [Bean Validation website](https://beanvalidation.org/). For information on the specific capabilities of the default reference implementation, see the [Hibernate Validator](https://hibernate.org/validator/) documentation. The default messages of Hibernate Validator can be seen in `ValidationMessages.properties` (under `org.hibernate.validator` package of dependency).

For general labels (e.g. headings, navigation links, buttons), use `[path].[view].[component|subcomponent]`. For example:

```properties
app.name=

## Common (on all pages)
# Dialog (on all pages)
dialog.ok=OK
dialog.close=Close
# Pagination (on all pages)
nav.first=
nav.previous=
nav.next=
nav.last=
# Navigation (on all pages)
nav.home=
nav.about=
nav.contact=
nav.login=
nav.logout=

## WEB-INF/views/members/*.jsp
# WEB-INF/views/members/index.jsp
members.index.title=Members
members.index.nav.create=Add
members.index.nav.find.form.submit=Search
members.index.nav.find.form.input.placeholder=
#members.index.items.bulk.delete=
#members.index.items.bulk.delete.confirm=
members.index.item.edit=Edit
members.index.item.delete=Delete
#members.index.item.delete.confirm=Are you sure you want to delete {0}?
# WEB-INF/views/members/create.jsp
members.create.title=Add New Member
members.create.nav.index=Back
members.create.form.submit=Add
members.create.form.submit.busy=Adding...
# WEB-INF/views/members/edit.jsp
members.edit.title=Edit Member
members.edit.form.submit=Save
members.edit.form.submit.busy=Saving...
# WEB-INF/views/members/show.jsp
members.show.nav.edit=Edit
members.show.nav.index=Back
```

And in the view, it would be used as:

```jsp
<%-- members/show.jsp --%>
<nav>
	<ul class="nav">
		<li class="nav-item">
			<spring:url var="editUrl" value="/members/{id}">
				<spring:param name="id" value="${member.id}" />
				<spring:param name="edit" value="" />
			</spring:url>
			<a href="${editUrl}" class="nav-link"><spring:message code="members.show.nav.edit" /></a>
		</li>
		<li class="nav-item">
			<spring:url var="indexUrl" value="/members" />
			<a href="${indexUrl}" class="nav-link"><spring:message code="members.show.nav.index" /></a>
		</li>
	</ul>
</nav>
<h1><c:out value="${member.name}" /></h1>
<!-- ... -->
```

### Java Persistence API (JPA)

O&B has historically *preferred* using Hibernate as the JPA vendor. And until today, we find no significant reason to change our preferences. Nonetheless, the recommended way is to use JPA, and avoid Hibernate-specific implementations as much as possible.

JPA requires at least a protected zero-arguments constructor. When using a domain model to handle complicated and ever-changing business rules, and you are implementing a domain entity that does not allow a zero-arguments constructor, it is *preferred* that you comment the zero-arguments constructor like below. That way, team mates can understand that the zero-arguments constructor is only for JPA/ORM, and is not meant to be used otherwise.

```java
@Entity
public class SomeBusinessEntity {
	@Id ... private Long id;
	public SomeBusinessEntity(Object arg1, Object arg2) {...}
	// other methods/operations
	protected SomeBusinessEntity() { /* as needed by JPA/ORM */ }
}
```

### Always Stream, Never Keep Fully In-Memory

Downloading various files (either text or binary) is a *must* in enterprise applications. PDF documents, attachments, media, executables, CSV, very large files, etc. Almost every application, sooner or later, will have to provide some form of download. 

One of the biggest scalability issues is loading the whole file into memory before streaming it. Loading full file into `byte[]` to later return it, is unpredictable and doesn't scale. Instead, stream the contents (i.e. use `InputStream` and `OutputStream`). Or, return a `Resource` from a Spring MVC `@Controller` handler method. You can use `FileSystemResource` to serve `java.io.File`.

When downloading is implemented in terms of HTTP, it is important to fully embrace this protocol and take full advantage of it. Consider the following HTTP headers when serving files:

- `Content-Type`
- `Last-Modified` and `If-Modified-Since`, or `ETag` and `If-None-Match`
- `Content-Length`
- `Range` in the request (sent by client when asking for parts of the file) and `Content-Range` in the response
