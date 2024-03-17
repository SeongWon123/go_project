package hello.group.service;


import hello.group.dto.AdList;
import hello.group.dto.HelloUserinfo;

import hello.group.entity.User;
import hello.group.repository.AdRepository;
import hello.group.repository.UserInfo;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class UserInfoService {

    @Autowired
    private UserInfo userInfo;

    @Autowired
    private AdRepository adRepo;

    @Autowired
    private EntityManager em;

    @Transactional
    public HelloUserinfo save(HelloUserinfo helloUserinfo){
        // 똑같은 아이디가 있는지 확인
        Optional<User> byId = userInfo.findById(helloUserinfo.getUserid());
        System.out.println(byId);
        //있다면 false 없다면 true
        boolean empty = byId.isEmpty();
        System.out.println(empty);
        //false면 null 리턴
        if(empty == false){
            return null;
        }
        //true면 저장
        User entity = helloUserinfo.toEntity();
        System.out.println(entity);
        em.persist(entity);
        HelloUserinfo dto = entity.toDto();

        return dto;
    }

    public HelloUserinfo findById(String userid, String userpassword) {
        //db에서 가져온 데이터와 아이디 비번 일치 확인
        Optional<User> byId = userInfo.findById(userid);
        System.out.println(byId);
        User user = byId.filter(u -> u.getUserpassword().equals(userpassword)).orElse(null);
        // 일치 하지 않다면 null
        if(user==null){ // 반환된 값이 null이라면 null로 반환
            return null;
        }
        //일치하면 user정보 보내기
        System.out.println(user);
        HelloUserinfo dto = user.toDto();

        return dto;

    }


    @Transactional
    public Map<String, String> findById2(String userid) {
        // 이미지 저장시 유저정보 필요
        Optional<User> byId = userInfo.findById(userid);
        User user = byId.get();
        Long Num = user.getNum();
        List<AdList> id = em.createQuery("select new hello.group.dto.AdList(s.prompt, s.createAd) from Ad s join s.userNum t where t.num=:id", AdList.class)
                .setParameter("id", Num)
                .getResultList();

        Map<String, String> resultMap = new HashMap<>();

        for (AdList ad : id) {
            String field1 = ad.getField1();
            String field2 = ad.getField2();
            resultMap.put(field1, field2);
        }
        System.out.println(resultMap);


        return resultMap;
    }

}
