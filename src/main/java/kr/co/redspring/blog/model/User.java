package kr.co.redspring.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

// ORM -> java(다른언어 포함) Object를 -> 테이블로 메핑해주는 기술
@Builder //빌더패턴!
@AllArgsConstructor  //전체 생성자
@NoArgsConstructor
//@DynamicInsert   //insert시 null인 값은 inset문에서 제외시킴 (여기서는 role값이 null이면 default값이 들어간다).. 근데 너무 어노테이션에 의지하면 안된다.
@Data
@Entity  // User 클래스가 Mysql에 테이블이 생성이 된다
public class User {

    @Id  //Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)// 프로젝트에서 연결된 db의 넘버링 전략을 따라간다.
    private int id;//시퀀스, auto_incrrement

    @Column(nullable = false, length = 30)
    private String username;//아이디

    @Column(nullable = false, length = 100) //123456 => 해위 (비밀번호 암호화)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    //@ColumnDefault("'user'")  //문자에 주의 'user'로,   이부분을 없애고 set처리하는 걸로
    //private String role;// Enum을 쓰는게 좋다. ADMIN, USER, MANAGER
    // DB는 RoleType이 없으므로 string이라고 알려줘야됨
    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp //시작을 자동 입력
    private Timestamp createDate;

}
