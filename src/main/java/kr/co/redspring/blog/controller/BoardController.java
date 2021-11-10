package kr.co.redspring.blog.controller;

import kr.co.redspring.blog.config.auth.PrincipalDetail;
import kr.co.redspring.blog.model.Board;
import kr.co.redspring.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;


    @GetMapping({"","/"})
    public String index(Model model, @PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){

        Page<Board> pagingList = boardService.글목록(pageable);
        model.addAttribute("list", pagingList);

        return "index";// viewResolver작동 (model정보를 가지고 이동)
    }
    //
    @GetMapping({"board/{id}"})
    public String boardFindById(@PathVariable int id, Model model){  //56강
        model.addAttribute("board", boardService.글상세보기(id));
        return "/board/detail";
    }
    // USER 권한이 필요
    @GetMapping({"board/saveForm"})
    public String saveForm(){  //53강
        return "board/saveForm";
    }
    @GetMapping({"board/{id}/updateForm"})
    public String updateForm(@PathVariable int id, Model model){  //58강
        System.out.println(">> board/{id}/updateForm .. id: "+id);
        model.addAttribute("board", boardService.글상세보기(id));
        return "board/updateForm";
    }

/*
    public String index(@AuthenticationPrincipal PrincipalDetail principal){

        System.out.println(">> 로그인 사용자 id: "+ principal.getUsername());
        return "index";
    }
*/



}
