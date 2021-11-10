package kr.co.redspring.blog.config;

import kr.co.redspring.blog.config.auth.PrincipalDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 빈등록 : 스프링 컨테이너에서 객체를 관리할 수 있게하는 것
@Configuration   //빈등록 (IoC관리)
@EnableWebSecurity  //필터 추가 = 활성화된 시큐리트를 설정  (모든 req를 캐치해서 필더링을 한다)
@EnableGlobalMethodSecurity(prePostEnabled = true)  //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig extends WebSecurityConfigurerAdapter { //49강

    @Autowired
    private PrincipalDetailService principalDetailService;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception { //61강(32분) 회원정보변경시 강제로 세션값 변경을 하기 위해
        return super.authenticationManagerBean();
    }

    @Bean  //IoC
    public BCryptPasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }

    // 시큐리티가 대신 로그인해주는데 password를 가로채기를 하는데, 해당 password가 뭘로 해쉬가 되어 회원가입을했는지 알아야..
    // 같은 해쉬로 암호화해서 db에 있는 값과 비교 가능
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {  //52강
        //principalDetailService를 통해서 password비교를 한다.
        auth.userDetailsService(principalDetailService).passwordEncoder(encoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {  //49강~50강
        http
              .csrf().disable()  //csrf토큰 비활성화 .. 테스트시에만 걸어두는게 좋음!!!!  우리는 ajax로 했기때문에 csrf토큰이 없어서.나중에 csrf토근 생성    51강
              .authorizeRequests()      /*요청 req가 들어오면 */
                .antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**", "/dummy/**")
                .permitAll()    /* /auth 허용*/
                .anyRequest()   /* 다른 모든 요청은 */
                .authenticated() /* 인증해야됨 */
              .and()
                .formLogin()
                .loginPage("/auth/loginForm") /* 인증이필요한곳에서 요청이 오면 */
                .loginProcessingUrl("/auth/loginProc")  /* 시큐리티가 이 주소 요청을 가로채서 대신 로그인처리  52강*/
                .defaultSuccessUrl("/"); /* 로그인 후 여기로 이동 */

    }
}
