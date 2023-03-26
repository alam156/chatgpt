package com.example.chatgpt.service;

import com.example.chatgpt.config.ChatGptConfiguration;
import com.example.chatgpt.model.request.BotRequest;
import com.example.chatgpt.model.request.ChatGptRequest;
import com.example.chatgpt.model.response.ChatGptResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BotServiceImpl implements BotService {

    private static RestTemplate restTemplate = new RestTemplate();

    //    Build headers
    public HttpEntity<ChatGptRequest> buildHttpEntity(ChatGptRequest chatRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(ChatGptConfiguration.MEDIA_TYPE));
        headers.add(ChatGptConfiguration.AUTHORIZATION, ChatGptConfiguration.BEARER + ChatGptConfiguration.API_KEY);
        return new HttpEntity<>(chatRequest, headers);
    }

    //    Generate response
    public ChatGptResponse getResponse(HttpEntity<ChatGptRequest> chatRequestHttpEntity) {
        ResponseEntity<ChatGptResponse> responseEntity = restTemplate.postForEntity(
                ChatGptConfiguration.URL,
                chatRequestHttpEntity,
                ChatGptResponse.class);

        return responseEntity.getBody();
    }

    public ChatGptResponse askQuestion(BotRequest botRequest) {
        return this.getResponse(
                this.buildHttpEntity(
                        new ChatGptRequest(
                                ChatGptConfiguration.MODEL,
                                botRequest.getMessage(),
                                ChatGptConfiguration.TEMPERATURE,
                                ChatGptConfiguration.MAX_TOKEN,
                                ChatGptConfiguration.TOP_P)));
    }
}
