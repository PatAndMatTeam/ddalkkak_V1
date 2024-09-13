package com.ddalkkak.splitting.reply.api.reqeust;


import lombok.Builder;

@Builder
public record ReplyCreateRequest(
        Long id,
        Long sub,
        String content,
        String writer,
        String createDate
) {
}
