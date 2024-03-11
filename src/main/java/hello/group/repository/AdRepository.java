package hello.group.repository;

import hello.group.entity.Ad;
import hello.group.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Long> {

}
