package com.realfinance.sofa.cg.config;

import com.bstek.ureport.console.UReportServlet;
import com.bstek.ureport.definition.datasource.BuildinDatasource;
import com.realfinance.sofa.cg.service.ureport.DbReportProvider;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@ImportResource("classpath:ureport-console-context.xml")
public class UreportConfig {

    @Bean
    public ServletRegistrationBean getServletRegistrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new UReportServlet());
        bean.addUrlMappings("/ureport/*");
        return bean;
    }

    @Bean
    public DbReportProvider dbReportProvider(JdbcTemplate jdbcTemplate) {
        DbReportProvider dbReportProvider = new DbReportProvider();
        dbReportProvider.setJdbcTemplate(jdbcTemplate);
        return dbReportProvider;
    }

    @Bean
    public BuildinDatasource ureportDatasource1(DataSource dataSource) {
        return new BuildinDatasource() {

            @Override
            public String name() {
                return "采购管理模块";
            }

            @Override
            public Connection getConnection() {
                return UreportConfig.this.getConnection("rf_cg_core");
            }
        };
    }

    @Bean
    public BuildinDatasource ureportDatasource2(DataSource dataSource) {
        return new BuildinDatasource() {

            @Override
            public String name() {
                return "供应商模块";
            }

            @Override
            public Connection getConnection() {
                return UreportConfig.this.getConnection("rf_cg_sup");
            }
        };
    }

    @Bean
    public BuildinDatasource ureportDatasource3(DataSource dataSource) {
        return new BuildinDatasource() {

            @Override
            public String name() {
                return "系统模块";
            }

            @Override
            public Connection getConnection() {
                return UreportConfig.this.getConnection("rf_system");
            }
        };
    }

    private Connection getConnection(String tableName) {

        try {
            Class.forName("com.alipay.oceanbase.obproxy.mysql.jdbc.Driver");
        } catch (Exception e) {

        }

        try {
            Properties p = new Properties();
            p.setProperty("user","root@purchase_60#ob_dvl");
            p.setProperty("password","Abcd1234");

            p.setProperty("zeroDateTimeBehavior","convertToNull");
            p.setProperty("characterEncoding","utf8");
            p.setProperty("useSSL","false");
            p.setProperty("serverTimezone","Asia/Shanghai");
            p.setProperty("rewriteBatchedStatements","true");

            return DriverManager.getConnection("jdbc:oceanbase://obproxy.ops.aliyun-test.sdebank.com:3306/" + tableName,p);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
