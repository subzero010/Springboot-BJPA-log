package kr.co.redspring.blog.service;

import kr.co.redspring.blog.model.RoleType;
import kr.co.redspring.blog.model.User;
import kr.co.redspring.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


//스프링이 컴포넌트 스캔을 통해서 Bean에 등록해줌. IoC를 해준다(메모리에 띄워준다)
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;


    @Transactional
    public void 회원가입(User user){//회원가입

        System.out.printf(">> 회원가입 in");
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);

        user.setPassword(encPassword);
        user.setRole(RoleType.USER);

        //40강의에서는 username을 중복등록하면 error가 발생되는데... 나는 중복등록됨 ㅠㅠ
        userRepository.save(user);

        //exception이 발생되면 GlobalExceptionHandler 로 가니깐.-> .. return을 void, try catch가 필요없다.   40강
        /*
        try{
            userRepository.save(user);
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println(">> UserService.save : "+ e.getMessage());
        }
        return -1;
        */
    }

    /* spring security 사용으로 인해 주석처리
    @Transactional(readOnly = true)  // select 할 때 트랜잭션 시작, 서비스가 종료될때 트랜잭션 종료할떄까지 정합성을 유지 (select를 여러번해도 같은 데이타를 유지)
    public User 로그인(User user){
        return  userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
    }
    */

    @Transactional
    public void 회원수정(User user) { // 60강~61강
        // 수정시에는 영속성 컨텍스트 User오브젝트를 영속화시키고, 영속화된 User 오브젝트를 수정!
        //영속화하기 위해 db로 가져온다.영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
        User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
           return new IllegalArgumentException("회원 찾기 실패");
        });
        String rawPassword = user.getPassword();
        String encPassword = encoder.encode(rawPassword);
        persistance.setPassword(encPassword);
        persistance.setEmail(user.getEmail());
        //회원수정 함수 종료 = 서비스 종료 = 트랜잭션 종료 = commit이 자동실행..
        //userRepository.save(user)  .. 영속화되었기 때문에 이 부분 생략해도됨!!

    }

}
