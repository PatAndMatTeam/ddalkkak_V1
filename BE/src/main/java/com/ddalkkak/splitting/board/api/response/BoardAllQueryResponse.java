package com.ddalkkak.splitting.board.api.response;

import lombok.Builder;

public record BoardAllQueryResponse(
        Long id,
        String title,
        String writer,
        String modifyDate
) {
}
