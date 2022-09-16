package com.realfinance.sofa.cg.controller.cg.core;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.controller.flow.FlowApi;
import com.realfinance.sofa.cg.core.facade.CgContractManageFacade;
import com.realfinance.sofa.cg.core.facade.CgParameterFacade;
import com.realfinance.sofa.cg.core.facade.CgPurchaseResultNoticeFacade;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.facade.UserMngFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "供应商评估提醒")
@RequestMapping("/cg/core/evaluateReminder")
public class EvaluateReminderController implements FlowApi {
    //只用于配置通知审批流 无其他功能点
    private static final Logger log = LoggerFactory.getLogger(EvaluateReminderController.class);

    public static final String MENU_CODE_ROOT = "evaluateReminder";

    @SofaReference(interfaceType = CgContractManageFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgContractManageFacade cgContractManageFacade;

    @SofaReference(interfaceType = CgPurchaseResultNoticeFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgPurchaseResultNoticeFacade cgPurchaseResultNoticeFacade;

    @SofaReference(interfaceType = SystemQueryFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private SystemQueryFacade systemQueryFacade;

    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;

    @SofaReference(interfaceType = CgParameterFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgParameterFacade cgParameterFacade;

    @SofaReference(interfaceType = UserMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private UserMngFacade userMngFacade;

/*
    @GetMapping("text")
    public void text() {
        ParameterDto parameterDto = cgParameterFacade.findByParameterCode("002");
        Long date = Long.valueOf(parameterDto.getValue());
        //获取需要供应商评估提醒的数据
        List<CgContractManageDto> dtoList = cgContractManageFacade.listToSupplierTask(date);
        if (dtoList != null && dtoList.size() != 0) {
            List<Integer> projectIds = dtoList.stream().map(e -> e.getProject().getId()).collect(Collectors.toList());
            List<CgPurchaseResultNoticeDto> cgPurchaseResultNoticeDtos = cgPurchaseResultNoticeFacade.listByProjectId(projectIds);
            Map<Integer, Integer> purMap = cgPurchaseResultNoticeDtos.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e.getContractProducerId()));

            //所有需要发送邮件的用户id集合
            Set<Integer> IdList = new HashSet<>();
            //key 邮箱内容  value 需要发邮箱的用户集合
            Map<String, List<Integer>> map = new HashMap<>();
            //key CgContractManageDto 主键 value 邮箱内容
            Map<Integer, String> contractManageMap = new HashMap<>();

            for (CgContractManageDto cgContractManageDto : dtoList) {
                List<Integer> userIds = new ArrayList<>();
                //合同制单人和方案创建人一样时 只发一条待办
                if ((cgContractManageDto.getProject().getCreatedUserId()).equals(purMap.get(cgContractManageDto.getProject().getId()))) {
                    flowFacade.startProcessToUserId("evaluateReminder", cgContractManageDto.getId().toString() + cgContractManageDto.getProject().getCreatedUserId().toString(), null, cgContractManageDto.getProject().getCreatedUserId(), null);
                    userIds.add(cgContractManageDto.getProject().getCreatedUserId());
                    IdList.add(cgContractManageDto.getProject().getCreatedUserId());
                } else {
                    //获取采购方案-创建人id
                    userIds.add(cgContractManageDto.getProject().getCreatedUserId());
                    //获取购结果通知-合同制单人id
                    userIds.add(purMap.get(cgContractManageDto.getProject().getId()));
                    for (Integer userId : userIds) {
                        flowFacade.startProcessToUserId("evaluateReminder", cgContractManageDto.getId().toString() + userId.toString(), null, userId, null);
                        IdList.add(userId);
                    }
                }
                String body = "【"+cgContractManageDto.getProject().getName()+"】项目距离结果通知发出时间已经过去了"+date+"个月，需要进行供应商评估。";
                //放入 邮箱内容 , 用户id集合
                map.put(body, userIds);
                //放入 主键  邮箱内容
                contractManageMap.put(cgContractManageDto.getId(), body);
            }

            //获取用户邮箱
            List<UserDetailsDto> userDetailsDtoList = userMngFacade.getDetailsByIds(IdList);
            //key 用户id value 用户信息
            Map<Integer, UserDetailsDto> emailMap = userDetailsDtoList.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
            for (CgContractManageDto cgContractManageDto : dtoList) {
                Email email = new Email();
                email.setId(cgContractManageDto.getId().toString());
                email.setBody(contractManageMap.get(cgContractManageDto.getId()));
                email.setTenantId("01");
                //收件人 集合
                List<String> list = new ArrayList<>();
                List<Integer> userIds = map.get(contractManageMap.get(cgContractManageDto.getId()));
                email.setSubject("供应商评估提醒");
                list.add("liaojya@sdebank.com");
                list.add("purchasing_management@sdebank.com");
                for (Integer userId : userIds) {
                    UserDetailsDto userDetailsDto = emailMap.get(userId);
                    if (userDetailsDto.getEmail() != null) {
                        if (StringUtils.substringAfterLast(userDetailsDto.getEmail(), "@").equals("sdebank.com")) {
                            list.add(userDetailsDto.getEmail());
                        }
                    }
                }
                System.out.println("需要发送邮箱的用户共"+list.size()+"个");
                if (list.size() == 0) {
                    break;
                }
                email.setDestAddress(list);
                SdebankEmailSender sd = new SdebankEmailSender();
                sd.send(email);
                System.out.println("邮件发送成功,合同id:"+cgContractManageDto.getId());
            }
        } else {
            System.out.println("暂无供应商评估提醒需要生成");
        }
    }
*/


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
