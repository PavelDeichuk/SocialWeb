package com.pavel.socialweb.Service;

import com.pavel.socialweb.Dto.UserDto;
import com.pavel.socialweb.Entity.UserEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface UserService {
    List<UserDto> GetAllUsers(int page, int size);

    UserDto GetUserById(Long user_id);

    UserDto CreateUser(UserEntity userEntity, BindingResult bindingResult);

    UserDto EditUser(Long user_id, UserEntity userEntity, BindingResult bindingResult);

    UserDto DeleteUser(Long user_id);
}
