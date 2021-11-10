package kr.co.redspring.blog.repository;

import kr.co.redspring.blog.model.Board;
import kr.co.redspring.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// DAO
// 자동으로 bean등록이 된다
//@Repository  //생각 가능
public interface BoardRepository extends JpaRepository<Board, Integer> {



}
