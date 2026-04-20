package com.merklys.api.identity.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.merklys.api.common.exception.ResourceNotFoundException;
import com.merklys.api.identity.entity.User;
import com.merklys.api.identity.repository.UserRepository;
import com.merklys.api.identity.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
