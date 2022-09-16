package com.realfinance.sofa.system.test.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import com.realfinance.sofa.common.datascope.RuleConditions;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.system.domain.Menu;
import com.realfinance.sofa.system.model.MenuDataRuleSmallDto;
import com.realfinance.sofa.system.repository.MenuRepository;
import com.realfinance.sofa.system.test.base.AbstractTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.realfinance.sofa.common.rpc.constants.RpcContextConstants.MENU_DATA_RULES_JSON_REQUEST_BAGGAGE_KEY;

@Transactional(readOnly = true)
public class MenuRepositoryTest extends AbstractTestBase {

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void test0() {
     /*   Set<Set<MenuDataRuleSmallDto>> r = new HashSet<>();
        Set<MenuDataRuleSmallDto> c = new HashSet<>();
        MenuDataRuleSmallDto menuDataRuleDto = new MenuDataRuleSmallDto();
        menuDataRuleDto.setId(1);
        menuDataRuleDto.setRuleConditions(RuleConditionsEnum.SQL.name());
        menuDataRuleDto.setRuleValue("$entity.roles.code$ = 'SYSTEM_ADMIN' OR $entity.parent.code$ = 'process'");
        c.add(menuDataRuleDto);
        r.add(c);
        DataRuleUtils.installMenuDataRules(r);*/
    }

    @Test
    public void test1() throws JsonProcessingException {
        JpaQueryHelper jpaQueryHelper = new JpaQueryHelper();
        ObjectMapper objectMapper = new ObjectMapper();
        Set<Set<MenuDataRuleSmallDto>> r = new HashSet<>();
        Set<MenuDataRuleSmallDto> c = new HashSet<>();
        MenuDataRuleSmallDto menuDataRuleDto = new MenuDataRuleSmallDto();
        menuDataRuleDto.setId(1);
       /* menuDataRuleDto.setRuleAttribute("roles.code");
        menuDataRuleDto.setRuleConditions(RuleConditionsEnum.EQUAL.name());
        menuDataRuleDto.setRuleValue("SYSTEM_ADMIN");*/
        menuDataRuleDto.setRuleConditions(RuleConditions.SQL.name());
        menuDataRuleDto.setRuleValue("$entity.roles.code$ = 'SYSTEM_ADMIN' OR $entity.parent.code$ = '#{@systemQueryImpl.queryTenants().toString()}'");
        c.add(menuDataRuleDto);
        r.add(c);

        RpcUtils.putRequestBaggage(MENU_DATA_RULES_JSON_REQUEST_BAGGAGE_KEY,objectMapper.writeValueAsString(r));

        List<Menu> all = menuRepository.findAll(new Specification<Menu>() {
            @Override
            public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Predicate id = criteriaBuilder.equal(root.get("id"), "26");

                return query.where(id).getRestriction();
            }
        }.and(jpaQueryHelper.dataRuleSpecification()));

        System.out.println(all);
    }
}
