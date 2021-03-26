package libraryapp.service.impl;

import libraryapp.dto.library.CategoryDTO;
import libraryapp.model.Category;
import libraryapp.repository.CategoryRepository;
import libraryapp.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);


    @Override
    public List<CategoryDTO> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        logger.info("get categories from db. categories: {}", categories);
        return mapCategoryEntityToCategoryDTO(categories);
    }

    private List<CategoryDTO> mapCategoryEntityToCategoryDTO(List<Category> categories) {
        return categories
                .stream()
                .map(categoryEntity -> {
                    CategoryDTO categoryDTO = new CategoryDTO();
                    categoryDTO.setName(categoryEntity.getName());
                    return categoryDTO;
                })
                .collect(Collectors.toList());
    }
}
