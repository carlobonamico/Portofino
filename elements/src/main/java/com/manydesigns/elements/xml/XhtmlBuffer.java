/*
 * Copyright (C) 2005-2010 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * Unless you have purchased a commercial license agreement from ManyDesigns srl,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3 as published by
 * the Free Software Foundation.
 *
 * There are special exceptions to the terms and conditions of the GPL 
 * as it is applied to this software. View the full text of the 
 * exception in file OPEN-SOURCE-LICENSE.txt in the directory of this
 * software distribution.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  02111-1307  USA
 *
 */


package com.manydesigns.elements.xml;

import org.apache.commons.lang.StringEscapeUtils;

import java.io.Writer;

/**
 * @author Paolo Predonzani - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo      - angelo.lupo@manydesigns.com
 */
public class XhtmlBuffer extends XmlBuffer implements XhtmlFragment {
    public static final String copyright =
            "Copyright (c) 2005-2010, ManyDesigns srl";

    //--------------------------------------------------------------------------
    // Constructors
    //--------------------------------------------------------------------------

    public XhtmlBuffer() {
        super();
    }

    public XhtmlBuffer(Writer writer) {
        super(writer);
    }

    //~--- methods ------------------------------------------------------------

    public void writeAnchor(String href, String text) {
        writeAnchor(href, text, null, null);
    }

    public void writeNbsp() {
        writeNoHtmlEscape("&nbsp;");
    }

    public void writeAnchor(String href, String text,
                            String classStr, String title) {
        openElement("a");
        addAttribute("href", href);
        addAttribute("class", classStr);
        addAttribute("title", title);
        write(text);
        closeElement("a");
    }

    public void writeCaption(String text) {
        openElement("caption");
        write(text);
        closeElement("caption");
    }

    public void writeLegend(String text, String htmlClass) {
        openElement("legend");
        addAttribute("class", htmlClass);
        write(text);
        closeElement("legend");
    }

    public void writeLabel(String text, String forId, String htmlClass) {
        openElement("label");
        if (forId != null) {
            addAttribute("for", forId);
        }

        addAttribute("class", htmlClass);
        write(text);
        closeElement("label");
    }

    public void writeBr() {
//        openElement("br");
//        closeElement("br");
        writeNoHtmlEscape("<br />");
    }

    public void writeDoctype() {
//        writeDoctype("html", "PUBLIC", "-//W3C//DTD XHTML 1.0 Transitional//EN",
//                     "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd");
        writeDoctype("html", "PUBLIC", "-//W3C//DTD XHTML 1.0 Strict//EN",
                "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd");
    }

    public void writeH1(String title) {
        openElement("h1");
        write(title);
        closeElement("h1");
    }

    public void writeH2(String title) {
        openElement("h2");
        write(title);
        closeElement("h2");
    }

    public void writeHr() {
        openElement("hr");
        closeElement("hr");
    }

    public void writeInputCheckbox(String id, String name, String value,
                                   boolean checked) {
        writeInputCheckbox(id, name, value, checked, false, null);
    }

    public void writeInputCheckbox(String id, String name, String value,
                                   boolean checked, boolean disabed, String cssClass) {
        openElement("input");
        addAttribute("id", id);
        addAttribute("type", "checkbox");
        addAttribute("name", name);
        addAttribute("value", value);

        if (checked) {
            addAttribute("checked", "checked");
        }

        if (disabed)
            addAttribute("disabled", "disabled");

        addAttribute("class", cssClass);

        closeElement("input");
    }

    public void writeInputHidden(String name, String value) {
        openElement("input");
        addAttribute("type", "hidden");
        addAttribute("name", name);
        addAttribute("value", value);
        closeElement("input");
    }

    public void writeInputHidden(String id, String name, String value) {
        openElement("input");
        addAttribute("type", "hidden");
        addAttribute("id", id);
        addAttribute("name", name);
        addAttribute("value", value);
        closeElement("input");
    }

    public void writeInputRadio(String id, String name, String value,
                                boolean checked) {
        writeInputRadio(id, name, value, checked, false, null);
    }

    public void writeInputRadio(String id, String name, String value,
                                boolean checked, boolean disabled) {
        writeInputRadio(id, name, value, checked, disabled, null);
    }

    public void writeInputRadio(String id, String name, String value,
                                boolean checked, boolean disabled, String onClickEvent) {
        openElement("input");
        addAttribute("type", "radio");
        addAttribute("id", id);
        addAttribute("name", name);
        addAttribute("value", value);

        if (checked) {
            addAttribute("checked", "checked");
        }

        if (disabled)
            addAttribute("disabled", "disabled");

        addAttribute("onclick", onClickEvent);
        closeElement("input");
    }

    public void writeInputSubmit(String name, String value, java.lang.String onSubmit) {
        openElement("input");
        addAttribute("type", "submit");
        addAttribute("name", name);
        addAttribute("value", value);
        addAttribute("class", "submit");
        addAttribute("onclick", onSubmit);
        closeElement("input");
    }

    public void writeInputText(String id, String name, String value,
                               String htmlClass, String size) {
        openElement("input");
        addAttribute("id", id);
        addAttribute("type", "text");
        addAttribute("name", name);
        addAttribute("value", value);
        addAttribute("class", htmlClass);
        addAttribute("size", size);
        closeElement("input");
    }

    public void writeOption(String value, boolean selected, String text) {
        openElement("option");
        addAttribute("value", value);

        if (selected) {
            addAttribute("selected", "selected");
        }

        write(text);
        closeElement("option");
    }

    public void writeParagraph(String value) {
        openElement("p");
        write(value);
        closeElement("p");
    }

    public void writeInputFile(String id, String name, String value,
                               boolean disabled) {
        openElement("input");
        addAttribute("type", "file");
        addAttribute("id", id);
        addAttribute("name", name);
        addAttribute("value", value);
        addAttribute("class", "text");
        if (disabled)
            addAttribute("disabled", "disabled");
        closeElement("input");
    }

    public void writeInputFile(String id, String name, boolean disabled) {
        writeInputFile(id, name, null, disabled);
    }

    public void writeImage(String src, String alt, String title,
                           String id, String htmlClass) {
        openElement("img");
        addAttribute("src", src);
        addAttribute("alt", alt);
        addAttribute("class", htmlClass);
        addAttribute("id", id);
        addAttribute("title", title);
        closeElement("img");
    }

    public void openFormElement(String id, String method,
                                String action, String htmlClass) {
        openElement("form");
        addAttribute("id", id);
        addAttribute("method", method);
        addAttribute("action", action);
        addAttribute("class", htmlClass);
    }

    public void closeFormElement() {
        closeElement("form");
    }

    @Override
    public String escape(String s) {
        return StringEscapeUtils.escapeHtml(s);
    }


    public void toXhtml(XhtmlBuffer xb) {
        xb.write(this);
    }
}