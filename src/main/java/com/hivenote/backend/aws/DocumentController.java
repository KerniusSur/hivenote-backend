package com.hivenote.backend.aws;

import com.hivenote.backend.aws.dto.FileUploadResponse;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
@ConditionalOnProperty(value = "aws.isDisabled", havingValue = "false", matchIfMissing = true)
public class DocumentController {
  private final DocumentService documentService;

  public DocumentController(DocumentService documentService) {
    this.documentService = documentService;
  }

  @PostMapping("/upload")
  public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
    return documentService.upload(file);
  }
}
