package com.dbsun.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.dbsun.mybatis.MyBatisInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * springboot集成mybatis的基本入口 1）创建数据源(如果采用的是默认的tomcat-jdbc数据源，则不需要)
 * 2）创建SqlSessionFactory 3）配置事务管理器，除非需要使用事务，否则不用配置
 */
@Configuration
// 该注解类似于spring配置文件
@EnableTransactionManagement
// Spring事务管理器
public class AllConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private Environment env;
	private static final Logger log = LoggerFactory.getLogger(AllConfig.class);
	/**
	 * web登录验证相关配置
	 */
	public final static String SESSION_KEY = "user";
    
    /**
     * 常用数据字典
     */
    public final static String SESSION_DICT = "dict";

	/**
	 * 用户的菜单数据
	 */
	public final static String SESSION_ALLMENULIST = "menulst";

	@Bean
	public SecurityInterceptor getSecurityInterceptor() {
		return new SecurityInterceptor();
	}

	private class SecurityInterceptor extends HandlerInterceptorAdapter {

		@Override
		public boolean preHandle(HttpServletRequest request,
				HttpServletResponse response, Object handler) throws Exception {
			HttpSession session = request.getSession();
			if (session.getAttribute(SESSION_KEY) != null) {
				return true;
			}
			// 跳转登录
			String url = request.getContextPath() + "/login";
			response.sendRedirect(url);
			return false;
		}
		/**
		 * controller 执行之后，且页面渲染之前调用
		 */
		@Override
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
							   ModelAndView modelAndView) throws Exception {

			System.out.println("------postHandle-----");
		}

		/**
		 * 页面渲染之后调用，一般用于资源清理操作
		 */
//		@Override
//		public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//				throws Exception {
//			System.out.println("------afterCompletion-----");
//		}
	}

	/**
	 * 创建数据源(数据源的名称：方法名可以取为XXXDataSource(),XXX为数据库名称,该名称也就是数据源的名称)
	 */
	@Bean
	public DataSource writeDataSource() throws Exception {
		Properties props = new Properties();
		props.put("driverClassName", env.getProperty("jdbc.driverClassName"));
		props.put("url", env.getProperty("jdbc.url"));
		props.put("username", env.getProperty("jdbc.username"));
		props.put("password", env.getProperty("jdbc.password"));
		return DruidDataSourceFactory.createDataSource(props);
	}

	@Bean
	public DataSource readDataSource() throws Exception {
		Properties props = new Properties();
		props.put("driverClassName", env.getProperty("jdbc2.driverClassName"));
		props.put("url", env.getProperty("jdbc2.url"));
		props.put("username", env.getProperty("jdbc2.username"));
		props.put("password", env.getProperty("jdbc2.password"));
		return DruidDataSourceFactory.createDataSource(props);
	}

	/******************** 初始化自定义拦截器 ********************/

	/**
	 * 添加拦截器
	 */
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration addInterceptor = registry
				.addInterceptor(getSecurityInterceptor());
		// 排除配置
		addInterceptor.excludePathPatterns("/error");

		addInterceptor.excludePathPatterns("/pro_cus_contract/**");
		addInterceptor.excludePathPatterns("/login**");

		addInterceptor.excludePathPatterns("/doc");
		addInterceptor.excludePathPatterns("/v2/**");
		addInterceptor.excludePathPatterns("/swagger-resources/**");

		// addInterceptor.excludePathPatterns("/assets**");
		// 拦截配置
		addInterceptor.addPathPatterns("/**");
		// 权限隔离
		log.debug("此处添加拦截器, Add interceptors here.");
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
			String imagesPath = AllConfig.class.getClassLoader().getResource("").getPath();
			if(imagesPath.indexOf(".jar")>0){
				imagesPath = imagesPath.substring(0, imagesPath.indexOf(".jar"));
			}else if(imagesPath.indexOf("classes")>0){
				imagesPath = "file:"+imagesPath.substring(0, imagesPath.indexOf("classes"));
			}
			imagesPath = imagesPath.substring(0, imagesPath.lastIndexOf("/"))+"/images/";
		registry.addResourceHandler("/images/**").addResourceLocations("file:/C:/upload/");
		// TODO Auto-generated method stub
		super.addResourceHandlers(registry);
	}

	/**
	 * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
	 * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
	 */
	@Bean
	@Primary
	public DynamicDataSource dataSource(
			@Qualifier("writeDataSource") DataSource myTestDbDataSource,
			@Qualifier("readDataSource") DataSource myTestDb2DataSource) {
		Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
		targetDataSources.put(DatabaseType.MASTER, myTestDbDataSource);
		targetDataSources.put(DatabaseType.SLAVE, myTestDb2DataSource);

		DynamicDataSource dataSource = new DynamicDataSource();
		dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
		dataSource.setDefaultTargetDataSource(myTestDbDataSource);// 默认的datasource设置为myTestDbDataSource

		return dataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(
			@Qualifier("writeDataSource") DataSource myTestDbDataSource,
			@Qualifier("readDataSource") DataSource myTestDb2DataSource)
			throws Exception {
		SqlSessionFactoryBean fb = new SqlSessionFactoryBean();
		fb.setDataSource(this.dataSource(myTestDbDataSource,
				myTestDb2DataSource));
		fb.setTypeAliasesPackage(env
				.getProperty("mybatis.type-aliases-package"));
		// fb.setTypeAliasesPackage("com.dbsun.util");
		// //分页插件未采用这个分页方法，在自定义拦截器里面自写了分页方法
		// PageHelper pageHelper = new PageHelper();
		// Properties properties = new Properties();
		// properties.setProperty("reasonable", "true");
		// properties.setProperty("supportMethodsArguments", "true");
		// // properties.setProperty("returnPageInfo", "check");
		// // properties.setProperty("params", "count=countSql");
		// // properties.setProperty("pageSizeZero", "true");
		// // properties.setProperty("rowBoundsWithCount", "true");
		// // properties.setProperty("reasonable", "false");
		// // properties.setProperty("dialect", "mysql");
		// // Properties p = new Properties();
		// // p.setProperty("offsetAsPageNum", "true");
		// // p.setProperty("rowBoundsWithCount", "true");
		// // p.setProperty("reasonable", "true");
		// // p.setProperty("returnPageInfo", "check");
		// // p.setProperty("params", "count=countSql");
		// //
		// pageHelper.setProperties(properties);

		// 添加插件
		fb.setPlugins(new Interceptor[] { new MyBatisInterceptor() });

		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
		// 配置自动将下划线转换至驼峰表示法
		configuration.setMapUnderscoreToCamelCase(true);
//		configuration.setCallSettersOnNulls(true);
		try {
			fb.setConfiguration(configuration);
			fb.setMapperLocations(new PathMatchingResourcePatternResolver()
					.getResources(env.getProperty("mybatis.mapper-locations")));
			return fb.getObject();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 配置事务管理器
	 */
	@Bean
	public DataSourceTransactionManager transactionManager(
			DynamicDataSource dataSource) throws Exception {
		return new DataSourceTransactionManager(dataSource);
	}

}