package com.example.xmlprocessing.config;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {

    private static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss";

    @Override
    public Date unmarshal(String s) throws Exception {
        DateFormat format = new SimpleDateFormat(dateFormat);

        return format.parse(s);
    }

    @Override
    public String marshal(Date date) throws Exception {
        return date.toString();
    }

}
