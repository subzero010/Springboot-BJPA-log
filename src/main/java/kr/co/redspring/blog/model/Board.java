package kr.co.redspring.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_incrrement
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob //대용량 데이터
    private String content;// 섬머노트 라이브러리 사용 <html>태그가 섞여서 디자인이 됨

    private int count;//조회수

    //연관관계   Many=Board, User=One  한명의 user는 여러개의 게시글을 쓸수 있다.
    //ManyToOne은 defult로 FetchType.EAGER 이다: Board를 조회할 때 바로 가져온다. (한건만 있으니깐...)
    @ManyToOne(fetch = FetchType.EAGER)  //ManyToOne일때 기본이 EAGER이다
    @JoinColumn(name="userId")
    private User user;//DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.

    //OneToMany은 defult로 FetchType.LAZY 이다. 여러건 이므로
    // mappedBy 연관관계의 주인이 아니다 (난 FK가 아니다) DB에 칼럼을 만들지 않는다.
    //@OneToMany(mappedBy = "board", fetch = FetchType.LAZY) //Reply에 있는 Board id명을 적어준다.
    //★cascade = CascadeType.REMOVE : 게시글을 지울때 reply정보도 함께 지우겠다는 뜻 .. 72강
    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) //Reply에 있는 Board id명을 적어준다.
    @JsonIgnoreProperties({"board"}) // ★★★ 67강(15분) Board 오브젝트를 부를때, 그 안에 있는 Reply 오브젝트가 또 Board 오브젝트를 참고하기 때문에 무한참조(반복)되므로 처리!!  다른 방법도 있음
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp //시작을 자동 입력
    private Timestamp createDate;

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", count=" + count +
                ", user=" + user +
                ", replys=" + replys +
                ", createDate=" + createDate +
                '}';
    }
}
