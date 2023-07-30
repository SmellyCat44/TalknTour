package com.tracysong.speechdemo.service;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.language.v1.*;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class KeywordExtractionService {

    public AnalyzeEntitiesResponse KeywordExtraction(String text) throws IOException {
        GoogleCredentials credentials = GoogleCredentials.fromStream(new FileInputStream("/Users/tracywater/credentials.json"));
        LanguageServiceSettings settings = LanguageServiceSettings.newBuilder()
                .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                .build();

        // 使用上面的设置来创建 Google Cloud Natural Language API 客户端
        try (LanguageServiceClient languageService = LanguageServiceClient.create(settings)) {
            Document doc = Document.newBuilder().setContent(text).setType(Document.Type.PLAIN_TEXT).build();
            AnalyzeEntitiesRequest request = AnalyzeEntitiesRequest.newBuilder()
                    .setDocument(doc)
                    .setEncodingType(EncodingType.UTF16)
                    .build();
            AnalyzeEntitiesResponse response = languageService.analyzeEntities(request);

            // 提取出文本中的关键词
            Set<String> extractedKeywords = new HashSet<>();
            for (Entity entity : response.getEntitiesList()) {
                extractedKeywords.add(entity.getName());
            }
            return response;
        }
    }

}