package ru.geekbrains.springdata;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.geekbrains.springdata.entity.Category;
import ru.geekbrains.springdata.repositories.CategoryRepo;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// java.lang.IllegalStateException: Failed to load ApplicationContext

@DataJpaTest
public class RepositoriesTest {
	@Autowired
	private CategoryRepo categoryRepository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void categoryRepositoryTest() {
		Category category1 = new Category();
		category1.setTitle("FIRST");
		entityManager.persist(category1);

		Category category2 = new Category();
		category1.setTitle("SECOND");
		entityManager.persist(category2);

		Optional<Category> categories = categoryRepository.findByIdWithProducts(1L);

		assertThat(categories).contains(category1);
	}
}
