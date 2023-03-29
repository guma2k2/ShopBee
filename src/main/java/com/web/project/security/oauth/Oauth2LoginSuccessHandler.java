package com.web.project.security.oauth;

import com.web.project.entity.AuthenticationType;
import com.web.project.entity.NhanVien;
import com.web.project.service.CustomerService;
import com.web.project.service.NhanVienNotFoundException;
import com.web.project.service.NhanVienService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Oauth2LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Autowired private CustomerService customerService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        CustomerOauth2User customerOauth2User = (CustomerOauth2User) authentication.getPrincipal();
        String name = customerOauth2User.getName();
        String email = customerOauth2User.getEmail();
        NhanVien customer = customerService.findByEmail(email);
        String clientName = customerOauth2User.getClientName();
        AuthenticationType type = getAuthenticationType(clientName);
        if(customer == null) {
            customerService.addNewCustomerOauth(name , email , type);
        }else{
            customerOauth2User.setFullName(customer.getFullName());
            customerService.updateAuthenticationType(type, customer.getId());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
    public AuthenticationType getAuthenticationType(String clientName){
        if(clientName.equals("Google")){
            return AuthenticationType.GOOGLE;
        }else  if (clientName.equals("Facebook")){
            return AuthenticationType.FACEBOOK;
        }else {
            return AuthenticationType.DATABASE;
        }
    }
}
