package at.bayava.olingo.service

import javax.ws.rs.{GET, Path}

import at.bayava.olingo.Test
import org.springframework.beans.factory.annotation.Autowired


/**
	* Created by pbayer.
	*/
@Path("my")
class RestTest {

	@Autowired
	var test: Test = _

	@GET
	@Path("test")
	def doTest() = {
		test.toString
	}

}
