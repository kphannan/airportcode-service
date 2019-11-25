
package com.airline.locationservice.config;

// import java.util.List;
// import java.util.Locale;

// import javax.annotation.PostConstruct;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
// import lombok.EqualsAndHashCode;

/**
 * This is the application configuration.
 *
 */
@ConfigurationProperties(prefix = "application")
@Component
@Data
public class ApplicationConfiguration
{

}