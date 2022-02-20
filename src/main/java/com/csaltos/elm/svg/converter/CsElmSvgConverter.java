package com.csaltos.elm.svg.converter;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.util.*;

public class CsElmSvgConverter extends DefaultHandler {

    int _indentLevel = 0;
    boolean _isFirstElement;

    public void startDocument() throws SAXException {
        this._indentLevel = 0;
        this._isFirstElement = true;
    }

    protected void printIndent(boolean isFirstElement) {
        System.out.print("\n");
        for (int i = 0; i < this._indentLevel; i++) {
            System.out.print("  ");
        }

        if (isFirstElement) {
            System.out.print("  ");
        } else {
            System.out.print(", ");
        }
    }

    @Override
    public void startElement(String namespaceURI,
            String localName,
            String qName,
            Attributes atts)
            throws SAXException {

        this.printIndent(this._isFirstElement);

        System.out.print(this.fromSvgElementToElm(localName));
        System.out.print(" [");

        // NOTE: inside a new element/node, we start with no child nodes.
        this._isFirstElement = true;
        this._indentLevel++;

        boolean isFirstAttr = true;
        for (int i = 0; i < atts.getLength(); i++) {

            String attrName = atts.getLocalName(i);
            String attrValue = atts.getValue(i);

            this.printIndent(isFirstAttr);
            System.out.print(this.fromSvgAttrToElm(attrName));
            System.out.print(" \"");
            System.out.print(attrValue.toString());
            System.out.print("\"");

            isFirstAttr = false;
        }

        System.out.print("] [");
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        System.out.print("]");
        this._isFirstElement = false;
        this._indentLevel--;
    }

    protected static final Map<String, String> _fromSvgElementToElm;
    protected static final Map<String, String> _fromSvgAttributeToElm;

    static {
        Map<String, String> me = new HashMap<String, String>();
        me.put("text", "text'");
        me.put("path", "Svg.path");
        me.put("style", "Svg.style");
        me.put("svg", "Svg.svg");
        _fromSvgElementToElm = me;

        Map<String, String> ma = new HashMap<String, String>();
        ma.put("d", "Svg.Attributes.d");
        ma.put("height", "Svg.Attributes.height");
        ma.put("path", "Svg.Attributes.path");
        ma.put("style", "Svg.Attributes.style");
        ma.put("text", "text");
        ma.put("width", "Svg.Attributes.width");
        ma.put("fill", "Svg.Attributes.fill");
        _fromSvgAttributeToElm = ma;
    }

    public String fromSvgElementToElm(String svgName) {
        String r = _fromSvgElementToElm.get(svgName);
        if (r == null) {
            return svgName;
        } else {
            return r;
        }
    }

    public String fromSvgAttrToElm(String svgName) {
        String r = _fromSvgAttributeToElm.get(svgName);
        if (r == null) {
            return svgName;
        } else {
            return r;
        }
    }
}
