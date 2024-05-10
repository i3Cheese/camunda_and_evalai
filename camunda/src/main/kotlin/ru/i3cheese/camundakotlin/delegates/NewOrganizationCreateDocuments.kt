package ru.i3cheese.camundakotlin.delegates

import org.apache.fop.apps.FopFactory
import org.apache.fop.apps.MimeConstants
import org.camunda.bpm.engine.delegate.DelegateExecution
import org.camunda.bpm.engine.delegate.JavaDelegate
import org.camunda.bpm.engine.variable.value.StringValue
import org.springframework.stereotype.Component
import ru.i3cheese.camundakotlin.xml.utils.XMLUtils
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.time.Instant
import javax.xml.datatype.DatatypeFactory
import javax.xml.transform.TransformerFactory
import javax.xml.transform.sax.SAXResult
import javax.xml.transform.Source
import javax.xml.transform.Result
import javax.xml.transform.stream.StreamSource
import kotlin.io.path.Path


@Component
class NewOrganizationCreateDocuments: JavaDelegate {
    override fun execute(execution: DelegateExecution) {
        val xmlNewOrganizationApplication = execution.getVariableTyped<StringValue>("xmlNewOrganizationApplication", true).value;
        println("NewOrganizationCreateDocuments: $xmlNewOrganizationApplication");
        val newOrganizationApplication = XMLUtils.unmarshal<ru.i3cheese.camundakotlin.xml.genmodel.NewOrganizationApplication>(
            xmlNewOrganizationApplication
        );
        val newOrganizationOrder = ru.i3cheese.camundakotlin.xml.genmodel.NewOrganizationOrder();
        newOrganizationOrder.organizationInfo = ru.i3cheese.camundakotlin.xml.genmodel.NewOrganizationOrder.OrganizationInfo();
        newOrganizationOrder.organizationInfo.name = newOrganizationApplication.organizationInfo.name;
        newOrganizationOrder.organizationInfo.email = newOrganizationApplication.organizationInfo.email;
        newOrganizationOrder.organizationInfo.phone = newOrganizationApplication.organizationInfo.phone;
        newOrganizationOrder.organizationInfo.address = newOrganizationApplication.organizationInfo.address;

        newOrganizationOrder.organizationContactPerson = ru.i3cheese.camundakotlin.xml.genmodel.NewOrganizationOrder.OrganizationContactPerson();
        newOrganizationOrder.organizationContactPerson.name = newOrganizationApplication.organizationContactPerson.name;
        newOrganizationOrder.organizationContactPerson.email = newOrganizationApplication.organizationContactPerson.email;
        newOrganizationOrder.organizationContactPerson.phone = newOrganizationApplication.organizationContactPerson.phone;
        newOrganizationOrder.organizationContactPerson.position = newOrganizationApplication.organizationContactPerson.position;

        newOrganizationOrder.signer = ru.i3cheese.camundakotlin.xml.genmodel.NewOrganizationOrder.Signer();
        newOrganizationOrder.signer.name = execution.getVariable("signerName") as String;
        newOrganizationOrder.signer.position = execution.getVariable("signerPosition") as String;

        newOrganizationOrder.orderDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(Instant.now().toString());

        val xmlNewOrganizationOrder = XMLUtils.marshal(newOrganizationOrder);

        val tmpDir = kotlin.io.path.createTempDirectory("NewOrganizationCreateDocuments")
        val xmlFile = tmpDir.resolve("NewOrganizationOrder.xml");
        xmlFile.toFile().writeText(xmlNewOrganizationOrder);
        val pdfFile = tmpDir.resolve("NewOrganizationOrder.pdf");
        println(this::class.java.classLoader.getResource("/xsl/NewOrganizationOrder.xsl")?.readText())
        println(this::class.java.classLoader.getResource("/fop/fop.xconf")!!.toURI())
        println("xmlFile: $xmlFile")
        println("pdfFile: $pdfFile")
        // run process
        val fopFactory: FopFactory = FopFactory.newInstance(
//            this::class.java.classLoader.getResource("/fop/fop.xconf")!!.toURI()
            // TODO: up thing doesnt render font but down thing does but not use resource
            File("src/main/resources/fop/fop.xconf"),
        )


        val out: OutputStream = BufferedOutputStream(FileOutputStream(pdfFile.toFile()))

        try {
            val fop = fopFactory.newFop(MimeConstants.MIME_PDF, out)

            val factory = TransformerFactory.newInstance()
            val xslt: Source = StreamSource(this::class.java.classLoader.getResource("/xsl/NewOrganizationOrder.xsl")!!.openStream())
            val transformer = factory.newTransformer(xslt)
            val src: Source = StreamSource(xmlFile.toFile())
            val res: Result = SAXResult(fop.defaultHandler)
            transformer.transform(src, res)
        } finally {
            out.close()
        }
        execution.setVariable("xmlNewOrganizationOrder", xmlNewOrganizationOrder);
        execution.setVariable("someText", "Some text from NewOrganizationCreateDocuments")
    }
}
