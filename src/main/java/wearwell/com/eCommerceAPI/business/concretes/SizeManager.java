package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.business.abstracts.SizeService;
import wearwell.com.eCommerceAPI.business.requests.CreateSizeRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateSizeRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllSizesResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdSizeResponse;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.SizeRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Size;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SizeManager implements SizeService {
    private SizeRepository sizeRepository;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetAllSizesResponse> getAll() {
        List<Size> size = sizeRepository.findAll();

        return size
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllSizesResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetByIdSizeResponse getById(String id) {
        Size size = this.sizeRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(size, GetByIdSizeResponse.class);
    }

    @Override
    public void add(CreateSizeRequest createSizeRequest) {
        Size size = this.modelMapperService
                .forRequest()
                .map(createSizeRequest, Size.class);

        this.sizeRepository.save(size);
    }

    @Override
    public void update(UpdateSizeRequest updateSizeRequest) {
        Size size = this.modelMapperService
                .forRequest()
                .map(updateSizeRequest, Size.class);

        this.sizeRepository.save(size);
    }

    @Override
    public void delete(String id) {
        this.sizeRepository.deleteById(id);
    }
}
