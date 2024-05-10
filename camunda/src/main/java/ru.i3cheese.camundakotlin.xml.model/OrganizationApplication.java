package ru.i3cheese.camundakotlin.xml.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "organizationApplication")
public class OrganizationApplication {
//    @XmlElement(name = "organization")
//    private OrganizationInfo organization;
    @XmlElement(name = "organizationName")
    private String organizationName;
}
