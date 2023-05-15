package wearwell.com.eCommerceAPI.webApi;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000","https://wearwell-shopping.vercel.app") // your reactjs URL
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE")
                .allowedHeaders("Content-Type","Authorization","Origin", "X-Api-Key", "X-Requested-With" ,"Accept") // Adjust headers you need to allow
                .allowCredentials(true); // Add only if you want to access cookie
    }

}