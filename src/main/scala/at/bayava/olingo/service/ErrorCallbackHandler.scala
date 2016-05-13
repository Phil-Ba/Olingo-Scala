package at.bayava.olingo.service

import com.typesafe.scalalogging.StrictLogging
import org.apache.olingo.odata2.api.ep.EntityProvider
import org.apache.olingo.odata2.api.processor.{ODataErrorCallback, ODataErrorContext, ODataResponse}

/**
	* Created by pbayer.
	*/
class ErrorCallbackHandler extends ODataErrorCallback with StrictLogging {

	override def handleError(context: ODataErrorContext): ODataResponse = {
		logger.error("ODate Exception: {}", context.getException)
		EntityProvider.writeErrorDocument(context)
	}

}
