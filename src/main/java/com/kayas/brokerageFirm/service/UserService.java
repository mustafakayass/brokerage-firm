package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.dto.response.AdminValidationResponse;
import com.kayas.brokerageFirm.entity.User;

public interface UserService {
    void addDefaultUser();
    User getCurrentUser();
    boolean isAdmin(User user);
    AdminValidationResponse validateUserAccess(Long userId);
    User getUserByUsername(String username);
}