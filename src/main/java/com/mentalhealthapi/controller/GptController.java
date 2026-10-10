package com.mentalhealthapi.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/gpt")
public class GptController {

    private final RestTemplate restTemplate;
    private final String gpt3ApiKey;  // Replace with your actual API key

    public GptController() {
        this.restTemplate = new  RestTemplate();
        gpt3ApiKey = System.getenv("OPENAI_API_KEY");
    }

    @PostMapping("/gpt3/completions")
    public ResponseEntity<String> getGpt3Completions(@RequestBody String prompt) {
        // Set the OpenAI GPT-3 API endpoint URL
        String url = "https://api.openai.com/v1/engines/davinci-codex/completions";

        // Set the headers, including the authorization header with your API key
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + gpt3ApiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request body
        String requestBody = "{\"prompt\": \"" + prompt + "\", \"max_tokens\": 100}";

        // Make a POST request to the OpenAI GPT-3 API endpoint
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

        // Handle the response from the OpenAI GPT-3 API
        if (response.getStatusCode() == HttpStatus.OK) {
            String gpt3Response = response.getBody();
            return ResponseEntity.ok(gpt3Response);
        } else {
            // Handle errors
            return ResponseEntity.status(response.getStatusCode()).body("Error occurred");
        }
    }
}
