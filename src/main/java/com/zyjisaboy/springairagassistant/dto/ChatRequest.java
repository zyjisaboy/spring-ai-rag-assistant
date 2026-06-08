package com.zyjisaboy.springairagassistant.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChatRequest(
        @Schema(description = "用户消息", example = "你好，请介绍一下你自己")
        String message
) {}
