package edu.upenn.eCommerceCrawler;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan("edu.upenn.eCommerceCrawler.web")
public class WebConfig extends WebMvcConfigurerAdapter {

}
