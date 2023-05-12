package wearwell.com.eCommerceAPI.business.abstracts;

import wearwell.com.eCommerceAPI.business.requests.CreateColorRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateColorRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllColorsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdColorResponse;

import java.util.List;

public interface ColorService {
    List<GetAllColorsResponse> getAll();

    GetByIdColorResponse getById(String id);

    void add(CreateColorRequest createColorRequest);

    void update(UpdateColorRequest updateColorRequest);

    void delete(String id);
}
