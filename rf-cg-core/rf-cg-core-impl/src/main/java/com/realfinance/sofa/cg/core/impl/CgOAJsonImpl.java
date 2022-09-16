package com.realfinance.sofa.cg.core.impl;

import com.alipay.sofa.rpc.common.utils.BeanUtils;
import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.jcraft.jsch.ChannelSftp;
import com.realfinance.sofa.cg.core.domain.oa.OaAttachment;
import com.realfinance.sofa.cg.core.facade.CgOAJsonFacade;
import com.realfinance.sofa.cg.core.model.CgOAJsonDto;
import com.realfinance.sofa.cg.core.repository.CgOAJsonRepository;
import com.realfinance.sofa.sdebank.sftp.SdebankSFTP;
import com.realfinance.sofa.sdebank.sftp.SftpCategory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.realfinance.sofa.cg.core.util.ExceptionUtils.businessException;


@Service
@SofaService(interfaceType = CgOAJsonFacade.class, uniqueId = "${service.rf-cg-core.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional(readOnly = true)
public class CgOAJsonImpl implements CgOAJsonFacade {

    private static final Logger log = LoggerFactory.getLogger(CgOAJsonImpl.class);

    private final CgOAJsonRepository cgOAJsonRepository;

    public CgOAJsonImpl(CgOAJsonRepository cgOAJsonRepository) {
        this.cgOAJsonRepository = cgOAJsonRepository;
    }
    //ftp操作转成sftp操作
    /*protected Ftp getFtp() {
        Ftp ftp = new Ftp("172.16.249.18", 21, "eosftp", "eosftp123");
        return ftp;
    }

    private String getFirstFileName(String ftpDir, Ftp ftp, Filter<FTPFile> filter) {
        List<FTPFile> ftpFiles = ftp.lsFiles(ftpDir, filter);
        if (ftpFiles.isEmpty()) {
            throw new RuntimeException("在FTP上找不到文件");
        }
        return ftpFiles.get(0).getName();
    }

    private byte[] downloadFile(String ftpDir, Ftp ftp, String fileName) {
        try (FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream()) {
            ftp.download(ftpDir, fileName, outputStream);
            return outputStream.toByteArray();
        }
    }

    private byte[] getFile(String fileDir) {
        String ftpDir = "/upload/EOSFTP/PMS/" + fileDir;
        String fileName;
        byte[] bytes;
        try (Ftp ftp = getFtp()) {

            fileName = getFirstFileName(ftpDir, ftp, new Filter<FTPFile>() {
                @Override

                public boolean accept(FTPFile ftpFile) {

                    return "zip".equals(StringUtils.substringAfter(ftpFile.getName(), "."));
                }
            });
            bytes = downloadFile(ftpDir, ftp, fileName);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("下载文件失败", e);
            }
            throw new RuntimeException("下载失败" + e.getMessage());
        }
        return bytes;
    }
*/

    @Override
    @Transactional
    public byte[] getOAJson(CgOAJsonDto cgOAJsonDto) {
        OaAttachment oaAttachment = new OaAttachment();
        BeanUtils.copyProperties(cgOAJsonDto, oaAttachment);
        try {
            cgOAJsonRepository.save(oaAttachment);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("保存失败", e);
            }
            throw businessException("保存失败");
        }
        SdebankSFTP sdebankSFTP = new SdebankSFTP();
        ChannelSftp connect = sdebankSFTP.connect(SftpCategory.OA);
        String fileDir = "/upload/EOSFTP/PMS/" + StringUtils.substringBeforeLast(cgOAJsonDto.getZIPFILE(), "/");
        String fileName = StringUtils.substringAfterLast(cgOAJsonDto.getZIPFILE(), "/");
        if (fileName == "" || fileName == null) {
            throw new RuntimeException("OA数据传值有误!(字段ZIPFILE无法获取到对应附件名)");
        }
        sdebankSFTP.getFileName(fileDir, fileName, connect);
        //在sftp上下载下来的附件压缩包 字节流
        byte[] bytes = sdebankSFTP.download(fileDir, fileName, connect);
        sdebankSFTP.close();
        return bytes;
    }

}
