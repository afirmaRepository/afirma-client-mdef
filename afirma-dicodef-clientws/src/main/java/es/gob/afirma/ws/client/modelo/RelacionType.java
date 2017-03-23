//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.23 at 03:05:53 PM CET 
//


package es.gob.afirma.ws.client.modelo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelacionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelacionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codRelacion" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="descRelacion" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="codEmpleo" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="descEmpleo" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="codEjercito" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="numEmpleado" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="escala" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="cuerpo" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="especialidad" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *         &lt;element name="dependencia" type="{http://www.w3.org/2001/XMLSchema}string" form="unqualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelacionType", propOrder = {
    "codRelacion",
    "descRelacion",
    "codEmpleo",
    "descEmpleo",
    "codEjercito",
    "numEmpleado",
    "escala",
    "cuerpo",
    "especialidad",
    "dependencia"
})
public class RelacionType {

    @XmlElement(required = true)
    protected String codRelacion;
    @XmlElement(required = true)
    protected String descRelacion;
    @XmlElement(required = true)
    protected String codEmpleo;
    @XmlElement(required = true)
    protected String descEmpleo;
    @XmlElement(required = true)
    protected String codEjercito;
    @XmlElement(required = true)
    protected String numEmpleado;
    @XmlElement(required = true)
    protected String escala;
    @XmlElement(required = true)
    protected String cuerpo;
    @XmlElement(required = true)
    protected String especialidad;
    @XmlElement(required = true)
    protected String dependencia;

    /**
     * Gets the value of the codRelacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodRelacion() {
        return codRelacion;
    }

    /**
     * Sets the value of the codRelacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodRelacion(String value) {
        this.codRelacion = value;
    }

    /**
     * Gets the value of the descRelacion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescRelacion() {
        return descRelacion;
    }

    /**
     * Sets the value of the descRelacion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescRelacion(String value) {
        this.descRelacion = value;
    }

    /**
     * Gets the value of the codEmpleo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEmpleo() {
        return codEmpleo;
    }

    /**
     * Sets the value of the codEmpleo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEmpleo(String value) {
        this.codEmpleo = value;
    }

    /**
     * Gets the value of the descEmpleo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescEmpleo() {
        return descEmpleo;
    }

    /**
     * Sets the value of the descEmpleo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescEmpleo(String value) {
        this.descEmpleo = value;
    }

    /**
     * Gets the value of the codEjercito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEjercito() {
        return codEjercito;
    }

    /**
     * Sets the value of the codEjercito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEjercito(String value) {
        this.codEjercito = value;
    }

    /**
     * Gets the value of the numEmpleado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumEmpleado() {
        return numEmpleado;
    }

    /**
     * Sets the value of the numEmpleado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumEmpleado(String value) {
        this.numEmpleado = value;
    }

    /**
     * Gets the value of the escala property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEscala() {
        return escala;
    }

    /**
     * Sets the value of the escala property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEscala(String value) {
        this.escala = value;
    }

    /**
     * Gets the value of the cuerpo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCuerpo() {
        return cuerpo;
    }

    /**
     * Sets the value of the cuerpo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCuerpo(String value) {
        this.cuerpo = value;
    }

    /**
     * Gets the value of the especialidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * Sets the value of the especialidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEspecialidad(String value) {
        this.especialidad = value;
    }

    /**
     * Gets the value of the dependencia property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDependencia() {
        return dependencia;
    }

    /**
     * Sets the value of the dependencia property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDependencia(String value) {
        this.dependencia = value;
    }

}
