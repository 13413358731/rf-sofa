package com.realfinance.sofa.flow.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.flow.domain.BizModel;
import com.realfinance.sofa.flow.facade.ProcessDefFacade;
import com.realfinance.sofa.flow.model.BizDto;
import com.realfinance.sofa.flow.model.BizModelDto;
import com.realfinance.sofa.flow.model.BizModelSaveDto;
import com.realfinance.sofa.flow.model.ProcessDefinitionDto;
import com.realfinance.sofa.flow.repository.BizModelRepository;
import com.realfinance.sofa.flow.repository.BizRepository;
import com.realfinance.sofa.flow.service.mapstruct.BizMapper;
import com.realfinance.sofa.flow.service.mapstruct.BizModelMapper;
import com.realfinance.sofa.flow.service.mapstruct.BizModelSaveMapper;
import com.realfinance.sofa.flow.util.ExceptionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.model.ModelKeyRepresentation;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.realfinance.sofa.flow.util.ExceptionUtils.businessException;
import static com.realfinance.sofa.flow.util.ExceptionUtils.entityNotFound;

@Service
@SofaService(interfaceType = ProcessDefFacade.class, uniqueId = "${service.rf-flow.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class ProcessDefImpl implements ProcessDefFacade {

    private static final Logger log = LoggerFactory.getLogger(ProcessDefImpl.class);

    private final BizRepository bizRepository;
    private final BizModelRepository bizModelRepository;
    private final BizMapper bizMapper;
    private final BizModelMapper bizModelMapper;
    private final BizModelSaveMapper bizModelSaveMapper;
    private final ModelService modelService;
    private final RepositoryService repositoryService;

    public ProcessDefImpl(BizRepository bizRepository,
                          BizModelRepository bizModelRepository,
                          BizMapper bizMapper,
                          BizModelMapper bizModelMapper,
                          BizModelSaveMapper bizModelSaveMapper,
                          ModelService modelService,
                          RepositoryService repositoryService) {
        this.bizRepository = bizRepository;
        this.bizModelRepository = bizModelRepository;
        this.bizMapper = bizMapper;
        this.bizModelMapper = bizModelMapper;
        this.bizModelSaveMapper = bizModelSaveMapper;
        this.modelService = modelService;
        this.repositoryService = repositoryService;
    }

    @Override
    public Page<BizDto> queryBizRefer(String filter, Pageable pageable) {
        if (StringUtils.isEmpty(filter)) {
            return bizRepository.findAll(pageable).map(bizMapper::toDto);
        } else {
            return bizRepository.findByFilter("%" + filter + "%",pageable)
                    .map(bizMapper::toDto);
        }
    }

    @Override
    public Page<BizModelDto> listBizModelByDepartmentId(Integer departmentId, Pageable pageable) {
        Page<BizModel> bizModels = bizModelRepository.findByDepartmentId(departmentId, pageable);
        return bizModels.map(bizModelMapper::toDto);
    }

    @Override
    public Page<ProcessDefinitionDto> listProcessDefinitionByBizModelId(Integer bizModelId, Pageable pageable) {
        BizModel bizModel = bizModelRepository.findById(bizModelId)
                .orElseThrow(() -> entityNotFound(BizModel.class, "id", bizModelId));
        ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(bizModel.getModelKey())
                .orderByProcessDefinitionVersion().desc();
        long count = query.count();
        if (count > 0) {
            List<ProcessDefinition> result = query.listPage((int) pageable.getOffset(), pageable.getPageSize());
            List<ProcessDefinitionDto> content = result.stream().map(e -> {
                ProcessDefinitionDto processDefinitionDto = new ProcessDefinitionDto();
                processDefinitionDto.setId(e.getId());
                processDefinitionDto.setName(e.getName());
                processDefinitionDto.setKey(e.getKey());
                processDefinitionDto.setVersion(e.getVersion());
                processDefinitionDto.setSuspended(e.isSuspended());
                processDefinitionDto.setDeploymentId(e.getDeploymentId());
                return processDefinitionDto;
            }).collect(Collectors.toList());
            return new PageImpl<>(content,pageable,count);
        } else {
            return Page.empty(pageable);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveBizModel(BizModelSaveDto saveDto) {
        BizModel bizModel;
        if (saveDto.getId() == null) { // 新增
            bizModel = bizModelSaveMapper.toEntity(saveDto);
            if (bizModel.getBiz() == null) {
                throw businessException("缺少biz属性");
            }
            if (StringUtils.isBlank(bizModel.getProcessInstName())) {
                bizModel.setProcessInstName(bizModel.getBiz().getName());
            }
            bizModel.setTenantId(DataScopeUtils.loadTenantId());
            // createModel
            Model model = createModel(bizModel.getModelKey(), bizModel.getBiz().getName(), bizModel.getBiz().getNote());
            bizModel.setModelId(model.getId());
        } else { // 修改
            bizModel = bizModelRepository.findById(saveDto.getId())
                    .orElseThrow(() -> entityNotFound(BizModel.class,"id",saveDto.getId()));
            bizModelSaveMapper.updateEntity(bizModel,saveDto);
        }
        try {
            BizModel saved = bizModelRepository.saveAndFlush(bizModel);
            return saved.getId();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存业务模型失败",e);
            }
            throw businessException("保存失败");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void activate(String processDefinitionId, Boolean activateProcessInstances) {
        try {
            repositoryService.activateProcessDefinitionById(processDefinitionId,activateProcessInstances,null);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("激活失败，processDefinitionId：{}，suspendProcessInstances：{}",
                        processDefinitionId,activateProcessInstances);
                log.error("",e);
            }
            throw businessException("激活失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void suspend(String processDefinitionId, Boolean suspendProcessInstances) {
        try {
            repositoryService.suspendProcessDefinitionById(processDefinitionId,suspendProcessInstances,null);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("挂起失败，processDefinitionId：{}，suspendProcessInstances：{}",
                        processDefinitionId,suspendProcessInstances);
                log.error("",e);
            }
            throw businessException("挂起失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLastDeploy(Integer bizModelId) {
        BizModel bizModel = bizModelRepository.findById(bizModelId)
                .orElseThrow(() -> entityNotFound(BizModel.class, "id", bizModelId));
        String deploymentId = bizModel.getDeploymentId();
        if (StringUtils.isEmpty(deploymentId)) {
            return;
        }
        try {
            repositoryService.deleteDeployment(deploymentId,true);
            resetDeploymentId(bizModel);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除最后一次部署失败，deploymentId：{}", deploymentId);
                log.error("",e);
            }
            throw businessException("删除最后一次部署失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBizModel(@NotNull Set<Integer> bizModelIds) {
        try {
            List<BizModel> toDelete = bizModelRepository.findAllById(bizModelIds);
            bizModelRepository.deleteAll(toDelete);
            // 把模型也删了
            for (BizModel bizModel : toDelete) {
                modelService.deleteModel(bizModel.getModelId());
            }
            bizModelRepository.flush();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("删除业务模型失败，bizModelIds：{}", bizModelIds);
                log.error("",e);
            }
            throw businessException("删除业务模型失败：" + e.getMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deploy(Integer bizModelId) {
        BizModel bizModel = bizModelRepository.findById(bizModelId)
                .orElseThrow(() -> entityNotFound(BizModel.class,"id",bizModelId));
        Model model = modelService.getModel(bizModel.getModelId());
        byte[] bytes = modelService.getBpmnXML(model);
        if(bytes == null){
            throw ExceptionUtils.businessException("模型数据为空");
        }
        BpmnModel bpmnModel = modelService.getBpmnModel(model);
        if(bpmnModel.getProcesses().size() == 0){
            throw ExceptionUtils.businessException("数据模型不符要求，请至少设计一条主线流程");
        }
        String processName = model.getName() + ".bpmn20.xml";
        try {
            Deployment deploy = repositoryService.createDeployment()
                    .key(model.getKey())
                    .name(model.getName())
                    .addBytes(processName, bytes)
                    .tenantId(model.getTenantId())
                    .deploy();
            bizModel.setDeploymentId(deploy.getId());
            bizModelRepository.save(bizModel);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("部署失败，bizModelId：{}", bizModelId);
                log.error("",e);
            }
            throw businessException("部署失败：" + e.getMessage());
        }
    }

    @Override
    public byte[] getModelResourceByProcessDefinitionId(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
        if (processDefinition == null) {
            throw businessException("Could not find a process definition with id '" + processDefinitionId + "'.");
        }
        InputStream imageStream = repositoryService.getProcessDiagram(processDefinition.getId());
        if (imageStream == null) {
            throw businessException("Process definition with id '" + processDefinition.getId() + "' has no image.");
        }
        try {
            return IOUtils.toByteArray(imageStream);
        } catch (IOException e) {
            throw businessException("Error reading image stream",e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteProcessDefinition(String processDefinitionId, Boolean cascade) {
        ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
        if (processDefinition == null) {
            return; // 如果找不到直接return， 或者抛出异常
        }
        if (cascade == null) {
            repositoryService.deleteDeployment(processDefinition.getDeploymentId());
        } else {
            repositoryService.deleteDeployment(processDefinition.getDeploymentId(),cascade);
        }
        Optional<BizModel> bizModel = bizModelRepository.findByDeploymentId(processDefinition.getDeploymentId());
        bizModel.ifPresent(this::resetDeploymentId);
    }

    /**
     * 创建模型
     * @param key
     * @param name
     * @param description
     * @return
     */
    protected Model createModel(String key, String name, String description) {
        try {
            ModelRepresentation modelRepresentation = new ModelRepresentation();
            modelRepresentation.setKey(key);
            modelRepresentation.setName(name);
            modelRepresentation.setDescription(description);
            modelRepresentation.setModelType(0); // bpmn

            modelRepresentation.setKey(modelRepresentation.getKey().replaceAll(" ", ""));

            ModelKeyRepresentation modelKeyInfo = modelService.validateModelKey(null, modelRepresentation.getModelType(), modelRepresentation.getKey());
            if (modelKeyInfo.isKeyAlreadyExists()) {
                throw new BadRequestException("Provided model key already exists: " + modelRepresentation.getKey());
            }

            String json = modelService.createModelJson(modelRepresentation);

            Model newModel = modelService.createModel(modelRepresentation, json, RpcUtils.requirePrincipalId().toString());

            return newModel;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("createModel:", e);
            }
            throw ExceptionUtils.businessException(e.getMessage());
        }
    }

    /**
     * 重置业务模型部署ID
     * @param bizModel
     */
    private void resetDeploymentId(BizModel bizModel) {
        Deployment deployment = repositoryService.createDeploymentQuery()
                .deploymentKey(bizModel.getModelKey())
                .latest().singleResult();
        String lastDeploymentId = deployment == null ? null : deployment.getId();
        if (!Objects.equals(bizModel.getDeploymentId(),lastDeploymentId)) {
            bizModel.setDeploymentId(lastDeploymentId);
            bizModelRepository.save(bizModel);
        }
    }

}
