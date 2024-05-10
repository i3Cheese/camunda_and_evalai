package ru.i3cheese.camundakotlin.xml.model;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "organizationInfo")
public class OrganizationApplicationInfo {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "address")
    private String address;
    @XmlElement(name = "phone")
    private String phone;


}
