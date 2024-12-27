package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.dto.response.AdminValidationResponse;
import com.kayas.brokerageFirm.entity.User;
import com.kayas.brokerageFirm.exception.UnauthorizedAccessException;
import com.kayas.brokerageFirm.exception.UserNotFoundException;
import com.kayas.brokerageFirm.repository.UserRepository;
import com.kayas.brokerageFirm.utility.enums.Role;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    public void addDefaultUser() {
        if (userRepository.findByUsername("test").isEmpty()) {
            User user = new User();
            user.setRole("U");
            user.setUsername("test");
            user.setPassword(passwordEncoder.encode("test123"));
            userRepository.save(user);
        }
        if (userRepository.findByUsername("test1").isEmpty()) {
            User user = new User();
            user.setRole("U");
            user.setUsername("test1");
            user.setPassword(passwordEncoder.encode("test12"));
            userRepository.save(user);
        }
        if (userRepository.findByUsername("admin").isEmpty()) {
            User user = new User();
            user.setRole("A");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin123"));
            userRepository.save(user);
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByUsername(username);
    }

    public AdminValidationResponse validateUserAccess(Long requestUserId) {
        User currentUser = getCurrentUser();
        boolean isAdmin = isAdmin(currentUser);
        
        if (!isAdmin && (requestUserId == null || !requestUserId.equals(currentUser.getId()))) {
            throw new UnauthorizedAccessException("You can only access your own resources");
        }
        
        return new AdminValidationResponse(
            isAdmin ? requestUserId : currentUser.getId(),
            isAdmin
        );
    }

    public boolean isAdmin(User user){
        return user.getRole().contains(Role.ADMIN.getDisplayName());
    }

}
