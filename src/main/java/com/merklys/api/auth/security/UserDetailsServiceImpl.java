package com.merklys.api.auth.security;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.merklys.api.common.exception.ResourceNotFoundException;
import com.merklys.api.identity.entity.User;
import com.merklys.api.identity.service.UserService;

@Service
public class UserDetailsServiceImpl implements CustomUserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {

        try {
            User user = this.userService.findByUsernameOrEmailWithRoles(identifier);

            return this.buildUserDetails(user);
        } catch (ResourceNotFoundException ex) {
            throw new UsernameNotFoundException("Credenciales inválidas");
        }

    }

    @Override
    public UserDetails loadUserById(Long userId) throws UsernameNotFoundException {
        try {
            User user = this.userService.findByIdWithRoles(userId);

            return this.buildUserDetails(user);
        } catch (ResourceNotFoundException ex) {
            throw new UsernameNotFoundException("Credenciales inválidas");
        }
    }

    private UserDetails buildUserDetails(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());

        return new CustomUserDetails(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.isActive(),
                user.isAccountNonLocked(),
                authorities);
    }

}
