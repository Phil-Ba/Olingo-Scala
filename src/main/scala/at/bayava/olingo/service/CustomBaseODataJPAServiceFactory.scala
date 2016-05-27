/** *****************************************************************************
	* Licensed to the Apache Software Foundation (ASF) under one
	* or more contributor license agreements. See the NOTICE file
	* distributed with this work for additional information
	* regarding copyright ownership. The ASF licenses this file
	* to you under the Apache License, Version 2.0 (the
	* "License"); you may not use this file except in compliance
	* with the License. You may obtain a copy of the License at
	*
	* http://www.apache.org/licenses/LICENSE-2.0
	*
	* Unless required by applicable law or agreed to in writing,
	* software distributed under the License is distributed on an
	* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
	* KIND, either express or implied. See the License for the
	* specific language governing permissions and limitations
	* under the License.
	* *****************************************************************************/
package at.bayava.olingo.service

import org.apache.olingo.odata2.api.edm.provider.EdmProvider
import org.apache.olingo.odata2.api.exception.ODataException
import org.apache.olingo.odata2.api.processor.{ODataContext, ODataErrorCallback, ODataSingleProcessor}
import org.apache.olingo.odata2.api.{ODataCallback, ODataService, ODataServiceFactory}
import org.apache.olingo.odata2.jpa.processor.api.exception.{ODataJPAErrorCallback, ODataJPARuntimeException}
import org.apache.olingo.odata2.jpa.processor.api.factory.{ODataJPAAccessFactory, ODataJPAFactory}
import org.apache.olingo.odata2.jpa.processor.api.{ODataJPAContext, ODataJPATransaction, OnJPAWriteContent}

abstract class CustomBaseODataJPAServiceFactory extends ODataServiceFactory {
	private var oDataJPAContext: ODataJPAContext = null
	private var oDataContext: ODataContext = null
	private var setDetailErrors: Boolean = false
	private var onJPAWriteContent: OnJPAWriteContent = null
	private var oDataJPATransaction: ODataJPATransaction = null

	@throws[ODataJPARuntimeException]
	def initializeODataJPAContext: ODataJPAContext

	@throws[ODataException]
	override final def createService(ctx: ODataContext): ODataService = {
		oDataContext = ctx
		oDataJPAContext = initializeODataJPAContext
		validatePreConditions
		val factory: ODataJPAFactory = ODataJPAFactory.createFactory
		val accessFactory: ODataJPAAccessFactory = factory.getODataJPAAccessFactory
		if (oDataJPAContext.getODataContext == null) {
			oDataJPAContext.setODataContext(ctx)
		}
		val odataJPAProcessor: ODataSingleProcessor = new CustomODataJpaProcessor(oDataJPAContext)
		val edmProvider: EdmProvider = accessFactory.createJPAEdmProvider(oDataJPAContext)
		createODataSingleProcessorService(edmProvider, odataJPAProcessor)
	}

	@throws[ODataJPARuntimeException]
	final def getODataJPAContext: ODataJPAContext = {
		if (oDataJPAContext == null) {
			oDataJPAContext = ODataJPAFactory.createFactory.getODataJPAAccessFactory.createODataJPAContext
		}
		if (oDataContext != null) {
			oDataJPAContext.setODataContext(oDataContext)
		}
		return oDataJPAContext
	}

	@SuppressWarnings(Array("unchecked")) override def getCallback[T <: ODataCallback](callbackInterface: Class[T]): T
	= {
		if (setDetailErrors == true) {
			if (callbackInterface.isAssignableFrom(classOf[ODataErrorCallback])) {
				return new ODataJPAErrorCallback().asInstanceOf[T]
			}
		}
		if (onJPAWriteContent != null) {
			if (callbackInterface.isAssignableFrom(classOf[OnJPAWriteContent])) {
				return onJPAWriteContent.asInstanceOf[T]
			}
		}
		if (oDataJPATransaction != null) {
			if (callbackInterface.isAssignableFrom(classOf[ODataJPATransaction])) {
				return oDataJPATransaction.asInstanceOf[T]
			}
		}
		null.asInstanceOf[T]
	}

	protected def setOnWriteJPAContent(onJPAWriteContent: OnJPAWriteContent) {
		this.onJPAWriteContent = onJPAWriteContent
	}

	protected def setODataJPATransaction(oDataJPATransaction: ODataJPATransaction) {
		this.oDataJPATransaction = oDataJPATransaction
	}

	protected def setDetailErrors(setDetailErrors: Boolean) {
		this.setDetailErrors = setDetailErrors
	}

	@throws[ODataJPARuntimeException]
	private def validatePreConditions {
		if (oDataJPAContext.getEntityManagerFactory == null) {
			throw ODataJPARuntimeException.throwException(ODataJPARuntimeException.ENTITY_MANAGER_NOT_INITIALIZED, null)
		}
	}
}