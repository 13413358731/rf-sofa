package com.realfinance.sofa.flow.flowable.diagram;

import org.flowable.bpmn.model.BpmnModel;
import org.flowable.image.ProcessDiagramGenerator;

import java.io.InputStream;
import java.util.List;

public interface CustomProcessDiagramGenerator extends ProcessDiagramGenerator {

    InputStream generateDiagram(BpmnModel bpmnModel, String imageType, List<String> highLightedActivities, List<String> highLightedFlows, List<String> redHighLightedActivities,
                                String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader, double scaleFactor, boolean drawSequenceFlowNameWithNoLabelDI);

}
