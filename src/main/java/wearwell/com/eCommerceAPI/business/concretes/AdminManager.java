package wearwell.com.eCommerceAPI.business.concretes;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import wearwell.com.eCommerceAPI.auth.AuthenticationResponse;
import wearwell.com.eCommerceAPI.business.abstracts.AdminService;
import wearwell.com.eCommerceAPI.business.requests.CreateAdminRequest;
import wearwell.com.eCommerceAPI.business.requests.UpdateAdminRequest;
import wearwell.com.eCommerceAPI.business.responses.GetAllAdminsResponse;
import wearwell.com.eCommerceAPI.business.responses.GetByIdAdminResponse;
import wearwell.com.eCommerceAPI.business.rules.UserBusinessRules;
import wearwell.com.eCommerceAPI.core.utilities.mappers.ModelMapperService;
import wearwell.com.eCommerceAPI.dataAccess.abstracts.AdminRepository;
import wearwell.com.eCommerceAPI.entities.concretes.Admin;
import wearwell.com.eCommerceAPI.entities.abstracts.Role;
import wearwell.com.eCommerceAPI.security.JwtService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AdminManager implements AdminService {
    private AdminRepository adminRepository;
    private UserBusinessRules userBusinessRules;
    private ModelMapperService modelMapperService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<GetAllAdminsResponse> getAll() {
        List<Admin> admins = adminRepository.findAll();

        return admins
                .stream()
                .map(model -> this.modelMapperService
                        .forResponse()
                        .map(model, GetAllAdminsResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public GetByIdAdminResponse getById(String id) {
        Admin admin = this.adminRepository.findById(id).orElseThrow();

        return this.modelMapperService.forResponse().map(admin, GetByIdAdminResponse.class);
    }

    @Override
    public AuthenticationResponse add(CreateAdminRequest createAdminRequest) {
        this.userBusinessRules.checkIfEmailExists(createAdminRequest.getEmail());

        Admin admin = this.modelMapperService
                .forRequest()
                .map(createAdminRequest, Admin.class);
        admin.setRole(Role.ADMIN);
        admin.setPassword(passwordEncoder.encode(createAdminRequest.getPassword()));
        admin = this.adminRepository.save(admin);
        var jwtToken = jwtService.generateToken(admin);

        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder().token(jwtToken).build();
        authenticationResponse.setUser(admin);

        return authenticationResponse;
    }

    @Override
    public void update(UpdateAdminRequest updateAdminRequest) {
        this.userBusinessRules.checkIfEmailExists(updateAdminRequest.getEmail());

        Admin admin = this.modelMapperService
                .forRequest()
                .map(updateAdminRequest, Admin.class);

        this.adminRepository.save(admin);
    }

    @Override
    public void delete(String id) {
        this.adminRepository.deleteById(id);
    }

}
