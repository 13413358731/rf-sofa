//package com.realfinance.sofa.system.schedule;
//
//import com.alipay.sofa.runtime.api.annotation.SofaReference;
//import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
//import com.realfinance.sofa.system.facade.AssociatedTranFacade;
//import com.realfinance.sofa.system.facade.DataSyncFacade;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//@Component
//public class Schedule {
//    Logger logger = LoggerFactory.getLogger(Schedule.class);
//
//    @SofaReference(interfaceType = AssociatedTranFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
//    private AssociatedTranFacade associatedTranFacade;
//
//    @SofaReference(interfaceType = DataSyncFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
//    private DataSyncFacade dataSyncFacade;
//
//    //关联交易数据同步
//    //每天凌晨一点整跑批
////    @Scheduled(cron = "0 0 1 * * *")
//    public void associatedTranScheduled() {
//        associatedTranFacade.syncAssociatedTran();
//    }
//
//    //统一门户
//    //@Scheduled(cron = "0 0 1 * * *")
//    public void dataSyncScheduled() {
//        dataSyncFacade.syncEtlUser();
//        dataSyncFacade.syncEtlRole();
//        dataSyncFacade.syncEtlDepartment();
//        dataSyncFacade.syncEtlUserDepartment();
//        dataSyncFacade.syncEtlUserRole();
//    }
//
//    //统一门户中间表数据同步
//    //@Scheduled(cron = "0 0 2 * * *")
//    public void syncScheduled() {
//        dataSyncFacade.syncAll();
//    }
//
//
//}
