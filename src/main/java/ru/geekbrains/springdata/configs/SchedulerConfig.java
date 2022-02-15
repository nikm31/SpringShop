package ru.geekbrains.springdata.configs;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class SchedulerConfig {


//	@Scheduled(cron = "@hourly")
//	@Async
//	public void addProducts() {
//		String title = productRepo.findById(1L).get().getTitle();
//		System.out.println(title);
//	}
}
