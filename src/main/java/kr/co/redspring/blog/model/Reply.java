package kr.co.redspring.blog.model;

import kr.co.redspring.blog.dto.ReplySaveRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Reply {
    @Override
    public String toString() {//70강(16분)   우측마우스>Generation(Alt+Insert)>toSting 선택
        return "Reply{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", board=" + board +
                ", user=" + user +
                ", createDate=" + createDate +
                '}';
    }

    public void update(User user, Board board, String content){ //69강 (8분)
        setUser(user);
        setBoard(board);
        setContent(content);
    }


    @Id  //PK
    @GeneratedValue(strategy = GenerationType.IDENTITY)//auto_incrrement
    private int id;

    @Column(nullable = false, length = 200)
    private String content;

    @ManyToOne
    @JoinColumn(name="boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;
}
