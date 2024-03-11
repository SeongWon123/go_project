package hello.group.repository;

import hello.group.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface UserInfo extends JpaRepository<User, Long> {
    // 파라미터로 받아온 userid로 db에서 같은 user정보 가져오기
    @Transactional
    @Query(value = "select u from User u where u.userid=:userid")
    Optional<User> findById(@Param(value = "userid") String userid);



}
