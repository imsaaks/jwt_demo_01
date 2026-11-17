package com.iung.dev.common.config;

import com.iung.dev.common.constants.AppConstants;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import java.util.TimeZone;

@Slf4j
@Configuration
public class TimeZoneConfig {
    @PostConstruct
    public void setupDefaultTimeZone() {
        log.info("* Setting default time zone to {}", AppConstants.DEFAULT_TIMEZONE_KO);
        TimeZone.setDefault(TimeZone.getTimeZone(AppConstants.DEFAULT_TIMEZONE_KO));
    }
}
