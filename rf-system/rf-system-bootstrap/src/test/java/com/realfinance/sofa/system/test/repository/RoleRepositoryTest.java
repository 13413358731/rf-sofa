package com.realfinance.sofa.system.test.repository;

import com.realfinance.sofa.system.domain.Role;
import com.realfinance.sofa.system.domain.Tenant;
import com.realfinance.sofa.system.domain.User;
import com.realfinance.sofa.system.repository.RoleRepository;
import com.realfinance.sofa.system.repository.UserRepository;
import com.realfinance.sofa.system.test.base.AbstractTestBase;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.CriteriaUpdateImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.Collections;
import java.util.Set;

@Transactional(readOnly = true)
public class RoleRepositoryTest extends AbstractTestBase {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void test1() {
        User user = em.find(User.class, 1);



        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

        CriteriaUpdate<User> criteriaUpdate = criteriaBuilder.createCriteriaUpdate(User.class);
        Root<User> from = criteriaUpdate.from(User.class);
        //Join<Object, Object> roles = from.join("roles");


        criteriaUpdate.set("username","321");

        Predicate id = criteriaBuilder.equal(from.get("id"), 1);

        criteriaUpdate.where(id);
        int i = em.createQuery(criteriaUpdate).executeUpdate();
        System.out.println(i);

    }
}
