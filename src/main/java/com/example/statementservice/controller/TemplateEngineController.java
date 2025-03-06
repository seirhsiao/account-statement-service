package com.example.statementservice.controller;

import com.example.statementservice.dto.GeneratePdfRequest;
import com.example.statementservice.dto.GeneratePdfResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RestController
public class TemplateEngineController {
    @PostMapping("/api/v1/pdf")
    public GeneratePdfResponse generatePdf(@RequestBody GeneratePdfRequest request) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(request);
            byte[] bytes = json.getBytes(StandardCharsets.UTF_8);
            return new GeneratePdfResponse(Base64.getEncoder().encodeToString(bytes));
        } catch (JsonProcessingException e) {
            return new GeneratePdfResponse(
                    "JVBERi0xLjUKJYCBgoMKMSAwIG9iago8PC9GaWx0ZXIvRmxhdGVEZWNvZGUvRmlyc3QgMTQxL04gMjAvTGVuZ3==");
        }
    }
}