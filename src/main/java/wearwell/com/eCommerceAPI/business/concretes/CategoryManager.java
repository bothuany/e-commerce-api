package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.business.abstracts.CategoryService;
import wearwell.com.eCommerceAPI.business.requests.CreateCategoryRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateCategoryRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllCategoriesResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdCategoryResponse;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.CategoryRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Category;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryManager implements CategoryService {
    private CategoryRepository categoryRepository;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetAllCategoriesResponse> getAll() {
        List<Category> categories = categoryRepository.findAll();

        return categories
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllCategoriesResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetByIdCategoryResponse getById(String id) {
        Category category = this.categoryRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(category, GetByIdCategoryResponse.class);
    }

    @Override
    public void add(CreateCategoryRequest createCategoryRequest) {
        Category category = this.modelMapperService
                .forRequest()
                .map(createCategoryRequest, Category.class);

        this.categoryRepository.save(category);
    }

    @Override
    public void update(UpdateCategoryRequest updateCategoryRequest) {
        Category category = this.modelMapperService
                .forRequest()
                .map(updateCategoryRequest, Category.class);

        this.categoryRepository.save(category);
    }

    @Override
    public void delete(String id) {
        this.categoryRepository.deleteById(id);
    }
}
