package com.pavel.socialweb.Service;

import com.pavel.socialweb.Dto.PostDto;
import com.pavel.socialweb.Entity.PostEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    List<PostDto> GetAllPostByUser(int page, int size,Long user_id);

    List<PostDto> GetAllPostsBySubscription(Long user_id);

    PostDto GetPostById(Long post_id);

    PostDto CreatePost(Long user_id, PostEntity postEntity, MultipartFile[] multipartFiles, BindingResult bindingResult) throws IOException;

    PostDto EditPost(Long user_id, Long post_id, PostEntity postEntity, BindingResult bindingResult);

    PostDto DeletePost(Long post_id);
}
