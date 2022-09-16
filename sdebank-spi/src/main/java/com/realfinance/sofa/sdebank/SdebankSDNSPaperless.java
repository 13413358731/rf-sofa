package com.realfinance.sofa.sdebank;

import cfca.paperless.client.bean.SealUserInfo;
import cfca.paperless.client.bean.SignLocation;
import cfca.paperless.client.servlet.PaperlessClient;
import cfca.paperless.client.util.IoUtil;
import cfca.paperless.client.util.StrategyUtil;
import com.realfinance.sofa.sdebank.model.NoteDto;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * 顺德农商行无纸化印章
 */
public class SdebankSDNSPaperless {

    // 部署无纸化服务的IP
    public String host = "172.16.249.34";
    // 部署无纸化服务的Port
    public String port = "9081";

    public String protocol = "http://";
    // 柜员编码，需要在无纸化管理系统中添加此柜员的信息，登录管理页面->选择系统管理->选择柜员管理->添加柜员信息，
    public String operatorCode = "001";
    //渠道编码(可以为空)，需要在无纸化管理系统中添加此渠道的信息，登录管理页面->选择系统管理->选择渠道管理->添加渠道信息
    public String channelCode = "";
    // 印章编码和印章密码(后续申请)
    public String sealCode = "shouxin2";

    public String sealPassword = "cfca1234";

    public String keyword = "年";

    //2.5  PDF自动化签章接口
    public byte[] sealAutoPdf(InputStream in) throws Exception {
        //传入需要盖章的文件路径
        PaperlessClient clientBean = new PaperlessClient(getUrl("PaperlessServlet"));

        byte[] pdfFileData = IoUtil.read(in);
        //签章人,签章地点,签章理由(可以为空)
        SealUserInfo sealUserInfo = new SealUserInfo();
        //印章的大小 100，代表100px
        sealUserInfo.setSealImageSize(170);
        // 3=关键字签章 位置风格：上:U；下:D；左:L；右:R；中:C 横轴偏移,纵轴偏移
        SignLocation signLocation = new SignLocation(keyword, "C", 0, 30);
        // 生成签章策略文件
        String sealStrategyXml = StrategyUtil.createSealStrategyXml(sealCode, sealPassword, "", sealUserInfo, signLocation);
        // 取得签章后的PDF文件数据
        byte[] sealedPdfData = clientBean.sealAutoPdf(pdfFileData, sealStrategyXml, operatorCode, channelCode);
        //返回信息处理
        String ErrorMessage = StringUtils.substringBetween(new String(sealedPdfData, StandardCharsets.UTF_8), "<ErrorMessage>", "</ErrorMessage>");
        if (ErrorMessage != null) {
            throw new RuntimeException(ErrorMessage);
        }

        return sealedPdfData;
    }


    /**
     * 无纸化基本接口的访问URL
     *
     * @return
     */
    protected String getUrl(String url) {
        return protocol + host + ":" + port + "/PaperlessServer/"+url;
    }

    /**
     * 采购结果通知书模板
     */
    public String note(NoteDto dto) {
        //换行
        String ln = "\r\n";
        //标题
        String title = StringUtils.center("采购结果通知书", 30);
        //内容
        String body = getString(dto);
        String content =
                StringUtils.rightPad("参与单位：", 30)
                        + ln
                        + body
                        +ln
                        + StringUtils.rightPad(StringUtils.leftPad("特此通知。", 7), 30)
                        + ln + ln
                        + StringUtils.leftPad("广东顺德农村商业银行股份有限公司", 30)
                        + ln
                        + StringUtils.leftPad(dto.getTime(), 46);
        return title + ln + content;
    }

    private String getString(NoteDto dto) {
        String body = "广东顺德农村商业银行股份有限公司（以下简称：我行）"+"\r\n"+ dto.getProjectName() + "（编号：" + dto.getProjectNo() + "）的采购，" +"\r\n"+
                "经评审确定成交单位为：" + StringUtils.join(dto.getSupplierNames(), "，") +"，请成交单位于本通知"+"\r\n"+"发出之日5天内联系我行授信管理部：" +
                dto.getRealName() + "（电话：" + dto.getMobile() + "）"+"\r\n"+"协商合同签订事宜，并于本通知发出之日起30天内签订正式合同。";

        return body;
    }

    //4.4 PDF自动化骑缝章签章接口
    public byte[]  sealAutoCrossPdf(InputStream in) throws Exception {
        //传入需要盖章的文件路径
        PaperlessClient clientBean = new PaperlessClient(getUrl("PaperlessAssistServlet"));

        byte[] pdfFileData = IoUtil.read(in);
        //签章人,签章地点,签章理由(不可以为空)
        SealUserInfo sealUserInfo = new SealUserInfo("业务人员","顺德","申请盖章");
        //起始页，如果为FromPage和ToPage都为0时代表全部页面都盖章
        SignLocation signLocation = new SignLocation(0,0);
        String crossStyle="3";
        // 生成签章策略文件
        String crossSealStrategyXml = crossSealStrategyXml(sealCode, sealPassword, sealUserInfo, signLocation,crossStyle);
        // 取得签章后的PDF文件数据
        byte[] sealedPdfData = clientBean.sealAutoCrossPdf(pdfFileData,null,crossSealStrategyXml, operatorCode, channelCode);
        //返回信息处理
        String ErrorMessage = StringUtils.substringBetween(new String(sealedPdfData, StandardCharsets.UTF_8), "<ErrorMessage>", "</ErrorMessage>");
        if (ErrorMessage != null) {
            throw new RuntimeException(ErrorMessage);
        }

        return sealedPdfData;
    }

    /**
     * 生成骑缝章策略文件
     * @param sealCode 印章编码（不能为空）
     * @param sealPassword 印章密码（不能为空）
     * @param sealUserInfo 签章人 签章地点 签章理由(不能为空)
     * @param signLocation 起始页，如果为FromPage和ToPage都为0时代表全部页面都盖章
     * @param crossStyle 骑缝章风格 1：上下各一半2：左右各一半3：上下骑缝4：左右骑缝5：左骑缝6：右骑缝
     * @return
     */
    private static String  crossSealStrategyXml(String sealCode,String sealPassword,SealUserInfo sealUserInfo,SignLocation signLocation,String crossStyle){
        StringBuilder crossSealStrategyXml = new StringBuilder("<Request>");
        crossSealStrategyXml.append("<SealCode>").append(sealCode).append("</SealCode>");
        crossSealStrategyXml.append("<SealPassword>").append(sealPassword).append("</SealPassword>");
        crossSealStrategyXml.append("<SealPerson>").append(sealUserInfo.getUserName()).append("</SealPerson>");
        crossSealStrategyXml.append("<SealLocation>").append(sealUserInfo.getSealLocation()).append("</SealLocation>");
        crossSealStrategyXml.append("<SealReason>").append(sealUserInfo.getSealReason()).append("</SealReason>");
        crossSealStrategyXml.append("<FromPage>").append(signLocation.getToPage()).append("</FromPage>");
        crossSealStrategyXml.append("<ToPage>").append(signLocation.getFromPage()).append("</ToPage>");
        crossSealStrategyXml.append("<CrossStyle>").append(crossStyle).append("</CrossStyle>");
        crossSealStrategyXml.append("</Request>");
        return crossSealStrategyXml.toString();
    }
}
