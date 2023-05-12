package wearwell.com.eCommerceAPI.webApi.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wearwell.com.eCommerceAPI.auth.AuthenticationResponse;
import wearwell.com.eCommerceAPI.business.abstracts.AdminService;
import wearwell.com.eCommerceAPI.business.requests.CreateAdminRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateAdminRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllAdminsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdAdminResponse;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@AllArgsConstructor
public class AdminsController {
    private AdminService adminService;

    @GetMapping()
    public ResponseEntity<List<GetAllAdminsResponse>> getAll() {
        return new ResponseEntity<>(adminService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdAdminResponse> getById(@PathVariable String id) {
        return new ResponseEntity<>(adminService.getById(id), HttpStatus.OK);
    }

    @PostMapping()
    public AuthenticationResponse add(@RequestBody @Valid CreateAdminRequest createAdminRequest) {
        return this.adminService.add(createAdminRequest);

    }

    @PutMapping
    public ResponseEntity<UpdateAdminRequest> update(@RequestBody @Valid UpdateAdminRequest updateAdminRequest) {
        this.adminService.update(updateAdminRequest);
        return new ResponseEntity<>(updateAdminRequest, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String id) {
        this.adminService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
