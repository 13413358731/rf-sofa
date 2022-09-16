package com.realfinance.sofa.cg.core.facade;

import com.realfinance.sofa.cg.core.model.CgOAJsonDto;

import java.util.List;


public interface CgOAJsonFacade {

    /**
     * 接收OA系统传过来的附件信息
     * @param cgOAJsonDto
     * @return 返回字节流数据
     */
    byte[] getOAJson(CgOAJsonDto cgOAJsonDto);
}
