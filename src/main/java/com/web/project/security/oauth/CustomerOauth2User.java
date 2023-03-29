package com.web.project.security.oauth;

import com.web.project.entity.NhanVien;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

public class CustomerOauth2User  implements OAuth2User {

    private OAuth2User oAuth2User;
    private String clientName ;
    private String fullName ;

    private String photos;
    public CustomerOauth2User(OAuth2User oAuth2User , String  clientName) {
        this.oAuth2User = oAuth2User;
        this.clientName = clientName;
    }

    public CustomerOauth2User(OAuth2User oAuth2User, String clientName, String fullName, String photos) {
        this.oAuth2User = oAuth2User;
        this.clientName = clientName;
        this.fullName = fullName;
        this.photos = photos;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    @Override
    public String getName() {
        return oAuth2User.getAttribute("name");
    }

    public String getFullName() {
        return oAuth2User.getAttribute("name");
    }
    public String getPhotos(){
        return this.photos;
    }

    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }
    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }
    public String getClientName() {
        return this.clientName;
    }
}
