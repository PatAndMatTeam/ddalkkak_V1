package com.ddalkkak.splitting.swagger.api;

import com.ddalkkak.splitting.board.api.request.BoardCreateRequest;
import com.ddalkkak.splitting.board.api.request.BoardUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Board API", description = "Board 관련 API 입니다.")
public interface BoardApiDocs {

    @Operation(summary = "글 생성", description = "글을 생성합니다.")
    @Parameter(name = "category", description = "글의 카테고리")
    @Parameter(name = "title", description = "글 제목")
    @Parameter(name = "content", description = "글 내용")
    @Parameter(name = "writer", description = "글 작성자")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 저장 성공"),
            @ApiResponse(responseCode = "409", description = "유저 정보 저장 실패(유저 중복)") })
    public void getBoard();
    public void createBoard(@RequestBody BoardCreateRequest boardCreateRequest);
    public void getBoards(@PathVariable("num") long num);
    public void updateBoard(@PathVariable("num") long num,
                            @RequestBody BoardUpdateRequest boardUpdateRequest);
    public void removeBoard(@PathVariable("num") long num);


}
