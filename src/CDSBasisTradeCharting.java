/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Rectangle2D;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CombinedRangeXYPlot;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Quarter;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

//to output graphics to pdf
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.FontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author segun
 */

public class CDSBasisTradeCharting extends JPanel {

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<MODEL
	// COMPONENTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private CDSBasisTradeModel model = null;

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<FOR JFREECHART OUTPUTTING
	// TEST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	TimeSeriesCollection totalSubprimeExposuresSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection capitalSavingsSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection basisTradeIncomeCol = new TimeSeriesCollection();
	TimeSeriesCollection totalCumulativeBasisTradeIncomeSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection notionalAmountOfUnwindsSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection incomeFromBasisTradeUnwindsSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection returnOnCapitalSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection tier1CapitalSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection trancheSavingsSeriesCol = new TimeSeriesCollection();

	TimeSeriesCollection aggregatedExposuresSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection aggregatedNetSavingSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection aggregatedGrossSavingSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection totalAccumulatedTrancheSavingsSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection baselLeverageRatioSeriesCol = new TimeSeriesCollection();
	TimeSeriesCollection aggregatedBasisTradeIncomeCol = new TimeSeriesCollection();
	TimeSeriesCollection aggregatedAccumulatedBasisTradeIncomeCol = new TimeSeriesCollection();
	TimeSeriesCollection aggregatedAccumulatedStrategyIncomeCol = new TimeSeriesCollection();

	CombinedRangeXYPlot jfcSuperPlot = new CombinedRangeXYPlot();
	JFreeChart subprimeExposureChart;
	JFreeChart capitalSavingsChart;
	JFreeChart basisIncomeChart;
	JFreeChart cumulativeBasisIncomeChart;
	JFreeChart notionalUnwindValueChart;
	JFreeChart unwindIncomeChart;
	JFreeChart tier1CapitalChart;
	JFreeChart trancheSavingsChart;
	JFreeChart aggregatedExposuresSeriesChart;
	JFreeChart aggregatedNetSavingSeriesChart;
	JFreeChart aggregatedGrossSavingSeriesChart;
	JFreeChart totalAccumulatedTrancheSavingsSeriesChart;
	JFreeChart baselLeverageRatioSeriesChart;
	JFreeChart aggregatedBasisTradeIncomeChart;
	JFreeChart aggregatedAccumulatedBasisTradeIncomeChart;
	JFreeChart aggregatedAccumulatedStrategyIncomeChart;

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<JAVA
	// COMPONENTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	JTabbedPane tabbedPane;
	JFrame chartViewer;

	private static final long serialVersionUID = 1L;
	private JMenuBar cdsBasisTradeModelGUIMenuBar = null;
	private JPanel jContentPane = null;
	private JMenuItem jMenuItemExit = null;
	private JMenuItem jMenuItemSingleRun = null;
	private JMenuItem jMenuItemComparisonRun = null;
	private JMenu jMenuFile = null;
	private JMenu jMenuHelp = null;
	private JMenuItem jMenuItemAbout = null;
	private JToolBar jToolBar = null;
	private JButton jButton_loadData = null;
	private JButton jButton_run = null;
	private JButton jButton_pause = null;
	private JButton jButton_step = null;
	private JButton jButton_stop = null;
	private JComboBox jComboBox_banks = null;
	private JComboBox jComboBox_exp = null;
	private JComboBox jComboBox_net = null;

	private JLabel jLabel_tick = null;
	private JLabel jLabel_tickValue = null;
	private int counter = 0;
	private JLabel jLabel9 = null;
	private JLabel jLabel_numDB = null;
	private JTabbedPane jTabbedPane_left = null;
	private JPanel jPanel_parameters = null;
	private JPanel jPanel_ModelOptions = null;
	private int contagionVisualizersizeX = 200;
	private int contagionVisualizersizeY = 250;
	private JLabel jLabel910 = null;
	private JLabel jLabel_simRound = null;
	private JPanel jPanel_topLeft = null;
	private JTabbedPane jTabbedPane_topLeft = null;
	private JTabbedPane jTabbedPane_parameters = null;
	private JPanel jPanel_topRight = null;
	private JTabbedPane jTabbedPane_topRight = null;

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CLASS
	// CONSTRUCTORS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public CDSBasisTradeCharting(CDSBasisTradeModel model) {

		this.model = model;

		initialize();

	}// End of CDSBasisTradeModelTest Class Constructor.

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY
	// METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private void initialize() {

		this.setSize(784, 658);
		this.setSize(new java.awt.Dimension(784, 658));
		setBorder(new EtchedBorder());
	}

	private void updateCharts() {
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Organise the
		// Generated Data>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		/**
		 * this section of code collects the data from the individual bank
		 * agents and collates them into various series collection objects which
		 * will be printed using JFreeChart
		 */
//		setBankLevelSeries();

		setAggregateSeries();
	}// end of updateCharts() method

//	private void setBankLevelSeries() {
//		// collate the various individual bank data JFreeChart time series into
//		// Time Series Collection varriables
//
//		for (int i = 0; i < model.getBankList().size(); i++) {
//			totalSubprimeExposuresSeriesCol.addSeries(model.getBankList()
//					.get(i).getTotalSubprimeExposuresSeries());
//			capitalSavingsSeriesCol.addSeries(model.getBankList().get(i)
//					.getCapitalSavingsSeries());
//			basisTradeIncomeCol.addSeries(model.getBankList().get(i)
//					.getBasisTradeIncomeSeries());
//			totalCumulativeBasisTradeIncomeSeriesCol.addSeries(model
//					.getBankList().get(i)
//					.getTotalCumulativeBasisTradeIncomeSeries());
//			notionalAmountOfUnwindsSeriesCol.addSeries(model.getBankList()
//					.get(i).getNotionalAmountOfUnwindsSeries());
//			incomeFromBasisTradeUnwindsSeriesCol.addSeries(model.getBankList()
//					.get(i).getIncomeFromBasisTradeUnwindsSeries());
//			returnOnCapitalSeriesCol.addSeries(model.getBankList().get(i)
//					.getReturnOnCapitalSeries());
//			tier1CapitalSeriesCol.addSeries(model.getBankList().get(i)
//					.getTier1CapitalSeries());
//			trancheSavingsSeriesCol.addSeries(model.getBankList().get(i)
//					.getTrancheSavingsSeries());
//		}
//
//	}

	private void setAggregateSeries() {
		// collate the various aggregated JFreeChart time series into Time
		// Series Collection varriables
//		aggregatedExposuresSeriesCol.addSeries(model.aggregatedExposuresSeries);
//		aggregatedExposuresSeriesCol
//				.addSeries(model.aggregatedActualExposuresSeries);
//		aggregatedNetSavingSeriesCol.addSeries(model.aggregatedNetSavingSeries);
//		aggregatedGrossSavingSeriesCol
//				.addSeries(model.aggregatedGrossSavingSeries);
//		totalAccumulatedTrancheSavingsSeriesCol
//				.addSeries(model.totalAccumulatedTrancheSavingsSeries);
//		baselLeverageRatioSeriesCol.addSeries(model.baselLeverageRatioSeries);
//		aggregatedBasisTradeIncomeCol
//				.addSeries(model.aggregatedBasisTradeIncomeSeries);
//		aggregatedAccumulatedBasisTradeIncomeCol
//				.addSeries(model.aggregatedAccumulatedBasisTradeIncomeSeries);
//		aggregatedAccumulatedStrategyIncomeCol
//				.addSeries(model.aggregatedAccumulatedStrategyIncomeSeries);

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<JFREE CHART CHARTING
	// METHODS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private JTabbedPane buildCharts() {

		if (this.tabbedPane == null) {
			this.tabbedPane = new JTabbedPane();
			subprimeExposureChart = createChart(
					totalSubprimeExposuresSeriesCol,
					"Sub-prime Exposures Held On Balance Sheet");
			ChartPanel subprimeExposureChartPanel = new ChartPanel(
					subprimeExposureChart);
			this.tabbedPane.addTab("Sub-prime Exposures Held On Balance Sheet",
					subprimeExposureChartPanel);

			capitalSavingsChart = createChart(capitalSavingsSeriesCol,
					"Net Capital Saving");
			ChartPanel capitalSavingsChartPanel = new ChartPanel(
					capitalSavingsChart);
			tabbedPane.addTab("Net Capital Saving", capitalSavingsChartPanel);

			trancheSavingsChart = createChart(trancheSavingsSeriesCol,
					"Gross Capital Saving");
			ChartPanel trancheSavingsChartPanel = new ChartPanel(
					trancheSavingsChart);
			tabbedPane.addTab("Gross Capital Saving", trancheSavingsChartPanel);

			basisIncomeChart = createChart(basisTradeIncomeCol,
					"Basis Trade Income Over Past Quarter");
			ChartPanel basisIncomeChartPanel = new ChartPanel(basisIncomeChart);
			tabbedPane.addTab("Basis Trade Income Over Past Quarter",
					basisIncomeChartPanel);

			cumulativeBasisIncomeChart = createChart(
					totalCumulativeBasisTradeIncomeSeriesCol,
					"Cumlulative Basis Trade Income");
			ChartPanel cumulativeBasisIncomeChartPanel = new ChartPanel(
					cumulativeBasisIncomeChart);
			tabbedPane.addTab("Cumlulative Basis Trade Income",
					cumulativeBasisIncomeChartPanel);

			notionalUnwindValueChart = createChart(
					notionalAmountOfUnwindsSeriesCol,
					"Notional Value of Unwinds");
			ChartPanel notionalUnwindValueChartPanel = new ChartPanel(
					notionalUnwindValueChart);
			tabbedPane.addTab("Notional Value of Unwinds",
					notionalUnwindValueChartPanel);

			unwindIncomeChart = createChart(
					incomeFromBasisTradeUnwindsSeriesCol,
					"Income From Basis Trade Unwinds");
			ChartPanel unwindIncomeChartPanel = new ChartPanel(
					unwindIncomeChart);
			tabbedPane.addTab("Income From Basis Trade Unwinds",
					unwindIncomeChartPanel);

			tier1CapitalChart = createChart(tier1CapitalSeriesCol,
					"Teir 1 Capital");
			ChartPanel tier1CapitalChartPanel = new ChartPanel(
					tier1CapitalChart);
			tabbedPane.addTab("Teir 1 Capital", tier1CapitalChartPanel);

			aggregatedNetSavingSeriesChart = createChart(
					aggregatedNetSavingSeriesCol,
					"Aggregated Net Capital Savings");
			ChartPanel aggregatedNetSavingSeriesChartPanel = new ChartPanel(
					aggregatedNetSavingSeriesChart);
			tabbedPane.addTab("Aggregated Net Capital Savings",
					aggregatedNetSavingSeriesChartPanel);

			aggregatedGrossSavingSeriesChart = createChart(
					aggregatedGrossSavingSeriesCol,
					"Aggregated Gross Capital Savings");
			ChartPanel aggregatedGrossSavingSeriesChartPanel = new ChartPanel(
					aggregatedGrossSavingSeriesChart);
			tabbedPane.addTab("Aggregated Gross Capital Savings",
					aggregatedGrossSavingSeriesChartPanel);

			totalAccumulatedTrancheSavingsSeriesChart = createChart(
					totalAccumulatedTrancheSavingsSeriesCol,
					"Aggregated Accumulated Capital Savings");
			ChartPanel totalAccumulatedTrancheSavingsSeriesChartPanel = new ChartPanel(
					totalAccumulatedTrancheSavingsSeriesChart);
			tabbedPane.addTab("Aggregated Accumulated Capital Savings",
					totalAccumulatedTrancheSavingsSeriesChartPanel);

			aggregatedBasisTradeIncomeChart = createChart(
					aggregatedBasisTradeIncomeCol, "Aggregated Basis Income");
			ChartPanel aggregatedBasisTradeIncomeChartPanel = new ChartPanel(
					aggregatedBasisTradeIncomeChart);
			tabbedPane.addTab("Aggregated Basis Income",
					aggregatedBasisTradeIncomeChartPanel);

			aggregatedAccumulatedBasisTradeIncomeChart = createChart(
					aggregatedAccumulatedBasisTradeIncomeCol,
					"Aggregated Accumulated Basis Income");
			ChartPanel aggregatedAccumulatedBasisTradeIncomeChartPanel = new ChartPanel(
					aggregatedAccumulatedBasisTradeIncomeChart);
			tabbedPane.addTab("Aggregated Accumulated Basis Income",
					aggregatedAccumulatedBasisTradeIncomeChartPanel);

			baselLeverageRatioSeriesChart = createChartPercent(
					baselLeverageRatioSeriesCol,
					"Basel Leverage Ratio Across Sector");
			ChartPanel baselLeverageRatioSeriesChartPanel = new ChartPanel(
					baselLeverageRatioSeriesChart);
			tabbedPane.addTab("Basel Leverage Ratio Across Sector",
					baselLeverageRatioSeriesChartPanel);

			aggregatedAccumulatedStrategyIncomeChart = createChart(
					aggregatedAccumulatedStrategyIncomeCol,
					"Aggregated Accumulated Income " + "from Trading Strategy");
			ChartPanel aggregatedAccumulatedStrategyIncomeChartPanel = new ChartPanel(
					aggregatedAccumulatedStrategyIncomeChart);
			tabbedPane.addTab("Aggregated Accumulated Income",
					aggregatedAccumulatedStrategyIncomeChartPanel);

			aggregatedExposuresSeriesChart = createChart(
					aggregatedExposuresSeriesCol,
					"Aggregated Sub-prime Exposures Held On Balance Sheet");
			ChartPanel aggregatedExposuresSeriesChartPanel = new ChartPanel(
					aggregatedExposuresSeriesChart);
			tabbedPane.addTab(
					"Aggregated Sub-prime Exposures Held On Balance Sheet",
					aggregatedExposuresSeriesChartPanel);

		}

		return this.tabbedPane;

	}

	private void printCharts() throws FileNotFoundException {
		File subprimeExp = new File(System.getProperty("user.home")
				+ "/totalSubprimeExposures.pdf");
		saveChartAsPDF(subprimeExp, subprimeExposureChart, 1028, 720,
				new DefaultFontMapper());

		File capitalSavingsCh = new File(System.getProperty("user.home")
				+ "/NetcapitalSavingsChart.pdf");
		saveChartAsPDF(capitalSavingsCh, capitalSavingsChart, 1028, 720,
				new DefaultFontMapper());

		File trancheSavingsCh = new File(System.getProperty("user.home")
				+ "/GrosscapitalSavingsChart.pdf");
		saveChartAsPDF(trancheSavingsCh, trancheSavingsChart, 1028, 720,
				new DefaultFontMapper());

		File basisIncome = new File(System.getProperty("user.home")
				+ "/basisIncomeChart.pdf");
		saveChartAsPDF(basisIncome, basisIncomeChart, 1028, 720,
				new DefaultFontMapper());

		File cumulativeBasisIncome = new File(System.getProperty("user.home")
				+ "/cumulativeBasisIncomeChart.pdf");
		saveChartAsPDF(cumulativeBasisIncome, cumulativeBasisIncomeChart, 1028,
				720, new DefaultFontMapper());

		File notionalUnwindValue = new File(System.getProperty("user.home")
				+ "/notionalUnwindValue.pdf");
		saveChartAsPDF(notionalUnwindValue, notionalUnwindValueChart, 1028,
				720, new DefaultFontMapper());

		File unwindIncome = new File(System.getProperty("user.home")
				+ "/unwindIncomeChart.pdf");
		saveChartAsPDF(unwindIncome, unwindIncomeChart, 1028, 720,
				new DefaultFontMapper());

		File tier1Capital = new File(System.getProperty("user.home")
				+ "/tier1Capital.pdf");
		saveChartAsPDF(tier1Capital, tier1CapitalChart, 1028, 720,
				new DefaultFontMapper());

		File aggregatedNetSaving = new File(System.getProperty("user.home")
				+ "/aggregatedNetSavingSeries.pdf");
		saveChartAsPDF(aggregatedNetSaving, aggregatedNetSavingSeriesChart,
				1028, 720, new DefaultFontMapper());

		File aggregatedGrossSaving = new File(System.getProperty("user.home")
				+ "/aggregatedGrossSavingSeries.pdf");
		saveChartAsPDF(aggregatedGrossSaving, aggregatedGrossSavingSeriesChart,
				1028, 720, new DefaultFontMapper());

		File totalAccumulatedTrancheSavings = new File(
				System.getProperty("user.home")
						+ "/aggregatedAccumulatedSavingSeries.pdf");
		saveChartAsPDF(totalAccumulatedTrancheSavings,
				totalAccumulatedTrancheSavingsSeriesChart, 1028, 720,
				new DefaultFontMapper());

		File aggregatedBasisTradeIncome = new File(
				System.getProperty("user.home")
						+ "/aggregatedBasisTradeIncome.pdf");
		saveChartAsPDF(aggregatedBasisTradeIncome,
				aggregatedBasisTradeIncomeChart, 1028, 720,
				new DefaultFontMapper());

		File aggregatedAccumulatedBasisIncome = new File(
				System.getProperty("user.home")
						+ "/aggregatedAccumulatedBasisIncome.pdf");
		saveChartAsPDF(aggregatedAccumulatedBasisIncome,
				aggregatedAccumulatedBasisTradeIncomeChart, 1028, 720,
				new DefaultFontMapper());

		File baselLeverageRatio = new File(System.getProperty("user.home")
				+ "/baselLeverageRatioSeries.pdf");
		saveChartAsPDF(baselLeverageRatio, baselLeverageRatioSeriesChart, 1028,
				720, new DefaultFontMapper());

		File aggregatedAccumulatedStrategyIncome = new File(
				System.getProperty("user.home")
						+ "/aggregatedAccumulatedStrategyIncome.pdf");
		saveChartAsPDF(aggregatedAccumulatedStrategyIncome,
				aggregatedAccumulatedStrategyIncomeChart, 1028, 720,
				new DefaultFontMapper());

		File aggregatedExposures = new File(System.getProperty("user.home")
				+ "/aggregatedExposuresSeries.pdf");
		saveChartAsPDF(aggregatedExposures, aggregatedExposuresSeriesChart,
				1028, 720, new DefaultFontMapper());

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY
	// METHODS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY METHODS USED TO CREATE PDF
	// PRINTOUTS OF THE CHARTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// this method saves the charts as pdf files
	private void saveChartAsPDF(File file, JFreeChart chart, int width,
			int height, DefaultFontMapper mapper) throws FileNotFoundException {

		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		writeChartAsPDF(out, chart, width, height, mapper);

	}

	// this method writes the charts in the saved pdf files
	private void writeChartAsPDF(OutputStream out, JFreeChart chart, int width,
			int height, DefaultFontMapper mapper) {
		// TODO Auto-generated method stub
		Rectangle pageSize = new Rectangle(width, height);
		Document document = new Document(pageSize, 50, 50, 50, 50);
		try {
			PdfWriter writer = PdfWriter.getInstance(document, out);
			document.addAuthor("Oluwasegun O. Bewaji: PhyperRutridge");
			document.addSubject("CDS Basis Trading Model");
			document.open();
			PdfContentByte cb = writer.getDirectContent();
			PdfTemplate tp = cb.createTemplate(width, height);
			Graphics2D g2D = tp.createGraphics(width, height, mapper);
			Rectangle2D r2D = new Rectangle2D.Double(0, 0, width, height);
			chart.draw(g2D, r2D);
			g2D.dispose();
			cb.addTemplate(tp, 0, 0);

		} catch (DocumentException de) {
			System.err.println(de.getMessage());
		}
		document.close();
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY METHODS USED TO
	// CREATE THE CHARTS IN
	// JFREECHART.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// This method is called to create charts with data units in $ thousands
	private JFreeChart createChart(
			TimeSeriesCollection totalSubprimeExposuresSeriesCol, String title) {
		// TODO Auto-generated method stub
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Time",
				"$ thousands", totalSubprimeExposuresSeriesCol, true, true,
				false);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(784, 658));
		chartPanel.setMouseZoomable(true, false);
		// setContentPane(chartPanel);

		return chart;

	}

	// This method is called to create charts with data units in % percentage
	// points
	private JFreeChart createChartPercent(
			TimeSeriesCollection totalSubprimeExposuresSeriesCol, String title) {
		// TODO Auto-generated method stub
		JFreeChart chart = ChartFactory.createTimeSeriesChart(title, "Time",
				"Percentage Points %", totalSubprimeExposuresSeriesCol, true,
				true, false);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(784, 658));
		chartPanel.setMouseZoomable(true, false);
		// setContentPane(chartPanel);

		return chart;

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<GETTERS AND
	// SETTERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.setPreferredSize(new java.awt.Dimension(1028, 720));
			jContentPane.add(getJToolBar(), null);
			jContentPane.add(getJPanel_topLeft(), null);
			jContentPane.add(getJPanel_topRight(), null);
		}
		return jContentPane;
	}// end of getJContentPane

	/**
	 * This method initializes jJMenuBar
	 * 
	 * @return javax.swing.JMenuBar
	 */
	private JMenuBar getCDSBasisTradeModelGUIMenuBar() {
		if (cdsBasisTradeModelGUIMenuBar == null) {
			cdsBasisTradeModelGUIMenuBar = new JMenuBar();
			cdsBasisTradeModelGUIMenuBar.add(getJMenuFile());
			cdsBasisTradeModelGUIMenuBar.add(getJMenuHelp());
		}
		return cdsBasisTradeModelGUIMenuBar;
	}// end of getCDSBasisTradeModelGUIMenuBar

	/**
	 * This method initializes jMenuItemExit
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setMnemonic(KeyEvent.VK_X);
			jMenuItemExit.setText("Exit");
		}
		return jMenuItemExit;
	}// end of getJMenuItemExit

	/**
	 * This method initializes jMenuFile
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuFile() {
		if (jMenuFile == null) {
			jMenuFile = new JMenu();
			jMenuFile.setMnemonic(KeyEvent.VK_F);
			jMenuFile.setText("File");
			// jMenuFile.add(getJMenuItemSingleRun());
			// jMenuFile.add(getJMenuItemComparisonRun());
			jMenuFile.add(getJMenuItemExit());

		}
		return jMenuFile;
	}// end of getJMenuFile

	/**
	 * This method initializes jMenuHelp
	 * 
	 * @return javax.swing.JMenu
	 */
	private JMenu getJMenuHelp() {
		if (jMenuHelp == null) {
			jMenuHelp = new JMenu();
			jMenuHelp.setText("Help");
			jMenuHelp.setMnemonic(KeyEvent.VK_H);
			jMenuHelp.add(getJMenuItemAbout());
		}
		return jMenuHelp;
	}

	/**
	 * This method initializes jMenuItemAbout
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemAbout() {
		if (jMenuItemAbout == null) {
			jMenuItemAbout = new JMenuItem();
			jMenuItemAbout.setText("About");
			jMenuItemAbout.setMnemonic(KeyEvent.VK_A);

		}
		return jMenuItemAbout;
	}

	private JMenuItem getJMenuItemComparisonRun() {
		// TODO Auto-generated method stub
		if (jMenuItemComparisonRun == null) {
			jMenuItemComparisonRun = new JMenuItem();
			jMenuItemComparisonRun.setText("Comparison Run");
			jMenuItemComparisonRun.setMnemonic(KeyEvent.VK_A);

		}
		return jMenuItemComparisonRun;
	}

	/**
	 * This method initializes jMenuItemSingleRun
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemSingleRun() {
		// TODO Auto-generated method stub
		if (jMenuItemSingleRun == null) {
			jMenuItemSingleRun = new JMenuItem();
			jMenuItemSingleRun.setText("Single Run");
			jMenuItemSingleRun.setMnemonic(KeyEvent.VK_A);
			jMenuItemSingleRun
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// System.out.println("actionPerformed()"); // TODO
							// Auto-generated Event stub actionPerformed()
							loadSignleRunPanel_click();

						}
					});

		}
		return jMenuItemSingleRun;
	}

	/**
	 * This method initializes jToolBar
	 * 
	 * @return javax.swing.JToolBar
	 */
	private JToolBar getJToolBar() {
		if (jToolBar == null) {
			jLabel_simRound = new JLabel();
			jLabel_simRound.setText("0");
			jLabel_simRound.setForeground(new Color(0, 150, 0));
			jLabel_simRound.setFont(new Font("Dialog", Font.BOLD, 14));
			jLabel910 = new JLabel();
			jLabel910.setText("    Simulation round:  ");
			jLabel910.setForeground(new Color(0, 150, 0));
			jLabel910.setFont(new Font("Dialog", Font.BOLD, 14));
			jToolBar = new JToolBar();
			jToolBar.setBounds(new java.awt.Rectangle(0, 0, 1007, 36));
			jToolBar.add(getJButton_loadData());
			jToolBar.add(getJButton_run());
			jToolBar.add(getJButton_pause());
			jToolBar.add(getJButton_step());
			jToolBar.add(getJButton_stop());
			jToolBar.add(jLabel910);
			jToolBar.add(jLabel_simRound);

		}
		return jToolBar;
	}

	/**
	 * This method initializes jButton_loadData
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_loadData() {
		if (jButton_loadData == null) {
			jButton_loadData = new JButton();
			jButton_loadData.setText("Load Data");
			jButton_loadData.setIcon(new ImageIcon(getClass().getResource(
					"/images/load.png")));
			jButton_loadData
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// System.out.println("actionPerformed()"); // TODO
							// Auto-generated Event stub actionPerformed()
							loadData_click();

						}
					});
		}
		return jButton_loadData;
	}

	/**
	 * This method initializes jButton_run clicking on this button will run the
	 * specified CDS Basis Trade model simulation
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_run() {
		if (jButton_run == null) {
			jButton_run = new JButton();
			jButton_run.setText("Run");
			jButton_run.setIcon(new ImageIcon(getClass().getResource(
					"/images/start.png")));
			jButton_run.setEnabled(false);
			jButton_run.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// System.out.println("actionPerformed()"); // TODO
					// Auto-generated Event stub actionPerformed()
					// eng.start();
					getJButton_run().setVisible(false);
				}
			});
		}
		return jButton_run;
	}

	/**
	 * This method initializes jButton_pause
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_pause() {
		if (jButton_pause == null) {
			jButton_pause = new JButton();
			jButton_pause.setText("Pause");
			jButton_pause.setIcon(new ImageIcon(getClass().getResource(
					"/images/pause.png")));
			jButton_pause.setEnabled(false);
			jButton_pause
					.addActionListener(new java.awt.event.ActionListener() {
						public void actionPerformed(java.awt.event.ActionEvent e) {
							// System.out.println("actionPerformed()"); // TODO
							// Auto-generated Event stub actionPerformed()
							// eng.stop();
						}
					});
		}
		return jButton_pause;
	}

	/**
	 * This method initializes jButton_step
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_step() {
		if (jButton_step == null) {
			jButton_step = new JButton();
			jButton_step.setText("Step");
			jButton_step.setIcon(new ImageIcon(getClass().getResource(
					"/images/step.png")));
			jButton_step.setEnabled(false);
			jButton_step.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// System.out.println("actionPerformed()"); // TODO
					// Auto-generated Event stub actionPerformed()
					// eng.stop();
					// eng.stepTime();
				}
			});
		}
		return jButton_step;
	}

	/**
	 * This method initializes jButton_stop
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getJButton_stop() {
		if (jButton_stop == null) {
			jButton_stop = new JButton();
			jButton_stop.setText("Stop");
			jButton_stop.setIcon(new ImageIcon(getClass().getResource(
					"/images/stop.png")));
			jButton_stop.setEnabled(false);
			jButton_stop.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// System.out.println("actionPerformed()"); // TODO
					// Auto-generated Event stub actionPerformed()
					// eng.stop();

					stop_click();
				}
			});
		}
		return jButton_stop;
	}

	private void loadData_click() {
		// this.eng.buildModels();

		this.getJButton_loadData().setEnabled(false);
		this.getJButton_run().setEnabled(true);

	}

	private void loadSignleRunPanel_click() {
		// TODO Auto-generated method stub

		if (jPanel_ModelOptions == null) {
			jPanel_ModelOptions = new JPanel();
			jPanel_ModelOptions.setBorder(new TitledBorder("Model Options"));
			jPanel_ModelOptions.setLayout(new GridLayout(4, 1));
			jTabbedPane_parameters.add(jPanel_ModelOptions);
		}
	}

	private void loadComparisonRunPanel_click() {
		// TODO Auto-generated method stub

		if (jPanel_ModelOptions == null) {
			jPanel_ModelOptions = new JPanel();
			jPanel_ModelOptions.setBorder(new TitledBorder("Model Options"));
			jPanel_ModelOptions.setLayout(new GridLayout(3, 1));
			jTabbedPane_parameters.add(jPanel_ModelOptions);
		}
	}

	private void stop_click() {
		// this.eng = new SimEngine ();
		//
		// this.model = new Model (this);

		// eng.rebuildModels();

	}

	public void update() {
		// this.jLabel_simRound.setText(String.valueOf(Sim.getAbsoluteTime() +
		// this.counter));

		this.updateCharts();

	}

	/**
	 * This method initializes jPanel_topLeft
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_topLeft() {
		if (jPanel_topLeft == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.gridx = 0;
			jPanel_topLeft = new JPanel();
			jPanel_topLeft.setLayout(new GridBagLayout());
			jPanel_topLeft.setBounds(new java.awt.Rectangle(15, 46, 194, 662));
			jPanel_topLeft.add(getJTabbedPane_topLeft(), gridBagConstraints1);
		}
		return jPanel_topLeft;
	}

	/**
	 * This method initializes jTabbedPane_topLeft
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane_topLeft() {
		if (jTabbedPane_topLeft == null) {
			jTabbedPane_topLeft = new JTabbedPane();
			jTabbedPane_topLeft.addTab("Model Configuration", null,
					getJTabbedPane_parameters(), null);
		}
		return jTabbedPane_topLeft;
	}

	/**
	 * This method initializes jTabbedPane_parameters
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane_parameters() {
		if (jTabbedPane_parameters == null) {
			jTabbedPane_parameters = new JTabbedPane();
		}
		return jTabbedPane_parameters;
	}

	/**
	 * This method initializes jPanel_topRight
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJPanel_topRight() {
		if (jPanel_topRight == null) {
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.fill = GridBagConstraints.BOTH;
			gridBagConstraints.gridy = 0;
			gridBagConstraints.weightx = 1.0;
			gridBagConstraints.weighty = 1.0;
			gridBagConstraints.gridx = 0;
			jPanel_topRight = new JPanel();
			jPanel_topRight.setLayout(new GridBagLayout());
			jPanel_topRight
					.setBounds(new java.awt.Rectangle(225, 46, 785, 662));
			jPanel_topRight.add(buildCharts(), gridBagConstraints);
			// jPanel_topRight.add(this.tabbedPane);
			// jPanel_topRight.add(getJTabbedPane_topRight(),
			// gridBagConstraints); this.tabbedPane = new JTabbedPane();
		}
		return jPanel_topRight;
	}

	/**
	 * This method initializes jTabbedPane_topRight
	 * 
	 * @return javax.swing.JTabbedPane
	 */
	private JTabbedPane getJTabbedPane_topRight() {
		if (jTabbedPane_topRight == null) {

			// jTabbedPane_topRight = new JTabbedPane();
			// jTabbedPane_topRight.
		}
		return jTabbedPane_topRight;
	}

}
