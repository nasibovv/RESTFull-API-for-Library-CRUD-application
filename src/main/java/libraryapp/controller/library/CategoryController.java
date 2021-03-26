package libraryapp.controller.library;

import libraryapp.dto.library.CategoryDTO;
import libraryapp.service.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/find-category-list")
    public List<CategoryDTO> getCategories() {
        return categoryService.getCategories();
    }
}
