package com.playmatch.playmatch.util;
import com.playmatch.playmatch.dto.UserResponse;
import com.playmatch.playmatch.entity.User;
import org.springframework.stereotype.Component;
@Component
public class UserResponseMapper {
    public UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRating()
        );
    }
}
