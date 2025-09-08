package com.fm.knight.knight.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.fm.knight.knight.plugins.PageInterceptor;
import com.fm.knight.knight.plugins.ProgramInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Objects;

/**
 * mybatis配置
 *
 * @author liuzemin 2025-08-08
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
public class MyBatisConfiguration {

    private static final Logger log = LoggerFactory.getLogger(MyBatisConfiguration.class);

    /**
     * druid数据源
     */
    @Bean
    public DataSource dataSource(Environment env) {
        DruidDataSource ds = new DruidDataSource();

        ds.setUrl(env.getProperty("spring.datasource.url"));
        ds.setUsername(env.getProperty("spring.datasource.username"));
        ds.setPassword(env.getProperty("spring.datasource.password"));
        ds.setDriverClassName(env.getProperty("spring.datasource.driver"));

        // 初始化时建立连接个数
        /*ds.setInitialSize(Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.datasource.initsize"))));

        // 连接池最大连接数
        ds.setMaxActive(Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.datasource.maxActive"))));

        // 最小连接池数
        ds.setMinIdle(Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.datasource.minIdle"))));

        // 获取连接时最大等待时间，单位：毫秒。配置了maxwait之后，缺省启用公平锁，并发效率会有所下降
        ds.setMaxWait(Long.parseLong(Objects.requireNonNull(env.getProperty("spring.datasource.maxWait"))));

        // 是否缓存prepareStatement，也就是PScache，对支持游标的数据库性能提升巨大，如oracle，mysql5.5以下不支持游标
        ds.setPoolPreparedStatements(Boolean.parseBoolean(env.getProperty("spring.datasource.poolPreparedStatements")));

        // 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
        // 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
        ds.setMaxOpenPreparedStatements(Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.datasource.maxOpenPreparedStatements"))));

        // 用来检测连接是否有效的sql
        ds.setValidationQuery(env.getProperty("spring.datasource.validationQuery"));

        // 申请连接时检测连接是否有效，会影响性能
        ds.setTestOnBorrow(Boolean.parseBoolean(env.getProperty("spring.datasource.testOnBorrow")));

        // 归还连接时检测连接是否有效，会影响性能
        ds.setTestOnReturn(Boolean.parseBoolean(env.getProperty("spring.datasource.testOnReturn")));

        // 测试空闲连接是否有效，不影响性能
        ds.setTestWhileIdle(Boolean.parseBoolean(env.getProperty("spring.datasource.testWhileIdle")));

        // 1,Destroy线程检测连接的间隔时间 2,testWhileIdle的判断依据
        ds.setTimeBetweenEvictionRunsMillis(Long.parseLong(Objects.requireNonNull(env.getProperty("spring.datasource.timeBetweenEvictionRunsMillis"))));*/

        try {
            // 通过别名的方式配置扩展插件，常用插件有
            // 监控统计用的：filter:stat
            // 日志用的：filter:log4j
            // 预防sql注入用的：filter:wall
            ds.setFilters(env.getProperty("spring.datasource.filters"));
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            log.error("init datasource error |", e);
        }

        // 类型是List<com.alibaba.druid.filter.Filter>，如果同时配置了filters和proxyFilters，是组合关系，并非替换关系
        // ds.setProxyFilters();
        return ds;
    }

    /**
     * 分页插件
     *
     * @param env
     * @return
     */
    @Bean
    public PageInterceptor pageInterceptor(Environment env) {
        return new PageInterceptor();
    }

    /**
     * 数据范围插件
     *
     * @param env
     * @return
     */
    @Bean
    public ProgramInterceptor programInterceptor(Environment env) {
        return new ProgramInterceptor();
    }
}
