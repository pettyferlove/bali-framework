package com.github.bali.core.framework.aspect;

import cn.hutool.core.util.StrUtil;
import com.github.bali.core.framework.annotation.SubmitAgain;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * 防止重复提交切面
 *
 * @author Petty
 * @version 1.0.0
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class SubmitAgainAspect {

    public final RedisTemplate<Object, Object> redisTemplate;

    private final static String SUBMIT_AGAIN = "bali:submit-again:%s";

    @Before("@annotation(submitAgain)")
    public void before(JoinPoint joinPoint, SubmitAgain submitAgain) {
        String key;
        Object[] args = joinPoint.getArgs();
        //设置文字模板,其中#{}表示表达式的起止，#user是表达式字符串，表示引用一个变量。
        String template = submitAgain.value();
        if (StrUtil.isBlank(template)) {
            key = String.valueOf(Arrays.hashCode(args));
        } else {
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;
            //测试SpringEL解析器

            //创建表达式解析器
            ExpressionParser passer = new SpelExpressionParser();
            //通过evaluationContext.setVariable可以在上下文中设定变量。
            EvaluationContext context = new StandardEvaluationContext();
            //2.最关键的一步:通过这获取到方法的所有参数名称的字符串数组
            String[] parameterNames = methodSignature.getParameterNames();
            for (String parameterName : parameterNames) {
                //3.通过你需要获取的参数名称的下标获取到对应的值
                int timeStampIndex = ArrayUtils.indexOf(parameterNames, parameterName);
                Object obj = args[timeStampIndex];
                context.setVariable(parameterName, obj);
            }
            //解析表达式，如果表达式是一个模板表达式，需要为解析传入模板解析器上下文。
            Expression expression = passer.parseExpression(template, new TemplateParserContext());
            //使用Expression.getValue()获取表达式的值，这里传入了Expression上下文，第二个参数是类型参数，表示返回值的类型。
            key = expression.getValue(context, String.class);
        }
        key = String.format(SUBMIT_AGAIN, key);
        long count = redisTemplate.opsForValue().increment(key, 1);
        if ((count - 1) > submitAgain.count()) {
            throw new RuntimeException("请勿重复提交");
        } else {
            redisTemplate.expire(key, submitAgain.time(), TimeUnit.MILLISECONDS);
        }
    }
}
