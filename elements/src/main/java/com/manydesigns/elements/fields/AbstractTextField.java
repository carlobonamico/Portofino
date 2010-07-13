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

package com.manydesigns.elements.fields;

import com.manydesigns.elements.annotations.MaxLength;
import com.manydesigns.elements.reflection.PropertyAccessor;
import com.manydesigns.elements.xml.XhtmlBuffer;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
*/
public abstract class AbstractTextField extends AbstractField {
    public static final String copyright =
            "Copyright (c) 2005-2010, ManyDesigns srl";

    protected String stringValue;
    protected boolean autoCapitalize = false;
    protected Integer maxLength = null;

    protected int size = 70;

    //--------------------------------------------------------------------------
    // Costruttori
    //--------------------------------------------------------------------------
    public AbstractTextField(PropertyAccessor accessor) {
        this(accessor, null);
    }

    public AbstractTextField(PropertyAccessor accessor, String prefix) {
        super(accessor, prefix);
        if (accessor.isAnnotationPresent(MaxLength.class)) {
            maxLength = accessor.getAnnotation(MaxLength.class).value();
        }
    }

    //--------------------------------------------------------------------------
    // Implementazione di Element
    //--------------------------------------------------------------------------
    public boolean validate() {
        boolean result = true;
        if (required && (stringValue == null || stringValue.length() == 0)) {
            errors.add(getText("elements.error.field.required"));
            result = false;
        }
        if (maxLength != null && stringValue != null
                && stringValue.length() > maxLength) {
            errors.add(getText("elements.error.field.length.exceeded", maxLength));
            result = false;
        }
        return result;
    }

    //--------------------------------------------------------------------------
    // Field implementation
    //--------------------------------------------------------------------------
    public void valueToXhtml(XhtmlBuffer xb) {
        switch (mode) {
            case EDIT:
                valueToXhtmlEdit(xb);
                break;
            case PREVIEW:
                valueToXhtmlPreview(xb);
                break;
            case VIEW:
                valueToXhtmlView(xb);
                break;
            case HIDDEN:
                xb.writeInputHidden(inputName, stringValue);
                break;
        }
    }

    protected void valueToXhtmlEdit(XhtmlBuffer xb) {
        xb.openElement("input");
        xb.addAttribute("type", "text");
        xb.addAttribute("class", "text");
        xb.addAttribute("id", id);
        xb.addAttribute("name", inputName);
        xb.addAttribute("value", stringValue);
        if (maxLength != null) {
            int textInputSize = (maxLength > size)
                    ? size
                    : maxLength;
            xb.addAttribute("maxlength",
                    Integer.toString(maxLength));
            xb.addAttribute("size",
                    Integer.toString(textInputSize));
        }
        xb.closeElement("input");
    }

    protected void valueToXhtmlPreview(XhtmlBuffer xb) {
        valueToXhtmlView(xb);
        xb.writeInputHidden(inputName, stringValue);
    }

    protected void valueToXhtmlView(XhtmlBuffer xb) {
        xb.openElement("div");
        xb.addAttribute("class", "value");
        xb.addAttribute("id", id);
        if (href != null) {
            xb.openElement("a");
            xb.addAttribute("href", href);
        }
        xb.write(stringValue);
        if (href != null) {
            xb.closeElement("a");
        }
        xb.closeElement("div");
    }

    //--------------------------------------------------------------------------
    // Getters/setters
    //--------------------------------------------------------------------------
    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public boolean isAutoCapitalize() {
        return autoCapitalize;
    }

    public void setAutoCapitalize(boolean autoCapitalize) {
        this.autoCapitalize = autoCapitalize;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}