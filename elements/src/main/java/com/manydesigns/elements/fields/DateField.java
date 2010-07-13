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

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.elements.Mode;
import com.manydesigns.elements.Util;
import com.manydesigns.elements.reflection.PropertyAccessor;
import com.manydesigns.elements.xml.XhtmlBuffer;
import org.apache.commons.lang.StringEscapeUtils;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
*/
public class DateField extends AbstractTextField {
    public static final String copyright =
            "Copyright (c) 2005-2010, ManyDesigns srl";

    protected Date dateValue;
    protected SimpleDateFormat simpleDateFormat;
    protected boolean dateFormatError;

    public DateField(PropertyAccessor accessor) {
        this(accessor, null);
    }

    public DateField(PropertyAccessor accessor, String prefix) {
        super(accessor, prefix);
        setMaxLength(10);
    }

    public void readFromRequest(HttpServletRequest req) {
        if (mode == Mode.VIEW) {
            return;
        }

        String reqValue = req.getParameter(inputName);
        if (reqValue == null) {
            return;
        }

        stringValue = reqValue.trim();
        dateFormatError = false;
        dateValue = null;

        if (stringValue.length() == 0) {
            return;
        }

        ParsePosition parsePos = new ParsePosition(0);
        ensureDateFormatPresent();
        java.util.Date parsedDate =
                simpleDateFormat.parse(stringValue, parsePos);

        if (stringValue.length() != parsePos.getIndex()) {
            dateFormatError = true;
            return;
        }
        dateValue = new Date(parsedDate.getTime());
    }

    public void ensureDateFormatPresent() {
        if (simpleDateFormat == null) {
            // install ISO date formatter
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        }
    }

    @Override
    public boolean validate() {
        if (!super.validate()) {
            return false;
        }

        if (dateFormatError) {
            errors.add(getText("elements.error.field.date.format"));
            return false;
        }

        return true;
    }

    public void readFromObject(Object obj) {
        super.readFromObject(obj);
        try {
            if (obj == null) {
                dateValue = null;
            } else {
                dateValue = (Date)accessor.get(obj);
            }
            if (dateValue == null) {
                stringValue = null;
            } else {
                ensureDateFormatPresent();
                stringValue = simpleDateFormat.format(dateValue);
            }
        } catch (IllegalAccessException e) {
            throw new Error(e);
        }
    }

    public void writeToObject(Object obj) {
        try {
            accessor.set(obj, dateValue);
        } catch (IllegalAccessException e) {
            throw new Error(e);
        }
    }

    //--------------------------------------------------------------------------
    // Other methods
    //--------------------------------------------------------------------------
    public void valueToXhtml(XhtmlBuffer xb) {
        switch (mode) {
            case EDIT:
                xb.openElement("input");
                xb.addAttribute("type", "text");
                xb.addAttribute("class", "text");
                xb.addAttribute("id", id);
                xb.addAttribute("name", inputName);
                if (stringValue != null) {
                    xb.addAttribute("value", stringValue);
                }
                if (maxLength != null) {
                    xb.addAttribute("maxlength", Integer.toString(maxLength));
                    xb.addAttribute("size", Integer.toString(maxLength));
                }
                String errorMsg =
                        getText("elements.error.field.date.format");

                ensureDateFormatPresent();
                String localizedPattern = simpleDateFormat.toLocalizedPattern();

                String onBlurScript = "ctrTypeDate(this, '" +
                        localizedPattern +
                        "','" + StringEscapeUtils.escapeJavaScript(errorMsg) + "');";
                xb.addAttribute("onblur", onBlurScript);
                xb.addAttribute("onfocus", "backgroundDelete(this);");
                xb.closeElement("input");

                xb.write(" (");
                xb.write(localizedPattern);
                xb.write(") ");

                HttpServletRequest req =
                        ElementsThreadLocals.getHttpServletRequest();
                String calImgLink =
                        Util.getAbsoluteLink(req, "/jscalendar/img.gif");
                xb.writeImage(calImgLink, "calendar", "calendar",
                        "cal" + id, "calendar");

                String dateFormat = localizedPattern.toUpperCase();
                dateFormat = dateFormat.replaceAll("DD", "%d");
                dateFormat = dateFormat.replaceAll("YYYY", "%Y");
                dateFormat = dateFormat.replaceAll("MM", "%m");

                xb.openElement("script");
                xb.addAttribute("type", "text/javascript");
                xb.writeNoHtmlEscape("Calendar.setup({ "
                        + "inputField     :    \"" + id + "\","
                        + "ifFormat       :    \"" + dateFormat + "\","
                        + "button         :    \"cal" + id + "\""
                        + "});");
                xb.closeElement("script");
                break;
            case PREVIEW:
            case VIEW:
            case HIDDEN:
                super.valueToXhtml(xb);
        }
    }

    //--------------------------------------------------------------------------
    // Getters/getters
    //--------------------------------------------------------------------------

    public Date getDateValue() {
        return dateValue;
    }

    public void setDateValue(Date dateValue) {
        this.dateValue = dateValue;
    }

    public SimpleDateFormat getSimpleDateFormat() {
        return simpleDateFormat;
    }

    public void setSimpleDateFormat(SimpleDateFormat simpleDateFormat) {
        this.simpleDateFormat = simpleDateFormat;
    }
}