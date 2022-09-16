package com.realfinance.sofa.system.sdebank.associatedTran;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.jcraft.jsch.ChannelSftp;
import com.realfinance.sofa.sdebank.sftp.SdebankSFTP;
import com.realfinance.sofa.sdebank.sftp.SftpCategory;
import com.realfinance.sofa.system.facade.AssociatedTranFacade;
import com.realfinance.sofa.system.model.AssociatedTranDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@SofaService(interfaceType = AssociatedTranFacade.class, uniqueId = "${service.rf-system.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class AssociatedTranImpl implements AssociatedTranFacade {

    private static final Logger log = LoggerFactory.getLogger(AssociatedTranImpl.class);

    private final AssociatedTranRepository associatedTranRepository;
    //ftp文件路径
    private String ftpDir = "/odsshare/receive/RXP/" + DATE;
    //同步文件名的前缀名称
    private String PREFIX = "ODS_RELA_NATPSN_INFO_";

//    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

    //    private static String DATE = simpleDateFormat.format(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 24));
    //测试数据
    private static String DATE = "20210202";

    public AssociatedTranImpl(AssociatedTranRepository associatedTranRepository) {
        this.associatedTranRepository = associatedTranRepository;
    }

    /*protected Ftp getFtp() {
        Ftp ftp = new Ftp("172.16.249.133", 21, "use", "use_1234");
        return ftp;
    }

    private String getFirstFileName(Ftp ftp, Filter<FTPFile> filter) {
        List<FTPFile> ftpFiles = ftp.lsFiles(ftpDir, filter);
        if (ftpFiles.isEmpty()) {
            throw new RuntimeException("在FTP上找不到文件");
        }
        return ftpFiles.get(0).getName();
    }

    private byte[] downloadFile(Ftp ftp, String fileName) {
        try (FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream()) {
            ftp.download(ftpDir, fileName, outputStream);
            return outputStream.toByteArray();
        }
    }

    private byte[] getFile() {
        String fileName;
        byte[] bytes;
        try (Ftp ftp = getFtp()) {
            //找不到OK文件报错 且不进行同步
            getFirstFileName(ftp, new Filter<FTPFile>() {
                @Override
                public boolean accept(FTPFile ftpFile) {
                    return (PREFIX + DATE + ".OK").equals(ftpFile.getName());
                }
            });
            fileName = getFirstFileName(ftp, new Filter<FTPFile>() {
                @Override

                public boolean accept(FTPFile ftpFile) {

                    return (PREFIX + DATE + ".DAT").equals(ftpFile.getName());
                }
            });
            bytes = downloadFile(ftp, fileName);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("下载文件失败", e);
            }
            throw new RuntimeException("下载失败" + e.getMessage());
        }
        return bytes;
    }*/

    @Override
    @Transactional(rollbackFor = Exception.class, readOnly = false)
    public void syncAssociatedTran() {
        SdebankSFTP sdebankSFTP = new SdebankSFTP();
        ChannelSftp connect = sdebankSFTP.connect(SftpCategory.ASSOCIATEDTRAN);
        //匹配后缀为.OK文件,匹配失败则不同步.DAT文件(确保.DAT文件完整性)
        sdebankSFTP.getFileName(ftpDir, PREFIX + DATE + ".OK", connect);
        //匹配后缀为.DAT文件
        String fileName = sdebankSFTP.getFileName(ftpDir, PREFIX + DATE + ".DAT", connect);
        //下载文件
        byte[] bytes = sdebankSFTP.download(ftpDir, fileName, connect);
        sdebankSFTP.close();
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        List<AssociatedTran> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(b, Charset.forName("GBK")))) {
            String tempString;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                String[] split = tempString.split("\\|");
                List<String> l = new ArrayList<>();
                for (String s : split) {
                    if (s.equals("")) {
                        l.add(null);
                    } else {
                        l.add(s);
                    }
                }
                if (split.length == 28) {
                    l.add(null);
                }
                if (l.size() == 29) {
                    AssociatedTran associatedTran = new AssociatedTran(l);
                    list.add(associatedTran);
                } else {
                    log.error("第" + line + "行数据有误! 数据为:" + tempString);
                }
                line++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        associatedTranRepository.deleteAll();
        associatedTranRepository.saveAll(list);
    }


    @Override
    public List<AssociatedTranDto> selectIds(List<AssociatedTranDto> dtos) {
        if (dtos.size()==0){
            throw new RuntimeException("传入数据不能为空!");
        }
        for (AssociatedTranDto dto : dtos) {
            dto.setStatus("0");
            List<AssociatedTran> list = associatedTranRepository.findByIdtpAndIdNo(dto.getType(), dto.getIdCardNumber());
            Date date = new Date();
            for (AssociatedTran associatedTran : list) {
                //错误数据跳过并打印日志
                if (associatedTran.getEnterTime() != null && associatedTran.getIsInner() != null) {
                    //退出时间大于当前时间or为空
                    if ((associatedTran.getQuitTime() == null) || (associatedTran.getQuitTime().getTime() > date.getTime())) {
                        //是否关联方 为是 and 准入时间小于当前时间
                        if (("是".equals(associatedTran.getIsInner()) && associatedTran.getEnterTime().getTime() < date.getTime())) {
                            dto.setStatus("1");
                            break;
                        }
                    }
                } else {
                    log.error("id为:" + associatedTran.getId() + "的准入时间或者是否关联方为空！");
                }
            }
        }

        return dtos;
    }
}
