package com.modeling.demo.service;

import com.modeling.demo.domains.Product;
import com.modeling.demo.repos.ProductRepos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

//@Service
//public class DataLoadService {
//    final ServletContext context;
//    final ProductRepos productRepos;
//
//    public DataLoadService(ServletContext context, ProductRepos productRepos) {
//        this.context = context;
//        this.productRepos = productRepos;
//    }
//
//    public void DataLoad() throws IOException, ParserConfigurationException, SAXException {
//        BufferedReader bufferedReader = new BufferedReader(new FileReader("csv/productData.csv", StandardCharsets.UTF_8));
//        String line;
//        Float[] param;
//        Float Path;
//        Float Article;
//        String Img ="";
//        ArrayList<Product> products = new ArrayList<>();
//        int i = 0;
//        while ((line = bufferedReader.readLine()) != null){
//            param = line.split(";");
//            Article = param[0];
//            Path = param[1];
//            URL loader = new URL(Path);
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(loader.openStream()));
//            String inputLine;
//            while ((inputLine = in.readLine()) != null){
//                if(inputLine.matches(".+product image.+" + Article +".+\\.jpg.+")) {
//                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//                    DocumentBuilder db = dbf.newDocumentBuilder();
//                    Document doc = db.parse(new ByteArrayInputStream(inputLine.getBytes()));
//                    NodeList nodes = doc.getElementsByTagName("img");
//                    Element e = (Element)nodes.item(0);
//                    Img = e.getAttribute("src");
//                    break;
//                }
//            }
//            products.add(new Product(Img,param[2],param[3],param[4]));
//            in.close();
//        }
//        bufferedReader.close();
//        for (Product p : products) {
//            productRepos.save(p);
//        }
//
//    }
//}
