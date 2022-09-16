package com.realfinance.sofa.system.test.base;

import com.alipay.sofa.rpc.context.RpcInvokeContext;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.system.SystemApplication;
import com.realfinance.sofa.system.domain.User;
import org.junit.After;
import org.junit.Assert;
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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = SystemApplication.class)
@Rollback(false)
public class AbstractTestBase {

    private static final Logger log = LoggerFactory.getLogger(AbstractTestBase.class);

    @Autowired
    protected EntityManager em;

    @Autowired
    protected TestHelper testHelper;

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

    public static class TestHelper {
        private static final Logger log = LoggerFactory.getLogger(TestHelper.class);
        @Autowired
        protected EntityManager em;

        /**
         * 设置安全认证上下文
         * @param userId
         */
        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public void setRpcContext(Integer userId) {
            User user = em.find(User.class, userId);
            Assert.assertNotNull(user);
            log.info("setRpcContext, userId: {}",userId);
            RpcUtils.setTenantId(user.getTenant().getId());
            RpcUtils.setPrincipalId(userId);
            log.info("setRpcContext completed");
        }

        @Transactional(propagation = Propagation.REQUIRES_NEW)
        public User getUser(Integer id) {
            return em.find(User.class,id);
        }
    }
}
