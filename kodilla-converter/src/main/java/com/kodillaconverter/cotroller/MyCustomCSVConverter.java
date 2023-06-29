package com.kodillaconverter.cotroller;

import com.kodillaconverter.domain.MyCustomClass;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotWritableException;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MyCustomCSVConverter implements HttpMessageConverter<Object> {

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        System.out.println("Checking if can read: " + clazz.getName() + ", " + mediaType);
        return MyCustomClass.class.isAssignableFrom(clazz) && MediaType.TEXT_PLAIN.includes(mediaType);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        System.out.println("Checking if can write: " + clazz.getName() + ", " + mediaType);
        return MyCustomClass.class.isAssignableFrom(clazz) && MediaType.TEXT_PLAIN.includes(mediaType);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        System.out.println("Getting supported media types");
        return List.of(MediaType.TEXT_PLAIN);
    }

    @Override
    public Object read(Class<?> clazz, HttpInputMessage inputMessage) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Reader reader = new BufferedReader(
                new InputStreamReader(
                        inputMessage.getBody(),
                        Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;

            while ((c = reader.read()) != -1)
                builder.append((char) c);
        }

        String[] fields = builder.toString().split(",");

        return new MyCustomClass(fields[0], fields[1], fields[2]);
    }

    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        MyCustomClass customClass = (MyCustomClass) o;
        String csvFormat = customClass.getFieldOne() + "," + customClass.getFieldTwo() + "," + customClass.getFieldThree();
        try (Writer writer = new BufferedWriter(
                new OutputStreamWriter(
                        outputMessage.getBody(),
                        Charset.forName(StandardCharsets.UTF_8.name())))) {
            writer.write(csvFormat);
        }
    }
}
