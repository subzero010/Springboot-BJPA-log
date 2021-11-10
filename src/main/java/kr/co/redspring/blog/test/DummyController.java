package kr.co.redspring.blog.test;

import kr.co.redspring.blog.model.RoleType;
import kr.co.redspring.blog.model.User;
import kr.co.redspring.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

// data를 return하는 controller
@RestController
public class DummyController {

    @Autowired  //의존성 주입(DI)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable int id){
        try{
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            return "del fail : not found id("+id+")";
        }
        return "del ok : "+ id;
    }

    //@RequestBody는 json데이타를 받아올때 (MessageConverter의 Jacson라이브러리가
    @Transactional  //★save(user)를 호출안해도  변경이 된다   더티체킹이라 한다!!!  ★함수종료시에 자동으로 commit이 된다.  29강
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User reqUser){
        // 포스트맨: PUT, body에 json 데이타, JSON(application/json)선택
        System.out.println(">> " + id);
        System.out.println(">> " + reqUser.getPassword());
        System.out.println(">> " + reqUser.getEmail());

        //passwork, email만 수정
        reqUser.setId(id);
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정 실패");
        });
        user.setPassword(reqUser.getPassword());
        user.setEmail(reqUser.getEmail());

        //userRepository.save(user);//pk값이 있으면 update 처리
        //@Transactional를 설정하고 save(user)를 호출안해도  변경이 된다   더티체킹이라 한다!!!

        return user;
    }


    ////////////////   select /////////////////


    // http://localhost:8080/blog/dummy/users
    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }
    // 한페이지당 2건에 데이터를 리턴  ... 데어프로그래밍 27강
    // http://localhost:8080/blog/dummy/user?page=0
    @GetMapping("/dummy/userL")
    public Page<User> __pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<User> users = userRepository.findAll(pageable);
        return users;
    }
    @GetMapping("/dummy/userPage")
    public List<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<User> pagingUser = userRepository.findAll(pageable);

        if(pagingUser.isFirst()){ // isLast()
            //....
        }
        List<User> users = pagingUser.getContent();
        return users;
    }


    // {id} 주소로 파라미터를 전달받음
    // http://localhost:8080/blog/dummy/user/3
    @PostMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        // user값이 없을때 null이 될 경우을 방지하기 위해
        // Optional로 User객체를 감싸서 가져오니 null인지 판단해서 return
        /*
        User user = userRepository.findById(id).orElseGet(new Supplier<User>() {
            @Override
            public User get() {
                return new User();
            }
        });*/
        /*   orElseThrow를 추천
        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당 사용자는 없습니다. id: "+id);
            }
        });
        // exception이 생기면 가로채서 error화면을 보여주면좋다 AOP개념
        */
        // 람다식 
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 사용자는 없습니다. id: "+id);
        });

        // 웹브라우저가 이해할 수 있는 데이타로 변환해야됨(json)
        // 스프링부트는 MessageConverter가 응답시에 자동작동해서 자바obj를 return을 하면
        // Jackson 라이브러리르 호출해서 user 오브젝트를 json으로 변환해서 브라우저에 던져준다.
        return user;
    }
    // http://localhost:8080/blog/dummy/join 요청
    // http의 body에 username, password, email 데이타를 가지고 요청
    @PostMapping("/dummy/join")
            public String join(User user, @RequestParam("username") String um, String password, String email){
        System.out.println(">> " + user.getUsername());
        System.out.println(">> " + um);
        System.out.println(">> " + password);
        System.out.println(">> " + email);

        /* insert */
        user.setRole(RoleType.USER);//디폴트값을 set
        userRepository.save(user);//insert

        /* update */
        //User detail = userRepository.getById(user.getId());
        //userRepository.save(detail);//update(pk가 있으면)   createDate는 update시 null값이 되므로 처리해야됨

        return "회원가입이 완료되었습니다.";
    }
}
