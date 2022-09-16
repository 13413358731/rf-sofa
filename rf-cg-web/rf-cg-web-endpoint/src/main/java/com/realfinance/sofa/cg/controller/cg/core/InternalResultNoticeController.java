package com.realfinance.sofa.cg.controller.cg.core;


import cn.hutool.core.io.FileUtil;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgProjectExecutionFacade;
import com.realfinance.sofa.cg.core.facade.CgProjectFacade;
import com.realfinance.sofa.cg.core.facade.CgPurchaseResultNoticeFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.model.cg.CgAttVo;
import com.realfinance.sofa.cg.model.cg.CgPurchaseResultNoticeSaveRequset;
import com.realfinance.sofa.cg.model.cg.CgPurchaseResultNoticeVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowTaskVo;
import com.realfinance.sofa.cg.model.system.DepartmentTreeVo;
import com.realfinance.sofa.cg.model.system.UserQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.cg.service.mapstruct.*;
import com.realfinance.sofa.cg.sup.facade.CgBusinessProjectFacade;
import com.realfinance.sofa.cg.sup.facade.CgMassMessagingFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierFacade;
import com.realfinance.sofa.cg.sup.model.*;
import com.realfinance.sofa.cg.util.LinkUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.filestore.FileStore;
import com.realfinance.sofa.common.filestore.FileStoreException;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.facade.TaskFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.sdebank.SdebankSDNSPaperless;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.DepartmentSmallTreeDto;
import com.realfinance.sofa.system.model.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Tag(name = "内部结果通知")
@RequestMapping("/cg/core/internalresultnotice")
public class InternalResultNoticeController implements FlowApi {
    //只用于配置通知审批流 无其他功能点
    private static final Logger log = LoggerFactory.getLogger(InternalResultNoticeController.class);

    public static final String MENU_CODE_ROOT = "internalresultnotice";

    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;


    @SofaReference(interfaceType = CgPurchaseResultNoticeFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchaseResultNoticeFacade cgPurchaseResultNoticeFacade;

    @SofaReference(interfaceType = CgProjectFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private CgProjectFacade cgProjectFacade;

    @Override
    public ResponseEntity<String> flowStartProcess(Integer id, Map<String, String> formData) {
        CgPurchaseResultNoticeDto purchaseResultNoticeDto = cgPurchaseResultNoticeFacade.getDetailsById(id);
        CgProjectDto projectDto = cgProjectFacade.getById(purchaseResultNoticeDto.getProjectId());

        //启动2个内部通知任务审批流 合同制单人/采购申请人 id相同则启动一个
        String processInstanceId = getFlowFacade().startProcessToUserId(getBusinessCode(), id.toString()+purchaseResultNoticeDto.getContractProducerId(), formData,purchaseResultNoticeDto.getContractProducerId(),projectDto.getName()+"已完成");
        String p="";
        if (projectDto!=null && !(purchaseResultNoticeDto.getContractProducerId()).equals(projectDto.getReqUserId())) {
            p = getFlowFacade().startProcessToUserId(getBusinessCode(), id.toString() + projectDto.getReqUserId(), formData, projectDto.getReqUserId(),projectDto.getName());
        }
        return ResponseEntity.ok(processInstanceId+":"+p);
    }

    @Override
    public ResponseEntity<FlowCallbackResponse> flowCallback(FlowCallbackRequest request) {
        return ResponseEntity.ok(FlowCallbackResponse.ok());
    }
    @Override
    public String getBusinessCode() {
        return MENU_CODE_ROOT;
    }

    @Override
    public FlowFacade getFlowFacade() {
        return flowFacade;
    }

}
