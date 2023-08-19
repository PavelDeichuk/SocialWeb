package com.pavel.socialweb.Controller;

import com.pavel.socialweb.Dto.PostDto;
import com.pavel.socialweb.Entity.MessageEntity;
import com.pavel.socialweb.Entity.PostEntity;
import com.pavel.socialweb.Model.ErrorModel;
import com.pavel.socialweb.Service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/post")
@Tag(name = "post")
public class PostController {
    private final PostService postService;

    private static final String POST_ID = "/{post_id}";

    private static final String CREATE_POST = "/user/{user_id}";

    private static final String EDIT_POST = "/{post_id}/user/{user_id}";

    private static final String GET_ALL_POST_BY_ACTIVITY = "/activity/user/{user_id}";

    private static final String USER_ID = "/user/{user_id}";

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @Operation(
            summary = "Get all posts",
            description = "Get all posts",
            responses = {@ApiResponse(
                    description = "Success, Get all posts",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))
            ), @ApiResponse(
                    description = "Error, list post is empty!",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )})
    @RequestMapping(value = USER_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDto> GetAllPostsByUser(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "8") int size,
                                           @PathVariable Long user_id) {
        return postService.GetAllPostByUser(page,size,user_id);
    }

    @Operation(
            summary = "Get all posts by activity",
            description = "Get all posts by activity",
            responses = {@ApiResponse(
                    description = "Success, Get all posts by activity",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))
            ), @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = GET_ALL_POST_BY_ACTIVITY, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PostDto> GetAllPostByActivity(@PathVariable Long user_id) {
        return postService.GetAllPostsBySubscription(user_id);
    }

    @Operation(
            summary = "Get post by id",
            description = "Get post by id",
            responses = {@ApiResponse(
                    description = "Success, get post by id",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))
            ), @ApiResponse(
                    description = "Error, not found post by id",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = POST_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto GetPostById(@PathVariable Long post_id) {
        return postService.GetPostById(post_id);
    }

    @Operation(
            summary = "Create post",
            description = "Create post",
            responses = {@ApiResponse(
                    description = "Success, post created",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))
            ), @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = CREATE_POST, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto CreatePost(@PathVariable Long user_id,
                              @RequestPart(value = "files", required = false) MultipartFile[] files,
                              @RequestPart("post") PostEntity post,
                              BindingResult bindingResult) throws IOException {
        return postService.CreatePost(user_id,post, files, bindingResult);
    }

    @Operation(
            summary = "Edit post",
            description = "Edit post",
            responses = {@ApiResponse(
                    description = "Success, Edit post",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))
            ), @ApiResponse(
                    description = "Error",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = EDIT_POST, method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto EditPost(@PathVariable Long user_id,
                            @PathVariable Long post_id,
                            @RequestBody PostEntity postEntity,
                            BindingResult bindingResult) {
        return postService.EditPost(user_id, post_id, postEntity, bindingResult);
    }

    @Operation(
            summary = "Delete post",
            description = "Delete post",
            responses = {@ApiResponse(
                    description = "Success, delete post",
                    responseCode = "200",
                    content = @Content(schema = @Schema(implementation = PostEntity.class))
            ), @ApiResponse(
                    description = "Error, not found for post id!",
                    responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorModel.class))
            )},
            security = @SecurityRequirement(name = "BearerJWT"))
    @RequestMapping(value = POST_ID, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public PostDto DeletePost(@PathVariable Long post_id) {
        return postService.DeletePost(post_id);
    }
}
