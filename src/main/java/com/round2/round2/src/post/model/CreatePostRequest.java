package com.round2.round2.src.post.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@AllArgsConstructor
public class CreatePostRequest {

    private int categoryId;
    private String title;
    private String content;
    private Boolean isAnonymous;
}
