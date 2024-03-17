package hello.group.service;

import hello.group.dto.AdDto;
import hello.group.entity.Ad;
import hello.group.entity.User;
import hello.group.repository.AdRepository;
import hello.group.repository.UserInfo;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MakeImageService {

    @Autowired
    private UserInfo userInfo;

    @Autowired
    private AdRepository adRepo;

    @Autowired
    private EntityManager em;


//    public User findByIdForImageSave(String userid) {
//        // 이미지 저장시 유저정보 필요
//        Optional<User> byId = userInfo.findById(userid);
//        System.out.println(byId);
//        User user = byId.get();
//
//        return user;
//    }

    @Transactional
    public String getImage(String replaceText, String userId, String text,int w, int h) {


        String url = "http://127.0.0.1:8000/tospring" + "?" + "q=" + replaceText;
        String sb = "";

        List<String> li = new ArrayList<>();

        // 3000/makebanner
        try {
            //url 커넥션을 활용하여 데이터 주고 받기
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestProperty("Content-type", "application/json");
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            // 받아온 인코딩으로 된 이미지 String형태로 저장
            String line = null;

            while ((line = br.readLine()) != null) {
                sb = sb + line;
            }

            if (sb.toString().contains("ok")) {
                System.out.println("test");

            }



            // 인코딩 -> 디코딩 base64사용
            byte[] bytes = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(sb.split(",", 1)[0]);
            //저장시 현재 시간 userId + 현재시간.png로 저장
            LocalDateTime now = LocalDateTime.now();
            String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String saveTime = format.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");

            //받아온 이미지 파일 생성 및 저장
            FileOutputStream fileOutputStream;
            File file = new File("C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\src\\static\\images\\" + userId + "__" + saveTime + ".png");
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.close();
            String adToString = file.toString();

            Image img = ImageIO.read(file);
            Image resizeImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);

            BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.getGraphics();
            g.drawImage(resizeImg, 0, 0, null);
            g.dispose();
            boolean png = ImageIO.write(newImage, "png", new File("C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\src\\public\\media\\" + userId + saveTime + ".png"));

            if(png == true){
                li.add(userId + saveTime + ".png");
                System.out.println(file);
            }

            // user정보를 알아야 하기 때문에 db에서 가져오기
//            Optional<User> byId = userInfo.findById(userId);
//            User user = byId.get();
//
//            // 생성된 이미지를 db에 저장
//            AdDto dto = new AdDto();
//            dto.setCreateAd(adToString);
//            dto.setPrompt(text);
//            Ad dtoEntity = dto.toEntity();
//            dtoEntity.setUserId(user);
//            adRepo.save(dtoEntity);
            br.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s = li.get(0);
        return s;


    }

    @Transactional
    public String saveImg(String userId, String image, String prompt){

        String filePath = null;

        boolean a = true;
        String resultPath = "C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\result\\" + image;
        String resultPath2 = "\\result\\" + image;
        while (a){
            try {

                File file = new File("C:\\Users\\OWNER\\Downloads\\" + image);
                File newFile = new File(resultPath);
                Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                boolean exists = newFile.exists();
                if (exists == false){
                    wait(10L);
                }else {
                    a = false;
                    filePath = newFile.toString();
                }



            }catch (InterruptedException e){

            } catch (NoSuchFileException newFile){

            }catch (IOException e){
                e.printStackTrace();
            }
        }

        Optional<User> byId = userInfo.findById(userId);
        User user = byId.get();

        User user1 = new User();
        user1.setNum(user.getNum());
        user1.setUserid(user.getUserid());
        user1.setUsername(user.getUsername());
        user1.setUserCRN(user.getUserCRN());
        user1.setUserpassword(user.getUserpassword());

        Ad b = new Ad();
        b.setUserNum(user);
        b.setPrompt(prompt);
        b.setCreateAd(resultPath2);
        adRepo.save(b);

        return resultPath2;
    }


}
