package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.business.requests.CreateCategoryRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateCategoryRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllCategoriesResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdCategoryResponse;

import java.util.List;

public interface CategoryService {
    List<GetAllCategoriesResponse> getAll();

    GetByIdCategoryResponse getById(String id);

    void add(CreateCategoryRequest createCategoryRequest);

    void update(UpdateCategoryRequest updateCategoryRequest);

    void delete(String id);
}
