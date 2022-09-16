package com.realfinance.sofa.sdebank;


import com.alipay.sofa.rpc.common.utils.BeanUtils;
import com.alipay.sofa.rpc.common.utils.JSONUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.realfinance.sofa.sdebank.model.CreditDto;
import com.realfinance.sofa.sdebank.model.ElementBusIInfoDto;
import com.realfinance.sofa.sdebank.model.EquityPenetrationDto;
import com.realfinance.sofa.sdebank.model.SupplierRelationshipDto;
import com.realfinance.sofa.sdebank.response.ElementBusIInfoResponse;
import com.realfinance.sofa.sdebank.response.PunishmentResponse;
import com.realfinance.sofa.sdebank.response.TokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.filtering;
import static java.util.stream.Collectors.toList;

/**
 * 企查查-供应商股权穿透
 */
public class SdebankEquityPenetration {

    private static final Logger log = LoggerFactory.getLogger(SdebankEquityPenetration.class);


    private static final String CLIENT_ID = "baea08b8-93ee-4d07-a8aa-affe9e7c1a64";

    private static final String CLIENT_SECRET = "SoxBvraXpVjvN5ttf9356b0OAmsXwqjHFxVknSzTRqBxQZXWvfinp7ZPcUYQn1fR";

    //默认:client_credentials 获取token信息接口中的参数
    private static final String GRANT_TYPE = "client_credentials";

    private static final String TOKEN_URL = "http://107.255.1.204:8611/poseidon/integration/oauth/token?grant_type=" + GRANT_TYPE;

    //内部自己定义
    //queryTypeParam调用方;当查询类型queryType为EXTERNAL_DATASOURCE，STOCK_DATASOURCE时该字段必输，其它情况下可为空
    private static final String QUERY_TYPE_PARAM = "EQUITY_PENETRATION";

    //计算client_id,client_secret的base64值
    private String getBase64() {
        Base64.Encoder encoder = Base64.getEncoder();
        String client = CLIENT_ID + ":" + CLIENT_SECRET;
        byte[] bytes = new byte[0];
        try {
            bytes = client.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String encodedClient = encoder.encodeToString(bytes);
        return encodedClient;
    }

    //获取token信息
    public TokenResponse getToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Basic " + getBase64());
        HttpEntity<String> requestEntity = new HttpEntity<>(null, headers);
        //执行http请求
        ResponseEntity<String> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, requestEntity, String.class);
        //获取其中的token认证信息
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonToken = null;
        try {
            jsonToken = objectMapper.readTree(response.getBody());
        } catch (IOException e) {
            log.error("数据接收失败", e);
        }
        if (response.getStatusCodeValue() == 401) {
            throw new RuntimeException("Bad credentials，client_id或client_secret错误!");
        } else if (response.getStatusCodeValue() == 500) {
            throw new RuntimeException("服务器内部异常!");
        } else if (jsonToken.get("access_token").toString() == null || jsonToken.get("access_token").toString() == "") {
            throw new RuntimeException("token信息获取失败!");
        }
        TokenResponse tokenResponse = null;
        try {
            tokenResponse = objectMapper.readValue(jsonToken.toString(), TokenResponse.class);
        } catch (IOException e) {
            log.error("数据接收失败", e);
        }
        return tokenResponse;
    }

    //2.26 元素征信工商信息(深度)(apiCode=elementbusiinfo)
    public ElementBusIInfoDto elementBusIInfo(TokenResponse tokenResponse, EquityPenetrationDto dto) {
        Objects.requireNonNull(tokenResponse);
        Objects.requireNonNull(dto.getName(), "公司名称不能为空!");
        //apiCode为接口编码
        //batch是否批量查询，true/false
        //queryType为查询类型 (STOCK_DATASOURCE:存量数据源，EXTERNAL_DATASOURCE:补充数据源CREDIT_QUERY:征信报告查询)
        String url = "http://107.255.1.204:9090/dataapi/execute?apiCode=elementbusiinfo&batch=false&queryType=EXTERNAL_DATASOURCE&queryTypeParam=" + QUERY_TYPE_PARAM;
        Map<String, String> map = new HashMap<>();
        //企业名称
        map.put("corpName", dto.getName());
        //统一社会信用代码
        if (dto.getUnifiedSocialCreditCode() != null) {
            map.put("corpCreditNo", dto.getUnifiedSocialCreditCode());
        }
        Map<String, Map<String, String>> mapData = new HashMap<>();
        mapData.put("requestData", map);
        String requestData = JSONUtils.toJSONString(mapData);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", tokenResponse.getToken_type() + " " + tokenResponse.getAccess_token());
        HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        } catch (IOException e) {
            log.error("数据接收失败", e);
        }
        if (jsonNode.get("retCode").toString().equals("00")) {
            throw new RuntimeException(String.valueOf(jsonNode.get("retMsg")));
        }
        if (jsonNode.get("data").toString().equals("{}")) {
            throw new RuntimeException("请求成功,但没有返回值(数据聚合平台API异常)");
        }
        JsonNode data = jsonNode.get("data");
        //不存在的字段,不被序列化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        ElementBusIInfoResponse elementBusIInfo = null;
        try {
            elementBusIInfo = objectMapper.readValue(data.toString(), ElementBusIInfoResponse.class);
        } catch (IOException e) {
            log.error("数据接收失败", e);
        }
        ElementBusIInfoDto elementBusIInfoDto = check(elementBusIInfo);
        elementBusIInfoDto.setName(dto.getName());

        return elementBusIInfoDto;
    }

    private ElementBusIInfoDto check(ElementBusIInfoResponse elementBusIInfo) {
        if (elementBusIInfo == null) {
            throw new RuntimeException("数据聚合平台API返回数据为null!");
        }
        if (elementBusIInfo.getElementbusiinfo_creditcode() == null || elementBusIInfo.getElementbusiinfo_frname() == null
                || elementBusIInfo.getElementbusiinfo_shareholders_0_fundedratio() == null || elementBusIInfo.getElementbusiinfo_shareholders_0_invtype() == null
                || elementBusIInfo.getElementbusiinfo_shareholders_0_shaname() == null){
            throw new RuntimeException("数据聚合平台API返回数据中存在字段为null的数据!");
        }
        ElementBusIInfoDto elementBusIInfoDto = new ElementBusIInfoDto();
        elementBusIInfoDto.setStatutoryRepresentative(elementBusIInfo.getElementbusiinfo_frname());
        elementBusIInfoDto.setShareholderNames(elementBusIInfo.getElementbusiinfo_shareholders_0_shaname());
        elementBusIInfoDto.setShareholderTypes(elementBusIInfo.getElementbusiinfo_shareholders_0_invtype());
        elementBusIInfoDto.setEquityRatio(elementBusIInfo.getElementbusiinfo_shareholders_0_fundedratio());
        elementBusIInfoDto.setUnifiedSocialCreditCode(elementBusIInfo.getElementbusiinfo_creditcode());
        return elementBusIInfoDto;
    }

    //2.26 元素征信工商信息 供应商关联关系
    public SupplierRelationshipDto SupplierRelationship(TokenResponse tokenResponse, List<ElementBusIInfoDto> list) {
        for (ElementBusIInfoDto e : list) {
            Objects.requireNonNull(e, "传入的供应商信息有误!");
        }
        SupplierRelationshipDto dto = new SupplierRelationshipDto();
        //3
        List<String> list3 = list.get(0).getShareholderNames().stream().
                filter(item -> list.get(1).getShareholderNames().contains(item)).collect(toList());
        //4
        Optional<Double> firstA = list.get(0).getEquityRatio().stream().filter(e -> e > 50).findFirst();
        Optional<Double> firstB = list.get(1).getEquityRatio().stream().filter(e -> e > 50).findFirst();

        //5
        Optional<String> firstNameA = list.get(0).getShareholderNames().stream().filter(item -> item.equals(list.get(1).getName())).findFirst();
        Optional<String> firstNameB = list.get(1).getShareholderNames().stream().filter(item -> item.equals(list.get(0).getName())).findFirst();

        //1:股权占比大于50%
        if (list.get(1).getShareholderNames().contains(list.get(0).getName())) {
            if (list.get(1).getEquityRatio().get(list.get(1).getShareholderNames().indexOf(list.get(0).getName())) > 50) {
                dto.setList(list);
                dto.setType(1);
            }
        } else if (list.get(0).getShareholderNames().contains(list.get(1).getName())) {
            if (list.get(0).getEquityRatio().get(list.get(0).getShareholderNames().indexOf(list.get(0).getName())) > 50) {
                dto.setList(list);
                dto.setType(1);
            }
        }
        //2:比较法人是否一致
        else if (list.get(0).getStatutoryRepresentative().equals(list.get(1).getStatutoryRepresentative())) {
            dto.setList(list);
            dto.setType(2);
        }
        //3:同一母公司控股（指控股大于50%） 两个公司都要大于50%
        else if (list3.size() != 0) {
            for (String l3 : list3) {
                if (list.get(0).getEquityRatio().get(list.get(0).getShareholderNames().indexOf(l3)) > 50
                        && list.get(1).getEquityRatio().get(list.get(1).getShareholderNames().indexOf(l3)) > 50) {
                    dto.setList(list);
                    dto.setType(3);
                    break;
                }
            }
        }
        //4:两个公司为上下级关系 A公司股东名称为公司且占比大于50%的D公司,D公司股东名称为公司且占比大于50%的公司为B公司;
        else if (!firstA.isEmpty()) {
            //比较A公司股东名中D公司是否和B公司一致
            if (list.get(0).getShareholderTypes().get(list.get(0).getEquityRatio().indexOf(firstA.get())) == "企业法人") {
                EquityPenetrationDto dto41 = new EquityPenetrationDto();
                dto41.setName(list.get(0).getShareholderNames().get(list.get(0).getEquityRatio().indexOf(firstA.get())));
                ElementBusIInfoDto elementBusIInfoDto1 = elementBusIInfo(tokenResponse, dto41);
                Optional<Double> first1 = elementBusIInfoDto1.getEquityRatio().stream().filter(e -> e > 50).findFirst();
                //比较D公司股东名中E公司是否和B公司一致
                if (!first1.isEmpty()) {
                    if (list.get(1).getName().equals(elementBusIInfoDto1.getShareholderNames().get(elementBusIInfoDto1.getEquityRatio().indexOf(first1.get())))) {
                        list.add(elementBusIInfoDto1);
                        dto.setList(list);
                        dto.setType(4);

                    } else if (elementBusIInfoDto1.getShareholderTypes().get(elementBusIInfoDto1.getEquityRatio().indexOf(first1.get())) == "企业法人") {
                        EquityPenetrationDto dto42 = new EquityPenetrationDto();
                        dto42.setName(elementBusIInfoDto1.getShareholderNames().get(elementBusIInfoDto1.getEquityRatio().indexOf(first1.get())));
                        ElementBusIInfoDto elementBusIInfoDto2 = elementBusIInfo(tokenResponse, dto42);
                        Optional<Double> first2 = elementBusIInfoDto2.getEquityRatio().stream().filter(e -> e > 50).findFirst();
                        //比较E公司股东名中公司是否和B公司一致
                        if (!first2.isEmpty()) {
                            if (list.get(1).getName().equals(elementBusIInfoDto2.getShareholderNames().get(elementBusIInfoDto2.getEquityRatio().indexOf(first2.get())))) {
                                list.add(elementBusIInfoDto1);
                                list.add(elementBusIInfoDto2);
                                dto.setList(list);
                                dto.setType(4);
                            }
                        }
                    }
                }
            }

        } else if (!firstB.isEmpty()) {
            //比较B公司股东名中F公司是否和A公司一致
            if (list.get(1).getShareholderTypes().get(list.get(1).getEquityRatio().indexOf(firstB.get())) == "企业法人") {
                EquityPenetrationDto dto41 = new EquityPenetrationDto();
                dto41.setName(list.get(1).getShareholderNames().get(list.get(1).getEquityRatio().indexOf(firstB.get())));
                ElementBusIInfoDto elementBusIInfoDto1 = elementBusIInfo(tokenResponse, dto41);
                Optional<Double> first1 = elementBusIInfoDto1.getEquityRatio().stream().filter(e -> e > 50).findFirst();
                //比较F公司股东名中G公司是否和A公司一致
                if (!first1.isEmpty()) {
                    if (list.get(0).getName().equals(elementBusIInfoDto1.getShareholderNames().get(elementBusIInfoDto1.getEquityRatio().indexOf(first1.get())))) {
                        list.add(elementBusIInfoDto1);
                        dto.setList(list);
                        dto.setType(4);

                    } else if (elementBusIInfoDto1.getShareholderTypes().get(elementBusIInfoDto1.getEquityRatio().indexOf(first1.get())) == "企业法人") {
                        EquityPenetrationDto dto42 = new EquityPenetrationDto();
                        dto42.setName(elementBusIInfoDto1.getShareholderNames().get(elementBusIInfoDto1.getEquityRatio().indexOf(first1.get())));
                        ElementBusIInfoDto elementBusIInfoDto2 = elementBusIInfo(tokenResponse, dto42);
                        Optional<Double> first2 = elementBusIInfoDto2.getEquityRatio().stream().filter(e -> e > 50).findFirst();
                        //比较G公司股东名中公司是否和A公司一致
                        if (!first2.isEmpty()) {
                            if (list.get(0).getName().equals(elementBusIInfoDto2.getShareholderNames().get(elementBusIInfoDto2.getEquityRatio().indexOf(first2.get())))) {
                                list.add(elementBusIInfoDto1);
                                list.add(elementBusIInfoDto2);
                                dto.setList(list);
                                dto.setType(4);
                            }
                        }
                    }
                }
            }


        }
        //5非控股的合并持股大于50%
        else if (!firstNameA.isEmpty()) {
            //占比
            Double proportion = 0.01 * list.get(0).getEquityRatio().get(list.get(0).getShareholderNames().indexOf(list.get(1).getName()));
            List<ElementBusIInfoDto> addList = new ArrayList<>();
            for (String name : list.get(0).getShareholderNames()) {
                //非B公司 且 为公司
                if (name != list.get(1).getName() && list.get(0).getShareholderTypes().get(list.get(0).getShareholderNames().indexOf(name)) == "企业法人") {
                    EquityPenetrationDto dto1 = new EquityPenetrationDto();
                    dto1.setName(name);
                    ElementBusIInfoDto elementBusIInfoDto = elementBusIInfo(tokenResponse, dto1);
                    if (elementBusIInfoDto.getShareholderNames().contains(list.get(1).getName())) {
                        Double aDouble = 0.01 * elementBusIInfoDto.getEquityRatio().get(elementBusIInfoDto.getShareholderNames().indexOf(list.get(1).getName()));
                        Double aDouble1 = 0.01 * list.get(0).getEquityRatio().get(list.get(0).getShareholderNames().indexOf(name));
                        proportion = proportion + (aDouble * aDouble1);
                        addList.add(elementBusIInfoDto);
                    }
                }
            }
            if ((proportion * 100) > 50) {
                list.addAll(addList);
                dto.setType(5);
                dto.setProportion(proportion * 100);
                dto.setList(list);
            }
        } else if (!firstNameB.isEmpty()) {
            //占比
            Double proportion = list.get(1).getEquityRatio().get(list.get(1).getShareholderNames().indexOf(list.get(0).getName()));
            List<ElementBusIInfoDto> addList = new ArrayList<>();
            for (String name : list.get(1).getShareholderNames()) {
                //非B公司 且 为公司
                if (name != list.get(0).getName() && list.get(1).getShareholderTypes().get(list.get(1).getShareholderNames().indexOf(name)) == "企业法人") {
                    EquityPenetrationDto dto1 = new EquityPenetrationDto();
                    dto1.setName(name);
                    ElementBusIInfoDto elementBusIInfoDto = elementBusIInfo(tokenResponse, dto1);
                    if (elementBusIInfoDto.getShareholderNames().contains(list.get(0).getName())) {
                        Double aDouble = elementBusIInfoDto.getEquityRatio().get(elementBusIInfoDto.getShareholderNames().indexOf(list.get(0).getName()));
                        proportion = proportion + aDouble * list.get(1).getEquityRatio().get(list.get(1).getShareholderNames().indexOf(name));
                        addList.add(elementBusIInfoDto);
                    }
                }
            }
            if (proportion > 50) {
                list.addAll(addList);
                dto.setType(5);
                dto.setList(list);
            }
        }
        return dto;
    }

    //2.48 融安E信_企业风险明细接口(apiCode= raex_company_fxmx)
    public List<CreditDto> raExCompanyFxMx(TokenResponse tokenResponse, EquityPenetrationDto dto) {
        Objects.requireNonNull(tokenResponse);
        Objects.requireNonNull(dto);
        //apiCode为接口编码
        //batch是否批量查询，true/false
        //queryType为查询类型 (STOCK_DATASOURCE:存量数据源，EXTERNAL_DATASOURCE:补充数据源CREDIT_QUERY:征信报告查询)
        String url = "http://107.255.1.204:9090/dataapi/execute?apiCode=raex_company_fxmx&batch=false&queryType=EXTERNAL_DATASOURCE&queryTypeParam=" + QUERY_TYPE_PARAM;
        Map<String, String> map = new HashMap<>();
        //企业名称
        map.put("corpName", dto.getName());
        //统一社会信用代码
        map.put("corpCreditNo", dto.getUnifiedSocialCreditCode());
        Map<String, Map<String, String>> mapData = new HashMap<>();
        mapData.put("requestData", map);
        String requestData = JSONUtils.toJSONString(mapData);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        headers.add("Authorization", tokenResponse.getToken_type() + " " + tokenResponse.getAccess_token());
        HttpEntity<String> requestEntity = new HttpEntity<>(requestData, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(response.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (jsonNode.get("retCode").toString().equals("00")) {
            throw new RuntimeException(String.valueOf(jsonNode.get("retMsg")));
        }
        if (jsonNode.get("data").toString().equals("{}")) {
            throw new RuntimeException("数据聚合平台API返回数据异常");
        }
        JsonNode data = jsonNode.get("data");
        //不存在的字段,不被序列化
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        PunishmentResponse punishment = null;
        try {
            punishment = objectMapper.readValue(data.toString(), PunishmentResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<CreditDto> dtos = new ArrayList<>();
        //严重违法
        if (punishment.getRaex_company_yzwf_data_0_tdata_0_INSN().size() != 0
                || punishment.getRaex_company_yzwf_data_0_tdata_0_INREASON().size() != 0
                || punishment.getRaex_company_yzwf_data_0_tdata_0_INDATE().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setDocumentNumbers(punishment.getRaex_company_yzwf_data_0_tdata_0_INSN().size() == 0 ? null : punishment.getRaex_company_yzwf_data_0_tdata_0_INSN().get(0));
            credit.setPenaltyCauses(punishment.getRaex_company_yzwf_data_0_tdata_0_INREASON().size() == 0 ? null : punishment.getRaex_company_yzwf_data_0_tdata_0_INREASON().get(0));
            credit.setPenaltyDates(punishment.getRaex_company_yzwf_data_0_tdata_0_INDATE().size() == 0 ? null : punishment.getRaex_company_yzwf_data_0_tdata_0_INDATE().get(0));
            credit.setPenaltyType("严重违法");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        //海关失信
        if (punishment.getRaex_company_hgsx_data_0_tdata_0_HGBM().size() != 0
                || punishment.getRaex_company_hgsx_data_0_tdata_0_XYDJ().size() != 0
                || punishment.getRaex_company_hgsx_data_0_tdata_0_DJRDSJ().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setDocumentNumbers(punishment.getRaex_company_hgsx_data_0_tdata_0_HGBM().size() == 0 ? null : punishment.getRaex_company_hgsx_data_0_tdata_0_HGBM().get(0));
            credit.setPenaltyItemNames(punishment.getRaex_company_hgsx_data_0_tdata_0_XYDJ().size() == 0 ? null : punishment.getRaex_company_hgsx_data_0_tdata_0_XYDJ().get(0));
            credit.setPenaltyDates(punishment.getRaex_company_hgsx_data_0_tdata_0_DJRDSJ().size() == 0 ? null : punishment.getRaex_company_hgsx_data_0_tdata_0_DJRDSJ().get(0));
            credit.setPenaltyType("海关失信");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        //政府采购违法
        if (punishment.getRaex_company_cgwf_data_0_tdata_0_C_BAK_NO_6().size() != 0
                || punishment.getRaex_company_cgwf_data_0_tdata_0_C_BAK_NO_7().size() != 0
                || punishment.getRaex_company_cgwf_data_0_tdata_0_C_BAK_NO_1().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setPenaltyItemNames(punishment.getRaex_company_cgwf_data_0_tdata_0_C_BAK_NO_6().size() == 0 ? null : punishment.getRaex_company_cgwf_data_0_tdata_0_C_BAK_NO_6().get(0));
            credit.setPenaltyCauses(punishment.getRaex_company_cgwf_data_0_tdata_0_C_BAK_NO_7().size() == 0 ? null : punishment.getRaex_company_cgwf_data_0_tdata_0_C_BAK_NO_7().get(0));
            credit.setPenaltyDates(punishment.getRaex_company_cgwf_data_0_tdata_0_C_BAK_NO_1().size() == 0 ? null : punishment.getRaex_company_cgwf_data_0_tdata_0_C_BAK_NO_1().get(0));
            credit.setPenaltyType("政府采购违法");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        //电子商务失信
        if (punishment.getRaex_company_dzswsx_data_0_tdata_0_C_BAK_NO_7().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setPenaltyItemNames(punishment.getRaex_company_dzswsx_data_0_tdata_0_C_BAK_NO_7().get(0));
            credit.setPenaltyType("电子商务失信");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        //涉金融黑名单
        if (punishment.getRaex_company_sjrhmd_data_0_tdata_0_C_BAK_NO_7().size() != 0
                || punishment.getRaex_company_sjrhmd_data_0_tdata_0_C_BAK_NO_6().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setDocumentNumbers(punishment.getRaex_company_sjrhmd_data_0_tdata_0_C_BAK_NO_7().size() == 0 ? null : punishment.getRaex_company_sjrhmd_data_0_tdata_0_C_BAK_NO_7().get(0));
            credit.setPenaltyItemNames(punishment.getRaex_company_sjrhmd_data_0_tdata_0_C_BAK_NO_6().size() == 0 ? null : punishment.getRaex_company_sjrhmd_data_0_tdata_0_C_BAK_NO_6().get(0));
            credit.setPenaltyType("涉金融黑名单");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        //环保违规处罚
        if (punishment.getRaex_company_hbwg_data_0_tdata_0_wh().size() != 0
                || punishment.getRaex_company_hbwg_data_0_tdata_0_fwmc().size() != 0
                || punishment.getRaex_company_hbwg_data_0_tdata_0_fwrq().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setDocumentNumbers(punishment.getRaex_company_hbwg_data_0_tdata_0_wh().size() == 0 ? null : punishment.getRaex_company_hbwg_data_0_tdata_0_wh().get(0));
            credit.setPenaltyItemNames(punishment.getRaex_company_hbwg_data_0_tdata_0_fwmc().size() == 0 ? null : punishment.getRaex_company_hbwg_data_0_tdata_0_fwmc().get(0));
            credit.setPenaltyDates(punishment.getRaex_company_hbwg_data_0_tdata_0_fwrq().size() == 0 ? null : punishment.getRaex_company_hbwg_data_0_tdata_0_fwrq().get(0));
            credit.setPenaltyType("环保违规处罚");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        //税务行政处罚
        if (punishment.getRaex_company_swxzcf_data_0_tdata_0_C_ILLEGAL().size() != 0
                || punishment.getRaex_company_swxzcf_data_0_tdata_0_C_EVAL_REASON().size() != 0
                || punishment.getRaex_company_swxzcf_data_0_tdata_0_D_EVAL_DATE().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setPenaltyItemNames(punishment.getRaex_company_swxzcf_data_0_tdata_0_C_ILLEGAL().size() == 0 ? null : punishment.getRaex_company_swxzcf_data_0_tdata_0_C_ILLEGAL().get(0));
            credit.setPenaltyCauses(punishment.getRaex_company_swxzcf_data_0_tdata_0_C_EVAL_REASON().size() == 0 ? null : punishment.getRaex_company_swxzcf_data_0_tdata_0_C_EVAL_REASON().get(0));
            credit.setPenaltyDates(punishment.getRaex_company_swxzcf_data_0_tdata_0_D_EVAL_DATE().size() == 0 ? null : punishment.getRaex_company_swxzcf_data_0_tdata_0_D_EVAL_DATE().get(0));
            credit.setPenaltyType("税务行政处罚");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        //假冒专利行为
        if (punishment.getRaex_company_jmzlxw_data_0_tdata_0_C_BAK_NO_7().size() != 0
                || punishment.getRaex_company_jmzlxw_data_0_tdata_0_C_BAK_NO_9().size() != 0
                || punishment.getRaex_company_jmzlxw_data_0_tdata_0_C_BAK_NO_8().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setDocumentNumbers(punishment.getRaex_company_jmzlxw_data_0_tdata_0_C_BAK_NO_7().size() == 0 ? null : punishment.getRaex_company_jmzlxw_data_0_tdata_0_C_BAK_NO_7().get(0));
            credit.setPenaltyItemNames(punishment.getRaex_company_jmzlxw_data_0_tdata_0_C_BAK_NO_9().size() == 0 ? null : punishment.getRaex_company_jmzlxw_data_0_tdata_0_C_BAK_NO_9().get(0));
            credit.setPenaltyCauses(punishment.getRaex_company_jmzlxw_data_0_tdata_0_C_BAK_NO_8().size() == 0 ? null : punishment.getRaex_company_jmzlxw_data_0_tdata_0_C_BAK_NO_8().get(0));
            credit.setPenaltyType("假冒专利行为");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        //工商行政处罚
        if (punishment.getRaex_company_gsxzcf_data_0_tdata_0_PENDECNO().size() != 0
                || punishment.getRaex_company_gsxzcf_data_0_tdata_0_PENCONTENT().size() != 0
                || punishment.getRaex_company_gsxzcf_data_0_tdata_0_ILLEGACTTYPE().size() != 0
                || punishment.getRaex_company_gsxzcf_data_0_tdata_0_PUBLICDATE().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setDocumentNumbers(punishment.getRaex_company_gsxzcf_data_0_tdata_0_PENDECNO().size() == 0 ? null : punishment.getRaex_company_gsxzcf_data_0_tdata_0_PENDECNO().get(0));
            credit.setPenaltyItemNames(punishment.getRaex_company_gsxzcf_data_0_tdata_0_PENCONTENT().size() == 0 ? null : punishment.getRaex_company_gsxzcf_data_0_tdata_0_PENCONTENT().get(0));
            credit.setPenaltyCauses(punishment.getRaex_company_gsxzcf_data_0_tdata_0_ILLEGACTTYPE().size() == 0 ? null : punishment.getRaex_company_gsxzcf_data_0_tdata_0_ILLEGACTTYPE().get(0));
            credit.setPenaltyDates(punishment.getRaex_company_gsxzcf_data_0_tdata_0_PUBLICDATE().size() == 0 ? null : punishment.getRaex_company_gsxzcf_data_0_tdata_0_PUBLICDATE().get(0));
            credit.setPenaltyType("工商行政处罚");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);

        }
        //安全生产黑名单
        if (punishment.getRaex_company_pbl_data_0_tdata_0_SXXWJK().size() != 0
                || punishment.getRaex_company_pbl_data_0_tdata_0_NRLY().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setPenaltyItemNames(punishment.getRaex_company_pbl_data_0_tdata_0_SXXWJK().size() == 0 ? null : punishment.getRaex_company_pbl_data_0_tdata_0_SXXWJK().get(0));
            credit.setPenaltyCauses(punishment.getRaex_company_pbl_data_0_tdata_0_NRLY().size() == 0 ? null : punishment.getRaex_company_pbl_data_0_tdata_0_NRLY().get(0));
            credit.setPenaltyType("安全生产黑名单");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        //进出口行政处罚
        if (punishment.getRaex_company_jckxzcf_data_0_tdata_0_PENDECNO().size() != 0
                || punishment.getRaex_company_jckxzcf_data_0_tdata_0_CACHAR().size() != 0
                || punishment.getRaex_company_jckxzcf_data_0_tdata_0_PENDATE().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setDocumentNumbers(punishment.getRaex_company_jckxzcf_data_0_tdata_0_PENDECNO().size() == 0 ? null : punishment.getRaex_company_jckxzcf_data_0_tdata_0_PENDECNO().get(0));
            credit.setPenaltyItemNames(punishment.getRaex_company_jckxzcf_data_0_tdata_0_CACHAR().size() == 0 ? null : punishment.getRaex_company_jckxzcf_data_0_tdata_0_CACHAR().get(0));
            credit.setPenaltyDates(punishment.getRaex_company_jckxzcf_data_0_tdata_0_PENDATE().size() == 0 ? null : punishment.getRaex_company_jckxzcf_data_0_tdata_0_PENDATE().get(0));
            credit.setPenaltyType("进出口行政处罚");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        //环保行政
        if (punishment.getRaex_company_hbxzcf_data_0_tdata_0_jdsh().size() != 0
                || punishment.getRaex_company_hbxzcf_data_0_tdata_0_cfresult().size() != 0
                || punishment.getRaex_company_hbxzcf_data_0_tdata_0_cfyy().size() != 0
                || punishment.getRaex_company_hbxzcf_data_0_tdata_0_jdrq().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setDocumentNumbers(punishment.getRaex_company_hbxzcf_data_0_tdata_0_jdsh().size() == 0 ? null : punishment.getRaex_company_hbxzcf_data_0_tdata_0_jdsh().get(0));
            credit.setPenaltyItemNames(punishment.getRaex_company_hbxzcf_data_0_tdata_0_cfresult().size() == 0 ? null : punishment.getRaex_company_hbxzcf_data_0_tdata_0_cfresult().get(0));
            credit.setPenaltyCauses(punishment.getRaex_company_hbxzcf_data_0_tdata_0_cfyy().size() == 0 ? null : punishment.getRaex_company_hbxzcf_data_0_tdata_0_cfyy().get(0));
            credit.setPenaltyDates(punishment.getRaex_company_hbxzcf_data_0_tdata_0_jdrq().size() == 0 ? null : punishment.getRaex_company_hbxzcf_data_0_tdata_0_jdrq().get(0));
            credit.setPenaltyType("环保行政");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        //重大税收违法
        if (punishment.getRaex_company_zdsswf_data_0_tdata_0_BRK_DETAIL().size() != 0
                || punishment.getRaex_company_zdsswf_data_0_tdata_0_EVAL_DATE().size() != 0) {
            CreditDto credit = new CreditDto();
            credit.setPenaltyItemNames(punishment.getRaex_company_zdsswf_data_0_tdata_0_BRK_DETAIL().size() == 0 ? null : punishment.getRaex_company_zdsswf_data_0_tdata_0_BRK_DETAIL().get(0));
            credit.setPenaltyDates(punishment.getRaex_company_zdsswf_data_0_tdata_0_EVAL_DATE().size() == 0 ? null : punishment.getRaex_company_zdsswf_data_0_tdata_0_EVAL_DATE().get(0));
            credit.setPenaltyType("重大税收违法");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        if (true){
            CreditDto credit = new CreditDto();
            List<String> names=new ArrayList<>();
            names.add("测试123");
            names.add("测试1231");
            credit.setPenaltyItemNames(names);
            List<String> documentNumbers=new ArrayList<>();
            documentNumbers.add("测试文书号1");
            documentNumbers.add("测试文书号2");
            credit.setDocumentNumbers(documentNumbers);
            List<String> causes=new ArrayList<>();
            causes.add("原因1");
            causes.add("原因2");
            credit.setPenaltyCauses(causes);
            List<String> dates=new ArrayList<>();
            dates.add("ces 123");
            dates.add("ces 1255");
            credit.setPenaltyDates(dates);
            credit.setPenaltyType("ces违法");
            credit.setSupplierId(dto.getSupplierId());
            dtos.add(credit);
        }
        return dtos;
    }


}
