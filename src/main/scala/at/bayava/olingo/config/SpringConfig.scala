package at.bayava.olingo.config

import java.util.Properties
import javax.sql.DataSource

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation._
import org.springframework.instrument.classloading.LoadTimeWeaver
import org.springframework.jdbc.datasource.embedded.{EmbeddedDatabaseBuilder, EmbeddedDatabaseType}
import org.springframework.orm.jpa.vendor.EclipseLinkJpaVendorAdapter
import org.springframework.orm.jpa.{JpaTransactionManager, LocalContainerEntityManagerFactoryBean}
import org.springframework.transaction.annotation.EnableTransactionManagement

/**
	* Created by pbayer.
	*/
@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableLoadTimeWeaving
@ComponentScan(Array("at.bayava"))
class SpringConfig {

	@Autowired
	var ltw: LoadTimeWeaver = _

	//		@Bean
	//		def createEmf: LocalEntityManagerFactoryBean = {
	//			val lemfb = new LocalEntityManagerFactoryBean()
	//			lemfb.setPersistenceUnitName("olingo")
	//			lemfb.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter())
	//			lemfb
	//		}
	//
	@Bean
	def createEmf: LocalContainerEntityManagerFactoryBean = {
		val lcemfb = new LocalContainerEntityManagerFactoryBean()
		lcemfb.setDataSource(createDataSource)
		lcemfb.setPersistenceUnitName("olingo")
		lcemfb.setJpaVendorAdapter(new EclipseLinkJpaVendorAdapter())
		val properties = new Properties()
		properties.setProperty("eclipselink.weaving", "static")
		//		properties.setProperty("eclipselink.ddl-generation.output-mode", "both")
		//		properties.setProperty("eclipselink.create-ddl-jdbc-file-name" ,"create.ddl")
		lcemfb.setJpaProperties(properties)
		lcemfb.setLoadTimeWeaver(ltw)
		lcemfb
	}

	@Bean
	def jpaTransMan: JpaTransactionManager = {
		val jtManager = new JpaTransactionManager(createEmf.getObject)
		jtManager
	}

	@Bean
	def createDataSource: DataSource = {
		val builder = new EmbeddedDatabaseBuilder()
		builder.setType(EmbeddedDatabaseType.HSQL)
			.addScript("create.ddl")
			.build()
	}

}
