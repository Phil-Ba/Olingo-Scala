package at.bayava.olingo.service

import javax.persistence.EntityManagerFactory

import org.apache.olingo.odata2.api.ODataCallback
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import scala.reflect.runtime.{universe => ru}

/**
	* Created by pbayer.
	*/

class AnnotationSampleServiceFactory extends CustomODataJPAServiceFactory {
	override def initializeODataJPAContext: ODataJPAContext = {
		val oDataJPAContext: ODataJPAContext = getODataJPAContext
		oDataJPAContext.setEntityManagerFactory(AnnotationSampleServiceFactory.emf)
		oDataJPAContext.setPersistenceUnitName("olingo")
		oDataJPAContext
	}

	override def getCallback[T <: ODataCallback](callbackInterface: Class[T]): T = {
		callbackInterface match {
			case _ if callbackInterface.isAssignableFrom(classOf[ErrorCallbackHandler]) => new ErrorCallbackHandler()
				.asInstanceOf[T]
			case _ => super.getCallback(callbackInterface)
		}
	}
}

@Component
object AnnotationSampleServiceFactory {

	@Autowired
	private var emf: EntityManagerFactory = _

}
