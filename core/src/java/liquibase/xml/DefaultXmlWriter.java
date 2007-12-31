package liquibase.xml;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import org.w3c.dom.Document;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerException;
import javax.xml.transform.Transformer;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.dom.DOMSource;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class DefaultXmlWriter implements XmlWriter {

    public void write(Document doc, OutputStream outputStream) throws IOException {
//        OutputFormat format = new OutputFormat(doc);
//        format.setIndenting(true);
//        format.setEncoding("UTF-8");
//        XMLSerializer serializer = new XMLSerializer(outputStream, format);
//        serializer.asDOMSerializer();
//        serializer.serialize(doc);

        try {
            TransformerFactory factory = TransformerFactory.newInstance();
            factory.setAttribute("indent-number", 4);

            Transformer transformer = factory.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            //need to nest outputStreamWriter to get around JDK 5 bug.  See http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6296446
            transformer.transform(new DOMSource(doc), new StreamResult(new OutputStreamWriter(outputStream, "utf-8")));
        } catch (TransformerException e) {
            throw new IOException(e.getMessage());
        }
    }
}
