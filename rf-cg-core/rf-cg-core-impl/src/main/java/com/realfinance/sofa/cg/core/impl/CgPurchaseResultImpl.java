package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.*;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.cg.core.domain.purresult.*;
import com.realfinance.sofa.cg.core.facade.CgPurchaseResultFacade;
import com.realfinance.sofa.cg.core.model.*;
import com.realfinance.sofa.cg.core.repository.*;
import com.realfinance.sofa.cg.core.service.mapstruct.*;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
import com.realfinance.sofa.sdebank.SdebankEquityPenetration;
import com.realfinance.sofa.sdebank.SdebankSDNSPaperless;
import com.realfinance.sofa.sdebank.model.ElementBusIInfoDto;
import com.realfinance.sofa.sdebank.model.EquityPenetrationDto;
import com.realfinance.sofa.sdebank.model.NoteDto;
import com.realfinance.sofa.sdebank.model.SupplierRelationshipDto;
import com.realfinance.sofa.sdebank.response.TokenResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.realfinance.sofa.cg.core.domain.FlowStatus.PASS;
import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;

@Service
@SofaService(interfaceType = CgPurchaseResultFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgPurchaseResultImpl implements CgPurchaseResultFacade {

    private static final Logger log = LoggerFactory.getLogger(CgPurchaseResultImpl.class);
    private final JpaQueryHelper jpaQueryHelper;
    private final PurchaseResultRepository purchaseResultRepository;
    private final PurchaseResultMapper purchaseResultMapper;
    private final PurchaseResultDetailsSaveMapper purchaseResultDetailsSaveMapper;
    private final PurchaseResultDetailsMapper purchaseResultDetailsMapper;
    private final ExpertRepository expertRepository;
    private final PurchaseResultRelationshipRepository purchaseResultRelationshipRepository;
    private final PurchaseResultRelationshipMapper relationshipMapper;
    private final PurResultSupRepository purResultSupRepository;
    private final PurResultSupMapper purResultSupMapper;
    private final PurchaseResultNoticeRepository purchaseResultNoticeRepository;
    private final ProjectRepository projectRepository;
    private final DrawExpertListRepository drawExpertListRepository;

    public CgPurchaseResultImpl(JpaQueryHelper jpaQueryHelper, PurchaseResultRepository purchaseResultRepository, PurchaseResultMapper purchaseResultMapper, PurchaseResultDetailsSaveMapper purchaseResultDetailsSaveMapper, PurchaseResultDetailsMapper purchaseResultDetailsMapper, ExpertRepository expertRepository, PurchaseResultRelationshipRepository purchaseResultRelationshipRepository, PurchaseResultRelationshipMapper relationshipMapper, PurResultSupRepository purResultSupRepository, PurResultSupMapper purResultSupMapper, PurchaseResultNoticeRepository purchaseResultNoticeRepository, ProjectRepository projectRepository, DrawExpertListRepository drawExpertListRepository) {
        this.jpaQueryHelper = jpaQueryHelper;
        this.purchaseResultRepository = purchaseResultRepository;
        this.purchaseResultMapper = purchaseResultMapper;
        this.purchaseResultDetailsSaveMapper = purchaseResultDetailsSaveMapper;
        this.purchaseResultDetailsMapper = purchaseResultDetailsMapper;
        this.expertRepository = expertRepository;
        this.purchaseResultRelationshipRepository = purchaseResultRelationshipRepository;
        this.relationshipMapper = relationshipMapper;
        this.purResultSupRepository = purResultSupRepository;
        this.purResultSupMapper = purResultSupMapper;
        this.purchaseResultNoticeRepository=purchaseResultNoticeRepository;
        this.projectRepository = projectRepository;
        this.drawExpertListRepository = drawExpertListRepository;
    }

    @Override
    public Page<CgPurchaseResultDto> list(CgPurchaseResultQueryCriteria queryCriteria, @NotNull Pageable pageable) {
        return purchaseResultRepository.findAll(new Specification<PurchaseResult>() {
            @Override
            public Predicate toPredicate(Root<PurchaseResult> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                if (queryCriteria == null) {
                    return null;
                }
                List<Predicate> predicates = new ArrayList<>();
                if (StringUtils.isNotBlank(queryCriteria.getName())) {
                    predicates.add(criteriaBuilder.like(root.get("name"), "%" + queryCriteria.getName() + "%"));
                }
                if (StringUtils.isNotBlank(queryCriteria.getProjectNo())) {
                    predicates.add(criteriaBuilder.like(root.get("projectNo"), "%" + queryCriteria.getProjectNo() + "%"));
                }
                return criteriaQuery.where(predicates.toArray(Predicate[]::new)).getRestriction();
            }
        }, pageable).map(purchaseResultMapper::toDto);
    }

    @Override
    public CgPurchaseResultDetailsDto getById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return purchaseResultDetailsMapper.toDto(getPurchaseResult(id));
    }


/*    @Override
    public CgProjectExecutionDto getById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        return projectExecutionMapper.toDto(getProjectExecution(id));
    }*/

    @Override
    public CgPurchaseResultDetailsDto getDetailsById(@NotNull Integer id) {
        Objects.requireNonNull(id);
        CgPurchaseResultDetailsDto cgPurchaseResultDetailsDto = purchaseResultDetailsMapper.toDto(getPurchaseResult(id));
        return cgPurchaseResultDetailsDto;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer generateResult(Integer projectExeId, CgProjectDetailsDto projectDetails, List<CgMeetingConfereeDto> conferees, List<CgProjectExecutionSupDto> projectExecutionSups) {
        Objects.requireNonNull(projectDetails);
        PurchaseResult purchaseResult = new PurchaseResult();
        List<CgProjectItemDto> projectItems = projectDetails.getProjectItems();

        purchaseResult.setTenantId(DataScopeUtils.loadTenantId());
        purchaseResult.setDepartmentId(DataScopeUtils.loadDepartmentId().orElse(null));
        purchaseResult.setProjectId(projectDetails.getId());
        purchaseResult.setProjectexeId(projectExeId);
        purchaseResult.setProjectNo(projectDetails.getProjectNo());
        purchaseResult.setName(projectDetails.getName());
        purchaseResult.setStatus(FlowStatus.EDIT);
        purchaseResult.setPurMode(PurchaseMode.valueOf(projectDetails.getPurMode()));
        purchaseResult.setEvalMethod(EvalMethod.valueOf(projectDetails.getEvalMethod()));
        purchaseResult.setPurType(PurchaseType.valueOf(projectDetails.getPurType()));
        purchaseResult.setValid(false);

        List<PurResultExpert> purResultExperts = new ArrayList<>();
        List<PurResultSupplier> purResultSups = new ArrayList<>();
        List<PurResultConfirmDet> purResultConfirmDets = new ArrayList<>();

        for (CgMeetingConfereeDto conferee : conferees) {
            PurResultExpert purResultExpert = new PurResultExpert();
            Integer expertId = conferee.getExpertId();
            Expert expert = getExpert(expertId);
            purResultExpert.setDrawWay(DrawExpertWay.valueOf(conferee.getDrawWay()));
            purResultExpert.setName(expert.getName());
            purResultExpert.setExpertDepartment(expert.getExpertDepartment());
            purResultExperts.add(purResultExpert);
        }
        purchaseResult.setPurResultExperts(purResultExperts);

        for (CgProjectExecutionSupDto projectExecutionSup : projectExecutionSups) {
            PurResultSupplier purResultSupplier = new PurResultSupplier();
            purResultSupplier.setSupplierId(projectExecutionSup.getSupplierId());
            purResultSupplier.setSupplierCreditTime(LocalDateTime.now());
            purResultSups.add(purResultSupplier);
        }
        purchaseResult.setPurResultSups(purResultSups);

        for (CgProjectItemDto projectItem : projectItems) {
            PurResultConfirmDet purResultConfirmDet = new PurResultConfirmDet();
            purResultConfirmDet.setPurchaseCatalog(projectItem.getPurchaseCatalog().getId());
            purResultConfirmDet.setName(projectItem.getName());
            purResultConfirmDet.setNumber(projectItem.getNumber());
            purResultConfirmDet.setMarketPrice(projectItem.getMarketPrice());
            purResultConfirmDet.setQualityRequirements(projectItem.getQualityRequirements());
            purResultConfirmDet.setNote(projectItem.getNote());
            purResultConfirmDets.add(purResultConfirmDet);
        }
        purchaseResult.setPurResultConfirmDets(purResultConfirmDets);

        Integer resultId = 0;
        if(!purchaseResultRepository.existsByTenantIdAndProjectexeId(purchaseResult.getTenantId(),purchaseResult.getProjectexeId())){
            try {
                PurchaseResult result = purchaseResultRepository.saveAndFlush(purchaseResult);
                resultId = result.getId();
            } catch (Exception e) {
                log.error("保存失败", e);
                throw businessException("保存失败");
            }
        }else {
            PurchaseResult purchaseResultId = getPurchaseResultByExecId(projectExeId);
            resultId = purchaseResultId.getId();
        }
       return resultId;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgPurchaseResultDetailsDto saveDto) {
        Objects.requireNonNull(saveDto);
        PurchaseResult purchaseResult;
        List<CgPurResultAttDto> purResultAtts = saveDto.getPurResultAtts();
        for (CgPurResultAttDto dto : purResultAtts) {
            if (dto.getSource() == null) {
                throw businessException("附件不能为空！");
            }
        }
        if (saveDto.getId() == null) { // 新增
            throw businessException("不允许新增");
        } else { // 修改
            PurchaseResult entity = getPurchaseResult(saveDto.getId());
            purchaseResult = purchaseResultDetailsSaveMapper.updateEntity(entity, saveDto);
        }
        try {
            PurchaseResult saved = purchaseResultRepository.saveAndFlush(purchaseResult);
            return saved.getId();
        } catch (Exception e) {
            log.error("保存失败", e);
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(@NotNull Integer id, @NotNull String status,String reqUserName,List<String> list) {
        Objects.requireNonNull(id);
        Objects.requireNonNull(status);
        FlowStatus statusEnum = Enum.valueOf(FlowStatus.class, status);

        PurchaseResult purchaseResult = purchaseResultRepository.findById(id)
                .orElseThrow(() -> entityNotFound(Expert.class, "id", id));

        FlowStatus currentStatusEnum = purchaseResult.getStatus();
        if (statusEnum == currentStatusEnum) {
            if (log.isWarnEnabled()) {
                log.warn("重复状态更新，id：{}，status：{}",
                        id, status);
            }
            return;
        }
        purchaseResult.setStatus(statusEnum);
        if (statusEnum == PASS) {
            handlePass(purchaseResult,reqUserName,list);
        }
        try {
            purchaseResultRepository.saveAndFlush(purchaseResult);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("更新状态失败", e);
            }
            throw businessException("更新状态失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CgPurchaseResultRelationshipDto> relationship(Integer id, List<CgPurchaseResultRelationshipDto> list) {
        Optional<PurchaseResult> byId = purchaseResultRepository.findById(id);
        //查询子表(供应商关联关系表是否存有上次关联数据)
        List<PurResultRelationship> byProject = purchaseResultRelationshipRepository.findByPurchaseResult(byId.get());
        if (byProject.size() != 0) {
            purchaseResultRelationshipRepository.deleteByPurchaseResult(byId.get());
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
        List<PurResultRelationship> saveList = new ArrayList<>();
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
                    PurResultRelationship purResultRelationship = getRelationship(elementBusIInfoDto);
                    purResultRelationship.setType(dto.getType());
                    purResultRelationship.setPurchaseResult(byId.get());
                    purResultRelationship.setNumber(number);
                    if (dto.getProportion() != null) {
                        purResultRelationship.setProportion(dto.getProportion());
                    }
                    saveList.add(purResultRelationship);
                }
                number++;
            }
        }
        if (saveList.size() == 0) {
            return null;
        }
        List<PurResultRelationship> projectRelationships = purchaseResultRelationshipRepository.saveAll(saveList);
        List<CgPurchaseResultRelationshipDto> returnList = relationshipMapper.toDtoList(projectRelationships);
        return returnList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CgPurResultSupDto> updateProjectSupRelatedStatus(List<CgPurResultSupDto> dtos) {
        List<Integer> ids=dtos.stream().map(CgPurResultSupDto::getId).collect(Collectors.toList());
        Map<Integer,CgPurResultSupDto> map=dtos.stream().collect(HashMap::new,(m,v)->m.put(v.getId(),v),HashMap::putAll);
        List<PurResultSupplier> allById = purResultSupRepository.findAllById(ids);
        if (allById.size()==0){
            throw new RuntimeException("传入数据异常!");
        }
        for (PurResultSupplier purResultSupplier : allById) {
            purResultSupplier.setSupplierRelatedStatus(map.get(purResultSupplier.getId()).getSupplierRelatedStatus());
        }
        //修改采购申请下子表供应商的数据
        List<PurResultSupplier> sups = purResultSupRepository.saveAll(allById);
        List<CgPurResultSupDto> supDtos = purResultSupMapper.toDtoList(sups);
        return supDtos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CgPurResultSupDto> updateProjectSupCreditStatus(List<CgPurResultSupDto> dtos) {
        List<Integer> ids=dtos.stream().map(CgPurResultSupDto::getId).collect(Collectors.toList());
        Map<Integer,CgPurResultSupDto> map=dtos.stream().collect(HashMap::new,(m,v)->m.put(v.getId(),v),HashMap::putAll);
        List<PurResultSupplier> allById = purResultSupRepository.findAllById(ids);
        for (PurResultSupplier purResultSupplier : allById) {
            purResultSupplier.setSupplierCreditStatus(map.get(purResultSupplier.getId()).getSupplierCreditStatus());
            purResultSupplier.setSupplierCreditTime(LocalDateTime.now());

        }
        //修改采购申请下子表供应商的数据
        List<PurResultSupplier> sups = purResultSupRepository.saveAll(allById);
        List<CgPurResultSupDto> supDtos = purResultSupMapper.toDtoList(sups);
        return supDtos;
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
    protected PurResultRelationship getRelationship(ElementBusIInfoDto dto) {
        PurResultRelationship relationship = new PurResultRelationship();
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
     * @param list
     * @return
     */
    protected String getString(Collection list){
        if (list==null || list.size()==0){
            return null;
        }
        String str = StringUtils.substringBeforeLast(
                StringUtils.substringAfterLast(list.toString(), "["), "]");
        return str;
    }


    /**
     * 通过处理
     *
     * @param purchaseResult
     * @param reqUserName 申请人真实名称
     * @param list 供应商名称集合
     */
    @Transactional
    protected void handlePass(PurchaseResult purchaseResult,String reqUserName,List<String> list) {
        if (log.isInfoEnabled()) {
            log.info("执行供应商黑名单通过处理，黑名单ID：{}", purchaseResult.getId());
        }
        purchaseResult.setPassTime(LocalDateTime.now());
        purchaseResult.setValid(true);
        PurchaseResultNotice notice = new PurchaseResultNotice();
        List<PurchaseResultNoticeSup> purchaseResultNoticeSups = new ArrayList<>();
        notice.setCreatedTime(LocalDateTime.now());
        notice.setModifiedTime(LocalDateTime.now());
        notice.setCreatedUserId(purchaseResult.getCreatedUserId());
        notice.setModifiedUserId(purchaseResult.getModifiedUserId());
        notice.setProjectId(purchaseResult.getProjectId());
        notice.setProjectExecutionId(purchaseResult.getCreatedUserId());
        notice.setProjectNo(purchaseResult.getProjectNo());
        notice.setName(purchaseResult.getName());
        notice.setProjectProducerId(purchaseResult.getCreatedUserId());
        notice.setEvalMethod(purchaseResult.getEvalMethod());
        notice.setPurMode(purchaseResult.getPurMode());
        notice.setTenantId(purchaseResult.getTenantId());
        Project project = projectRepository.findById(purchaseResult.getProjectId()).get();
        //放入方案的合同制单日人
        notice.setContractProducerId(project.getContractCreatedUserId());
        SdebankSDNSPaperless sd = new SdebankSDNSPaperless();
        NoteDto noteDto = new NoteDto();
        SimpleDateFormat s = new SimpleDateFormat("yyyy年MM月dd日");
        String time=s.format(new Date());
        noteDto.setProjectName(purchaseResult.getName());
        noteDto.setProjectNo(purchaseResult.getProjectNo());
        noteDto.setSupplierNames(list);
        noteDto.setRealName(reqUserName);
        noteDto.setMobile(project.getReqUserPhone());
        noteDto.setTime(time);
        String note = sd.note(noteDto);
        //生成内外部标题
        notice.setOutsideTitle("采购结果通知书("+project.getName()+")");
        notice.setInsideTitle("采购结果通知书("+project.getName()+")");
        //生成通知书
        notice.setInsideContent(note);
        notice.setOutsideContent(note);
        List<PurResultSupplier> purResultSups = purchaseResult.getPurResultSups();
        for (PurResultSupplier purResultSup : purResultSups) {
            PurchaseResultNoticeSup sup=this.createPurchaseResultNoticeSup(purResultSup);
            purchaseResultNoticeSups.add(sup);
        }
        notice.setResultNoticeSups(purchaseResultNoticeSups);
        notice.setStatus(FlowStatus.EDIT);
        try {
            purchaseResultNoticeRepository.saveAndFlush(notice);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("失败", e);
            }
            throw businessException("失败", e);
        }
    }

    protected PurchaseResultNoticeSup createPurchaseResultNoticeSup(PurResultSupplier purResultSup){
        PurchaseResultNoticeSup sup=new PurchaseResultNoticeSup();
        sup.setSupplierId(purResultSup.getSupplierId());
        sup.setSupplierName(purResultSup.getName());
        if(purResultSup.getRecommend()!=null){
            sup.setBid(purResultSup.getRecommend());
        }else{
            sup.setBid(false);
        }
        return sup;
    }

    protected PurchaseResult getPurchaseResult(Integer id) {
        Objects.requireNonNull(id);
        List<PurchaseResult> all = purchaseResultRepository.findAll(
                ((Specification<PurchaseResult>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!purchaseResultRepository.existsById(id)) {
                throw entityNotFound(ProjectExecution.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    protected PurchaseResult getPurchaseResultByExecId(Integer id) {
        Objects.requireNonNull(id);
        List<PurchaseResult> all = purchaseResultRepository.findAll(
                ((Specification<PurchaseResult>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("projectexeId"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!purchaseResultRepository.existsById(id)) {
                throw entityNotFound(ProjectExecution.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected Expert getExpert(Integer id) {
        Objects.requireNonNull(id);
        List<Expert> all = expertRepository.findAll(
                ((Specification<Expert>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!expertRepository.existsById(id)) {
                System.out.println("找不到相应专家");
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

}
