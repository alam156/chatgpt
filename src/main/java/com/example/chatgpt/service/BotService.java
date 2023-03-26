package com.example.chatgpt.service;

import com.example.chatgpt.model.request.BotRequest;
import com.example.chatgpt.model.response.ChatGptResponse;

public interface BotService {

    ChatGptResponse askQuestion(BotRequest botRequest);
}
