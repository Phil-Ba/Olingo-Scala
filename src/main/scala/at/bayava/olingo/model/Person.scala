package at.bayava.olingo.model

import javax.persistence.{Entity, GeneratedValue, GenerationType, Id}
import javax.validation.constraints.NotNull

import com.fasterxml.jackson.databind.BeanProperty

/**
	* Created by pbayer.
	*/
@Entity
class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@BeanProperty
	var id: Long = _

	@BeanProperty
	@NotNull
	var name: String = _

	@BeanProperty
	var lastName: String = _

}
