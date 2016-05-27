package at.bayava.olingo.service

import javax.validation.ConstraintViolationException

import com.typesafe.scalalogging.StrictLogging
import org.apache.olingo.odata2.api.commons.HttpStatusCodes
import org.apache.olingo.odata2.api.ep.EntityProvider
import org.apache.olingo.odata2.api.processor.{ODataErrorCallback, ODataErrorContext, ODataResponse}
import org.apache.olingo.odata2.api.uri.UriNotMatchingException

/**
	* Created by pbayer.
	*/
class ErrorCallbackHandler extends ODataErrorCallback with StrictLogging {

	override def handleError(context: ODataErrorContext): ODataResponse = {
		logger.error("OData Exception:", context.getException)
		context.getException match {
			case e: ConstraintViolationException => context.setHttpStatus(HttpStatusCodes.BAD_REQUEST)
			case e: UriNotMatchingException => context.setHttpStatus(HttpStatusCodes.NOT_FOUND)
			case _ => context.setHttpStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR)
		}
		EntityProvider.writeErrorDocument(context)
	}

}
