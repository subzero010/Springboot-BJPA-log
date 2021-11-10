package kr.co.redspring.blog.service;

import kr.co.redspring.blog.dto.ReplySaveRequestDto;
import kr.co.redspring.blog.model.Board;
import kr.co.redspring.blog.model.Reply;
import kr.co.redspring.blog.model.RoleType;
import kr.co.redspring.blog.model.User;
import kr.co.redspring.blog.repository.BoardRepository;
import kr.co.redspring.blog.repository.ReplyRepository;
import kr.co.redspring.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC를 해준다(메모리에 띄워준다)
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Transactional
    public void 글쓰기(Board board, User user){

        System.out.printf(">> 글쓰기 in");
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);//title, content
    }
    public Page<Board> 글목록(Pageable pageable){
        return boardRepository.findAll(pageable);
    }
    @Transactional(readOnly = true)
    public List<Board> 글목록(){
        return boardRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Board 글상세보기(int id){
        return boardRepository.findById(id).orElseThrow(()->{
           return new IllegalArgumentException("없는 게시물입니다.");
        });
    }
    @Transactional
    public void 글삭제하기(int id){
        boardRepository.deleteById(id);
    }
    @Transactional
    public void 글수정하기(int id, Board reqBoard){
        Board board = boardRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("없는 게시물입니다.");
        });// 영속화 완료
        board.setTitle(reqBoard.getTitle());
        board.setContent(reqBoard.getContent());
        // 해당 함수 종료시에 (Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티체킹 - 자동 업데이트가됨 db쪽으로 flush됨!!
    }

    //--------------------------
    // 댓글
    //--------------------------

    @Transactional
    public void 댓글쓰기(User user, int boardId, Reply reqReply){

        Board board = boardRepository.findById(boardId).orElseThrow(()->{
           return new IllegalArgumentException("댓글 쓰기 실패: 게시글 id를 찾을 수 없습니다.");
        });
        reqReply.setUser(user);
        reqReply.setBoard(board);

        replyRepository.save(reqReply);
    }
    @Transactional
    public void 댓글쓰기Dto(ReplySaveRequestDto replySaveRequestDto){
        System.out.println(">>>>>>>>>>  service 댓글쓰기Dto !!!!!!!");

        User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
            return new IllegalArgumentException("댓글 쓰기 실패: 유저 id를 찾을 수 없습니다.");
        });//영속화 완료
        Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
            return new IllegalArgumentException("댓글 쓰기 실패: 게시글 id를 찾을 수 없습니다.");
        });//영속화 완료
        /*
        Reply reply = Reply.builder()
                .user(user)
                .board(board)
                .content(replySaveRequestDto.getContent())
                .build();
        */

        //Reply에 update 함수만들어서 하는 방법 //69강 (8분)
        Reply reply = new Reply();
        reply.update(user, board, replySaveRequestDto.getContent());

        //System.out.println(reply);//오브젝트를 출럭하게 되면 자동으로 toString()이 호출된다.. StackOverflowError발생..잘되다가 또 발생 ㅠㅠ
        replyRepository.save(reply);
    }
    @Transactional   //70강 native sql 사용
    public int 댓글쓰기NativeSQL(ReplySaveRequestDto replySaveRequestDto){
        int iRtn = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
        return iRtn;
    }

    @Transactional
    public void 댓글삭제(int replyId){
        replyRepository.deleteById(replyId);
    }

}
