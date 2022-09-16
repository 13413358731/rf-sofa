package com.realfinance.sofa.cg.controller.flow;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.model.flow.ProcessInstanceVo;
import com.realfinance.sofa.cg.service.mapstruct.FlowMapper;
import com.realfinance.sofa.flow.facade.ProcessInsFacade;
import com.realfinance.sofa.flow.model.ProcessInstanceDto;
import com.realfinance.sofa.flow.model.HistoricProcessInstanceQueryCriteria;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@Tag(name = "我发起的流程")
@RequestMapping("/mytask/proccess")
public class MyProcessController {

    private static final Logger log = LoggerFactory.getLogger(MyProcessController.class);

    public static final String MENU_CODE_ROOT = "proccess";

    @SofaReference(interfaceType = ProcessInsFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private ProcessInsFacade processInsFacade;

    @Resource
    private FlowMapper flowMapper;

    @GetMapping("list")
    @Operation(summary = "查询我发起的流程")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<ProcessInstanceVo>> list(@ParameterObject HistoricProcessInstanceQueryCriteria queryCriteria,
                                                        Pageable pageable,
                                                        Authentication authentication) {
        queryCriteria.setStartedBy(authentication.getName());
        Page<ProcessInstanceDto> result = processInsFacade.listHistory(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(flowMapper::processInstanceDto2ProcessInstanceVo));
    }

}
