package com.merklys.api.identity.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merklys.api.common.exception.BusinessException;
import com.merklys.api.common.exception.DuplicateResourceException;
import com.merklys.api.common.exception.ResourceNotFoundException;
import com.merklys.api.identity.dto.request.ChangePasswordRequest;
import com.merklys.api.identity.dto.request.CreateUserRequest;
import com.merklys.api.identity.entity.Role;
import com.merklys.api.identity.entity.User;
import com.merklys.api.identity.mapper.UserMapper;
import com.merklys.api.identity.repository.UserRepository;
import com.merklys.api.identity.service.RoleService;
import com.merklys.api.identity.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleService roleService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
            RoleService roleService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    @Transactional
    public User createEntity(CreateUserRequest createUserRequest) {
        String username = createUserRequest.username().toLowerCase().trim();
        String email = createUserRequest.email().toLowerCase().trim();

        if (this.userRepository.existsByUsername(username)) {
            throw new DuplicateResourceException("El nombre de usuario " + username + " ya está en uso");
        }

        if (this.userRepository.existsByEmail(email)) {
            throw new DuplicateResourceException("El correo electrónico ya está en uso: " + email);
        }

        User user = this.userMapper.toEntity(createUserRequest);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(this.passwordEncoder.encode(createUserRequest.password()));

        List<Role> roles = this.roleService.findRolesByIds(createUserRequest.roleIds());
        roles.forEach(user::addRole);

        return this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest changePasswordRequest) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el id: " + userId));

        String currentPassword = changePasswordRequest.currentPassword();
        String newPassword = changePasswordRequest.newPassword();
        String confirmNewPassword = changePasswordRequest.confirmNewPassword();

        if (!this.passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new BusinessException("La contraseña actual es incorrecta");
        }

        if (!newPassword.equals(confirmNewPassword)) {
            throw new BusinessException("La nueva contraseña y su confirmación no coinciden");
        }

        if (this.passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new BusinessException("La nueva contraseña no puede ser igual a la contraseña actual");
        }

        user.setPassword(this.passwordEncoder.encode(newPassword));
        this.userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByIdWithRoles(Long userId) {
        return this.userRepository.findByIdWithRoles(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con el id: " + userId));
    }

    @Override
    @Transactional(readOnly = true)
    public User findByUsernameOrEmailWithRoles(String identifier) {
        return this.userRepository.findByUsernameOrEmailWithRoles(identifier)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Usuario no encontrado con el identificador: " + identifier));
    }

}
