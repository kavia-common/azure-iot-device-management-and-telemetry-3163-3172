package com.example.deviceregistrationandtelemetrybackend.iothub;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Spring configuration for selecting IoT Hub integration mode:
 * <ul>
 *   <li>Azure-backed when azure.iothub.hostname + azure.iothub.sas-token are present</li>
 *   <li>Local stub otherwise</li>
 * </ul>
 */
@Configuration
@EnableConfigurationProperties(IoTHubProperties.class)
public class IoTHubConfiguration {

    @Bean
    public IoTHubClient ioTHubClient(IoTHubProperties properties) {
        if (properties.isConfigured()) {
            WebClient client = AzureIoTHubClient.buildWebClient(properties);
            return new AzureIoTHubClient(properties, client);
        }
        return new LocalStubIoTHubClient();
    }
}
