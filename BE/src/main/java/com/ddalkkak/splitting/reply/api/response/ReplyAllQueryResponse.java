package com.ddalkkak.splitting.reply.api.response;

import lombok.Builder;

import java.util.List;

@Builder
public record ReplyAllQueryResponse(

        List<ReplyQuery> replys
) {
    private record ReplyQuery(
            Long id,
            Long parentId,
            String writer,
            String content,
            String modifyDate
    ){}
}
