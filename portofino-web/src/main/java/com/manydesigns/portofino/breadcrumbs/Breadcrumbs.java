/*
 * Copyright (C) 2005-2012 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * Unless you have purchased a commercial license agreement from ManyDesigns srl,
 * the following license terms apply:
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package com.manydesigns.portofino.breadcrumbs;

import com.manydesigns.portofino.dispatcher.Dispatch;
import com.manydesigns.portofino.dispatcher.PageInstance;
import com.manydesigns.portofino.pages.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo          - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla       - alessio.stalla@manydesigns.com
 */
public class Breadcrumbs {
    public static final String copyright =
            "Copyright (c) 2005-2012, ManyDesigns srl";

    protected final Dispatch dispatch;
    protected final List<BreadcrumbItem> items;

    public Breadcrumbs(Dispatch dispatch) {
        this(dispatch, dispatch.getPageInstancePath().length);
    }

    public Breadcrumbs(Dispatch dispatch, int upto) {
        this.dispatch = dispatch;
        items = new ArrayList<BreadcrumbItem>();

        StringBuilder sb = new StringBuilder();
        int start = dispatch.getClosestSubtreeRootIndex();
        sb.append(dispatch.getContextPath());
        PageInstance pageInstance = dispatch.getPageInstance(start);
        PageInstance parentPageInstance = pageInstance.getParent();
        if(parentPageInstance == null) {
            start++;
        } else {
            sb.append(parentPageInstance.getPath());
        }
        for (int i = start; i < upto; i++) {
            PageInstance current = dispatch.getPageInstancePath()[i];
            //Resolve references to treat Crud pages differently below
            //TODO current = current.dereference();
            sb.append("/");
            Page page = current.getPage();
            sb.append(current.getName());
            BreadcrumbItem item = new BreadcrumbItem(
                    sb.toString(), page.getTitle(),
                    page.getDescription());
            items.add(item);
            if(!current.getParameters().isEmpty()) {
                for(String param : current.getParameters()) {
                    sb.append("/").append(param);
                }
                String description = current.getDescription();
                String title = String.format("%s (%s)", description, page.getTitle());
                BreadcrumbItem item2 = new BreadcrumbItem(sb.toString(), description, title);
                items.add(item2);
            }
        }
    }

    public List<BreadcrumbItem> getItems() {
        return items;
    }

}
