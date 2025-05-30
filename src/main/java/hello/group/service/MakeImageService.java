package hello.group.service;

import hello.group.entity.Banner;
import hello.group.entity.User;
import hello.group.repository.AdRepository;
import hello.group.repository.UserInfoRepo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import java.nio.file.Files;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@Service
public class MakeImageService {

    @Autowired
    private UserInfoRepo userInfo;

    @Autowired
    private AdRepository adRepo;

    public Map<String, String> getImage(String replaceText, String userId, String text, int w, int h) {
        String wi = Integer.toString(w);
        String he = Integer.toString(h);


        String url = "http://127.0.0.1:8000/tospring" + "?" + "prompt=" + replaceText + "&" + "width=" + wi + "&" + "height=" +he;
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
                    images.add(sp[i].replaceAll("\\[", ""));
                } else {
                    seeds.add(sp[i].replaceAll("]", ""));
                }
            }


            int k = 0;

            for (String part : images) {
                byte[] bytes = Base64.decodeBase64(part);
                // 디코딩된 문자열에 대한 추가 처리 수행

                FileOutputStream fileOutputStream;
                File file = new File("C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\result\\" + saveTime + k + ".png");
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.close();

                String resultPath3 = "C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\result\\" + saveTime + k + ".png";

                Path path = Paths.get(resultPath3);

                // 파일이 존재하는지 확인
                boolean exists = Files.exists(path);

                if(exists == true) {
                    savedFileNames.add("\\result\\" +saveTime + k + ".png");

                }
                k += 1;
            }

            for (int r = 0; r < savedFileNames.size(); r++) {
                li.put(seeds.get(r), savedFileNames.get(r));
            }

        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return li;
    }


    @Transactional
    public String saveImg(String userId, String image, String prompt, String seed, String width, String height){

        String filePath = null;
        boolean a = true;
        String resultPath = "C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\resultbanner\\" + image;
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

        Banner b = new Banner();
        b.setUserNum(user);
        b.setPrompt(prompt);
        b.setImagePath(resultPath2);
        b.setSeed(seed);
        b.setWidth(width);
        b.setHeight(height);
        adRepo.save(b);

        return resultPath2;
    }


    public Map<String, String> recycleImg(String prompt, String seed, String w, String h) {

        String url = "http://127.0.0.1:8000/tospring2" + "?" + "prompt=" + prompt + "&" + "seed=" + seed + "&" + "width=" + w + "&" + "height=" +h;
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
                    images.add(sp[i].replaceAll("\\[", ""));
                } else {
                    seeds.add(sp[i].replaceAll("]", ""));
                }
            }


            int k = 0;

            for (String part : images) {
                byte[] bytes = Base64.decodeBase64(part);
                // 디코딩된 문자열에 대한 추가 처리 수행

                FileOutputStream fileOutputStream;
                File file = new File("C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\result\\" + saveTime + k + ".png");
                file.createNewFile();
                fileOutputStream = new FileOutputStream(file);
                fileOutputStream.write(bytes);
                fileOutputStream.close();

                String resultPath3 = "C:\\git\\group\\src\\main\\resources\\templates\\make-banner-react-template\\public\\result\\" + saveTime + k + ".png";

                Path path = Paths.get(resultPath3);

                // 파일이 존재하는지 확인
                boolean exists = Files.exists(path);

                if(exists == true) {
                    savedFileNames.add("\\result\\" +saveTime + k + ".png");

                }
                k += 1;
            }

            for (int r = 0; r < savedFileNames.size(); r++) {
                li.put(seeds.get(r), savedFileNames.get(r));
            }

        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return li;
    }

    public Map<String, String> getText(String subject) {

        String url = "http://127.0.0.1:8000/tospring3" + "?" + "subject=" + subject;
        String sb = "";

        Map<String, String> li = new LinkedHashMap<>();

        // 3000/makebanner
        try {

            //url 커넥션을 활용하여 데이터 주고 받기
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

            String line = null;

            while ((line = br.readLine()) != null) {
                sb = sb + line;
            }

            if (sb.toString().contains("ok")) {
                System.out.println("test");
            }

            br.close();

            li.put("recommendation", sb);

        }catch(MalformedURLException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        return li;
    }


}
