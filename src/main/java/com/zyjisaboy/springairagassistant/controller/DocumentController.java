package com.zyjisaboy.springairagassistant.controller;

import com.zyjisaboy.springairagassistant.dto.UploadResponse;
import com.zyjisaboy.springairagassistant.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/document")
@Tag(name = "文档", description = "文档上传接口")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "上传文档", description = "上传 PDF 或 TXT 文档，用于 RAG 知识库")
    public ResponseEntity<UploadResponse> upload(@RequestPart("file") MultipartFile file) {
        try {
            var savedPath = documentService.saveFile(file);
            return ResponseEntity.ok(new UploadResponse(
                    savedPath.getFileName().toString(),
                    file.getSize(),
                    "上传成功"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new UploadResponse(file.getOriginalFilename(), file.getSize(), "上传失败: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new UploadResponse(file.getOriginalFilename(), 0, "上传失败: 服务器内部错误"));
        }
    }
}
