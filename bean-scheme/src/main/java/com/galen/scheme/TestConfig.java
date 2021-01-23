package com.galen.scheme;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@ComponentScan(basePackages = {"com.gitee.sunchenbin.mybatis.actable.manager.*"})
public class TestConfig {
    //连接数据库配置文件的地址，具体查阅配置文件的结构
    @Value("${spring.datasource.driver-class-name}")
    private String driver;
    //连接数据库配置文件的地址，具体查阅配置文件的结构
    @Value("${spring.datasource.url}")
    private String url;
    //连接数据库配置文件的地址，具体查阅配置文件的结构
    @Value("${spring.datasource.username}")
    private String username;
    //连接数据库配置文件的地址，具体查阅配置文件的结构
    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public PropertiesFactoryBean configProperties() throws Exception {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        propertiesFactoryBean.setLocations(resolver.getResources("classpath*:application.properties"));//classpath*:application.properties是mybatis的生成表配置文件
        return propertiesFactoryBean;
    }

    @Bean
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(30);
        dataSource.setInitialSize(10);
        dataSource.setValidationQuery("SELECT 1");
        dataSource.setTestOnBorrow(true);
        return dataSource;
    }

    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager() {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource());
        return dataSourceTransactionManager;
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:com/gitee/sunchenbin/mybatis/actable/mapping/*/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("com.galen.scheme.entity.*");
        //上述classpath*:com/gitee/sunchenbin/mybatis/actable/mapping/*/*.xml固定的包路径
        //com.xxx.xxx.entity.*替换成你的实体类地址
        return sqlSessionFactoryBean;
    }
}
