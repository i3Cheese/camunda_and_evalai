package ru.i3cheese.camundakotlin.xml.utils

import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Marshaller
import jakarta.xml.bind.UnmarshalException
import jakarta.xml.bind.annotation.XmlRootElement
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.StringWriter

class XMLUtils {

    companion object Methods {

        val errorLogger: Logger = LoggerFactory.getLogger("error")

        /**
         * JAXB unmarshalling
         */
        inline fun <reified T>  unmarshal(xml: String): T {

            val jaxbContext = JAXBContext.newInstance(T::class.java)
            val unmarshaller = jaxbContext.createUnmarshaller()

            xml.reader().use { it ->

                return try {
                    unmarshaller.unmarshal(it) as T
                } catch (ume: UnmarshalException) {
                    if (ume.linkedException != null) {
                        errorLogger.error(ume.linkedException.message)
                    }
                    throw Exception("Error unmarshalling: $ume")
                } catch (e: Exception) {
                    errorLogger.error(e.message)
                    throw Exception("Error unmarshalling: $e.message")
                }

            }
        }

        /**
         * JAXB marshalling
         */
        inline fun <reified T>  marshal(obj: T): String {

            val jaxbContext = JAXBContext.newInstance(T::class.java)
            val marshaller = jaxbContext.createMarshaller()
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
            val sw = StringWriter()
            sw.use {
                marshaller.marshal(obj, sw)
            }
            return sw.toString()
        }

        inline fun <reified T> isRoot() : Boolean =
            T::class.java.annotations.any { it.annotationClass == XmlRootElement::class.java }


    }

}
