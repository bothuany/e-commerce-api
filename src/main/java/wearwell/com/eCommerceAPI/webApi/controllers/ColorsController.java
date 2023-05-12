package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.business.abstracts.ColorService;
import wearwell.com.eCommerceAPI.business.requests.CreateColorRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateColorRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllColorsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdColorResponse;

import java.util.List;

@RestController
@RequestMapping("/api/colors")
@AllArgsConstructor
public class ColorsController {
    private ColorService colorService;

    @GetMapping()
    public ResponseEntity<List<GetAllColorsResponse>> getAll() {
        return new ResponseEntity<>(colorService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdColorResponse> getById(@PathVariable String id) {
        return new ResponseEntity<>(colorService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CreateColorRequest> add(@RequestBody @Valid CreateColorRequest createColorRequest) {
        this.colorService.add(createColorRequest);
        return new ResponseEntity<>(createColorRequest, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UpdateColorRequest> update(@RequestBody @Valid UpdateColorRequest updateColorRequest) {
        this.colorService.update(updateColorRequest);
        return new ResponseEntity<>(updateColorRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        this.colorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
