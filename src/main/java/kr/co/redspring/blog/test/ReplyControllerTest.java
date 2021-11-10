package kr.co.redspring.blog.test;

import kr.co.redspring.blog.model.Board;
import kr.co.redspring.blog.model.Reply;
import kr.co.redspring.blog.repository.BoardRepository;
import kr.co.redspring.blog.repository.ReplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReplyControllerTest {  //67강 무한참조 방지!!

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    // Board를 불렀을때 Board내 Reply가 또 Board를 호출하게되어서.. 무한 참조되고 있는거 확인!!  그래서..
    // Board안에 @JsonIgnoreProperties({"board"})를 추가해서 .. 무한 참조방지!!!!
    @GetMapping("/test/board/{id}")
    public Board getBoard(@PathVariable int id){
        return boardRepository.findById(id).get();
    }

    // 이렇게 바로 Reply를 다리렉트로 값을 가져올때는 Board데이타를 가져올 수 있고,
    // 이 Board안에 Reply가 있어도 Board를 가져오지 않고 있어 무한 참조되지 않고 있다. (@JsonIgnoreProperties({"board"})를 추가로 인해)
    @GetMapping("/test/reply")
    public List<Reply> getReply(){
        return replyRepository.findAll();
    }


}
