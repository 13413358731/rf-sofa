package com.realfinance.sofa.flow.flowable.interceptor;

import org.flowable.engine.interceptor.CreateUserTaskAfterContext;
import org.flowable.engine.interceptor.CreateUserTaskBeforeContext;
import org.flowable.engine.interceptor.CreateUserTaskInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCreateUserTaskInterceptor implements CreateUserTaskInterceptor {

    public static final Logger log = LoggerFactory.getLogger(CustomCreateUserTaskInterceptor.class);

    @Override
    public void beforeCreateUserTask(CreateUserTaskBeforeContext context) {

    }

    @Override
    public void afterCreateUserTask(CreateUserTaskAfterContext context) {

    }
}
