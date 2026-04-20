package com.merklys.api.identity.service;

import com.merklys.api.identity.dto.request.ChangePasswordRequest;
import com.merklys.api.identity.entity.User;

public interface UserService {

    User findByIdWithRoles(Long userId);

    User findByUsernameOrEmailWithRoles(String identifier);

    void changePassword(Long userId, ChangePasswordRequest changePasswordRequest);

}
