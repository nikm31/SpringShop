package ru.geekbrains.springdata.configs;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import ru.geekbrains.springdata.dto.ExecutionTimeLogger;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

	private ExecutionTimeLogger executionTimeLogger;

	@PostConstruct
	public void init() {
		executionTimeLogger = new ExecutionTimeLogger(new ConcurrentHashMap<>());
	}

	@Around("execution (public * ru.geekbrains.springdata.services..*(..))")
	public Object executionTimeLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		Long begin = System.currentTimeMillis();
		Object out = proceedingJoinPoint.proceed();
		Long end = System.currentTimeMillis();

		Long duration = end - begin;
		String className = proceedingJoinPoint.getSignature().getDeclaringTypeName();
		String methodName = proceedingJoinPoint.getSignature().getName();
		executionTimeLogger.add(className, methodName, duration);
		log.info(executionTimeLogger.toString());

		return out;
	}
}