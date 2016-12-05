package ru.fuzzysearch;

import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class LineChart extends ApplicationFrame {

	private static final long serialVersionUID = -6004935850777793060L;
	
	public LineChart(String title, List<XYSeries> seriesList) {
        super(title);
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        
        for(XYSeries series : seriesList) {
            dataset.addSeries(series);
        }
        
        final JFreeChart chart = createChart(title, dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }
	
	private JFreeChart createChart(String title, XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
        	title,      // chart title
            "Tamanho do Dicionário",                      // x axis label
            "Tempo (ms)",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        return chart;
        
    }
}
