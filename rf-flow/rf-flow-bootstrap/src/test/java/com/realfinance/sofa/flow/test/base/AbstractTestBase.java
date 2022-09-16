package com.realfinance.sofa.flow.test.base;

import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.realfinance.sofa.flow.FlowApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = FlowApplication.class)
@Rollback(false)
public class AbstractTestBase {

    private static final Logger log = LoggerFactory.getLogger(AbstractTestBase.class);

    @Autowired
    protected EntityManager em;

    @Autowired
    protected DataSource dataSource;

    @Test
    public void contextLoads() {

    }

    @Before
    public void before() {
        log.info("RpcInvokeContext.removeContext()");
        RpcInvokeContext.removeContext();
        log.info("RpcInvokeContext.removeContext() completed");
    }

    @After
    public void after() {
        log.info("RpcInvokeContext.removeContext()");
        RpcInvokeContext.removeContext();
        log.info("RpcInvokeContext.removeContext() completed");
    }
}
