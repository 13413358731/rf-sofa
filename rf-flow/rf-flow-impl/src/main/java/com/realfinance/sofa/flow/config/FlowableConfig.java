package com.realfinance.sofa.flow.config;

import com.realfinance.sofa.flow.flowable.TenantProviderImpl;
import com.realfinance.sofa.flow.flowable.behavior.CustomActivityBehaviorFactory;
import com.realfinance.sofa.flow.flowable.diagram.CustomProcessDiagramGeneratorImpl;
import com.realfinance.sofa.flow.flowable.form.CustomFormTypes;
import com.realfinance.sofa.flow.flowable.interceptor.CustomCreateUserTaskInterceptor;
import com.realfinance.sofa.flow.flowable.listener.NextUserTaskInfoListener;
import com.realfinance.sofa.flow.flowable.service.CustomFlowableModelQueryService;
import com.realfinance.sofa.flow.flowable.service.CustomModelImageService;
import com.realfinance.sofa.flow.flowable.service.CustomModelService;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.common.engine.api.delegate.event.FlowableEventListener;
import org.flowable.engine.impl.form.*;
import org.flowable.spring.SpringProcessEngineConfiguration;
import org.flowable.spring.boot.EngineConfigurationConfigurer;
import org.flowable.ui.common.repository.UuidIdGenerator;
import org.flowable.ui.common.tenant.TenantProvider;
import org.flowable.ui.modeler.service.FlowableModelQueryService;
import org.flowable.ui.modeler.service.ModelImageService;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: 2020/12/27 集群部署时可能需要重写 org.flowable.common.engine.impl.persistence.deploy.DeploymentCache 或者禁用cache
@Configuration
@ComponentScan("org.flowable.ui.modeler")
public class FlowableConfig implements EngineConfigurationConfigurer<SpringProcessEngineConfiguration> {

    @Override
    public void configure(SpringProcessEngineConfiguration engineConfiguration) {

        engineConfiguration.setDisableIdmEngine(true);

        CustomFormTypes formTypes = getCustomFormTypes();
        engineConfiguration.setFormTypes(formTypes);
        //engineConfiguration.setDisableIdmEngine(true);
        engineConfiguration.setActivityFontName("宋体")
                .setLabelFontName("宋体")
                .setAnnotationFontName("宋体");
        engineConfiguration.setProcessDiagramGenerator(processDiagramGenerator());
        engineConfiguration
                .setActivityBehaviorFactory(new CustomActivityBehaviorFactory());

        Map<String, List<FlowableEventListener>> typedListeners = getTypedEventListeners();
        engineConfiguration.setTypedEventListeners(typedListeners);
        engineConfiguration.setCreateUserTaskInterceptor(new CustomCreateUserTaskInterceptor());
    }

    private Map<String, List<FlowableEventListener>> getTypedEventListeners() {
        Map<String, List<FlowableEventListener>> typedListeners = new HashMap<>();
        typedListeners.put(FlowableEngineEventType.ACTIVITY_STARTED.name(), Collections.singletonList(nextUserTaskInfoListener()));
        typedListeners.put(FlowableEngineEventType.MULTI_INSTANCE_ACTIVITY_STARTED.name(), Collections.singletonList(nextUserTaskInfoListener()));
        return typedListeners;
    }

    private CustomFormTypes getCustomFormTypes() {
        CustomFormTypes formTypes = new CustomFormTypes();
        formTypes.addFormType(new StringFormType());
        formTypes.addFormType(new LongFormType());
        formTypes.addFormType(new DateFormType("dd/MM/yyyy"));
        formTypes.addFormType(new BooleanFormType());
        formTypes.addFormType(new DoubleFormType());
        return formTypes;
    }

    @Bean
    public NextUserTaskInfoListener nextUserTaskInfoListener() {
        return new NextUserTaskInfoListener();
    }

    @Bean
    public ModelService modelerModelService() {
        return new CustomModelService();
    }

    @Bean
    public ModelImageService modelerModelImageService() {
        return new CustomModelImageService();
    }

    @Bean
    public FlowableModelQueryService modelerModelQueryService() {
        return new CustomFlowableModelQueryService();
    }

    @Bean
    public TenantProvider tenantProvider() {
        return new TenantProviderImpl();
    }

    @Bean
    public UuidIdGenerator uuidIdGenerator() {
        return new UuidIdGenerator();
    }

    @Bean
    public CustomProcessDiagramGeneratorImpl processDiagramGenerator() {
        return new CustomProcessDiagramGeneratorImpl();
    }
}
