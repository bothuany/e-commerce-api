package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.business.abstracts.StockService;
import wearwell.com.eCommerceAPI.business.requests.CreateStockRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateStockRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllStocksResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdStockResponse;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
@AllArgsConstructor
public class StocksController {
    private StockService stockService;

    @GetMapping()
    public ResponseEntity<List<GetAllStocksResponse>> getAll() {
        return new ResponseEntity<>(stockService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdStockResponse> getById(@PathVariable String id) {
        return new ResponseEntity<>(stockService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<CreateStockRequest> add(@RequestBody @Valid CreateStockRequest createStockRequest) {
        this.stockService.add(createStockRequest);
        return new ResponseEntity<>(createStockRequest, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<UpdateStockRequest> update(@RequestBody @Valid UpdateStockRequest updateStockRequest) {
        this.stockService.update(updateStockRequest);
        return new ResponseEntity<>(updateStockRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        this.stockService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
