package com.hivenote.backend.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class DocumentController {
    private final DocumentService documentService;

    @PostMapping
    public void saveImage(@RequestParam("file") MultipartFile file) {
        documentService.upload(file);
    }
}
