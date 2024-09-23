package com.ddalkkak.splitting.comment.api.response;

import lombok.Builder;

import java.util.List;

@Builder
public record CommentsResponse(

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
