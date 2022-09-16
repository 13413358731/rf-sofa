package com.realfinance.sofa.cg.controller.cg.sup;


import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;

import com.realfinance.sofa.cg.model.system.AnnouncementChannelQueryCriteriaRequest;
import com.realfinance.sofa.cg.model.system.AnnouncementChannelVo;
import com.realfinance.sofa.cg.service.mapstruct.AnnouncementChannelMapper;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAnnouncementChannelFacade;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementChannelDto;
import com.realfinance.sofa.cg.sup.model.SupplierAnnouncementChannelSaveDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

@RestController
@RequestMapping("/cg/sup/channel")
@Tag(name = "公告频道")
public class AnnouncementChannelController {

    private static final Logger log = LoggerFactory.getLogger(AnnouncementChannelController.class);

    //TODO  不知是否需要设置权限
    public static final String MENU_CODE_ROOT = "announcementchannel";
    public static final String MENU_CODE_VIEW = MENU_CODE_ROOT + ":view";
    public static final String MENU_CODE_SAVE = MENU_CODE_ROOT + ":save";
    public static final String MENU_CODE_DELETE = MENU_CODE_ROOT + ":delete";

    @SofaReference(interfaceType = CgSupplierAnnouncementChannelFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierAnnouncementChannelFacade channelFacade;
    @Resource
    private AnnouncementChannelMapper channelMapper;

    @GetMapping("/list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.AnnouncementChannelController).MENU_CODE_VIEW)")
    public ResponseEntity<Page<AnnouncementChannelVo>> list(Pageable pageable, AnnouncementChannelQueryCriteriaRequest request) {
        Page<SupplierAnnouncementChannelDto> list = channelFacade.list(pageable, request);
        Page<AnnouncementChannelVo> map = list.map(channelMapper::announcementChannelDto2AnnouncementChannelVo);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/get")
    @Operation(summary = "详情")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.AnnouncementChannelController).MENU_CODE_VIEW)")
    public ResponseEntity<AnnouncementChannelVo> getone(@Parameter(description = "频道ID") @RequestParam Integer id) {
        SupplierAnnouncementChannelDto one = channelFacade.getOne(id);
        AnnouncementChannelVo channelVo = channelMapper.announcementChannelDto2AnnouncementChannelVo(one);
        return ResponseEntity.ok(channelVo);
    }

    @DeleteMapping("/deleate")
    @Operation(summary = "删除")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.AnnouncementChannelController).MENU_CODE_DELETE)")
    public ResponseEntity<?> deleate(@Parameter(description = "频道ID") @RequestParam Set<Integer> ids) {
        channelFacade.deleate(ids);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/save")
    @Operation(summary = "保存")
    @PreAuthorize("hasAuthority(T(com.realfinance.sofa.cg.controller.cg.sup.AnnouncementChannelController).MENU_CODE_SAVE)")
    public ResponseEntity<Integer> save(@RequestBody SupplierAnnouncementChannelSaveDto saveDto) {
        Integer save = channelFacade.save(saveDto);
        return ResponseEntity.ok(save);
    }

}
