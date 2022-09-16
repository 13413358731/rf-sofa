package com.realfinance.sofa.cg.core.service.serialno;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.realfinance.sofa.cg.core.domain.serialno.SerialNumberRecord;
import com.realfinance.sofa.cg.core.domain.serialno.SerialNumberRecordId;
import com.realfinance.sofa.cg.core.repository.SerialNumberRecordRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.SpelCompilerMode;
import org.springframework.expression.spel.SpelParserConfiguration;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class SerialNumberServiceImpl implements SerialNumberService {

    private static final Logger log = LoggerFactory.getLogger(SerialNumberServiceImpl.class);
    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser(new SpelParserConfiguration(SpelCompilerMode.IMMEDIATE, null));
    private static final TemplateParserContext TEMPLATE_PARSER_CONTEXT = new TemplateParserContext();
    private static final LoadingCache<String, Expression> EXPRESSION_CACHE = CacheBuilder.newBuilder()
            .expireAfterAccess(6L, TimeUnit.HOURS)
            .initialCapacity(10)
            .maximumSize(100)
            .build(new CacheLoader<>() {
                @Override
                public Expression load(String key) {
                    if (log.isTraceEnabled()) {
                        log.trace("Parse expression：{}", key);
                    }
                    return EXPRESSION_PARSER.parseExpression(key, TEMPLATE_PARSER_CONTEXT);
                }
            });

    @Resource
    private SerialNumberRecordRepository serialNumberRecordRepository;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public String next(SerialNumberRecordId id) {
        return next(id, new StandardEvaluationContext());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public String next(SerialNumberRecordId id, Map<String, Object> variables) {
        if (log.isDebugEnabled()) {
            log.debug("输入variables：{}",variables);
        }
        StandardEvaluationContext standardEvaluationContext = new StandardEvaluationContext();
        if (variables != null) {
            standardEvaluationContext.setVariables(variables);
        }
        return next(id, standardEvaluationContext);
    }

    private String next(SerialNumberRecordId id, EvaluationContext context) {
        Objects.requireNonNull(context);
        SerialNumberRecord serialNumberRecord = serialNumberRecordRepository
                .findById(Objects.requireNonNull(id)).orElse(null);
        if (serialNumberRecord == null) {
            return "";
        }
        if (serialNumberRecord.getTemplate() == null) {
            return "";
        }
        String result = parse(serialNumberRecord.getTemplate(), setInternalVariables(context, serialNumberRecord));
        try {
            serialNumberRecordRepository.saveAndFlush(serialNumberRecord);
        } catch (Exception e) {
            log.error("保存序列号失败",e);
            throw new RuntimeException("保存序列号失败");
        }
        if (log.isDebugEnabled()) {
            log.debug("{}生成序列号：{}",id,result);
        }
        return result;
    }

    private EvaluationContext setInternalVariables(EvaluationContext context, SerialNumberRecord e) {
        context.setVariable("_no", nextSerialNumberStr(e));
        context.setVariable("_year", String.valueOf(e.getYear()));
        context.setVariable("_month", StringUtils.leftPad(String.valueOf(e.getMonth()),2,'0'));
        context.setVariable("_day", StringUtils.leftPad(String.valueOf(e.getDay()),2,'0'));
        return context;
    }

    private String nextSerialNumberStr(SerialNumberRecord e) {
        int next = e.nextSerialNumber();
        if (next < 0) {
            throw new RuntimeException("流水号小于0");
        }
        Integer length = e.getSerialNumberLength();
        if (length == null || length <= 0) {
            return String.valueOf(next);
        }
        //StringBuilder sb = new StringBuilder(next);
        if ((next+"").length() > length) {
            throw new RuntimeException("序列号超过最大长度" + length + "位");
        } else {
            return StringUtils.leftPad(String.valueOf(next),length,'0');
        }
    }

    private String parse(String template, EvaluationContext context) {
        try {
            return EXPRESSION_CACHE.getUnchecked(template).getValue(context,String.class);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("解析序列号模板失败",e);
            }
            throw new RuntimeException("解析序列号模板失败");
        }
    }
}
