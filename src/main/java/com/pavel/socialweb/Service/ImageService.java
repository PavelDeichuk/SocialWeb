package com.pavel.socialweb.Service;

import com.pavel.socialweb.Dto.ImageDto;
import com.pavel.socialweb.Entity.ImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface ImageService {
    List<ImageEntity> UploadImage(MultipartFile[] multipartFile) throws IOException;

    ImageDto DeleteImage(Long image_id);
}
