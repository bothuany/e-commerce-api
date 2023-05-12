package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.business.abstracts.SizeService;
import wearwell.com.eCommerceAPI.business.requests.CreateSizeRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateSizeRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllSizesResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdSizeResponse;

import java.util.List;

@RestController
@RequestMapping("/api/sizes")
@AllArgsConstructor
public class SizesController {
    private SizeService SizeService;

    @GetMapping()
    public ResponseEntity<List<GetAllSizesResponse>> getAll() {
        return new ResponseEntity<>(SizeService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdSizeResponse> getById(@PathVariable String id) {
        return new ResponseEntity<>(SizeService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CreateSizeRequest> add(@RequestBody @Valid CreateSizeRequest createSizeRequest) {
        this.SizeService.add(createSizeRequest);
        return new ResponseEntity<>(createSizeRequest, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UpdateSizeRequest> update(@RequestBody @Valid UpdateSizeRequest updateSizeRequest) {
        this.SizeService.update(updateSizeRequest);
        return new ResponseEntity<>(updateSizeRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        this.SizeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
