package com.ddalkkak.splitting.board.api.response;

import lombok.Builder;

import java.util.List;

public record BoardDetailedResponse(
        Long id,
        String title,
        String writer,
        String content,
        String modifyDate,

        List<Reply> replys
) {
     record Reply (
        String writer,
        String content,
        String modifyDate
    ) {}
}
