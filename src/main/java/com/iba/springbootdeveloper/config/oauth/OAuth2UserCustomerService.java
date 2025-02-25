package com.iba.springbootdeveloper.config.oauth;

import com.iba.springbootdeveloper.domain.Role;
import com.iba.springbootdeveloper.domain.User;
import com.iba.springbootdeveloper.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class OAuth2UserCustomerService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;
    @Value("${admin.email}")
    private String adminEmail;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws
            OAuth2AuthenticationException{
        OAuth2User user = super.loadUser(userRequest);
        saveOrUpdate(user);
        return user;
    }

    private User saveOrUpdate(OAuth2User oAuth2User){
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String email = (String) attributes.get("email");
        String name= (String)attributes.get("name");

        Role role = email.equals(adminEmail) ? Role.ADMIN : Role.USER;
        User user = userRepository.findByEmail(email)
                .map(entity -> entity.update(name))
                .orElse(User.builder()
                    .email(email)
                    .nickname(name)
                    .role(role)
                    .build());
        return userRepository.save(user);
    }
}
