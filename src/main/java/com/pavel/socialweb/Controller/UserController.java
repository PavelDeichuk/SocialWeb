package com.pavel.socialweb.Controller;

import com.pavel.socialweb.Dto.UserDto;
import com.pavel.socialweb.Entity.UserEntity;
import com.pavel.socialweb.Model.ErrorModel;
import com.pavel.socialweb.Service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/user")
@Tag(name = "user")
public class UserController {
    private final UserService userService;

    private static final String USER_ID = "/{user_id}";

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Get all users",
            description = "Get all users",
            responses = {@ApiResponse(
            description = "Success, Get all users",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = UserEntity.class))
            ), @ApiResponse(
            description = "Error, List users is empty",
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )})
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDto> GetAllUsers(@RequestParam(value = "page",defaultValue = "0") int page,
                                     @RequestParam(value = "size", defaultValue = "8") int size) {
        return userService.GetAllUsers(page,size);
    }

    @Operation(
            summary = "Get user by id",
            description = "Get user by id",
            parameters = @Parameter(name = "user_id", description = "field user id", example = "1"),
    responses = {@ApiResponse(
            description = "Success, Get user",
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = UserEntity.class))
            ), @ApiResponse(
            description = "Error, not found for user",
            responseCode = "404",
            content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
    security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = USER_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto GetUserById(@PathVariable Long user_id) {
        return userService.GetUserById(user_id);
    }

    @Operation(
            summary = "Create user",
            description = "Create user",
            parameters = {@Parameter(name = "username", description = "field username", example = "test"),
            @Parameter(name = "password", description = "field password", example = "test"),
            @Parameter(name = "email", description = "field email", example = "test@mail.ru")},
            responses = {@ApiResponse(
                    description = "Success, Create user",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UserEntity.class))
            ), @ApiResponse(
                    description = "Error, not found for user",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            ), @ApiResponse(
                    description = "Error, username is exist!",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            ), @ApiResponse(
                    description = "Error, email is exist!",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto CreateUser(@RequestBody UserEntity user, BindingResult bindingResult) {
        return userService.CreateUser(user, bindingResult);
    }

    @Operation(
            summary = "Edit user",
            description = "Edit user",
            responses = {@ApiResponse(
                    description = "Success, Edit user",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UserEntity.class))
            ), @ApiResponse(
                    description = "Error, not found for user",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = USER_ID, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto EditUser(@PathVariable Long user_id, @RequestBody UserEntity user, BindingResult bindingResult) {
        return userService.EditUser(user_id, user, bindingResult);
    }

    @Operation(
            summary = "Delete user by id",
            description = "Delete user by id",
            parameters = @Parameter(name = "user_id", description = "field user id", example = "1"),
            responses = {@ApiResponse(
                    description = "Success, Delete user",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UserEntity.class))
            ), @ApiResponse(
                    description = "Error, not found for user",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = USER_ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserDto DeleteUser(@PathVariable Long user_id) {
        return userService.DeleteUser(user_id);
    }
}
