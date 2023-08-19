package com.pavel.socialweb.Controller;

import com.pavel.socialweb.Dto.MessageDto;
import com.pavel.socialweb.Entity.MessageEntity;
import com.pavel.socialweb.Entity.UserEntity;
import com.pavel.socialweb.Model.ErrorModel;
import com.pavel.socialweb.Service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/message")
@Tag(name = "message")
public class MessageController {
    private final MessageService messageService;

    private static final String MESSAGE_TO_USER = "/user/{first_user_id}/{second_user_id}";

    private static final String MESSAGE_ID = "/{message_id}";

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(
            summary = "Get all messages",
            description = "Get all messages",
            responses = {@ApiResponse(
                    description = "Success, Get all messages",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = MessageEntity.class))
            ), @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = MESSAGE_TO_USER, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MessageDto> GetAllMessageByUser(@PathVariable Long first_user_id, @PathVariable Long second_user_id) {
        return messageService.GetAllMessageByUsers(first_user_id, second_user_id);
    }

    @Operation(
            summary = "Send message",
            description = "Send Message",
            responses = {@ApiResponse(
                    description = "Success, Send message",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = MessageEntity.class))
            ), @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = MESSAGE_TO_USER, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto SendMessage(@PathVariable Long first_user_id,
                                  @PathVariable Long second_user_id,
                                  @RequestBody MessageEntity message,
                                  BindingResult bindingResult) {
        return messageService.SendMessage(first_user_id, second_user_id, message, bindingResult);
    }

    @Operation(
            summary = "Edit message",
            description = "Edit message",
            responses = {@ApiResponse(
                    description = "Success, edit message",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = MessageEntity.class))
            ), @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = MESSAGE_ID, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto EditMessage(@PathVariable Long message_id,
                                  @RequestBody MessageEntity message,
                                  BindingResult bindingResult) {
        return messageService.EditMessage(message_id, message, bindingResult);
    }

    @Operation(
            summary = "Delete message",
            description = "Delete message",
            responses = {@ApiResponse(
                    description = "Success, delete message",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = MessageEntity.class))
            ), @ApiResponse(
                    description = "Error, not found for message id!",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = MESSAGE_ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageDto DeleteMessage(@PathVariable Long message_id) {
        return messageService.DeleteMessage(message_id);
    }
}