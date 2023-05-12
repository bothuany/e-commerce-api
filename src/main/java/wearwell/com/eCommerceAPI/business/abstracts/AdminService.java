package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.auth.AuthenticationResponse;
import wearwell.com.eCommerceAPI.business.requests.CreateAdminRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateAdminRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllAdminsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdAdminResponse;

import java.util.List;


public interface AdminService {
    List<GetAllAdminsResponse> getAll();

    GetByIdAdminResponse getById(String id);

    AuthenticationResponse add(CreateAdminRequest createAdminRequest);

    void update(UpdateAdminRequest updateAdminRequest);

    void delete(String id);
}
