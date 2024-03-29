package hello.group.service;
import hello.group.dto.AdList;
import hello.group.dto.UserInfo;
import hello.group.entity.Ad;
import hello.group.entity.User;
import hello.group.repository.AdRepository;
import hello.group.repository.UserInfoRepo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
public class UserInfoService {

    @Autowired
    private UserInfoRepo userInfoRepo;

    @Autowired
    private AdRepository adRepo;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public UserInfo save(UserInfo userInfo){
        //true면 저장
        User entity = userInfo.toEntity();
        if (entity.getUserId() == null) { //id값이 없으면 영속성 컨텍스트로 저장
            em.persist(entity);
        } else { //아니면 준영속
            em.merge(entity);
        }
        UserInfo dto = entity.toDto();

        return dto;
    }

    public boolean getAllList(UserInfo userInfo){
        Optional<User> a = userInfoRepo.findById(userInfo.getUserId());
        System.out.println(a);
        return a.isEmpty();
    }

    public UserInfo findById(String userId, String userPassword) {
        //db에서 가져온 데이터와 아이디 비번 일치 확인
        Optional<User> byId = userInfoRepo.findById(userId);
        System.out.println(byId);
        User user = byId.filter(u -> u.getUserPassword().equals(userPassword)).orElse(null);
        // 일치 하지 않다면 null
        if(user==null){ // 반환된 값이 null이라면 null로 반환
            return null;
        }
        //일치하면 user정보 보내기
        System.out.println(user);
        UserInfo dto = user.toDto();

        return dto;

    }


    @Transactional
    public Map<String, List<String>> findByImg(String userId) {
        // 이미지 저장시 유저정보 필요
        Optional<User> byId = userInfoRepo.findById(userId);
        User user = byId.get();
        Long Num = user.getNum();
        List<AdList> id = em.createQuery("select new hello.group.dto.AdList(s.prompt, s.imagePath) from Ad s join s.userNum t where t.num=:id", AdList.class)
                .setParameter("id", Num)
                .getResultList();
        System.out.println(id);

        Map<String, List<String>> resultMap = new LinkedHashMap<>();

        for (AdList ad : id) {
            String field1 = ad.getField1();
            String field2 = ad.getField2();

            if (!resultMap.containsKey(field1)) {
                resultMap.put(field1, new ArrayList<>());
            }
            resultMap.get(field1).add(field2);

        }


        System.out.println(resultMap);


        return resultMap;
    }

    @Transactional
    public void updateUserInfo(UserInfo userInfo){
        Optional<User> byId = userInfoRepo.findById(userInfo.getUserId());
        System.out.println("========" + byId);
        User user = byId.get();
        user.setUserId(userInfo.getUserId());
        user.setUserName(userInfo.getUserName());
        user.setUserPassword(userInfo.getUserPassword());
        user.setBusinessNumber(userInfo.getBusinessNumber());

    }

    @Transactional
    public UserInfo findByUserInfo(String userId){
        Optional<User> byId = userInfoRepo.findById(userId);
        User user = byId.get();
        UserInfo dto = user.toDto();
        return dto;
    }

    @Transactional
    public void deleteUserInfo(String userId){
        Optional<User> byId = userInfoRepo.findById(userId);
        User user = byId.get();
        userInfoRepo.delete(user);
        em.flush();
    }

    @Transactional
    public void deleteUserBanner(String imgPath){

        List<Ad> all = adRepo.findAll();
        Optional<Ad> deleteInfo = all.stream().filter(s -> s.getImagePath().equals(imgPath)).findFirst();
        Ad ad = deleteInfo.get();
        adRepo.delete(ad);
        em.flush();
    }

}
