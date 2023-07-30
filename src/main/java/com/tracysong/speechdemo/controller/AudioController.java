package com.tracysong.speechdemo.controller;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.speech.v1p1beta1.*;
import com.google.protobuf.ByteString;
import com.tracysong.speechdemo.entity.RedirectResponse;
import com.tracysong.speechdemo.service.KeywordExtractionService;
import com.tracysong.speechdemo.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import reactor.util.function.Tuple2;

import com.google.cloud.speech.v1p1beta1.SpeechSettings;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

@CrossOrigin(origins = "http://localhost:8081")
@Api(tags = "audio")
@RequestMapping("/audio")
@RestController
public class AudioController {

    @Autowired
    KeywordExtractionService keywordExtractionService;

    @Autowired
    RedisService redisService;

    @ApiOperation(value = "speech-to-text")
    @PostMapping("/recognize")
    public Mono<RedirectResponse> recognize(@RequestParam("file") MultipartFile file) {
        GoogleCredentials credentials;
        try (FileInputStream serviceAccountStream = new FileInputStream("/Users/tracywater/credentials.json")) {
            credentials = ServiceAccountCredentials.fromStream(serviceAccountStream)
                    .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            byte[] data = file.getBytes();

            SpeechSettings settings = SpeechSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            try (SpeechClient speechClient = SpeechClient.create(settings)) {
                RecognitionConfig config = RecognitionConfig.newBuilder()
                        .setEncoding(RecognitionConfig.AudioEncoding.WEBM_OPUS)
                        .setSampleRateHertz(48000)
                        .setLanguageCode("en-UK")
                        .build();

                RecognitionAudio audio = RecognitionAudio.newBuilder()
                        .setContent(ByteString.copyFrom(data))
                        .build();

                RecognizeResponse response = speechClient.recognize(config, audio);
                List<SpeechRecognitionResult> results = response.getResultsList();

                RedirectResponse rs = new RedirectResponse();

                return Flux.fromIterable(results)
                        .flatMap(result -> {
                            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                            rs.setTranscript(alternative.getTranscript());
                            try {
                                return extractedKeywords(alternative.getTranscript())
                                        .map(keyword -> Tuples.of(keyword, alternative.getTranscript()));
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .collectList()  // 收集所有的关键词到一个列表
                        .map(list -> {
                            List<String> keywords = list.stream()
                                    .map(Tuple2::getT1)
                                    .collect(Collectors.toList());
                            rs.setRedirect(redirectQuery(keywords));
                            rs.setPlacesSearchResponse(keywords);
                            return rs;
                        });
                    }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int redirectQuery(List<String> keywords) {
        System.out.println(keywords);
        if (keywords.contains("restaurant") && keywords.contains("nearby")) {
            return 1;
        }
        if(keywords.contains("hotel") || keywords.contains("lodging") || keywords.contains("accommodation")){
            return 2;
        }
        if(keywords.contains("attraction") ||keywords.contains("amusement park")){
            return 3;
        }
        if(keywords.contains("park") && keywords.contains("nearby")) return 4;
        if((keywords.contains("club")&& keywords.contains("nearby")) || (keywords.contains("pub") && keywords.contains("nearby"))) return 5;
        if((keywords.contains("station") || keywords.contains("bus") || keywords.contains("train") || keywords.contains("underground") || keywords.contains("subway"))) return 6;
        return 0;
    }


    private Flux<String> extractedKeywords(String transcript) throws IOException {
        //keywordExtractionService part
        AnalyzeEntitiesResponse response1 = keywordExtractionService.KeywordExtraction(transcript);
        // 提取出文本中的关键词
        Set<String> extractedKeywords = new HashSet<>();
        for (Entity entity : response1.getEntitiesList()) {
            extractedKeywords.add(entity.getName());
        }

        //redis part
        Flux<String> redisKeywords = redisService.getSortedSet("keywords");
        Mono<List<String>> redisKeywordsList = redisKeywords.collectList();
        return redisKeywordsList.flatMapIterable(list -> {
            List<String> matchedKeywords = list.stream()
                    .filter(keyword -> transcript.contains(keyword))
                    .collect(Collectors.toList());
            return matchedKeywords;
        });
    }
}

