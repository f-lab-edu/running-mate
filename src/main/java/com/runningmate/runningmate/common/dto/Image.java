package com.runningmate.runningmate.common.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class Image {

    @NonNull
    private String original_name;

    @NonNull
    private String store_name;

    @NonNull
    private String extension;

    @NonNull
    private String path;
}
