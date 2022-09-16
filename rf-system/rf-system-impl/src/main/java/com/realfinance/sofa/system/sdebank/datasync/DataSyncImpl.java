package com.realfinance.sofa.system.sdebank.datasync;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.jcraft.jsch.ChannelSftp;
import com.realfinance.sofa.sdebank.sftp.SdebankSFTP;
import com.realfinance.sofa.sdebank.sftp.SftpCategory;
import com.realfinance.sofa.system.domain.Department;
import com.realfinance.sofa.system.domain.Role;
import com.realfinance.sofa.system.domain.Tenant;
import com.realfinance.sofa.system.domain.User;
import com.realfinance.sofa.system.facade.DataSyncFacade;
import com.realfinance.sofa.system.repository.DepartmentRepository;
import com.realfinance.sofa.system.repository.RoleRepository;
import com.realfinance.sofa.system.repository.TenantRepository;
import com.realfinance.sofa.system.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;


@Service
@SofaService(interfaceType = DataSyncFacade.class, uniqueId = "${service.rf-system.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
public class DataSyncImpl implements DataSyncFacade {

    private static final Logger log = LoggerFactory.getLogger(DataSyncImpl.class);

    private final EtlEmpRepository etlEmpRepository;
    private final EtlEmpRoleRepository etlEmpRoleRepository;
    private final EtlOrgRepository etlOrgRepository;
    private final EtlOrgEmpRepository etlOrgEmpRepository;
    private final EtlRoleRepository etlRoleRepository;
    private final TenantRepository tenantRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public DataSyncImpl(EtlEmpRepository etlEmpRepository,
                        EtlEmpRoleRepository etlEmpRoleRepository,
                        EtlOrgRepository etlOrgRepository,
                        EtlOrgEmpRepository etlOrgEmpRepository,
                        EtlRoleRepository etlRoleRepository,
                        TenantRepository tenantRepository,
                        DepartmentRepository departmentRepository,
                        UserRepository userRepository,
                        PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.etlEmpRepository = etlEmpRepository;
        this.etlEmpRoleRepository = etlEmpRoleRepository;
        this.etlOrgRepository = etlOrgRepository;
        this.etlOrgEmpRepository = etlOrgEmpRepository;
        this.etlRoleRepository = etlRoleRepository;
        this.tenantRepository = tenantRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    private String ftpDir = "/home/eip/bamboocloud/exportData/sit/";


    /**
     * 检查MD5
     *
     * @param fileName
     * @return
     */
    private void checkFileMd5(String fileName, byte[] file) {
        //文件上的校验码
        String md5Str = StringUtils.substringBeforeLast(
                StringUtils.substringAfterLast(fileName, "["), "]");
        if (!md5Str.equals(DigestUtils.md5DigestAsHex(file))) {
            throw new RuntimeException("MD5校验失败");
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncEtlUser() {
        SdebankSFTP sdebankSFTP = new SdebankSFTP();
        ChannelSftp connect = sdebankSFTP.connect(SftpCategory.DATASYNC);
        String fileName = sdebankSFTP.getFilePrefix(ftpDir, "EMP_", connect);
        byte[] bytes = sdebankSFTP.download(ftpDir, fileName, connect);
        sdebankSFTP.close();
        checkFileMd5(fileName, bytes);

        ByteArrayInputStream b = new ByteArrayInputStream(bytes);

        SAXReader reader = new SAXReader();
        //2.加载xml
        Document document = null;
        try {
            document = reader.read(b);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<Iterator> xmlList = Dom4JUtils.getXml(document);
        List<EtlEmp> etlEmpList = new ArrayList<>();
        for (Iterator dataIterator : xmlList) {
            while (dataIterator.hasNext()) {
                Element orgEmployeeStuChild = (Element) dataIterator.next();
                Iterator orgEmployeeIterator = orgEmployeeStuChild.elementIterator();
                EtlEmp etlEmp = new EtlEmp();
                while (orgEmployeeIterator.hasNext()) {
                    Element stuChild = (Element) orgEmployeeIterator.next();
                    if (stuChild.getName().equals("empcode") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setEmpcode(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("userid") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setUserid(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("empname") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setEmpname(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("gender") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setGender(stuChild.getStringValue());
                    }
                    /*if (stuChild.getName().equals("birthdate") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setBirthdate(Date.valueOf(stuChild.getStringValue()));
                    }
                    if (stuChild.getName().equals("position") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setPosition(new BigDecimal(stuChild.getStringValue()));
                    }*/
                    if (stuChild.getName().equals("empstatus") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setEmpstatus(stuChild.getStringValue());
                    }
                    /*if (stuChild.getName().equals("cardtype") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setCardtype(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("cardno") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setCardno(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("indate") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setIndate(Date.valueOf(stuChild.getStringValue()));
                    }*/
                    if (stuChild.getName().equals("otel") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setOtel(stuChild.getStringValue());
                    }
                    /*if (stuChild.getName().equals("oaddress") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setOaddress(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("ozipcode") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setOzipcode(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("oemail") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setOemail(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("faxno") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setFaxno(stuChild.getStringValue());
                    }*/
                    if (stuChild.getName().equals("mobileno") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setMobileno(stuChild.getStringValue());
                    }
                    /*if (stuChild.getName().equals("htel") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setHtel(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("haddress") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setHaddress(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("hzipcode") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setHzipcode(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("pemail") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setPemail(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("party") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setParty(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("degree") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setDegree(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("major") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setMajor(new BigDecimal(stuChild.getStringValue()));
                    }
                    if (stuChild.getName().equals("regdate") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setRegdate(Date.valueOf(stuChild.getStringValue()));
                    }*/
                    if (stuChild.getName().equals("orgid") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setOrgid(new BigDecimal(stuChild.getStringValue()));
                    }
                    if (stuChild.getName().equals("remark") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setRemark(stuChild.getStringValue());
                    }
                    /*if (stuChild.getName().equals("sortno") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setSortno(stuChild.getStringValue());
                    }*/
                    if (stuChild.getName().equals("orgcode") && !stuChild.getStringValue().equals("")) {
                        etlEmp.setOrgcode(stuChild.getStringValue());
                    }
                }
                etlEmpList.add(etlEmp);
            }
        }
        //同步前清空数据
        etlEmpRepository.deleteAll();
        etlEmpRepository.saveAll(etlEmpList);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncEtlRole() {
        SdebankSFTP sdebankSFTP = new SdebankSFTP();
        ChannelSftp connect = sdebankSFTP.connect(SftpCategory.DATASYNC);
        String fileName = sdebankSFTP.getFilePrefix(ftpDir, "ROLE_SRCPS_", connect);
        byte[] bytes = sdebankSFTP.download(ftpDir, fileName, connect);
        sdebankSFTP.close();
        checkFileMd5(fileName, bytes);

        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        SAXReader reader = new SAXReader();
        //2.加载xml
        Document document = null;
        try {
            document = reader.read(b);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<Iterator> xmlList = Dom4JUtils.getXml(document);
        List<EtlRole> etlRoleList = new ArrayList<>();
        for (Iterator dataIterator : xmlList) {
            while (dataIterator.hasNext()) {
                Element capRoleStuChild = (Element) dataIterator.next();
                Iterator capRoleIterator = capRoleStuChild.elementIterator();
                EtlRole etlRole = new EtlRole();
                while (capRoleIterator.hasNext()) {
                    Element stuChild = (Element) capRoleIterator.next();
                    if (stuChild.getName().equals("rolecode") && !stuChild.getStringValue().equals("")) {
                        etlRole.setRolecode(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("rolename") && !stuChild.getStringValue().equals("")) {
                        etlRole.setRolename(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("roledesc") && !stuChild.getStringValue().equals("")) {
                        etlRole.setRoledesc("roledesc");
                    }
                }
                etlRoleList.add(etlRole);
            }
        }
        //同步前清空数据
        etlRoleRepository.deleteAll();
        etlRoleRepository.saveAll(etlRoleList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncEtlDepartment() {
        SdebankSFTP sdebankSFTP = new SdebankSFTP();
        ChannelSftp connect = sdebankSFTP.connect(SftpCategory.DATASYNC);
        String fileName = sdebankSFTP.getFilePrefix(ftpDir, "ORG_", connect);
        byte[] bytes = sdebankSFTP.download(ftpDir, fileName, connect);
        sdebankSFTP.close();
        checkFileMd5(fileName, bytes);
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        SAXReader reader = new SAXReader();
        //2.加载xml
        Document document = null;
        try {
            document = reader.read(b);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<Iterator> xmlList = Dom4JUtils.getXml(document);
        List<EtlOrg> etlOrgList = new ArrayList<>();
        for (Iterator dataIterator : xmlList) {
            while (dataIterator.hasNext()) {
                Element orgOrganizationStuChild = (Element) dataIterator.next();
                Iterator orgOrganizationStuChildIterator = orgOrganizationStuChild.elementIterator();
                EtlOrg etlOrg = new EtlOrg();
                while (orgOrganizationStuChildIterator.hasNext()) {
                    Element stuChild = (Element) orgOrganizationStuChildIterator.next();
                    if (stuChild.getName().equals("orgid") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setOrgid(new BigDecimal(stuChild.getStringValue()));
                    }
                    if (stuChild.getName().equals("orgcode") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setOrgcode(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("orgname") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setOrgname(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("orglevel") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setOrglevel(new BigDecimal(stuChild.getStringValue()));
                    }
                    if (stuChild.getName().equals("orgdegree") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setOrgdegree(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("orgseq") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setOrgseq(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("orgtype") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setOrgtype(stuChild.getStringValue());
                    }
                    /*if (stuChild.getName().equals("orgaddr") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setOrgaddr(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("zipcode") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setZipcode(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("manaposition") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setManaposition(new BigDecimal(stuChild.getStringValue()));
                    }*/
                    if (stuChild.getName().equals("managerid") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setManagerid(new BigDecimal(stuChild.getStringValue()));
                    }
                    /*if (stuChild.getName().equals("orgmanager") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setOrgmanager(stuChild.getStringValue());
                    }*/
                    if (stuChild.getName().equals("linkman") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setLinkman(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("linktel") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setLinktel(stuChild.getStringValue());
                    }
                    /*if (stuChild.getName().equals("email") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setEmail(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("weburl") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setWeburl(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("startdate") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setStartdate(Date.valueOf(stuChild.getStringValue()));
                    }
                    if (stuChild.getName().equals("enddate") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setEnddate(Date.valueOf(stuChild.getStringValue()));
                    }*/
                    if (stuChild.getName().equals("status") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setStatus(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("sortno") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setSortno(Integer.parseInt(stuChild.getStringValue()));
                    }
                    if (stuChild.getName().equals("isleaf") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setIsleaf(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("subcount") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setSubcount(new BigDecimal(stuChild.getStringValue()));
                    }
                    if (stuChild.getName().equals("remark") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setRemark(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("parentorgid") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setParentorgid(new BigDecimal(stuChild.getStringValue()));
                    }
                    if (stuChild.getName().equals("bankno") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setBankno(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("oucode") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setOucode(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("orgcategory") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setOrgcategory(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("parentorgcode") && !stuChild.getStringValue().equals("")) {
                        etlOrg.setParentorgcode(stuChild.getStringValue());
                    }
                }
                etlOrgList.add(etlOrg);
            }
        }
        //同步前清空数据
        etlOrgRepository.deleteAll();
        etlOrgRepository.saveAll(etlOrgList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncEtlUserDepartment() {
        SdebankSFTP sdebankSFTP = new SdebankSFTP();
        ChannelSftp connect = sdebankSFTP.connect(SftpCategory.DATASYNC);
        String fileName = sdebankSFTP.getFilePrefix(ftpDir, "EMPORG_", connect);
        byte[] bytes = sdebankSFTP.download(ftpDir, fileName, connect);
        sdebankSFTP.close();
        checkFileMd5(fileName, bytes);
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        SAXReader reader = new SAXReader();
        //2.加载xml
        Document document = null;
        try {
            document = reader.read(b);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<Iterator> xmlList = Dom4JUtils.getXml(document);
        List<EtlOrgEmp> etlOrgEmpList = new ArrayList<>();
        for (Iterator dataIterator : xmlList) {
            while (dataIterator.hasNext()) {
                Element orgEmporgStuChild = (Element) dataIterator.next();
                Iterator orgEmporgStuChildIterator = orgEmporgStuChild.elementIterator();
                EtlOrgEmp etlOrgEmp = new EtlOrgEmp();
                while (orgEmporgStuChildIterator.hasNext()) {
                    Element stuChild = (Element) orgEmporgStuChildIterator.next();
                    if (stuChild.getName().equals("orgid") && !stuChild.getStringValue().equals("")) {
                        etlOrgEmp.setOrgid(new BigDecimal(stuChild.getStringValue()));
                    }
                    if (stuChild.getName().equals("empid") && !stuChild.getStringValue().equals("")) {
                        etlOrgEmp.setEmpid(new BigDecimal(stuChild.getStringValue()));
                    }
                    if (stuChild.getName().equals("ismain") && !stuChild.getStringValue().equals("")) {
                        etlOrgEmp.setIsmain(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("tenantid") && !stuChild.getStringValue().equals("")) {
                        etlOrgEmp.setTenantid(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("orgcode") && !stuChild.getStringValue().equals("")) {
                        etlOrgEmp.setOrgcode(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("empcode") && !stuChild.getStringValue().equals("")) {
                        etlOrgEmp.setEmpcode(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("userid") && !stuChild.getStringValue().equals("")) {
                        etlOrgEmp.setUserid(stuChild.getStringValue());
                    }
                }
                etlOrgEmpList.add(etlOrgEmp);
            }
        }
        //同步前清空数据
        etlOrgEmpRepository.deleteAll();
        etlOrgEmpRepository.saveAll(etlOrgEmpList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncEtlUserRole() {
        SdebankSFTP sdebankSFTP = new SdebankSFTP();
        ChannelSftp connect = sdebankSFTP.connect(SftpCategory.DATASYNC);
        String fileName = sdebankSFTP.getFilePrefix(ftpDir, "EMPROLE_SRCPS_", connect);
        byte[] bytes = sdebankSFTP.download(ftpDir, fileName, connect);
        sdebankSFTP.close();
        checkFileMd5(fileName, bytes);
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        SAXReader reader = new SAXReader();
        //2.加载xml
        Document document = null;
        try {
            document = reader.read(b);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        List<Iterator> xmlList = Dom4JUtils.getXml(document);
        List<EtlEmpRole> etlEmpRoleList = new ArrayList<>();
        for (Iterator dataIterator : xmlList) {
            while (dataIterator.hasNext()) {
                Element capPartyauthStuChild = (Element) dataIterator.next();
                Iterator capPartyauthStuChildIterator = capPartyauthStuChild.elementIterator();
                EtlEmpRole etlEmpRole = new EtlEmpRole();
                while (capPartyauthStuChildIterator.hasNext()) {
                    Element stuChild = (Element) capPartyauthStuChildIterator.next();
                    if (stuChild.getName().equals("empcode") && !stuChild.getStringValue().equals("")) {
                        etlEmpRole.setEmpcode(stuChild.getStringValue());
                    }
                    if (stuChild.getName().equals("rolecode") && !stuChild.getStringValue().equals("")) {
                        etlEmpRole.setRolecode(stuChild.getStringValue());
                    }
                }
                etlEmpRoleList.add(etlEmpRole);
            }
        }
        //同步前清空数据
        etlEmpRoleRepository.deleteAll();
        etlEmpRoleRepository.saveAll(etlEmpRoleList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void syncAll() {
        /*同步机构*/
        List<EtlOrg> etlOrgs = etlOrgRepository.findAll();
        List<Department> departments = departmentRepository.findAll();
        Map<String, Department> departmentMap = departments.stream().collect(Collectors.toMap(e -> e.getCode() + "_" + e.getTenant().getId(), e -> e));
        //法人
        List<Tenant> tenants = tenantRepository.findAll();
        Map<String, Tenant> tenantMap = tenants.stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
        List<EtlOrg> orgDeleteList = new ArrayList<>();
        for (EtlOrg etlOrg : etlOrgs) {
            if (etlOrg.getOrgcode() == null) {
                log.error("etlOrg表中id为" + etlOrg.getId() + "的机构编码不能为空!");
                orgDeleteList.add(etlOrg);
                continue;
            }
            if (etlOrg.getBankno() == null || etlOrg.getOrgname() == null) {
                log.error("机构编码为" + etlOrg.getOrgcode() + "的法人代码或机构名称不能为空!");
                orgDeleteList.add(etlOrg);
                continue;
            }
            Department department;
            //校验机构编码和法人是否一致
            if (departmentMap.get(etlOrg.getOrgcode() + "_" + etlOrg.getBankno()) != null) {
                department = departmentMap.get(etlOrg.getOrgcode() + "_" + etlOrg.getBankno());
            } else {
                department = new Department();
                department.setCode(etlOrg.getOrgcode());
                department.setTenant(tenantMap.get(etlOrg.getBankno()));
                department.setName(etlOrg.getOrgname());
                if (etlOrg.getSortno() != null) {
                    department.setSort(etlOrg.getSortno());
                } else {
                    //后续观察数据手动修改值
                    department.setSort(100);
                }
                department.setEnabled(true);
                department.setLeafCount(0);
                if (etlOrg.getParentorgid() == null) {
                    department.setType(Department.Type.FIRST_LEVEL);
                    department.setParent(null);
                } else {
                    department.setType(Department.Type.SUB);
                }

                if (etlOrg.getSortno() != null) {
                    department.setSort(etlOrg.getSortno());
                }
                departments.add(department);
            }
            department.setName(etlOrg.getOrgname());
            if (etlOrg.getParentorgid() == null) {
                department.setType(Department.Type.FIRST_LEVEL);
                department.setParent(null);
            } else {
                department.setType(Department.Type.SUB);
            }
            if (etlOrg.getSortno() != null) {
                department.setSort(etlOrg.getSortno());
            }
        }
        etlOrgs.removeAll(orgDeleteList);
        //更新父节点
        Map<BigDecimal, EtlOrg> orgIdMap = etlOrgs.stream().collect(Collectors.toMap(e -> e.getOrgid(), e -> e));
        Map<String, Department> dtMap = departments.stream().collect(Collectors.toMap(e -> e.getCode() + "_" + e.getTenant().getId(), e -> e));
        etlOrgs.forEach(etlOrg -> {
            Department department;
            if (dtMap.get(etlOrg.getOrgcode() + "_" + etlOrg.getBankno()) != null && etlOrg.getParentorgid() != null) {
                department = dtMap.get(etlOrg.getOrgcode() + "_" + etlOrg.getBankno());
                //父记录数据
                EtlOrg eo = orgIdMap.get(etlOrg.getParentorgid());
                department.setParent(dtMap.get(eo.getOrgcode() + "_" + eo.getBankno()));
            }
        });
        departmentRepository.saveAll(departments);
        //更新子节点数
        Map<Integer, List<Department>> collect = departments.stream()
                .filter(e -> e.getType() == Department.Type.SUB)
                .collect(Collectors.groupingBy(e -> e.getParent().getId()));
        for (Department department : departments) {
            int size = collect.getOrDefault(department.getId(), Collections.emptyList()).size();
            department.setLeafCount(size);
        }
        //更新code_path,name_path字段
        Map<Integer, Department> pathMap = departments.stream().collect
                (HashMap::new, (m, v) -> m.put(v.getId(), v.getParent()), HashMap::putAll);
        for (Department department : departments) {
            department.setCodePath(getCodePath(department,pathMap));
            department.setNamePath(getNamePath(department,pathMap));
        }
        departmentRepository.saveAll(departments);
        /*同步人员*/
        List<EtlOrgEmp> etlOrgEmps = etlOrgEmpRepository.findByIsmain("y");
        List<EtlOrgEmp> orgEmpDeleteList = new ArrayList<>();
        for (EtlOrgEmp etlOrgEmp : etlOrgEmps) {
            if (etlOrgEmp.getEmpcode() == null) {
                log.error("orgemp表中di为" + etlOrgEmp.getId() + "的人员编码empcode不能为空!");
                orgEmpDeleteList.add(etlOrgEmp);
            }
            if (etlOrgEmp.getOrgcode() == null) {
                log.error("orgemp表中di为" + etlOrgEmp.getId() + "的人员编码orgcode不能为空!");
                orgEmpDeleteList.add(etlOrgEmp);
            }
        }
        etlOrgEmps.removeAll(orgEmpDeleteList);
        List<String> empCodeS = etlOrgEmps.stream().map(EtlOrgEmp::getEmpcode).collect(Collectors.toList());
        List<String> orgCodeList = etlOrgEmps.stream().map(EtlOrgEmp::getOrgcode).collect(Collectors.toList());
        //取出有关联的机构数据
        List<EtlOrg> etlOrgCodeList = etlOrgs.stream().filter(e -> orgCodeList.contains(e.getOrgcode())).collect(Collectors.toList());
        Map<String, String> empCodeMap = etlOrgEmps.stream().collect(Collectors.toMap(e -> e.getEmpcode(), e -> e.getOrgcode()));
        Map<String, String> orgMap = etlOrgCodeList.stream().collect(Collectors.toMap(e -> e.getOrgcode(), e -> e.getBankno()));
        List<EtlEmp> etlEmps = etlEmpRepository.findByEmpcodeIn(empCodeS);
        List<User> users = userRepository.findAll();
        Map<String, User> userMap = users.stream().collect(Collectors.toMap(e -> e.getUsername() + "_" + e.getTenant().getId(), e -> e));
        List<EtlEmp> empDeleteList = new ArrayList<>();
        String encode = passwordEncoder.encode("123456");
        for (EtlEmp etlEmp : etlEmps) {
            if (orgMap.get(empCodeMap.get(etlEmp.getEmpcode())) == null) {
                log.error("编码为" + etlEmp.getEmpcode() + "的人员关联的机构法人代码bankno不能为空!");
                empDeleteList.add(etlEmp);
                continue;
            }
            User user;
            //校验人员编码和法人是否一致
            if (userMap.get(etlEmp.getEmpcode() + "_" + orgMap.get(empCodeMap.get(etlEmp.getEmpcode()))) != null) {
                user = userMap.get(etlEmp.getEmpcode() + "_" + orgMap.get(empCodeMap.get(etlEmp.getEmpcode())));
            } else {
                user = new User();
                user.setTenant(tenantMap.get(orgMap.get(empCodeMap.get(etlEmp.getEmpcode()))));
                user.setUsername(etlEmp.getEmpcode());
                user.setPassword(encode);
                user.setEnabled(true);
                user.setDepartment(dtMap.get(empCodeMap.get(etlEmp.getEmpcode()) + "_" + orgMap.get(empCodeMap.get(etlEmp.getEmpcode()))));
                if (etlEmp.getEmpname() != null) {
                    user.setRealname(etlEmp.getEmpname());
                    user.setDepartment(dtMap.get(empCodeMap.get(etlEmp.getEmpcode()) + "_" + orgMap.get(empCodeMap.get(etlEmp.getEmpcode()))));
                }
                if (etlEmp.getPemail() != null) {
                    user.setEmail(etlEmp.getPemail());
                }
                if (etlEmp.getMobileno() != null) {
                    user.setMobile(etlEmp.getMobileno());
                }
                userMap.put(etlEmp.getEmpcode() + "_" + orgMap.get(empCodeMap.get(etlEmp.getEmpcode())), user);
                users.add(user);
            }
            if (etlEmp.getEmpname() != null) {
                user.setRealname(etlEmp.getEmpname());
            }
            if (etlEmp.getPemail() != null) {
                user.setEmail(etlEmp.getPemail());
            }
            if (etlEmp.getMobileno() != null) {
                user.setMobile(etlEmp.getMobileno());
            }
        }
        etlEmps.removeAll(empDeleteList);
        //这里报错的话大概率是uer表的电话号码有正则匹配
        userRepository.saveAll(users);

        /*同步角色表*/
        //当前法人统一为01
        List<EtlRole> etlRoles = etlRoleRepository.findAll();
        List<Role> roleList = roleRepository.findAll();
        Map<String, Role> roleMap = roleList.stream().collect(Collectors.toMap(e -> e.getCode() + "_" + e.getTenant().getId(), e -> e));
        List<EtlRole> roleDeleteList = new ArrayList<>();
        for (EtlRole etlRole : etlRoles) {
            if (etlRole.getRolecode() == null) {
                log.error("etlRole表中id为" + etlRole.getId() + "的角色编码不能为空!");
                roleDeleteList.add(etlRole);
                continue;
            }
            if (etlRole.getRolename() == null) {
                log.error("角色编码为" + etlRole.getRolecode() + "的角色名称不能为空!");
                roleDeleteList.add(etlRole);
                continue;
            }

            Role role;
            //校验机构编码和法人是否一致
            if (roleMap.get(etlRole.getRolecode() + "_" + "01") != null) {
                role = roleMap.get(etlRole.getRolecode() + "_" + "01");
                role.setName(etlRole.getRolename());
            } else {
                role = new Role();
                role.setTenant(tenantMap.get("01"));
                role.setCode(etlRole.getRolecode());
                role.setName(etlRole.getRolename());
                role.setEnabled(true);
                roleList.add(role);
            }
        }
        etlRoles.removeAll(roleDeleteList);
        roleRepository.saveAll(roleList);
        //同步角色_用户表
        List<EtlEmpRole> empEmpRoleList = etlEmpRoleRepository.findAll();
        List<EtlEmpRole> empRoleDeleteList = new ArrayList<>();
        for (EtlEmpRole etlEmpRole : empEmpRoleList) {
            if (etlEmpRole.getEmpcode() == null) {
                log.error("etlEmpRole表中id为" + etlEmpRole.getId() + "人员编码empcode不能为空!");
                empRoleDeleteList.add(etlEmpRole);
                continue;
            }
            if (empCodeMap.get(etlEmpRole.getEmpcode()) == null) {
                log.error("etlEmpRole表中id为" + etlEmpRole.getId() + "机构编码orgcode不能为空!");
                empRoleDeleteList.add(etlEmpRole);
                continue;
            }
            if (orgMap.get(empCodeMap.get(etlEmpRole.getEmpcode())) == null) {
                log.error("角色编码为" + etlEmpRole.getRolecode() + "关联的机构的法人代码不能为空!");
                empRoleDeleteList.add(etlEmpRole);
                continue;
            }
            //取出机构里面的法人id
            String tenantId = orgMap.get(empCodeMap.get(etlEmpRole.getEmpcode()));
            roleList.forEach(role -> {
                Set<User> user;
                if (etlEmpRole.getRolecode().equals(role.getCode())) {
                    user = role.getUsers();
                    user.add(userMap.get(etlEmpRole.getEmpcode() + "_" + tenantId));
                }
            });
        }
        empEmpRoleList.removeAll(empDeleteList);
        roleRepository.saveAll(roleList);
    }

    @Override
    public void text() {
        List<Department> departments = departmentRepository.findAll();
        Optional<Department> byId = departmentRepository.findById(2);
        Optional<Department> byIds = departmentRepository.findById(684);
        //更新code_path,name_path字段
        Map<Integer, Department> pathMap = departments.stream().collect
                (HashMap::new, (m, v) -> m.put(v.getId(), v.getParent()), HashMap::putAll);
        for (Department department : departments) {
            if (department.getParent()!=null && department.getParent().getId().equals(684)){
                department.setParent(byId.get());
            }
            if (department.getParent()!=null && department.getParent().getId().equals(2)){
                department.setParent(byIds.get());
            }
        }
        for (Department department : departments) {
            department.setCodePath(getCodePath(department,pathMap));
            department.setNamePath(getNamePath(department,pathMap));
        }
        departmentRepository.saveAll(departments);
    }

    //获取code_path
    private String getCodePath(Department department, Map<Integer, Department> pathMap) {
        String codePath;
        if (pathMap.get(department.getId()) == null) {
            codePath = "/" + department.getCode();
        } else {
            codePath = getCodePath(pathMap.get(department.getId()), pathMap) + "/" + department.getCode();
        }
        return codePath;
    }
    private String getNamePath(Department department, Map<Integer, Department> pathMap){
        String namePath;
        if (pathMap.get(department.getId()) == null) {
            namePath = "/"+department.getName();
        } else {
            namePath=getNamePath(pathMap.get(department.getId()), pathMap) +"/"+department.getName();
        }
        return namePath;
    }

}
