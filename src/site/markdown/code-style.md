# Code Style

## Introduction

This document defines the coding standards for source files in this programming assignment.

The structure of this document is based on the [Google Java Style Guide](https://google.github.io/styleguide/javaguide.html), [Spring Framework](https://github.com/spring-projects/spring-framework/wiki/Code-Style), and [Spring Boot](https://github.com/spring-projects/spring-boot/blob/master/CONTRIBUTING.adoc#code-conventions-and-housekeeping) projects, and is a work in progress.

### JPQL

For **JPQL**, simply use uppercase for reserved keywords like `SELECT` and `FROM`.

### SQL

For **SQL** (including DDL scripts), here are the formatting rules:

- Avoid reserved words. Avoid using any word that is considered a reserved word in the database that you are using.
- Always use uppercase for the reserved keywords like `SELECT` and `WHERE`.
- Identifiers should be written entirely in lower case. This includes tables, views, column, and other objects. Use underscores to separate words. Object names that are comprised of multiple words should be separated by underscores (ie. [snake case](https://en.wikipedia.org/wiki/Snake_case)). E.g. `first_name`, not `"First_Name"`.
- Single column primary key fields should be named `id`. Foreign key fields should be a combination of the name of the referenced table (singular form) and the name of the referenced fields. For single column foreign keys (by far the most common case) this will be something like `team_id`.
- Singular or plural table names?
	- Using plural table names avoid colliding with reserved keywords (e.g. `orders` since `ORDER BY` is reserved).
	- However, using singular table names keeps it consistent with Java classes.
	- Just pick one and use it consistently.

Refer to [SQL Style Guide](https://www.sqlstyle.guide/) for everything else.

## Package Structure

Having a common package structure would allow for developers familiar with one project to immediately feel at home in another project. To keep things simple, we *intentionally skip* the use of a domain that is typically used for uniqueness (e.g. `org.springframework`, `com.oracle`). But this is by no means what you would do in a real-world project.

For this programming assignment, please conform to the following package structure:

- `movietickets.application`
	- This layer is well suited for spanning *transactions*, and *security*.
	- If the application-layer service simply delegates to a repository (e.g. service exposes a `save()` method that delegates to a method of a repository), then this service is not needed and can/should be removed. But if the service starts a transaction (e.g. `@Transactional`) and delegates to two or more repositories, then the service is doing something useful and can/should be retained.
	- The application layer is responsible for driving the workflow of the application, matching the use cases at hand. These operations are interface-independent (i.e. independent of either REST or HTML/HTTP) and can be both synchronous or message-driven.
	- The application layer is thin in terms of domain logic &mdash; it merely coordinates the domain layer objects to perform the actual work. 
- `movietickets.domain`
	- The domain layer is the heart of the software, and this is where the interesting stuff happens. There can be one subpackage per aggregate (e.g. `movietickets.domain.cargo`), and to each aggregate belongs entities, value objects, domain events, a repository interface, and sometimes factories.
	- *Do not* place repository interfaces or exceptions in a separate package. Keep them as close as possible to the aggregates they work with.  
- `movietickets.infrastructure.config`
	- Place your `@Configuration` classes here (e.g. `WebMvcConfiguration`, `WebSecurityConfiguration`)
- `movietickets.infrastructure.persistence`
	- Place any custom repository implementations here (that Spring Data JPA does not handle) 
- `movietickets.infrastructure.web.mvc`
	- Place your `@Controller` classes here. Also include *view models* (e.g. form objects) here. By *view models*, we mean they're not domain models, and are specifically used by the view technology (JSP in this case).
	- Feel free to use repositories (and the aggregates they handle) and application-layer services.
- `movietickets.infrastructure.web.api`
	- Place your `@RestController` classes here (if any). Also include DTO-serializing here.

## Source File Basics

### File encoding: UTF-8

Source files must be encoded using UTF-8.

### Indentation

- Indentation uses tabs (not spaces)
- Unix (LF), not DOS (CRLF) line endings
- Eliminate all trailing whitespace
	- On Linux, Mac, etc.: `find . -type f -name "*.java" -exec perl -p -i -e "s/[ \t]$//g" {} \;`

## Source file structure (Java)

A source file consists of the following, in this exact order:

1. Package statement
2. Import statements
3. Exactly one top-level class

Exactly one blank line separates each of the above sections

### Import statements

The import statements are structured as follow:

- import static (all static imports in one block)
- blank line
- import `java.*`
- blank line
- import `javax.*`
- blank line
- import `org.*`
- blank line
- import `com.*`
- blank line
- import all other imports

Static imports should not be used in production code. They should be used in test code, especially for things like `import static org.assertj.core.api.Assertions.*;`.

### Java source file organization

The following governs how the elements of a source file are organized:

1. static fields
2. normal fields
3. constructors
4. (private) methods called from constructors
5. static factory methods
6. JavaBean properties (i.e., getters and setters)
7. method implementations coming from interfaces
8. private or protected templates that get called from method implementations coming from interfaces
9. other methods
10. `equals`, `hashCode`, and `toString`

Note that private or protected methods called from method implementations should be placed immediately below the methods where they're used. In other words if there 3 interface method implementations with 3 private methods (one used from each), then the order of methods should include 1 interface and 1 private method in sequence, not 3 interface and then 3 private methods at the bottom.

Above all, the organization of the code should feel *natural*.

## Formatting

### Braces

#### Block-like constructs: K&R style

Braces mostly follow the Kernighan and Ritchie style (a.k.a., "Egyptian brackets") for nonempty blocks and block-like constructs:

- No line break before the opening brace but prefixed by a single space
- Line break after the opening brace
- Line break before the closing brace
- Line break after the closing brace if that brace terminates a statement or the body of a method, constructor, or named class
- Line break before else, catch and finally statements

Example:

```java
return new MyClass() {
	@Override 
	public void method() {
		if (condition()) {
			something();
		}
		else {
			try {
				alternative();
			} 
			catch (ProblemException ex) {
				recover();
			}
		}
	}
};
```

### Line wrapping

90 characters is the preferred line length we aim for. In some cases the preferred length can be achieved by refactoring code slightly. In other cases it's just not possible.

90 is not a hard limit. Lines between 90-105 are perfectly acceptable in many cases where it aids readability and where wrapping has the opposite effect of reducing readability. This is a judgement call and it's also important to seek consistency. Many times you can learn by looking how specific situations are handled in other parts of the code.

Lines between 105-120 are allowed but discouraged and should be few.

No lines should exceed 120 characters.

The one big exception to the above line wrapping rules is Javadoc where we aim to wrap around 80 characters for maximum readability in all kinds of contexts, e.g. reading on Github, on your phone, etc.

When wrapping a lengthy expression, 90 characters is the length at which we aim to wrap. Put the separator symbols at the end of the line rather on the next line (comma separated arguments, etc). For instance:

```java
if (thisLengthyMethodCall(param1, param2) && anotherCheck() &&
        yetAnotherCheck()) {

    // ....
}
```

### Blank Lines

Add two blank lines before the following elements:

- `static {}` block
- Fields
- Constructors
- Inner classes

Add one blank line after a method signature that is multiline, i.e.

```java
@Override
protected Object invoke(FooBarOperationContext context, 
        AnotherSuperLongName name) {

    // code here
}
```

For inner-classes, extra blank lines around fields and constructors are typically not added as the inner class is already separated by 2 lines, unless the inner class is more substantial in which case the 2 extra lines could still help with readability.

## Class declaration

Try as much as possible to put the implements, extends section of a class declaration on the same line as the class itself.

Order the classes so that the most important comes first.

## Naming

### Constant names

Constant names use `CONSTANT_CASE`: all uppercase letters, with words separated by underscores.

Every constant is a `static final` field, but not all `static final` fields are constants. Constant case should therefore be chosen only if the field **is really** a constant.

Example:

```java
// Constants
private static final Object NULL_HOLDER = new NullHolder();
public static final int DEFAULT_PORT = -1;

// Not constants
private static final ThreadLocal<Executor> executorHolder = new ThreadLocal<Executor>();
private static final Set<String> internalAnnotationAttributes = new HashSet<String>();
```

### Variable names

Avoid using single characters as variable names. For instance, prefer `Method method` and *not* `Method m`.

## Programming Practices

### File history

- A file should look like it was crafted by a single author, not like a history of changes
- Don't artificially spread things out that belong together

### Organization of setter methods

Choose wisely where to add a new setter method; it should not be simply added at the end of the list. Perhaps the setter is related to another setter or relates to a group. In that case it should be placed near related methods.

- Setter order should reflect order of importance, not historical order
- Ordering of fields and setters should be consistent

### Ternary Operator

Wrap the ternary operator within parentheses, i.e. `return (foo != null ? foo : "default");`

Also make sure that the *not null* condition comes first.

### Null Checks

Use the `org.springframework.util.Assert.notNull` static method to check that a method argument is not null. Format the exception message so that the name of the parameter comes first with its first character capitalized, followed by "must not be null". For instance

```java
public void handle(Event event) {
    Assert.notNull(event, "Event must not be null");
    //...
}
```

### Use of @Override

Always add `@Override` on methods overriding or implementing a method declared in a super type.

### Utility classes

A class that is only a collection of static utility methods must be named with a `Utils` suffix, must have a `private` default constructor, and must be `abstract`. Making the class `abstract` and providing a `private` default constructor prevents anyone from instantiating it. For example:

```java
public abstract MyUtils {

    private MyUtils() {
        /* prevent instantiation */
    }

    // static utility methods
}
```

### Field and method references

A field of a class should always be referenced using this. A method of class, however, should never be referenced using this.

## Javadoc

### Javadoc formatting

The following template summarizes a typical use for the Javadoc of a method.

```java
/**
 * Parse the specified {@link Element} and register the resulting
 * {@link BeanDefinition BeanDefinition(s)}.
 * <p>Implementations must return the primary {@link BeanDefinition} that results
 * from the parsing if they will ever be used in a nested fashion (for example as
 * an inner tag in a {@code <property/>} tag). Implementations may return
 * {@code null} if they will <strong>not</strong> be used in a nested fashion.
 * @param element the element that is to be parsed into one or more {@link BeanDefinition BeanDefinitions}
 * @param parserContext the object encapsulating the current state of the parsing process;
 * provides access to a {@link org.springframework.beans.factory.support.BeanDefinitionRegistry}
 * @return the primary {@link BeanDefinition}
 */
BeanDefinition parse(Element element, ParserContext parserContext);
```

In particular, please note:

- Use an imperative style (i.e. *Return* and not *Returns*) for the first sentence.
- No blank lines between the description and the parameter descriptions.
- If the description is defined with multiple paragraphs, start each of them with `<p>`.
- If a parameter description needs to be wrapped, do not indent subsequent lines (see `parserContext`).
- The Javadoc of a class has some extra rules that are illustrated by the sample below:

```java
/*
 * Interface used by the {@link DefaultBeanDefinitionDocumentReader} to handle custom,
 * top-level (directly under {@code <beans/>}) tags.
 *
 * <p>Implementations are free to turn the metadata in the custom tag into as many
 * {@link BeanDefinition BeanDefinitions} as required.
 *
 * <p>The parser locates a {@link BeanDefinitionParser} from the associated
 * {@link NamespaceHandler} for the namespace in which the custom tag resides.
 *
 * @author Rob Harrop
 * @see NamespaceHandler
 * @see AbstractBeanDefinitionParser
 */
```

- In contrast to method-level Javadoc, the paragraphs of a class description are separated by blank lines.

The following are additional general rules to apply when writing Javadoc:

- Use `{@code}` to wrap code statements or values such as `null`.
- If a type is only referenced by a `{@link}` element, use the fully qualified name in order to avoid an unnecessary `import` declaration.

## Tests

### Testing Framework

Tests must be written using JUnit Jupiter (a.k.a., JUnit 5).

### Naming

Each test class name must end with a `Tests` suffix.

### Assertions

Use AssertJ for assertions.

### Mocking

Use the BDD Mockito support.
