package com.realfinance.sofa.cg.sup.controller;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAnnouncementChannelFacade;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAnnouncementFacade;

import com.realfinance.sofa.cg.sup.model.*;

import com.realfinance.sofa.cg.sup.service.mapstruct.SupplierAnnoucemtMapper;
import com.realfinance.sofa.cg.sup.util.LinkUtils;
import com.realfinance.sofa.common.filetoken.FileToken;
import com.realfinance.sofa.common.filetoken.FileTokens;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


@RestController
@Tag(name = "供应商门户公告")
@RequestMapping("/cg/supAnnoucemt")
public class SupplierAnnoucemtController {

    private static final Logger log = LoggerFactory.getLogger(SupplierAnnoucemtController.class);

    @SofaReference(interfaceType = CgSupplierAnnouncementFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierAnnouncementFacade announcementFacade;

    @SofaReference(interfaceType = CgSupplierAnnouncementChannelFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierAnnouncementChannelFacade channelFacade;


    @GetMapping("/getdetails")
    @Operation(summary = "门户公告详情")
    /* @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.system.AnnouncementController.MENU_CODE_VIEW ))")*/
    public ResponseEntity<SupplierAnnouncementVo> getdetails(@Parameter(description = "公告ID") @RequestParam Integer id,
                                                             @AuthenticationPrincipal Authentication authentication) {
        //dataRuleHelper.installDataRule(MENU_CODE_VIEW);
        SupplierAnnouncementDto detailsDto = announcementFacade.getdetail(id);
        SupplierAnnouncementVo announcementVo = SupplierAnnoucemtMapper.INSTANCE.toVo(detailsDto);
        if (announcementVo.getAttachments() != null) {
            for (AttachmentVo vo : announcementVo.getAttachments()) {
                FileToken fileToken = FileTokens.create(vo.getPath(), vo.getName(),null);
                vo.setLink(LinkUtils.createFileDownloadLink(fileToken));
            }
        }
        return ResponseEntity.ok(announcementVo);
    }


    @GetMapping("/listchannel")
    @Operation(summary = "门户公告频道")
    public ResponseEntity<Page<SupplierAnnouncementChannelDto>> listChannel(Pageable pageable, Authentication authentication) {
        Boolean flag;
        if (authentication == null) {
            flag = false;
        } else {
            flag = true;
        }
        Page<SupplierAnnouncementChannelDto> list = channelFacade.listquery(pageable, flag);
        return ResponseEntity.ok(list);
    }


    @GetMapping("/get")
    @Operation(summary = "查询公告频道下的公告")
    public ResponseEntity<Page<SupplierAnnouncementDto>> querylist(CgSupplierAnnouncementQueryCriteriaRequest criteriaRequest, Pageable pageable) {
        Page<SupplierAnnouncementDto> querylist = announcementFacade.querylist(criteriaRequest, pageable);
        return ResponseEntity.ok(querylist);
    }

}
