package com.tilialog;

import com.jcabi.xml.XML;
import com.jcabi.xml.XMLDocument;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

public class XmlTest {

    @Test
    public void pomVersion() {
        XML xml = new XMLDocument(
            "<orders><order id=\"4\">Coffee to go</order></orders>"
        );
        String id = xml.xpath("//order/@id").get(0);
        String name = xml.xpath("//order[@id=4]/text()").get(0);
        MatcherAssert.assertThat(
            id,
            Matchers.equalTo("4")
        );
        MatcherAssert.assertThat(
            name,
            Matchers.equalTo("Coffee to go")
        );

        String str = "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
            "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
            "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
            "    <version>0.14.0</version>\n" +
            "</project>";
        MatcherAssert.assertThat(
            new XMLDocument(str).xpath("/*[local-name()=\"project\"]/*[local-name()=\"version\"]/text()").get(0),
            Matchers.equalTo("0.14.0")
        );
    }
}
