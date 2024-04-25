package guru.springframework.msscbreweryclient.web.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("http.client")
@Data
public class RestClientCustomizerConfig {
    private Integer socketTimeout = 5000;
    private Integer connectionRequestTimeout = 5000;
    private Integer maxTotalConnections = 50;
    private Integer defaultMaxTotalConnections = 5;
}