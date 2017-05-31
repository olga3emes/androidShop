package com.example.olga.shop.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 30/5/17.
 */

    public class RssHandler extends DefaultHandler {
        private List<Report> reports;
        private Report reportActual;
        private StringBuilder sbTexto;
        public List<Report> getReports(){
            return reports;
        }
        @Override
        public void characters(char[] ch, int start, int length)
                throws SAXException {
            super.characters(ch, start, length);
            if (this.reportActual != null)
                sbTexto.append(ch, start, length);
        }
        @Override
        public void endElement(String uri, String localName, String name)
                throws SAXException {
            super.endElement(uri, localName, name);
            if (this.reportActual != null) {
                if (localName.equals("title")) {
                    reportActual.setTitle(sbTexto.toString());
                } else if (localName.equals("link")) {
                    reportActual.setLink(sbTexto.toString().replace(" ", ""));
                } else if (localName.equals("description")) {
                    reportActual.setDescription(sbTexto.toString());
                } else if (localName.equals("pubDate")) {
                    reportActual.setDate(sbTexto.toString());
                } else if (localName.equals("item")) {
                    reports.add(reportActual);
                }
                sbTexto.setLength(0);
            }

        }
        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
            reports = new ArrayList<Report>();
            sbTexto = new StringBuilder();
        }
        @Override
        public void startElement(String uri, String localName,
                                 String name, Attributes attributes) throws SAXException {
            super.startElement(uri, localName, name, attributes);
            if (localName.equals("item")) {
                reportActual = new Report();
            }
        }
    }


            

