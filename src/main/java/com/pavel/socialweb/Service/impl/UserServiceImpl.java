package com.pavel.socialweb.Service.impl;

import com.pavel.socialweb.Dto.UserDto;
import com.pavel.socialweb.Entity.UserEntity;
import com.pavel.socialweb.Exception.BadRequestException;
import com.pavel.socialweb.Exception.NotFoundException;
import com.pavel.socialweb.Mapper.UserMapper;
import com.pavel.socialweb.Repository.UserRepository;
import com.pavel.socialweb.Service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDto> GetAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page,size);
        Page<UserEntity> users = userRepository.findAll(pageable);
        if(users.isEmpty()) {
            throw new NotFoundException("Not found for user list!");
        }
        return users
                .stream()
                .map(userMapper::USER_DTO)
                .toList();
    }

    @Override
    public UserDto GetUserById(Long user_id) {
        UserEntity user = userRepository
                .findById(user_id)
                .orElseThrow(() -> new NotFoundException("Not found for user id!"));
        return userMapper.USER_DTO(user);
    }

    @Override
    public UserDto CreateUser(UserEntity userEntity, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors){
                throw new BadRequestException(error.getObjectName() + " " + error.getDefaultMessage());
            }
        }
       userRepository
               .findByUsername(userEntity.getUsername())
               .ifPresent(user -> {
                   throw new BadRequestException("username is exist!");
               });
       userRepository
               .findByEmail(userEntity.getEmail())
               .ifPresent(user -> {
                   throw new BadRequestException("email is exist!");
               });
        userEntity.setRole("QUEST");
        UserEntity save = userRepository.save(userEntity);
        return userMapper.USER_DTO(save);
    }

    @Override
    public UserDto EditUser(Long user_id, UserEntity userEntity, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors){
                throw new BadRequestException(error.getObjectName() + " " + error.getDefaultMessage());
            }
        }
        UserEntity user = userRepository
                .findById(user_id)
                .orElseThrow(() -> new NotFoundException("Not found for user id!"));
        user.setUsername(userEntity.getUsername());
        user.setPassword(userEntity.getPassword());
        user.setEmail(userEntity.getEmail());
        user.setRole(userEntity.getRole());
        UserEntity save = userRepository.save(user);
        return userMapper.USER_DTO(save);
    }

    @Override
    public UserDto DeleteUser(Long user_id) {
       UserEntity user = userRepository
                .findById(user_id)
                .orElseThrow(() -> new NotFoundException("Not found for user id!"));
       userRepository.deleteById(user_id);
       return userMapper.USER_DTO(user);
    }
}
