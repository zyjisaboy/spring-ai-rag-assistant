package com.zyjisaboy.springairagassistant.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record UploadResponse(
        @Schema(description = "文件名")
        String filename,
        @Schema(description = "文件大小（字节）")
        long size,
        @Schema(description = "上传结果消息")
        String message
) {}
