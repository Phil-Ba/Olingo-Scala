package at.bayava.olingo.config

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer

/**
	* Created by pbayer.
	*/
class SpringConfigInit extends AbstractAnnotationConfigDispatcherServletInitializer {

	def getRootConfigClasses: Array[Class[_]] = {
		Array(classOf[SpringConfig])
	}

	def getServletConfigClasses: Array[Class[_]] = {
		null
	}

	def getServletMappings: Array[String] = {
		Array("/")
	}
}
