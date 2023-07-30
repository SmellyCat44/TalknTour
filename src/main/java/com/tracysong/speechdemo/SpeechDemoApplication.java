package com.tracysong.speechdemo;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.tracysong.speechdemo.service.RedisService;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

@Service
@SpringBootApplication
public class SpeechDemoApplication {
    private static byte[] readFileContent(String filePath) {
        try (FileInputStream fileInputStream = new FileInputStream(filePath)) {
            return fileInputStream.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String[] args){

//        GoogleCredentials credentials;
//
//        try (FileInputStream serviceAccountStream = new FileInputStream("/Users/tracywater/IdeaProjects/speech-demo/src/main/resources/credentials.json")) {
//            credentials = ServiceAccountCredentials.fromStream(serviceAccountStream)
//                    .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
//
//        BlobId blobId = BlobId.of("speech-demo-tracysong", "/Users/tracywater/IdeaProjects/speech-demo/src/main/resources/audio.wav");
//        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
//
//        // 执行文件上传
//        byte[] fileContent = readFileContent("/Users/tracywater/IdeaProjects/speech-demo/src/main/resources/audio.wav");
//        Blob blob = storage.create(blobInfo, fileContent);
//
//        System.out.println("File uploaded successfully: " + blob.getName());
        SpringApplication.run(SpeechDemoApplication.class, args);
    }
}
