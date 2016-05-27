package at.bayava.olingo.model

import javax.persistence._
import javax.validation.constraints.NotNull

import org.apache.olingo.odata2.api.annotation.edm.EdmProperty

import scala.beans.BeanProperty


/**
	* Created by pbayer.
	*/
@Entity
class Person {

	@Id
	@BeanProperty
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	var id: Long = _

	@NotNull
	@BeanProperty
	var name: String = _

	@BeanProperty
	var lastName: String = _

	@BeanProperty
	@EdmProperty(name = "addresses")
	@OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = Array(CascadeType.ALL), mappedBy = "person")
	var addresses: java.util.Set[Address] = _

}
