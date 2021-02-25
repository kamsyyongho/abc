package com.skt.doss.common.config;

import com.skt.doss.common.utils.CipherManager;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;

@Slf4j
public class AuthRequestInterceptor implements RequestInterceptor {

	private static final String token = "doss-portal-front";

	@Value("${crowd.application.username}")
	private String applicationId;

	@Value("${crowd.application.password}")
	private String password;

	@Override
	public void apply(RequestTemplate template) {
		// TODO Auto-generated method stub
		Base64.Encoder encoder = Base64.getEncoder();
//		byte[] encodeBytes = encoder.encode("api_dossportal:test".getBytes());
		String authToken = encoder.encodeToString((applicationId+":"+password).getBytes());
		try {
			System.out.println(authToken);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("암호화 오류 발생");
			authToken = "error";
		}
		template.header("Content-Type", "application/json");
		template.header("Accept", "application/json");
		template.header("Authorization", "Basic "+authToken);
	}

}
