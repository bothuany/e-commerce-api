package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.business.requests.CreateSizeRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateSizeRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllSizesResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdSizeResponse;

import java.util.List;

public interface SizeService {
    List<GetAllSizesResponse> getAll();
    GetByIdSizeResponse getById(String id);
    void add(CreateSizeRequest createSizeRequest);
    void update(UpdateSizeRequest updateSizeRequest);
    void delete(String id);
}
