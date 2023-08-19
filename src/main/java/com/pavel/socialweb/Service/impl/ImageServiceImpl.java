package com.pavel.socialweb.Service.impl;

import com.pavel.socialweb.Dto.ImageDto;
import com.pavel.socialweb.Entity.ImageEntity;
import com.pavel.socialweb.Exception.BadRequestException;
import com.pavel.socialweb.Exception.NotFoundException;
import com.pavel.socialweb.Mapper.ImageMapper;
import com.pavel.socialweb.Repository.ImageRepository;
import com.pavel.socialweb.Service.ImageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    public ImageServiceImpl(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    @Override
    public List<ImageEntity> UploadImage(MultipartFile[] multipartFiles) throws IOException {
        List<ImageEntity> imageEntity = new ArrayList<>();
        for (int i = 0; i < multipartFiles.length; i++) {
            ImageEntity image = new ImageEntity();
            File fileSave = new File(multipartFiles[i].getOriginalFilename());
            FileOutputStream fileOutputStream = new FileOutputStream(fileSave);
            fileOutputStream.write(multipartFiles[i].getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
            image.setSize(multipartFiles[i].getSize());
            image.setName(fileSave.getName());
            image.setPath(fileSave.getPath());
            imageEntity.add(image);
        }
        imageRepository.saveAll(imageEntity);
        System.out.println(imageEntity);
        return imageEntity;
    }

    @Override
    public ImageDto DeleteImage(Long image_id) {
        ImageEntity image = imageRepository
                .findById(image_id)
                .orElseThrow(() -> new NotFoundException("Not found for image id!"));
        File file = new File(image.getPath());
        boolean delete = file.delete();
        if(!delete){
            throw new BadRequestException("Error to delete file!");
        }
        imageRepository.deleteById(image_id);
        return imageMapper.IMAGE_DTO(image);
    }
}
