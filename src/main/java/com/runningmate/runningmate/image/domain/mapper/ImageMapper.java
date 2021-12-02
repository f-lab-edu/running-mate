package com.runningmate.runningmate.image.domain.mapper;

import com.runningmate.runningmate.image.domain.entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    public Image selectImage(long imageId);
    public void insertImage(Image image);
    public void deleteImage(Image image);
}
