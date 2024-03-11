package hello.group.service;

import hello.group.dto.AdDto;
import hello.group.dto.HelloUserinfo;
import hello.group.entity.Ad;
import hello.group.entity.User;
import hello.group.repository.AdRepository;
import hello.group.repository.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class UserInfoService {

    @Autowired
    private UserInfo userInfo;

    @Autowired
    private AdRepository adRepo;

    @Transactional
    public User save(HelloUserinfo helloUserinfo){
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
        userInfo.save(entity);

        return entity;
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

//    public User findByIdForImageSave(String userid) {
//        // 이미지 저장시 유저정보 필요
//        Optional<User> byId = userInfo.findById(userid);
//        System.out.println(byId);
//        User user = byId.get();
//
//        return user;
//    }
//
//    @Transactional
//    public void getImage(String replaceText, String userId, String text) {
//
//
//        String url = "http://127.0.0.1:8000/tospring" + "?" + "q=" + replaceText;
//        String sb = "";
//
//
//        try {
//            //url 커넥션을 활용하여 데이터 주고 받기
//            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
//            conn.setRequestProperty("Content-type", "application/json");
//            conn.setDoOutput(true);
//
//            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//
//            // 받아온 인코딩으로 된 이미지 String형태로 저장
//            String line = null;
//
//            while ((line = br.readLine()) != null) {
//                sb = sb + line;
//            }
//
//            if (sb.toString().contains("ok")) {
//                System.out.println("test");
//
//            }
//
//            // 인코딩 -> 디코딩 base64사용
//            byte[] bytes = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(sb.split(",", 1)[0]);
//
//            //저장시 현재 시간 userId + 현재시간.png로 저장
//            LocalDateTime now = LocalDateTime.now();
//            String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            String saveTime = format.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
//
//            //받아온 이미지 파일 생성 및 저장
//            FileOutputStream fileOutputStream;
//            File file = new File("C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\src\\static\\images\\" + userId + "__" + saveTime + ".png");
//            file.createNewFile();
//            fileOutputStream = new FileOutputStream(file);
//            fileOutputStream.write(bytes);
//            fileOutputStream.close();
//            String adToString = file.toString();
//
//            System.out.println(file);
//            // user정보를 알아야 하기 때문에 db에서 가져오기
//            User byId2 = findByIdForImageSave(userId);
//
//            // 생성된 이미지를 db에 저장
//            AdDto dto = new AdDto();
//            dto.setCreateAd(adToString);
//            dto.setPrompt(text);
//            Ad dtoEntity = dto.toEntity();
//            dtoEntity.setUserId(byId2);
//            adRepo.save(dtoEntity);
//            br.close();
//
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
}
