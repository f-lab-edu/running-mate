package com.runningmate.runningmate.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
public class UserImageSaveDto {

    @NonNull
    private String originalName;

    @NonNull
    private String storeName;

    @NonNull
    private String extension;

    @NonNull
    private String path;
}
