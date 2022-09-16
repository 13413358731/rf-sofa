package com.realfinance.sofa.sdebank;

import com.realfinance.sofa.common.notice.sms.Sms;
import com.realfinance.sofa.common.notice.sms.SmsException;
import com.realfinance.sofa.common.notice.sms.SmsSender;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * 顺德农商行短息短信发送实现
 */
public class SdebankSmsSender implements SmsSender {

    private static final Logger log = LoggerFactory.getLogger(SdebankSmsSender.class);

    private final String host;
    private final int port;
    private Charset charset = Charset.forName("GBK");

    public SdebankSmsSender(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public String send(Sms sms) throws SmsException {
        validate(sms);
        if (sms.getContent().length() > 150) {
            throw new SmsException("短信内容长度不能超过150");
        }
        Collection<String> errorDestAddress = new ArrayList<>();
        for (String destAddress : sms.getDestAddress()) {
            try (Socket socket = new Socket(host, port)) {
                OutputStream outputStream = socket.getOutputStream();
                String s = header() + body(sms.getTenantId(),destAddress,sms.getContent());
                String l = StringUtils.leftPad(String.valueOf(s.length()), 4, '0');
                IOUtils.write(l + s,outputStream,charset);
                socket.shutdownOutput();
                InputStream inputStream = socket.getInputStream();
                // TODO: 2021/2/2 暂时忽略结果
                String response = IOUtils.toString(inputStream,charset);
                log.info("短信平台返回的数据:"+response);
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(outputStream);
            } catch (IOException e) {
                log.error("短信发送失败",e);
                errorDestAddress.add(destAddress);
            }
        }
        if (!errorDestAddress.isEmpty()) {
            String destAddress = String.join("、", errorDestAddress);
            log.error("短信发送失败，destAddress：{}，content：{}",destAddress,sms.getContent());
            throw new SmsException("短信发送失败，目标地址：" + destAddress);
        }
        return sms.getId() == null ? UUID.randomUUID().toString() : sms.getId();
    }

    //目前只给发送验证码用的模板
    protected String header() {
        //信息来源(6)
        String Sources = StringUtils.rightPad("CAIG", 6);
        //渠道号(6)
        String Channel = StringUtils.rightPad("011002", 6);
        //机构号(6)
        String Organization = StringUtils.rightPad("", 6);
        //柜员号(7)
        String Teller = StringUtils.rightPad("", 7);
        //交易码(6)
        String TransactionCode = StringUtils.rightPad("980002", 6);
        String header = Sources +
                Channel +
                Organization +
                Teller +
                TransactionCode;
        return header;
    }
    protected String body(String tenantId, String phone, String message) {
        //原交易码(6)
        String T_ISS_MSG_UNIT_ORG_TRAN = StringUtils.rightPad("CAIG01", 6);
        //借贷标识（D-借方 C-贷方）(1)
        String T_ISS_MSG_UNIT_CR_FLAG = "C";
        //签约账号(32)
        String T_ISS_MSG_UNIT_ACTNO1 = StringUtils.rightPad("", 32);
        //短信发送的目的手机号码(20)
        String T_ISS_MSG_UNIT_MOBILE = StringUtils.rightPad(phone, 20);
        //法人代码(6)
        String T_ISS_MSG_UNIT_CORP_CODE = StringUtils.rightPad(tenantId, 6);
        //冲销交易标识(1)
        String T_ISS_MSG_UNIT_REVS_FLAG = "0";
        //户名(100)
        String T_ISS_MSG_UNIT_ACT_NAME1 = StringUtils.rightPad("", 100);
        //账号2(32)
        String T_ISS_MSG_UNIT_ACTNO2 = StringUtils.rightPad("", 32);
        //金额1(17)
        String T_ISS_MSG_UNIT_AMT1 = StringUtils.leftPad("", 17);
        //金额2(17)
        String T_ISS_MSG_UNIT_AMT2 = StringUtils.leftPad("", 17);
        //金额3(17)
        String T_ISS_MSG_UNIT_AMT3 = StringUtils.leftPad("", 17);
        //期数(5)
        String T_ISS_MSG_UNIT_ATTIME = StringUtils.rightPad("", 5);
        //交易网点(6)
        String T_ISS_MSG_UNIT_BRCH_NO1 = StringUtils.rightPad("", 6);
        //业务介质(2)
        String T_ISS_MSG_UNIT_BUSI_MED = StringUtils.rightPad("", 2);
        //业务种类(1)
        String T_ISS_MSG_UNIT_BUSI_TYPE = " ";
        //卡积分(17)
        String T_ISS_MSG_UNIT_CARDSCORE = StringUtils.leftPad("", 17);
        //代码(4)
        String T_ISS_MSG_UNIT_CODE = StringUtils.rightPad("", 4);
        //操作标识(2)
        String T_ISS_MSG_UNIT_CRT_FLAG = StringUtils.rightPad("", 2);
        //货币符号(3)
        String T_ISS_MSG_UNIT_CURR_FLAG = StringUtils.rightPad("", 3);
        //日期1(8)
        String T_ISS_MSG_UNIT_DATE1 = StringUtils.rightPad("", 8);
        //日期2(8)
        String T_ISS_MSG_UNIT_DATE2 = StringUtils.rightPad("", 8);
        //日期3(8)
        String T_ISS_MSG_UNIT_DATE3 = StringUtils.rightPad("", 8);
        //不成功交易标识(2)
        String T_ISS_MSG_UNIT_FAIL_FLAG = StringUtils.rightPad("", 2);
        //不成功交易信息体(40)
        String T_ISS_MSG_UNIT_FAIL_MSG = StringUtils.rightPad("", 40);
        //不成功交易信息ID码(4)
        String T_ISS_MSG_UNIT_FAIL_ID = StringUtils.rightPad("5", 4);
        //标志1(1)
        String T_ISS_MSG_UNIT_FLAG1 = StringUtils.rightPad("", 1);
        //标志2(3)
        String T_ISS_MSG_UNIT_FLAG2 = StringUtils.rightPad("", 3);
        //短信息内容(150)
        String T_ISS_MSG_UNIT_MSGCONTENT = StringUtils.rightPad("", 150);
        //号码(20)
        String T_ISS_MSG_UNIT_NUM = StringUtils.rightPad(message, 20);
        //平台原交易日期(8)
        String T_ISS_MSG_UNIT_OLD_DATE = StringUtils.rightPad("", 8);
        //平台原交易流水(12)
        String T_ISS_MSG_UNIT_OLD_SEQ = StringUtils.leftPad("", 12);
        //采集优先级（值越少越优先）(12)
        String T_ISS_MSG_UNIT_OLD_SEQ_SUB = StringUtils.leftPad("", 12);
        //续存方式(1)
        String T_ISS_MSG_UNIT_SAVE_FLAG = " ";
        //时间(6)
        String T_ISS_MSG_UNIT_TIME = StringUtils.rightPad("", 6);
        //交易摘要(22)
        String T_ISS_MSG_UNIT_TRAN_MARK = StringUtils.rightPad("", 22);
        //扣费名称(40)
        String T_ISS_MSG_UNIT_FEE_NAME = StringUtils.rightPad("", 40);
        //交易来源名称（渠道名称）(40)
        String T_ISS_MSG_UNIT_CHN_NAME = StringUtils.rightPad("", 40);
        //手机对应的客户号(128)
        String T_ISS_MSG_UNIT_RMRK1 = StringUtils.rightPad("", 128);
        //预留2(128)
        String T_ISS_MSG_UNIT_RMRK2 = StringUtils.rightPad("", 128);
        //预留3(128)
        String T_ISS_MSG_UNIT_RMRK3 = StringUtils.rightPad("", 128);
        //预留4(E购通已乱填，交易码：P59503)(12)
        String T_ISS_MSG_UNIT_RMRK4 = StringUtils.leftPad("", 12);
        //预留5(12)
        String T_ISS_MSG_UNIT_RMRK5 = StringUtils.leftPad("", 12);
        //预留6(12)
        String T_ISS_MSG_UNIT_RMRK6 = StringUtils.leftPad("", 12);

        String body = T_ISS_MSG_UNIT_ORG_TRAN +
                T_ISS_MSG_UNIT_CR_FLAG +
                T_ISS_MSG_UNIT_ACTNO1 +
                T_ISS_MSG_UNIT_MOBILE +
                T_ISS_MSG_UNIT_CORP_CODE +
                T_ISS_MSG_UNIT_REVS_FLAG +
                T_ISS_MSG_UNIT_ACT_NAME1 +
                T_ISS_MSG_UNIT_ACTNO2 +
                T_ISS_MSG_UNIT_AMT1 +
                T_ISS_MSG_UNIT_AMT2 +
                T_ISS_MSG_UNIT_AMT3 +
                T_ISS_MSG_UNIT_ATTIME +
                T_ISS_MSG_UNIT_BRCH_NO1 +
                T_ISS_MSG_UNIT_BUSI_MED +
                T_ISS_MSG_UNIT_BUSI_TYPE +
                T_ISS_MSG_UNIT_CARDSCORE +
                T_ISS_MSG_UNIT_CODE +
                T_ISS_MSG_UNIT_CRT_FLAG +
                T_ISS_MSG_UNIT_CURR_FLAG +
                T_ISS_MSG_UNIT_DATE1 +
                T_ISS_MSG_UNIT_DATE2 +
                T_ISS_MSG_UNIT_DATE3 +
                T_ISS_MSG_UNIT_FAIL_FLAG +
                T_ISS_MSG_UNIT_FAIL_MSG +
                T_ISS_MSG_UNIT_FAIL_ID +
                T_ISS_MSG_UNIT_FLAG1 +
                T_ISS_MSG_UNIT_FLAG2 +
                T_ISS_MSG_UNIT_MSGCONTENT +
                T_ISS_MSG_UNIT_NUM +
                T_ISS_MSG_UNIT_OLD_DATE +
                T_ISS_MSG_UNIT_OLD_SEQ +
                T_ISS_MSG_UNIT_OLD_SEQ_SUB +
                T_ISS_MSG_UNIT_SAVE_FLAG +
                T_ISS_MSG_UNIT_TIME +
                T_ISS_MSG_UNIT_TRAN_MARK +
                T_ISS_MSG_UNIT_FEE_NAME +
                T_ISS_MSG_UNIT_CHN_NAME +
                T_ISS_MSG_UNIT_RMRK1 +
                T_ISS_MSG_UNIT_RMRK2 +
                T_ISS_MSG_UNIT_RMRK3 +
                T_ISS_MSG_UNIT_RMRK4 +
                T_ISS_MSG_UNIT_RMRK5 +
                T_ISS_MSG_UNIT_RMRK6;
        return body;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public Charset getCharset() {
        return charset;
    }

    public void setCharset(Charset charset) {
        this.charset = charset;
    }
}
