package ru.fuzzysearch;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
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
        
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);

    }
	
	private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Line Chart Demo 6",      // chart title
            "Tamanho do Dicionário",                      // x axis label
            "Tempo (ms)",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

//        chart.setBackgroundPaint(Color.white);
//
//        final XYPlot plot = chart.getXYPlot();
//        plot.setBackgroundPaint(Color.lightGray);
//        plot.setDomainGridlinePaint(Color.white);
//        plot.setRangeGridlinePaint(Color.white);
//        
//        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//        renderer.setSeriesLinesVisible(0, false);
//        renderer.setSeriesShapesVisible(1, false);
//        plot.setRenderer(renderer);
//
//        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
                
        return chart;
        
    }
}
