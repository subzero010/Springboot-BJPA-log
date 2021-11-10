package kr.co.redspring.blog.config.auth;

import kr.co.redspring.blog.model.User;
import kr.co.redspring.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service // Bean등록
public class PrincipalDetailService implements UserDetailsService {  // 52강

    @Autowired
    private UserRepository userRepository;

    // 스프링이 로그인 요청을 가로챌때, username, password 변수2개를 가로채는데
    // password 체크는 알아서 처리함
    // username이 db에 있는지만 확인하는걸 여기서함.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {//52강
        User principal = userRepository.findByUsername(username)
                .orElseThrow(()-> {
                    return new UsernameNotFoundException("해당 사용자를 찾을 수 없습니다."+username);
                });
        return new PrincipalDetail(principal);// 시큐리티 세션의 유저정보를 저장
    }
}
