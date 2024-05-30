package com.hivenote.backend.aws.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class FileUploadResponse {
    private Integer success; // 1 for success, 0 for failure
    private FileUrlResponse file;
}


