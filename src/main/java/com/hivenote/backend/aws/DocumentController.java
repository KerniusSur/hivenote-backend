package com.hivenote.backend.aws;

import com.hivenote.backend.aws.dto.FileUploadResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file")
public class DocumentController {
  private final DocumentService documentService;

  public DocumentController(DocumentService documentService) {
    this.documentService = documentService;
  }

  @PostMapping("/upload/{componentId}")
  public FileUploadResponse uploadImage(
      @RequestParam("file") MultipartFile file, @PathVariable("componentId") String componentId) {
    return documentService.upload(file);
  }

  @PostMapping("/upload")
  public FileUploadResponse uploadFile(@RequestParam("file") MultipartFile file) {
    return documentService.upload(file);
  }
}
