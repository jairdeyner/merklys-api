package com.merklys.api.identity.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merklys.api.common.exception.BusinessException;
import com.merklys.api.common.exception.ResourceNotFoundException;
import com.merklys.api.identity.dto.request.ChangePasswordRequest;
import com.merklys.api.identity.entity.User;
import com.merklys.api.identity.repository.UserRepository;
import com.merklys.api.identity.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
