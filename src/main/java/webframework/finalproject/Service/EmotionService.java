package webframework.finalproject.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import webframework.finalproject.Model.Content;
import webframework.finalproject.Model.Post;

import java.util.Map;

@RestController
@Slf4j
public class EmotionService {
    @Autowired
    PostService postService;

    private String clientId = "kd9peerq63";
    private String clientSecret = "kIt19rB5YFihpiKdDVcOi4CX4SoMmULYrmLyIhZT";
    private String apiUrl = "https://naveropenapi.apigw.ntruss.com/sentiment-analysis/v1/analyze";


    WebClient webClient = WebClient.builder()
            .defaultHeader("X-NCP-APIGW-API-KEY-ID", clientId)
            .defaultHeader("X-NCP-APIGW-API-KEY", clientSecret)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    ObjectMapper mapper = new ObjectMapper();

    public int emotionCount(int id) throws JsonProcessingException {
        Post postById = postService.findById(id);
        String content = postById.getContent();

        Content requestData = new Content(content);

        String requestJsonContent = mapper.writeValueAsString(requestData);

        String block = webClient.post()
                .uri(apiUrl)
                .body(BodyInserters.fromValue(requestJsonContent))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String resultEmotion = responseToJson(block);

        if (resultEmotion.equals("negative")) {
            return 1;
        } else {
            return 0;
        }
    }

    public String responseToJson(String response) {
        try {
            Map<String, Object> result = mapper.readValue(response, new TypeReference<Map<String, Object>>() {
            });

            Map<String, String> document = (Map<String, String>) result.get("document");
            String sentiment = document.get("sentiment");

            return sentiment;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "error";
    }
}
