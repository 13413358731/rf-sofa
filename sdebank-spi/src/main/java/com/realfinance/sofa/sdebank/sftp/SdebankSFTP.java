package com.realfinance.sofa.sdebank.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FastByteArrayOutputStream;

import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;


public class SdebankSFTP {

    private static final Logger log = LoggerFactory.getLogger(SdebankSFTP.class);

    private Session sshSession = null;
    private ChannelSftp sftp = null;

    /**
     * sftpCategory
     * 连接sftp服务器
     */
    public ChannelSftp connect(SftpCategory sftpCategory) {

        try {
            JSch jsch = new JSch();
            jsch.getSession(sftpCategory.getUsername(), sftpCategory.getHost(), sftpCategory.getPort());
            sshSession = jsch.getSession(sftpCategory.getUsername(), sftpCategory.getHost(), sftpCategory.getPort());
            sshSession.setPassword(sftpCategory.getPassword());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            sftp = (ChannelSftp) channel;
        } catch (Exception e) {
            log.error("connect:" + sftpCategory.getHost(), e);
            close();
            return null;
        }
        return sftp;
    }

    /**
     * 匹配文件全名是否存在
     * @param fileDir 路径
     * @param fileName 文件名
     * @param sftp 连接的ftp
     * @return 文件名
     */
    public String getFileName(String fileDir, String fileName, ChannelSftp sftp) {
        String name = null;
        try {
            Vector vector = sftp.ls(fileDir);
            Iterator iterator = vector.iterator();
            while (iterator.hasNext()) {
                ChannelSftp.LsEntry file = (ChannelSftp.LsEntry) iterator.next();
                if (file.getFilename().equals(fileName)) {
                    name = file.getFilename();
                    break;
                }
            }
        } catch (SftpException e) {
            e.printStackTrace();
            close();
        }
        if (name.isEmpty()) {
            throw new RuntimeException("FTP上找不到文件");
        }
        return name;
    }

    /**
     * 匹配文件前缀是否存在
     * @param fileDir 路径
     * @param suffix 前缀
     * @param sftp 连接的ftp
     * @return 文件名
     */
    public String getFilePrefix(String fileDir, String suffix, ChannelSftp sftp){
        String name = null;
        try {
            Vector vector = sftp.ls(fileDir);
            Iterator iterator = vector.iterator();
            while (iterator.hasNext()) {
                ChannelSftp.LsEntry file = (ChannelSftp.LsEntry) iterator.next();
                if (!file.getFilename().isEmpty()){
                    if (suffix.equals(StringUtils.substringBefore(file.getFilename(), "["))){
                        name=file.getFilename();
                        break;
                    }
                }
            }
        } catch (SftpException e) {
            e.printStackTrace();
            close();
        }
        if (name.isEmpty()) {
            throw new RuntimeException("FTP上找不到文件");
        }
        return name;
    }


    /**
     * 关闭连接
     */
    public void close() {
        if (sftp != null) {
            sftp.disconnect();
        }
        if (sshSession != null) {
            sshSession.disconnect();
        }
    }

    /**
     * 下载文件
     * @param fileDir  文件路径
     * @param fileName 文件名
     * @param sftp 连接的sftp
     */
    public byte[] download(String fileDir, String fileName, ChannelSftp sftp) {
        try (FastByteArrayOutputStream outputStream = new FastByteArrayOutputStream()) {
            sftp.cd(fileDir);
            sftp.get(fileName, outputStream);
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            close();
            if (log.isErrorEnabled()) {
                log.error("下载文件失败", e);
            }
            throw new RuntimeException("下载失败" + e.getMessage());
        }
    }


}