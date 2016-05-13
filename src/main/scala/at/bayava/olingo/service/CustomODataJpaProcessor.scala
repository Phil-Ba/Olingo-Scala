package at.bayava.olingo.service

import com.typesafe.scalalogging.LazyLogging
import org.apache.olingo.odata2.api.processor.ODataResponse
import org.apache.olingo.odata2.api.uri.info.{GetEntitySetUriInfo, GetEntityUriInfo}
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAProcessorDefault


/**
	* Created by pbayer.
	*/
class CustomODataJpaProcessor(oDataJPAContext: ODataJPAContext) extends ODataJPAProcessorDefault(oDataJPAContext) with
	LazyLogging {


	override def readEntitySet(uriInfo: GetEntitySetUriInfo, contentType: String): ODataResponse = {

		System.out.print("test1123")
		logger.info("Processing {}", uriInfo)
		val result = super.readEntitySet(uriInfo, contentType)
		logger.info("Result {}", result)
		result

	}

	override def readEntity(uriInfo: GetEntityUriInfo,
													contentType: String): ODataResponse = {
		System.out.print("test1123")
		logger.info("Processing {}", uriInfo)
		val result = super.readEntity(uriInfo, contentType)
		logger.info("Result {}", result)
		result
	}
}
