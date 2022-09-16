package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.cg.core.domain.ProductLibrary;
import com.realfinance.sofa.cg.core.domain.PurchaseCatalog;
import com.realfinance.sofa.cg.core.domain.serialno.SerialNumberRecordId;
import com.realfinance.sofa.cg.core.facade.CgProductLibraryFacade;
import com.realfinance.sofa.cg.core.model.CgProductSaveDto;
import com.realfinance.sofa.cg.core.model.ProductLibraryDto;
import com.realfinance.sofa.cg.core.model.ProductLibraryQueryCriteria;
import com.realfinance.sofa.cg.core.repository.ProductLibraryRepository;
import com.realfinance.sofa.cg.core.repository.PurchaseCatalogRepository;
import com.realfinance.sofa.cg.core.service.mapstruct.ProductLibraryMapper;
import com.realfinance.sofa.cg.core.service.mapstruct.ProductLibrarySaveMapper;
import com.realfinance.sofa.cg.core.service.serialno.SerialNumberService;
import com.realfinance.sofa.cg.model.cg.CgProductLibraryImportDto;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.datascope.JpaQueryHelper;
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
import java.util.*;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.*;
import static com.realfinance.sofa.cg.core.util.QueryCriteriaUtils.toSpecification;


@Service
@SofaService(interfaceType = CgProductLibraryFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
//@Transactional(readOnly = false)
public class CgProductLibrarylmpl implements CgProductLibraryFacade {


    private static final Logger log = LoggerFactory.getLogger(CgProductLibrarylmpl.class);

    private final JpaQueryHelper jpaQueryHelper;
    private final ProductLibraryRepository productLibraryRepository;
    private final ProductLibrarySaveMapper productLibrarySaveMapper;
    private final ProductLibraryMapper productLibraryMapper;
    private final PurchaseCatalogRepository purchaseCatalogRepository;
    private final SerialNumberService serialNumberService;


    public CgProductLibrarylmpl(JpaQueryHelper jpaQueryHelper,
                                ProductLibraryRepository productLibraryRepository,
                                ProductLibrarySaveMapper productLibrarySaveMapper,
                                ProductLibraryMapper productLibraryMapper, PurchaseCatalogRepository purchaseCatalogRepository, SerialNumberService serialNumberService) {

        this.jpaQueryHelper = jpaQueryHelper;
        this.productLibraryRepository = productLibraryRepository;
        this.productLibrarySaveMapper = productLibrarySaveMapper;
        this.productLibraryMapper = productLibraryMapper;
        this.purchaseCatalogRepository = purchaseCatalogRepository;
        this.serialNumberService = serialNumberService;
    }


    /**
     * 保存
     *
     * @param saveDto
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer save(CgProductSaveDto saveDto) {
        Objects.requireNonNull(saveDto);
        ProductLibrary productLibrary;
        if (saveDto.getId() == null) { // 新增
            productLibrary = productLibrarySaveMapper.toEntity(saveDto);
            productLibrary.setTenantId(DataScopeUtils.loadTenantId());
            productLibrary.setDepartmentId(DataScopeUtils.loadDepartmentId().get());
            //生成产品编码
            generateProductEncoded(productLibrary);
        } else {
            ProductLibrary entity = getProductLibrary(saveDto.getId());
            productLibrary = productLibrarySaveMapper.updateEntity(entity, saveDto);
        }
        try {
            ProductLibrary saved = productLibraryRepository.saveAndFlush(productLibrary);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败");
        }
    }

    @Override
    public Page<ProductLibraryDto> list(Pageable pageable, ProductLibraryQueryCriteria queryCriteria) {
        Page<ProductLibraryDto> purchaseCatalog = productLibraryRepository.findAll(new Specification<ProductLibrary>() {
            @Override
            public Predicate toPredicate(Root<ProductLibrary> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                if (queryCriteria == null) {
                    return null;
                }
                List<Predicate> predicates = new ArrayList<>();
                if (queryCriteria.getPurchaseCatalogId() != null) {
                    predicates.add(criteriaBuilder.equal(root.get("purchaseCatalog"), queryCriteria.getPurchaseCatalogId()));
                }
                return query.where(predicates.toArray(Predicate[]::new)).getRestriction();
            }
        }, pageable).map(productLibraryMapper::toDto);
        return purchaseCatalog;
    }

    @Override
    public List<ProductLibraryDto> getList(ProductLibraryQueryCriteria queryCriteria) {
        List<ProductLibrary> result = productLibraryRepository.findAll(toSpecification(queryCriteria).and(jpaQueryHelper.dataRuleSpecification()));
        return productLibraryMapper.toDtoList(result);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(@NotNull Set<Integer> ids) {
        Objects.requireNonNull(ids);
        for (Integer id : ids) {
            productLibraryRepository.deleteById(id);
        }
    }

    @Override
    public Integer saveList(List<CgProductLibraryImportDto> list, Integer purchaseCatalogId) {
        ProductLibrary productLibrary;
        for(CgProductLibraryImportDto importDto:list){
            importDto.setPurchaseCatalog(purchaseCatalogId);
            //importDto.setProject(projectId);
            if(importDto.getId()==null){
                CgProductSaveDto saveDto = mapper(importDto);
                if("是".equals(importDto.getBid())&&importDto.getBid()!=null){
                    saveDto.setBid(true);
                }else{
                    saveDto.setBid(false);
                }
                productLibrary=productLibrarySaveMapper.toEntity(saveDto);
                productLibrary.setTenantId(DataScopeUtils.loadTenantId());
                productLibrary.setDepartmentId(DataScopeUtils.loadDepartmentId().get());
                try{
                    ProductLibrary saved = productLibraryRepository.saveAndFlush(productLibrary);
                    return saved.getId();
                }catch (Exception e){
                    if (log.isErrorEnabled()) {
                        log.error("保存失败", e);
                    }
                    throw businessException("保存失败");
                }
            }
        }
        return list.size();
    }

    /**
     * 根据ID获取实体
     * 校验数据权限
     *
     * @param id
     * @return
     */
    protected ProductLibrary getProductLibrary(Integer id) {
        Objects.requireNonNull(id);
        List<ProductLibrary> all = productLibraryRepository.findAll(
                ((Specification<ProductLibrary>) (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("id"), id))
                        .and(jpaQueryHelper.dataRuleSpecification()));
        if (all.isEmpty()) {
            if (!productLibraryRepository.existsById(id)) {
                throw entityNotFound(ProductLibrary.class, "id", id);
            }
            throw dataAccessForbidden();
        }
        return all.get(0);
    }

    protected CgProductSaveDto mapper(CgProductLibraryImportDto importDto){
        CgProductSaveDto saveDto=new CgProductSaveDto();

        saveDto.setProductEncoded(importDto.getProductEncoded());
        saveDto.setProductName(importDto.getProductName());
        saveDto.setPurchaseCatalog(importDto.getPurchaseCatalog());
        saveDto.setProductDescribe(importDto.getProductDescribe());
        saveDto.setModel(importDto.getModel());
        saveDto.setCalculateUnit(importDto.getCalculateUnit());
        saveDto.setProductCode(importDto.getProductCode());
        saveDto.setEnglishName(importDto.getEnglishName());
        saveDto.setProject(importDto.getProject());
        saveDto.setBid(importDto.getBid());
        saveDto.setEnterRepositoryTime(importDto.getEnterRepositoryTime());
        saveDto.setSupplier(importDto.getSupplier());
        saveDto.setPrice(importDto.getPrice());

        return saveDto;
    }

    /**
     * 生成产品编码
     *
     * @param productLibrary
     */
    protected void generateProductEncoded(ProductLibrary productLibrary) {
        if (productLibrary.getPurchaseCatalog()==null){
            throw new RuntimeException("采购目录不能为空!");
        }
        PurchaseCatalog projectCategory = purchaseCatalogRepository.findById(productLibrary.getPurchaseCatalog()).get();
        if (log.isDebugEnabled()) {
            log.debug("生成产品编码，项目类别：{}", projectCategory);
        }
        if (projectCategory == null) {
            // 忽略
        } else {
            String no = serialNumberService.next(SerialNumberRecordId
                            .of("PRODUCTLIB", DataScopeUtils.loadTenantId()),
                    Collections.singletonMap("projectCategory", projectCategory.getCode()));
            productLibrary.setProductEncoded(projectCategory.getCode() + no);
        }
    }
}
