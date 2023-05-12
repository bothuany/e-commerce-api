package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.business.abstracts.CategoryService;
import wearwell.com.eCommerceAPI.business.requests.CreateCategoryRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateCategoryRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllCategoriesResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdCategoryResponse;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoriesController {
    private CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<GetAllCategoriesResponse>> getAll() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdCategoryResponse> getById(@PathVariable String id) {
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CreateCategoryRequest> add(@RequestBody @Valid CreateCategoryRequest createCategoryRequest) {
        this.categoryService.add(createCategoryRequest);
        return new ResponseEntity<>(createCategoryRequest, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UpdateCategoryRequest> update(@RequestBody @Valid UpdateCategoryRequest updateCategoryRequest) {
        this.categoryService.update(updateCategoryRequest);
        return new ResponseEntity<>(updateCategoryRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        this.categoryService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
