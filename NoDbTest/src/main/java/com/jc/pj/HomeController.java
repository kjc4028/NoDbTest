package com.jc.pj;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	@Autowired
	private ThreadPoolTaskScheduler threadPoolTaskScheduler; 
	
	int cnt = 0;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	@Async     
	@RequestMapping("/test") 
	public void excutorTest() {
		System.out.println("async... " + Thread.activeCount() + " " + Thread.currentThread().getName() + " ");
		try {
			System.out.println(
					"getCorePoolSize : " + threadPoolTaskExecutor.getCorePoolSize()
					);
			System.out.println(
					"getPoolSize : " + threadPoolTaskExecutor.getPoolSize()
					);
			System.out.println(
					"getActiveCount : " + threadPoolTaskExecutor.getActiveCount()
					);
			System.out.println(
					"getQueue().size() : " + threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().size()
					+ "\n" + threadPoolTaskExecutor.getThreadPoolExecutor().getQueue().toString()
					);
			
			Thread.sleep(30000);
			 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
		System.out.println("async... " + Thread.activeCount() + " " + Thread.currentThread().getName() + " end");
	}   
	
	
	@Scheduled(fixedRate = 10000)
	public void schdulerTest() {
		
		long time = System.currentTimeMillis(); cnt++; 

		SimpleDateFormat dayTime = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");

		String str = dayTime.format(new Date(time));
		System.out.println(cnt + " " + "스케줄러 동작중...1 " + " " + 
				Thread.activeCount()  + " " +
							Thread.currentThread().getName()  + " " +
							//Thread.currentThread().activeCount()  + " " +
							str
				);
		/*
		 * System.out.println(scheduler.getThreadNamePrefix() + " " );
		 * if(Thread.currentThread().activeCount() > 100) { //scheduler.destroy();
		 * scheduler.shutdown(); scheduler.getThreadGroup(); schdulerTest(); }
		 */
		
		 
	}
	
}
