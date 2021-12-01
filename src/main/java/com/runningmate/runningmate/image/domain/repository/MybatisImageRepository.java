package com.runningmate.runningmate.image.domain.repository;

import com.runningmate.runningmate.image.domain.entity.Image;
import com.runningmate.runningmate.image.domain.mapper.ImageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MybatisImageRepository implements ImageRepository {

    private final ImageMapper imageMapper;

    @Override
    public Image findById(long imageId) {
        return imageMapper.selectImage(imageId);
    }

    @Override
    public void save(Image image) {
        imageMapper.insertImage(image);
    }
}
