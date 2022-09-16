package com.realfinance.sofa.cg.sup.facade;

import com.realfinance.sofa.cg.sup.model.CgAttachmentDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectDetailsDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectDto;
import com.realfinance.sofa.cg.sup.model.CgBusinessProjectQueryCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

public interface CgBusinessProjectFacade {

    Page<CgBusinessProjectDto> list(CgBusinessProjectQueryCriteria queryCriteria, Pageable pageable);

    List<CgBusinessProjectDto> list(CgBusinessProjectQueryCriteria queryCriteria);

    /**
     * 获取详情，包括子表数据
     * 该方法可以使用数据权限
     * @param id 供应商ID
     * @return
     */
    CgBusinessProjectDetailsDto getDetailsById(@NotNull Integer id);

    /**
     * 新增或保存
     * @param projectId
     * @param projectNo
     * @param projectName
     * @return
     */
    Integer save(String projectId, String projectNo, String projectName);

    Integer save(CgBusinessProjectDetailsDto businessProject, List<CgAttachmentDto> dtoList);

    /**
     * 更新项目状态
     * @param projectId
     * @param projectStatus
     */
    void updateProjectStatus(String projectId, String projectStatus);

    void delete(Set<String> projectIds);
}
