package com.pavel.socialweb.Controller;

import com.pavel.socialweb.Dto.FriendDto;
import com.pavel.socialweb.Dto.UserDto;
import com.pavel.socialweb.Entity.FriendEntity;
import com.pavel.socialweb.Entity.MessageEntity;
import com.pavel.socialweb.Model.ErrorModel;
import com.pavel.socialweb.Service.FriendService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/friend")
@Tag(name = "friend")
public class FriendController {
    private final FriendService friendService;

    private static final String USER_ID = "/user/{user_id}";

    private static final String ADD_FRIEND = "/user/{first_user_id}/{second_user_id}";

    private static final String FRIEND_ID = "/{friend_id}";


    public FriendController(FriendService friendService) {
        this.friendService = friendService;
    }

    @Operation(
            summary = "Get all friends by user id",
            description = "Get all friends by user id",
            responses = {@ApiResponse(
                    description = "Success, get all friends by user",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = FriendEntity.class))
            ), @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = USER_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> GetAllFriendByUser(@PathVariable Long user_id) {
        return friendService.GetFriends(user_id);
    }

    @Operation(
            summary = "Add friends",
            description = "Add friends",
            responses = {@ApiResponse(
                    description = "Success, add friends",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = FriendEntity.class))
            ), @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = ADD_FRIEND, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public FriendDto AddFriend(@PathVariable Long first_user_id, @PathVariable Long second_user_id) {
        return friendService.AddFriend(first_user_id, second_user_id);
    }

    @Operation(
            summary = "Accept friends",
            description = "Accept friends",
            responses = {@ApiResponse(
                    description = "Success, accept friends",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = FriendEntity.class))
            ), @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = "/accept/{friend_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public FriendDto AcceptFriend(@PathVariable Long friend_id) {
        return friendService.AcceptFriend(friend_id);
    }

    @Operation(
            summary = "Denied friend",
            description = "Denied friend",
            responses = {@ApiResponse(
                    description = "Success, Get all messages",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = FriendEntity.class))
            ), @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = "/denied/{friend_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public FriendDto DeniedFriend(@PathVariable Long friend_id) {
        return friendService.DeniedFriend(friend_id);
    }

    @Operation(
            summary = "Delete friend",
            description = "Delete friend",
            responses = {@ApiResponse(
                    description = "Success, delete friend",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = FriendEntity.class))
            ), @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = FRIEND_ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public FriendDto DeleteFriend(@PathVariable Long friend_id) {
        return friendService.DeleteFriend(friend_id);
    }
}
