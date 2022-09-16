package com.realfinance.sofa.cg.controller.system;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.jcraft.jsch.ChannelSftp;
import com.realfinance.sofa.sdebank.SdebankEquityPenetration;
import com.realfinance.sofa.sdebank.model.ElementBusIInfoDto;
import com.realfinance.sofa.sdebank.model.EquityPenetrationDto;
import com.realfinance.sofa.sdebank.model.SupplierRelationshipDto;
import com.realfinance.sofa.sdebank.response.TokenResponse;
import com.realfinance.sofa.sdebank.sftp.SdebankSFTP;
import com.realfinance.sofa.sdebank.sftp.SftpCategory;
import com.realfinance.sofa.system.facade.AssociatedTranFacade;
import com.realfinance.sofa.system.facade.DataSyncFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "统一门户")
@RequestMapping("/system/datasync")
public class DataSyncController {
    private static final Logger log = LoggerFactory.getLogger(DataSyncController.class);

    @SofaReference(interfaceType = DataSyncFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt", timeout = 300000))
    private DataSyncFacade dataSyncFacade;


    @PostMapping("syncEtlUser")
    @Operation(summary = "统一人员数据")
    public void syncEtlUser() {
        dataSyncFacade.syncEtlUser();
    }

    @PostMapping("syncEtlDepartment")
    @Operation(summary = "统一机构数据")
    public void syncEtlDepartment() {
        dataSyncFacade.syncEtlDepartment();
    }

    @PostMapping("syncEtlRole")
    @Operation(summary = "统一角色数据")
    public void syncEtlRole() {
        dataSyncFacade.syncEtlRole();
    }

    @PostMapping("syncEtlUserDepartment")
    @Operation(summary = "统一人员机构数据")
    public void syncEtlUserDepartment() {

        dataSyncFacade.syncEtlUserDepartment();
    }

    @PostMapping("syncEtlUserRole")
    @Operation(summary = "统一人员角色数据")
    public void syncEtlUserRole() {
        dataSyncFacade.syncEtlUserRole();
    }

    @PostMapping("syncAll")
    @Operation(summary = "中间表数据同步")
    public void syncAll() {
        dataSyncFacade.syncAll();
    }

}
