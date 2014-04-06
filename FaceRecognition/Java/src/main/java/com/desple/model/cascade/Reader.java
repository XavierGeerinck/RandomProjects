package com.desple.model.cascade;

import com.desple.model.structure.Cascade;
import com.desple.model.structure.OpencvStorage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Reader {
    public static OpencvStorage readHaarCascade(String path) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(OpencvStorage.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

            String xmlContent = readFile(path, Charset.defaultCharset());
            StringReader reader = new StringReader(xmlContent);

            OpencvStorage opencvStorage = (OpencvStorage) unmarshaller.unmarshal(reader);

            return opencvStorage;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoding.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
