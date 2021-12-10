package com.runningmate.runningmate.image.service;

import com.runningmate.runningmate.image.domain.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageUploadService {

    public Image upload(MultipartFile file);

    public void delete(long imageId);
}
