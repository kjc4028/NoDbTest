package com.jc.pj;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ThreadPoolExecutor;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

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
		
		String str = "test<br>test2<p>testing.....</p><div>div area</div>&nbsp;&amp;";
		
		str = str.replaceAll("^[<>\\/]$", "");
		
		model.addAttribute("testTxt", str );
		
		return "home";
	}
	
	@RequestMapping(value = "/ips", method = RequestMethod.GET)
	public void testPost(Model model, HttpServletRequest request, HttpServletResponse response) {
		try {
			
			TrustManager[] trustCerts = new TrustManager[]{
				    new X509TrustManager() {
				        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				            return null;
				        }
				        public void checkClientTrusted(
				            java.security.cert.X509Certificate[] certs, String authType) {
				        }
				        public void checkServerTrusted(
				            java.security.cert.X509Certificate[] certs, String authType) {
				        }
				    }
				};
			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			sc.init(null, trustCerts, new java.security.SecureRandom());
			
			//URL url = new URL("http://javaking75.blog.me/rss");
			URL url = new URL("https://www.kiep.go.kr/kiepForGov24OpenAPI.do?startDate=20191118&endDate=20191121");

			HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());
			
			//HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		    conn.setRequestMethod("GET"); 
		    conn.setConnectTimeout(3000); // 3초 
		    conn.setDoInput(true);
		    conn.setDoOutput(true);

		       // 응답 내용(BODY) 구하기        
	        try (InputStream in = conn.getInputStream();
	                ByteArrayOutputStream out = new ByteArrayOutputStream()) {
	            
	            byte[] buf = new byte[1024 * 8];
	            int length = 0;
	            while ((length = in.read(buf)) != -1) {
	                out.write(buf, 0, length);
	            }
	            System.out.println(new String(out.toByteArray(), "UTF-8"));            
	        }
	        conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}

	@RequestMapping(value = "/ips2", method = RequestMethod.GET)
	public void testPost2(Model model, HttpServletRequest request, HttpServletResponse response) {
			
			try {
		         
				HashMap< String, String > pList = new HashMap<String, String>();  
				pList.put("test", "tVal");
				
				URL url = new URL("http://localhost:8080/pj/ips3"); // URL 설정 

		            HttpURLConnection http = (HttpURLConnection) url.openConnection(); // 접속 
		            //-------------------------- 
		            //   전송 모드 설정 - 기본적인 설정 
		            //-------------------------- 
		            http.setDefaultUseCaches(false);
		            http.setDoInput(true); // 서버에서 읽기 모드 지정 
		            http.setDoOutput(true); // 서버로 쓰기 모드 지정  
		            http.setRequestMethod("POST"); // 전송 방식은 POST S



		            //--------------------------
		            // 헤더 세팅
		            //--------------------------
		            // 서버에게 웹에서 <Form>으로 값이 넘어온 것과 같은 방식으로 처리하라는 걸 알려준다 
		            http.setRequestProperty("content-type", "application/x-www-form-urlencoded");
		            //http.setRequestProperty("content-type", "text/html");


		            //-------------------------- 
		            //   서버로 값 전송 
		            //-------------------------- 
		            StringBuffer buffer = new StringBuffer();

		            //HashMap으로 전달받은 파라미터가 null이 아닌경우 버퍼에 넣어준다
		            if (pList != null) {

		                Set<String> key = pList.keySet();

		                for (Iterator<String> iterator = key.iterator(); iterator.hasNext();) {
		                    String keyName = (String) iterator.next();
		                    String valueName = pList.get(keyName);
		                    buffer.append(keyName).append("=").append(valueName);
		                }
		            } 

		            OutputStreamWriter outStream = new OutputStreamWriter(http.getOutputStream(), "UTF-8");
			
			  PrintWriter writer = new PrintWriter(outStream); 
			  writer.write(buffer.toString()); 
			  writer.flush();
			 
		            
		            System.out.println(http.getResponseCode());
			} catch (Exception e) {
				System.out.println("error!!!" + e);
				e.printStackTrace(); 
			}
		
		//return "redirect:https://nid.naver.com/nidlogin.login";
	
	}
	
	@RequestMapping(value = "/ips3", method = RequestMethod.POST)
	public String testPost3(Model model, HttpServletRequest request, HttpServletResponse response) {
			
		System.out.println("ips3...");  
		
		return "postTest";
	
	} 

	@RequestMapping(value = "/ips4", method = RequestMethod.GET)
	public String testPost4(Model model, HttpServletRequest request, HttpServletResponse response) {
		
		 
		return "postTest2";
		
	}
	
	@RequestMapping(value = "/ips5", method = RequestMethod.GET)
	public String testPost5(Model model, HttpServletRequest request, HttpServletResponse response) {
		return "postTest";
	}

	@RequestMapping(value = "/rest", method = RequestMethod.GET)
	public void testRest(Model model, HttpServletRequest request, HttpServletResponse response) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("kwd", "test");

	//Test용 로컬 주소
        String url = "http://localhost:8080/pj/ips3";
        ResponseEntity<String> res = new RestTemplate().postForEntity(url, parameters, String.class);
        System.out.println(res.getBody());
        System.out.println(res.getStatusCode());
	}
	
	@Async     
	@RequestMapping("/test") 
	public void excutorTest() {
		ThreadPoolExecutor threadPoolExecutor =  threadPoolTaskExecutor.getThreadPoolExecutor();
		System.out.println(threadPoolExecutor.getCorePoolSize() + " " 
				+ threadPoolExecutor.getCorePoolSize() + " "
				+ threadPoolExecutor.getActiveCount() + " " 
				+ threadPoolExecutor.getQueue() + " "
				+ threadPoolExecutor.getRejectedExecutionHandler() + " "
				+ threadPoolExecutor.getTaskCount() + " "
				+ threadPoolExecutor.getCompletedTaskCount()
		);
		
		
		
		threadPoolExecutor.shutdownNow();
		threadPoolTaskExecutor.initialize();
		
		System.out.println("async... " + Thread.activeCount() + " " + Thread.currentThread().getName() + " ");
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
			for(int i =0; i < 100000000; i++) {
				String a = ""+i;
				if(i%10000000 == 0) {
					//System.out.println(">>>>>" + i);
				}
			}
		System.out.println("async... " + Thread.activeCount() + " " + Thread.currentThread().getName() + " end");
		
		ThreadPoolExecutor threadPoolExecutor2 =  threadPoolTaskExecutor.getThreadPoolExecutor();
		System.out.println(threadPoolExecutor2.getCorePoolSize() + " " 
				+ threadPoolExecutor2.getCorePoolSize() + " "
				+ threadPoolExecutor2.getActiveCount() + " " 
				+ threadPoolExecutor2.getQueue() + " "
				+ threadPoolExecutor2.getRejectedExecutionHandler() + " "
				+ threadPoolExecutor2.getTaskCount() + " "
				+ threadPoolExecutor2.getCompletedTaskCount()
		);
	}   
	
	
	//@Scheduled(fixedRate = 10000)
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
