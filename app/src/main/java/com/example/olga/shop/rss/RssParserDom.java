package com.example.olga.shop.rss;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by olga on 30/5/17.
 */

public class RssParserDom
{
    private URL rssUrl;
    public RssParserDom(String url)
    {
        try
        {
            this.rssUrl = new URL(url);
        }
        catch (MalformedURLException e)
        {
            throw new RuntimeException(e);
        }
    }
    public List<Report> parse()
    {
        //Instanciamos la fábrica para DOM
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        List<Report> noticias = new ArrayList<Report>();
        try
        {
            //Creamos un nuevo parser DOM
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Realizamos lalectura completa del XML
            Document dom = builder.parse(this.getInputStream());
            //Nos posicionamos en el nodo principal del árbol (<rss>)
            Element root = dom.getDocumentElement();
            //Localizamos todos los elementos <item>
            NodeList items = root.getElementsByTagName("item");
            //Recorremos la lista de noticias
            for (int i=0; i<items.getLength(); i++)
            {
                Report noticia = new Report();
                //Obtenemos la noticia actual
                Node item = items.item(i);
                //Obtenemos la lista de datos de la noticia actual
                NodeList datosReport = item.getChildNodes();
                //Procesamos cada dato de la noticia
                for (int j=0; j<datosReport.getLength(); j++)
                {
                    Node dato = datosReport.item(j);
                    String etiqueta = dato.getNodeName();
                    if (etiqueta.equals("title"))
                    {
                        String texto = obtenerTexto(dato);
                        noticia.setTitle(texto);
                    }
                    else if (etiqueta.equals("link"))
                    {
                        noticia.setLink(dato.getFirstChild().getNodeValue());
                    }
                    else if (etiqueta.equals("description"))
                    {
                        String texto = obtenerTexto(dato);
                        noticia.setDescription(texto);
                    }
                    else if (etiqueta.equals("pubDate"))
                    {
                        noticia.setDate(dato.getFirstChild().getNodeValue());
                    }
                }
                noticias.add(noticia);
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
        return noticias;
    }
    private String obtenerTexto(Node dato) //Recorre los nodos hijos para componer el texto completo
    {
        StringBuilder texto = new StringBuilder();
        NodeList fragmentos = dato.getChildNodes();
        for (int k=0;k<fragmentos.getLength();k++)
        {
            texto.append(fragmentos.item(k).getNodeValue());
        }
        return texto.toString();
    }
    private InputStream getInputStream()
    {
        try
        {
            return rssUrl.openConnection().getInputStream();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }
}