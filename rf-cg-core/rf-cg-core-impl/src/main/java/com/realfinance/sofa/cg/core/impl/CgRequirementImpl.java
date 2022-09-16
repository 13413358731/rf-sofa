package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.*;
import com.realfinance.sofa.cg.core.domain.proj.*;
import com.realfinance.sofa.cg.core.domain.req.*;
import com.realfinance.sofa.cg.core.domain.serialno.SerialNumberRecordId;
import com.realfinance.sofa.cg.core.facade.CgRequirementFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.*;
import com.realfinance.sofa.cg.core.service.mapstruct.*;
import com.realfinance.sofa.cg.core.service.serialno.SerialNumberService;
import com.realfinance.sofa.cg.core.util.QueryCriteriaUtils;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import com.realfinance.sofa.sdebank.SdebankEquityPenetration;
import com.realfinance.sofa.sdebank.model.ElementBusIInfoDto;
import com.realfinance.sofa.sdebank.model.EquityPenetrationDto;
import com.realfinance.sofa.sdebank.model.SupplierRelationshipDto;
import com.realfinance.sofa.sdebank.response.TokenResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.core.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgRequirementFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgRequirementImpl implements CgRequirementFacade {

    private static final Logger log = LoggerFactory.getLogger(CgRequirementImpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final RequirementRepository requirementRepository;
    private final HistoricRequirementRepository historicRequirementRepository;
    private final ProjectRepository projectRepository;
    private final RequirementMapper requirementMapper;
    private final RequirementDetailsMapper requirementDetailsMapper;
    private final RequirementDetailsSaveMapper requirementDetailsSaveMapper;
    private final HistoricRequirementDetailsMapper historicRequirementDetailsMapper;
    private final RequirementRelationshipRepository requirementRelationshipRepository;
    private final RequirementSupRepository requirementSupRepository;
    private final RequirementOaDatumRepository requirementOaDatumRepository;
    private final RequirementRelationshipMapper relationshipMapper;
    private final RequirementSupMapper requirementSupMapper;
    private final RequirementOaDatumMapper requirementOaDatumMapper;
    private final SerialNumberService serialNumberService;
    private final PurchaseCatalogRepository purchaseCatalogRepository;

    public CgRequirementImpl(JpaQueryHelper jpaQueryHelper,
                             RequirementRepository requirementRepository,
                             HistoricRequirementRepository historicRequirementRepository,
                             ProjectRepository projectRepository,
                             RequirementMapper requirementMapper,
                             RequirementDetailsMapper requirementDetailsMapper,
                             RequirementDetailsSaveMapper requirementDetailsSaveMapper,
                             HistoricRequirementDetailsMapper historicRequirementDetailsMapper, RequirementRelationshipRepository requirementRelationshipRepository, RequirementSupRepository requirementSupRepository, RequirementOaDatumRepository requirementOaDatumRepository, RequirementRelationshipMapper relationshipMapper, RequirementSupMapper requirementSupMapper, RequirementOaDatumMapper requirementOaDatumMapper, SerialNumberService serialNumberService, PurchaseCatalogRepository purchaseCatalogRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.requirementRepository = requirementRepository;
        this.historicRequirementRepository = historicRequirementRepository;
        this.projectRepository = projectRepository;
        this.requirementMapper = requirementMapper;
        this.requirementDetailsMapper = requirementDetailsMapper;
        this.requirementDetailsSaveMapper = requirementDetailsSaveMapper;
        this.historicRequirementDetailsMapper = historicRequirementDetailsMapper;
        this.requirementRelationshipRepository = requirementRelationshipRepository;
        this.requirementSupRepository = requirementSupRepository;
        this.requirementOaDatumRepository = requirementOaDatumRepository;
        this.relationshipMapper = relationshipMapper;
        this.requirementSupMapper = requirementSupMapper;
        this.requirementOaDatumMapper = requirementOaDatumMapper;
        this.serialNumberService = serialNumberService;
        this.purchaseCatalogRepository = purchaseCatalogRepository;
    }

    @Override
    public Page<CgRequirementDto> list(CgRequirementQueryCriteria queryCriteria, Pageable pageable) {
        Objects.requireNonNull(pageable);
        Page<Requirement> result = requirementRepository.findAll(QueryCriteriaUtils.toSpecification(queryCriteria)
                .and(jpaQueryHelper.dataRuleSpecification()), pageable);
        return result.map(requirementMapper::toDto);
    }

    @Override
    public CgRequirementDto getById(Integer id) {
        Objects.requireNonNull(id);
        return requirementMapper.toDto(getRequirement(id));
    }

    @Override
    public CgRequirementDetailsDto getDetailsById(Integer id) {
        Objects.requireNonNull(id);
         CgRequirementDetailsDto requirementDetailsDto = requirementDetailsMapper.toDto(getRequirement(id));

        return requirementDetailsDto;
    }

    @Override
    public List<CgRequirementDetailsDto> listHistoricDetailsById(Integer id, Boolean all) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(all);
        Requirement requirement = getRequirement(id);
        Sort sort = Sort.by(Sort.Direction.DESC, "createdTime");
        List<CgRequirementDetailsDto> result = new ArrayList<>();
        result.add(requirementDetailsMapper.toDto(getRequirement(id)));
        if (!all) {
            Page<HistoricRequirement> page = historicRequirementRepository
                    .findByRequirement(requirement, PageRequest.of(0, 1, sort));
            if (!page.isEmpty()) {
                result.addAll(page.map(historicRequirementDetailsMapper::toDto).getContent());
            }
        } else {
            List<HistoricRequirement> allHistoricRequirement = historicRequirementRepository.findByRequirement(requirement, sort);
            if (!allHistoricRequirement.isEmpty()) {
                result.addAll(allHistoricRequirement.stream().map(historicRequirementDetailsMapper::toDto).collect(Collectors.toList()));
            }
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgRequirementDetailsSaveDto saveDto) {
        //默认采购部门(前端不传该值) 计财部采购管理中心
        saveDto.setPurDepartmentId(484);
//        saveDto.setPurDepartmentId(3);
        Objects.requireNonNull(saveDto);
        Requirement requirement;
        if (saveDto.getId() == null) { // 新增
            requirement = requirementDetailsSaveMapper.toEntity(saveDto);
            requirement.setTenantId(DataScopeUtils.loadTenantId());
            requirement.setAcceptStatus(Requirement.AcceptStatus.CS);
            requirement.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
            requirement.setStatus(FlowStatus.EDIT);
        } else { // 修改
            //查看供应商关联表 的上 供应商与修改采购申请后的供应商是否一致 一致则不变,不一致则清空该采购申请的供应商管理关系
            Optional<Requirement> byId = requirementRepository.findById(saveDto.getId());
            //修改前的数据
            List<RequirementSup> sups = requirementSupRepository.findByRequirement(byId.get());
            List<RequirementRelationship> byRelationship = requirementRelationshipRepository.findByRequirement(byId.get());
            //修改前有供应商数据且条数与修改后不一致,则清空关联关系表
            if (byRelationship.size() != 0) {
                if (sups.size() != 0 && sups.size() != saveDto.getRequirementSups().size()) {
                    requirementRelationshipRepository.deleteByRequirement(byId.get());
                }
                if (sups.size() == saveDto.getRequirementSups().size()) {
                    List<Integer> saveId = saveDto.getRequirementSups().stream().map(CgRequirementSupDto::getSupplierId).collect(Collectors.toList());
                    List<Integer> supsId = sups.stream().map(RequirementSup::getSupplierId).collect(Collectors.toUnmodifiableList());
                    for (Integer id : saveId) {
                        boolean contains = supsId.contains(id);
                        if (!contains) {
                            requirementRelationshipRepository.deleteByRequirement(byId.get());
                        }
                    }
                }
            }
            Requirement entity = getRequirement(saveDto.getId());
            requirement = requirementDetailsSaveMapper.updateEntity(entity, saveDto);
        }
        List<CgAttSaveDto> atts = saveDto.getRequirementAtts();
        for (CgAttSaveDto dto : atts) {
            if (dto.getName() == null) {
                throw businessException("附件不能为空！");
            }
        }
        preSave(requirement);
        try {
            Requirement saved = requirementRepository.saveAndFlush(requirement);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignOperator(Integer id, Integer userId, String comment) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(userId);

        Requirement requirement = getRequirement(id);
        System.out.println("requirement=" + requirement);

        if (requirement.getAcceptStatus() != Requirement.AcceptStatus.DCL) {
            throw businessException("需求申请处于非待处理状态");
        }

        requirement.setOperatorUserId(userId);
        if (StringUtils.isNotBlank(comment)) {
            requirement.setComment(StringUtils.join(requirement.getComment(), comment, "；"));
        }
        try {
            requirementRepository.saveAndFlush(requirement);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("指派经办人失败", e);
            }
            throw businessException("指派经办人失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAcceptStatus(Integer id, String acceptStatus, String reason) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(acceptStatus);
        Requirement.AcceptStatus acceptStatusEnum = Enum.valueOf(Requirement.AcceptStatus.class, acceptStatus);
        Requirement requirement = getRequirement(id);
        Requirement.AcceptStatus currentAcceptStatusEnum = requirement.getAcceptStatus();
        if (acceptStatusEnum == currentAcceptStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复受理状态更新，id：{}，acceptStatus：{}",
                        id, acceptStatus);
            }
            return;
        }
        requirement.setAcceptStatus(acceptStatusEnum);
        if (StringUtils.isNotBlank(reason)) {
            requirement.setReason(StringUtils.join(requirement.getReason(), reason, ";"));
        }
        if (acceptStatusEnum == Requirement.AcceptStatus.TG) {
            handleAccept(requirement);
        } else if (acceptStatusEnum == Requirement.AcceptStatus.TH) {
            // 退回，需要重新走审批流
            requirement.setStatus(FlowStatus.EDIT);
        } else if (acceptStatusEnum == Requirement.AcceptStatus.FAZXTH) {
            requirement.setStatus(FlowStatus.EDIT);
        }
        try {
            requirementRepository.saveAndFlush(requirement);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败", e);
            }
            throw businessException("更新状态失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Integer id, String status) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);

        Requirement requirement = requirementRepository.findById(id)
                .orElseThrow(() -> entityNotFound(Requirement.class, "id", id));

        FlowStatus currentStatusEnum = requirement.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id, status);
            }
            return;
        }
        requirement.setStatus(statusEnum);
        if (statusEnum == PASS) {
            handlePass(requirement);
        }
        try {
            requirementRepository.saveAndFlush(requirement);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败", e);
            }
            throw businessException("更新状态失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Integer> ids) {
        Objects.requireNonNull(ids);
        if (ids.isEmpty()) {
            return;
        }
        // 启用数据权限查询
        List<Requirement> toDelete = requirementRepository.findAll(
                ((Specification<Requirement>) (root, query, criteriaBuilder) ->
                        root.get("id").in(ids))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (toDelete.isEmpty()) {
            throw dataAccessForbidden();
        }
        // 检查是否可删除
        toDelete.forEach(e -> {
            if (e.getStatus() != FlowStatus.EDIT) {
                throw businessException("流程已启动，不能删除");
            }
        });
        // 删除
        try {
            requirementRepository.deleteAll(toDelete);
            requirementRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除失败", e);
            }
            throw businessException("删除失败");
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CgRequirementRelationshipDto> relationship(Integer id, List<CgRequirementRelationshipDto> list) {
        Optional<Requirement> byId = requirementRepository.findById(id);
        //查询子表(供应商关联关系表是否存有上次关联数据)
        List<RequirementRelationship> byRequirement = requirementRelationshipRepository.findByRequirement(byId.get());
        if (byRequirement.size() != 0) {
            requirementRelationshipRepository.deleteByRequirement(byId.get());
        }
        SdebankEquityPenetration sd = new SdebankEquityPenetration();
        TokenResponse token = sd.getToken();
        List<EquityPenetrationDto> dtoList = new ArrayList<>();
        List<ElementBusIInfoDto> elementBusIInfoDtos = new ArrayList<>();
        list.forEach(e -> {
            EquityPenetrationDto dto = new EquityPenetrationDto();
            dto.setName(e.getName());
            if (e.getUnifiedSocialCreditCode() != null && e.getUnifiedSocialCreditCode() != "") {
                dto.setUnifiedSocialCreditCode(e.getUnifiedSocialCreditCode());
            }
            dtoList.add(dto);
        });
        dtoList.forEach(e -> {
            ElementBusIInfoDto elementBusIInfoDto = sd.elementBusIInfo(token, e);
            elementBusIInfoDtos.add(elementBusIInfoDto);
        });
        //筛选出 满足1-5条件的供应商并保存到数据库中
        List<RequirementRelationship> saveList = new ArrayList<>();
        //关联序号
        Integer number = 1;
        List<List<ElementBusIInfoDto>> lists = new ArrayList<>();
        if (elementBusIInfoDtos.size() > 2) {
            lists = getLists(elementBusIInfoDtos);
        } else {
            lists.add(elementBusIInfoDtos);
        }
        for (List<ElementBusIInfoDto> busIInfoDtos : lists) {
            SupplierRelationshipDto dto = sd.SupplierRelationship(token, busIInfoDtos);
            if (dto.getType() != null) {
                for (ElementBusIInfoDto elementBusIInfoDto : dto.getList()) {
                    RequirementRelationship relationship = getRelationship(elementBusIInfoDto);
                    relationship.setType(dto.getType());
                    relationship.setRequirement(byId.get());
                    relationship.setNumber(number);
                    if (dto.getProportion() != null) {
                        relationship.setProportion(dto.getProportion());
                    }
                    saveList.add(relationship);
                }
                number++;
            }
        }
        if (saveList.size() == 0) {
            return null;
        }
        List<RequirementRelationship> requirementRelationships = requirementRelationshipRepository.saveAll(saveList);
        List<CgRequirementRelationshipDto> returnList = relationshipMapper.toDtoList(requirementRelationships);
        return returnList;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CgRequirementSupDto> updateRequirementSupRelatedStatus(List<CgRequirementSupDto> dtos) {
        List<Integer> ids = dtos.stream().map(CgRequirementSupDto::getId).collect(Collectors.toList());
        Map<Integer, CgRequirementSupDto> map = dtos.stream().collect(HashMap::new, (m, v) -> m.put(v.getId(), v), HashMap::putAll);
        List<RequirementSup> allById = requirementSupRepository.findAllById(ids);
        for (RequirementSup requirementSup : allById) {
            requirementSup.setSupplierRelatedStatus(map.get(requirementSup.getId()).getSupplierRelatedStatus());
        }
        //修改采购申请下子表供应商的数据
        List<RequirementSup> sups = requirementSupRepository.saveAll(allById);
        List<CgRequirementSupDto> supDtos = requirementSupMapper.toDtoList(sups);
        return supDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CgRequirementSupDto> updateRequirementSupCreditStatus(List<CgRequirementSupDto> dtos) {
        List<Integer> ids = dtos.stream().map(CgRequirementSupDto::getId).collect(Collectors.toList());
        Map<Integer, CgRequirementSupDto> map = dtos.stream().collect(HashMap::new, (m, v) -> m.put(v.getId(), v), HashMap::putAll);
        List<RequirementSup> allById = requirementSupRepository.findAllById(ids);
        for (RequirementSup requirementSup : allById) {
            requirementSup.setSupplierCreditStatus(map.get(requirementSup.getId()).getSupplierCreditStatus());
            requirementSup.setSupplierCreditTime(LocalDateTime.now());

        }
        //修改采购申请下子表供应商的数据
        List<RequirementSup> sups = requirementSupRepository.saveAll(allById);
        List<CgRequirementSupDto> supDtos = requirementSupMapper.toDtoList(sups);
        return supDtos;
    }

    @Override
    public List<CgRequirementOaDatumDto> findOaDatumList(List<String> approvalNoList) {
        List<RequirementOaDatum> requirementOaDatumList = requirementOaDatumRepository.findByApprovalNoIn(approvalNoList);
        List<RequirementOaDatum> list = requirementOaDatumList.stream().filter(e -> e.getRequirement().getStatus() != FlowStatus.EDIT).collect(Collectors.toList());

        return requirementOaDatumMapper.toDtoList(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveList(List<CgRequirementItemImportDto> list, Integer id) {
        Optional<Requirement> byId = requirementRepository.findById(id);
        Requirement requirement = byId.get();
        if (!requirement.getStatus().equals(FlowStatus.EDIT)) {
            throw new RuntimeException("当前状态为非编辑状态,无法导入!");
        }
        List<String> purchaseCatalogNames = list.stream().map(e -> e.getPurchaseCatalogName()).distinct().collect(Collectors.toList());
        List<PurchaseCatalog> purchaseCatalogs = purchaseCatalogRepository.findByNameIn(purchaseCatalogNames);
        Map<String, PurchaseCatalog> map = purchaseCatalogs.stream().collect(Collectors.toMap(e -> e.getName(), e -> e));
        List<RequirementItem> requirementItems = requirement.getRequirementItems();
        for (CgRequirementItemImportDto dto : list) {
            RequirementItem requirementItem = mapper(dto);
            requirementItem.setRequirement(requirement);
            requirementItem.setPurchaseCatalog(map.get(dto.getPurchaseCatalogName()));
            requirementItems.add(requirementItem);
        }
        try {
            requirementRepository.saveAndFlush(requirement);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("格式错误", e);
            }
            throw businessException("格式错误", e);
        }
        return requirementItems.size();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveAmount(Integer id) {
        Requirement requirement = requirementRepository.findById(id).get();
        //清空 已占用金额和可使用金额
        List<RequirementOaDatum> list = requirement.getRequirementOaData();
        for (RequirementOaDatum requirementOaDatum : list) {
            requirementOaDatum.setUsedAmount(null);
            requirementOaDatum.setRemainAmount(null);
        }
        try {
            Requirement saved = requirementRepository.saveAndFlush(requirement);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败");
        }
    }

    protected RequirementItem mapper(CgRequirementItemImportDto dto) {
        RequirementItem requirementItem = new RequirementItem();
        requirementItem.setName(dto.getName());
        requirementItem.setModel(dto.getModel());
        requirementItem.setNumber(dto.getNumber());
        requirementItem.setMarketPrice(dto.getMarketPrice());
        requirementItem.setUnit(dto.getUnit());
        requirementItem.setSource("REQUIREMENT");
        requirementItem.setQualityRequirements(dto.getQualityRequirements());
        if (dto.getNeedSample() != null) {
            requirementItem.setNeedSample(dto.getNeedSample().equals("是") ? true : false);
        }
        return requirementItem;
    }


    /**
     * 供应商 匹配方法
     *
     * @param list
     * @return
     */
    protected List<List<ElementBusIInfoDto>> getLists(List<ElementBusIInfoDto> list) {
        List<List<ElementBusIInfoDto>> result = new ArrayList<>();  //用来存放子集的集合，如{{},{1},{2},{1,2}}
        int length = list.size();
        int num = length == 0 ? 0 : 1 << (length);  //2的n次方，若集合set为空，num为0；若集合set有4个元素，那么num为16.

        //从0到2^n-1（[00...00]到[11...11]）
        for (int i = 0; i < num; i++) {
            List<ElementBusIInfoDto> subSet = new ArrayList<>();
            int index = i;
            for (int j = 0; j < length; j++) {
                if ((index & 1) == 1) {    //每次判断index最低位是否为1，为1则把集合set的第j个元素放到子集中
                    subSet.add(list.get(j));
                }
                index >>= 1;    //右移一位
            }
            if (subSet.size() == 2) {
                result.add(subSet);    //把子集存储起来
            }
        }
        return result;
    }

    /**
     * 供应商关联关系表
     * 部分字段获取
     *
     * @param dto 比较出供应商有关联的数据
     * @return
     */
    protected RequirementRelationship getRelationship(ElementBusIInfoDto dto) {
        RequirementRelationship relationship = new RequirementRelationship();
        relationship.setName(dto.getName());
        relationship.setUnifiedSocialCreditCode(dto.getUnifiedSocialCreditCode());
        relationship.setStatutoryRepresentative(dto.getStatutoryRepresentative());
        relationship.setShareholderNames(getString(dto.getShareholderNames()));
        relationship.setShareholderTypes(getString(dto.getShareholderTypes()));
        relationship.setEquityRatio(getString(dto.getEquityRatio()));
        return relationship;
    }


    /**
     * list集合转String
     *
     * @param list
     * @return
     */
    protected String getString(Collection list) {
        if (list == null || list.size() == 0) {
            return null;
        }
        String str = StringUtils.substringBeforeLast(
                StringUtils.substringAfterLast(list.toString(), "["), "]");
        return str;
    }


    /**
     * 保存前处理
     *
     * @param requirement
     */
    protected void preSave(Requirement requirement) {
        if (!requirement.getAccept()) {
            if (requirement.getStatus() == PASS) {
                throw businessException("流程已通过，不能修改");
            }
        }
        if (requirement.getHasBond() == null || !requirement.getHasBond()) {
            requirement.setHasBond(false);
            requirement.setBidBond(null);
            requirement.setPerformanceBond(null);
            requirement.setPerformanceYears(null);
            requirement.setWarrantyBond(null);
            requirement.setWarrantyYears(null);
        } else {
            // 判断3种不能同时为null
            if (requirement.getBidBond() == null
                    || requirement.getPerformanceBond() == null
                    || requirement.getWarrantyBond() == null) {
                throw businessException("保证金输入不正确");
            }
            // 判断金额和年份必须同时为null 或者 同时不为null
            if ((requirement.getPerformanceBond() == null && requirement.getPerformanceYears() != null)
                    || (requirement.getPerformanceBond() != null && requirement.getPerformanceYears() == null)
                    || (requirement.getWarrantyBond() == null && requirement.getWarrantyYears() != null)
                    || (requirement.getWarrantyBond() != null && requirement.getWarrantyYears() == null)) {
                throw businessException("保证金输入不正确");
            }
        }

        if (requirement.getRequirementSups().stream().map(BasePurSup::getSupplierId)
                .collect(Collectors.toSet()).size() != requirement.getRequirementSups().size()) {
            throw businessException("包含重复的推荐供应商");
        }
        // 计算总市场价
//        requirement.resetMarketPrice();
        // TODO: 2021/1/8 计划内的处理，校验
    }

    /**
     * 受理
     *
     * @param requirement
     */
    protected void handleAccept(Requirement requirement) {
        if (log.isInfoEnabled()) {
            log.info("需求受理，ID：{}", requirement.getId());
        }
        if (requirement.getOperatorUserId() == null) {
            throw businessException("未指派经办人");
        }
        if (requirement.getStatus() != PASS) {
            throw businessException("采购需求单未进行审批");
        }
        generateProject(requirement);
    }

    /**
     * 通过处理
     *
     * @param requirement
     */
    protected void handlePass(Requirement requirement) {
        if (log.isInfoEnabled()) {
            log.info("需求审批通过，ID：{}", requirement.getId());
        }
        Requirement.AcceptStatus acceptStatus = requirement.getAcceptStatus();
        if (acceptStatus == Requirement.AcceptStatus.CS) {
            requirement.setAcceptStatus(Requirement.AcceptStatus.DCL);
        } else if (acceptStatus == Requirement.AcceptStatus.TH) {
            requirement.setAcceptStatus(Requirement.AcceptStatus.DCL);
        } else if (acceptStatus == BaseRequirement.AcceptStatus.FATH) {
            requirement.setAcceptStatus(Requirement.AcceptStatus.TG);
            updateProject(requirement);
        } else if (acceptStatus == BaseRequirement.AcceptStatus.FAZXTH) {
            requirement.setAcceptStatus(Requirement.AcceptStatus.TG);
            updateProject(requirement);
        }
        saveHistory(requirement);
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected Requirement getRequirement(Integer id) {
        Objects.requireNonNull(id);
        List<Requirement> all = requirementRepository.findAll(
                ((Specification<Requirement>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!requirementRepository.existsById(id)) {
                throw entityNotFound(Requirement.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据需求申请生成采购方案
     *
     * @param requirement
     */
    protected void generateProject(Requirement requirement) {
        Project project = new Project();
        project.setTenantId(DataScopeUtils.loadTenantId());
        project.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
        project.setReqDepartmentId(requirement.getDepartmentId());
        project.setReqUserId(requirement.getCreatedUserId());
        project.setAcceptTime(LocalDateTime.now());
        project.setStatus(FlowStatus.EDIT);
        project.setRequirement(requirement);
        project.setPurchasePlan(requirement.getPurchasePlan());
        project.setReqCreatedTime(requirement.getCreatedTime());
        project.setApprovalAmount(requirement.getApprovalAmount());
        project.setContractValidity(requirement.getContractValidity());
        project.setSingleReason(requirement.getSingleReason());
        project.setSupRequirements(requirement.getSupRequirements());
        project.setContractCreatedUserId(requirement.getContractCreatedUserId());
        project.setUseDepartmentIds(requirement.getUseDepartmentIds());

        updateProject(project, requirement);
        for (RequirementItem requirementItem : requirement.getRequirementItems()) {
            ProjectItem projectItem = createProjectItem(requirementItem);
            project.getProjectItems().add(projectItem);
        }
        for (RequirementSup requirementSup : requirement.getRequirementSups()) {
            ProjectSup projectSup = createProjectSup(requirementSup);
            project.getProjectSups().add(projectSup);
        }
        for (RequirementOaDatum requirementOaDatum : requirement.getRequirementOaData()) {
            ProjectOaDatum projectOaDatum = createProjectOaDatum(requirementOaDatum);
            project.getProjectOaData().add(projectOaDatum);
        }
        for (RequirementAtt requirementAtt : requirement.getRequirementAtts()) {
            ProjectAtt projectAtt = createProjectAtt(requirementAtt);
            project.getProjectAtts().add(projectAtt);
        }
//        project.resetMarketPrice();
        project.setMarketPrice(requirement.getMarketPrice());
        project.setPurModeReason(requirement.getPurModeReason());
        project.setProjectCategory(requirement.getProjectCategory());
        project.setEvalMethodReason(requirement.getEvalMethodReason());
        project.setPurType(requirement.getPurType());
        //生成项目编码
        generateProjectNo(project);
        try {
            projectRepository.saveAndFlush(project);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("生成采购方案异常", e);
            }
            throw businessException("生成采购方案失败");
        }
        requirement.setProjectNo(project.getProjectNo());
    }

    /**
     * 保存历史记录
     *
     * @param requirement
     */
    private void saveHistory(Requirement requirement) {
        try {
            // 添加到历史
            HistoricRequirement historicRequirement = historicRequirementDetailsMapper.fromRequirement(requirement);
            historicRequirement.setRequirement(requirement);
            historicRequirementRepository.saveAndFlush(historicRequirement);
        } catch (Exception e) {
            throw businessException("保存到历史表失败");
        }
    }

    /**
     * 根据采购需求更新采购方案
     *
     * @param requirement
     */
    protected void updateProject(Requirement requirement) {
        if (log.isTraceEnabled()) {
            log.trace("更新采购方案，需求申请ID：{}", requirement.getId());
        }
        Project project = projectRepository.findByRequirement(requirement)
                .orElseThrow(() -> entityNotFound(Project.class, "requirementId", requirement.getId()));
        // 处理主表
        updateProject(project, requirement);

        // 处理清单
        List<ProjectItem> projectItems = project.getProjectItems();
        List<RequirementItem> requirementItems = requirement.getRequirementItems();
        Set<Integer> requirementItemIdSet = requirementItems.stream().map(BasePurItem::getId).collect(Collectors.toSet());
        projectItems.removeIf(e -> e.getReqItemId() != null && !requirementItemIdSet.contains(e.getReqItemId()));
        item:
        for (RequirementItem requirementItem : requirementItems) {
            for (ProjectItem projectItem : projectItems) {
                if (Objects.equals(requirementItem.getId(), projectItem.getReqItemId())) {
                    updateProjectItem(projectItem, requirementItem);
                    break item;
                }
            }
            ProjectItem projectItem = createProjectItem(requirementItem);
            projectItems.add(projectItem);
        }

        // 处理推荐供应商
        List<ProjectSup> projectSups = project.getProjectSups();
        List<RequirementSup> requirementSups = requirement.getRequirementSups();
        Set<Integer> requirementSupIdSet = requirementSups.stream().map(BasePurSup::getId).collect(Collectors.toSet());
        projectSups.removeIf(e -> e.getReqSupId() != null && requirementSupIdSet.contains(e.getReqSupId()));
        sup:
        for (RequirementSup requirementSup : requirementSups) {
            for (ProjectSup projectSup : projectSups) {
                if (Objects.equals(requirementSup.getId(), projectSup.getReqSupId())) {
                    updateProjectSup(projectSup, requirementSup);
                    break sup;
                }
            }
            ProjectSup projectItemSup = createProjectSup(requirementSup);
            projectSups.add(projectItemSup);
        }

        // 处理附件
        List<ProjectAtt> projectAtts = project.getProjectAtts();
        projectAtts.removeIf(e -> e.getReqAttId() != null);
        for (RequirementAtt requirementAtt : requirement.getRequirementAtts()) {
            ProjectAtt projectAtt = createProjectAtt(requirementAtt);
            project.getProjectAtts().add(projectAtt);
        }
        // 处理OA数据表
        project.getProjectOaData().clear();
        for (RequirementOaDatum requirementOaDatum : requirement.getRequirementOaData()) {
            ProjectOaDatum projectOaDatum = createProjectOaDatum(requirementOaDatum);
            project.getProjectOaData().add(projectOaDatum);
        }
        //处理供应商关联关系表
        project.getRelationships().clear();
        if (requirement.getRelationships().size() != 0) {
            for (RequirementRelationship relationship : requirement.getRelationships()) {
                ProjectRelationship projectRelationship = createProjectRelationship(relationship);
                project.getRelationships().add(projectRelationship);
            }
        }
        // 重新计算市场总价格
        project.resetMarketPrice();
    }

    private ProjectItem createProjectItem(RequirementItem requirementItem) {
        ProjectItem projectItem = new ProjectItem();
        projectItem.setReqItemId(requirementItem.getId());
        projectItem.setSource(requirementItem.getSource());
        projectItem.setModel(requirementItem.getModel());
        projectItem.setUnit(requirementItem.getUnit());
        updateProjectItem(projectItem, requirementItem);
        return projectItem;
    }

    private ProjectSup createProjectSup(RequirementSup requirementSup) {
        ProjectSup projectItemSup = new ProjectSup();
        projectItemSup.setReqSupId(requirementSup.getId());
        projectItemSup.setSource(requirementSup.getSource());
        projectItemSup.setSupplierCreditStatus(requirementSup.getSupplierCreditStatus());
        projectItemSup.setSupplierCreditTime(requirementSup.getSupplierCreditTime());
        projectItemSup.setSupplierRelatedStatus(requirementSup.getSupplierRelatedStatus());
        updateProjectSup(projectItemSup, requirementSup);
        return projectItemSup;
    }

    private ProjectOaDatum createProjectOaDatum(RequirementOaDatum requirementOaDatum) {
        ProjectOaDatum projectOaDatum = new ProjectOaDatum();
        projectOaDatum.setApprovalNo(requirementOaDatum.getApprovalNo());
        projectOaDatum.setApprovalTitle(requirementOaDatum.getApprovalTitle());
        projectOaDatum.setApprovalAmount(requirementOaDatum.getApprovalAmount());
        projectOaDatum.setThisPurAmount(requirementOaDatum.getThisPurAmount());
        projectOaDatum.setUsedAmount(requirementOaDatum.getUsedAmount());
        projectOaDatum.setRemainAmount(requirementOaDatum.getRemainAmount());
        return projectOaDatum;
    }

    private ProjectAtt createProjectAtt(RequirementAtt requirementAtt) {
        ProjectAtt projectAtt = new ProjectAtt();
        projectAtt.setReqAttId(requirementAtt.getId());
        projectAtt.setName(requirementAtt.getName());
        projectAtt.setSource(requirementAtt.getSource());
        projectAtt.setSize(requirementAtt.getSize());
        projectAtt.setExt(requirementAtt.getExt());
        projectAtt.setPath(requirementAtt.getPath());
        projectAtt.setUploadTime(requirementAtt.getUploadTime());
        projectAtt.setUploader(requirementAtt.getCreatedUserId());
        return projectAtt;
    }

    private ProjectRelationship createProjectRelationship(RequirementRelationship requirementRelationship) {
        ProjectRelationship projectRelationship = new ProjectRelationship();
        projectRelationship.setNumber(requirementRelationship.getNumber());
        projectRelationship.setType(requirementRelationship.getType());
        projectRelationship.setProportion(requirementRelationship.getProportion());
        projectRelationship.setName(requirementRelationship.getName());
        projectRelationship.setUnifiedSocialCreditCode(requirementRelationship.getUnifiedSocialCreditCode());
        projectRelationship.setStatutoryRepresentative(requirementRelationship.getStatutoryRepresentative());
        projectRelationship.setShareholderNames(requirementRelationship.getShareholderNames());
        projectRelationship.setShareholderTypes(requirementRelationship.getShareholderTypes());
        projectRelationship.setEquityRatio(requirementRelationship.getEquityRatio());
        return projectRelationship;
    }

    private ProjectItem updateProjectItem(ProjectItem projectItem, RequirementItem requirementItem) {
        projectItem.setPurchaseCatalog(requirementItem.getPurchaseCatalog());
        projectItem.setName(requirementItem.getName());
        projectItem.setNumber(requirementItem.getNumber());
        projectItem.setMarketPrice(requirementItem.getMarketPrice());
        projectItem.setQualityRequirements(requirementItem.getQualityRequirements());
        projectItem.setNote(requirementItem.getNote());
        projectItem.setNeedSample(requirementItem.getNeedSample());
        return projectItem;
    }

    private void updateProjectSup(ProjectSup projectSup, RequirementSup requirementSup) {
        projectSup.setReqSupId(requirementSup.getId());
        projectSup.setSupplierId(requirementSup.getSupplierId());
        projectSup.setContactName(requirementSup.getContactName());
        projectSup.setContactMobile(requirementSup.getContactMobile());
        projectSup.setContactEmail(requirementSup.getContactEmail());
        projectSup.setReason(requirementSup.getReason());
        projectSup.setNote(requirementSup.getNote());
    }

    private void updateProject(Project project, Requirement requirement) {
        project.setName(requirement.getName());
        project.setReqTotalAmount(requirement.getReqTotalAmount());
        if (requirement.getNumberOfWinSup() == null) {
            project.setNumberOfWinSup(1);
        } else {
            project.setNumberOfWinSup(requirement.getNumberOfWinSup());
        }
        project.setPurMode(requirement.getPurMode());
        project.setProjFeatures(requirement.getProjFeatures());
        project.setEvalMethod(requirement.getSugEvalMethod());
        project.setReqUserPhone(requirement.getReqUserPhone());
        project.setPurType(requirement.getPurType());
        project.setContractCategory(requirement.getContractCategory());
        project.setPurCategory(requirement.getPurCategory());
    }

    /**
     * 生成项目编码
     * @param project
     */
    protected void generateProjectNo(Project project) {
        ProjectCategory projectCategory = project.getProjectCategory();
        if (log.isDebugEnabled()) {
            log.debug("生成采购方案项目编码，项目类别：{}", projectCategory);
        }
        if (projectCategory == null) {
            // 忽略
        } else {
            String no = serialNumberService.next(SerialNumberRecordId
                            .of("PROJECT", DataScopeUtils.loadTenantId()),
                    Collections.singletonMap("projectCategory", projectCategory.getCode()));
            project.setProjectNo("SDG" + projectCategory.getCode() + no);
        }
    }

}
