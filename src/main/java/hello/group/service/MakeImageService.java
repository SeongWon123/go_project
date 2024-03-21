package hello.group.service;

import hello.group.entity.Ad;
import hello.group.entity.User;
import hello.group.repository.AdRepository;
import hello.group.repository.UserInfo;
import jakarta.persistence.EntityManager;
import org.apache.tomcat.util.codec.binary.Base64;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import java.nio.file.Files;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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
    public Map<String, String> getImage(String replaceText, String userId, String text,int w, int h) {


        String url = "http://127.0.0.1:8000/tospring" + "?" + "q=" + replaceText;
        String sb = "";

        Map<String, String> li = new LinkedHashMap<>();
        List<String> savedFileNames = new ArrayList<>();
        List<String> images = new ArrayList<>();
        List<String> seeds = new ArrayList<>();

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

            br.close();

            LocalDateTime now = LocalDateTime.now();
            String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            String saveTime = format.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");

            String[] sp = sb.split(",");

            for (int i = 0; i < sp.length; i++) {
                if (i % 2 == 0) { // 짝수 인덱스는 0을 기준으로 시작하므로 홀수 번째를 나타냄
                    images.add(sp[i].replaceAll("\\[",""));
                } else {
                    seeds.add(sp[i].replaceAll("]",""));
                }
            }
            int k = 0;

            for (String part : images) {
                byte[] bytes = Base64.decodeBase64(part);
                // 디코딩된 문자열에 대한 추가 처리 수행

//                BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(bytes));
//                BufferedImage resizedImage2 = Scalr.resize(originalImage, Scalr.Method.SPEED, Scalr.Mode.FIT_EXACT, 100, 100);
//                String resizedImagePath2 = "C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\result\\" + saveTime + k + ".jpg";
//                File resizedFile2 = new File(resizedImagePath2);
//                ImageIO.write(resizedImage2, "jpg", resizedFile2);

                FileOutputStream fileOutputStream;
                File file = new File("C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\result\\" + saveTime + k + ".jpg");
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.close();

//                Image img = ImageIO.read(file);
//                Image resizeImg = img.getScaledInstance(w, h, Image.SCALE_FAST);
//
//                BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//                Graphics g = newImage.getGraphics();
//                g.drawImage(resizeImg, 0, 0, null);
//                g.dispose();
//                File file1 = new File("C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\result\\" + saveTime + k + ".jpg");
//                ImageIO.write(newImage, "jpg", file1);

//                BufferedImage originalImage = ImageIO.read(file);

                // Resize the image
//                BufferedImage resizedImage = Scalr.resize(originalImage, Scalr.Method.SPEED, Scalr.Mode.FIT_EXACT, w, h);
//
//                // Save the resized image
//                String resizedImagePath = "C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\hello\\" + saveTime + k + ".jpg";
//                File resizedFile = new File(resizedImagePath);
//                ImageIO.write(resizedImage, "jpg", resizedFile);


//                Image img1 = ImageIO.read(file1);
//                Image resizeImg1 = img1.getScaledInstance(100, 100, Image.SCALE_FAST);
//
//                BufferedImage newImage1 = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
//                Graphics g1 = newImage.getGraphics();
//                g1.drawImage(resizeImg1, 0, 0, null);
//                g1.dispose();
//                ImageIO.write(newImage1, "jpg", new File("C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\hello\\" + saveTime + k + ".jpg"));



                String resultPath3 = "C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\result\\" + saveTime + k + ".jpg";

                Path path = Paths.get(resultPath3);

                // 파일이 존재하는지 확인
                boolean exists = Files.exists(path);

                if(exists == true){
                    savedFileNames.add(saveTime + k + ".jpg");
                }
                k+=1;
            }

            for (int r =0;  r < savedFileNames.size(); r++ ) {
                li.put(seeds.get(r), "\\result\\" + savedFileNames.get(r));
            }

            // 인코딩 -> 디코딩 base64사용
//            byte[] bytes = org.apache.tomcat.util.codec.binary.Base64.decodeBase64(sb.split(",", 1)[0]);
//            //저장시 현재 시간 userId + 현재시간.png로 저장
////            LocalDateTime now = LocalDateTime.now();
////            String format = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
////            String saveTime = format.replaceAll(" ", "").replaceAll("-", "").replaceAll(":", "");
//
//            //받아온 이미지 파일 생성 및 저장
//            FileOutputStream fileOutputStream;
//            File file = new File("C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\src\\static\\images\\" + userId + "__" + saveTime + ".png");
//            file.createNewFile();
//            fileOutputStream = new FileOutputStream(file);
//            fileOutputStream.write(bytes);
//            fileOutputStream.close();
//            String adToString = file.toString();

//            Image img = ImageIO.read(file);
//            Image resizeImg = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
//
//            BufferedImage newImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
//            Graphics g = newImage.getGraphics();
//            g.drawImage(resizeImg, 0, 0, null);
//            g.dispose();
//            boolean png = ImageIO.write(newImage, "png", new File("C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\src\\public\\media\\" + userId + saveTime + ".png"));
//
//            if(png == true){
//                li.add(userId + saveTime + ".png");
//                System.out.println(file);
//            }

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
//            br.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        String s = li.get(0);
        return li;


    }

    @Transactional
    public String saveImg(String userId, String image, String prompt, String seed){

        String filePath = null;

        boolean a = true;
        String resultPath = "C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\resultbanner\\" + image;
        System.out.println(resultPath);
        String resultPath2 = "\\resultbanner\\" + image;
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
        b.setImagePath(resultPath2);
        b.setSeed(seed);
        adRepo.save(b);

        return resultPath2;
    }


}
