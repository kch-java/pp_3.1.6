import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Consumer {

    private static final String URL = "http://94.198.50.185:7081/api/users";
    private static final String DELETE_URL = "http://94.198.50.185:7081/api/users/3";
    private static final RestTemplate restTemplate = new RestTemplate();
    private static final HttpHeaders headers = new HttpHeaders();

    public static void main(String[] args) {

        Consumer consumer = new Consumer();

        headers.set("Cookie", getHeaders());

        consumer.createUser();
        consumer.updateUser();
        consumer.deleteUser();
    }

    private static String getHeaders() {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(URL, String.class);
        return String.join(";", Objects.requireNonNull(forEntity.getHeaders().get("Set-Cookie")));
    }

    private void createUser() {

        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("id", "3");
        jsonToSend.put("name", "James");
        jsonToSend.put("lastName", "Brown");
        jsonToSend.put("age", "21");

        HttpEntity<Map<String, String>> newUser = new HttpEntity<>(jsonToSend, Consumer.headers);

        ResponseEntity<String> result = restTemplate.postForEntity(URL, newUser, String.class);

        System.out.println(result.getBody());
    }

    private void updateUser() {

        Map<String, String> jsonToSend = new HashMap<>();
        jsonToSend.put("id", "3");
        jsonToSend.put("name", "Thomas");
        jsonToSend.put("lastName", "Shelby");
        jsonToSend.put("age", "21");

        HttpEntity<Map<String, String>> updateUser = new HttpEntity<>(jsonToSend, Consumer.headers);

        ResponseEntity<String> result = restTemplate.exchange(URL, HttpMethod.PUT, updateUser, String.class);

        System.out.println(result.getBody());
    }

    private void deleteUser() {

        HttpEntity<Map<String, String>> entity = new HttpEntity<>(Consumer.headers);

        ResponseEntity<String> result = restTemplate.exchange(DELETE_URL, HttpMethod.DELETE, entity, String.class);

        System.out.println(result.getBody());
    }
}
