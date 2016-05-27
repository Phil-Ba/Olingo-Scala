package at.bayava.olingo.service

import java.io.InputStream

import com.typesafe.scalalogging.LazyLogging
import org.apache.olingo.odata2.api.processor.ODataResponse
import org.apache.olingo.odata2.api.uri.info.{GetEntitySetUriInfo, GetEntityUriInfo, PutMergePatchUriInfo}
import org.apache.olingo.odata2.jpa.processor.api.ODataJPAContext
import org.apache.olingo.odata2.jpa.processor.core.ODataJPAProcessorDefault


/**
	* Created by pbayer.
	*/
class CustomODataJpaProcessor(oDataJPAContext: ODataJPAContext) extends ODataJPAProcessorDefault(oDataJPAContext) with
	LazyLogging {

	override def readEntitySet(uriInfo: GetEntitySetUriInfo, contentType: String): ODataResponse = {
		logger.info("Processing {}", uriInfo)
		val result = super.readEntitySet(uriInfo, contentType)
		logger.info("Result {}", result)
		result
	}


	override def updateEntity(uriParserResultView: PutMergePatchUriInfo,
														content: InputStream, requestContentType: String,
														merge: Boolean,
														contentType: String): ODataResponse = {
		logger.info("Processing {}", uriParserResultView)
		val result = super.updateEntity(uriParserResultView, content, requestContentType, merge, contentType)
		logger.info("Result {}", result)
		result

	}

	override def readEntity(uriInfo: GetEntityUriInfo,
													contentType: String): ODataResponse = {
		logger.info("Processing {}", uriInfo)
		val result = super.readEntity(uriInfo, contentType)
		logger.info("Result {}", result)
		result
	}
}
