package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.auth.AuthenticationResponse;
import wearwell.com.eCommerceAPI.business.abstracts.SellerService;
import wearwell.com.eCommerceAPI.business.requests.CreateSellerRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateSellerRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllSellersResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdSellerResponse;
import wearwell.com.eCommerceAPI.business.rules.UserBusinessRules;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.SellerRepository;
import wearwell.com.eCommerceAPI.entities.abstracts.Role;
import wearwell.com.eCommerceAPI.entities.concretes.Seller;
import wearwell.com.eCommerceAPI.security.JwtService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SellerManager implements SellerService {
    private SellerRepository sellerRepository;
    private UserBusinessRules userBusinessRules;
    private ModelMapperService modelMapperService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<GetAllSellersResponse> getAll() {
        List<Seller> sellers = sellerRepository.findAll();

        return sellers
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllSellersResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetByIdSellerResponse getById(String id) {
        Seller seller = this.sellerRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(seller, GetByIdSellerResponse.class);
    }

    @Override
    public AuthenticationResponse add(CreateSellerRequest createSellerRequest) {
        this.userBusinessRules.checkIfEmailExists(createSellerRequest.getEmail());

        Seller seller = this.modelMapperService
                .forRequest()
                .map(createSellerRequest, Seller.class);
        seller.setRole(Role.SELLER);
        seller.setPassword(passwordEncoder.encode(createSellerRequest.getPassword()));
        this.sellerRepository.save(seller);

        var jwtToken = jwtService.generateToken(seller);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public void update(UpdateSellerRequest updateSellerRequest) {
        this.userBusinessRules.checkIfEmailExists(updateSellerRequest.getEmail());

        Seller seller = this.modelMapperService
                .forRequest()
                .map(updateSellerRequest, Seller.class);

        this.sellerRepository.save(seller);
    }

    @Override
    public void delete(String id) {
        this.sellerRepository.deleteById(id);
    }
}
