package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.business.abstracts.ColorService;
import wearwell.com.eCommerceAPI.business.requests.CreateColorRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateColorRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllColorsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdColorResponse;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.ColorRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Color;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ColorManager implements ColorService {
    private ColorRepository colorRepository;
    private ModelMapperService modelMapperService;

    @Override
    public List<GetAllColorsResponse> getAll() {
        List<Color> color = colorRepository.findAll();

        return color
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllColorsResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetByIdColorResponse getById(String id) {
        Color color = this.colorRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(color, GetByIdColorResponse.class);
    }

    @Override
    public void add(CreateColorRequest createColorRequest) {
        Color color = this.modelMapperService
                .forRequest()
                .map(createColorRequest, Color.class);

        this.colorRepository.save(color);
    }

    @Override
    public void update(UpdateColorRequest updateColorRequest) {
        Color color = this.modelMapperService
                .forRequest()
                .map(updateColorRequest, Color.class);

        this.colorRepository.save(color);
    }

    @Override
    public void delete(String id) {
        this.colorRepository.deleteById(id);
    }
}
