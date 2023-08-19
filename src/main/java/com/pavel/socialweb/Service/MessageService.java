package com.pavel.socialweb.Service;

import com.pavel.socialweb.Dto.MessageDto;
import com.pavel.socialweb.Entity.MessageEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface MessageService {
    List<MessageDto> GetAllMessageByUsers(Long first_user_id, Long second_user_id);

    MessageDto SendMessage(Long first_user_id, Long second_user_id, MessageEntity messageEntity, BindingResult bindingResult);

    MessageDto EditMessage(Long message_id, MessageEntity messageEntity, BindingResult bindingResult);

    MessageDto DeleteMessage(Long message_id);
}
