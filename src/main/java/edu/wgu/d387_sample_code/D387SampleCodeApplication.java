package edu.wgu.d387_sample_code;

import org.aspectj.apache.bcel.util.ClassPath;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

@SpringBootApplication
public class D387SampleCodeApplication {
	static ExecutorService messageExecutor=newFixedThreadPool(5);
	public static List<String> messages = new ArrayList<>();
	public static List<String> timeZones = new ArrayList<>();
	public static void main(String[] args) {
		SpringApplication.run(D387SampleCodeApplication.class, args);

		ZoneId zEastern=ZoneId.of("America/New_York");
		ZoneId zMountain=ZoneId.of("America/Phoenix");
		ZoneId zUniversal=ZoneId.of("UTC");
		ZoneId zoneId=ZoneId.systemDefault();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm z");

		LocalDateTime localDateTime=LocalDateTime.now();
		ZonedDateTime zonedDateTime=localDateTime.atZone(zoneId);
		ZonedDateTime zonedDateTimeEastern=zonedDateTime.withZoneSameInstant(zEastern);
		timeZones.add("Eastern time "+formatter.format(zonedDateTimeEastern));
		ZonedDateTime zonedDateTimeMountain=zonedDateTime.withZoneSameInstant(zMountain);
		timeZones.add("Mountain Time "+formatter.format(zonedDateTimeMountain));
		ZonedDateTime zonedDateTimeUniversal=zonedDateTime.withZoneSameInstant(zUniversal);
		timeZones.add(formatter.format(zonedDateTimeUniversal));

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
