package com.example.mytttptestpro.utils;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

@Configuration
public class CanalConfig {
    private CanalConnector connectors = CanalConnectors
            .newSingleConnector(
                    new InetSocketAddress("127.0.0.1", 11111),
                    "example",
                    "",
                    "");
    @Bean
    public  CanalConnector connector() {
        return connectors;
    }
}
