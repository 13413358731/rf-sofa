package com.realfinance.sofa.system.facade;

/**
 *统一门户:人员、机构、角色、人员机构关系以及人员角色关系同步
 */
public interface DataSyncFacade {

    //统一人员(中间表)
    void syncEtlUser();

    //统一角色(中间表)
    void syncEtlRole();

    //统一机构(中间表)
    void syncEtlDepartment();

    //统一人员机构(中间表)
    void syncEtlUserDepartment();

    //统一人员角色(中间表)
    void syncEtlUserRole();

    //中间表数据同步
    void syncAll();

    void text();
}
