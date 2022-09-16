//package com.realfinance.sofa.cg.core.schedule;
//
//import com.alipay.sofa.runtime.api.annotation.SofaReference;
//import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
//import com.realfinance.sofa.cg.core.domain.contract.ContractManage;
//import com.realfinance.sofa.cg.core.facade.CgContractManageFacade;
//import com.realfinance.sofa.cg.core.facade.CgParameterFacade;
//import com.realfinance.sofa.cg.core.facade.CgPurchaseResultNoticeFacade;
//import com.realfinance.sofa.cg.core.model.CgContractManageDto;
//import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeDto;
//import com.realfinance.sofa.cg.core.model.ParameterDto;
//import com.realfinance.sofa.common.notice.email.Email;
//import com.realfinance.sofa.flow.facade.FlowFacade;
//import com.realfinance.sofa.sdebank.SdebankEmailSender;
//import com.realfinance.sofa.system.facade.*;
//import com.realfinance.sofa.system.model.UserDetailsDto;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Component
//public class Schedule {
//    Logger logger = LoggerFactory.getLogger(Schedule.class);
//
//
//    @SofaReference(interfaceType = CgParameterFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
//    private CgParameterFacade cgParameterFacade;
//
//    @SofaReference(interfaceType = CgContractManageFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
//    private CgContractManageFacade cgContractManageFacade;
//
//    @SofaReference(interfaceType = CgPurchaseResultNoticeFacade.class, uniqueId = "${service.rf-cg-core.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
//    private CgPurchaseResultNoticeFacade cgPurchaseResultNoticeFacade;
//
//    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
//    private FlowFacade flowFacade;
//
//    @SofaReference(interfaceType = UserMngFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
//    private UserMngFacade userMngFacade;
//
//
//
//    //合同预警
//    @Scheduled(cron = "0 0 1 * * *")
//    public void ContractTaskScheduled() {
//        ParameterDto parameterDto = cgParameterFacade.findByParameterCode("001");
//        Long date = Long.valueOf(parameterDto.getValue());
//
//        //获取需要合同预警的数据
//        List<CgContractManageDto> dtoList = cgContractManageFacade.listToTask(date);
//        if (dtoList != null && dtoList.size() != 0) {
//            List<Integer> projectIds = dtoList.stream().map(e -> e.getProject().getId()).collect(Collectors.toList());
//            List<CgPurchaseResultNoticeDto> cgPurchaseResultNoticeDtos = cgPurchaseResultNoticeFacade.listByProjectId(projectIds);
//            Map<Integer, Integer> purMap = cgPurchaseResultNoticeDtos.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e.getContractProducerId()));
//
//            //所有需要发送邮件的用户id集合
//            Set<Integer> IdList = new HashSet<>();
//            //key 邮箱内容  value 需要发邮箱的用户集合
//            Map<String, List<Integer>> map = new HashMap<>();
//            //key CgContractManageDto 主键 value 邮箱内容
//            Map<Integer, String> contractManageMap = new HashMap<>();
//
//            for (CgContractManageDto cgContractManageDto : dtoList) {
//                List<Integer> userIds = new ArrayList<>();
//                //合同制单人和方案创建人一样时 只发一条待办
//                if ((cgContractManageDto.getProject().getCreatedUserId()).equals(purMap.get(cgContractManageDto.getProject().getId()))) {
//                    flowFacade.startProcessToUserId("contract", cgContractManageDto.getId().toString() + cgContractManageDto.getProject().getCreatedUserId().toString(), null, cgContractManageDto.getProject().getCreatedUserId(), cgContractManageDto.getProject().getName());
//                    userIds.add(cgContractManageDto.getProject().getCreatedUserId());
//                    logger.info("合同预警任务生成成功!通知用户Id:" + cgContractManageDto.getProject().getCreatedUserId());
//                    IdList.add(cgContractManageDto.getProject().getCreatedUserId());
//                } else {
//                    //获取采购方案-创建人id
//                    userIds.add(cgContractManageDto.getProject().getCreatedUserId());
//                    //获取购结果通知-合同制单人id
//                    userIds.add(purMap.get(cgContractManageDto.getProject().getId()));
//                    for (Integer userId : userIds) {
//                        flowFacade.startProcessToUserId("contract", cgContractManageDto.getId().toString() + userId.toString(), null, userId, cgContractManageDto.getProject().getName());
//                        IdList.add(userId);
//                        logger.info("合同预警任务生成成功!通知用户Id:" + userId);
//                    }
//                }
//                String body = getContractBody(cgContractManageDto, date);
//                //放入 邮箱内容 , 用户id集合
//                map.put(body, userIds);
//                //放入 主键  邮箱内容
//                contractManageMap.put(cgContractManageDto.getId(), body);
//            }
//
//            //获取用户邮箱
//            List<UserDetailsDto> userDetailsDtoList = userMngFacade.getDetailsByIds(IdList);
//            //key 用户id value 用户信息
//            Map<Integer, UserDetailsDto> emailMap = userDetailsDtoList.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
//            for (CgContractManageDto cgContractManageDto : dtoList) {
//                Email email = new Email();
//                email.setId(cgContractManageDto.getId().toString());
//                email.setBody(contractManageMap.get(cgContractManageDto.getId()));
//                email.setTenantId("01");
//                //收件人 集合
//                List<String> list = new ArrayList<>();
//                List<Integer> userIds = map.get(contractManageMap.get(cgContractManageDto.getId()));
//                email.setSubject("合同到期预警");
//                list.add("liaojya@sdebank.com");
//                list.add("purchasing_management@sdebank.com");
//                for (Integer userId : userIds) {
//                    UserDetailsDto userDetailsDto = emailMap.get(userId);
//                    if (userDetailsDto.getEmail() != null) {
//                        if (StringUtils.substringAfterLast(userDetailsDto.getEmail(), "@").equals("sdebank.com")) {
//                            list.add(userDetailsDto.getEmail());
//                        }
//                    }
//                }
//                logger.info("需要发送邮箱的用户共"+list.size()+"个");
//                if (list.size() == 0) {
//                    break;
//                }
//                email.setDestAddress(list);
//                SdebankEmailSender sd = new SdebankEmailSender();
//                sd.send(email);
//                logger.info("邮件发送成功,合同id:"+cgContractManageDto.getId());
//            }
//            List<Integer> ids = dtoList.stream().map(e -> e.getId()).collect(Collectors.toList());
//            //修改成已发送待办的状态
//            cgContractManageFacade.updateExpireStatus(ids, 1);
//        } else {
//            logger.info("暂无合同预警需要生成");
//        }
//    }
//
//    //供应商评估提醒
//    @Scheduled(cron = "0 0 2 * * *")
//    public void evaluateReminderTaskScheduled(){
//        ParameterDto parameterDto = cgParameterFacade.findByParameterCode("002");
//        Long date = Long.valueOf(parameterDto.getValue());
//
//        //获取需要供应商评估提醒的数据
//        List<CgContractManageDto> dtoList = cgContractManageFacade.listToSupplierTask(date);
//        if (dtoList != null && dtoList.size() != 0) {
//            List<Integer> projectIds = dtoList.stream().map(e -> e.getProject().getId()).collect(Collectors.toList());
//            List<CgPurchaseResultNoticeDto> cgPurchaseResultNoticeDtos = cgPurchaseResultNoticeFacade.listByProjectId(projectIds);
//            Map<Integer, Integer> purMap = cgPurchaseResultNoticeDtos.stream().collect(Collectors.toMap(e -> e.getProjectId(), e -> e.getContractProducerId()));
//
//            //所有需要发送邮件的用户id集合
//            Set<Integer> IdList = new HashSet<>();
//            //key 邮箱内容  value 需要发邮箱的用户集合
//            Map<String, List<Integer>> map = new HashMap<>();
//            //key CgContractManageDto 主键 value 邮箱内容
//            Map<Integer, String> contractManageMap = new HashMap<>();
//
//            for (CgContractManageDto cgContractManageDto : dtoList) {
//                List<Integer> userIds = new ArrayList<>();
//                //合同制单人和方案创建人一样时 只发一条待办
//                if ((cgContractManageDto.getProject().getCreatedUserId()).equals(purMap.get(cgContractManageDto.getProject().getId()))) {
//                    flowFacade.startProcessToUserId("evaluateReminder", cgContractManageDto.getId().toString() + cgContractManageDto.getProject().getCreatedUserId().toString(), null, cgContractManageDto.getProject().getCreatedUserId(), cgContractManageDto.getProject().getName());
//                    userIds.add(cgContractManageDto.getProject().getCreatedUserId());
//                    logger.info("供应商评估提醒任务生成成功!通知用户Id:" + cgContractManageDto.getProject().getCreatedUserId());
//                    IdList.add(cgContractManageDto.getProject().getCreatedUserId());
//                } else {
//                    //获取采购方案-创建人id
//                    userIds.add(cgContractManageDto.getProject().getCreatedUserId());
//                    //获取购结果通知-合同制单人id
//                    userIds.add(purMap.get(cgContractManageDto.getProject().getId()));
//                    for (Integer userId : userIds) {
//                        flowFacade.startProcessToUserId("evaluateReminder", cgContractManageDto.getId().toString() + userId.toString(), null, userId, cgContractManageDto.getProject().getName());
//                        IdList.add(userId);
//                        logger.info("供应商评估提醒任务生成成功!通知用户Id:" + userId);
//                    }
//                }
//                String body = getSupplierBody(cgContractManageDto, date);
//                //放入 邮箱内容 , 用户id集合
//                map.put(body, userIds);
//                //放入 主键  邮箱内容
//                contractManageMap.put(cgContractManageDto.getId(), body);
//            }
//
//            //获取用户邮箱
//            List<UserDetailsDto> userDetailsDtoList = userMngFacade.getDetailsByIds(IdList);
//            //key 用户id value 用户信息
//            Map<Integer, UserDetailsDto> emailMap = userDetailsDtoList.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
//            for (CgContractManageDto cgContractManageDto : dtoList) {
//                Email email = new Email();
//                email.setId(cgContractManageDto.getId().toString());
//                email.setBody(contractManageMap.get(cgContractManageDto.getId()));
//                email.setTenantId("01");
//                //收件人 集合
//                List<String> list = new ArrayList<>();
//                List<Integer> userIds = map.get(contractManageMap.get(cgContractManageDto.getId()));
//                email.setSubject("供应商评估提醒");
//                list.add("liaojya@sdebank.com");
//                list.add("purchasing_management@sdebank.com");
//                for (Integer userId : userIds) {
//                    UserDetailsDto userDetailsDto = emailMap.get(userId);
//                    if (userDetailsDto.getEmail() != null) {
//                        if (StringUtils.substringAfterLast(userDetailsDto.getEmail(), "@").equals("sdebank.com")) {
//                            list.add(userDetailsDto.getEmail());
//                        }
//                    }
//                }
//                logger.info("需要发送邮箱的用户共"+list.size()+"个");
//                if (list.size() == 0) {
//                    break;
//                }
//                email.setDestAddress(list);
//                SdebankEmailSender sd = new SdebankEmailSender();
//                sd.send(email);
//                logger.info("邮件发送成功,合同id:"+cgContractManageDto.getId());
//            }
//        } else {
//            logger.info("暂无供应商评估提醒需要生成");
//        }
//    }
//
//    /**
//     * 生成合同预警,动态邮箱内容
//     *
//     * @param cgContractManageDto 合同归档数据
//     * @param date                参数
//     * @return
//     */
//    private static String getContractBody(CgContractManageDto cgContractManageDto, Long date) {
//        String body = "" +
//                "【" + cgContractManageDto.getProject().getName() + "】" +
//                "项目下合同名称为【" + cgContractManageDto.getContractName() +
//                "】的合同还有" + date + "个月到期。";
//        return body;
//    }
//
//    /**
//     * 生成供应商评估提醒,动态邮箱内容
//     *
//     * @param cgContractManageDto 合同归档数据
//     * @param date                参数
//     * @return
//     */
//    private static String getSupplierBody(CgContractManageDto cgContractManageDto, Long date) {
//        String body = "【"+cgContractManageDto.getProject().getName()+
//                "】项目的结果通知发出已经隔了"
//                +date+"个月，需要进行供应商评估。";
//        return body;
//    }
//}
