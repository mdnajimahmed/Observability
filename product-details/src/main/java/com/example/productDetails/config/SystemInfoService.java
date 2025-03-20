package com.example.productDetails.config;

import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
public class SystemInfoService {

    public String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "Unknown Host";
        }
    }

    public String getProcessId() {
        return ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
    }

    public String getSystemInfo() {
        String instanceId = System.getenv("INSTANCE_ID");
        if (instanceId == null || instanceId.isEmpty()) {
            return String.format("host[%s]-pid[%s]", getHostname(), getProcessId());
        }
        return instanceId;
    }
}