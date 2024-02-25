package edu.java;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class WireMockConfig {
    private WireMockServer wireMockServer;

    public void startWireMockServer() {
        wireMockServer = new WireMockServer(WireMockConfiguration.options().port(8081));
        wireMockServer.start();
    }

    public void stopWireMockServer() {
        wireMockServer.stop();
    }
}
