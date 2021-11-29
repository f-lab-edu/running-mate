package com.runningmate.runningmate.image.domain.repository;

import com.runningmate.runningmate.image.domain.entity.Image;

public interface ImageRepository {
    public Image fineById(long imageId);
    public void save(Image image);
}
