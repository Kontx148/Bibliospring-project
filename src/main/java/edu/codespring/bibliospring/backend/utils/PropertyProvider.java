package edu.codespring.bibliospring.backend.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.PropertyResolver;

@Configuration
@PropertySource("classpath:/bibliospring.properties")
public class PropertyProvider {
    @Autowired
    PropertyResolver resolver;
    public String getProperty(String key)
    {
        return resolver.getProperty(key);
    }

}
