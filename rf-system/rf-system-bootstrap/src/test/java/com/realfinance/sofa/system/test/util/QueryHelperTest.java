package com.realfinance.sofa.system.test.util;

import com.realfinance.sofa.system.domain.User;
import com.realfinance.sofa.system.repository.UserRepository;
import com.realfinance.sofa.system.test.base.AbstractTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;

public class QueryHelperTest extends AbstractTestBase {

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void test() {
        userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate p = criteriaBuilder.equal(root.get("department").get("code"), "1");
                return query.where(p).getRestriction();
            }
        });
    }
}
