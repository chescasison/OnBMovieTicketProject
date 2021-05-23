## Sample Spring MVC Controller

Following the said [URL mapping conventions](web-development-guidelines.md#url-mapping), the controller class looks something like this (see below). Note that it is *recommended* to make the class and the methods *package private*. This helps keep the class less cluttered. Making them *package private* does *not* hinder testing, since the tests are in the same package.

```java
package /* ... */ webmvc;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
// ...
@Controller
@RequestMapping("/members")
class MembersController {
	// ...
	@RequestMapping
	String index(Pageable pageable, Model model) {
		// ...
		// Note: Use a projection! Not all fields will be needed.
		// Page<MemberSummary> page = this.repository.findAll...By(pageable);
		// Or, this.repository.findAll(pageable) if entity is simple and all fields are needed
		model.addAttribute("membersPage" /*, page */);
		model.addAttribute("members" /*, page.getContents() */);
		return "members/index";
	}

	@GetMapping("/{id}")
	String show(@PathVariable("id") /* ... */ id, Model model) {
		// find by id
		model.addAttribute("member" /*, ... */);
		return "members/show";
	}

	@GetMapping(params="create")
	String create(Model model) {
		// ...
		model.addAttribute("member" /*, ... */);
		return "members/create";
	}

	@PostMapping
	String save(@Valid /* ... */, BindingResult bindingResult,
			RedirectAttributes redirectAttrs /*, ... */) {
		if (bindingResult.hasErrors()) {
			return "members/create";
		}
		// persist new entity
		/* ... */ newEntity = this.repository.save(/* ... */);
		redirectAttrs.addAttribute("id", newEntity.getId());
		return "redirect:/members/{id}"; // redirect to show newly persisted entity
	}

	@GetMapping(path="/{id}", params="edit")
	String edit(@PathVariable("id") /* ... */ id, Model model) {
		// ...
		model.addAttribute("member" /*, ... */);
		return "members/edit";
	}

	@PutMapping("/{id}")
	String update(
			@PathVariable("id") /* ... */ id,
			@Valid /* ... */, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "members/edit";
		}
		// Use direct field access when `id` does not have a setter
		// new DirectFieldAccessor(entity).setPropertyValue("id", id);
		// merge updated entity
		// entity this.repository.save(entity);
		return "redirect:/members/{id}"; // redirect to show newly updated entity
		// URI template variables from the present request are automatically
		// made available when expanding a redirect URL, and you don’t need to
		// explicitly add them through `Model` or `RedirectAttributes`.
	}

	@DeleteMapping("/{id}")
	String delete(@PathVariable("id") /* ... */ id) {
		// delete entity by id;
		return "redirect:/members";
	}

}
```

Note that the [Pageable](https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/domain/Pageable.html) argument to the `index()` handler method is provided by Spring Data through `PageableHandlerMethodArgumentResolver`. It defaults to `page` 0 (zero) and a `size` of 20.

The corresponding test for the above controller would look like this:

```java
// ...
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.isA;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// ...
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
// ...
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(controllers = MembersController.class)
// Use @WithMockUser when applicable
class MembersControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private MemberRepository memberRepository;
	// Mock all controller dependencies

	@BeforeEach
	void setUp() throws Exception {
		// ...
	}

	@AfterEach
	void tearDown() throws Exception {
		// ...
	}

	@Test
	void index() throws Exception {
		final int PAGE = 0;
		final int SIZE = 20;
		final int TOTAL = 100;
		Pageable pageable = PageRequest.of(PAGE, SIZE);
		List<MemberSummary> members = List.of(...);
		Page<MemberSummary> page = new PageImpl<>(members, pageable, TOTAL);
		// Note: Use a projection! Not all fields will be needed.
		given(this.memberRepository.findAllSummariesBy(eq(pageable))).willReturn(page);
		// Or, this.memberRepository.findAll(pageable) if entity is simple and all fields are needed

		this.mockMvc.perform(get("/members")
				.param("page", Integer.valueOf(PAGE).toString())
				.param("size", Integer.valueOf(SIZE).toString()))
			.andExpect(model().attribute("membersPage", sameInstance(page))) // org.hamcrest.Matchers
			.andExpect(model().attribute("members", equalTo(page.getContent()))) // org.hamcrest.Matchers
			.andExpect(view().name("members/index"))
			.andExpect(status().isOk());

		then(this.memberRepository).should().findAllSummariesBy(isA(Pageable.class));
	}

	@Test
	void show() throws Exception {
		final long ID = 1L;
		Member member = new Member();
		given(this.memberRepository.findById(eq(ID))).willReturn(Optional.of(member));

		this.mockMvc.perform(get("/members/{id}", ID))
			.andExpect(model().attribute("member", sameInstance(member)))
			.andExpect(view().name("/members/show"))
			.andExpect(status().isOk());

		then(this.memberRepository).should().findById(eq(ID));
	}

	@Test
	void showNotFound() throws Exception {
		final long ID = 1L;
		given(this.memberRepository.findById(eq(ID))).willReturn(Optional.empty());

		this.mockMvc.perform(get("/members/{id}", ID))
			.andExpect(status().isNotFound());

		then(this.memberRepository).should().findById(eq(ID));
	}

	@Test
	void create() throws Exception {
		this.mockMvc.perform(get("/members")
				.param("create", ""))
			.andExpect(model().attribute("member", isA(Member.class)))
			.andExpect(view().name("members/create"))
			.andExpect(status().isOk());

		then(this.memberRepository).shouldHaveNoInteractions();
	}

	@Test
	void save() throws Exception {
		final Long NEW_ID = 42L;
		given(this.memberRepository.save(isA(Member.class)))
			.will(invocation -> {
				Member member = invocation.getArgument(0, Member.class);
				member.setId(NEW_ID); // assuming ID is generated
				return member;
			});

		this.mockMvc.perform(post("/members")
				.param("name", "John Smith")
				.with(csrf()))
			.andExpect(redirectedUrl("/members/42")); // show newly created member

		then(this.memberRepository).should().save(isA(Member.class));
	}

	@Test
	void saveWithErrors() throws Exception {
		this.mockMvc.perform(post("/members")
				// name is required
				.param("birthDate", "...")
				.with(csrf()))
			.andExpect(model().attributeHasErrors("member"))
			.andExpect(view().name("member/create"));
		// Note: Further validation tests should be on the Member class (not here).
		// This test just ensures proper use of @Valid and BindingResults.

		then(this.memberRepository).shouldHaveNoInteractions();
	}

	@Test
	void edit() throws Exception {
		final long ID = 1L;
		Member member = new Member();
		given(this.memberRepository.findById(eq(ID))).willReturn(Optional.of(member));

		this.mockMvc.perform(get("/members/{id}", ID)
				.param("edit", ""))
			.andExpect(model().attribute("member", sameInstance(member)))
			.andExpect(view().name("members/edit"))
			.andExpect(status().isOk());

		then(this.memberRepository).should().findById(eq(ID));
	}

	@Test
	void editNotFound() throws Exception {
		final long ID = 1L;
		given(this.memberRepository.findById(eq(ID))).willReturn(Optional.empty());

		this.mockMvc.perform(get("/members/{id}", ID)
				.param("edit", ""))
			.andExpect(status().isNotFound());

		then(this.memberRepository).should().findById(eq(ID));
	}

	@Test
	void update() throws Exception {
		final long ID = 42L;
		given(this.memberRepository.save(isA(Member.class)))
			.will(invocation -> invocation.getArgument(0, Member.class));

		this.mockMvc.perform(put("/members/{id}", ID)
				.param("name", "John Smith")
				.param("birthDate", "...")
				.with(csrf()))
			.andExpect(redirectedUrl("/members/42")); // show updated member

		then(this.memberRepository).should().save(isA(Member.class));
	}

	// @Test updateWithErrors()

	@Test
	void delete_() throws Exception {
		final long ID = 42L;
		this.mockMvc.perform(delete("/members/{id}", ID).with(csrf()))
			.andExpect(redirectedUrl("/members"));

		then(this.memberRepository).should().deleteById(ID);
	}

}
```
