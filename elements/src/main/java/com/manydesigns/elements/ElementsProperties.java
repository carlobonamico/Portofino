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

package com.manydesigns.elements;

import com.manydesigns.elements.logging.LogUtil;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
*/
public final class ElementsProperties {
    public static final String copyright =
            "Copyright (c) 2005-2010, ManyDesigns srl";

    //**************************************************************************
    // Default and custom properties location
    //**************************************************************************

    public final static String PROPERIES_RESOURCE =
            "elements.properties";
    public final static String CUSTOM_PROPERIES_RESOURCE =
            "elements-custom.properties";


    //**************************************************************************
    // Property names
    //**************************************************************************

    public static final String FIELD_MANAGER_PROPERTY =
            "elements.field.helpers.manager";
    public static final String FIELD_HELPERS_LIST_PROPERTY =
            "elements.field.helpers.list";

    public static final String CLASS_ACCESSOR_MANAGER_PROPERTY =
            "elements.class.accessor.manager";
    public static final String CLASS_ACCESSOR_HELPERS_LIST_PROPERTY =
            "elements.class.accessor.helpers.list";


    //**************************************************************************
    // Static fields, singleton initialization and retrieval
    //**************************************************************************

    private static final Properties properties;

    public static final Logger logger =
            LogUtil.getLogger(ElementsProperties.class);

    static {
        properties = new Properties();

        loadProperties(PROPERIES_RESOURCE);
        loadProperties(CUSTOM_PROPERIES_RESOURCE);
    }

    private static void loadProperties(String resource) {
        ClassLoader cl = ElementsProperties.class.getClassLoader();
        InputStream stream = cl.getResourceAsStream(resource);
        if (stream == null) {
            LogUtil.infoMF(logger, "Properties resource not found: {0}",
                    resource);
            return;
        }
        try {
            properties.load(stream);
            LogUtil.infoMF(logger, "Properties loaded from: {0}",
                    resource);
        } catch (Throwable e) {
            LogUtil.warningMF(logger, "Error loading properties from: {0}", e,
                    resource);
        }
    }

    public static Properties getProperties() {
        return properties;
    }

    //**************************************************************************
    // Dummy constructor
    //**************************************************************************

    private ElementsProperties() {}

}