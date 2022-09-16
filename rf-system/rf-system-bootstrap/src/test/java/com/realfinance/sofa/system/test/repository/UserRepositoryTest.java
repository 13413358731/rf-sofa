package com.realfinance.sofa.system.test.repository;

import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.system.domain.Department;
import com.realfinance.sofa.system.domain.Role;
import com.realfinance.sofa.system.domain.User;
import com.realfinance.sofa.system.facade.UserMngFacade;
import com.realfinance.sofa.system.model.UserDto;
import com.realfinance.sofa.system.repository.DepartmentRepository;
import com.realfinance.sofa.system.repository.RoleRepository;
import com.realfinance.sofa.system.repository.UserRepository;
import com.realfinance.sofa.system.test.base.AbstractTestBase;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.ParameterRegistry;
import org.hibernate.query.criteria.internal.Renderable;
import org.hibernate.query.criteria.internal.compile.RenderingContext;
import org.hibernate.query.criteria.internal.predicate.AbstractSimplePredicate;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
public class UserRepositoryTest extends AbstractTestBase {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserMngFacade userMngFacade;

    @Autowired
    RoleRepository roleRepository;
    @Test
    public void test1() {
        long a = System.currentTimeMillis();
        try {
            User user = em.find(User.class, 1);
            Optional<User> byId = userRepository.findById(1);
        } finally {
            System.out.println(System.currentTimeMillis() - a);
        }
    }

    @Test
    public void test2() {
        Pageable of = PageRequest.of(1, 10);
        Page<User> all = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                return criteriaBuilder.and();
            }
        }, of);
    }
    @Test
    public void test3() {
        RpcUtils.putRequestBaggage("operation.menu.code","usermng:view");
        RpcUtils.putRequestBaggage("principal.roles","ADMIN");
        RpcUtils.putRequestBaggage("principal.id","1");
        Page<UserDto> list = userMngFacade.list(null, PageRequest.of(1, 10));
    }

    @Test
    public void test4() {
        Optional<User> byId = userRepository.findById(1);
        User user = byId.get();
        user.getRoles().clear();
        userRepository.save(user);
    }

    @Test
    public void test5() {
        User user = userRepository.findById(1).get();
        userRepository.deleteById(1);
        //user = userRepository.findById(1).get();
        Optional<Role> byId = roleRepository.findById(1);
        byId.get().getUsers().clear();
        roleRepository.save(byId.get());
        System.out.println(byId.get().getUsers().stream().map(User::getId).collect(Collectors.toList()));

    }

    @Test
    @Transactional
    public void test6() {
        User user = userRepository.findById(1).get();
        User user1 = new User();
        BeanUtils.copyProperties(user,user1);
        user1.setUsername("12321321");
        user1.setId(null);
        user1.setV(null);
        user1.setRoles(new HashSet<>());
        user1.setRoleGroups(new HashSet<>());
        User save = userRepository.saveAndFlush(user1);
        System.out.println(save);
    }

    @Test
    public void test7() {
        Page<User> all = userRepository.findAll(new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate in = root.join("roles").get("id").in(2,3,4,5);
                return query.distinct(true).where(in).getRestriction();
            }
        }, PageRequest.of(0, 4));
        System.out.println(all);
    }


    @Autowired
    DepartmentRepository departmentRepository;
    @Test
    public void test8() {
        Department department = departmentRepository.findById(1).get();
        Department department1 = new Department();
        BeanUtils.copyProperties(department,department1);
        department1.setCode("12321321");
        department1.setId(null);
        department1.setV(null);
        department1.setUsers(new HashSet<>());
        department1.setChildren(new HashSet<>());
        Department save = departmentRepository.saveAndFlush(department1);
        System.out.println(save);
    }


    @Test
    public void test9() {
        List<Department> all = departmentRepository.findAll(new Specification<Department>() {
            @Override
            public Predicate toPredicate(Root<Department> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                Path<Object> tenantId = root.get("tenant").get("name");
                Predicate predicate = new AbstractSimplePredicate((CriteriaBuilderImpl) criteriaBuilder) {

                    @Override
                    public String render(boolean isNegated, RenderingContext renderingContext) {
                        return ( (Renderable) tenantId ).render( renderingContext ) + " = '系统'";
                    }

                    @Override
                    public void registerParameters(ParameterRegistry registry) {
                        Helper.possibleParameter( tenantId, registry );
                    }
                };

                return query.where(predicate).getRestriction();
            }
        });
        System.out.println(all.size());

    }

    @Test
    @Transactional
    public void test10() {
        List<User> all = userRepository.findAll();
        User user = all.get(0);
        Integer userId = user.getId();
        User user1 = userRepository.findById(userId).get();
        user1.setClassification("3311");
        User user2 = testHelper.getUser(userId);
        System.out.println(Objects.equals(user1.getClassification(),
                user2.getClassification()));
        System.out.println(user1 == user2);
    }
}
