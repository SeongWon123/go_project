package hello.group.service;

import hello.group.dto.AdDto;
import hello.group.entity.Ad;
import hello.group.entity.User;
import hello.group.repository.AdRepository;
import hello.group.repository.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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

    public User findByIdForImageSave(String userid) {
        // 이미지 저장시 유저정보 필요
        Optional<User> byId = userInfo.findById(userid);
        System.out.println(byId);
        User user = byId.get();

        return user;
    }

    @Transactional
    public void getImage(String replaceText, String userId, String text) {


        String url = "http://127.0.0.1:8000/tospring" + "?" + "q=" + replaceText;
        String sb = "";

        List<BufferedImage> li = new ArrayList<>();


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
            Image resizeImg = img.getScaledInstance(1024, 1024, Image.SCALE_SMOOTH);

            BufferedImage newImage = new BufferedImage(1024, 1024, BufferedImage.TYPE_INT_RGB);
            Graphics g = newImage.getGraphics();
            g.drawImage(resizeImg, 0, 0, null);
            g.dispose();
            ImageIO.write(newImage, "png", new File("C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\src\\static\\resizeImg\\" + userId + "__" + saveTime+ ".png"));

            System.out.println(file);
            // user정보를 알아야 하기 때문에 db에서 가져오기
            User byId2 = findByIdForImageSave(userId);

            // 생성된 이미지를 db에 저장
            AdDto dto = new AdDto();
            dto.setCreateAd(adToString);
            dto.setPrompt(text);
            Ad dtoEntity = dto.toEntity();
            dtoEntity.setUserId(byId2);
            adRepo.save(dtoEntity);
            br.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
