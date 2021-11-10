package kr.co.redspring.blog.controller.api;

import kr.co.redspring.blog.config.auth.PrincipalDetail;
import kr.co.redspring.blog.dto.ReplySaveRequestDto;
import kr.co.redspring.blog.dto.ResponseDto;
import kr.co.redspring.blog.model.Board;
import kr.co.redspring.blog.model.Reply;
import kr.co.redspring.blog.model.User;
import kr.co.redspring.blog.service.BoardService;
import kr.co.redspring.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){// 53강
        System.out.printf(">> boardApiController.save ... in");
        boardService.글쓰기(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    @DeleteMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id){// 57강
        System.out.printf(">> boardApiController.delete ... in");
        boardService.글삭제하기(id);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    @PutMapping("/api/board/{id}")
    public ResponseDto<Integer> deleteById(@PathVariable int id, @RequestBody Board board){// 57강
        System.out.printf(">> boardApiController.update ... id : "+id);
        boardService.글수정하기(id, board);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }


    //--------------------------
    // 댓글
    //--------------------------

    @PostMapping("/api/board/{boardId}/reply")
    public ResponseDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply
            , @AuthenticationPrincipal PrincipalDetail principal){// 68강
        System.out.printf(">> boardApiController.replySave ... boardId : "+boardId);

        // 원래 데이터를 받을때 dto를 만들어주는게 좋다
        // dto를 사용안한 이유는 작은 프로젝트에서는 필요가 없어서..

        boardService.댓글쓰기(principal.getUser(), boardId, reply);

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    // dto로 전달받아서 처리
    @PostMapping("/api/board/{boardId}/reply_dto")
    public ResponseDto<Integer> replySaveDto(@RequestBody ReplySaveRequestDto replySaveRequestDto){// 68강
        //dto로 처리
        boardService.댓글쓰기Dto(replySaveRequestDto);

        //native sql로 처리
        /*
        int iRtn = boardService.댓글쓰기NativeSQL(replySaveRequestDto);

        //처리해야됨 !!! (강의에는 없음)
        if(iRtn>0){
        }
        else {
        }*/

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    @DeleteMapping("/api/board/{boardId}/reply/{replyId}")
    public ResponseDto<Integer> deleteReplyById(@PathVariable int boardId, @PathVariable int replyId){// 57강
        System.out.printf(">> boardApiController.deleteReplyById ... boardId:"+boardId + ", replyId:" + replyId);
        boardService.댓글삭제(replyId);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

}
