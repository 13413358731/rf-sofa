-- 租户
INSERT INTO SYSTEM_TENANT(id,name,note,start_time,end_time,enabled,v,created_user_id,created_time) VALUES
('SYSTEM_TENANT','系统','系统自带租户','2000-01-01 00:00:00','2030-01-01 00:00:00',1,0,0,'2000-01-01 00:00:00'),
('sdebank','顺德农商行','顺德农商行','2000-01-01 00:00:00','2030-01-01 00:00:00',1,0,1,'2000-01-01 00:00:00'),
('TEST_TENANT1','测试租户1','测试数据','2000-01-01 00:00:00','2010-01-01 00:00:00',1,0,1,'2000-01-01 00:00:00');

-- 部门
INSERT INTO SYSTEM_DEPARTMENT(id,parent_id,tenant_id,code,code_path,name,name_path,type,leaf_count,sort,category,enabled,v,created_user_id,created_time) VALUES
(1,null,'SYSTEM_TENANT','1','/1','研发中心','/研发中心',0,0,1,'A',1,0,0,'2000-01-01 00:00:00'),
(2,null,'sdebank','1','/1','总行','/总行',0,3,1,'A',1,0,1,'2000-01-01 00:00:00'),
(11,null,'TEST_TENANT1','1','/1','测试','/测试',0,1,1,'A',1,0,1,'2000-01-01 00:00:00'),

(3,2,'sdebank','11','/1/11','一级部门1','/总行/一级部门1',1,2,1,'A',1,0,1,'2000-01-01 00:00:00'),
(7,2,'sdebank','12','/1/12','一级部门2','/总行/一级部门2',1,2,2,'A',1,0,1,'2000-01-01 00:00:00'),
(10,2,'sdebank','13','/1/13','一级部门3','/总行/一级部门3',1,0,3,'A',1,0,1,'2000-01-01 00:00:00'),

(4,3,'sdebank','111','/1/11/111','二级部门1','/总行/一级部门1/二级部门1',1,1,1,'A',1,0,1,'2000-01-01 00:00:00'),
(6,3,'sdebank','112','/1/11/112','二级部门2','/总行/一级部门1/二级部门2',1,0,2,'A',1,0,1,'2000-01-01 00:00:00'),

(5,4,'sdebank','1111','/1/11/111/1111','三级部门1','/总行/一级部门1/二级部门1/三级部门1',1,0,1,'B',1,0,1,'2000-01-01 00:00:00'),

(8,7,'sdebank','121','/1/12/121','二级部门1','/总行/一级部门2/二级部门1',1,0,2,'C',0,0,1,'2000-01-01 00:00:00'),
(9,7,'sdebank','122','/1/12/122','二级部门2','/总行/一级部门2/二级部门2',1,0,2,'C',1,0,1,'2000-01-01 00:00:00'),

(12,11,'TEST_TENANT1','11','/1/1','测试1','/测试/测试1',1,1,1,'A',1,0,1,'2000-01-01 00:00:00'),
(13,12,'TEST_TENANT1','111','/1/1/1','测试11','/测试/测试1/测试11',1,0,1,'A',1,0,1,'2000-01-01 00:00:00');

-- 用户
INSERT INTO SYSTEM_USER(id,username,password,realname,email,mobile,classification,enabled,tenant_id,department_id,v,created_user_id,created_time) VALUES
-- 密码rf2020
(1,'realfinance','{bcrypt}$2a$10$3zPDNAO4p9qIMaT0Mq922eipn8zbR9cKmvCnY3x/kUTdFD.jOwtFm','瑞友信息技术有限公司','service@realfinance.com.cn','13539454000','1',1,'SYSTEM_TENANT',null,0,0,'2000-01-01 00:00:00'),
(2,'realfinance1','{bcrypt}$2a$10$3zPDNAO4p9qIMaT0Mq922eipn8zbR9cKmvCnY3x/kUTdFD.jOwtFm','瑞友1','service@realfinance.com.cn','13539454000','1',1,'SYSTEM_TENANT',1,0,0,'2000-01-01 00:00:00'),
(3,'realfinance2','{bcrypt}$2a$10$3zPDNAO4p9qIMaT0Mq922eipn8zbR9cKmvCnY3x/kUTdFD.jOwtFm','瑞友2','service@realfinance.com.cn','13539454000','1',0,'SYSTEM_TENANT',1,0,0,'2000-01-01 00:00:00'),
-- 密码123456
(4,'testuser1','{bcrypt}$2a$10$40cYAWCfWah8MjbGrulnMeo0dGPOB3W2SvIaRXdnk0P7WWE6rZ6KC','瑞友测试1','lurj@realfinance.com.cn','13539454000','2',1,'sdebank',2,0,1,'2020-10-06 16:29:13'),
(5,'testuser2','{bcrypt}$2a$10$40cYAWCfWah8MjbGrulnMeo0dGPOB3W2SvIaRXdnk0P7WWE6rZ6KC','瑞友测试2','lurj@realfinance.com.cn','13539454001','2',1,'sdebank',3,0,1,'2020-10-06 16:29:13'),
(6,'testuser3','{bcrypt}$2a$10$40cYAWCfWah8MjbGrulnMeo0dGPOB3W2SvIaRXdnk0P7WWE6rZ6KC','瑞友测试3','lurj@realfinance.com.cn','13539454001','2',1,'sdebank',4,0,1,'2020-10-06 16:29:13'),
(7,'testuser4','{bcrypt}$2a$10$40cYAWCfWah8MjbGrulnMeo0dGPOB3W2SvIaRXdnk0P7WWE6rZ6KC','瑞友测试4','lurj@realfinance.com.cn','13539454001','2',1,'sdebank',5,0,1,'2020-10-06 16:29:13'),
(8,'testuser5','{bcrypt}$2a$10$40cYAWCfWah8MjbGrulnMeo0dGPOB3W2SvIaRXdnk0P7WWE6rZ6KC','瑞友测试5','lurj@realfinance.com.cn','13539454001','2',1,'sdebank',6,0,1,'2020-10-06 16:29:13'),
(9,'testuser6','{bcrypt}$2a$10$40cYAWCfWah8MjbGrulnMeo0dGPOB3W2SvIaRXdnk0P7WWE6rZ6KC','瑞友测试6','lurj@realfinance.com.cn','13539454001','2',1,'sdebank',7,0,1,'2020-10-06 16:29:13'),
(10,'testuser7','{bcrypt}$2a$10$40cYAWCfWah8MjbGrulnMeo0dGPOB3W2SvIaRXdnk0P7WWE6rZ6KC','瑞友测试7','lurj@realfinance.com.cn','13539454001','2',1,'sdebank',8,0,1,'2020-10-06 16:29:13'),
(11,'testuser8','{bcrypt}$2a$10$40cYAWCfWah8MjbGrulnMeo0dGPOB3W2SvIaRXdnk0P7WWE6rZ6KC','瑞友测试8','lurj@realfinance.com.cn','13539454001','2',1,'sdebank',9,0,1,'2020-10-06 16:29:13'),
(12,'testuser9','{bcrypt}$2a$10$40cYAWCfWah8MjbGrulnMeo0dGPOB3W2SvIaRXdnk0P7WWE6rZ6KC','瑞友测试9','lurj@realfinance.com.cn','13539454001','2',1,'sdebank',9,0,1,'2020-10-06 16:29:13'),
(13,'testuser10','{bcrypt}$2a$10$40cYAWCfWah8MjbGrulnMeo0dGPOB3W2SvIaRXdnk0P7WWE6rZ6KC','瑞友测试10','lurj@realfinance.com.cn','13539454001','2',1,'sdebank',9,0,1,'2020-10-06 16:29:13');

-- 角色
INSERT INTO SYSTEM_ROLE(id,code,name,note,enabled,tenant_id,v,created_user_id,created_time) VALUES
(1,'ADMIN','超级管理员','系统创建角色',1,'SYSTEM_TENANT',0,0,'2000-01-01 00:00:00'),
(2,'ROLE1','其他角色1','系统创建角色',1,'SYSTEM_TENANT',0,1,'2000-01-01 00:00:00'),
(3,'ROLE2','其他角色2','系统创建角色',0,'SYSTEM_TENANT',0,1,'2000-01-01 00:00:00'),
(4,'SYSTEM_ADMIN','系统管理员','系统管理员',1,'sdebank',0,1,'2000-01-01 00:00:00'),
(5,'JCBFZR','计财部负责人','计财部负责人',1,'sdebank',0,1,'2000-01-01 00:00:00'),
(6,'CGZXFZR','采购中心负责人','采购中心负责人',1,'sdebank',0,1,'2000-01-01 00:00:00'),
(7,'CGZXJBY','采购中心经办员','采购中心经办员',1,'sdebank',0,1,'2000-01-01 00:00:00'),
(8,'PSZJ','评审专家','评审专家',1,'sdebank',0,1,'2000-01-01 00:00:00'),
(9,'CGGLHHY','采购管理会员会','采购管理会员会（委员会下可设多个小组）',1,'sdebank',0,1,'2000-01-01 00:00:00'),
(10,'JBR','总行统筹部门/分支行采购经办','总行统筹部门/分支行采购经办',1,'sdebank',0,1,'2000-01-01 00:00:00'),
(11,'SHR','总行统筹部门/分支行采购审核人','总行统筹部门/分支行采购审核人',1,'sdebank',0,1,'2000-01-01 00:00:00'),
(12,'SPR','总行统筹部门/分支行采购审审批人','总行统筹部门/分支行采购审核人',1,'sdebank',0,1,'2000-01-01 00:00:00');

-- 角色组
INSERT INTO SYSTEM_ROLE_GROUP(id,code,name,tenant_id,enabled,v,created_user_id,created_time) VALUES
(1,'rolegroup1','角色组1','sdebank',1,0,1,'2000-01-01 00:00:00'),
(2,'rolegroup2','角色组2','sdebank',1,0,1,'2000-01-01 00:00:00');

-- 菜单
INSERT INTO SYSTEM_MENU(id,parent_id,code,code_path,name,name_path,type,leaf_count,sort,hidden,icon,component,component_name,url,keep_alive,has_menu_data_rule,v,created_user_id,created_time) VALUES

(1,null,'index','/index','首页','/首页',0,0,1,0,'system','/dashboard/index',null,'/index',0,0,0,0,'2000-01-01 00:00:00'),

(2,null,'system','/system','系统管理','/系统管理',0,5,2,0,'system','/layout',null,'/system',0,0,0,0,'2000-01-01 00:00:00'),

(3,2,'usermng','/system/usermng','用户管理','/系统管理/用户管理',1,3,1,0,'peoples','/sysManage/userMng',null,'/system/usermng',0,0,0,0,'2000-01-01 00:00:00'),
(4,3,'usermng:view','/system/usermng/usermng:view','查看','/系统管理/用户管理/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(5,3,'usermng:save','/system/usermng/usermng:save','保存','/系统管理/用户管理/保存',2,0,2,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(6,3,'usermng:delete','/system/usermng/usermng:delete','删除','/系统管理/用户管理/删除',2,0,3,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(7,2,'rolemng','/system/rolemng','角色管理','/系统管理/角色管理',1,5,2,0,'role','/sysManage/roleMng',null,'/system/rolemng',0,0,0,0,'2000-01-01 00:00:00'),
(8,7,'rolemng:view','/system/rolemng/rolemng:view','查看','/系统管理/角色管理/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(9,7,'rolemng:save','/system/rolemng/rolemng:save','保存','/系统管理/角色管理/保存',2,0,2,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(10,7,'rolemng:delete','/system/rolemng/rolemng:delete','删除','/系统管理/角色管理/删除',2,0,3,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(11,7,'rolemng:users','/system/rolemng/rolemng:users','关联用户','/系统管理/角色管理/关联用户',2,0,4,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(12,7,'rolemng:menus','/system/rolemng/rolemng:menus','授权菜单','/系统管理/角色管理/授权菜单',2,0,5,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(13,2,'menumng','/system/menumng','菜单管理','/系统管理/菜单管理',1,4,3,0,'menu','/sysManage/menuMng',null,'/system/menumng',0,0,0,0,'2000-01-01 00:00:00'),
(14,13,'menumng:view','/system/menumng/menumng:view','查看','/系统管理/菜单管理/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(15,13,'menumng:save','/system/menumng/menumng:save','保存','/系统管理/菜单管理/保存',2,0,2,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(16,13,'menumng:delete','/system/menumng/menumng:delete','删除','/系统管理/菜单管理/删除',2,0,3,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(17,13,'menumng:menudatarules','/system/menumng/menumng:menudatarules','数据规则','/系统管理/菜单管理/数据规则',2,0,4,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(18,2,'tenantmng','/system/tenantmng','法人管理','/系统管理/法人管理',1,3,4,0,'corp','/sysManage/tenantMng',null,'/system/tenantmng',0,0,0,0,'2000-01-01 00:00:00'),
(19,18,'tenantmng:view','/system/tenantmng/tenantmng:view','查看','/系统管理/法人管理/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(20,18,'tenantmng:save','/system/tenantmng/tenantmng:save','保存','/系统管理/法人管理/保存',2,0,2,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(21,18,'tenantmng:delete','/system/tenantmng/tenantmng:delete','删除','/系统管理/法人管理/删除',2,0,3,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(22,2,'departmentmng','/system/departmentmng','部门管理','/系统管理/部门管理',1,3,5,0,'dept','/sysManage/departmentMng',null,'/system/departmentmng',0,0,0,0,'2000-01-01 00:00:00'),
(23,22,'departmentmng:view','/system/departmentmng/departmentmng:view','查看','/系统管理/部门管理/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(24,22,'departmentmng:save','/system/departmentmng/departmentmng:save','保存','/系统管理/部门管理/保存',2,0,2,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(25,22,'departmentmng:delete','/system/departmentmng/departmentmng:delete','删除','/系统管理/部门管理/删除',2,0,3,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(26,2,'rolegroupmng','/system/rolegroupmng','角色组管理','/系统管理/角色组管理',1,5,6,0,'role','/sysManage/roleGroupMng',null,'/system/rolegroupmng',0,0,0,0,'2000-01-01 00:00:00'),
(27,26,'rolegroupmng:view','/system/rolegroupmng/rolegroupmng:view','查看','/系统管理/角色组管理/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(28,26,'rolegroupmng:save','/system/rolegroupmng/rolegroupmng:save','保存','/系统管理/角色组管理/保存',2,0,2,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(29,26,'rolegroupmng:delete','/system/rolegroupmng/rolegroupmng:delete','删除','/系统管理/角色组管理/删除',2,0,3,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(30,26,'rolegroupmng:users','/system/rolegroupmng/rolegroupmng:users','关联用户','/系统管理/角色组管理/关联用户',2,0,4,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(31,26,'rolegroupmng:roles','/system/rolegroupmng/rolegroupmng:roles','关联角色','/系统管理/角色组管理/关联角色',2,0,5,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(32,null,'test','/test','测试目录','/测试目录',0,3,20,0,'test','/layout',null,'/test',0,0,0,1,'2000-01-01 00:00:00'),

(33,32,'test1','/test/test1','测试菜单1','/测试目录/测试菜单1',1,0,1,0,'test',null,null,'/test/test1',0,1,0,1,'2000-01-01 00:00:00'),
(34,32,'test2','/test/test2','测试菜单2','/测试目录/测试菜单2',1,0,2,0,'test',null,null,'/test/test2',0,0,0,1,'2000-01-01 00:00:00'),
(35,32,'test3','/test/test3','测试菜单3','/测试目录/测试菜单3',1,0,3,0,'test',null,null,'/test/test3',0,0,0,1,'2000-01-01 00:00:00'),

(36,null,'process','/process','流程设计','/流程设计',0,2,1,0,'test','/layout',null,'/process',0,0,0,1,'2000-01-01 00:00:00'),
(37,36,'processdef','/process/processdef','流程设计','/流程设计/流程设计',1,0,2,0,'test','/process/processdef',null,'/process/processdef',0,0,0,1,'2000-01-01 00:00:00'),
(42,36,'bizmng','/process/bizmng','业务管理','/流程设计/业务管理',1,0,1,0,'test','/process/bizmng',null,'/process/bizmng',0,0,0,1,'2000-01-01 00:00:00'),

(38,null,'mytask','/mytask','个人办公','/个人办公',0,3,4,0,'test','/layout',null,'/mytask',0,0,0,1,'2000-01-01 00:00:00'),
(39,38,'todotask','/mytask/todotask','待办任务','/个人办公/待办任务',1,0,1,0,'test','/mytask/todotask',null,'/mytask/todotask',0,0,0,1,'2000-01-01 00:00:00'),
(40,38,'historytask','/mytask/historytask','已办任务','/个人办公/已办任务',1,0,2,0,'test','/mytask/historytask',null,'/mytask/historytask',0,0,0,1,'2000-01-01 00:00:00'),
(41,38,'proccess','/mytask/proccess','我发起的流程','/个人办公/我发起的流程',1,0,3,0,'test','/mytask/proccess',null,'/mytask/proccess',0,0,0,1,'2000-01-01 00:00:00'),

(43,null,'suppliermng','/suppliermng','供应商管理','/供应商管理',0,9,10,0,'test','/layout',null,'/suppliermng',0,0,0,1,'2000-01-01 00:00:00'),

(44,43,'supplierbase','/suppliermng/supplierbase','供应商库','/供应商管理/供应商库',1,1,1,0,'test','/suppliermng/supplierbase',null,'/suppliermng/supplierbase',0,0,0,1,'2000-01-01 00:00:00'),
(59,44,'supplierbase:view','/suppliermng/supplierbase/supplierbase:view','查看','/供应商管理/供应商库/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(58,43,'supplierbasedetails','/suppliermng/supplierbase/supplierbasedetails','供应商详情','/供应商管理/供应商详情',1,0,1,1,'test','/suppliermng/supplierdetails',null,'/suppliermng/supplierbase/view/:id',0,0,0,1,'2000-01-01 00:00:00'),

(45,43,'supplieraudit','/suppliermng/supplieraudit','供应商审核','/供应商管理/供应商审核',1,3,1,0,'test','/suppliermng/supplieraudit',null,'/suppliermng/supplieraudit',0,0,0,1,'2000-01-01 00:00:00'),
(50,45,'supplieraudit:view','/suppliermng/supplieraudit/supplieraudit:view','查看','/供应商管理/供应商审核/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(51,45,'supplieraudit:delete','/suppliermng/supplieraudit/supplieraudit:delete','删除','/供应商管理/供应商审核/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(52,45,'supplieraudit:approve','/suppliermng/supplieraudit/supplieraudit:approve','审核','/供应商管理/供应商审核/审核',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(65,43,'supplierauditdetails','/suppliermng/supplieraudit/supplierauditdetails','供应商审核详情','/供应商管理/供应商审核详情',1,0,1,1,'test','/suppliermng/supplierauditdetails',null,'/suppliermng/supplieraudit/view/:id',0,0,0,1,'2000-01-01 00:00:00'),

(46,43,'supplierblacklist','/suppliermng/supplierblacklist','黑名单管理','/供应商管理/黑名单管理',1,4,1,0,'test','/suppliermng/supplierblacklist',null,'/suppliermng/supplierblacklist',0,0,0,1,'2000-01-01 00:00:00'),
(54,46,'supplierblacklist:view','/suppliermng/supplierblacklist/supplierblacklist:view','查看','/供应商管理/黑名单管理/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(55,46,'supplierblacklist:save','/suppliermng/supplierblacklist/supplierblacklist:save','保存','/供应商管理/黑名单管理/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(56,46,'supplierblacklist:delete','/suppliermng/supplierblacklist/supplierblacklist:delete','删除','/供应商管理/黑名单管理/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(57,46,'supplierblacklist:approve','/suppliermng/supplierblacklist/supplierblacklist:approve','审批','/供应商管理/黑名单管理/审批',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(53,43,'supplierblacklistdetails','/suppliermng/supplierblacklist/supplierblacklistdetails','黑名单详情','/供应商管理/黑名单详情',1,0,1,1,'test','/suppliermng/blacklistdetails',null,'/suppliermng/supplierblacklist/view/:id',0,0,0,1,'2000-01-01 00:00:00'),

(47,43,'supplieraccountmng','/suppliermng/supplieraccountmng','供应商账号管理','/供应商管理/供应商账号管理',1,2,1,0,'test','/suppliermng/supplieraccountmng',null,'/suppliermng/supplieraccountmng',0,0,0,1,'2000-01-01 00:00:00'),
(48,47,'supplieraccountmng:view','/suppliermng/supplieraccountmng/supplieraccountmng:view','查看','/供应商管理/供应商账号管理/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(49,47,'supplieraccountmng:save','/suppliermng/supplieraccountmng/supplieraccountmng:save','保存','/供应商管理/供应商账号管理/保存',2,0,2,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(60,43,'supplieredit','/suppliermng/supplieredit','供应商信息修改','/供应商管理/供应商信息修改',1,4,1,0,'test','/suppliermng/supplieredit',null,'/suppliermng/supplieredit',0,0,0,1,'2000-01-01 00:00:00'),
(61,60,'supplieredit:view','/suppliermng/supplieredit/supplieredit:view','查看','/供应商管理/供应商信息修改/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(62,60,'supplieredit:save','/suppliermng/supplieredit/supplieredit:save','保存','/供应商管理/供应商信息修改/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(63,60,'supplieredit:delete','/suppliermng/supplieredit/supplieredit:delete','删除','/供应商管理/供应商信息修改/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(64,60,'supplieredit:approve','/suppliermng/supplieredit/supplieredit:approve','审批','/供应商管理/供应商信息修改/审批',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(66,43,'suppliereditdetails','/suppliermng/suppliereditdetails','供应商信息修改详情','/供应商管理/供应商信息修改详情',1,0,1,1,'test','/suppliermng/suppliereditdetails',null,'/suppliermng/supplieredit/view/:id',0,0,0,1,'2000-01-01 00:00:00'),

(67,43,'supplierlabel','/suppliermng/supplierlabel','标签管理','/供应商管理/标签管理',1,4,1,0,'test','/suppliermng/supplierlabel',null,'/suppliermng/supplierlabel',0,0,0,1,'2000-01-01 00:00:00'),
(68,67,'supplierlabel:view','/suppliermng/supplierlabel/supplierlabel:view','查看','/供应商管理/供应商信息修改/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(69,67,'supplierlabel:save','/suppliermng/supplierlabel/supplierlabel:save','保存','/供应商管理/供应商信息修改/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(70,67,'supplierlabel:delete','/suppliermng/supplierlabel/supplierlabel:delete','删除','/供应商管理/供应商信息修改/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(71,67,'supplierlabel:typemng','/suppliermng/supplierlabel/supplierlabel:typemng','标签类型管理','/供应商管理/供应商信息修改/标签类型管理',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(129,43,'supintsol','/suppliermng/supintsol','供应商意向征集','/供应商管理/供应商意向征集',1,5,1,0,'test','/suppliermng/supintsol',null,'/suppliermng/supintsol',0,0,0,1,'2000-01-01 00:00:00'),
(130,129,'supintsol:view','/suppliermng/supintsol/supintsol:view','查看','/供应商管理/供应商意向征集/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(131,129,'supintsol:save','/suppliermng/supintsol/supintsol:save','保存','/供应商管理/供应商意向征集/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(132,129,'supintsol:delete','/suppliermng/supintsol/supintsol:delete','删除','/供应商管理/供应商意向征集/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(133,129,'supintsol:startproc','/suppliermng/supintsol/supintsol:startproc','启动流程','/供应商管理/供应商意向征集/启动流程',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(134,43,'supintsoldetails','/suppliermng/supintsoldetails','供应商意向征集详情','/供应商管理/供应商意向征集详情',1,0,1,1,'test','/suppliermng/supintsoldetails',null,'/suppliermng/supintsoldetails/view/:id',0,0,0,1,'2000-01-01 00:00:00'),

(72,null,'announcementmng','/announcementmng','公告管理','/公告管理',0,1,1,0,'test','/layout',null,'/announcementmng',0,0,0,1,'2000-01-01 00:00:00'),

(73,72,'supannouncement','/announcementmng/supannouncement','供应商门户公告','/公告管理/供应商门户公告',1,4,1,0,'test','/announcementmng/supplierannouncement',null,'/announcementmng/supplierannouncement',0,0,0,1,'2000-01-01 00:00:00'),
(74,73,'supannouncement:view','/announcementmng/supannouncement/supannouncement:view','查看','/供应商管理/供应商信息修改/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(75,73,'supannouncement:save','/announcementmng/supannouncement/supannouncement:save','保存','/供应商管理/供应商信息修改/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(76,73,'supannouncement:delete','/announcementmng/supannouncement/supannouncement:delete','删除','/供应商管理/供应商信息修改/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(77,73,'supannouncement:approve','/announcementmng/supannouncement/supannouncement:approve','审批','/供应商管理/供应商信息修改/审批',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(89,72,'supannouncementchannel','/announcementmng/supannouncementchannel','供应商门户公告频道','/公告管理/供应商门户公告频道',1,3,1,0,'test','/announcementmng/supannouncementchannel',null,'/announcementmng/supannouncementchannel',0,0,0,1,'2000-01-01 00:00:00'),
(90,89,'supannouncementchannel:view','/announcementmng/supannouncementchannel/supannouncementchannel:view','查看','/供应商管理/供应商门户公告频道/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(91,89,'supannouncementchannel:save','/announcementmng/supannouncementchannel/supannouncementchannel:save','保存','/供应商管理/供应商门户公告频道/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(92,89,'supannouncementchannel:delete','/announcementmng/supannouncementchannel/supannouncementchannel:delete','删除','/供应商管理/供应商门户公告频道/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(78,null,'expertsmng','/expertsmng','专家库','/专家库',0,4,1,0,'test','/layout',null,'/expertsmng',0,0,0,1,'2000-01-01 00:00:00'),
(79,78,'expertsdoc','/expertsmng/expertsdoc','专家档案','/专家库/专家档案',1,3,1,0,'test','/expertsmng/expertsdoc',null,'/expertsmng/expertsdoc',0,0,0,1,'2000-01-01 00:00:00'),
(80,79,'expertsdoc:view','/expertsmng/expertsdoc/expertsdoc:view','查看','/专家库/专家档案/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(81,79,'expertsdoc:save','/expertsmng/expertsdoc/expertsdoc:save','保存','/专家库/专家档案/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(82,79,'expertsdoc:delete','/expertsmng/expertsdoc/expertsdoc:delete','删除','/专家库/专家档案/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(84,78,'expertsextract','/expertsmng/expertsextract','专家抽取','/专家库/专家抽取',1,4,1,0,'test','/expertsmng/expertsextract',null,'/expertsmng/expertsextract',0,0,0,1,'2000-01-01 00:00:00'),
(85,84,'expertsextract:view','/expertsmng/expertsextract/expertsextract:view','查看','/专家库/专家抽取/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(86,84,'expertsextract:save','/expertsmng/expertsextract/expertsextract:save','保存','/专家库/专家抽取/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(87,84,'expertsextract:delete','/expertsmng/expertsextract/expertsextract:delete','删除','/专家库/专家抽取/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(88,84,'expertsextract:approve','/expertsmng/expertsextract/expertsextract:approve','审批','/专家库/专家抽取/审批',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(93,78,'expertsdeptassigned','/expertsmng/expertsdeptassigned','部门委派','/专家库/部门委派',1,3,1,0,'test','/expertsmng/expertsdeptassigned',null,'/expertsmng/expertsdeptassigned',0,0,0,1,'2000-01-01 00:00:00'),
(94,93,'expertsdeptassigned:view','/expertsmng/expertsdeptassigned/expertsdeptassigned:view','查看','/专家库/部门委派/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(95,93,'expertsdeptassigned:save','/expertsmng/expertsdeptassigned/expertsdeptassigned:save','保存','/专家库/部门委派/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(96,93,'expertsdeptassigned:delete','/expertsmng/expertsdeptassigned/expertsdeptassigned:delete','删除','/专家库/部门委派/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(97,78,'expertsconfirm','/expertsmng/expertsconfirm','专家确认','/专家库/专家确认',1,1,1,0,'test','/expertsmng/expertsconfirm',null,'/expertsmng/expertsconfirm',0,0,0,1,'2000-01-01 00:00:00'),
(98,97,'expertsconfirm:view','/expertsmng/expertsconfirm/expertsconfirm:view','查看','/专家库/专家抽取/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(99,78,'expertsmajor','/expertsmng/expertsmajor','专家标签','/专家库/专家标签',1,3,1,0,'test','/expertsmng/expertsmajor',null,'/expertsmng/expertsmajor',0,0,0,1,'2000-01-01 00:00:00'),
(100,99,'expertsmajor:view','/expertsmng/expertsmajor/expertsmajor:view','查看','/专家库/专家标签/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(101,99,'expertsmajor:save','/expertsmng/expertsmajor/expertsmajor:save','保存','/专家库/专家标签/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(102,99,'expertsmajor:delete','/expertsmng/expertsmajor/expertsmajor:delete','删除','/专家库/专家标签/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(103,null,'purchasemng','/purchasemng','采购流程管理','/采购流程管理',0,3,1,0,'test','/layout',null,'/purchasemng',0,0,0,1,'2000-01-01 00:00:00'),

(104,103,'purreq','/purchasemng/purreq','采购申请','/采购流程管理/采购申请',1,4,1,0,'test','/purchasemng/requirement',null,'/purchasemng/requirement',0,0,0,1,'2000-01-01 00:00:00'),
(105,104,'purreq:view','/purchasemng/purreq/purreq:view','查看','/采购流程管理/采购申请/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(106,104,'purreq:save','/purchasemng/purreq/purreq:save','保存','/采购流程管理/采购申请/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(107,104,'purreq:delete','/purchasemng/purreq/purreq:delete','删除','/采购流程管理/采购申请/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(108,104,'purreq:startproc','/purchasemng/purreq/purreq:startproc','启动流程','/采购流程管理/采购申请/启动流程',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(109,103,'reqaccept','/purchasemng/reqaccept','采购受理','/采购流程管理/采购受理',1,4,1,0,'test','/purchasemng/reqacceptance',null,'/purchasemng/reqacceptance',0,0,0,1,'2000-01-01 00:00:00'),
(110,109,'reqaccept:view','/purchasemng/reqaccept/reqaccept:view','查看','/采购流程管理/采购受理/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(111,109,'reqaccept:assign','/purchasemng/reqaccept/reqaccept:assign','分派','/采购流程管理/采购受理/分派',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(112,109,'reqaccept:accept','/purchasemng/reqaccept/reqaccept:accept','受理','/采购流程管理/采购受理/受理',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(113,109,'reqaccept:return','/purchasemng/reqaccept/reqaccept:return','退回','/采购流程管理/采购受理/退回',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(114,103,'purproj','/purchasemng/purproj','采购方案','/采购流程管理/采购方案',1,4,1,0,'test','/purchasemng/project',null,'/purchasemng/project',0,0,0,1,'2000-01-01 00:00:00'),
(115,114,'purproj:view','/purchasemng/purproj/purproj:view','查看','/采购流程管理/采购方案/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(116,114,'purproj:save','/purchasemng/purproj/purproj:save','保存','/采购流程管理/采购方案/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(117,114,'purproj:startproc','/purchasemng/purproj/purproj:startproc','启动流程','/采购流程管理/采购方案/启动流程',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(126,103,'purprojexec','/purchasemng/purprojexec','采购方案执行','/采购流程管理/采购方案执行',1,2,1,0,'test','/purchasemng/projectexecution',null,'/purchasemng/projectexecution',0,0,0,1,'2000-01-01 00:00:00'),
(127,126,'purprojexec:view','/purchasemng/purprojexec/purprojexec:view','查看','/采购流程管理/采购方案执行/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(128,126,'purprojexec:save','/purchasemng/purprojexec/purprojexec:save','保存','/采购流程管理/采购方案执行/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(129,103,'purprojmtg','/purchasemng/purprojmtg','评审会','/采购流程管理/评审会',1,1,1,0,'test','/purchasemng/purprojmtg',null,'/purchasemng/purprojmtg',0,0,0,1,'2000-01-01 00:00:00'),
(130,129,'purprojmtg:view','/purchasemng/purprojmtg/purprojmtg:view','查看','/采购流程管理/评审会/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(118,null,'purchasedoc','/purchasedoc','基础信息','/基础信息',0,1,1,0,'test','/layout',null,'/purchasedoc',0,0,0,1,'2000-01-01 00:00:00'),
(119,118,'purcatalog','/purchasedoc/purcatalog','采购目录','/基础信息/采购目录',1,2,1,0,'test','/purchasedoc/purcatalog',null,'/purchasedoc/purcatalog',0,0,0,1,'2000-01-01 00:00:00'),
(120,119,'purcatalog:view','/purchasedoc/purcatalog/purcatalog:view','查看','/基础信息/采购目录/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(121,119,'purcatalog:save','/purchasedoc/purcatalog/purcatalog:save','保存','/基础信息/采购目录/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),

(122,118,'texttemp','/purchasedoc/texttemp','文本模板','/基础信息/文本模板',1,3,1,0,'test','/purchasedoc/texttemp',null,'/purchasedoc/texttemp',0,0,0,1,'2000-01-01 00:00:00'),
(123,122,'texttemp:view','/purchasedoc/texttemp/texttemp:view','查看','/基础信息/文本模板/查看',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(124,122,'texttemp:save','/purchasedoc/texttemp/texttemp:save','保存','/基础信息/文本模板/保存',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00'),
(125,122,'texttemp:delete','/purchasedoc/texttemp/texttemp:delete','删除','/基础信息/文本模板/删除',2,0,1,0,null,null,null,null,0,0,0,0,'2000-01-01 00:00:00');

INSERT INTO SYSTEM_MENU_DATA_RULE(id,menu_id,rule_name,rule_attribute,rule_conditions,rule_value,enabled,created_user_id,created_time) VALUES
(1,33,'测试规则','departmentId','in','4',1,1,'2000-01-01 00:00:00'),
(2,33,'测试规则2','tenantId','equal','4',1,1,'2000-01-01 00:00:00');

-- 用户角色
INSERT INTO SYSTEM_USER_ROLE(user_id,role_id) VALUES
(1,1),
(2,2),
(2,3),
(3,2),
(4,4),
(5,5),
(6,6),
(7,7),
(8,8),
(9,9),
(10,10);

-- 角色菜单
INSERT INTO SYSTEM_ROLE_MENU(role_id,menu_id) VALUES
(1,1),
(1,2),
(1,3),
(1,4),
(1,5),
(1,6),
(1,7),
(1,8),
(1,9),
(1,10),
(1,11),
(1,12),
(1,13),
(1,14),
(1,15),
(1,16),
(1,17),
(1,18),
(1,19),
(1,20),
(1,21),
(1,22),
(1,23),
(1,24),
(1,25),
(1,26),
(1,27),
(1,28),
(1,29),
(1,30),
(1,31),
(1,32),
(1,33),
(1,34),
(1,35),
(1,36),
(1,37),
(1,38),
(1,39),
(1,40),
(1,41),
(1,42),
(1,43),
(1,44),
(1,45),
(1,46),
(1,47),
(1,48),
(1,49),
(1,50),
(1,51),
(1,52),
(1,53),
(1,54),
(1,55),
(1,56),
(1,57),
(1,58),
(1,59),
(1,60),
(1,61),
(1,62),
(1,63),
(1,64),
(1,65),
(1,66),
(1,67),
(1,68),
(1,69),
(1,70),
(1,71),
(1,72),
(1,73),
(1,74),
(1,75),
(1,76),
(1,77),
(1,78),
(1,79),
(1,80),
(1,81),
(1,82),
-- (1,83),
(1,84),
(1,85),
(1,86),
(1,87),
(1,88),
(4,1),
(4,2),
(4,3),
(4,4),
(4,5),
(4,7),
(4,8),
(4,11),
(4,22),
(4,23),
(4,24),
(4,25),
(4,26),
(4,27),
(4,32);

-- 角色角色组
INSERT INTO SYSTEM_ROLE_ROLE_GROUP(role_group_id,role_id) VALUES
(1,5),
(1,6);

-- 用户角色组
INSERT INTO SYSTEM_USER_ROLE_GROUP(role_group_id,user_id) VALUES
(1,2),
(1,4),
(1,5),
(1,6),
(1,7),
(1,8),
(1,9);