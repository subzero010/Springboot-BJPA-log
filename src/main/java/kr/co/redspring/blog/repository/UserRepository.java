package kr.co.redspring.blog.repository;

import kr.co.redspring.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// DAO
// 자동으로 bean등록이 된다
//@Repository  //생각 가능
public interface UserRepository extends JpaRepository<User, Integer> {

    // select * from user where username = 1?
    Optional<User> findByUsername(String username);


    /* spring security 사용으로 인해 주석처리
    // JAP Naming 쿼리
    // select * from user where username=? and password=?; 가 동작함
    User findByUsernameAndPassword(String username, String password);
    */


    //@Query(value="select * from user where username=? and password=?", nativeQuery=true)
    //User login(String username, String password);

}
