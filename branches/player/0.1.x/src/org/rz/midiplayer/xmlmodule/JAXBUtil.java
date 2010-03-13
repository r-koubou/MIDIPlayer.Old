package org.rz.midiplayer.xmlmodule;

import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author rz
 */
public class JAXBUtil<T>
{

    private final Class<T> clazz;

    public JAXBUtil( Class<T> clazz_ )
    {
        this.clazz = clazz_;
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public JAXBElement<T> loadFromClasspath( String xsdUri, String xml ) throws JAXBException, SAXException
    {
        JAXBContext context = JAXBContext.newInstance( clazz );
        SchemaFactory factory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
        Schema schema = factory.newSchema( clazz.getResource( xsdUri ) );

        // XML から Java オブジェクトに変換するための Unmarshaller を作成.
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema( schema );

        return unmarshaller.unmarshal( new StreamSource( getClass().getResourceAsStream( xml ) ), clazz );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     *
     */
    public JAXBElement<T> loadFromFile( String xsdUri, String xml ) throws JAXBException, SAXException
    {
        return loadFromFile( xsdUri, new File( xml ) );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public JAXBElement<T> loadFromFile( String xsdUri, File xml ) throws JAXBException, SAXException
    {
        JAXBContext context = JAXBContext.newInstance( clazz );
        SchemaFactory factory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
        Schema schema = factory.newSchema( clazz.getResource( xsdUri ) );

        // XML から Java オブジェクトに変換するための Unmarshaller を作成.
        Unmarshaller unmarshaller = context.createUnmarshaller();
        unmarshaller.setSchema( schema );

        return unmarshaller.unmarshal( new StreamSource( xml ), clazz );
    }

    ////////////////////////////////////////////////////////////////////////////////
    /**
     * 
     */
    public void writeToFile( JAXBElement<T> root, String xsdUri, File xml ) throws JAXBException, SAXException
    {
        JAXBContext context = JAXBContext.newInstance( clazz );
        SchemaFactory factory = SchemaFactory.newInstance( XMLConstants.W3C_XML_SCHEMA_NS_URI );
        Schema schema = factory.newSchema( clazz.getResource( xsdUri ) );

        Marshaller marshaller = context.createMarshaller();
        marshaller.setSchema( schema );
        marshaller.setProperty( Marshaller.JAXB_ENCODING, "UTF-8" );
        marshaller.setProperty( Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );

        marshaller.marshal( root, xml );

    }
}
