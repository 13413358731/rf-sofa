package com.realfinance.sofa.cg.controller.cg.core;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgAfterSalesFacade;
import com.realfinance.sofa.cg.core.facade.CgCommodityFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgAfterSalesVo;
import com.realfinance.sofa.cg.model.cg.CgCommodityVo;
import com.realfinance.sofa.cg.service.mapstruct.CgAfterSalesMapper;
import com.realfinance.sofa.cg.service.mapstruct.CgCommodityMapper;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@Tag(name = "售后服务")
@RequestMapping("/cg/core/AfterSalesController")
public class AfterSalesController implements FlowApi {
    private static final Logger log = LoggerFactory.getLogger(AfterSalesController.class);

    public static final String MENU_CODE_ROOT = "afteraales";

    @SofaReference(interfaceType = CgAfterSalesFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgAfterSalesFacade cgAfterSalesFacade;

    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;


    @Resource
    private CgAfterSalesMapper cgAfterSalesMapper;

    @GetMapping("AfterSaleslist")
    @Operation(summary = "查询售后商品")
    public ResponseEntity<Page<CgAfterSalesVo>> list(CgAfterSalesQueryCriteria queryCriteria,
                                                     Pageable pageable) {
        Page<CgAfterSalesDto> result = cgAfterSalesFacade.list(queryCriteria, pageable);
        return ResponseEntity.ok(result.map(cgAfterSalesMapper::toVo));
    }

    @PostMapping("save")
    @Operation(summary = "保存")
    public ResponseEntity<Integer> save(@Validated @RequestBody CgAfterSalesVo vo) {
        CgAfterSalesDto saveDto = cgAfterSalesMapper.toSaveDto(vo);
        Integer id = cgAfterSalesFacade.save(saveDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("delete")
    @Operation(summary = "删除")
    public ResponseEntity<?> delete(@Parameter(description = "商品ID") @RequestParam Set<Integer> id) {
        cgAfterSalesFacade.delete(id);
        return ResponseEntity.ok().build();
    }


    @Override
    public String getBusinessCode() {
        return MENU_CODE_ROOT;
    }

    @Override
    public FlowFacade getFlowFacade() {
        return flowFacade;
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(@RequestBody FlowCallbackRequest request) {
        return ResponseEntity.ok(FlowCallbackResponse.ok());
    }

}
