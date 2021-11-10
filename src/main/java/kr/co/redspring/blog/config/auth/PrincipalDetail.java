package kr.co.redspring.blog.config.auth;

import kr.co.redspring.blog.model.User;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인이 완료되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티의 고유한 세션저장소에 저장을 해준다.
@Getter
public class PrincipalDetail implements UserDetails {  //52강

    private User user;//콤포지션(객체를 품고있는것)

    public PrincipalDetail(User user){
        this.user = user;
    }
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
    // 계정이 만료되지 않았는지 리턴한다.(true 만료안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 계정이 잠겨있지 않았는지 리턴한다.(true 잠기자 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 비밀번호가 만료되지 않았는지 리턴 (true 만료되지않음)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 계정활성화 여부를 리턴 (true 활성화되어있음)
    @Override
    public boolean isEnabled() {
        return true;
    }
    // 계정이 가지고있는 권한 목록을 리턴한다.(권한이 여러개 있을 수 있어서 루프를 돌아야 하는데.. 여기는 한개만)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(()->{ return "ROLE_"+user.getRole();});
        /*
        collectors.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return "ROLE_"+user.getRole();// 스프링 ROLE 표기 규칙!!  (EX)"ROLE_USER" 형태로 해야됨   52강
            }
        });*/
        return collectors;
    }
}
