package com.runningmate.runningmate.image.domain.entity;

import static com.runningmate.runningmate.image.domain.entity.ImageStatus.*;

import lombok.*;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Image {

    private long imageId;
    private ImageStatus status;
    private String originalFileName;
    private String storageFileName;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    public static class ImageBuilder {

        public ImageBuilder storageFileName(String originalFileName) {
            this.storageFileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(originalFileName);
            return this;
        }
    }

    public void delete() {
        status = NOT_USED;
        updateDate = LocalDateTime.now();
    }
}
