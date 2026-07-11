package com.profumi.profumi.services;

import com.profumi.profumi.models.Rol;
import com.profumi.profumi.models.Usuario;
import com.profumi.profumi.repositories.UsuarioRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UsuarioRepository usuarioRepository;

    public CustomOAuth2UserService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        Map<String, Object> attributes = oAuth2User.getAttributes();
        String provider = userRequest.getClientRegistration().getRegistrationId();
        
        // Get identifier (email is preferred, but sub is the unique ID for OIDC)
        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");
        String providerId = (String) attributes.get("sub");

        if (email == null) {
            // Fallback for providers that might not return email immediately
            email = providerId + "@" + provider + ".com";
        }
        
        if (name == null) {
            name = email;
        }

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        Usuario usuario;

        if (usuarioOpt.isEmpty()) {
            usuario = new Usuario(email, name, Rol.CLIENTE, provider.toUpperCase());
            usuarioRepository.save(usuario);
        } else {
            usuario = usuarioOpt.get();
        }

        // Determine the principal name attribute (Google usually uses 'email' or 'sub')
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
        
        if (userNameAttributeName == null || userNameAttributeName.isEmpty()) {
            userNameAttributeName = "email"; // Default to email for our logic
        }

        return new DefaultOAuth2User(
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name())),
                attributes,
                userNameAttributeName
        );
    }
}
