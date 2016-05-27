package at.bayava.olingo.model

import javax.persistence._
import javax.validation.constraints.NotNull

import scala.beans.BeanProperty


/**
	* Created by pbayer.
	*/
@Entity
class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@BeanProperty
	var id: Long = _

	@BeanProperty
	@JoinColumn(name = "PERSON_ID")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	var person: Person = _

	@NotNull
	@BeanProperty
	var areaCode: Int = _

	@BeanProperty
	var streetName: String = _

	@BeanProperty
	var streetNumber: String = _

}
