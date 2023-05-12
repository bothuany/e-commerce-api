package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.auth.AuthenticationResponse;
import wearwell.com.eCommerceAPI.business.requests.CreateCustomerRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateCustomerRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllCustomersResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdCustomerResponse;

import java.util.List;


public interface CustomerService {
    List<GetAllCustomersResponse> getAll();

    GetByIdCustomerResponse getById(String id);

    AuthenticationResponse add(CreateCustomerRequest createCustomerRequest);

    void update(UpdateCustomerRequest updateCustomerRequest);

    void delete(String id);
}
