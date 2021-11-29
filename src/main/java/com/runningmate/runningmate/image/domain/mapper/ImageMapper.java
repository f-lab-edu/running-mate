package com.runningmate.runningmate.image.domain.mapper;

import com.runningmate.runningmate.image.domain.entity.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {
    public void insertImage(Image image);
}
