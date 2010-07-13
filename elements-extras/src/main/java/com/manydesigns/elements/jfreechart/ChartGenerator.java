/*
 * Copyright (C) 2005-2009 ManyDesigns srl.  All rights reserved.
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

package com.manydesigns.elements.jfreechart;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.xy.XYSeriesCollection;
import com.manydesigns.elements.ElementsThreadLocals;

import java.io.File;
import java.io.IOException;

/*
* @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
* @author Angelo Lupo          - angelo.lupo@manydesigns.com
* @author Giampiero Granatella - giampiero.granatella@manydesigns.com
*/
public class ChartGenerator {
    public static final String copyright =
            "Copyright (c) 2005-2009, ManyDesigns srl";


    public static String generateChart(int chartType, String title,
                                       String axisLabel, String valueLabel, Dataset dataset, ChartRenderingInfo info,
                                       int width, int height) {
        JFreeChart chart;

        switch (chartType) {
            case ChartUtil.PIE_CHART:
                chart = ChartFactory.createPieChart(title, (PieDataset) dataset,
                        true, true, true);

                break;
            case ChartUtil.PIE_CHART_3D:
                chart = ChartFactory.createPieChart3D(title, (PieDataset) dataset,
                        true, true, true);

                break;
            case ChartUtil.VERTICAL_BAR_CHART:
                chart = ChartFactory.createBarChart(title, axisLabel, valueLabel,
                        (CategoryDataset) dataset,
                        PlotOrientation.VERTICAL, true, true, true);
                break;
            case ChartUtil.VERTICAL_BAR_CHART_3D:
                chart = ChartFactory.createBarChart3D(title, axisLabel, valueLabel,
                        (CategoryDataset) dataset,
                        PlotOrientation.VERTICAL, true, true, true);
                break;
            case ChartUtil.HORIZONTAL_BAR_CHART:
                chart = ChartFactory.createBarChart(title, axisLabel, valueLabel,
                        (CategoryDataset) dataset,
                        PlotOrientation.HORIZONTAL, true, true, true);
                break;
            case ChartUtil.HORIZONTAL_BAR_CHART_3D:
                chart = ChartFactory.createBarChart3D(title, axisLabel, valueLabel,
                        (CategoryDataset) dataset,
                        PlotOrientation.HORIZONTAL, true, true, true);
                break;
            case ChartUtil.XY_CHART:
                chart = ChartFactory.createXYLineChart(title, axisLabel, valueLabel,
                        (XYSeriesCollection) dataset,
                        PlotOrientation.HORIZONTAL, true, true, true);
                break;
            case ChartUtil.RING_CHART:
                chart = ChartFactory.createRingChart(title, (PieDataset) dataset,
                        true, true, true);
                break;
            case ChartUtil.GANTT_CHART:
                    chart = ChartFactory.createGanttChart(title, axisLabel, valueLabel,
                        (IntervalCategoryDataset) dataset,
                         true, true, true);
                    break;
            default:
                throw new InternalError(ElementsThreadLocals.getText(
                        "Portlet_report_type_not_recognized"));
        }
        File tempFile = null;
        try {
            tempFile = File.createTempFile("MDChart", ".png",
                    new File(System.getProperty("java.io.tmpdir")));
            ChartUtilities.saveChartAsPNG(tempFile, chart, width, height, info);
        } catch (IOException e) {
            throw new InternalError("Cannot store images");
        }

        return tempFile.getName();
    }
}