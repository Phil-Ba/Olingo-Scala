package at.bayava.olingo.config

import at.bayava.olingo.service.RestTest
import org.apache.olingo.odata2.core.rest.ODataRootLocator
import org.glassfish.jersey.server.ResourceConfig

/**
	* Created by pbayer.
	*/
class RestConfig extends ResourceConfig {

	register(classOf[RestTest])
	register(classOf[ODataRootLocator])

}
