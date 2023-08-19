package com.pavel.socialweb.Service.impl;

import com.pavel.socialweb.Dto.MessageDto;
import com.pavel.socialweb.Entity.FriendEntity;
import com.pavel.socialweb.Entity.MessageEntity;
import com.pavel.socialweb.Entity.UserEntity;
import com.pavel.socialweb.Exception.BadRequestException;
import com.pavel.socialweb.Exception.NotFoundException;
import com.pavel.socialweb.Mapper.MessageMapper;
import com.pavel.socialweb.Repository.MessageRepository;
import com.pavel.socialweb.Repository.UserRepository;
import com.pavel.socialweb.Service.MessageService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final UserRepository userRepository;

    private final MessageMapper messageMapper;

    public MessageServiceImpl(MessageRepository messageRepository, UserRepository userRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.messageMapper = messageMapper;
    }

    @Override
    public List<MessageDto> GetAllMessageByUsers(Long first_user_id, Long second_user_id) {
       UserEntity firstUser = userRepository
                .findById(first_user_id)
                .orElseThrow(() -> new NotFoundException("Not found for first user!"));
       UserEntity secondUser = userRepository
                .findById(second_user_id)
                .orElseThrow(() -> new NotFoundException("Not found for second user!"));
        List<MessageEntity> firstMessageUser = messageRepository.findByFirstUser(firstUser);
        List<MessageEntity> secondMessageUser = messageRepository.findBySecondUser(secondUser);
        List<MessageEntity> messagesUsers = new ArrayList<>();
        for (MessageEntity message : firstMessageUser) {
            messagesUsers.add(messageRepository.findById(message.getSecondUser().getId()).get());
        }
        for (MessageEntity message : secondMessageUser) {
            messagesUsers.add(messageRepository.findById(message.getFirstUser().getId()).get());
        }
        return messagesUsers
                .stream()
                .map(messageMapper::MESSAGE_DTO)
                .toList();
    }

    @Override
    public MessageDto SendMessage(Long first_user_id, Long second_user_id, MessageEntity messageEntity, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors){
                throw new BadRequestException(error.getObjectName() + " " + error.getDefaultMessage());
            }
        }
        UserEntity firstUser = userRepository
                .findById(first_user_id)
                .orElseThrow(() -> new NotFoundException("Not found for first user!"));
        UserEntity secondUser = userRepository
                .findById(second_user_id)
                .orElseThrow(() -> new NotFoundException("Not found for second user!"));
        messageEntity.setFirstUser(firstUser);
        messageEntity.setSecondUser(secondUser);
        MessageEntity save = messageRepository.save(messageEntity);
        return messageMapper.MESSAGE_DTO(save);
    }

    @Override
    public MessageDto EditMessage(Long message_id, MessageEntity messageEntity, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors){
                throw new BadRequestException(error.getObjectName() + " " + error.getDefaultMessage());
            }
        }
        MessageEntity message = messageRepository
                .findById(message_id)
                .orElseThrow(() -> new NotFoundException("Not found for message id!"));
        message.setDescription(messageEntity.getDescription());
        MessageEntity save = messageRepository.save(message);
        return messageMapper.MESSAGE_DTO(save);
    }

    @Override
    public MessageDto DeleteMessage(Long message_id) {
        MessageEntity message = messageRepository
                .findById(message_id)
                .orElseThrow(() -> new NotFoundException("Not found for message id!"));
        messageRepository.deleteById(message_id);
        return messageMapper.MESSAGE_DTO(message);
    }
}
