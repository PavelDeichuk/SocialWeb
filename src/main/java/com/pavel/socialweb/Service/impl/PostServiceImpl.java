package com.pavel.socialweb.Service.impl;

import com.pavel.socialweb.Dto.PostDto;
import com.pavel.socialweb.Entity.ImageEntity;
import com.pavel.socialweb.Entity.PostEntity;
import com.pavel.socialweb.Entity.SubscriptionEntity;
import com.pavel.socialweb.Entity.UserEntity;
import com.pavel.socialweb.Exception.BadRequestException;
import com.pavel.socialweb.Exception.NotFoundException;
import com.pavel.socialweb.Mapper.ImageMapper;
import com.pavel.socialweb.Mapper.PostMapper;
import com.pavel.socialweb.Repository.PostRepository;
import com.pavel.socialweb.Repository.SubscriptionRepository;
import com.pavel.socialweb.Repository.UserRepository;
import com.pavel.socialweb.Service.ImageService;
import com.pavel.socialweb.Service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final SubscriptionRepository subscriptionRepository;
    private final PostMapper postMapper;


    private final ImageService imageService;

    public PostServiceImpl(PostRepository postRepository, UserRepository userRepository, SubscriptionRepository subscriptionRepository, PostMapper postMapper, ImageService imageService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.subscriptionRepository = subscriptionRepository;
        this.postMapper = postMapper;
        this.imageService = imageService;
    }

    @Override
    public List<PostDto> GetAllPostByUser(int page, int size, Long user_id) {
        UserEntity user = userRepository
                .findById(user_id)
                .orElseThrow(() -> new NotFoundException("Not found for user id!"));
        Pageable pageable = PageRequest.of(page,size);
        Page<PostEntity> posts = postRepository.findByUser(user, pageable);
        if(posts.isEmpty()) {
            throw new NotFoundException("Posts for user is null!");
        }
        return posts
                .stream()
                .map(postMapper::POST_DTO)
                .toList();
    }

    @Override
    public List<PostDto> GetAllPostsBySubscription(Long user_id) {
        UserEntity user = userRepository
                .findById(user_id)
                .orElseThrow(() -> new NotFoundException("Not found for user id!"));
        List<SubscriptionEntity> subscriptionEntity = subscriptionRepository.findBySubUser(user);
        if(subscriptionEntity.isEmpty()){
            throw new NotFoundException("Not found for sub id!");
        }
        List<PostEntity> posts = new ArrayList<>();
        for (var sub : subscriptionEntity) {
            List<PostEntity> post = sub.getSubUser().getPost();
            posts.addAll(post);
        }
        return posts
                .stream()
                .map(postMapper::POST_DTO)
                .toList();
    }

    @Override
    public PostDto GetPostById(Long post_id) {
        PostEntity post = postRepository
                .findById(post_id)
                .orElseThrow(() -> new NotFoundException("Not found for post id!"));
        return postMapper.POST_DTO(post);
    }

    @Override
    public PostDto CreatePost(Long user_id, PostEntity postEntity, MultipartFile[] multipartFiles, BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors){
                throw new BadRequestException(error.getObjectName() + " " + error.getDefaultMessage());
            }
        }
        UserEntity user = userRepository
                .findById(user_id)
                .orElseThrow(() -> new NotFoundException("Not found for user id!"));
        if(multipartFiles != null) {
           List<ImageEntity> imageEntity = imageService.UploadImage(multipartFiles);
           postEntity.setImages(imageEntity);
        }
        postRepository
                .findByTitle(postEntity.getTitle())
                .ifPresent(post -> {
                    throw new BadRequestException("post is exist!");
                });
        postEntity.setUser(user);
        PostEntity save = postRepository.save(postEntity);
        return postMapper.POST_DTO(save);
    }

    @Override
    public PostDto EditPost(Long user_id, Long post_id, PostEntity postEntity, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error : fieldErrors){
                throw new BadRequestException(error.getObjectName() + " " + error.getDefaultMessage());
            }
        }
        UserEntity user = userRepository
                .findById(user_id)
                .orElseThrow(() -> new NotFoundException("Not found for user id!"));
        PostEntity post = postRepository
                .findById(post_id)
                .orElseThrow(() -> new NotFoundException("Not found for post id!"));
        post.setTitle(postEntity.getTitle());
        post.setDescription(postEntity.getDescription());
        PostEntity save = postRepository.save(post);
        return postMapper.POST_DTO(save);
    }

    @Override
    public PostDto DeletePost(Long post_id) {
        PostEntity post = postRepository
                .findById(post_id)
                .orElseThrow(() -> new NotFoundException("Not found for post id!"));
        postRepository.deleteById(post_id);
        return postMapper.POST_DTO(post);
    }
}
