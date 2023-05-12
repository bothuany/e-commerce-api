package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.auth.AuthenticationResponse;
import wearwell.com.eCommerceAPI.business.abstracts.CustomerService;
import wearwell.com.eCommerceAPI.business.requests.CreateCustomerRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateCustomerRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllCustomersResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdCustomerResponse;
import wearwell.com.eCommerceAPI.business.rules.UserBusinessRules;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.CustomerRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Customer;
import wearwell.com.eCommerceAPI.entities.abstracts.Role;
import wearwell.com.eCommerceAPI.security.JwtService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerManager implements CustomerService {
    private CustomerRepository customerRepository;
    private UserBusinessRules userBusinessRules;
    private ModelMapperService modelMapperService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<GetAllCustomersResponse> getAll() {
        List<Customer> customers = customerRepository.findAll();

        return customers
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllCustomersResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetByIdCustomerResponse getById(String id) {
        Customer customer = this.customerRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(customer, GetByIdCustomerResponse.class);
    }

    @Override
    public AuthenticationResponse add(CreateCustomerRequest createCustomerRequest) {
        this.userBusinessRules.checkIfEmailExists(createCustomerRequest.getEmail());

        Customer customer = this.modelMapperService
                .forRequest()
                .map(createCustomerRequest, Customer.class);
        customer.setRole(Role.CUSTOMER);
        customer.setPassword(passwordEncoder.encode(createCustomerRequest.getPassword()));

        this.customerRepository.save(customer);
        var jwtToken = jwtService.generateToken(customer);

        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    @Override
    public void update(UpdateCustomerRequest updateCustomerRequest) {
        this.userBusinessRules.checkIfEmailExists(updateCustomerRequest.getEmail());

        Customer customer = this.modelMapperService
                .forRequest()
                .map(updateCustomerRequest, Customer.class);

        this.customerRepository.save(customer);
    }

    @Override
    public void delete(String id) {
        this.customerRepository.deleteById(id);
    }
}
