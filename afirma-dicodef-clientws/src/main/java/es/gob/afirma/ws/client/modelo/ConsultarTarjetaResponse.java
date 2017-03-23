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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Fotografia" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0" form="unqualified"/>
 *         &lt;element name="TarjetaNSerieFCaducidad" type="{http://servicios.mdef.es/dicodef/consultarDICODEF.xsd}TarjetaNSerieFCaducidadType" minOccurs="0" form="unqualified"/>
 *         &lt;element name="RequiereCertificado" type="{http://servicios.mdef.es/dicodef/consultarDICODEF.xsd}Caracteres255Type" minOccurs="0" form="unqualified"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "fotografia",
    "tarjetaNSerieFCaducidad",
    "requiereCertificado"
})
@XmlRootElement(name = "ConsultarTarjetaResponse")
public class ConsultarTarjetaResponse {

    @XmlElement(name = "Fotografia")
    protected byte[] fotografia;
    @XmlElement(name = "TarjetaNSerieFCaducidad")
    protected String tarjetaNSerieFCaducidad;
    @XmlElement(name = "RequiereCertificado")
    protected String requiereCertificado;

    /**
     * Gets the value of the fotografia property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getFotografia() {
        return fotografia;
    }

    /**
     * Sets the value of the fotografia property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setFotografia(byte[] value) {
        this.fotografia = ((byte[]) value);
    }

    /**
     * Gets the value of the tarjetaNSerieFCaducidad property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTarjetaNSerieFCaducidad() {
        return tarjetaNSerieFCaducidad;
    }

    /**
     * Sets the value of the tarjetaNSerieFCaducidad property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTarjetaNSerieFCaducidad(String value) {
        this.tarjetaNSerieFCaducidad = value;
    }

    /**
     * Gets the value of the requiereCertificado property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRequiereCertificado() {
        return requiereCertificado;
    }

    /**
     * Sets the value of the requiereCertificado property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRequiereCertificado(String value) {
        this.requiereCertificado = value;
    }

}
