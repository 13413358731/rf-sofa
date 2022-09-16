package com.realfinance.sofa.system.sdebank.datasync;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dom4JUtils {

    public static List<Iterator> getXml(Document document){
        //获取根节点
        Element rootElement = document.getRootElement();
        Iterator rootIterator = rootElement.elementIterator();
        List<Iterator> list=new ArrayList<>();
        while (rootIterator.hasNext()){
            Element dataStuChild = (Element) rootIterator.next();
            Iterator dataIterator = dataStuChild.elementIterator();
            list.add(dataIterator);
        }
        return list;
    }

   /* public static void main(String[] args) {
        try {
            //1.创建Reader对象
            SAXReader reader = new SAXReader();
            //2.加载xml
            Document document = reader.read(new File("C:\\Users\\rf\\Desktop\\1.XML"));
            List<Iterator> list = getXml(document);
            getSyncUser(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}
