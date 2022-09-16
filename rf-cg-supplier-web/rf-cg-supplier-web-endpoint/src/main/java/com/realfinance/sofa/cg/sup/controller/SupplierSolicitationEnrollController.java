package com.realfinance.sofa.cg.sup.controller;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAccountFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierSolicitationFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierSolicitationMapper;
import com.realfinance.sofa.cg.sup.util.LinkUtils;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.curator.framework.AuthInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "意向征集应答")
@RequestMapping("/cg/supsolicitation")
public class SupplierSolicitationEnrollController {

    private static final Logger log = LoggerFactory.getLogger(SupplierSolicitationEnrollController.class);

    @SofaReference(interfaceType = CgSupplierAccountFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierAccountFacade cgSupplierAccountFacade;

    @SofaReference(interfaceType = CgSupplierSolicitationFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierSolicitationFacade solicitationFacade;

    @SofaReference(interfaceType = CgSupplierFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierFacade cgSupplierFacade;

    @Resource
    private ObjectMapper objectMapper;


    @GetMapping("querysuppliercontactsrefer")
    @Operation(summary = "查询供应商联系人参照")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Page<SupplierContactsDto>> querySupplierRefer(Pageable pageable,@RequestParam Integer id){
        Page<SupplierContactsDto> page = solicitationFacade.listrefer(pageable,id);
        return ResponseEntity.ok(page);

    }


    @PostMapping("/enroll")
    @Operation(summary = "报名")
    public ResponseEntity<Integer> enroll(Authentication authentication, Integer solicitationId) {
        ObjectNode result = objectMapper.convertValue(authentication.getPrincipal(), ObjectNode.class);
        CgSupplierDetailsDto detailsDto = cgSupplierFacade.getDetailsByUsername(result.get("username").asText());
        Integer[] arr = new Integer[2];
        arr[0] = detailsDto.getId();
        boolean enlist = solicitationFacade.isEnlist(solicitationId, detailsDto.getId());
        if (!enlist) { //已报名
            arr[1] = 1;
        } else { //未报名，进行报名
            EnrollSaveDto saveDto = new EnrollSaveDto();
            saveDto.setSolicitationId(solicitationId);
            saveDto.setSupplierId(detailsDto.getId());
            saveDto.setSupplierName(detailsDto.getName());
            solicitationFacade.saveEnroll(saveDto);
            arr[1] = 1;
        }

        return ResponseEntity.ok(detailsDto.getId());
    }


    @PostMapping("registrationinformation")
    @Operation(summary = "保存报名信息")
    public ResponseEntity<?> saveinformation(@RequestBody registrationInformation information, Authentication authentication, Integer solicitationId) {
        ObjectNode result = objectMapper.convertValue(authentication.getPrincipal(), ObjectNode.class);
        CgSupplierDetailsDto detailsDto = cgSupplierFacade.getDetailsByUsername(result.get("username").asText());
        solicitationFacade.saveinformation(information, solicitationId, detailsDto.getId());
        return ResponseEntity.ok().build();
    }


    @GetMapping("list")
    @Operation(summary = "列表")
    public ResponseEntity<Page<CgSupplierSolicitationDto>> list(Pageable pageable) {
        Page<CgSupplierSolicitationDto> list = solicitationFacade.listrefer(pageable);
        return ResponseEntity.ok(list);
    }


    @GetMapping("getdetail")
    @Operation(summary = "获取意向征集详情")
    public ResponseEntity<Object[]> getdetail(@Parameter(description = "意向征集ID") @RequestParam Integer id,
                                              @AuthenticationPrincipal Authentication authentication) {
        CgSupplierSolicitationDetailsDto dto = solicitationFacade.getdetail(id);
        SupplierSolicitationVo vo = SupplierSolicitationMapper.INSTANCE.toVo(dto);
        Object[] arr = new Object[4];
        if (vo.getAttachments() != null) {
            for (AttachmentVo o : vo.getAttachments()) {
                FileToken fileToken = FileTokens.create(o.getPath(), o.getName(),null);
                o.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        if (vo.getEnabled()){
            arr[3]=0;//未到报名时间或已超过报名时间或未审核入库，不显示报名按钮
        }else {
            arr[3]=1;//在报名时间范围内，显示报名按钮
        }
        arr[0] = vo;//意向征集vo
        if (authentication != null) {
            ObjectNode result = objectMapper.convertValue(authentication.getPrincipal(), ObjectNode.class);
            CgSupplierDetailsDto detailsDto = cgSupplierFacade.getDetailsByUsername(result.get("username").asText());
              if (detailsDto!=null){
                  boolean enlist = solicitationFacade.isEnlist(id, detailsDto.getId());
                  if (!enlist) { //已报名,显示修改报名信息按钮
                      arr[1] = 1;
                  } else { //未报名，显示报名按钮
                      arr[1] = 0;
                  }
                  arr[2] = detailsDto.getId();
              }else {
                  arr[3]=0;
              }

            return ResponseEntity.ok(arr);
        } else {
            arr[0] = vo;
            return ResponseEntity.ok(arr);
        }
    }


}