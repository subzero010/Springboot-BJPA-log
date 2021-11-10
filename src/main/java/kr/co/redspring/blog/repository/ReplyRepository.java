package kr.co.redspring.blog.repository;

import kr.co.redspring.blog.dto.ReplySaveRequestDto;
import kr.co.redspring.blog.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface ReplyRepository extends JpaRepository<Reply, Integer> {

    @Modifying
    @Query(value="INSERT INTO reply(userId, boardId, content, createDate) VALUES (?1, ?2, ?3, now())", nativeQuery = true)
    public int mSave(int userId, int boardId, String content); //70강   업데이트된 행의 갯수를 리턴

}
