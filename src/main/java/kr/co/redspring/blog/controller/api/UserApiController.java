package kr.co.redspring.blog.controller.api;

import kr.co.redspring.blog.config.auth.PrincipalDetail;
import kr.co.redspring.blog.dto.ResponseDto;
import kr.co.redspring.blog.model.RoleType;
import kr.co.redspring.blog.model.User;
import kr.co.redspring.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    //@Autowired
    //private HttpSession session;
    @Autowired
    private AuthenticationManager authenticationManager;//61강(32분) 회원정보변경시 강제로 세션값 변경을 하기 위해

    @Autowired
    private UserService userService;

    @PostMapping("/auth/joinProc")
    public ResponseDto<Integer> save(@RequestBody User user){

        userService.회원가입(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    @PutMapping("/user/updateProc")
    public ResponseDto<Integer> updateProc(@RequestBody User user ){// @RequestBody로 json 데이타를 받음
        System.out.printf(">> UserApiController updateProc .. id: "+user.getId());
        userService.회원수정(user);

        // 여기서는 트랜잭션이 종료시 db값은 변경되었지만, 세션값은 변경되지 않은 상태이어서..
        // 직접 세션값을 변경해줘야됨     61강
        // ★세션등록 ... DB내용이 변경된후 해야됨!!!!!!   61강(32분~)
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);//세션에 직접 접근할 필요없음. 자동으로 해줌

        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    // 이 방식말고 스프링 시큐리티를 이용해서 로그인 구현 47강
    /*
    @PostMapping("/api/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){

        User principal = userService.로그인(user);// principal(접근주체)
        if(principal != null){
            session.setAttribute("principal", principal);
        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
    */



}
