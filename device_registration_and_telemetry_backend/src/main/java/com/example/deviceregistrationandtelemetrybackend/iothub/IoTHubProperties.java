package com.example.deviceregistrationandtelemetrybackend.iothub;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for Azure IoT Hub integration.
 * <p>
 * Uses environment variables via Spring's relaxed binding:
 * <ul>
 *   <li>AZURE_IOT_HUB_HOSTNAME</li>
 *   <li>AZURE_IOT_HUB_SAS_TOKEN</li>
 *   <li>AZURE_IOT_HUB_API_VERSION (optional)</li>
 * </ul>
 */
@ConfigurationProperties(prefix = "azure.iothub")
public class IoTHubProperties {

    /**
     * IoT Hub host name, e.g. "myhub.azure-devices.net".
     */
    private String hostname;

    /**
     * SAS token to authenticate service API calls (SharedAccessSignature ...).
     * <p>
     * This should be treated as a secret and provided via environment variables.
     */
    private String sasToken;

    /**
     * IoT Hub REST API version.
     */
    private String apiVersion = "2021-04-12";

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getSasToken() {
        return sasToken;
    }

    public void setSasToken(String sasToken) {
        this.sasToken = sasToken;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * Returns true if the required credentials appear present.
     *
     * @return true when hostname and SAS token are set
     */
    public boolean isConfigured() {
        return hostname != null && !hostname.isBlank()
                && sasToken != null && !sasToken.isBlank();
    }
}
