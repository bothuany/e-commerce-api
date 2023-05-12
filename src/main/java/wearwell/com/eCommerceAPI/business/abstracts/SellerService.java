package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.auth.AuthenticationResponse;
import wearwell.com.eCommerceAPI.business.requests.CreateSellerRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateSellerRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllSellersResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdSellerResponse;

import java.util.List;


public interface SellerService {
    List<GetAllSellersResponse> getAll();
    GetByIdSellerResponse getById(String id);
    AuthenticationResponse add(CreateSellerRequest createSellerRequest);
    void update(UpdateSellerRequest updateSellerRequest);
    void delete(String id);
}
