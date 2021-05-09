package org.smartdubia.app.aspect;
import java.util.Arrays;
import java.util.Objects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class BookServiceAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@AfterReturning(value = "execution(* org.smartdubai.app.service.*.*(..))", 
			returning = "result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		logger.info("{} returned with value {}", joinPoint, result);
	}
	
	@Before(value = "execution(* org.smartdubai.app.service.*.*(..))")
	public void Before(JoinPoint joinPoint) {
		logger.info("Before execution of {} with args {}", joinPoint, Arrays.toString(joinPoint.getArgs()));		
	}
	
	@After(value = "execution(* org.smartdubai.app.service.*.*(..))")
	public void after(JoinPoint joinPoint) {
		logger.info("After execution of {}", joinPoint);
	}	
}