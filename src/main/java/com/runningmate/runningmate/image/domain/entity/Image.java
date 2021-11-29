package com.runningmate.runningmate.image.domain.entity;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Image {
    private long imageId;
    private String originalName;
    private String storeName;
    private String extension;
    private String path;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
