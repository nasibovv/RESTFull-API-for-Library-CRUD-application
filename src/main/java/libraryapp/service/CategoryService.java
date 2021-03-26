package libraryapp.service;

import libraryapp.dto.library.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getCategories();
}
