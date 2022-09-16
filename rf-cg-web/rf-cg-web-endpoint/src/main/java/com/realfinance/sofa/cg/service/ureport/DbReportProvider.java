package com.realfinance.sofa.cg.service.ureport;

import com.bstek.ureport.exception.ReportException;
import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.ReportProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

/**
 * Ureport报表存储到数据库实现
 * 需要建表 table(name,content,update_date)
 */
public class DbReportProvider implements ReportProvider {

    public static final String DEFAULT_TABLE_NAME = "URP_REPORT";
    private static final String LOAD_REPORT_SQL = "SELECT content FROM %TABLE_NAME% WHERE name = ?";
    private static final String DELETE_REPORT_SQL = "DELETE FROM %TABLE_NAME% WHERE name = ?";
    private static final String GET_REPORT_FILES_SQL = "SELECT name,update_date as updateDate FROM %TABLE_NAME% ORDER BY updateDate DESC";
    private static final String SAVE_REPORT_SQL = "INSERT INTO %TABLE_NAME% VALUES (?,?,?)";

    /**
     * 表名
     */
    private String tableName = DEFAULT_TABLE_NAME;
    private String prefix = "db:";
    private boolean disabled;

    private JdbcTemplate jdbcTemplate;

    @Override
    public InputStream loadReport(String file) {
        if(file.startsWith(prefix)){
            file = file.substring(prefix.length());
        }
        String content = jdbcTemplate.queryForObject(getQuery(LOAD_REPORT_SQL), String.class, file);
        if (content == null) {
            throw new ReportException("找不到报表：" + file);
        }
        return new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public void deleteReport(String file) {
        if(file.startsWith(prefix)){
            file = file.substring(prefix.length());
        }
        int update = jdbcTemplate.update(getQuery(DELETE_REPORT_SQL), file);
        if (update < 1) {
            throw new ReportException("找不到报表：" + file);
        }
    }

    @Override
    public List<ReportFile> getReportFiles() {
        List<ReportFile> reportFiles = jdbcTemplate.query(getQuery(GET_REPORT_FILES_SQL),
                (rs, rowNum) -> new ReportFile(rs.getString(1),rs.getDate(2)));
        return reportFiles;
    }

    @Override
    public void saveReport(String file, String content) {
        if(file.startsWith(prefix)){
            file = file.substring(prefix.length());
        }
        int update = jdbcTemplate.update(getQuery(SAVE_REPORT_SQL), file, content, new Date());
        if (update < 1) {
            throw new ReportException("保存报表失败：" + file);
        }
    }

    @Override
    public String getName() {
        return "数据库存储";
    }

    @Override
    public boolean disabled() {
        return disabled;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    private String getQuery(String base) {
        return StringUtils.replace(base, "%TABLE_NAME%", this.tableName);
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
