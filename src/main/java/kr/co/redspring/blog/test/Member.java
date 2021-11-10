package kr.co.redspring.blog.test;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor  /*lombok 생성자*/
@NoArgsConstructor  /*lombok 빈생성자*/
public class Member {
    private int id;
    private String username;
    private String password;
    private String email;

    @Builder  /* lombok에서 지원 */
    public Member(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

}
