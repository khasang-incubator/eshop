package io.khasang.eshop.config;
import io.khasang.eshop.dao.BookDao;
import io.khasang.eshop.dao.CatDao;
import io.khasang.eshop.dao.CustomerDao;
import io.khasang.eshop.dao.impl.BookDaoImpl;
import io.khasang.eshop.dao.impl.CatDaoImpl;
import io.khasang.eshop.dao.impl.CustomerDaoImpl;
import io.khasang.eshop.entity.Book;
import io.khasang.eshop.entity.Cat;
import io.khasang.eshop.entity.Customer;
import io.khasang.eshop.model.CrudOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

@Configuration
@PropertySource(value = "classpath:util.properties")
@PropertySource(value = "classpath:auth.properties")
public class AppConfig {

    @Autowired
    private Environment environment;

    @Bean
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(environment.getRequiredProperty("jdbc.postgresql.driver"));
        dataSource.setUrl(environment.getRequiredProperty("jdbc.postgresql.url"));
        dataSource.setUsername(environment.getRequiredProperty("jdbc.postgresql.user"));
        dataSource.setPassword(environment.getRequiredProperty("jdbc.postgresql.password"));
        return dataSource;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        JdbcDaoImpl jdbcDao = new JdbcDaoImpl();
        jdbcDao.setDataSource(dataSource());
        jdbcDao.setUsersByUsernameQuery(environment.getRequiredProperty("usersByQuery"));
        jdbcDao.setAuthoritiesByUsernameQuery(environment.getRequiredProperty("rolesByQuery"));
        return jdbcDao;
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public CatDao catDao(){
        return new CatDaoImpl(Cat.class);
    }

    @Bean
    public CustomerDao customerDao(){
        return new CustomerDaoImpl(Customer.class);
    }

    @Bean
    public BookDao bookDao(){
        return new BookDaoImpl(Book.class);
    }

    @Bean
    public CrudOperations createTable() {
        return new CrudOperations(jdbcTemplate());
    }



}
