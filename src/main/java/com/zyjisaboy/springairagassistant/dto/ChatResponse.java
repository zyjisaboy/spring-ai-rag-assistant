package com.zyjisaboy.springairagassistant.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public record ChatResponse(
        @Schema(description = "AI 回复内容")
        String reply
) {}
