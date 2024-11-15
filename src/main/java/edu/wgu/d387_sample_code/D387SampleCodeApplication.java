package edu.wgu.d387_sample_code;

import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@SpringBootApplication
public class D387SampleCodeApplication {
	static ExecutorService messageExecutor=newFixedThreadPool(5);
	public static List<String> messages = new ArrayList<>();
	public static void main(String[] args) {
		SpringApplication.run(D387SampleCodeApplication.class, args);
		messageExecutor.execute(()-> {
			try {
				Properties properties=new Properties();
				InputStream stream = new ClassPathResource("translation_en_US.properties").getInputStream();
				properties.load(stream);
				synchronized (messages) {
					messages.add(properties.getProperty("welcome"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		messageExecutor.execute(()-> {
			try {
				Properties properties=new Properties();
				InputStream stream = new ClassPathResource("translation_fr_CA.properties").getInputStream();
				properties.load(stream);
				synchronized (messages) {
					messages.add(properties.getProperty("welcome"));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		messageExecutor.shutdown();
	}

}
