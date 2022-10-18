package com.example.project.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.mapping.Join;
import org.springframework.stereotype.Component;

@Aspect //AOP 클래스 선언 : 부가기능을 주입하는 클래스
@Component// IOC 컨테이너가 해당 객체를 생성 및 관리
@Slf4j
public class DebuggingAspect {

    //대상 메소드 선택 : api패키지의 모든 메소드
    @Pointcut("execution(* com.example.project.api.*.*(..))")
    private void cut(){

    }

    //실행 시점 설정 : cut()의 대상이 수행되기 이전에 이 메서드가 실행
    @Before("cut()")
    public void loggingArgs(JoinPoint joinPoint){ //cut()의 대상 메소드
        // 입력 값을 가져온다
        Object[] args = joinPoint.getArgs();

        //클래스명
        String className = joinPoint.getTarget().getClass().getSimpleName();

        //메소드명
        String methodName = joinPoint.getSignature().getName();

        //입력값 로깅
        for(Object obj : args){
            log.info("{}#{}의 입력값 => {}",className, methodName, obj);
        }

        //CommentService#create()의 입력값 => 5
        //CommentService#create()의 입력값 => CommentDto
    }

    //실행시점 : cut()에 지정된 대상 호출 성공후
    @AfterReturning(value = "cut()", returning = "returnObj")
    public void loggingReturnValue(JoinPoint joinPoint, //cut()의 대상메소드
                                   Object returnObj){ //리턴값
//클래스명
        String className = joinPoint.getTarget().getClass().getSimpleName();

        //메소드명
        String methodName = joinPoint.getSignature().getName();

        //반환값 로깅
        // CommentService#create()의 반환값 => CommentDto(id=10.....
        log.info("{}#{}의 반환값 => {}",className, methodName, returnObj);

        //
    }
}
