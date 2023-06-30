import jas.engine.Sim;
import jas.engine.SimEngine;
import jas.plot.ColorMap;
import jas.plot.ColorRangeMap;
import jas.plot.LayerDblGridDrawer;
import jas.plot.LayerObjGridDrawer;
import jas.plot.LayeredSurfaceFrame;
import jas.plot.LayeredSurfacePanel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.QuarterDateFormat;
import org.jfree.chart.plot.CombinedRangeXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Quarter;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.RectangleInsets;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Toolkit;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;
import java.awt.Insets;

import javax.swing.DefaultComboBoxModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.JSlider;

public class CDSBasisTradeJASApplicationFrame extends JFrame {

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	boolean defaultModel; // this defines if the model simulation will use the
	// default data set of 2006Q1 to 2007Q3 or if it
	// will
	// use data from a MySQL database. defaultModel=true if using 2006Q1 to
	// 2007Q3 and false otherwise.
	int coreBanks; // this defines if the model simulation will use all the
	// active banks in the CDS and RMBS market between
	// 2006Q1 to 2007Q3 or if it will use core 26 banks with the largest cds
	// market activity between 2006 and 2009

	boolean dataAllRMBS; // this defines if the model simulation will look at
	// all the
	// MBS on banks' balancesheets or just the structured MBS

	int maximumEarningMethod; // determines how investor chooses to allocate
	// wealth between the various tranches of notes.
	// maximumEarningMethod can be a member of {0,1,2,3}.

	int natureOfEconomicExpectations; // determines how investors view the
										// economic climate i.e are they
										// optimistic, pessimistic, think it
										// will remain consistent or mixed views
										// takes a value of {1,2,3,4}

	boolean crm;// determines if model uses Credit risk mitigation Default value
	// is true
	boolean leveragedTrades;// determines if model uses leveraged trades Default
	// value
	// is false
	boolean baselII;// determines if the risk weights to use come from Basel I
	// or Basel II.
	boolean tradePositionUnwindsPermited;// true if trade position unwinds are
											// to be allowed based on market
											// conditions

	boolean homogeneousAgents;// determines if model treats all banks as being
								// identical in terms of expectations of the
								// economy Default
	// value
	// is true
	double vThreshold; // determines the volatility threshold. Note that the
	// lower this value is the more likey
	// investors are to unwind their trade positions.
	// high values are more akin with creditors and interest groups who want an
	// investment to succeed
	// or have a long term view of the investment.
	double counterpartyRiskWeight; // DEFAULT COUNTER PARTY RISK WEIGHT. WHERE
	// THE CRM OPTION IS CHOSEN THE COUNTERPARTY
	// RISK
	// WEIGHTS OF THE INDIVIDUAL TRANCHES WIL BE USED BY THE CDSBASISTRADE CLASS
	// THIS IS A DEFAULT BY DESIGN FOR RISK WEIGHTS
	// IN FORM OF ARRAYS THE RELEVANT METHOD CALL WILL NEED TO BE OVERLOADED.
	double[] counterpartyRiskW;
	int numOfQuarters;// This is defendant on the number of data points the user
	// has
	int startyear; // required for plotting series into JFreeChart.
	int startQutr; // required for plotting series into JFreeChart.
	int leverageStartyear; // required for kicking off the leveraged borrowing.
	int leverageStartQutr; // required for kicking off the leveraged borrowing.

	double haircut;// THIS IS REQUIRED WHEN LEVERAGE IS CONSIDERED. LEVERAGED
	// TRADES HAVE SO FAR NOT BEEN CODED BUT I MAY ADD THEM
	// LATER ON
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private SimEngine eng;
	public marketEnviromentConfigurator enviroment;
	public marketDataManager dataManager;
	public CDSBasisTradeModel model;
	public CDSBasisTradeCharting resultsCharts;
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<FOR JFREECHART OUTPUTTING
	// TEST>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	TimeSeriesCollection cdsAndBondSpreads = new TimeSeriesCollection();
	TimeSeriesCollection cdsBasis = new TimeSeriesCollection();
	TimeSeriesCollection cdsPrices = new TimeSeriesCollection();
	TimeSeriesCollection cdsTradingDiscount = new TimeSeriesCollection();

	CombinedRangeXYPlot jfcSuperPlot = new CombinedRangeXYPlot();

	JFreeChart cdsAndBondSpreadChart;
	JFreeChart cdsPriceChart;
	JFreeChart cdsbasisChart;
	JFreeChart cdsTradingDiscountChart;

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<JAVA
	// COMPONENTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	JTabbedPane tabbedPane;
	JFrame chartViewer;

	int time = 0;
	private static final long serialVersionUID = 1L;

	private JMenuBar cdsBasisTradeModelGUIMenuBar = null;
	private JPanel jContentPane = null;
	private JPanel jParentPane = null;
	private JMenuItem jMenuItemExit = null;
	private JMenuItem jMenuItemSingleRun = null;
	private JMenuItem jMenuItemComparisonRun = null;
	private JMenu jMenuFile = null;
	private JMenu jMenuHelp = null;
	private JMenuItem jMenuItemAbout = null;
	private final JButton jButton_loadConfiguration = null;
	private JButton jButton_loadModelSettings = null;
	private JButton jButton_run = null;
	private JButton jButton_resetModel = null;
	private JLabel jLabel_simRound;
	private JLabel jLabel910;
	private JPanel jPanel_ModelConfig;
	private JTabbedPane jTabbedPane__Results;
	private JPanel jPanel_Results;

	private JPanel jPanel_ModelSetup;
	private JPanel jPanel_ModelEnviromentConfig;
	private JPanel jPanel_ModelCRMChoice;
	private JPanel jPanel_ModelRegRegimeChoice;
	private JPanel jPanel_ModelChoice;

	JLabel numQuatersLabel;
	JTextField numQuatersTF;

	JLabel haircutLabel;
	JTextField haircutTF;

	JLabel volThresholdLabel;
	JTextField volThresholdTF;

	Box crmBox;
	Box baselBox;
	Box modelBox;
	Box banksChoiceBox;
	ButtonGroup bgCRM;
	ButtonGroup bgBasel;
	ButtonGroup bgModel;
	ButtonGroup bgBanksChoice;
	private Box modelTypeBox;

	private ButtonGroup bgMBSDataChoice;
	private Box MBSDataChoiceBox;

	private ButtonGroup bgModelWithLeverageChoice;
	private JPanel modelWithLeverageChoiceBox;
	private JRadioButton noLeverageButton;
	private JRadioButton leverageButton;

	String[] modelString = { "Default", "Real TIme" };
	String[] permitPositionUnwindsString = { "false", "true" };

	String[] leverageModelString = { "No Leverage", "Leveraged" };

	String[] crmTxt = { "With CRM", "Without CRM" };
	String[] basel = { "Basel I", "Basel II" };
	String[] modelledBankMBSDataTypeString = { "All MBS", "Structured MBS" };
	String[] bankString = { "select", "26 Core Banks",
			"2006-07 CDS Market Active Banks", "Top 12 Banks vs Others",
			"Top 5 Banks", "2-bank Comparison" };

	JComboBox tradingStrategy;
	JComboBox startYear;
	JComboBox startQuater;
	JComboBox cb_LeverageStartyear;
	JComboBox cb_LeverageStartQutr;
	JComboBox bankCompareSelection1;
	JComboBox bankCompareSelection2;
	JComboBox modelledBanksSelection;

	String[] tradeStrategy = { "select", "Tranching Structure",
			"Maximum Basis", "Equal", "Maximum Saving", "70-30 CDO Structure",
			"90-10 CDO Structure", "80-20 CDO Structure",
			"75-25 CDO Structure", "Nomura 40-60 CDS Market" };

	String[] startYr = { "select", "2001", "2002", "2003", "2004", "2005",
			"2006", "2007", "2008", "2009" };

	String[] startQtr = { "select", "1", "2", "3", "4" };

	String[] leverageStartYr = { "select", "2001", "2002", "2003", "2004",
			"2005", "2006", "2007", "2008", "2009" };

	String[] leverageStartQtr = { "select", "1", "2", "3", "4" };

	String[] quarters = { "Quarter 1", "Quarter 2", "Quarter 3", "Quarter 4" };

	private ActionListener modelChoiceListener;
	private ActionListener crmChoiceListener;
	private ActionListener baselChoiceListener;
	private ActionListener bankChoiceListener;
	private ActionListener bankRMBSDataChoiceListener;
	private ActionListener leverageChoiceListener;

	private JRadioButton crmButton;
	private JRadioButton noCRMButton;
	private JRadioButton baselIButton;
	private JRadioButton baselIIButton;
	private JRadioButton core26BanksButton;
	private JRadioButton allActiveBanksutton;

	private JTabbedPane tabbedPaneSpreadAndBasis;
	private JTabbedPane jTabbedPane__Spreads;
	private JPanel jPanel_Spreads;
	private JPanel jPanel_SimulationResults;
	private JPanel jContentPaneSpreads;
	private JMenuItem jMenuItemPrintChartsToFile;
	private JMenuItem jMenuItemSaveChartsToImageFile;
	private JLabel modelledBanksSelectionLabel;
	private JFreeChart totalBofA_JPM_SubprimeExposuresSeriesChart;
	private final TimeSeriesCollection totalBofA_JPM_SubprimeExposuresSeriesCol = new TimeSeriesCollection();
	private final TimeSeriesCollection totalCitiBank_ML_SubprimeExposuresSeriesCol = new TimeSeriesCollection();
	private JFreeChart totalCitiBank_ML_SubprimeExposuresSeriesChart;

	private JFreeChart totalBofA_AllOthers_SubprimeExposuresSeriesChart;
	private final TimeSeriesCollection totalBofA_AllOthers_SubprimeExposuresSeriesCol = new TimeSeriesCollection();

	private JFreeChart totalHSBC_KeyBank_SubprimeExposuresSeriesChart;
	private final TimeSeriesCollection totalHSBC_KeyBank_SubprimeExposuresSeriesCol = new TimeSeriesCollection();

	private JPanel contentPane;
	private JPanel contentPaneParent;
	private ModelParameters param;
	protected int reloads;
	private TimeSeries top5AccumulatedCDSCarryTradeIncomeTS;
	private TimeSeries top5AccumulatedCapitalSavingsTS;
	private TimeSeries top5AccumulatedCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetTop5AccumulatedBasisTradeIncome;
	private TimeSeriesCollection dataSetTop5AccumulatedCapitalSavings;
	private TimeSeries BofACurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries BofAACurrentQtrCapitalSavingsTS;
	private TimeSeries BofACurrentQtrCDSBasisIncomeTS;
	private TimeSeries BofARMBSExposuresSimulatedTS;
	private TimeSeries BofARMBSExposuresFDICDataTS;
	private TimeSeries BofAMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetBofARMBSExposures;
	private TimeSeriesCollection dataSetBofACurrentQtrBasisTradeIncome;
	private TimeSeries BoNYCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries BoNYACurrentQtrCapitalSavingsTS;
	private TimeSeries BoNYCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetBoNYCurrentQtrBasisTradeIncome;
	private TimeSeries BoNYRMBSExposuresSimulatedTS;
	private TimeSeries BoNYRMBSExposuresFDICDataTS;
	private TimeSeries BoNYMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetBoNYRMBSExposures;
	private TimeSeries CitibankCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries CitibankACurrentQtrCapitalSavingsTS;
	private TimeSeries CitibankCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetCitibankCurrentQtrBasisTradeIncome;
	private TimeSeries CitibankRMBSExposuresSimulatedTS;
	private TimeSeries CitibankRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetCitibankRMBSExposures;
	private TimeSeries CitibankMezzanineRMBSExposuresFDICDataTS;
	private TimeSeries DeutscheCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries DeutscheACurrentQtrCapitalSavingsTS;
	private TimeSeries DeutscheCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetDeutscheCurrentQtrBasisTradeIncome;
	private TimeSeries DeutscheRMBSExposuresSimulatedTS;
	private TimeSeries DeutscheRMBSExposuresFDICDataTS;
	private TimeSeries DeutscheMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetDeutscheRMBSExposures;
	private TimeSeries GoldmanCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries GoldmanACurrentQtrCapitalSavingsTS;
	private TimeSeries GoldmanCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetGoldmanCurrentQtrBasisTradeIncome;
	private TimeSeries GoldmanRMBSExposuresSimulatedTS;
	private TimeSeries GoldmanRMBSExposuresFDICDataTS;
	private TimeSeries GoldmanMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetGoldmanRMBSExposures;
	private TimeSeries HSBCCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries HSBCACurrentQtrCapitalSavingsTS;
	private TimeSeries HSBCCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetHSBCCurrentQtrBasisTradeIncome;
	private TimeSeries HSBCRMBSExposuresSimulatedTS;
	private TimeSeries HSBCRMBSExposuresFDICDataTS;
	private TimeSeries HSBCMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetHSBCRMBSExposures;
	private TimeSeries JPMorganCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries JPMorganACurrentQtrCapitalSavingsTS;
	private TimeSeries JPMorganCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetJPMorganCurrentQtrBasisTradeIncome;
	private TimeSeries JPMorganRMBSExposuresSimulatedTS;
	private TimeSeries JPMorganRMBSExposuresFDICDataTS;
	private TimeSeries JPMorganMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetJPMorganRMBSExposures;
	private TimeSeries KeybankCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries KeybankACurrentQtrCapitalSavingsTS;
	private TimeSeries KeybankCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetKeybankCurrentQtrBasisTradeIncome;
	private TimeSeries KeybankRMBSExposuresSimulatedTS;
	private TimeSeries KeybankRMBSExposuresFDICDataTS;
	private TimeSeries KeybankMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetKeybankRMBSExposures;
	private TimeSeries MerrillCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries MerrillACurrentQtrCapitalSavingsTS;
	private TimeSeries MerrillCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetMerrillCurrentQtrBasisTradeIncome;
	private TimeSeries MerrillRMBSExposuresSimulatedTS;
	private TimeSeries MerrillRMBSExposuresFDICDataTS;
	private TimeSeries MerrillMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetMerrillRMBSExposures;
	private TimeSeries MorganStanleyCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries MorganStanleyACurrentQtrCapitalSavingsTS;
	private TimeSeries MorganStanleyCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetMorganStanleyCurrentQtrBasisTradeIncome;
	private TimeSeries MorganStanleyRMBSExposuresSimulatedTS;
	private TimeSeries MorganStanleyRMBSExposuresFDICDataTS;
	private TimeSeries MorganStanleyMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetMorganStanleyRMBSExposures;
	private TimeSeries WachoviaCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries WachoviaACurrentQtrCapitalSavingsTS;
	private TimeSeries WachoviaCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetWachoviaCurrentQtrBasisTradeIncome;
	private TimeSeries WachoviaRMBSExposuresSimulatedTS;
	private TimeSeries WachoviaRMBSExposuresFDICDataTS;
	private TimeSeries WachoviaMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetWachoviaRMBSExposures;
	private TimeSeries WellsFargoCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries WellsFargoACurrentQtrCapitalSavingsTS;
	private TimeSeries WellsFargoCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetWellsFargoCurrentQtrBasisTradeIncome;
	private TimeSeries WellsFargoRMBSExposuresSimulatedTS;
	private TimeSeries WellsFargoRMBSExposuresFDICDataTS;
	private TimeSeries WellsFargoMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetWellsFargoRMBSExposures;
	private TimeSeries AllBanksRMBSExposuresSimulatedTS;
	private TimeSeries AllBanksRMBSExposuresFDICDataTS;
	private TimeSeries AllBanksMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetAllBanksRMBSExposures;
	private TimeSeries AllBanksCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries AllBanksACurrentQtrCapitalSavingsTS;
	private TimeSeries AllBanksCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetAllBanksCurrentQtrBasisTradeIncome;
	private TimeSeries OtherBanksRMBSExposuresSimulatedTS;
	private TimeSeries OtherBanksRMBSExposuresFDICDataTS;
	private TimeSeries OtherBanksMezzanineRMBSExposuresFDICDataTS;
	private TimeSeriesCollection dataSetOtherBanksRMBSExposures;
	private TimeSeries OtherBanksCurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries OtherBanksACurrentQtrCapitalSavingsTS;
	private TimeSeries OtherBanksCurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetOtherBanksCurrentQtrBasisTradeIncome;
	private TimeSeriesCollection dataSetMajor12RMBSExposures;
	private TimeSeries Major12RMBSExposuresSimulatedTS;
	private TimeSeries Major12RMBSExposuresFDICDataTS;
	private TimeSeries Major12MezzanineRMBSExposuresFDICDataTS;
	private TimeSeries Major12CurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries Major12ACurrentQtrCapitalSavingsTS;
	private TimeSeries Major12CurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetMajor12CurrentQtrBasisTradeIncome;
	private TimeSeries top5RegulatoryCapitalLeverageTakingTS;
	private TimeSeriesCollection dataSetTop5RegulatoryCapitalLeverageTaking;
	private TimeSeries top5RMBSExposuresTS;
	private TimeSeriesCollection dataSetTop5RMBSExposures;
	private TimeSeries top5CurrentQtrCDSCarryTradeIncomeTS;
	private TimeSeries top5ACurrentQtrCapitalSavingsTS;
	private TimeSeries top5CurrentQtrCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetTop5CurrentQtrBasisTradeIncome;
	private TimeSeries top5CurrentQtrCapitalSavingsTS;
	private TimeSeries AllBanksAccumulatedCDSCarryTradeIncomeTS;
	private TimeSeries AllBanksAccumulatedCapitalSavingsTS;
	private TimeSeries AllBanksAccumulatedCDSBasisIncomeTS;
	private TimeSeriesCollection dataSetAllBanksAccumulatedBasisTradeIncome;
	private TimeSeriesCollection dataSetAllBanksAccumulatedCapitalSavings;
	private TimeSeries AllBanksRegulatoryCapitalLeverageTakingTS;
	private TimeSeriesCollection dataSetAllBanksRegulatoryCapitalLeverageTaking;
	private TimeSeries top5RMBSMezzanineTrancheExposuresTS;
	private TimeSeries top5RMBSExposuresFDICTS;
	private TimeSeriesCollection cashAndCreditDerivativesSpreadsTC;
	private TimeSeries cashSpreadsTS;
	private TimeSeries creditDerivativesSpreadsTS;
	private TimeSeriesCollection CDSBasisTC;
	private TimeSeries cdsBasisTS;
	private TimeSeriesCollection libroRateTC;
	private TimeSeries liborRateTS;
	private JPanel panel_AllBanksOutput;
	private ChartPanel panel_RMBSAssetAccumulation;
	private ChartPanel panel_CDSCarryTradeIncomeAccumulation;
	private ChartPanel panel_AllBanksAccumulatedCapitalSavings;
	private ChartPanel panel_PerQuarterCDSCarryTradeIncome;
	private ChartPanel panel_RegulatroyLeverageRatio;
	private JPanel panel_Top5BanksOutput;
	private ChartPanel panel_Top5RMBSAssetAccumulation;
	private ChartPanel panel_Top5CDSCarryTradeIncomeAccumulation;
	private ChartPanel panel_Top5PerQuarterCDSCarryTradeIncome;
	private ChartPanel panel_Top5RegulatroyLeverageRatio;
	private ChartPanel panel_Top5AccumulatedCapitalSavings;
	private JPanel panel_Major12AndOtherBanksOutput;
	private ChartPanel panel_Major12RMBSExposures;
	private ChartPanel panel_OtherBanksRMBSExposures;
	private ChartPanel panel_Major12CurrentQtrBasisTradeIncome;
	private ChartPanel panel_OtherBanksCurrentQtrBasisTradeIncome;
	private JPanel panel_BOFABONYOutput;
	private ChartPanel panel_BofARMBSExposures;
	private ChartPanel panel_BoNYRMBSExposures;
	private ChartPanel panel_BofACurrentQtrBasisTradeIncome;
	private ChartPanel panel_BoNYCurrentQtrBasisTradeIncome;
	private JPanel panel_KeyBankAndCitiBankOutput;
	private ChartPanel panel_KeyBankRMBSExposures;
	private ChartPanel panel_CitiBankRMBSExposures;
	private ChartPanel panel_KeyBankCurrentQtrBasisTradeIncome;
	private ChartPanel panel_CitiBankCurrentQtrBasisTradeIncome;
	private JPanel panel_WellsAndHSBCOutput;
	private ChartPanel panel_WellsRMBSExposures;
	private ChartPanel panel_HSBCRMBSExposures;
	private ChartPanel panel_WellsCurrentQtrBasisTradeIncome;
	private ChartPanel panel_HSBCCurrentQtrBasisTradeIncome;
	private JPanel panel_MerrilAndJPMorganOutput;
	private ChartPanel panel_MerrilLynchRMBSExposures;
	private ChartPanel panel_JPMorganRMBSExposures;
	private ChartPanel panel_MerrilLynchCurrentQtrBasisTradeIncome;
	private ChartPanel panel_JPMorganCurrentQtrBasisTradeIncome;
	private JPanel panel_GoldmanAndMorganStanleyOutput;
	private ChartPanel panel_GoldmanRMBSExposures;
	private ChartPanel panel_MorganStanleyRMBSExposures;
	private ChartPanel panel_GoldmanCurrentQtrBasisTradeIncome;
	private ChartPanel panel_MorganStanleyCurrentQtrBasisTradeIncome;
	private JPanel panel_WachoviaAndDeutscheOutput;
	private ChartPanel panel_WachoviaRMBSExposures;
	private ChartPanel panel_DeutscheRMBSExposures;
	private ChartPanel panel_WachoviaCurrentQtrBasisTradeIncome;
	private ChartPanel panel_DeutscheCurrentQtrBasisTradeIncome;
	private JPanel application_main_panel;
	private JPanel jTool_bar;

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<FOR CHART
	// UPDATE>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	double aggregateRMBSExposuresTop5BanksSimulated = 0;
	double aggregateRMBSMezTrancheExposuresTop5BanksSimulated = 0;
	double aggregateRMBSExposuresTop5BanksFDICData = 0;
	double top5AccumulatedCDSCarryTradeIncome = 0;
	double top5AccumulatedCapitalSavings = 0;
	double top5AccumulatedCDSBasisIncome = 0;
	double top5RegulatoryCapitalLeverageTaking = 0;
	double top5CurrentQtrCDSCarryTradeIncome = 0;
	double top5CurrentQtrCapitalSavings = 0;
	double top5CurrentQtrCDSBasisIncome = 0;
	double top5AggregatedExposuresProtectionBuyers = 0;
	double top5AggregatedGrossSavingProtectionBuyers = 0;
	double top5AggregatedReInvestmentExposuresProtectionBuyers = 0;

	double AllBanksRMBSExposuresSimulated = 0;
	double AllBanksRMBSExposuresFDICData = 0;
	double AllBanksMezzanineRMBSExposuresSimulated = 0;
	double AllBanksCurrentQtrCDSCarryTradeIncome = 0;
	double AllBanksACurrentQtrCapitalSavings = 0;
	double AllBanksCurrentQtrCDSBasisIncome = 0;
	double AllBanksAccumulatedCDSCarryTradeIncome = 0;
	double AllBanksAccumulatedCapitalSavings = 0;
	double AllBanksAccumulatedCDSBasisIncome = 0;
	double AllBanksRegulatoryCapitalLeverageTaking = 0;
	double allBanksAggregatedExposuresProtectionBuyers = 0;
	double allBanksAggregatedGrossSavingProtectionBuyers = 0;
	double allBanksAggregatedReInvestmentExposuresProtectionBuyers = 0;

	double OtherBanksRMBSExposuresSimulated = 0;
	double OtherBanksRMBSExposuresFDICData = 0;
	double OtherBanksMezzanineRMBSExposuresSimulated = 0;
	double OtherBanksCurrentQtrCDSCarryTradeIncome = 0;
	double OtherBanksACurrentQtrCapitalSavings = 0;
	double OtherBanksCurrentQtrCDSBasisIncome = 0;

	double Major12RMBSExposuresSimulated = 0;
	double Major12RMBSExposuresFDICData = 0;
	double Major12MezzanineRMBSExposuresSimulated = 0;
	double Major12CurrentQtrCDSCarryTradeIncome = 0;
	double Major12ACurrentQtrCapitalSavings = 0;
	double Major12CurrentQtrCDSBasisIncome = 0;
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF
	// AGGREGATED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<INDIVIDUAL
	// BANKS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	double BofACurrentQtrCDSCarryTradeIncome = 0;
	double BofAACurrentQtrCapitalSavings = 0;
	double BofACurrentQtrCDSBasisIncome = 0;
	double BofARMBSExposuresSimulated = 0;
	double BofARMBSExposuresFDICData = 0;
	double BofAMezzanineRMBSExposuresSimulated = 0;

	double BoNYCurrentQtrCDSCarryTradeIncome = 0;
	double BoNYACurrentQtrCapitalSavings = 0;
	double BoNYCurrentQtrCDSBasisIncome = 0;
	double BoNYRMBSExposuresSimulated = 0;
	double BoNYRMBSExposuresFDICData = 0;
	double BoNYMezzanineRMBSExposuresSimulated = 0;

	double CitibankCurrentQtrCDSCarryTradeIncome = 0;
	double CitibankACurrentQtrCapitalSavings = 0;
	double CitibankCurrentQtrCDSBasisIncome = 0;
	double CitibankRMBSExposuresSimulated = 0;
	double CitibankRMBSExposuresFDICData = 0;
	double CitibankMezzanineRMBSExposuresSimulated = 0;

	double DeutscheCurrentQtrCDSCarryTradeIncome = 0;
	double DeutscheACurrentQtrCapitalSavings = 0;
	double DeutscheCurrentQtrCDSBasisIncome = 0;
	double DeutscheRMBSExposuresSimulated = 0;
	double DeutscheRMBSExposuresFDICData = 0;
	double DeutscheMezzanineRMBSExposuresSimulated = 0;

	double GoldmanCurrentQtrCDSCarryTradeIncome = 0;
	double GoldmanACurrentQtrCapitalSavings = 0;
	double GoldmanCurrentQtrCDSBasisIncome = 0;
	double GoldmanRMBSExposuresSimulated = 0;
	double GoldmanRMBSExposuresFDICData = 0;
	double GoldmanMezzanineRMBSExposuresSimulated = 0;

	double HSBCCurrentQtrCDSCarryTradeIncome = 0;
	double HSBCACurrentQtrCapitalSavings = 0;
	double HSBCCurrentQtrCDSBasisIncome = 0;
	double HSBCRMBSExposuresSimulated = 0;
	double HSBCRMBSExposuresFDICData = 0;
	double HSBCMezzanineRMBSExposuresSimulated = 0;

	double JPMorganCurrentQtrCDSCarryTradeIncome = 0;
	double JPMorganACurrentQtrCapitalSavings = 0;
	double JPMorganCurrentQtrCDSBasisIncome = 0;
	double JPMorganRMBSExposuresSimulated = 0;
	double JPMorganRMBSExposuresFDICData = 0;
	double JPMorganMezzanineRMBSExposuresSimulated = 0;

	double KeybankCurrentQtrCDSCarryTradeIncome = 0;
	double KeybankACurrentQtrCapitalSavings = 0;
	double KeybankCurrentQtrCDSBasisIncome = 0;
	double KeybankRMBSExposuresSimulated = 0;
	double KeybankRMBSExposuresFDICData = 0;
	double KeybankMezzanineRMBSExposuresSimulated = 0;

	double MerrillCurrentQtrCDSCarryTradeIncome = 0;
	double MerrillACurrentQtrCapitalSavings = 0;
	double MerrillCurrentQtrCDSBasisIncome = 0;
	double MerrillRMBSExposuresSimulated = 0;
	double MerrillRMBSExposuresFDICData = 0;
	double MerrillMezzanineRMBSExposuresSimulated = 0;

	double MorganStanleyCurrentQtrCDSCarryTradeIncome = 0;
	double MorganStanleyACurrentQtrCapitalSavings = 0;
	double MorganStanleyCurrentQtrCDSBasisIncome = 0;
	double MorganStanleyRMBSExposuresSimulated = 0;
	double MorganStanleyRMBSExposuresFDICData = 0;
	double MorganStanleyMezzanineRMBSExposuresSimulated = 0;

	double WachoviaCurrentQtrCDSCarryTradeIncome = 0;
	double WachoviaACurrentQtrCapitalSavings = 0;
	double WachoviaCurrentQtrCDSBasisIncome = 0;
	double WachoviaRMBSExposuresSimulated = 0;
	double WachoviaRMBSExposuresFDICData = 0;
	double WachoviaMezzanineRMBSExposuresSimulated = 0;

	double WellsFargoCurrentQtrCDSCarryTradeIncome = 0;
	double WellsFargoACurrentQtrCapitalSavings = 0;
	double WellsFargoCurrentQtrCDSBasisIncome = 0;
	double WellsFargoRMBSExposuresSimulated = 0;
	double WellsFargoRMBSExposuresFDICData = 0;
	double WellsFargoMezzanineRMBSExposuresSimulated = 0;
	private JTabbedPane tabbedPane_SimulationOutput;
	private JTabbedPane tabbedPane_BasisAndSpreads;

	ArrayList defaultCashSpreads = new ArrayList();
	ArrayList defaultCreditDerivativesSpreads = new ArrayList();
	ArrayList defaultCreditDerivativesPrices = new ArrayList();
	ArrayList defaultCDSBasis = new ArrayList<Double>();

	ArrayList<ArrayList> defaultCashSpreadsTS = new ArrayList<ArrayList>();
	ArrayList<ArrayList> defaultCreditDerivativesSpreadsTS = new ArrayList<ArrayList>();
	ArrayList<ArrayList> defaultCDSBasisTS = new ArrayList<ArrayList>();

	double defaultcashSpreadsAAA;
	double defaultcashSpreadsAA;
	double defaultcashSpreadsA;
	double defaultcashSpreadsBBB;
	double defaultcashSpreadsBBB3;

	double defaultcreditDerivativesSpreadsAAA;
	double defaultcreditDerivativesSpreadsAA;
	double defaultcreditDerivativesSpreadsA;
	double defaultcreditDerivativesSpreadsBBB;
	double defaultcreditDerivativesSpreadsBBB3;

	double defaultCDSBasisAAA;
	double defaultCDSBasisAA;
	double defaultCDSBasisA;
	double defaultCDSBasisBBB;
	double defaultCDSBasisBBB3;

	double defaultcreditDerivativesPricesAAA;
	double defaultcreditDerivativesPricesAA;
	double defaultcreditDerivativesPricesA;
	double defaultcreditDerivativesPricesBBB;
	double defaultcreditDerivativesPricesBBB3;

	double defaultLIBORRate;
	private TimeSeries cashSpreadsAAATS;
	private TimeSeries cashSpreadsAATS;
	private TimeSeries cashSpreadsATS;
	private TimeSeries cashSpreadsBBBTS;
	private TimeSeries cashSpreadsBBB3TS;
	private TimeSeries creditDerivativesSpreadsAAATS;
	private TimeSeries creditDerivativesSpreadsAATS;
	private TimeSeries creditDerivativesSpreadsATS;
	private TimeSeries creditDerivativesSpreadsBBBTS;
	private TimeSeries creditDerivativesSpreadsBBB3TS;

	private TimeSeries creditDerivativesPricesAAATS;
	private TimeSeries creditDerivativesPricesAATS;
	private TimeSeries creditDerivativesPricesATS;
	private TimeSeries creditDerivativesPricesBBBTS;
	private TimeSeries creditDerivativesPricesBBB3TS;

	private TimeSeries CDSBasisAAATS;
	private TimeSeries CDSBasisAATS;
	private TimeSeries CDSBasisATS;
	private TimeSeries CDSBasisBBBTS;
	private TimeSeries CDSBasisBBB3TS;

	private JPanel panel_DefaultCDSandBondSpread;
	private ChartPanel panel_CDSandBondSpread;
	private JPanel panel_DefaultCDSBasis;
	private ChartPanel panel_CDSBasis;
	private JPanel panel_DefaultLIBORRates;
	private ChartPanel panel_LIBORRates;
	private TimeSeriesCollection cashAndCreditDerivativesPriceTC;
	private JPanel panel_DefaultCDSPrices;
	private ChartPanel panel_CDSPrice;
	private JPanel jpanel_CDSIndexPrices;
	private JPanel panel_CDSIndexPricesChoice;
	private JLabel lbl_CDSIndexPricesChoice;
	private JComboBox comboBox_cdsPriceChangeDataChoice;
	private Container jPanel_AgentDataParameters;
	protected int cdsPriceChangeDataChoiceSelection;
	private JPanel panel_PermitTradeUnwinds;
	private JLabel lblAllowTradePosition;
	private JComboBox comboBox_PermitTradeUnwinds;
	private JPanel jPanel_EconomicVariables;
	private JLabel JLabel_RiskFreeRateProcess;
	private JComboBox jComboBox_StochasticProcess;
	private JLabel jLabel_JumpCorrelation;
	private JTextField JTextField_JumpCorrelationRho;
	private JLabel jLabel_InitialValue;
	private JTextField jTextField_InitialValue;
	private JLabel JLabel_Drift;
	private JTextField jTextField_Drift;
	private JLabel JLabel_StandardDeviation;
	private JTextField textField_StandardDeviation;
	private JLabel jLabel_HestonsLongTerm;
	private JTextField textField_HestonsLongTermVariance;
	private JLabel jLabel_HestonsVarianceVolatility;
	private JTextField jTextField_HestonVarianceVolatility;
	private JLabel jLabel_HestonsMeanReversion;
	private JTextField jTextField_HestonsMeanReversion;
	private JLabel jLabel_CirAlpha;
	private JLabel jLabel_CirTheta;
	private JLabel jLabel_JumpIntensity;
	private JLabel lblJumpSizeDistribution;
	private JTextField jTextField_CIRAlpha;
	private JTextField jTextField_CIRTheta;
	private JTextField jTextField_JumpIntensity;
	private JTextField jTextField_MeanJumpSize;
	private JLabel jLabel_MeanJumpSize;
	private JTextField textField_Jump_Size_Distribution_Width;
	private JPanel jPanel_NatureOfEconomicExpectations;
	private JLabel jlabel_NatureOfEconomicExpectations;
	private JComboBox jComboBox_NatureOfEconomicExpectations;
	private JPanel jPanel_DefaultExpectationsType;
	private JLabel jLabel_DefaultExpectationsType;
	private JComboBox jComboBox_DefaultExpectationsType;
	private JPanel jPanel_InitialSurvivalProbabilities;
	private JPanel jPanel_BondTrancheCoupons;
	private JLabel jLabel_TrancheSurvivalProbabilities;
	private JLabel jLabel_ProbSurvivalAfterT;
	private JLabel jLabel_ProbSurvivalAfterTm1;
	private JLabel jLabel_InitialSurvivalProbabilities;
	private JLabel label;
	private JLabel label_1;
	private JLabel jLabel_TrancheCoupons;
	private JTextField jTextField_AAATrancheProbSurvivalAfterT;
	private JTextField jTextField_AATrancheProbSurvivalAfterT;
	private JTextField jTextField_ATrancheProbSurvivalAfterT;
	private JTextField jTextField_BBBTrancheProbSurvivalAfterT;
	private JTextField jTextField_BBB3TrancheProbSurvivalAfterT;
	private JTextField jTextField_AAATrancheProbSurvivalAfterTm1;
	private JTextField jTextField_ATrancheProbSurvivalAfterTm1;
	private JTextField jTextField_BBBTrancheProbSurvivalAfterTm1;
	private JTextField jTextField_AATrancheProbSurvivalAfterTm1;
	private JTextField jTextField_BBB3TrancheProbSurvivalAfterTm1;
	private JLabel jLabel_TrancheBBB3SurvivalProbabilities;
	private JLabel jLabel_TrancheBBBSurvivalProbabilities;
	private JLabel jLabel_TrancheASurvivalProbabilities;
	private JLabel jLabel_TrancheAASurvivalProbabilities;
	private JLabel jLabel_TrancheAAASurvivalProbabilities;
	private JLabel jLabel_TrancheAAACoupons;
	private JLabel jLabel_TrancheAACoupons;
	private JLabel jLabel_TrancheACoupons;
	private JLabel jLabel_TrancheBBBCoupons;
	private JLabel jLabel_TrancheBBB3Coupons;
	private JTextField jTextField_AAATrancheCoupon;
	private JTextField jTextField_AATrancheCoupon;
	private JTextField jTextField_ATrancheCoupon;
	private JTextField jTextField_BBBTrancheCoupon;
	private JTextField jTextField_BBB3TrancheCoupon;
	private JPanel jPanelAnnuityAndDiscount;
	private JLabel jLabel_DiscountFactor;
	private JLabel jLabel_AnnuityFactor;
	private JTextField jTextField_DiscountFactor;
	private JTextField jTextField_AnnuityFactor;

	double AAATrancheProbSurvivalAfterT;
	double AATrancheProbSurvivalAfterT;
	double ATrancheProbSurvivalAfterT;
	double BBBTrancheProbSurvivalAfterT;
	double BBB3TrancheProbSurvivalAfterT;

	double AAATrancheProbSurvivalAfterTm1;
	double ATrancheProbSurvivalAfterTm1;
	double BBBTrancheProbSurvivalAfterTm1;
	double AATrancheProbSurvivalAfterTm1;
	double BBB3TrancheProbSurvivalAfterTm1;

	double AAATrancheCoupon;
	double AATrancheCoupon;
	double ATrancheCoupon;
	double BBBTrancheCoupon;
	double BBB3TrancheCoupon;
	double discountFactor;
	double annuityFactor;

	String stochasticProcessTypeStrng;
	int stochasticProcessTypeStringIndex;
	int numberOfIterations;
	int numberOfPaths;
	int pathLength;
	String assetName;
	double initialAssetvalue;
	double driftMean;
	double standardDeviation;
	double timeShit_dt;
	double cir_Alpha;
	double cir_Theta;
	double jumpIntensity;
	double jump_Mean_Size;
	double jump_Size_Distribution_Width;
	double jumpCorrelation;
	double hestonLongTermVariance;
	double hestonMeanReversionRate;
	double hestonVarianceVolatility;
	private JPanel jPanel_SurvivalProbabilityChange;
	private JLabel jLabel_SurvivalProbabilityIncrements;
	private JLabel jLabel_SurvivalProbabilityChangeTranche;
	private JLabel label_4;
	private JLabel jLabel_SurvivalProbabilityChangeT;
	private JLabel jLabel_AAASurvivalProbabilityChange;
	private JLabel jLabel_AASurvivalProbabilityChange;
	private JLabel jLabel_ASurvivalProbabilityChange;
	private JLabel jLabel_BBBSurvivalProbabilityChange;
	private JLabel jLabel_BBB3SurvivalProbabilityChange;
	private JTextField jTextField_AAASurvivalProbabilityChangeT;
	private JTextField jTextField_AASurvivalProbabilityChangeT;
	private JTextField jTextField_ASurvivalProbabilityChangeT;
	private JTextField jTextField_BBBSurvivalProbabilityChangeT;
	private JTextField jTextField_BBB3SurvivalProbabilityChangeT;
	private JTextField textField_5;
	private JTextField textField_6;
	private JTextField textField_7;
	private JTextField textField_8;
	private JTextField textField_9;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CDSBasisTradeJASApplicationFrame frame = new CDSBasisTradeJASApplicationFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CDSBasisTradeJASApplicationFrame() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/ca.gif")));
		initialize();
		this.eng = new SimEngine();
		this.model = new CDSBasisTradeModel();
		this.model.frame = this;
		eng.addModel(this.model);
		// eng.addModel(this.modelObserver);
		// buildOrUpdateCharts();

	}

	public CDSBasisTradeJASApplicationFrame(SimEngine eng,
			CDSBasisTradeModel model) throws HeadlessException {
		super();
		this.eng = eng;
		this.model = model;
		this.model.frame = this;
		// this.modelObserver = new SecuritisationModelObserver(this.model);
		eng.addModel(this.model);
		// eng.addModel(this.modelObserver);
		param = new ModelParameters();
		initialize();
	}

	public void initialize() {
		setTitle("CDS Basis Trade and Bank Synthetic Securitisation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1590, 889);
		this.cdsBasisTradeModelGUIMenuBar = new JMenuBar();
		setJMenuBar(this.cdsBasisTradeModelGUIMenuBar);
		this.cdsBasisTradeModelGUIMenuBar.add(getJMenuFile());
		this.cdsBasisTradeModelGUIMenuBar.add(getJMenuHelp());
		bgModel = new ButtonGroup();
		bgMBSDataChoice = new ButtonGroup();
		// bgMBSDataChoice = new ButtonGroup();
		bgCRM = new ButtonGroup();
		// bgBasel.add(label);
		bgBasel = new ButtonGroup();
		bgModelWithLeverageChoice = new ButtonGroup();

		// if (jContentPane == null) {
		jContentPane = new JPanel();
		BorderLayout layout = new BorderLayout();
		jContentPane.setPreferredSize(new java.awt.Dimension(1064, 952));
		jContentPane.setLayout(new BorderLayout(0, 0));

		jTool_bar = new JPanel();
		jContentPane.add(jTool_bar, BorderLayout.NORTH);
		jTool_bar.setLayout(new GridLayout(1, 0, 0, 0));

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<JAS SIMULATION
		// RUNNING METHODS AND BUTTON
		// STEPUPS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//
		/**
		 * This button loads the model data and settings
		 */
		jButton_loadModelSettings = new JButton();
		jTool_bar.add(jButton_loadModelSettings);
		jButton_loadModelSettings.setText("Load Model Settings");
		jButton_loadModelSettings.setIcon(new ImageIcon(
				CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/build16.gif")));
		jButton_loadModelSettings
				.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						loadModelSettings_click();

						if (enviroment != null) {
							enviroment = null;
						}
						if (defaultModel) {
							if (tradingStrategy.getSelectedIndex() == 0) {
								JOptionPane
										.showMessageDialog(
												null,
												"Please make sure all simulation settings are properly selected",
												"",
												JOptionPane.INFORMATION_MESSAGE);
								initializeInternalBasisCalculationParameters();
							} else {

								initializeInternalBasisCalculationParameters();
								setModelParameters();

							}

						}
					}

					private void initializeInternalBasisCalculationParameters() {
						// TODO Auto-generated method stub
						vThreshold = Double.parseDouble(volThresholdTF
								.getText());
						haircut = Double.parseDouble(haircutTF.getText());
						numOfQuarters = Integer.parseInt(numQuatersTF.getText());

						AAATrancheProbSurvivalAfterT = Double
								.parseDouble(jTextField_AAATrancheProbSurvivalAfterT
										.getText());
						AATrancheProbSurvivalAfterT = Double
								.parseDouble(jTextField_AATrancheProbSurvivalAfterT
										.getText());
						ATrancheProbSurvivalAfterT = Double
								.parseDouble(jTextField_ATrancheProbSurvivalAfterT
										.getText());
						BBBTrancheProbSurvivalAfterT = Double
								.parseDouble(jTextField_BBBTrancheProbSurvivalAfterT
										.getText());
						BBB3TrancheProbSurvivalAfterT = Double
								.parseDouble(jTextField_BBB3TrancheProbSurvivalAfterT
										.getText());

						AAATrancheProbSurvivalAfterTm1 = Double
								.parseDouble(jTextField_AAATrancheProbSurvivalAfterTm1
										.getText());
						ATrancheProbSurvivalAfterTm1 = Double
								.parseDouble(jTextField_ATrancheProbSurvivalAfterTm1
										.getText());
						BBBTrancheProbSurvivalAfterTm1 = Double
								.parseDouble(jTextField_BBBTrancheProbSurvivalAfterTm1
										.getText());
						AATrancheProbSurvivalAfterTm1 = Double
								.parseDouble(jTextField_AATrancheProbSurvivalAfterTm1
										.getText());
						BBB3TrancheProbSurvivalAfterTm1 = Double
								.parseDouble(jTextField_BBB3TrancheProbSurvivalAfterTm1
										.getText());

						// JOptionPane.showMessageDialog(null,
						// "BBB3TrancheProbSurvivalAfterTm1:"
						// + BBB3TrancheProbSurvivalAfterTm1, "",
						// JOptionPane.INFORMATION_MESSAGE);

						AAATrancheCoupon = Double
								.parseDouble(jTextField_AAATrancheCoupon
										.getText());
						AATrancheCoupon = Double
								.parseDouble(jTextField_AATrancheCoupon
										.getText());
						ATrancheCoupon = Double
								.parseDouble(jTextField_ATrancheCoupon
										.getText());
						BBBTrancheCoupon = Double
								.parseDouble(jTextField_BBBTrancheCoupon
										.getText());
						BBB3TrancheCoupon = Double
								.parseDouble(jTextField_BBB3TrancheCoupon
										.getText());
						discountFactor = Double
								.parseDouble(jTextField_DiscountFactor
										.getText());
						annuityFactor = Double
								.parseDouble(jTextField_AnnuityFactor.getText());

						numberOfIterations = 1;
						numberOfPaths = 1;
						pathLength = numOfQuarters * 3;
						assetName = "Risk Free Rate";
						initialAssetvalue = Double
								.parseDouble(jTextField_InitialValue.getText());
						driftMean = Double.parseDouble(jTextField_Drift
								.getText());
						standardDeviation = Double
								.parseDouble(textField_StandardDeviation
										.getText());
						timeShit_dt = 0.25;// due to quarterly data
						cir_Alpha = Double.parseDouble(jTextField_CIRAlpha
								.getText());
						cir_Theta = Double.parseDouble(jTextField_CIRTheta
								.getText());
						jumpIntensity = Double
								.parseDouble(jTextField_JumpIntensity.getText());
						jump_Mean_Size = Double
								.parseDouble(jTextField_MeanJumpSize.getText());
						jump_Size_Distribution_Width = Double
								.parseDouble(textField_Jump_Size_Distribution_Width
										.getText());
						jumpCorrelation = Double
								.parseDouble(JTextField_JumpCorrelationRho
										.getText());
						hestonLongTermVariance = Double
								.parseDouble(textField_HestonsLongTermVariance
										.getText());
						hestonMeanReversionRate = Double
								.parseDouble(jTextField_HestonsMeanReversion
										.getText());
						hestonVarianceVolatility = Double
								.parseDouble(jTextField_HestonVarianceVolatility
										.getText());
					}
				});

		JButton btnBuildModel = new JButton("Build Model");
		jTool_bar.add(btnBuildModel);
		btnBuildModel.setIcon(new ImageIcon(
				CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/build.gif")));
		btnBuildModel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eng.buildModels();
			}
		});

		jButton_run = new JButton();
		jTool_bar.add(jButton_run);
		jButton_run.setText("Run Simulation");
		jButton_run.setIcon(new ImageIcon(getClass().getResource(
				"/jas/images/play.gif")));
		jButton_run.setEnabled(false);
		jButton_run.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				jButton_run.setVisible(true);
				if (model != null) {
					eng.start();
				} else {
					JOptionPane.showMessageDialog(null, "Model Not Loaded"
							+ "\n"
							+ "Please select a model configuration and define "
							+ "the associated enviroment parameters" + "\n",
							"", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		JButton button_Pause = new JButton("Pause");
		jTool_bar.add(button_Pause);
		button_Pause.setIcon(new ImageIcon(
				CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/pause.gif")));
		button_Pause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				eng.stop();
			}
		});

		JButton button_Step = new JButton("Step");
		jTool_bar.add(button_Step);
		button_Step.setIcon(new ImageIcon(
				CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/timeStep.gif")));
		button_Step.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eng.stop();
				eng.stepTime();
				// updatedModelSimulationState();
			}
		});

		JButton button_Stop = new JButton("Stop");
		jTool_bar.add(button_Stop);
		button_Stop.setIcon(new ImageIcon(
				CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/stop.gif")));
		button_Stop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eng.end();
			}
		});

		jButton_resetModel = new JButton();
		jTool_bar.add(jButton_resetModel);
		jButton_resetModel.setText("Reset Simulation Settings");
		jButton_resetModel.setIcon(new ImageIcon(getClass().getResource(
				"/jas/images/refresh.gif")));
		jButton_resetModel
				.addActionListener(new java.awt.event.ActionListener() {
					@Override
					public void actionPerformed(java.awt.event.ActionEvent e) {
						// System.out.println("actionPerformed()"); // TODO
						// Auto-generated Event stub actionPerformed()
						resetModel_click();
						reloads++;
						eng.disposeModels();
						clearAllActiveGraphicsPanels();
						createNewModel();
						initialize();
					}
				});

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<JAS SIMULATION
		// RUNNING METHODS AND BUTTON
		// STEPUPS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>//

		application_main_panel = new JPanel();
		application_main_panel.setBorder(new BevelBorder(BevelBorder.LOWERED,

		null, null, null, null));
		jContentPane.add(application_main_panel);
		application_main_panel.setLayout(new BorderLayout(0, 0));

		JPanel jPanel_RegRegimeAndEnvRules = new JPanel();
		application_main_panel.add(jPanel_RegRegimeAndEnvRules,
				BorderLayout.EAST);
		jPanel_RegRegimeAndEnvRules.setBorder(new TitledBorder(null,
				"Environment Parameters", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		jPanel_ModelConfig = new JPanel();
		application_main_panel.add(jPanel_ModelConfig, BorderLayout.WEST);
		jPanel_ModelConfig.setBorder(new TitledBorder(null,
				"Banks Decision Parameters", TitledBorder.LEADING,
				TitledBorder.TOP, null, null));
		GridBagLayout gbl_jPanel_ModelConfig = new GridBagLayout();
		gbl_jPanel_ModelConfig.columnWidths = new int[] { 210 };
		gbl_jPanel_ModelConfig.rowWeights = new double[] { 1.0 };
		gbl_jPanel_ModelConfig.columnWeights = new double[] { 1.0 };
		jPanel_ModelConfig.setLayout(gbl_jPanel_ModelConfig);

		JPanel jPanel_AgentParameters = new JPanel();
		GridBagConstraints gbc_jPanel_AgentParameters = new GridBagConstraints();
		gbc_jPanel_AgentParameters.anchor = GridBagConstraints.WEST;
		gbc_jPanel_AgentParameters.insets = new Insets(0, 0, 5, 0);
		gbc_jPanel_AgentParameters.gridx = 0;
		gbc_jPanel_AgentParameters.gridy = 0;
		jPanel_ModelConfig.add(jPanel_AgentParameters,
				gbc_jPanel_AgentParameters);
		GridBagLayout gbl_jPanel_AgentParameters = new GridBagLayout();
		gbl_jPanel_AgentParameters.columnWidths = new int[] { 210 };
		gbl_jPanel_AgentParameters.rowHeights = new int[] { 98, 98, 98, 98 };
		gbl_jPanel_AgentParameters.columnWeights = new double[] { 0.0 };
		gbl_jPanel_AgentParameters.rowWeights = new double[] { 0.0, 0.0, 0.0,
				0.0 };
		jPanel_AgentParameters.setLayout(gbl_jPanel_AgentParameters);
		JPanel strategySelectdBox = new JPanel();
		GridBagConstraints gbc_strategySelectdBox = new GridBagConstraints();
		gbc_strategySelectdBox.fill = GridBagConstraints.BOTH;
		gbc_strategySelectdBox.insets = new Insets(0, 0, 5, 0);
		gbc_strategySelectdBox.gridx = 0;
		gbc_strategySelectdBox.gridy = 0;
		jPanel_AgentParameters.add(strategySelectdBox, gbc_strategySelectdBox);
		strategySelectdBox.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Bank Strategy Selection"));
		GridBagLayout gbl_strategySelectdBox = new GridBagLayout();
		gbl_strategySelectdBox.columnWidths = new int[] { 196 };
		gbl_strategySelectdBox.rowHeights = new int[] { 0, 0, 49 };
		gbl_strategySelectdBox.columnWeights = new double[] { 1.0 };
		gbl_strategySelectdBox.rowWeights = new double[] { 0.0, 1.0, 1.0 };
		strategySelectdBox.setLayout(gbl_strategySelectdBox);
		JPanel strategySelect = new JPanel();
		GridBagConstraints gbc_strategySelect = new GridBagConstraints();
		gbc_strategySelect.insets = new Insets(0, 0, 5, 0);
		gbc_strategySelect.anchor = GridBagConstraints.WEST;
		gbc_strategySelect.gridx = 0;
		gbc_strategySelect.gridy = 0;
		strategySelectdBox.add(strategySelect, gbc_strategySelect);
		GridBagLayout gbl_strategySelect = new GridBagLayout();
		gbl_strategySelect.columnWidths = new int[] { 140 };
		gbl_strategySelect.rowHeights = new int[] { 14, 14 };
		gbl_strategySelect.columnWeights = new double[] { 0.0 };
		gbl_strategySelect.rowWeights = new double[] { 0.0, 0.0 };
		strategySelect.setLayout(gbl_strategySelect);

		JLabel stratLabel = new JLabel("Basis Trading Strategy Choice");
		stratLabel.setFont(new Font("Serif", Font.BOLD, 11));
		GridBagConstraints gbc_stratLabel = new GridBagConstraints();
		gbc_stratLabel.fill = GridBagConstraints.BOTH;
		gbc_stratLabel.insets = new Insets(0, 0, 5, 0);
		gbc_stratLabel.gridx = 0;
		gbc_stratLabel.gridy = 0;
		strategySelect.add(stratLabel, gbc_stratLabel);

		tradingStrategy = new JComboBox();
		tradingStrategy.setFont(new Font("SansSerif", Font.PLAIN, 11));
		tradingStrategy.setModel(new DefaultComboBoxModel(new String[] {
				"select", "Tranching Structure", "Maximum Basis", "Equal",
				"Maximum Saving", "70-30 CDO Structure", "90-10 CDO Structure",
				"80-20 CDO Structure", "75-25 CDO Structure",
				"Nomura 40-60 CDS Market" }));
		tradingStrategy.setSelectedItem(tradeStrategy[0]);

		tradingStrategy.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = ((JComboBox) e.getSource()).getSelectedIndex();
				maximumEarningMethod = i;// note that the index of the
				// ComboBox item is fine here
				// because this is the
				// value that is pluged into the model whereas the text in
				// the combobox item is just for user convinence.
				// JOptionPane.showMessageDialog(null,
				// "Selsected Trading Strategy is: "
				// + tradeStrategy[maximumEarningMethod]
				// + " with selection index "
				// + maximumEarningMethod, "",
				// JOptionPane.INFORMATION_MESSAGE);
			}
		});
		GridBagConstraints gbc_tradingStrategy = new GridBagConstraints();
		gbc_tradingStrategy.fill = GridBagConstraints.BOTH;
		gbc_tradingStrategy.gridx = 0;
		gbc_tradingStrategy.gridy = 1;
		strategySelect.add(tradingStrategy, gbc_tradingStrategy);

		jPanel_DefaultExpectationsType = new JPanel();
		GridBagConstraints gbc_jPanel_DefaultExpectationsType = new GridBagConstraints();
		gbc_jPanel_DefaultExpectationsType.insets = new Insets(0, 0, 0, 0);
		gbc_jPanel_DefaultExpectationsType.fill = GridBagConstraints.BOTH;
		gbc_jPanel_DefaultExpectationsType.gridx = 0;
		gbc_jPanel_DefaultExpectationsType.gridy = 1;
		strategySelectdBox.add(jPanel_DefaultExpectationsType,
				gbc_jPanel_DefaultExpectationsType);
		GridBagLayout gbl_jPanel_DefaultExpectationsType = new GridBagLayout();
		gbl_jPanel_DefaultExpectationsType.columnWidths = new int[] { 140, 0 };
		gbl_jPanel_DefaultExpectationsType.rowHeights = new int[] { 14, 14 };
		gbl_jPanel_DefaultExpectationsType.columnWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		gbl_jPanel_DefaultExpectationsType.rowWeights = new double[] { 0.0, 0.0 };
		jPanel_DefaultExpectationsType
				.setLayout(gbl_jPanel_DefaultExpectationsType);

		jLabel_DefaultExpectationsType = new JLabel("Expectations of Default");
		jLabel_DefaultExpectationsType
				.setFont(new Font("Serif", Font.BOLD, 11));
		GridBagConstraints gbc_jLabel_DefaultExpectationsType = new GridBagConstraints();
		gbc_jLabel_DefaultExpectationsType.fill = GridBagConstraints.BOTH;
		gbc_jLabel_DefaultExpectationsType.insets = new Insets(0, 0, 0, 0);
		gbc_jLabel_DefaultExpectationsType.gridx = 0;
		gbc_jLabel_DefaultExpectationsType.gridy = 0;
		jPanel_DefaultExpectationsType.add(jLabel_DefaultExpectationsType,
				gbc_jLabel_DefaultExpectationsType);

		jComboBox_DefaultExpectationsType = new JComboBox();
		jComboBox_DefaultExpectationsType
				.setToolTipText("Select if Banks have Heterogeneous Economic Beliefs");
		jComboBox_DefaultExpectationsType.setModel(new DefaultComboBoxModel(
				new String[] { "Homogeneous", "Heterogeneous" }));
		jComboBox_DefaultExpectationsType.setSelectedIndex(0);
		int selectedIndex = jComboBox_DefaultExpectationsType
				.getSelectedIndex();
		jComboBox_DefaultExpectationsType.setFont(new Font("SansSerif",
				Font.PLAIN, 11));
		jComboBox_DefaultExpectationsType
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int i = ((JComboBox) e.getSource()).getSelectedIndex();
						if (i == 0) {
							homogeneousAgents = true;// note that the index of
							jComboBox_NatureOfEconomicExpectations
									.setEnabled(true); // the
						} else {
							homogeneousAgents = false;
							jComboBox_NatureOfEconomicExpectations
									.setSelectedIndex(4);
							jComboBox_NatureOfEconomicExpectations
									.setEnabled(false);
						}

					}
				});
		GridBagConstraints gbc_jComboBox_DefaultExpectationsType = new GridBagConstraints();
		gbc_jComboBox_DefaultExpectationsType.fill = GridBagConstraints.BOTH;
		gbc_jComboBox_DefaultExpectationsType.gridx = 0;
		gbc_jComboBox_DefaultExpectationsType.gridy = 1;
		jPanel_DefaultExpectationsType.add(jComboBox_DefaultExpectationsType,
				gbc_jComboBox_DefaultExpectationsType);

		jPanel_NatureOfEconomicExpectations = new JPanel();
		GridBagConstraints gbc_jPanel_NatureOfEconomicExpectations = new GridBagConstraints();
		gbc_jPanel_NatureOfEconomicExpectations.fill = GridBagConstraints.BOTH;
		gbc_jPanel_NatureOfEconomicExpectations.gridx = 0;
		gbc_jPanel_NatureOfEconomicExpectations.gridy = 2;
		strategySelectdBox.add(jPanel_NatureOfEconomicExpectations,
				gbc_jPanel_NatureOfEconomicExpectations);
		GridBagLayout gbl_jPanel_NatureOfEconomicExpectations = new GridBagLayout();
		gbl_jPanel_NatureOfEconomicExpectations.columnWidths = new int[] { 140,
				0 };
		gbl_jPanel_NatureOfEconomicExpectations.rowHeights = new int[] { 14,
				14, 0 };
		gbl_jPanel_NatureOfEconomicExpectations.columnWeights = new double[] {
				0.0, Double.MIN_VALUE };
		gbl_jPanel_NatureOfEconomicExpectations.rowWeights = new double[] {
				0.0, 0.0, Double.MIN_VALUE };
		jPanel_NatureOfEconomicExpectations
				.setLayout(gbl_jPanel_NatureOfEconomicExpectations);

		jlabel_NatureOfEconomicExpectations = new JLabel(
				"Nature of Economic Expectations");
		jlabel_NatureOfEconomicExpectations.setFont(new Font("Serif",
				Font.BOLD, 11));
		GridBagConstraints gbc_jlabel_NatureOfEconomicExpectations = new GridBagConstraints();
		gbc_jlabel_NatureOfEconomicExpectations.fill = GridBagConstraints.BOTH;
		gbc_jlabel_NatureOfEconomicExpectations.insets = new Insets(0, 0, 5, 0);
		gbc_jlabel_NatureOfEconomicExpectations.gridx = 0;
		gbc_jlabel_NatureOfEconomicExpectations.gridy = 0;
		jPanel_NatureOfEconomicExpectations.add(
				jlabel_NatureOfEconomicExpectations,
				gbc_jlabel_NatureOfEconomicExpectations);

		jComboBox_NatureOfEconomicExpectations = new JComboBox();
		jComboBox_NatureOfEconomicExpectations
				.setModel(new DefaultComboBoxModel(new String[] { "select",
						"Optimistic", "Pessimistic", "Constistent", "Mixed" }));
		jComboBox_NatureOfEconomicExpectations.setSelectedIndex(3);
		jComboBox_NatureOfEconomicExpectations.setFont(new Font("SansSerif",
				Font.PLAIN, 11));

		jComboBox_NatureOfEconomicExpectations
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int i = ((JComboBox) e.getSource()).getSelectedIndex();
						natureOfEconomicExpectations = i;//
					}
				});

		GridBagConstraints gbc_jComboBox_NatureOfEconomicExpectations = new GridBagConstraints();
		gbc_jComboBox_NatureOfEconomicExpectations.fill = GridBagConstraints.BOTH;
		gbc_jComboBox_NatureOfEconomicExpectations.gridx = 0;
		gbc_jComboBox_NatureOfEconomicExpectations.gridy = 1;
		jPanel_NatureOfEconomicExpectations.add(
				jComboBox_NatureOfEconomicExpectations,
				gbc_jComboBox_NatureOfEconomicExpectations);

		modelWithLeverageChoiceBox = new JPanel();
		GridBagConstraints gbc_modelWithLeverageChoiceBox = new GridBagConstraints();
		gbc_modelWithLeverageChoiceBox.anchor = GridBagConstraints.WEST;
		gbc_modelWithLeverageChoiceBox.fill = GridBagConstraints.BOTH;
		gbc_modelWithLeverageChoiceBox.insets = new Insets(0, 0, 0, 0);
		gbc_modelWithLeverageChoiceBox.gridx = 0;
		gbc_modelWithLeverageChoiceBox.gridy = 1;
		jPanel_AgentParameters.add(modelWithLeverageChoiceBox,
				gbc_modelWithLeverageChoiceBox);
		modelWithLeverageChoiceBox.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Leverage Start Period Selection"));
		GridBagLayout gbl_modelWithLeverageChoiceBox = new GridBagLayout();
		gbl_modelWithLeverageChoiceBox.columnWidths = new int[] { 196, 0 };
		gbl_modelWithLeverageChoiceBox.rowHeights = new int[] { 154 };
		gbl_modelWithLeverageChoiceBox.columnWeights = new double[] { 0.0, 0.0 };
		gbl_modelWithLeverageChoiceBox.rowWeights = new double[] { 0.0 };
		modelWithLeverageChoiceBox.setLayout(gbl_modelWithLeverageChoiceBox);

		JPanel jPanel_MergeLeverageChoice = new JPanel();
		jPanel_MergeLeverageChoice.setPreferredSize(new Dimension(147, 154));
		GridBagLayout gbl_jPanel_MergeLeverageChoice = new GridBagLayout();
		gbl_jPanel_MergeLeverageChoice.columnWidths = new int[] { 140 };
		gbl_jPanel_MergeLeverageChoice.rowHeights = new int[] { 49, 49 };
		gbl_jPanel_MergeLeverageChoice.columnWeights = new double[] { 0.0 };
		gbl_jPanel_MergeLeverageChoice.rowWeights = new double[] { 0.0, 0.0,
				0.0 };
		jPanel_MergeLeverageChoice.setLayout(gbl_jPanel_MergeLeverageChoice);
		JPanel leverageSelect = new JPanel();
		GridBagLayout gbl_leverageSelect = new GridBagLayout();
		gbl_leverageSelect.columnWidths = new int[] { 49, 49, 49 };
		gbl_leverageSelect.rowHeights = new int[] { 14, 14 };
		gbl_leverageSelect.columnWeights = new double[] { 0.0, 0.0, 0.0 };
		gbl_leverageSelect.rowWeights = new double[] { 0.0, 0.0 };
		leverageSelect.setLayout(gbl_leverageSelect);
		JLabel noLeverageButtonLbel = new JLabel(leverageModelString[0]);
		noLeverageButtonLbel.setFont(new Font("Serif", Font.BOLD, 11));
		GridBagConstraints gbc_noLeverageButtonLbel = new GridBagConstraints();
		gbc_noLeverageButtonLbel.anchor = GridBagConstraints.WEST;
		gbc_noLeverageButtonLbel.insets = new Insets(0, 0, 5, 5);
		gbc_noLeverageButtonLbel.gridx = 0;
		gbc_noLeverageButtonLbel.gridy = 0;
		leverageSelect.add(noLeverageButtonLbel, gbc_noLeverageButtonLbel);

		noLeverageButton = new JRadioButton();
		noLeverageButton.addActionListener(leverageChoiceListener);
		bgModelWithLeverageChoice.add(noLeverageButton);
		GridBagConstraints gbc_noLeverageButton = new GridBagConstraints();
		gbc_noLeverageButton.fill = GridBagConstraints.BOTH;
		gbc_noLeverageButton.insets = new Insets(0, 0, 5, 0);
		gbc_noLeverageButton.gridx = 2;
		gbc_noLeverageButton.gridy = 0;
		leverageSelect.add(noLeverageButton, gbc_noLeverageButton);
		JLabel leverageButtonLabel = new JLabel(leverageModelString[1]);
		leverageButtonLabel.setFont(new Font("Serif", Font.BOLD, 11));
		GridBagConstraints gbc_leverageButtonLabel = new GridBagConstraints();
		gbc_leverageButtonLabel.anchor = GridBagConstraints.WEST;
		gbc_leverageButtonLabel.fill = GridBagConstraints.BOTH;
		gbc_leverageButtonLabel.insets = new Insets(0, 0, 0, 5);
		gbc_leverageButtonLabel.gridx = 0;
		gbc_leverageButtonLabel.gridy = 1;
		leverageSelect.add(leverageButtonLabel, gbc_leverageButtonLabel);

		leverageButton = new JRadioButton();
		leverageButton.addActionListener(leverageChoiceListener);
		bgModelWithLeverageChoice.add(leverageButton);
		GridBagConstraints gbc_leverageButton = new GridBagConstraints();
		gbc_leverageButton.fill = GridBagConstraints.BOTH;
		gbc_leverageButton.gridx = 2;
		gbc_leverageButton.gridy = 1;
		leverageSelect.add(leverageButton, gbc_leverageButton);
		GridBagConstraints gbc_leverageSelect = new GridBagConstraints();
		gbc_leverageSelect.anchor = GridBagConstraints.WEST;
		gbc_leverageSelect.gridy = 0;
		gbc_leverageSelect.gridx = 0;
		jPanel_MergeLeverageChoice.add(leverageSelect, gbc_leverageSelect);
		// jPanel_MergeLeverageChoice.add(jPanel_LeverageButtonChoice,
		// gbc_jPanel_LeverageButtonChoice);

		GridBagConstraints gbc_jPanel_MergeLeverageChoice = new GridBagConstraints();
		gbc_jPanel_MergeLeverageChoice.fill = GridBagConstraints.BOTH;
		gbc_jPanel_MergeLeverageChoice.insets = new Insets(0, 0, 0, 5);
		gbc_jPanel_MergeLeverageChoice.anchor = GridBagConstraints.WEST;
		gbc_jPanel_MergeLeverageChoice.gridx = 0;
		gbc_jPanel_MergeLeverageChoice.gridy = 0;
		modelWithLeverageChoiceBox.add(jPanel_MergeLeverageChoice,
				gbc_jPanel_MergeLeverageChoice);

		// JPanel selfFinancingLeverage = new JPanel();
		// GridBagConstraints gbc_selfFinancingLeverage = new
		// GridBagConstraints();
		// gbc_selfFinancingLeverage.anchor = GridBagConstraints.NORTH;
		// gbc_selfFinancingLeverage.fill = GridBagConstraints.HORIZONTAL;
		// gbc_selfFinancingLeverage.insets = new Insets(0, 0, 5, 0);
		// gbc_selfFinancingLeverage.gridx = 0;
		// gbc_selfFinancingLeverage.gridy = 0;
		// jPanel_LeverageStartPeriodChoice.add(selfFinancingLeverage,
		// gbc_selfFinancingLeverage);
		// selfFinancingLeverage.setLayout(new GridLayout(2, 1, 7, 0));
		JPanel leveragePeriodSelect = new JPanel();
		GridBagLayout gbl_leveragePeriodSelect = new GridBagLayout();
		gbl_leveragePeriodSelect.columnWidths = new int[] { 140 };
		gbl_leveragePeriodSelect.rowHeights = new int[] { 14, 14 };
		gbl_leveragePeriodSelect.columnWeights = new double[] { 0.0 };
		gbl_leveragePeriodSelect.rowWeights = new double[] { 0.0, 0.0 };
		leveragePeriodSelect.setLayout(gbl_leveragePeriodSelect);

		cb_LeverageStartyear = new JComboBox();
		cb_LeverageStartyear.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cb_LeverageStartyear.setModel(new DefaultComboBoxModel(new String[] {
				"select", "2001", "2002", "2003", "2004", "2005", "2006",
				"2007", "2008", "2009" }));
		cb_LeverageStartyear.setSelectedItem(this.leverageStartYr[0]);

		cb_LeverageStartyear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = ((JComboBox) e.getSource()).getSelectedIndex();

				String str = leverageStartYr[i];
				if (i != 0) {
					leverageStartyear = Integer.parseInt(str);

					// JOptionPane.showMessageDialog(null,
					// "Selsected leverage start year is: "
					// + leverageStartyear
					// + " with selection index " + i, "",
					// JOptionPane.INFORMATION_MESSAGE);

				}
			}
		});
		GridBagConstraints gbc_cb_LeverageStartyear = new GridBagConstraints();
		gbc_cb_LeverageStartyear.fill = GridBagConstraints.BOTH;
		gbc_cb_LeverageStartyear.anchor = GridBagConstraints.WEST;
		gbc_cb_LeverageStartyear.insets = new Insets(0, 0, 5, 0);
		gbc_cb_LeverageStartyear.gridx = 0;
		gbc_cb_LeverageStartyear.gridy = 0;
		leveragePeriodSelect
				.add(cb_LeverageStartyear, gbc_cb_LeverageStartyear);
		GridBagConstraints gbc_leveragePeriodSelect = new GridBagConstraints();
		gbc_leveragePeriodSelect.anchor = GridBagConstraints.NORTH;
		gbc_leveragePeriodSelect.fill = GridBagConstraints.HORIZONTAL;
		gbc_leveragePeriodSelect.insets = new Insets(0, 0, 5, 0);
		gbc_leveragePeriodSelect.gridx = 0;
		gbc_leveragePeriodSelect.gridy = 1;
		jPanel_MergeLeverageChoice.add(leveragePeriodSelect,
				gbc_leveragePeriodSelect);
		cb_LeverageStartQutr = new JComboBox();
		cb_LeverageStartQutr.setFont(new Font("SansSerif", Font.PLAIN, 11));
		cb_LeverageStartQutr.setModel(new DefaultComboBoxModel(new String[] {
				"select", "1", "2", "3", "4" }));
		cb_LeverageStartQutr.setSelectedItem(leverageStartQtr[0]);

		cb_LeverageStartQutr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = ((JComboBox) e.getSource()).getSelectedIndex();
				String str = leverageStartQtr[i];
				if (i != 0) {
					leverageStartQutr = Integer.parseInt(str);

					// JOptionPane.showMessageDialog(null,
					// "Selsected leverage start quarter is: "
					// + leverageStartQutr
					// + " with selection index " + i, "",
					// JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		GridBagConstraints gbc_cb_LeverageStartQutr = new GridBagConstraints();
		gbc_cb_LeverageStartQutr.anchor = GridBagConstraints.WEST;
		gbc_cb_LeverageStartQutr.fill = GridBagConstraints.BOTH;
		gbc_cb_LeverageStartQutr.gridx = 0;
		gbc_cb_LeverageStartQutr.gridy = 1;
		leveragePeriodSelect
				.add(cb_LeverageStartQutr, gbc_cb_LeverageStartQutr);
		// jPanel_MergeLeverageChoice.add(jPanel_LeverageStartPeriodChoice,
		// gbc_jPanel_LeverageStartPeriodChoice);

		JCheckBox chckbxSelfFinancedLeverage = new JCheckBox(
				"Self-Financing Leverage");
		chckbxSelfFinancedLeverage
				.setFont(new Font("SansSerif", Font.PLAIN, 10));
		chckbxSelfFinancedLeverage
				.setToolTipText("If thicked the leverage is internally financed by trading. Note the the external borrowing is not implemented yet");
		chckbxSelfFinancedLeverage.setSelected(true);
		GridBagConstraints gbc_chckbxSelfFinancedLeverage = new GridBagConstraints();
		gbc_chckbxSelfFinancedLeverage.anchor = GridBagConstraints.NORTH;
		gbc_chckbxSelfFinancedLeverage.fill = GridBagConstraints.HORIZONTAL;
		gbc_chckbxSelfFinancedLeverage.gridx = 0;
		gbc_chckbxSelfFinancedLeverage.gridy = 2;
		jPanel_MergeLeverageChoice.add(chckbxSelfFinancedLeverage,
				gbc_chckbxSelfFinancedLeverage);

		JPanel haircutVThresholdInputPanel = new JPanel();
		GridBagConstraints gbc_haircutVThresholdInputPanel = new GridBagConstraints();
		gbc_haircutVThresholdInputPanel.fill = GridBagConstraints.BOTH;
		gbc_haircutVThresholdInputPanel.anchor = GridBagConstraints.EAST;
		gbc_haircutVThresholdInputPanel.gridx = 1;
		gbc_haircutVThresholdInputPanel.gridy = 0;
		modelWithLeverageChoiceBox.add(haircutVThresholdInputPanel,
				gbc_haircutVThresholdInputPanel);
		haircutVThresholdInputPanel.setPreferredSize(new Dimension(147, 70));
		GridBagLayout gbl_haircutVThresholdInputPanel = new GridBagLayout();
		gbl_haircutVThresholdInputPanel.columnWidths = new int[] { 56 };
		gbl_haircutVThresholdInputPanel.rowHeights = new int[] { 14, 14, 14, 14 };
		gbl_haircutVThresholdInputPanel.columnWeights = new double[] { 0.0 };
		gbl_haircutVThresholdInputPanel.rowWeights = new double[] { 0.0, 0.0,
				0.0, 0.0 };
		haircutVThresholdInputPanel.setLayout(gbl_haircutVThresholdInputPanel);

		haircutLabel = new JLabel("Haircut");
		haircutLabel.setFont(new Font("serif", Font.BOLD, 10));
		GridBagConstraints gbc_haircutLabel = new GridBagConstraints();
		gbc_haircutLabel.anchor = GridBagConstraints.WEST;
		gbc_haircutLabel.insets = new Insets(14, 0, 5, 0);
		gbc_haircutLabel.gridx = 0;
		gbc_haircutLabel.gridy = 0;
		haircutVThresholdInputPanel.add(haircutLabel, gbc_haircutLabel);
		haircutTF = new JTextField(14);
		haircutTF.setFont(new Font("SansSerif", Font.PLAIN, 11));
		haircutTF.setToolTipText("Enter haircut for non-internal levelerage");
		haircutTF.setText("1.0");
		GridBagConstraints gbc_haircutTF = new GridBagConstraints();
		gbc_haircutTF.fill = GridBagConstraints.HORIZONTAL;
		gbc_haircutTF.anchor = GridBagConstraints.WEST;
		gbc_haircutTF.insets = new Insets(0, 0, 5, 0);
		gbc_haircutTF.gridx = 0;
		gbc_haircutTF.gridy = 1;
		haircutVThresholdInputPanel.add(haircutTF, gbc_haircutTF);
		volThresholdLabel = new JLabel("Volatility Threshold");
		volThresholdLabel.setFont(new Font("serif", Font.BOLD, 10));
		GridBagConstraints gbc_volThresholdLabel = new GridBagConstraints();
		gbc_volThresholdLabel.fill = GridBagConstraints.BOTH;
		gbc_volThresholdLabel.insets = new Insets(0, 0, 5, 0);
		gbc_volThresholdLabel.gridx = 0;
		gbc_volThresholdLabel.gridy = 2;
		haircutVThresholdInputPanel.add(volThresholdLabel,
				gbc_volThresholdLabel);
		volThresholdTF = new JTextField(14);
		volThresholdTF.setFont(new Font("SansSerif", Font.PLAIN, 11));
		volThresholdTF
				.setToolTipText("Enter maximum permisible change before off loading of positions");
		volThresholdTF.setText("9.4");
		GridBagConstraints gbc_volThresholdTF = new GridBagConstraints();
		gbc_volThresholdTF.insets = new Insets(0, 0, 14, 0);
		gbc_volThresholdTF.fill = GridBagConstraints.BOTH;
		gbc_volThresholdTF.gridx = 0;
		gbc_volThresholdTF.gridy = 3;
		haircutVThresholdInputPanel.add(volThresholdTF, gbc_volThresholdTF);

		JPanel box_CDSIndexPrices = new JPanel();
		GridBagConstraints gbc_box_CDSIndexPrices = new GridBagConstraints();
		gbc_box_CDSIndexPrices.fill = GridBagConstraints.BOTH;
		gbc_box_CDSIndexPrices.insets = new Insets(0, 0, 0, 0);
		gbc_box_CDSIndexPrices.gridx = 0;
		gbc_box_CDSIndexPrices.gridy = 2;
		jPanel_AgentParameters.add(box_CDSIndexPrices, gbc_box_CDSIndexPrices);
		box_CDSIndexPrices
				.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(),
						"Market Pricing Parameters"));
		GridBagLayout gbl_box_CDSIndexPrices = new GridBagLayout();
		gbl_box_CDSIndexPrices.columnWidths = new int[] { 196 };
		gbl_box_CDSIndexPrices.rowHeights = new int[] { 49, 49, 49, 49 };
		gbl_box_CDSIndexPrices.columnWeights = new double[] { 1.0 };
		gbl_box_CDSIndexPrices.rowWeights = new double[] { 0.0, 1.0, 0.0, 0.0 };
		box_CDSIndexPrices.setLayout(gbl_box_CDSIndexPrices);

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		jpanel_CDSIndexPrices = new JPanel();
		jpanel_CDSIndexPrices.setPreferredSize(new Dimension(259, 63));
		GridBagLayout gbl_jpanel_CDSIndexPrices = new GridBagLayout();
		gbl_jpanel_CDSIndexPrices.columnWidths = new int[] { 98, 98 };
		gbl_jpanel_CDSIndexPrices.rowHeights = new int[] { 21, 21 };
		gbl_jpanel_CDSIndexPrices.columnWeights = new double[] { 0.0, 1.0 };
		gbl_jpanel_CDSIndexPrices.rowWeights = new double[] { 1.0,
				Double.MIN_VALUE };
		jpanel_CDSIndexPrices.setLayout(gbl_jpanel_CDSIndexPrices);
		GridBagConstraints gbc_jpanel_CDSIndexPrices = new GridBagConstraints();
		gbc_jpanel_CDSIndexPrices.insets = new Insets(21, 0, 0, 0);
		gbc_jpanel_CDSIndexPrices.anchor = GridBagConstraints.WEST;
		gbc_jpanel_CDSIndexPrices.gridx = 0;
		gbc_jpanel_CDSIndexPrices.gridy = 0;
		box_CDSIndexPrices
				.add(jpanel_CDSIndexPrices, gbc_jpanel_CDSIndexPrices);

		panel_CDSIndexPricesChoice = new JPanel();
		GridBagConstraints gbc_panel_CDSIndexPricesChoice = new GridBagConstraints();
		gbc_panel_CDSIndexPricesChoice.insets = new Insets(0, 0, 0, 5);
		gbc_panel_CDSIndexPricesChoice.anchor = GridBagConstraints.WEST;
		gbc_panel_CDSIndexPricesChoice.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel_CDSIndexPricesChoice.gridx = 0;
		gbc_panel_CDSIndexPricesChoice.gridy = 0;
		jpanel_CDSIndexPrices.add(panel_CDSIndexPricesChoice,
				gbc_panel_CDSIndexPricesChoice);
		GridBagLayout gbl_panel_CDSIndexPricesChoice = new GridBagLayout();
		gbl_panel_CDSIndexPricesChoice.columnWidths = new int[] { 98 };
		gbl_panel_CDSIndexPricesChoice.rowHeights = new int[] { 14, 14 };
		gbl_panel_CDSIndexPricesChoice.columnWeights = new double[] { 0.0 };
		gbl_panel_CDSIndexPricesChoice.rowWeights = new double[] { 0.0, 0.0 };
		panel_CDSIndexPricesChoice.setLayout(gbl_panel_CDSIndexPricesChoice);

		comboBox_cdsPriceChangeDataChoice = new JComboBox();
		comboBox_cdsPriceChangeDataChoice.setFont(new Font("SansSerif",
				Font.PLAIN, 11));
		comboBox_cdsPriceChangeDataChoice.setModel(new DefaultComboBoxModel(
				new String[] { "select", "Combined Tranche Prices",
						"Tranche Vintage Prices" }));
		comboBox_cdsPriceChangeDataChoice.setSelectedIndex(0);
		comboBox_cdsPriceChangeDataChoice
				.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int i = ((JComboBox) e.getSource()).getSelectedIndex();
						if (i == 0) {
							JOptionPane
									.showMessageDialog(
											null,
											"Please selsected which prices changes to use for Mark-to-market P&L",
											"", JOptionPane.INFORMATION_MESSAGE);

						} else if (i == 1) {
							cdsPriceChangeDataChoiceSelection = 1;

							// JOptionPane.showMessageDialog(null,
							// "Selsected leverage start year is: "
							// + leverageStartyear
							// + " with selection index " + i, "",
							// JOptionPane.INFORMATION_MESSAGE);

						} else if (i == 2) {
							cdsPriceChangeDataChoiceSelection = 2;

							// JOptionPane.showMessageDialog(null,
							// "Selsected leverage start year is: "
							// + leverageStartyear
							// + " with selection index " + i, "",
							// JOptionPane.INFORMATION_MESSAGE);

						}
					}
				});

		lbl_CDSIndexPricesChoice = new JLabel("Base Model Mark-to-Market Data");
		lbl_CDSIndexPricesChoice.setFont(new Font("Serif", Font.BOLD, 10));
		GridBagConstraints gbc_lbl_CDSIndexPricesChoice = new GridBagConstraints();
		gbc_lbl_CDSIndexPricesChoice.fill = GridBagConstraints.BOTH;
		gbc_lbl_CDSIndexPricesChoice.anchor = GridBagConstraints.WEST;
		gbc_lbl_CDSIndexPricesChoice.gridx = 0;
		gbc_lbl_CDSIndexPricesChoice.gridy = 0;
		panel_CDSIndexPricesChoice.add(lbl_CDSIndexPricesChoice,
				gbc_lbl_CDSIndexPricesChoice);

		GridBagConstraints gbc_comboBox_cdsPriceChangeDataChoice = new GridBagConstraints();
		gbc_comboBox_cdsPriceChangeDataChoice.anchor = GridBagConstraints.WEST;
		gbc_comboBox_cdsPriceChangeDataChoice.gridx = 0;
		gbc_comboBox_cdsPriceChangeDataChoice.gridy = 1;
		panel_CDSIndexPricesChoice.add(comboBox_cdsPriceChangeDataChoice,
				gbc_comboBox_cdsPriceChangeDataChoice);

		jPanelAnnuityAndDiscount = new JPanel();
		GridBagConstraints gbc_jPanelAnnuityAndDiscount = new GridBagConstraints();
		gbc_jPanelAnnuityAndDiscount.anchor = GridBagConstraints.EAST;
		gbc_jPanelAnnuityAndDiscount.fill = GridBagConstraints.BOTH;
		gbc_jPanelAnnuityAndDiscount.gridx = 1;
		gbc_jPanelAnnuityAndDiscount.gridy = 0;
		jpanel_CDSIndexPrices.add(jPanelAnnuityAndDiscount,
				gbc_jPanelAnnuityAndDiscount);
		GridBagLayout gbl_jPanelAnnuityAndDiscount = new GridBagLayout();
		gbl_jPanelAnnuityAndDiscount.columnWidths = new int[] { 49, 49 };
		gbl_jPanelAnnuityAndDiscount.rowHeights = new int[] { 14, 14 };
		gbl_jPanelAnnuityAndDiscount.columnWeights = new double[] { 0.0, 1.0 };
		gbl_jPanelAnnuityAndDiscount.rowWeights = new double[] { 0.0, 0.0 };
		jPanelAnnuityAndDiscount.setLayout(gbl_jPanelAnnuityAndDiscount);

		jLabel_DiscountFactor = new JLabel("DiscountFactor");
		jLabel_DiscountFactor.setFont(new Font("SansSerif", Font.BOLD, 9));
		GridBagConstraints gbc_jLabel_DiscountFactor = new GridBagConstraints();
		gbc_jLabel_DiscountFactor.anchor = GridBagConstraints.EAST;
		gbc_jLabel_DiscountFactor.gridx = 0;
		gbc_jLabel_DiscountFactor.gridy = 0;
		jPanelAnnuityAndDiscount.add(jLabel_DiscountFactor,
				gbc_jLabel_DiscountFactor);

		jTextField_DiscountFactor = new JTextField(7);
		jTextField_DiscountFactor.setToolTipText("enter the Discount Factor");
		jTextField_DiscountFactor.setText("0.015");
		jTextField_DiscountFactor.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_DiscountFactor = new GridBagConstraints();
		gbc_jTextField_DiscountFactor.anchor = GridBagConstraints.WEST;
		gbc_jTextField_DiscountFactor.fill = GridBagConstraints.BOTH;
		gbc_jTextField_DiscountFactor.gridx = 1;
		gbc_jTextField_DiscountFactor.gridy = 0;
		jPanelAnnuityAndDiscount.add(jTextField_DiscountFactor,
				gbc_jTextField_DiscountFactor);

		jLabel_AnnuityFactor = new JLabel("Annuity Factor");
		jLabel_AnnuityFactor.setFont(new Font("SansSerif", Font.BOLD, 9));
		GridBagConstraints gbc_jLabel_AnnuityFactor = new GridBagConstraints();
		gbc_jLabel_AnnuityFactor.fill = GridBagConstraints.HORIZONTAL;
		gbc_jLabel_AnnuityFactor.anchor = GridBagConstraints.EAST;
		gbc_jLabel_AnnuityFactor.gridx = 0;
		gbc_jLabel_AnnuityFactor.gridy = 1;
		jPanelAnnuityAndDiscount.add(jLabel_AnnuityFactor,
				gbc_jLabel_AnnuityFactor);

		jTextField_AnnuityFactor = new JTextField(7);
		jTextField_AnnuityFactor.setToolTipText("enter the Annuity Factor");
		jTextField_AnnuityFactor.setText("0.035");
		jTextField_AnnuityFactor.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_AnnuityFactor = new GridBagConstraints();
		gbc_jTextField_AnnuityFactor.anchor = GridBagConstraints.WEST;
		gbc_jTextField_AnnuityFactor.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_AnnuityFactor.gridx = 1;
		gbc_jTextField_AnnuityFactor.gridy = 1;
		jPanelAnnuityAndDiscount.add(jTextField_AnnuityFactor,
				gbc_jTextField_AnnuityFactor);

		jPanel_BondTrancheCoupons = new JPanel();
		jPanel_BondTrancheCoupons.setBorder(null);
		GridBagConstraints gbc_jPanel_BondTrancheCoupons = new GridBagConstraints();
		gbc_jPanel_BondTrancheCoupons.insets = new Insets(7, 0, 0, 0);
		gbc_jPanel_BondTrancheCoupons.fill = GridBagConstraints.BOTH;
		gbc_jPanel_BondTrancheCoupons.gridx = 0;
		gbc_jPanel_BondTrancheCoupons.gridy = 1;
		box_CDSIndexPrices.add(jPanel_BondTrancheCoupons,
				gbc_jPanel_BondTrancheCoupons);
		GridBagLayout gbl_jPanel_BondTrancheCoupons = new GridBagLayout();
		gbl_jPanel_BondTrancheCoupons.columnWidths = new int[] { 28, 28, 28,
				28, 28, 28, 28 };
		gbl_jPanel_BondTrancheCoupons.rowHeights = new int[] { 14, 14 };
		gbl_jPanel_BondTrancheCoupons.columnWeights = new double[] { Double.MIN_VALUE };
		gbl_jPanel_BondTrancheCoupons.rowWeights = new double[] { Double.MIN_VALUE };
		jPanel_BondTrancheCoupons.setLayout(gbl_jPanel_BondTrancheCoupons);

		label = new JLabel("MBS Bond Coupon Rates");
		label.setFont(new Font("SansSerif", Font.BOLD, 10));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.WEST;
		gbc_label.insets = new Insets(7, 0, 0, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 0;
		jPanel_BondTrancheCoupons.add(label, gbc_label);

		label_1 = new JLabel("Tranche");
		label_1.setFont(new Font("SansSerif", Font.BOLD, 10));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(7, 0, 0, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 0;
		jPanel_BondTrancheCoupons.add(label_1, gbc_label_1);

		jLabel_TrancheCoupons = new JLabel("Coupon");
		jLabel_TrancheCoupons.setFont(new Font("SansSerif", Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheCoupons = new GridBagConstraints();
		gbc_jLabel_TrancheCoupons.insets = new Insets(0, 0, 0, 7);
		gbc_jLabel_TrancheCoupons.gridx = 1;
		gbc_jLabel_TrancheCoupons.gridy = 1;
		jPanel_BondTrancheCoupons.add(jLabel_TrancheCoupons,
				gbc_jLabel_TrancheCoupons);

		jLabel_TrancheAAACoupons = new JLabel("AAA");
		jLabel_TrancheAAACoupons.setFont(new Font("SansSerif", Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheAAACoupons = new GridBagConstraints();
		gbc_jLabel_TrancheAAACoupons.anchor = GridBagConstraints.WEST;
		gbc_jLabel_TrancheAAACoupons.insets = new Insets(7, 0, 0, 7);
		gbc_jLabel_TrancheAAACoupons.gridx = 2;
		gbc_jLabel_TrancheAAACoupons.gridy = 0;
		jPanel_BondTrancheCoupons.add(jLabel_TrancheAAACoupons,
				gbc_jLabel_TrancheAAACoupons);

		jLabel_TrancheAACoupons = new JLabel("AA");
		jLabel_TrancheAACoupons.setFont(new Font("SansSerif", Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheAACoupons = new GridBagConstraints();
		gbc_jLabel_TrancheAACoupons.anchor = GridBagConstraints.WEST;
		gbc_jLabel_TrancheAACoupons.insets = new Insets(7, 0, 0, 7);
		gbc_jLabel_TrancheAACoupons.gridx = 3;
		gbc_jLabel_TrancheAACoupons.gridy = 0;
		jPanel_BondTrancheCoupons.add(jLabel_TrancheAACoupons,
				gbc_jLabel_TrancheAACoupons);

		jLabel_TrancheACoupons = new JLabel("A");
		jLabel_TrancheACoupons.setFont(new Font("SansSerif", Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheACoupons = new GridBagConstraints();
		gbc_jLabel_TrancheACoupons.anchor = GridBagConstraints.WEST;
		gbc_jLabel_TrancheACoupons.insets = new Insets(7, 0, 0, 7);
		gbc_jLabel_TrancheACoupons.gridx = 4;
		gbc_jLabel_TrancheACoupons.gridy = 0;
		jPanel_BondTrancheCoupons.add(jLabel_TrancheACoupons,
				gbc_jLabel_TrancheACoupons);

		jLabel_TrancheBBBCoupons = new JLabel("BBB");
		jLabel_TrancheBBBCoupons.setFont(new Font("SansSerif", Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheBBBCoupons = new GridBagConstraints();
		gbc_jLabel_TrancheBBBCoupons.anchor = GridBagConstraints.WEST;
		gbc_jLabel_TrancheBBBCoupons.insets = new Insets(7, 0, 0, 7);
		gbc_jLabel_TrancheBBBCoupons.gridx = 5;
		gbc_jLabel_TrancheBBBCoupons.gridy = 0;
		jPanel_BondTrancheCoupons.add(jLabel_TrancheBBBCoupons,
				gbc_jLabel_TrancheBBBCoupons);

		jLabel_TrancheBBB3Coupons = new JLabel("BBB-");
		jLabel_TrancheBBB3Coupons.setFont(new Font("SansSerif", Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheBBB3Coupons = new GridBagConstraints();
		gbc_jLabel_TrancheBBB3Coupons.anchor = GridBagConstraints.WEST;
		gbc_jLabel_TrancheBBB3Coupons.insets = new Insets(7, 0, 0, 7);
		gbc_jLabel_TrancheBBB3Coupons.gridx = 6;
		gbc_jLabel_TrancheBBB3Coupons.gridy = 0;
		jPanel_BondTrancheCoupons.add(jLabel_TrancheBBB3Coupons,
				gbc_jLabel_TrancheBBB3Coupons);

		jTextField_AAATrancheCoupon = new JTextField(5);
		jTextField_AAATrancheCoupon.setToolTipText("Coupon on the AAA Tranche");
		jTextField_AAATrancheCoupon.setText("0.03");
		jTextField_AAATrancheCoupon
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_AAATrancheCoupon = new GridBagConstraints();
		gbc_AAATrancheCoupon.anchor = GridBagConstraints.WEST;
		gbc_AAATrancheCoupon.insets = new Insets(0, 0, 0, 0);
		gbc_AAATrancheCoupon.fill = GridBagConstraints.HORIZONTAL;
		gbc_AAATrancheCoupon.gridx = 2;
		gbc_AAATrancheCoupon.gridy = 1;
		jPanel_BondTrancheCoupons.add(jTextField_AAATrancheCoupon,
				gbc_AAATrancheCoupon);

		jTextField_AATrancheCoupon = new JTextField(5);
		jTextField_AATrancheCoupon.setToolTipText("Coupon on the AA Tranche");
		jTextField_AATrancheCoupon.setText("0.04");
		jTextField_AATrancheCoupon
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_AATrancheCoupon = new GridBagConstraints();
		gbc_AATrancheCoupon.anchor = GridBagConstraints.WEST;
		gbc_AATrancheCoupon.insets = new Insets(0, 0, 0, 0);
		gbc_AATrancheCoupon.fill = GridBagConstraints.HORIZONTAL;
		gbc_AATrancheCoupon.gridx = 3;
		gbc_AATrancheCoupon.gridy = 1;
		jPanel_BondTrancheCoupons.add(jTextField_AATrancheCoupon,
				gbc_AATrancheCoupon);

		jTextField_ATrancheCoupon = new JTextField(5);
		jTextField_ATrancheCoupon.setToolTipText("Coupon on the A Tranche");
		jTextField_ATrancheCoupon.setText("0.052");
		jTextField_ATrancheCoupon.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_ATrancheCoupon = new GridBagConstraints();
		gbc_ATrancheCoupon.anchor = GridBagConstraints.WEST;
		gbc_ATrancheCoupon.insets = new Insets(0, 0, 0, 0);
		gbc_ATrancheCoupon.fill = GridBagConstraints.HORIZONTAL;
		gbc_ATrancheCoupon.gridx = 4;
		gbc_ATrancheCoupon.gridy = 1;
		jPanel_BondTrancheCoupons.add(jTextField_ATrancheCoupon,
				gbc_ATrancheCoupon);

		jTextField_BBBTrancheCoupon = new JTextField(5);
		jTextField_BBBTrancheCoupon.setToolTipText("Coupon on the BBB Tranche");
		jTextField_BBBTrancheCoupon.setText("0.6");
		jTextField_BBBTrancheCoupon
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_BBBTrancheCoupon = new GridBagConstraints();
		gbc_BBBTrancheCoupon.anchor = GridBagConstraints.WEST;
		gbc_BBBTrancheCoupon.insets = new Insets(0, 0, 0, 0);
		gbc_BBBTrancheCoupon.fill = GridBagConstraints.HORIZONTAL;
		gbc_BBBTrancheCoupon.gridx = 5;
		gbc_BBBTrancheCoupon.gridy = 1;
		jPanel_BondTrancheCoupons.add(jTextField_BBBTrancheCoupon,
				gbc_BBBTrancheCoupon);

		jTextField_BBB3TrancheCoupon = new JTextField(5);
		jTextField_BBB3TrancheCoupon
				.setToolTipText("Coupon on the BBB- Tranche");
		jTextField_BBB3TrancheCoupon.setText("0.1");
		jTextField_BBB3TrancheCoupon.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		GridBagConstraints gbc_BBB3TrancheCoupon = new GridBagConstraints();
		gbc_BBB3TrancheCoupon.anchor = GridBagConstraints.WEST;
		gbc_BBB3TrancheCoupon.insets = new Insets(0, 0, 0, 0);
		gbc_BBB3TrancheCoupon.fill = GridBagConstraints.HORIZONTAL;
		gbc_BBB3TrancheCoupon.gridx = 6;
		gbc_BBB3TrancheCoupon.gridy = 1;
		jPanel_BondTrancheCoupons.add(jTextField_BBB3TrancheCoupon,
				gbc_BBB3TrancheCoupon);

		jPanel_InitialSurvivalProbabilities = new JPanel();
		jPanel_InitialSurvivalProbabilities.setBorder(null);
		GridBagConstraints gbc_jPanel_InitialSurvivalProbabilities = new GridBagConstraints();
		gbc_jPanel_InitialSurvivalProbabilities.insets = new Insets(0, 0, 5, 0);
		gbc_jPanel_InitialSurvivalProbabilities.fill = GridBagConstraints.BOTH;
		gbc_jPanel_InitialSurvivalProbabilities.gridx = 0;
		gbc_jPanel_InitialSurvivalProbabilities.gridy = 2;
		box_CDSIndexPrices.add(jPanel_InitialSurvivalProbabilities,
				gbc_jPanel_InitialSurvivalProbabilities);
		GridBagLayout gbl_jPanel_InitialSurvivalProbabilities = new GridBagLayout();
		gbl_jPanel_InitialSurvivalProbabilities.columnWidths = new int[] { 0,
				28, 28, 28, 28, 28, 28 };
		gbl_jPanel_InitialSurvivalProbabilities.rowHeights = new int[] { 14, 0,
				14 };
		gbl_jPanel_InitialSurvivalProbabilities.columnWeights = new double[] {
				0.0, Double.MIN_VALUE };
		gbl_jPanel_InitialSurvivalProbabilities.rowWeights = new double[] {
				0.0, 0.0, Double.MIN_VALUE };
		jPanel_InitialSurvivalProbabilities
				.setLayout(gbl_jPanel_InitialSurvivalProbabilities);

		jLabel_InitialSurvivalProbabilities = new JLabel(
				"Initial Survival Probabilities");
		GridBagConstraints gbc_jLabel_InitialSurvivalProbabilities = new GridBagConstraints();
		gbc_jLabel_InitialSurvivalProbabilities.insets = new Insets(7, 0, 0, 7);
		gbc_jLabel_InitialSurvivalProbabilities.gridx = 0;
		gbc_jLabel_InitialSurvivalProbabilities.gridy = 0;
		jPanel_InitialSurvivalProbabilities.add(
				jLabel_InitialSurvivalProbabilities,
				gbc_jLabel_InitialSurvivalProbabilities);
		jLabel_InitialSurvivalProbabilities.setFont(new Font("SansSerif",
				Font.BOLD, 10));

		jLabel_TrancheSurvivalProbabilities = new JLabel("Tranche");
		jLabel_TrancheSurvivalProbabilities.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheSurvivalProbabilities = new GridBagConstraints();
		gbc_jLabel_TrancheSurvivalProbabilities.anchor = GridBagConstraints.WEST;
		gbc_jLabel_TrancheSurvivalProbabilities.insets = new Insets(7, 0, 0, 7);
		gbc_jLabel_TrancheSurvivalProbabilities.gridx = 1;
		gbc_jLabel_TrancheSurvivalProbabilities.gridy = 0;
		jPanel_InitialSurvivalProbabilities.add(
				jLabel_TrancheSurvivalProbabilities,
				gbc_jLabel_TrancheSurvivalProbabilities);

		jLabel_ProbSurvivalAfterTm1 = new JLabel("P(x>T-1)");
		jLabel_ProbSurvivalAfterTm1
				.setFont(new Font("SansSerif", Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_ProbSurvivalAfterTm1 = new GridBagConstraints();
		gbc_jLabel_ProbSurvivalAfterTm1.anchor = GridBagConstraints.WEST;
		gbc_jLabel_ProbSurvivalAfterTm1.insets = new Insets(0, 0, 0, 7);
		gbc_jLabel_ProbSurvivalAfterTm1.gridx = 1;
		gbc_jLabel_ProbSurvivalAfterTm1.gridy = 2;
		jPanel_InitialSurvivalProbabilities.add(jLabel_ProbSurvivalAfterTm1,
				gbc_jLabel_ProbSurvivalAfterTm1);

		jLabel_ProbSurvivalAfterT = new JLabel("P(x>T)");
		jLabel_ProbSurvivalAfterT.setFont(new Font("SansSerif", Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_ProbSurvivalAfterT = new GridBagConstraints();
		gbc_jLabel_ProbSurvivalAfterT.anchor = GridBagConstraints.WEST;
		gbc_jLabel_ProbSurvivalAfterT.insets = new Insets(0, 0, 0, 7);
		gbc_jLabel_ProbSurvivalAfterT.gridx = 1;
		gbc_jLabel_ProbSurvivalAfterT.gridy = 1;
		jPanel_InitialSurvivalProbabilities.add(jLabel_ProbSurvivalAfterT,
				gbc_jLabel_ProbSurvivalAfterT);

		jLabel_TrancheAAASurvivalProbabilities = new JLabel("AAA");
		jLabel_TrancheAAASurvivalProbabilities.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheAAASurvivalProbabilities = new GridBagConstraints();
		gbc_jLabel_TrancheAAASurvivalProbabilities.anchor = GridBagConstraints.WEST;
		gbc_jLabel_TrancheAAASurvivalProbabilities.insets = new Insets(7, 0, 0,
				7);
		gbc_jLabel_TrancheAAASurvivalProbabilities.gridx = 2;
		gbc_jLabel_TrancheAAASurvivalProbabilities.gridy = 0;
		jPanel_InitialSurvivalProbabilities.add(
				jLabel_TrancheAAASurvivalProbabilities,
				gbc_jLabel_TrancheAAASurvivalProbabilities);

		jLabel_TrancheAASurvivalProbabilities = new JLabel("AA");
		jLabel_TrancheAASurvivalProbabilities.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheAASurvivalProbabilities = new GridBagConstraints();
		gbc_jLabel_TrancheAASurvivalProbabilities.anchor = GridBagConstraints.WEST;
		gbc_jLabel_TrancheAASurvivalProbabilities.insets = new Insets(7, 0, 0,
				7);
		gbc_jLabel_TrancheAASurvivalProbabilities.gridx = 3;
		gbc_jLabel_TrancheAASurvivalProbabilities.gridy = 0;
		jPanel_InitialSurvivalProbabilities.add(
				jLabel_TrancheAASurvivalProbabilities,
				gbc_jLabel_TrancheAASurvivalProbabilities);

		jLabel_TrancheASurvivalProbabilities = new JLabel("A");
		jLabel_TrancheASurvivalProbabilities.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheASurvivalProbabilities = new GridBagConstraints();
		gbc_jLabel_TrancheASurvivalProbabilities.anchor = GridBagConstraints.WEST;
		gbc_jLabel_TrancheASurvivalProbabilities.insets = new Insets(7, 0, 0, 7);
		gbc_jLabel_TrancheASurvivalProbabilities.gridx = 4;
		gbc_jLabel_TrancheASurvivalProbabilities.gridy = 0;
		jPanel_InitialSurvivalProbabilities.add(
				jLabel_TrancheASurvivalProbabilities,
				gbc_jLabel_TrancheASurvivalProbabilities);

		jLabel_TrancheBBBSurvivalProbabilities = new JLabel("BBB");
		jLabel_TrancheBBBSurvivalProbabilities.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheBBBSurvivalProbabilities = new GridBagConstraints();
		gbc_jLabel_TrancheBBBSurvivalProbabilities.anchor = GridBagConstraints.WEST;
		gbc_jLabel_TrancheBBBSurvivalProbabilities.insets = new Insets(7, 0, 0,
				7);
		gbc_jLabel_TrancheBBBSurvivalProbabilities.gridx = 5;
		gbc_jLabel_TrancheBBBSurvivalProbabilities.gridy = 0;
		jPanel_InitialSurvivalProbabilities.add(
				jLabel_TrancheBBBSurvivalProbabilities,
				gbc_jLabel_TrancheBBBSurvivalProbabilities);

		jLabel_TrancheBBB3SurvivalProbabilities = new JLabel("BBB-");
		jLabel_TrancheBBB3SurvivalProbabilities.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_TrancheBBB3SurvivalProbabilities = new GridBagConstraints();
		gbc_jLabel_TrancheBBB3SurvivalProbabilities.anchor = GridBagConstraints.WEST;
		gbc_jLabel_TrancheBBB3SurvivalProbabilities.insets = new Insets(7, 0,
				0, 7);
		gbc_jLabel_TrancheBBB3SurvivalProbabilities.gridx = 6;
		gbc_jLabel_TrancheBBB3SurvivalProbabilities.gridy = 0;
		jPanel_InitialSurvivalProbabilities.add(
				jLabel_TrancheBBB3SurvivalProbabilities,
				gbc_jLabel_TrancheBBB3SurvivalProbabilities);

		jTextField_AAATrancheProbSurvivalAfterT = new JTextField(5);
		jTextField_AAATrancheProbSurvivalAfterT
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		jTextField_AAATrancheProbSurvivalAfterT.setText("0.97");
		jTextField_AAATrancheProbSurvivalAfterT.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_AAATrancheProbSurvivalAfterT = new GridBagConstraints();
		gbc_AAATrancheProbSurvivalAfterT.anchor = GridBagConstraints.WEST;
		gbc_AAATrancheProbSurvivalAfterT.insets = new Insets(0, 0, 0, 0);
		gbc_AAATrancheProbSurvivalAfterT.fill = GridBagConstraints.HORIZONTAL;
		gbc_AAATrancheProbSurvivalAfterT.gridx = 2;
		gbc_AAATrancheProbSurvivalAfterT.gridy = 1;
		jPanel_InitialSurvivalProbabilities.add(
				jTextField_AAATrancheProbSurvivalAfterT,
				gbc_AAATrancheProbSurvivalAfterT);

		jTextField_AATrancheProbSurvivalAfterT = new JTextField(5);
		jTextField_AATrancheProbSurvivalAfterT
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		jTextField_AATrancheProbSurvivalAfterT.setText("0.95");
		jTextField_AATrancheProbSurvivalAfterT.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_AATrancheProbSurvivalAfterT = new GridBagConstraints();
		gbc_AATrancheProbSurvivalAfterT.anchor = GridBagConstraints.WEST;
		gbc_AATrancheProbSurvivalAfterT.insets = new Insets(0, 0, 0, 0);
		gbc_AATrancheProbSurvivalAfterT.fill = GridBagConstraints.HORIZONTAL;
		gbc_AATrancheProbSurvivalAfterT.gridx = 3;
		gbc_AATrancheProbSurvivalAfterT.gridy = 1;
		jPanel_InitialSurvivalProbabilities.add(
				jTextField_AATrancheProbSurvivalAfterT,
				gbc_AATrancheProbSurvivalAfterT);

		jTextField_ATrancheProbSurvivalAfterT = new JTextField(5);
		jTextField_ATrancheProbSurvivalAfterT
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		jTextField_ATrancheProbSurvivalAfterT.setText("0.93");
		jTextField_ATrancheProbSurvivalAfterT.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_ATrancheProbSurvivalAfterT = new GridBagConstraints();
		gbc_ATrancheProbSurvivalAfterT.anchor = GridBagConstraints.WEST;
		gbc_ATrancheProbSurvivalAfterT.insets = new Insets(0, 0, 0, 0);
		gbc_ATrancheProbSurvivalAfterT.fill = GridBagConstraints.HORIZONTAL;
		gbc_ATrancheProbSurvivalAfterT.gridx = 4;
		gbc_ATrancheProbSurvivalAfterT.gridy = 1;
		jPanel_InitialSurvivalProbabilities.add(
				jTextField_ATrancheProbSurvivalAfterT,
				gbc_ATrancheProbSurvivalAfterT);

		jTextField_BBBTrancheProbSurvivalAfterT = new JTextField(5);
		jTextField_BBBTrancheProbSurvivalAfterT
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		jTextField_BBBTrancheProbSurvivalAfterT.setText("0.90");
		jTextField_BBBTrancheProbSurvivalAfterT.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_BBBTrancheProbSurvivalAfterT = new GridBagConstraints();
		gbc_BBBTrancheProbSurvivalAfterT.anchor = GridBagConstraints.WEST;
		gbc_BBBTrancheProbSurvivalAfterT.insets = new Insets(0, 0, 0, 0);
		gbc_BBBTrancheProbSurvivalAfterT.fill = GridBagConstraints.HORIZONTAL;
		gbc_BBBTrancheProbSurvivalAfterT.gridx = 5;
		gbc_BBBTrancheProbSurvivalAfterT.gridy = 1;
		jPanel_InitialSurvivalProbabilities.add(
				jTextField_BBBTrancheProbSurvivalAfterT,
				gbc_BBBTrancheProbSurvivalAfterT);

		jTextField_BBB3TrancheProbSurvivalAfterT = new JTextField(5);
		jTextField_BBB3TrancheProbSurvivalAfterT
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		jTextField_BBB3TrancheProbSurvivalAfterT.setText("0.85");
		jTextField_BBB3TrancheProbSurvivalAfterT.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_BBB3TrancheProbSurvivalAfterT = new GridBagConstraints();
		gbc_BBB3TrancheProbSurvivalAfterT.anchor = GridBagConstraints.WEST;
		gbc_BBB3TrancheProbSurvivalAfterT.insets = new Insets(0, 0, 0, 0);
		gbc_BBB3TrancheProbSurvivalAfterT.fill = GridBagConstraints.HORIZONTAL;
		gbc_BBB3TrancheProbSurvivalAfterT.gridx = 6;
		gbc_BBB3TrancheProbSurvivalAfterT.gridy = 1;
		jPanel_InitialSurvivalProbabilities.add(
				jTextField_BBB3TrancheProbSurvivalAfterT,
				gbc_BBB3TrancheProbSurvivalAfterT);

		jTextField_AAATrancheProbSurvivalAfterTm1 = new JTextField(5);
		jTextField_AAATrancheProbSurvivalAfterTm1
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		jTextField_AAATrancheProbSurvivalAfterTm1.setText("0.99");
		jTextField_AAATrancheProbSurvivalAfterTm1.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_AAATrancheProbSurvivalAfterTm1 = new GridBagConstraints();
		gbc_AAATrancheProbSurvivalAfterTm1.anchor = GridBagConstraints.WEST;
		gbc_AAATrancheProbSurvivalAfterTm1.insets = new Insets(0, 0, 0, 0);
		gbc_AAATrancheProbSurvivalAfterTm1.fill = GridBagConstraints.HORIZONTAL;
		gbc_AAATrancheProbSurvivalAfterTm1.gridx = 2;
		gbc_AAATrancheProbSurvivalAfterTm1.gridy = 2;
		jPanel_InitialSurvivalProbabilities.add(
				jTextField_AAATrancheProbSurvivalAfterTm1,
				gbc_AAATrancheProbSurvivalAfterTm1);

		jTextField_AATrancheProbSurvivalAfterTm1 = new JTextField(5);
		jTextField_AATrancheProbSurvivalAfterTm1
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		jTextField_AATrancheProbSurvivalAfterTm1.setText("0.97");
		jTextField_AATrancheProbSurvivalAfterTm1.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_AATrancheProbSurvivalAfterTm1 = new GridBagConstraints();
		gbc_AATrancheProbSurvivalAfterTm1.anchor = GridBagConstraints.WEST;
		gbc_AATrancheProbSurvivalAfterTm1.insets = new Insets(0, 0, 0, 0);
		gbc_AATrancheProbSurvivalAfterTm1.fill = GridBagConstraints.HORIZONTAL;
		gbc_AATrancheProbSurvivalAfterTm1.gridx = 3;
		gbc_AATrancheProbSurvivalAfterTm1.gridy = 2;
		jPanel_InitialSurvivalProbabilities.add(
				jTextField_AATrancheProbSurvivalAfterTm1,
				gbc_AATrancheProbSurvivalAfterTm1);

		jTextField_ATrancheProbSurvivalAfterTm1 = new JTextField(5);
		jTextField_ATrancheProbSurvivalAfterTm1
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		jTextField_ATrancheProbSurvivalAfterTm1.setText("0.95");
		jTextField_ATrancheProbSurvivalAfterTm1.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_ATrancheProbSurvivalAfterTm1 = new GridBagConstraints();
		gbc_ATrancheProbSurvivalAfterTm1.anchor = GridBagConstraints.WEST;
		gbc_ATrancheProbSurvivalAfterTm1.insets = new Insets(0, 0, 0, 0);
		gbc_ATrancheProbSurvivalAfterTm1.fill = GridBagConstraints.HORIZONTAL;
		gbc_ATrancheProbSurvivalAfterTm1.gridx = 4;
		gbc_ATrancheProbSurvivalAfterTm1.gridy = 2;
		jPanel_InitialSurvivalProbabilities.add(
				jTextField_ATrancheProbSurvivalAfterTm1,
				gbc_ATrancheProbSurvivalAfterTm1);

		jTextField_BBBTrancheProbSurvivalAfterTm1 = new JTextField(5);
		jTextField_BBBTrancheProbSurvivalAfterTm1
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		jTextField_BBBTrancheProbSurvivalAfterTm1.setText("0.92");
		jTextField_BBBTrancheProbSurvivalAfterTm1.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_BBBTrancheProbSurvivalAfterTm1 = new GridBagConstraints();
		gbc_BBBTrancheProbSurvivalAfterTm1.anchor = GridBagConstraints.WEST;
		gbc_BBBTrancheProbSurvivalAfterTm1.insets = new Insets(0, 0, 0, 0);
		gbc_BBBTrancheProbSurvivalAfterTm1.fill = GridBagConstraints.HORIZONTAL;
		gbc_BBBTrancheProbSurvivalAfterTm1.gridx = 5;
		gbc_BBBTrancheProbSurvivalAfterTm1.gridy = 2;
		jPanel_InitialSurvivalProbabilities.add(
				jTextField_BBBTrancheProbSurvivalAfterTm1,
				gbc_BBBTrancheProbSurvivalAfterTm1);

		jTextField_BBB3TrancheProbSurvivalAfterTm1 = new JTextField(5);
		jTextField_BBB3TrancheProbSurvivalAfterTm1
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		jTextField_BBB3TrancheProbSurvivalAfterTm1.setText("0.87");
		jTextField_BBB3TrancheProbSurvivalAfterTm1.setFont(new Font(
				"SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_BBB3TrancheProbSurvivalAfterTm1 = new GridBagConstraints();
		gbc_BBB3TrancheProbSurvivalAfterTm1.anchor = GridBagConstraints.WEST;
		gbc_BBB3TrancheProbSurvivalAfterTm1.insets = new Insets(0, 0, 0, 0);
		gbc_BBB3TrancheProbSurvivalAfterTm1.fill = GridBagConstraints.HORIZONTAL;
		gbc_BBB3TrancheProbSurvivalAfterTm1.gridx = 6;
		gbc_BBB3TrancheProbSurvivalAfterTm1.gridy = 2;
		jPanel_InitialSurvivalProbabilities.add(
				jTextField_BBB3TrancheProbSurvivalAfterTm1,
				gbc_BBB3TrancheProbSurvivalAfterTm1);

		jPanel_SurvivalProbabilityChange = new JPanel();
		GridBagConstraints gbc_jPanel_SurvivalProbabilityChange = new GridBagConstraints();
		gbc_jPanel_SurvivalProbabilityChange.gridx = 0;
		gbc_jPanel_SurvivalProbabilityChange.gridy = 3;
		box_CDSIndexPrices.add(jPanel_SurvivalProbabilityChange,
				gbc_jPanel_SurvivalProbabilityChange);
		jPanel_SurvivalProbabilityChange.setBorder(null);
		GridBagLayout gbl_jPanel_SurvivalProbabilityChange = new GridBagLayout();
		gbl_jPanel_SurvivalProbabilityChange.columnWidths = new int[] { 28, 28,
				28, 28, 28, 28 };
		gbl_jPanel_SurvivalProbabilityChange.rowHeights = new int[] { 14, 14 };
		gbl_jPanel_SurvivalProbabilityChange.columnWeights = new double[] {
				0.0, 4.9E-324, 0.0, 0.0, 0.0, 0.0, 0.0 };
		gbl_jPanel_SurvivalProbabilityChange.rowWeights = new double[] { 0.0,
				0.0, 4.9E-324 };
		jPanel_SurvivalProbabilityChange
				.setLayout(gbl_jPanel_SurvivalProbabilityChange);

		jLabel_SurvivalProbabilityIncrements = new JLabel(
				" Abolute Change in Survival Probabilities");
		GridBagConstraints gbc_jLabel_SurvivalProbabilityIncrements = new GridBagConstraints();
		gbc_jLabel_SurvivalProbabilityIncrements.insets = new Insets(0, 0, 0, 5);
		gbc_jLabel_SurvivalProbabilityIncrements.gridx = 0;
		gbc_jLabel_SurvivalProbabilityIncrements.gridy = 0;
		jPanel_SurvivalProbabilityChange.add(
				jLabel_SurvivalProbabilityIncrements,
				gbc_jLabel_SurvivalProbabilityIncrements);
		jLabel_SurvivalProbabilityIncrements.setFont(new Font("SansSerif",
				Font.BOLD, 10));

		jLabel_SurvivalProbabilityChangeTranche = new JLabel("Tranche");
		jLabel_SurvivalProbabilityChangeTranche.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_SurvivalProbabilityChangeTranche = new GridBagConstraints();
		gbc_jLabel_SurvivalProbabilityChangeTranche.anchor = GridBagConstraints.WEST;
		gbc_jLabel_SurvivalProbabilityChangeTranche.insets = new Insets(0, 0,
				0, 7);
		gbc_jLabel_SurvivalProbabilityChangeTranche.gridx = 1;
		gbc_jLabel_SurvivalProbabilityChangeTranche.gridy = 0;
		jPanel_SurvivalProbabilityChange.add(
				jLabel_SurvivalProbabilityChangeTranche,
				gbc_jLabel_SurvivalProbabilityChangeTranche);

		jLabel_AAASurvivalProbabilityChange = new JLabel("AAA");
		jLabel_AAASurvivalProbabilityChange.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_AAASurvivalProbabilityChange = new GridBagConstraints();
		gbc_jLabel_AAASurvivalProbabilityChange.anchor = GridBagConstraints.WEST;
		gbc_jLabel_AAASurvivalProbabilityChange.insets = new Insets(0, 0, 0, 7);
		gbc_jLabel_AAASurvivalProbabilityChange.gridx = 2;
		gbc_jLabel_AAASurvivalProbabilityChange.gridy = 0;
		jPanel_SurvivalProbabilityChange.add(
				jLabel_AAASurvivalProbabilityChange,
				gbc_jLabel_AAASurvivalProbabilityChange);

		jLabel_AASurvivalProbabilityChange = new JLabel("AA");
		jLabel_AASurvivalProbabilityChange.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_AASurvivalProbabilityChange = new GridBagConstraints();
		gbc_jLabel_AASurvivalProbabilityChange.anchor = GridBagConstraints.WEST;
		gbc_jLabel_AASurvivalProbabilityChange.insets = new Insets(0, 0, 0, 7);
		gbc_jLabel_AASurvivalProbabilityChange.gridx = 3;
		gbc_jLabel_AASurvivalProbabilityChange.gridy = 0;
		jPanel_SurvivalProbabilityChange.add(
				jLabel_AASurvivalProbabilityChange,
				gbc_jLabel_AASurvivalProbabilityChange);

		jLabel_ASurvivalProbabilityChange = new JLabel("A");
		jLabel_ASurvivalProbabilityChange.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_ASurvivalProbabilityChange = new GridBagConstraints();
		gbc_jLabel_ASurvivalProbabilityChange.anchor = GridBagConstraints.WEST;
		gbc_jLabel_ASurvivalProbabilityChange.insets = new Insets(0, 0, 0, 7);
		gbc_jLabel_ASurvivalProbabilityChange.gridx = 4;
		gbc_jLabel_ASurvivalProbabilityChange.gridy = 0;
		jPanel_SurvivalProbabilityChange.add(jLabel_ASurvivalProbabilityChange,
				gbc_jLabel_ASurvivalProbabilityChange);

		jLabel_BBBSurvivalProbabilityChange = new JLabel("BBB");
		jLabel_BBBSurvivalProbabilityChange.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_BBBSurvivalProbabilityChange = new GridBagConstraints();
		gbc_jLabel_BBBSurvivalProbabilityChange.anchor = GridBagConstraints.WEST;
		gbc_jLabel_BBBSurvivalProbabilityChange.insets = new Insets(0, 0, 0, 7);
		gbc_jLabel_BBBSurvivalProbabilityChange.gridx = 5;
		gbc_jLabel_BBBSurvivalProbabilityChange.gridy = 0;
		jPanel_SurvivalProbabilityChange.add(
				jLabel_BBBSurvivalProbabilityChange,
				gbc_jLabel_BBBSurvivalProbabilityChange);

		jLabel_BBB3SurvivalProbabilityChange = new JLabel("BBB-");
		jLabel_BBB3SurvivalProbabilityChange.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_BBB3SurvivalProbabilityChange = new GridBagConstraints();
		gbc_jLabel_BBB3SurvivalProbabilityChange.anchor = GridBagConstraints.WEST;
		gbc_jLabel_BBB3SurvivalProbabilityChange.insets = new Insets(0, 0, 0, 7);
		gbc_jLabel_BBB3SurvivalProbabilityChange.gridx = 6;
		gbc_jLabel_BBB3SurvivalProbabilityChange.gridy = 0;
		jPanel_SurvivalProbabilityChange.add(
				jLabel_BBB3SurvivalProbabilityChange,
				gbc_jLabel_BBB3SurvivalProbabilityChange);

		jLabel_SurvivalProbabilityChangeT = new JLabel("P(x>T)");
		jLabel_SurvivalProbabilityChangeT.setFont(new Font("SansSerif",
				Font.BOLD, 10));
		GridBagConstraints gbc_jLabel_SurvivalProbabilityChangeT = new GridBagConstraints();
		gbc_jLabel_SurvivalProbabilityChangeT.anchor = GridBagConstraints.WEST;
		gbc_jLabel_SurvivalProbabilityChangeT.insets = new Insets(0, 0, 0, 7);
		gbc_jLabel_SurvivalProbabilityChangeT.gridx = 1;
		gbc_jLabel_SurvivalProbabilityChangeT.gridy = 1;
		jPanel_SurvivalProbabilityChange.add(jLabel_SurvivalProbabilityChangeT,
				gbc_jLabel_SurvivalProbabilityChangeT);

		jTextField_AAASurvivalProbabilityChangeT = new JTextField(5);
		jTextField_AAASurvivalProbabilityChangeT
				.setToolTipText("Change in Survival Probability of the AAA Tranche till After T");
		jTextField_AAASurvivalProbabilityChangeT.setText("0.005");
		jTextField_AAASurvivalProbabilityChangeT.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_AAASurvivalProbabilityChangeT = new GridBagConstraints();
		gbc_jTextField_AAASurvivalProbabilityChangeT.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_AAASurvivalProbabilityChangeT.anchor = GridBagConstraints.WEST;
		gbc_jTextField_AAASurvivalProbabilityChangeT.insets = new Insets(0, 0,
				0, 5);
		gbc_jTextField_AAASurvivalProbabilityChangeT.gridx = 2;
		gbc_jTextField_AAASurvivalProbabilityChangeT.gridy = 1;
		jPanel_SurvivalProbabilityChange.add(
				jTextField_AAASurvivalProbabilityChangeT,
				gbc_jTextField_AAASurvivalProbabilityChangeT);

		jTextField_AASurvivalProbabilityChangeT = new JTextField(5);
		jTextField_AASurvivalProbabilityChangeT
				.setToolTipText("Change in Survival Probability of the AA Tranche till After T");
		jTextField_AASurvivalProbabilityChangeT.setText("0.01");
		jTextField_AASurvivalProbabilityChangeT.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_AASurvivalProbabilityChangeT = new GridBagConstraints();
		gbc_jTextField_AASurvivalProbabilityChangeT.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_AASurvivalProbabilityChangeT.anchor = GridBagConstraints.WEST;
		gbc_jTextField_AASurvivalProbabilityChangeT.insets = new Insets(0, 0,
				0, 5);
		gbc_jTextField_AASurvivalProbabilityChangeT.gridx = 3;
		gbc_jTextField_AASurvivalProbabilityChangeT.gridy = 1;
		jPanel_SurvivalProbabilityChange.add(
				jTextField_AASurvivalProbabilityChangeT,
				gbc_jTextField_AASurvivalProbabilityChangeT);

		jTextField_ASurvivalProbabilityChangeT = new JTextField(5);
		jTextField_ASurvivalProbabilityChangeT
				.setToolTipText("Change in Survival Probability of the A Tranche till After T");
		jTextField_ASurvivalProbabilityChangeT.setText("0.02");
		jTextField_ASurvivalProbabilityChangeT.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_ASurvivalProbabilityChangeT = new GridBagConstraints();
		gbc_jTextField_ASurvivalProbabilityChangeT.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_ASurvivalProbabilityChangeT.anchor = GridBagConstraints.WEST;
		gbc_jTextField_ASurvivalProbabilityChangeT.insets = new Insets(0, 0, 0,
				5);
		gbc_jTextField_ASurvivalProbabilityChangeT.gridx = 4;
		gbc_jTextField_ASurvivalProbabilityChangeT.gridy = 1;
		jPanel_SurvivalProbabilityChange.add(
				jTextField_ASurvivalProbabilityChangeT,
				gbc_jTextField_ASurvivalProbabilityChangeT);

		jTextField_BBBSurvivalProbabilityChangeT = new JTextField(5);
		jTextField_BBBSurvivalProbabilityChangeT
				.setToolTipText("Change in Survival Probability of the BBB Tranche till After T");
		jTextField_BBBSurvivalProbabilityChangeT.setText("0.01");
		jTextField_BBBSurvivalProbabilityChangeT.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_BBBSurvivalProbabilityChangeT = new GridBagConstraints();
		gbc_jTextField_BBBSurvivalProbabilityChangeT.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_BBBSurvivalProbabilityChangeT.anchor = GridBagConstraints.WEST;
		gbc_jTextField_BBBSurvivalProbabilityChangeT.insets = new Insets(0, 0,
				0, 5);
		gbc_jTextField_BBBSurvivalProbabilityChangeT.gridx = 5;
		gbc_jTextField_BBBSurvivalProbabilityChangeT.gridy = 1;
		jPanel_SurvivalProbabilityChange.add(
				jTextField_BBBSurvivalProbabilityChangeT,
				gbc_jTextField_BBBSurvivalProbabilityChangeT);

		jTextField_BBB3SurvivalProbabilityChangeT = new JTextField(5);
		jTextField_BBB3SurvivalProbabilityChangeT
				.setToolTipText("Change in Survival Probability of the BBB- Tranche till After T");
		jTextField_BBB3SurvivalProbabilityChangeT.setText("0.02");
		jTextField_BBB3SurvivalProbabilityChangeT.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_BBB3SurvivalProbabilityChangeT = new GridBagConstraints();
		gbc_jTextField_BBB3SurvivalProbabilityChangeT.insets = new Insets(0, 0,
				0, 0);
		gbc_jTextField_BBB3SurvivalProbabilityChangeT.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_BBB3SurvivalProbabilityChangeT.anchor = GridBagConstraints.WEST;
		gbc_jTextField_BBB3SurvivalProbabilityChangeT.gridx = 6;
		gbc_jTextField_BBB3SurvivalProbabilityChangeT.gridy = 1;
		jPanel_SurvivalProbabilityChange.add(
				jTextField_BBB3SurvivalProbabilityChangeT,
				gbc_jTextField_BBB3SurvivalProbabilityChangeT);

		label_4 = new JLabel("P(x>T-1)");
		label_4.setFont(new Font("SansSerif", Font.BOLD, 10));
		GridBagConstraints gbc_label_4 = new GridBagConstraints();
		gbc_label_4.anchor = GridBagConstraints.WEST;
		gbc_label_4.insets = new Insets(0, 0, 0, 7);
		gbc_label_4.gridx = 1;
		gbc_label_4.gridy = 2;
		jPanel_SurvivalProbabilityChange.add(label_4, gbc_label_4);

		textField_5 = new JTextField(5);
		textField_5
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		textField_5.setText("0.99");
		textField_5.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_textField_5 = new GridBagConstraints();
		gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_5.anchor = GridBagConstraints.WEST;
		gbc_textField_5.insets = new Insets(0, 0, 0, 5);
		gbc_textField_5.gridx = 2;
		gbc_textField_5.gridy = 2;
		jPanel_SurvivalProbabilityChange.add(textField_5, gbc_textField_5);

		textField_6 = new JTextField(5);
		textField_6
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		textField_6.setText("0.97");
		textField_6.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_textField_6 = new GridBagConstraints();
		gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_6.anchor = GridBagConstraints.WEST;
		gbc_textField_6.insets = new Insets(0, 0, 0, 5);
		gbc_textField_6.gridx = 3;
		gbc_textField_6.gridy = 2;
		jPanel_SurvivalProbabilityChange.add(textField_6, gbc_textField_6);

		textField_7 = new JTextField(5);
		textField_7
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		textField_7.setText("0.95");
		textField_7.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_textField_7 = new GridBagConstraints();
		gbc_textField_7.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_7.anchor = GridBagConstraints.WEST;
		gbc_textField_7.insets = new Insets(0, 0, 0, 5);
		gbc_textField_7.gridx = 4;
		gbc_textField_7.gridy = 2;
		jPanel_SurvivalProbabilityChange.add(textField_7, gbc_textField_7);

		textField_8 = new JTextField(5);
		textField_8
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		textField_8.setText("0.92");
		textField_8.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_textField_8 = new GridBagConstraints();
		gbc_textField_8.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_8.anchor = GridBagConstraints.WEST;
		gbc_textField_8.insets = new Insets(0, 0, 0, 5);
		gbc_textField_8.gridx = 5;
		gbc_textField_8.gridy = 2;
		jPanel_SurvivalProbabilityChange.add(textField_8, gbc_textField_8);

		textField_9 = new JTextField(5);
		textField_9
				.setToolTipText("Survival Probability of the AAA-Tranche till After T");
		textField_9.setText("0.87");
		textField_9.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_textField_9 = new GridBagConstraints();
		gbc_textField_9.insets = new Insets(0, 0, 0, 0);
		gbc_textField_9.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_9.anchor = GridBagConstraints.WEST;
		gbc_textField_9.gridx = 6;
		gbc_textField_9.gridy = 2;
		jPanel_SurvivalProbabilityChange.add(textField_9, gbc_textField_9);

		jPanel_EconomicVariables = new JPanel();
		jPanel_EconomicVariables.setBorder(new TitledBorder(null,
				"Risk Free Rate", TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		GridBagConstraints gbc_jPanel_EconomicVariables = new GridBagConstraints();
		gbc_jPanel_EconomicVariables.fill = GridBagConstraints.BOTH;
		gbc_jPanel_EconomicVariables.gridx = 0;
		gbc_jPanel_EconomicVariables.gridy = 3;
		jPanel_AgentParameters.add(jPanel_EconomicVariables,
				gbc_jPanel_EconomicVariables);
		GridBagLayout gbl_jPanel_EconomicVariables = new GridBagLayout();
		gbl_jPanel_EconomicVariables.columnWidths = new int[] { 49, 49, 49, 49 };
		gbl_jPanel_EconomicVariables.rowHeights = new int[] { 14, 14, 14, 14,
				14, 14, 14 };
		gbl_jPanel_EconomicVariables.columnWeights = new double[] { 0.0, 1.0,
				0.0, 1.0 };
		gbl_jPanel_EconomicVariables.rowWeights = new double[] { 0.0, 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0 };
		jPanel_EconomicVariables.setLayout(gbl_jPanel_EconomicVariables);

		JLabel_RiskFreeRateProcess = new JLabel("Risk Free Rate Process");
		JLabel_RiskFreeRateProcess.setFont(new Font("Serif", Font.BOLD, 9));
		GridBagConstraints gbc_JLabel_RiskFreeRateProcess = new GridBagConstraints();
		gbc_JLabel_RiskFreeRateProcess.fill = GridBagConstraints.BOTH;
		gbc_JLabel_RiskFreeRateProcess.insets = new Insets(14, 0, 0, 5);
		gbc_JLabel_RiskFreeRateProcess.gridx = 0;
		gbc_JLabel_RiskFreeRateProcess.gridy = 0;
		jPanel_EconomicVariables.add(JLabel_RiskFreeRateProcess,
				gbc_JLabel_RiskFreeRateProcess);

		jLabel_InitialValue = new JLabel("Initial Value");
		jLabel_InitialValue.setFont(new Font("SansSerif", Font.BOLD, 9));
		GridBagConstraints gbc_jLabel_InitialValue = new GridBagConstraints();
		gbc_jLabel_InitialValue.anchor = GridBagConstraints.WEST;
		gbc_jLabel_InitialValue.insets = new Insets(14, 0, 0, 5);
		gbc_jLabel_InitialValue.gridx = 2;
		gbc_jLabel_InitialValue.gridy = 0;
		jPanel_EconomicVariables.add(jLabel_InitialValue,
				gbc_jLabel_InitialValue);

		jTextField_InitialValue = new JTextField(7);
		jTextField_InitialValue
				.setToolTipText("enter the number of quaters over which the simulation will run");
		jTextField_InitialValue.setText("0.02");
		jTextField_InitialValue.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_InitialValue = new GridBagConstraints();
		gbc_jTextField_InitialValue.insets = new Insets(14, 0, 0, 0);
		gbc_jTextField_InitialValue.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_InitialValue.gridx = 3;
		gbc_jTextField_InitialValue.gridy = 0;
		jPanel_EconomicVariables.add(jTextField_InitialValue,
				gbc_jTextField_InitialValue);

		JLabel_Drift = new JLabel("Drift");
		GridBagConstraints gbc_JLabel_Drift = new GridBagConstraints();
		gbc_JLabel_Drift.anchor = GridBagConstraints.WEST;
		gbc_JLabel_Drift.insets = new Insets(0, 0, 0, 5);
		gbc_JLabel_Drift.gridx = 2;
		gbc_JLabel_Drift.gridy = 1;
		jPanel_EconomicVariables.add(JLabel_Drift, gbc_JLabel_Drift);
		JLabel_Drift.setFont(new Font("SansSerif", Font.BOLD, 9));

		jTextField_Drift = new JTextField(7);
		jTextField_Drift.setToolTipText("enter the mean drift per quarter");
		jTextField_Drift.setText("0.004");
		jTextField_Drift.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_Drift = new GridBagConstraints();
		gbc_jTextField_Drift.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_Drift.anchor = GridBagConstraints.WEST;
		gbc_jTextField_Drift.gridx = 3;
		gbc_jTextField_Drift.gridy = 1;
		jPanel_EconomicVariables.add(jTextField_Drift, gbc_jTextField_Drift);

		jLabel_CirAlpha = new JLabel("CIR Alpha");
		jLabel_CirAlpha.setFont(new Font("SansSerif", Font.BOLD, 9));
		GridBagConstraints gbc_jLabel_CirAlpha = new GridBagConstraints();
		gbc_jLabel_CirAlpha.anchor = GridBagConstraints.WEST;
		gbc_jLabel_CirAlpha.insets = new Insets(0, 0, 0, 5);
		gbc_jLabel_CirAlpha.gridx = 2;
		gbc_jLabel_CirAlpha.gridy = 2;
		jPanel_EconomicVariables.add(jLabel_CirAlpha, gbc_jLabel_CirAlpha);

		jTextField_CIRAlpha = new JTextField(7);
		jTextField_CIRAlpha.setToolTipText("enter the mean reversion rate");
		jTextField_CIRAlpha.setText("0.0354");
		jTextField_CIRAlpha.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_CIRAlpha = new GridBagConstraints();
		gbc_jTextField_CIRAlpha.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_CIRAlpha.gridx = 3;
		gbc_jTextField_CIRAlpha.gridy = 2;
		jPanel_EconomicVariables.add(jTextField_CIRAlpha,
				gbc_jTextField_CIRAlpha);

		jLabel_CirTheta = new JLabel("CIR Theta");
		jLabel_CirTheta.setFont(new Font("SansSerif", Font.BOLD, 9));
		GridBagConstraints gbc_jLabel_CirTheta = new GridBagConstraints();
		gbc_jLabel_CirTheta.anchor = GridBagConstraints.WEST;
		gbc_jLabel_CirTheta.insets = new Insets(0, 0, 0, 5);
		gbc_jLabel_CirTheta.gridx = 2;
		gbc_jLabel_CirTheta.gridy = 3;
		jPanel_EconomicVariables.add(jLabel_CirTheta, gbc_jLabel_CirTheta);

		jTextField_CIRTheta = new JTextField(7);
		jTextField_CIRTheta.setToolTipText("enter the long term mean");
		jTextField_CIRTheta.setText("0.0238");
		jTextField_CIRTheta.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_CIRTheta = new GridBagConstraints();
		gbc_jTextField_CIRTheta.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_CIRTheta.gridx = 3;
		gbc_jTextField_CIRTheta.gridy = 3;
		jPanel_EconomicVariables.add(jTextField_CIRTheta,
				gbc_jTextField_CIRTheta);

		jLabel_JumpIntensity = new JLabel("Jump Intensity (lamda)");
		jLabel_JumpIntensity.setFont(new Font("SansSerif", Font.BOLD, 9));
		GridBagConstraints gbc_jLabel_JumpIntensity = new GridBagConstraints();
		gbc_jLabel_JumpIntensity.anchor = GridBagConstraints.WEST;
		gbc_jLabel_JumpIntensity.insets = new Insets(0, 0, 0, 5);
		gbc_jLabel_JumpIntensity.gridx = 2;
		gbc_jLabel_JumpIntensity.gridy = 4;
		jPanel_EconomicVariables.add(jLabel_JumpIntensity,
				gbc_jLabel_JumpIntensity);

		jTextField_JumpIntensity = new JTextField(7);
		jTextField_JumpIntensity
				.setToolTipText("enter the rate of jumps per quaters");
		jTextField_JumpIntensity.setText("0.04");
		jTextField_JumpIntensity.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_JumpIntensity = new GridBagConstraints();
		gbc_jTextField_JumpIntensity.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_JumpIntensity.gridx = 3;
		gbc_jTextField_JumpIntensity.gridy = 4;
		jPanel_EconomicVariables.add(jTextField_JumpIntensity,
				gbc_jTextField_JumpIntensity);

		jLabel_MeanJumpSize = new JLabel("Mean Jump Size (mu)");
		jLabel_MeanJumpSize.setFont(new Font("SansSerif", Font.BOLD, 9));
		GridBagConstraints gbc_jLabel_MeanJumpSize = new GridBagConstraints();
		gbc_jLabel_MeanJumpSize.insets = new Insets(0, 0, 0, 5);
		gbc_jLabel_MeanJumpSize.anchor = GridBagConstraints.WEST;
		gbc_jLabel_MeanJumpSize.gridx = 2;
		gbc_jLabel_MeanJumpSize.gridy = 5;
		jPanel_EconomicVariables.add(jLabel_MeanJumpSize,
				gbc_jLabel_MeanJumpSize);

		jTextField_MeanJumpSize = new JTextField(7);
		jTextField_MeanJumpSize
				.setToolTipText("enter the mean size of jumps per quater");
		jTextField_MeanJumpSize.setText("0.014");
		jTextField_MeanJumpSize.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_MeanJumpSize = new GridBagConstraints();
		gbc_jTextField_MeanJumpSize.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_MeanJumpSize.gridx = 3;
		gbc_jTextField_MeanJumpSize.gridy = 5;
		jPanel_EconomicVariables.add(jTextField_MeanJumpSize,
				gbc_jTextField_MeanJumpSize);

		lblJumpSizeDistribution = new JLabel(
				"Jump Size Distribution Width (tau)");
		lblJumpSizeDistribution.setVerticalAlignment(SwingConstants.TOP);
		lblJumpSizeDistribution.setFont(new Font("Serif", Font.BOLD, 9));
		GridBagConstraints gbc_lblJumpSizeDistribution = new GridBagConstraints();
		gbc_lblJumpSizeDistribution.anchor = GridBagConstraints.WEST;
		gbc_lblJumpSizeDistribution.insets = new Insets(0, 0, 14, 5);
		gbc_lblJumpSizeDistribution.gridx = 0;
		gbc_lblJumpSizeDistribution.gridy = 6;
		jPanel_EconomicVariables.add(lblJumpSizeDistribution,
				gbc_lblJumpSizeDistribution);

		textField_Jump_Size_Distribution_Width = new JTextField(7);
		textField_Jump_Size_Distribution_Width
				.setToolTipText("enter the jump size distribution per quater");
		textField_Jump_Size_Distribution_Width.setText("0.18");
		textField_Jump_Size_Distribution_Width.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_textField_Jump_Size_Distribution_Width = new GridBagConstraints();
		gbc_textField_Jump_Size_Distribution_Width.insets = new Insets(0, 0,
				14, 5);
		gbc_textField_Jump_Size_Distribution_Width.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_Jump_Size_Distribution_Width.gridx = 1;
		gbc_textField_Jump_Size_Distribution_Width.gridy = 6;
		jPanel_EconomicVariables.add(textField_Jump_Size_Distribution_Width,
				gbc_textField_Jump_Size_Distribution_Width);

		jLabel_JumpCorrelation = new JLabel("Jump Correlation (rho)");
		jLabel_JumpCorrelation.setVerticalAlignment(SwingConstants.TOP);
		GridBagConstraints gbc_jLabel_JumpCorrelation = new GridBagConstraints();
		gbc_jLabel_JumpCorrelation.anchor = GridBagConstraints.WEST;
		gbc_jLabel_JumpCorrelation.insets = new Insets(0, 0, 14, 5);
		gbc_jLabel_JumpCorrelation.gridx = 2;
		gbc_jLabel_JumpCorrelation.gridy = 6;
		jPanel_EconomicVariables.add(jLabel_JumpCorrelation,
				gbc_jLabel_JumpCorrelation);
		jLabel_JumpCorrelation.setFont(new Font("Serif", Font.BOLD, 9));

		JTextField_JumpCorrelationRho = new JTextField(7);
		JTextField_JumpCorrelationRho.setFont(new Font("SansSerif", Font.PLAIN,
				9));
		JTextField_JumpCorrelationRho
				.setToolTipText("enter the correlation between successive jumps per quater");
		JTextField_JumpCorrelationRho.setText("0.1");
		GridBagConstraints gbc_JTextField_JumpCorrelationRho = new GridBagConstraints();
		gbc_JTextField_JumpCorrelationRho.fill = GridBagConstraints.BOTH;
		gbc_JTextField_JumpCorrelationRho.insets = new Insets(0, 0, 14, 0);
		gbc_JTextField_JumpCorrelationRho.anchor = GridBagConstraints.WEST;
		gbc_JTextField_JumpCorrelationRho.gridx = 3;
		gbc_JTextField_JumpCorrelationRho.gridy = 6;
		jPanel_EconomicVariables.add(JTextField_JumpCorrelationRho,
				gbc_JTextField_JumpCorrelationRho);

		jComboBox_StochasticProcess = new JComboBox();
		jComboBox_StochasticProcess
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		jComboBox_StochasticProcess.setModel(new DefaultComboBoxModel(
				new String[] { "Descrete Brownian Motion",
						"Contimuous Brownian Motion", "Cox-Ingesoll-Ross",
						"CIR with Stochastic Jumps",
						"Brownian Motion with Jumps",
						"Heston's Stochastic Volatility" }));
		jComboBox_StochasticProcess.setSelectedIndex(2);

		jComboBox_StochasticProcess.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = ((JComboBox) e.getSource()).getSelectedIndex();
				stochasticProcessTypeStrng = (String) ((JComboBox) e
						.getSource()).getSelectedItem();
				stochasticProcessTypeStringIndex = i;//

			}
		});

		GridBagConstraints gbc_jComboBox_StochasticProcess = new GridBagConstraints();
		gbc_jComboBox_StochasticProcess.fill = GridBagConstraints.HORIZONTAL;
		gbc_jComboBox_StochasticProcess.insets = new Insets(0, 0, 0, 5);
		gbc_jComboBox_StochasticProcess.gridx = 0;
		gbc_jComboBox_StochasticProcess.gridy = 1;
		jPanel_EconomicVariables.add(jComboBox_StochasticProcess,
				gbc_jComboBox_StochasticProcess);

		JLabel_StandardDeviation = new JLabel("Standard Deviation");
		JLabel_StandardDeviation.setFont(new Font("SansSerif", Font.BOLD, 9));
		GridBagConstraints gbc_JLabel_StandardDeviation = new GridBagConstraints();
		gbc_JLabel_StandardDeviation.anchor = GridBagConstraints.WEST;
		gbc_JLabel_StandardDeviation.insets = new Insets(0, 0, 0, 5);
		gbc_JLabel_StandardDeviation.gridx = 0;
		gbc_JLabel_StandardDeviation.gridy = 2;
		jPanel_EconomicVariables.add(JLabel_StandardDeviation,
				gbc_JLabel_StandardDeviation);

		textField_StandardDeviation = new JTextField(7);
		textField_StandardDeviation
				.setToolTipText("enter the number of quaters over which the simulation will run");
		textField_StandardDeviation.setText("0.015");
		textField_StandardDeviation
				.setFont(new Font("SansSerif", Font.PLAIN, 9));
		GridBagConstraints gbc_textField_StandardDeviation = new GridBagConstraints();
		gbc_textField_StandardDeviation.insets = new Insets(0, 0, 0, 5);
		gbc_textField_StandardDeviation.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_StandardDeviation.gridx = 1;
		gbc_textField_StandardDeviation.gridy = 2;
		jPanel_EconomicVariables.add(textField_StandardDeviation,
				gbc_textField_StandardDeviation);

		jLabel_HestonsLongTerm = new JLabel("Heston's Long Term Variance");
		jLabel_HestonsLongTerm.setFont(new Font("SansSerif", Font.BOLD, 9));
		GridBagConstraints gbc_jLabel_HestonsLongTerm = new GridBagConstraints();
		gbc_jLabel_HestonsLongTerm.anchor = GridBagConstraints.WEST;
		gbc_jLabel_HestonsLongTerm.insets = new Insets(0, 0, 0, 5);
		gbc_jLabel_HestonsLongTerm.gridx = 0;
		gbc_jLabel_HestonsLongTerm.gridy = 3;
		jPanel_EconomicVariables.add(jLabel_HestonsLongTerm,
				gbc_jLabel_HestonsLongTerm);

		textField_HestonsLongTermVariance = new JTextField(7);
		textField_HestonsLongTermVariance
				.setToolTipText("enter the number of quaters over which the simulation will run");
		textField_HestonsLongTermVariance.setText("0.075");
		textField_HestonsLongTermVariance.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_textField_HestonsLongTermVariance = new GridBagConstraints();
		gbc_textField_HestonsLongTermVariance.insets = new Insets(0, 0, 0, 5);
		gbc_textField_HestonsLongTermVariance.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_HestonsLongTermVariance.gridx = 1;
		gbc_textField_HestonsLongTermVariance.gridy = 3;
		jPanel_EconomicVariables.add(textField_HestonsLongTermVariance,
				gbc_textField_HestonsLongTermVariance);

		jLabel_HestonsVarianceVolatility = new JLabel(
				"Heston's Variance Volatility");
		jLabel_HestonsVarianceVolatility.setFont(new Font("SansSerif",
				Font.BOLD, 9));
		GridBagConstraints gbc_jLabel_HestonsVarianceVolatility = new GridBagConstraints();
		gbc_jLabel_HestonsVarianceVolatility.anchor = GridBagConstraints.WEST;
		gbc_jLabel_HestonsVarianceVolatility.insets = new Insets(0, 0, 0, 5);
		gbc_jLabel_HestonsVarianceVolatility.gridx = 0;
		gbc_jLabel_HestonsVarianceVolatility.gridy = 4;
		jPanel_EconomicVariables.add(jLabel_HestonsVarianceVolatility,
				gbc_jLabel_HestonsVarianceVolatility);

		jTextField_HestonVarianceVolatility = new JTextField(7);
		jTextField_HestonVarianceVolatility
				.setToolTipText("enter the number of quaters over which the simulation will run");
		jTextField_HestonVarianceVolatility.setText("0.0142");
		jTextField_HestonVarianceVolatility.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_HestonVarianceVolatility = new GridBagConstraints();
		gbc_jTextField_HestonVarianceVolatility.insets = new Insets(0, 0, 0, 5);
		gbc_jTextField_HestonVarianceVolatility.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_HestonVarianceVolatility.gridx = 1;
		gbc_jTextField_HestonVarianceVolatility.gridy = 4;
		jPanel_EconomicVariables.add(jTextField_HestonVarianceVolatility,
				gbc_jTextField_HestonVarianceVolatility);

		jLabel_HestonsMeanReversion = new JLabel("Heston's Mean Reversion Rate");
		jLabel_HestonsMeanReversion
				.setFont(new Font("SansSerif", Font.BOLD, 9));
		GridBagConstraints gbc_jLabel_HestonsMeanReversion = new GridBagConstraints();
		gbc_jLabel_HestonsMeanReversion.anchor = GridBagConstraints.WEST;
		gbc_jLabel_HestonsMeanReversion.insets = new Insets(0, 0, 0, 5);
		gbc_jLabel_HestonsMeanReversion.gridx = 0;
		gbc_jLabel_HestonsMeanReversion.gridy = 5;
		jPanel_EconomicVariables.add(jLabel_HestonsMeanReversion,
				gbc_jLabel_HestonsMeanReversion);

		jTextField_HestonsMeanReversion = new JTextField(7);
		jTextField_HestonsMeanReversion
				.setToolTipText("enter the number of quaters over which the simulation will run");
		jTextField_HestonsMeanReversion.setText("0.0542");
		jTextField_HestonsMeanReversion.setFont(new Font("SansSerif",
				Font.PLAIN, 9));
		GridBagConstraints gbc_jTextField_HestonsMeanReversion = new GridBagConstraints();
		gbc_jTextField_HestonsMeanReversion.insets = new Insets(0, 0, 0, 5);
		gbc_jTextField_HestonsMeanReversion.fill = GridBagConstraints.HORIZONTAL;
		gbc_jTextField_HestonsMeanReversion.gridx = 1;
		gbc_jTextField_HestonsMeanReversion.gridy = 5;
		jPanel_EconomicVariables.add(jTextField_HestonsMeanReversion,
				gbc_jTextField_HestonsMeanReversion);
		if (selectedIndex == 0) {
			homogeneousAgents = true;// note that the index of the
		} else {
			homogeneousAgents = false;
		}

		leverageChoiceListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton aButton = (AbstractButton) actionEvent
						.getSource();
				if (aButton == noLeverageButton) {
					leveragedTrades = false;
					cb_LeverageStartyear.setEnabled(false);
					cb_LeverageStartQutr.setEnabled(false);
					// JOptionPane.showMessageDialog(null,
					// "The simulation will run without leveraged borrowing "
					// + Boolean.valueOf(leveragedTrades), "",
					// JOptionPane.INFORMATION_MESSAGE);

				} else {
					cb_LeverageStartyear.setEnabled(true);
					cb_LeverageStartQutr.setEnabled(true);
					leveragedTrades = true;
					cb_LeverageStartyear.setSelectedItem(leverageStartYr[6]);
					cb_LeverageStartQutr.setSelectedItem(leverageStartQtr[4]);
					leverageStartyear = 2006;
					leverageStartQutr = 4;
					// JOptionPane
					// .showMessageDialog(
					// null,
					// "The simulation will run with leveraged borrowing"
					// + "\n"
					// +
					// "Plase ensure a start period for leveraged borrowing is defined "
					// + Boolean.valueOf(leveragedTrades),
					// "", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		};

		// Radio Buttons for the Selection of if the banks in the simulation
		// use Credit Risk Mitigation or do not use Credit Risk Mitigation
		crmChoiceListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton aButton = (AbstractButton) actionEvent
						.getSource();
				if (aButton == crmButton) {
					crm = true;
					leverageButton.setSelected(true);
					cb_LeverageStartyear.setEnabled(true);
					cb_LeverageStartQutr.setEnabled(true);
					leveragedTrades = true;
					cb_LeverageStartyear.setSelectedItem(leverageStartYr[6]);
					cb_LeverageStartQutr.setSelectedItem(leverageStartQtr[4]);
					leverageStartyear = 2006;
					leverageStartQutr = 4;
				} else {
					crm = false;
					noLeverageButton.setSelected(true);
					leveragedTrades = false;
					cb_LeverageStartyear.setEnabled(false);
					cb_LeverageStartQutr.setEnabled(false);
				}

			}
		};

		baselChoiceListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				AbstractButton aButton = (AbstractButton) actionEvent
						.getSource();
				if (aButton == baselIIButton) {
					baselII = true;
				} else {
					baselII = false;
				}

			}
		};
		GridBagLayout gbl_jPanel_RegRegimeAndEnvRules = new GridBagLayout();
		gbl_jPanel_RegRegimeAndEnvRules.columnWidths = new int[] { 210 };
		gbl_jPanel_RegRegimeAndEnvRules.rowHeights = new int[] { 777 };
		gbl_jPanel_RegRegimeAndEnvRules.columnWeights = new double[] { 0.0 };
		gbl_jPanel_RegRegimeAndEnvRules.rowWeights = new double[] { 0.0 };
		jPanel_RegRegimeAndEnvRules.setLayout(gbl_jPanel_RegRegimeAndEnvRules);

		jPanel_ModelEnviromentConfig = new JPanel();
		jPanel_ModelEnviromentConfig.setBounds(0, 0, 0, -51);
		GridBagLayout gbl_jPanel_ModelEnviromentConfig = new GridBagLayout();
		gbl_jPanel_ModelEnviromentConfig.columnWidths = new int[] { 210, 0 };
		gbl_jPanel_ModelEnviromentConfig.rowHeights = new int[] { 84, 84, 84,
				84, 119, 119, 0 };
		gbl_jPanel_ModelEnviromentConfig.columnWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		gbl_jPanel_ModelEnviromentConfig.rowWeights = new double[] { 0.0, 0.0,
				0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		jPanel_ModelEnviromentConfig
				.setLayout(gbl_jPanel_ModelEnviromentConfig);
		baselBox = new Box(BoxLayout.X_AXIS);
		baselBox.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Regulation Selection"));

		// jTabbedPane_parameters.setLayout(new GridLayout(3, 1, 7, 0));

		modelBox = new Box(BoxLayout.X_AXIS);
		modelBox.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Data Range Specification"));

		JPanel jPanel_ModelChoice_1 = new JPanel();
		jPanel_ModelChoice_1.setPreferredSize(new Dimension(217, 98));
		GridBagLayout gbl_jPanel_ModelChoice_1 = new GridBagLayout();
		gbl_jPanel_ModelChoice_1.columnWidths = new int[] { 196 };
		gbl_jPanel_ModelChoice_1.rowHeights = new int[] { 49, 49 };
		gbl_jPanel_ModelChoice_1.columnWeights = new double[] { 0.0 };
		gbl_jPanel_ModelChoice_1.rowWeights = new double[] { 0.0, 0.0 };
		jPanel_ModelChoice_1.setLayout(gbl_jPanel_ModelChoice_1);
		JPanel modelSelect = new JPanel();
		GridBagConstraints gbc_modelSelect = new GridBagConstraints();
		gbc_modelSelect.anchor = GridBagConstraints.WEST;
		gbc_modelSelect.fill = GridBagConstraints.BOTH;
		gbc_modelSelect.insets = new Insets(0, 0, 0, 28);
		gbc_modelSelect.gridx = 0;
		gbc_modelSelect.gridy = 0;
		jPanel_ModelChoice_1.add(modelSelect, gbc_modelSelect);
		GridBagLayout gbl_modelSelect = new GridBagLayout();
		gbl_modelSelect.columnWidths = new int[] { 140 };
		gbl_modelSelect.rowHeights = new int[] { 14, 14 };
		gbl_modelSelect.columnWeights = new double[] { 0.0 };
		gbl_modelSelect.rowWeights = new double[] { 0.0, 0.0 };
		modelSelect.setLayout(gbl_modelSelect);

		JLabel lblSelectModel = new JLabel("Select Model");
		lblSelectModel.setFont(new Font("SansSerif", Font.BOLD, 11));
		GridBagConstraints gbc_lblSelectModel = new GridBagConstraints();
		gbc_lblSelectModel.fill = GridBagConstraints.BOTH;
		gbc_lblSelectModel.insets = new Insets(0, 0, 5, 0);
		gbc_lblSelectModel.gridx = 0;
		gbc_lblSelectModel.gridy = 0;
		modelSelect.add(lblSelectModel, gbc_lblSelectModel);
		JComboBox comboBoxModelChoice = new JComboBox();
		comboBoxModelChoice.setFont(new Font("SansSerif", Font.PLAIN, 11));
		comboBoxModelChoice.setModel(new DefaultComboBoxModel(new String[] {
				"Select", "Default", "Real Time" }));
		// comboBoxModelChoice.addActionListener(modelChoiceListener);
		GridBagConstraints gbc_comboBoxModelChoice = new GridBagConstraints();
		gbc_comboBoxModelChoice.fill = GridBagConstraints.BOTH;
		gbc_comboBoxModelChoice.gridx = 0;
		gbc_comboBoxModelChoice.gridy = 1;
		modelSelect.add(comboBoxModelChoice, gbc_comboBoxModelChoice);
		// ActionListener modelChoiceListener = new ActionListener() {
		comboBoxModelChoice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int i = ((JComboBox) actionEvent.getSource())
						.getSelectedIndex();

				if (i == 0) {

				} else if (i == 1) {
					startYear.setSelectedItem(startYr[6]);
					startQuater.setSelectedItem(startQtr[1]);
					startYear.setEnabled(false);
					startQuater.setEnabled(false);
					volThresholdTF.setText("9.4");
					haircutTF.setText("1.0");
					numQuatersTF.setText("7");
					numOfQuarters = 7;
					vThreshold = 9.4;
					haircut = 1.0;
					startyear = 2006;
					defaultModel = true;
					// JOptionPane
					// .showMessageDialog(
					// null,
					// "Simulation will use 2006Q1 to 2007Q3 data: "
					// + defaultModel
					// + "\n"
					// +
					// "Now please select whether to allow credit risk mitigation or not,"
					// + "\n"
					// +
					// "Then choose the regulatory landscape and the basis trading strategy to be employed by the banks",
					// "", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"The data required to run the realtime simulation is not "
											+ "currently available."
											+ "\n"
											+ "Please select the Default Model instead",
									"", JOptionPane.INFORMATION_MESSAGE);
					defaultModel = false;
					startYear.setEnabled(true);
					startQuater.setEnabled(true);
					numQuatersTF.setText("");
					volThresholdTF.setText("");
					haircutTF.setText("");
				}

			}
		});

		panel_PermitTradeUnwinds = new JPanel();
		GridBagConstraints gbc_panel_PermitTradeUnwinds = new GridBagConstraints();
		gbc_panel_PermitTradeUnwinds.anchor = GridBagConstraints.WEST;
		gbc_panel_PermitTradeUnwinds.fill = GridBagConstraints.BOTH;
		gbc_panel_PermitTradeUnwinds.gridx = 0;
		gbc_panel_PermitTradeUnwinds.gridy = 1;
		jPanel_ModelChoice_1.add(panel_PermitTradeUnwinds,
				gbc_panel_PermitTradeUnwinds);
		GridBagLayout gbl_panel_PermitTradeUnwinds = new GridBagLayout();
		gbl_panel_PermitTradeUnwinds.columnWidths = new int[] { 140 };
		gbl_panel_PermitTradeUnwinds.rowHeights = new int[] { 14, 14 };
		panel_PermitTradeUnwinds.setLayout(gbl_panel_PermitTradeUnwinds);

		lblAllowTradePosition = new JLabel("Allow Trade Position Unwinds?");
		lblAllowTradePosition.setFont(new Font("SansSerif", Font.BOLD, 11));
		GridBagConstraints gbc_lblAllowTradePosition = new GridBagConstraints();
		gbc_lblAllowTradePosition.fill = GridBagConstraints.BOTH;
		gbc_lblAllowTradePosition.gridx = 0;
		gbc_lblAllowTradePosition.gridy = 0;
		panel_PermitTradeUnwinds.add(lblAllowTradePosition,
				gbc_lblAllowTradePosition);

		comboBox_PermitTradeUnwinds = new JComboBox();
		comboBox_PermitTradeUnwinds.setFont(new Font("SansSerif", Font.PLAIN,
				11));
		GridBagConstraints gbc_comboBox_PermitTradeUnwinds = new GridBagConstraints();
		gbc_comboBox_PermitTradeUnwinds.fill = GridBagConstraints.BOTH;
		gbc_comboBox_PermitTradeUnwinds.gridx = 0;
		gbc_comboBox_PermitTradeUnwinds.gridy = 1;
		comboBox_PermitTradeUnwinds.setModel(new DefaultComboBoxModel(
				new String[] { "Select", "False", "True" }));
		panel_PermitTradeUnwinds.add(comboBox_PermitTradeUnwinds,
				gbc_comboBox_PermitTradeUnwinds);
		// panel_PermitTradeUnwinds.add(comboBox_PermitTradeUnwinds);
		// ActionListener modelChoiceListener = new ActionListener() {
		comboBox_PermitTradeUnwinds.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int i = ((JComboBox) actionEvent.getSource())
						.getSelectedIndex();

				if (i == 0) {

				} else if (i == 1) {
					tradePositionUnwindsPermited = false;
				} else {
					tradePositionUnwindsPermited = true;
				}

			}
		});

		modelBox.add(jPanel_ModelChoice_1);

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF JRADIO
		// BUTTONS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<ADD COMPONENTS TO CONFIGURATION
		// PANEL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		GridBagConstraints gbc_modelBox = new GridBagConstraints();
		gbc_modelBox.fill = GridBagConstraints.BOTH;
		gbc_modelBox.insets = new Insets(0, 0, 5, 0);
		gbc_modelBox.gridx = 0;
		gbc_modelBox.gridy = 0;
		jPanel_ModelEnviromentConfig.add(modelBox, gbc_modelBox);

		banksChoiceBox = new Box(BoxLayout.X_AXIS);
		banksChoiceBox
				.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(),
						"Modelled Banks Selection"));

		MBSDataChoiceBox = new Box(BoxLayout.X_AXIS);
		MBSDataChoiceBox
				.setBorder(BorderFactory.createTitledBorder(
						BorderFactory.createEtchedBorder(),
						"Model MBS Type Selection"));

		JPanel jPanel_MBSModelChoice = new JPanel();
		jPanel_MBSModelChoice.setPreferredSize(new Dimension(217, 49));
		GridBagLayout gbl_jPanel_MBSModelChoice = new GridBagLayout();
		gbl_jPanel_MBSModelChoice.columnWidths = new int[] { 196 };
		gbl_jPanel_MBSModelChoice.rowHeights = new int[] { 49 };
		gbl_jPanel_MBSModelChoice.columnWeights = new double[] { 0.0 };
		gbl_jPanel_MBSModelChoice.rowWeights = new double[] { 0.0 };
		jPanel_MBSModelChoice.setLayout(gbl_jPanel_MBSModelChoice);
		MBSDataChoiceBox.add(jPanel_MBSModelChoice);
		JPanel mbsModelDataSelect = new JPanel();
		GridBagLayout gbl_mbsModelDataSelect = new GridBagLayout();
		gbl_mbsModelDataSelect.columnWidths = new int[] { 140 };
		gbl_mbsModelDataSelect.rowHeights = new int[] { 14, 14 };
		gbl_mbsModelDataSelect.columnWeights = new double[] { 0.0 };
		gbl_mbsModelDataSelect.rowWeights = new double[] { 0.0, 0.0 };
		mbsModelDataSelect.setLayout(gbl_mbsModelDataSelect);

		JLabel lblNewLabelMBSDataTypeSelection = new JLabel("Type of MBS Used");
		lblNewLabelMBSDataTypeSelection.setFont(new Font("SansSerif",
				Font.BOLD, 11));
		lblNewLabelMBSDataTypeSelection
				.setToolTipText("Select the type of MBS on banks balance sheets to use in modeling");
		GridBagConstraints gbc_lblNewLabelMBSDataTypeSelection = new GridBagConstraints();
		gbc_lblNewLabelMBSDataTypeSelection.fill = GridBagConstraints.BOTH;
		gbc_lblNewLabelMBSDataTypeSelection.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabelMBSDataTypeSelection.gridx = 0;
		gbc_lblNewLabelMBSDataTypeSelection.gridy = 0;
		mbsModelDataSelect.add(lblNewLabelMBSDataTypeSelection,
				gbc_lblNewLabelMBSDataTypeSelection);
		GridBagConstraints gbc_mbsModelDataSelect = new GridBagConstraints();
		gbc_mbsModelDataSelect.insets = new Insets(0, 0, 0, 28);
		gbc_mbsModelDataSelect.fill = GridBagConstraints.BOTH;
		gbc_mbsModelDataSelect.gridx = 0;
		gbc_mbsModelDataSelect.gridy = 0;
		jPanel_MBSModelChoice.add(mbsModelDataSelect, gbc_mbsModelDataSelect);

		JComboBox comboBoxMBSDataSelection = new JComboBox();
		comboBoxMBSDataSelection.setFont(new Font("SansSerif", Font.PLAIN, 11));
		comboBoxMBSDataSelection.setModel(new DefaultComboBoxModel(
				new String[] { "Select", "Structured MBS", "All MBS" }));
		comboBoxMBSDataSelection.addActionListener(bankRMBSDataChoiceListener);
		GridBagConstraints gbc_comboBoxMBSDataSelection = new GridBagConstraints();
		gbc_comboBoxMBSDataSelection.fill = GridBagConstraints.BOTH;
		gbc_comboBoxMBSDataSelection.gridx = 0;
		gbc_comboBoxMBSDataSelection.gridy = 1;
		mbsModelDataSelect.add(comboBoxMBSDataSelection,
				gbc_comboBoxMBSDataSelection);
		comboBoxMBSDataSelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				int i = ((JComboBox) actionEvent.getSource())
						.getSelectedIndex();

				if (i == 0) {

				} else if (i == 1) {
					dataAllRMBS = false;
					// JOptionPane
					// .showMessageDialog(
					// null,
					// "The Selected simulation models the Banks' "
					// + "on balancesheet holdings of Structured MBS "
					// + dataAllRMBS, "",
					// JOptionPane.INFORMATION_MESSAGE);
				} else {
					dataAllRMBS = true;
					// JOptionPane.showMessageDialog(null,
					// "The Selected simulation models the Banks' "
					// + "on balancesheet holdings of MBS "
					// + dataAllRMBS, "",
					// JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		GridBagConstraints gbc_MBSDataChoiceBox = new GridBagConstraints();
		gbc_MBSDataChoiceBox.fill = GridBagConstraints.BOTH;
		gbc_MBSDataChoiceBox.insets = new Insets(0, 0, 5, 0);
		gbc_MBSDataChoiceBox.gridx = 0;
		gbc_MBSDataChoiceBox.gridy = 1;
		jPanel_ModelEnviromentConfig
				.add(MBSDataChoiceBox, gbc_MBSDataChoiceBox);

		JPanel jPanel_ModelBankChoice = new JPanel();
		jPanel_ModelBankChoice.setPreferredSize(new Dimension(217, 49));
		GridBagLayout gbl_jPanel_ModelBankChoice = new GridBagLayout();
		gbl_jPanel_ModelBankChoice.columnWidths = new int[] { 196, 0 };
		gbl_jPanel_ModelBankChoice.rowHeights = new int[] { 49, 0 };
		gbl_jPanel_ModelBankChoice.columnWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		gbl_jPanel_ModelBankChoice.rowWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		jPanel_ModelBankChoice.setLayout(gbl_jPanel_ModelBankChoice);
		banksChoiceBox.add(jPanel_ModelBankChoice);
		JPanel modelBankSelect = new JPanel();
		GridBagLayout gbl_modelBankSelect = new GridBagLayout();
		gbl_modelBankSelect.columnWidths = new int[] { 140 };
		gbl_modelBankSelect.rowHeights = new int[] { 14, 14 };
		gbl_modelBankSelect.columnWeights = new double[] { 0.0 };
		gbl_modelBankSelect.rowWeights = new double[] { 0.0, 0.0 };
		modelBankSelect.setLayout(gbl_modelBankSelect);
		modelledBanksSelection = new JComboBox();
		modelledBanksSelection.setModel(new DefaultComboBoxModel(new String[] {
				"select", "Top 12 Banks vs Others" }));
		modelledBanksSelection.setSelectedItem(bankString[0]);

		modelledBanksSelection.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				coreBanks = ((JComboBox) e.getSource()).getSelectedIndex();
				switch (coreBanks) {
				case 0:
					break;
				case 1:
					// JOptionPane.showMessageDialog(null,
					// "The Selected simulation models the 26 Core Banks with the "
					// + "largest activity between 2006-2009: "
					// + coreBanks, "",
					// JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			}
		});// end modelledBanksSelection actionlistener setup

		modelledBanksSelectionLabel = new JLabel("Banks to be Included");
		modelledBanksSelectionLabel
				.setFont(new Font("SansSerif", Font.BOLD, 11));
		GridBagConstraints gbc_modelledBanksSelectionLabel = new GridBagConstraints();
		gbc_modelledBanksSelectionLabel.fill = GridBagConstraints.BOTH;
		gbc_modelledBanksSelectionLabel.insets = new Insets(0, 0, 5, 0);
		gbc_modelledBanksSelectionLabel.gridx = 0;
		gbc_modelledBanksSelectionLabel.gridy = 0;
		modelBankSelect.add(modelledBanksSelectionLabel,
				gbc_modelledBanksSelectionLabel);
		GridBagConstraints gbc_modelledBanksSelection = new GridBagConstraints();
		gbc_modelledBanksSelection.fill = GridBagConstraints.BOTH;
		gbc_modelledBanksSelection.gridx = 0;
		gbc_modelledBanksSelection.gridy = 1;
		modelBankSelect.add(modelledBanksSelection, gbc_modelledBanksSelection);
		GridBagConstraints gbc_modelBankSelect = new GridBagConstraints();
		gbc_modelBankSelect.fill = GridBagConstraints.BOTH;
		gbc_modelBankSelect.gridx = 0;
		gbc_modelBankSelect.gridy = 0;
		jPanel_ModelBankChoice.add(modelBankSelect, gbc_modelBankSelect);
		GridBagConstraints gbc_banksChoiceBox = new GridBagConstraints();
		gbc_banksChoiceBox.fill = GridBagConstraints.BOTH;
		gbc_banksChoiceBox.insets = new Insets(0, 0, 5, 0);
		gbc_banksChoiceBox.gridx = 0;
		gbc_banksChoiceBox.gridy = 2;
		jPanel_ModelEnviromentConfig.add(banksChoiceBox, gbc_banksChoiceBox);//

		JPanel jPanel_BaselChoice = new JPanel();
		jPanel_BaselChoice.setPreferredSize(new Dimension(217, 49));
		GridBagLayout gbl_jPanel_BaselChoice = new GridBagLayout();
		gbl_jPanel_BaselChoice.columnWidths = new int[] { 196, 0 };
		gbl_jPanel_BaselChoice.rowHeights = new int[] { 49, 0 };
		gbl_jPanel_BaselChoice.columnWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		gbl_jPanel_BaselChoice.rowWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		jPanel_BaselChoice.setLayout(gbl_jPanel_BaselChoice);
		baselBox.add(jPanel_BaselChoice);
		JPanel baselSelect = new JPanel();
		GridBagLayout gbl_baselSelect = new GridBagLayout();
		gbl_baselSelect.columnWidths = new int[] { 49, 77, 49, 0 };
		gbl_baselSelect.rowHeights = new int[] { 21, 21, 0 };
		gbl_baselSelect.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_baselSelect.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		baselSelect.setLayout(gbl_baselSelect);

		// Radio Buttons for the Selection of Basel I vs Basel II model
		// simulation
		// Note basel I means only one couterparty risk weight
		// Basel II on the other hand means two counterparty risk weights
		// AAA-enty and AAA-rated assets
		// under ownership of the CDO trust/SPV.
		baselIButton = new JRadioButton();
		bgBasel.add(baselIButton);

		baselIButton.addActionListener(baselChoiceListener);
		JLabel baselIlabel = new JLabel(basel[0]);
		baselIlabel.setFont(new Font("Serif", Font.BOLD, 11));
		GridBagConstraints gbc_baselIlabel = new GridBagConstraints();
		gbc_baselIlabel.fill = GridBagConstraints.BOTH;
		gbc_baselIlabel.insets = new Insets(0, 0, 5, 5);
		gbc_baselIlabel.gridx = 0;
		gbc_baselIlabel.gridy = 0;
		baselSelect.add(baselIlabel, gbc_baselIlabel);
		GridBagConstraints gbc_baselIButton = new GridBagConstraints();
		gbc_baselIButton.fill = GridBagConstraints.BOTH;
		gbc_baselIButton.insets = new Insets(0, 0, 5, 0);
		gbc_baselIButton.gridx = 2;
		gbc_baselIButton.gridy = 0;
		baselSelect.add(baselIButton, gbc_baselIButton);
		JLabel baselIIlabel = new JLabel(basel[1]);
		baselIIlabel.setFont(new Font("Serif", Font.BOLD, 11));
		GridBagConstraints gbc_baselIIlabel = new GridBagConstraints();
		gbc_baselIIlabel.fill = GridBagConstraints.BOTH;
		gbc_baselIIlabel.insets = new Insets(0, 0, 0, 5);
		gbc_baselIIlabel.gridx = 0;
		gbc_baselIIlabel.gridy = 1;
		baselSelect.add(baselIIlabel, gbc_baselIIlabel);
		GridBagConstraints gbc_baselSelect = new GridBagConstraints();
		gbc_baselSelect.fill = GridBagConstraints.BOTH;
		gbc_baselSelect.gridx = 0;
		gbc_baselSelect.gridy = 0;
		jPanel_BaselChoice.add(baselSelect, gbc_baselSelect);
		baselIIButton = new JRadioButton();
		// bgBasel.add(label);
		bgBasel.add(baselIIButton);
		baselIIButton.addActionListener(baselChoiceListener);
		GridBagConstraints gbc_baselIIButton = new GridBagConstraints();
		gbc_baselIIButton.fill = GridBagConstraints.BOTH;
		gbc_baselIIButton.gridx = 2;
		gbc_baselIIButton.gridy = 1;
		baselSelect.add(baselIIButton, gbc_baselIIButton);
		GridBagConstraints gbc_baselBox = new GridBagConstraints();
		gbc_baselBox.fill = GridBagConstraints.BOTH;
		gbc_baselBox.insets = new Insets(0, 0, 5, 0);
		gbc_baselBox.gridx = 0;
		gbc_baselBox.gridy = 3;
		jPanel_ModelEnviromentConfig.add(baselBox, gbc_baselBox);
		// modelledBanksSelection

		crmBox = new Box(0);
		crmBox.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(),
				"Crdiet Risk Mitigation Selection"));

		JPanel jPanel_CRMChoice = new JPanel();
		jPanel_CRMChoice.setPreferredSize(new Dimension(217, 49));
		GridBagLayout gbl_jPanel_CRMChoice = new GridBagLayout();
		gbl_jPanel_CRMChoice.columnWidths = new int[] { 196, 0 };
		gbl_jPanel_CRMChoice.rowHeights = new int[] { 49, 0 };
		gbl_jPanel_CRMChoice.columnWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		gbl_jPanel_CRMChoice.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		jPanel_CRMChoice.setLayout(gbl_jPanel_CRMChoice);
		crmBox.add(jPanel_CRMChoice);
		JPanel crmSelect = new JPanel();
		GridBagLayout gbl_crmSelect = new GridBagLayout();
		gbl_crmSelect.columnWidths = new int[] { 49, 49, 49, 0 };
		gbl_crmSelect.rowHeights = new int[] { 21, 21, 0 };
		gbl_crmSelect.columnWeights = new double[] { 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_crmSelect.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		crmSelect.setLayout(gbl_crmSelect);
		JLabel crmlabel = new JLabel(crmTxt[0]);
		crmlabel.setFont(new Font("Serif", Font.BOLD, 11));
		GridBagConstraints gbc_crmlabel = new GridBagConstraints();
		gbc_crmlabel.fill = GridBagConstraints.BOTH;
		gbc_crmlabel.insets = new Insets(0, 0, 5, 5);
		gbc_crmlabel.gridx = 0;
		gbc_crmlabel.gridy = 0;
		crmSelect.add(crmlabel, gbc_crmlabel);

		crmButton = new JRadioButton();
		crmButton.addActionListener(crmChoiceListener);
		bgCRM.add(crmButton);
		GridBagConstraints gbc_crmButton = new GridBagConstraints();
		gbc_crmButton.fill = GridBagConstraints.BOTH;
		gbc_crmButton.insets = new Insets(0, 0, 5, 0);
		gbc_crmButton.gridx = 2;
		gbc_crmButton.gridy = 0;
		crmSelect.add(crmButton, gbc_crmButton);
		JLabel noCRMlabel = new JLabel(crmTxt[1]);
		noCRMlabel.setFont(new Font("Serif", Font.BOLD, 11));
		GridBagConstraints gbc_noCRMlabel = new GridBagConstraints();
		gbc_noCRMlabel.fill = GridBagConstraints.BOTH;
		gbc_noCRMlabel.insets = new Insets(0, 0, 0, 5);
		gbc_noCRMlabel.gridx = 0;
		gbc_noCRMlabel.gridy = 1;
		crmSelect.add(noCRMlabel, gbc_noCRMlabel);
		GridBagConstraints gbc_crmSelect = new GridBagConstraints();
		gbc_crmSelect.fill = GridBagConstraints.BOTH;
		gbc_crmSelect.gridx = 0;
		gbc_crmSelect.gridy = 0;
		jPanel_CRMChoice.add(crmSelect, gbc_crmSelect);

		noCRMButton = new JRadioButton();
		noCRMButton.addActionListener(crmChoiceListener);

		bgCRM.add(noCRMButton);
		GridBagConstraints gbc_noCRMButton = new GridBagConstraints();
		gbc_noCRMButton.fill = GridBagConstraints.BOTH;
		gbc_noCRMButton.gridx = 2;
		gbc_noCRMButton.gridy = 1;
		crmSelect.add(noCRMButton, gbc_noCRMButton);
		GridBagConstraints gbc_crmBox = new GridBagConstraints();
		gbc_crmBox.fill = GridBagConstraints.BOTH;
		gbc_crmBox.insets = new Insets(0, 0, 5, 0);
		gbc_crmBox.gridx = 0;
		gbc_crmBox.gridy = 4;
		jPanel_ModelEnviromentConfig.add(crmBox, gbc_crmBox);

		JPanel jPanel_ModelPeriodChoice = new JPanel();
		Box periodBox = new Box(BoxLayout.Y_AXIS);
		periodBox.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createEtchedBorder(), "Model Period Selection"));
		GridBagLayout gbl_jPanel_ModelPeriodChoice = new GridBagLayout();
		gbl_jPanel_ModelPeriodChoice.columnWidths = new int[] { 98, 98, 0 };
		gbl_jPanel_ModelPeriodChoice.rowHeights = new int[] { 56, 56, 0 };
		gbl_jPanel_ModelPeriodChoice.columnWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_jPanel_ModelPeriodChoice.rowWeights = new double[] { 0.0, 0.0,
				Double.MIN_VALUE };
		jPanel_ModelPeriodChoice.setLayout(gbl_jPanel_ModelPeriodChoice);
		startYear = new JComboBox();
		startYear.setModel(new DefaultComboBoxModel(new String[] { "select",
				"2001", "2002", "2003", "2004", "2005", "2006", "2007", "2008",
				"2009" }));
		startQuater = new JComboBox();
		startQuater.setModel(new DefaultComboBoxModel(new String[] { "select",
				"1", "2", "3", "4 " }));
		startYear.setSelectedItem(startYr[0]);

		startYear.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = ((JComboBox) e.getSource()).getSelectedIndex();
				String str = startYr[i];
				if (i != 0) {
					startyear = Integer.parseInt(str);
					// JOptionPane.showMessageDialog(null,
					// "Selsected simulation start year is: " + startyear
					// + " with selection index " + i, "",
					// JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		startQuater.setSelectedItem(startQtr[0]);

		startQuater.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int i = ((JComboBox) e.getSource()).getSelectedIndex();
				String str = startQtr[i];
				if (i != 0) {
					startQutr = Integer.parseInt(str);
					// JOptionPane.showMessageDialog(null,
					// "Selsected simulation start quarter is: "
					// + startQutr + " with selection index " + i,
					// "", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});
		JLabel syLabel = new JLabel("Start Year and Quarter");
		syLabel.setFont(new Font("serif", Font.BOLD, 10));
		GridBagConstraints gbc_syLabel = new GridBagConstraints();
		gbc_syLabel.fill = GridBagConstraints.BOTH;
		gbc_syLabel.insets = new Insets(0, 0, 5, 5);
		gbc_syLabel.gridx = 0;
		gbc_syLabel.gridy = 0;
		jPanel_ModelPeriodChoice.add(syLabel, gbc_syLabel);
		JPanel periodSelect = new JPanel();
		periodSelect.setLayout(new GridLayout(2, 1, 7, 0));
		periodSelect.add(startYear);
		periodSelect.add(startQuater);
		GridBagConstraints gbc_periodSelect = new GridBagConstraints();
		gbc_periodSelect.fill = GridBagConstraints.BOTH;
		gbc_periodSelect.insets = new Insets(0, 0, 5, 0);
		gbc_periodSelect.gridx = 1;
		gbc_periodSelect.gridy = 0;
		jPanel_ModelPeriodChoice.add(periodSelect, gbc_periodSelect);

		JPanel quaterInputPanel = new JPanel();
		GridBagLayout gbl_quaterInputPanel = new GridBagLayout();
		gbl_quaterInputPanel.columnWidths = new int[] { 102, 0 };
		gbl_quaterInputPanel.rowHeights = new int[] { 28, 0 };
		gbl_quaterInputPanel.columnWeights = new double[] { 0.0,
				Double.MIN_VALUE };
		gbl_quaterInputPanel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		quaterInputPanel.setLayout(gbl_quaterInputPanel);
		GridBagConstraints gbc_quaterInputPanel = new GridBagConstraints();
		gbc_quaterInputPanel.fill = GridBagConstraints.BOTH;
		gbc_quaterInputPanel.insets = new Insets(0, 0, 0, 5);
		gbc_quaterInputPanel.gridx = 0;
		gbc_quaterInputPanel.gridy = 1;
		jPanel_ModelPeriodChoice.add(quaterInputPanel, gbc_quaterInputPanel);

		numQuatersLabel = new JLabel("Number of Quaters");
		numQuatersLabel.setFont(new Font("serif", Font.BOLD, 10));
		GridBagConstraints gbc_numQuatersLabel = new GridBagConstraints();
		gbc_numQuatersLabel.fill = GridBagConstraints.BOTH;
		gbc_numQuatersLabel.gridx = 0;
		gbc_numQuatersLabel.gridy = 0;
		quaterInputPanel.add(numQuatersLabel, gbc_numQuatersLabel);
		periodBox.add(jPanel_ModelPeriodChoice);
		numQuatersTF = new JTextField(7);
		GridBagConstraints gbc_numQuatersTF = new GridBagConstraints();
		gbc_numQuatersTF.anchor = GridBagConstraints.NORTH;
		gbc_numQuatersTF.gridx = 1;
		gbc_numQuatersTF.gridy = 1;
		jPanel_ModelPeriodChoice.add(numQuatersTF, gbc_numQuatersTF);
		numQuatersTF
				.setToolTipText("enter the number of quaters over which the simulation will run");
		numQuatersTF.setText("7");
		GridBagConstraints gbc_periodBox = new GridBagConstraints();
		gbc_periodBox.fill = GridBagConstraints.BOTH;
		gbc_periodBox.gridx = 0;
		gbc_periodBox.gridy = 5;
		jPanel_ModelEnviromentConfig.add(periodBox, gbc_periodBox);
		GridBagConstraints gbc_jPanel_ModelEnviromentConfig = new GridBagConstraints();
		gbc_jPanel_ModelEnviromentConfig.fill = GridBagConstraints.BOTH;
		gbc_jPanel_ModelEnviromentConfig.gridx = 0;
		gbc_jPanel_ModelEnviromentConfig.gridy = 0;
		jPanel_RegRegimeAndEnvRules.add(jPanel_ModelEnviromentConfig,
				gbc_jPanel_ModelEnviromentConfig);

		JPanel jPanel_LeverageButtonChoice = new JPanel();
		GridBagLayout layout_1 = new GridBagLayout();
		layout_1.rowHeights = new int[] { 14, 14 };
		layout_1.columnWidths = new int[] { 140 };
		jPanel_LeverageButtonChoice.setLayout(layout_1);
		jPanel_LeverageButtonChoice.setPreferredSize(new Dimension(147, 56));
		GridBagConstraints gbc_jPanel_LeverageButtonChoice = new GridBagConstraints();
		gbc_jPanel_LeverageButtonChoice.anchor = GridBagConstraints.NORTHWEST;
		gbc_jPanel_LeverageButtonChoice.gridx = 0;
		gbc_jPanel_LeverageButtonChoice.gridy = 0;

		JPanel jPanel_LeverageStartPeriodChoice = new JPanel();
		jPanel_LeverageStartPeriodChoice
				.setPreferredSize(new Dimension(168, 49));
		GridBagLayout gbl_jPanel_LeverageStartPeriodChoice = new GridBagLayout();
		gbl_jPanel_LeverageStartPeriodChoice.columnWidths = new int[] { 168 };
		gbl_jPanel_LeverageStartPeriodChoice.rowHeights = new int[] { 90, 40,
				28 };
		gbl_jPanel_LeverageStartPeriodChoice.columnWeights = new double[] { 0.0 };
		gbl_jPanel_LeverageStartPeriodChoice.rowWeights = new double[] { 0.0,
				0.0 };
		jPanel_LeverageStartPeriodChoice
				.setLayout(gbl_jPanel_LeverageStartPeriodChoice);
		GridBagConstraints gbc_jPanel_LeverageStartPeriodChoice = new GridBagConstraints();
		gbc_jPanel_LeverageStartPeriodChoice.anchor = GridBagConstraints.NORTHWEST;
		gbc_jPanel_LeverageStartPeriodChoice.gridx = 0;
		gbc_jPanel_LeverageStartPeriodChoice.gridy = 1;

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		jPanel_SimulationResults = new JPanel();
		application_main_panel.add(jPanel_SimulationResults,
				BorderLayout.CENTER);
		jPanel_SimulationResults.setBorder(new TitledBorder(null, "",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		jPanel_SimulationResults.setLayout(new CardLayout(0, 0));

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<BANK SIMULATION CHARTS PANEL
		// SETUP>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		tabbedPane_SimulationOutput = new JTabbedPane(JTabbedPane.BOTTOM);
		jPanel_SimulationResults.add(tabbedPane_SimulationOutput,
				"name_1370301253106934");
		tabbedPane_SimulationOutput.setMinimumSize(new Dimension(200, 300));
		tabbedPane_SimulationOutput.setMaximumSize(new Dimension(700, 1024));
		tabbedPane_SimulationOutput
				.setToolTipText("View of the agent environment ");
		tabbedPane_SimulationOutput
				.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		this.panel_AllBanksOutput = new JPanel();
		panel_AllBanksOutput.setPreferredSize(new Dimension(340, 210));
		panel_AllBanksOutput.setMinimumSize(new Dimension(200, 300));
		panel_AllBanksOutput.setMaximumSize(new Dimension(768, 1024));
		panel_AllBanksOutput.setBounds(new Rectangle(0, 0, 140, 140));
		tabbedPane_SimulationOutput.addTab(
				"All Banks",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_AllBanksOutput, "Simulated And FDIC Bank Data Output");
		panel_AllBanksOutput.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_RMBSAssetAccumulation = new ChartPanel(
				this.getAllBanksRMBSExposuresChart());
		panel_RMBSAssetAccumulation.setRangeZoomable(true);
		panel_RMBSAssetAccumulation.setPreferredSize(new Dimension(340, 210));
		panel_RMBSAssetAccumulation.setSize(new Dimension(340, 210));
		this.panel_RMBSAssetAccumulation.setLayout(new GridBagLayout());
		this.panel_RMBSAssetAccumulation
				.setBounds(new Rectangle(0, 0, 140, 140));
		panel_AllBanksOutput.add(panel_RMBSAssetAccumulation);

		this.panel_CDSCarryTradeIncomeAccumulation = new ChartPanel(
				this.getAllBanksAccumulatedBasisTradeIncomeChart());
		this.panel_CDSCarryTradeIncomeAccumulation
				.setLayout(new GridBagLayout());
		this.panel_CDSCarryTradeIncomeAccumulation.setBounds(new Rectangle(0,
				0, 140, 140));
		panel_AllBanksOutput.add(panel_CDSCarryTradeIncomeAccumulation);

		this.panel_PerQuarterCDSCarryTradeIncome = new ChartPanel(
				this.getAllBanksCurrentQtrBasisTradeIncomeChart());
		this.panel_PerQuarterCDSCarryTradeIncome.setLayout(new GridBagLayout());
		this.panel_PerQuarterCDSCarryTradeIncome.setBounds(new Rectangle(0, 0,
				140, 140));
		panel_AllBanksOutput.add(panel_PerQuarterCDSCarryTradeIncome);

		this.panel_RegulatroyLeverageRatio = new ChartPanel(
				this.getAllBanksRegulatoryCapitalLeverageTakingChart());
		this.panel_RegulatroyLeverageRatio.setLayout(new GridBagLayout());
		this.panel_RegulatroyLeverageRatio.setBounds(new Rectangle(0, 0, 140,
				140));
		// panel_AllBanksOutput.add(panel_RegulatroyLeverageRatio);

		this.panel_AllBanksAccumulatedCapitalSavings = new ChartPanel(
				this.getAllBanksAccumulatedCapitalSavingsChart());
		this.panel_AllBanksAccumulatedCapitalSavings
				.setLayout(new GridBagLayout());
		this.panel_AllBanksAccumulatedCapitalSavings.setBounds(new Rectangle(0,
				0, 140, 140));
		panel_AllBanksOutput.add(panel_AllBanksAccumulatedCapitalSavings);

		this.panel_Top5BanksOutput = new JPanel();
		tabbedPane_SimulationOutput.addTab(
				"Top5 Banks",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_Top5BanksOutput, "Simulated And FDIC Top5 Banks Data");
		panel_Top5BanksOutput.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_Top5RMBSAssetAccumulation = new ChartPanel(
				this.getTop5RMBSExposuresChart());
		this.panel_Top5RMBSAssetAccumulation.setLayout(new GridBagLayout());
		this.panel_Top5RMBSAssetAccumulation.setBounds(new Rectangle(0, 0, 140,
				140));
		panel_Top5BanksOutput.add(panel_Top5RMBSAssetAccumulation);

		this.panel_Top5CDSCarryTradeIncomeAccumulation = new ChartPanel(
				this.getTop5AccumulatedBasisTradeIncomeChart());
		this.panel_Top5CDSCarryTradeIncomeAccumulation
				.setLayout(new GridBagLayout());
		this.panel_Top5CDSCarryTradeIncomeAccumulation.setBounds(new Rectangle(
				0, 0, 140, 140));
		panel_Top5BanksOutput.add(panel_Top5CDSCarryTradeIncomeAccumulation);

		this.panel_Top5PerQuarterCDSCarryTradeIncome = new ChartPanel(
				this.getTop5CurrentQtrBasisTradeIncomeChart());
		this.panel_Top5PerQuarterCDSCarryTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_Top5PerQuarterCDSCarryTradeIncome.setBounds(new Rectangle(0,
				0, 140, 140));
		panel_Top5BanksOutput.add(panel_Top5PerQuarterCDSCarryTradeIncome);

		this.panel_Top5RegulatroyLeverageRatio = new ChartPanel(
				this.getTop5RegulatoryCapitalLeverageTakingChart());
		this.panel_Top5RegulatroyLeverageRatio.setLayout(new GridBagLayout());
		this.panel_Top5RegulatroyLeverageRatio.setBounds(new Rectangle(0, 0,
				140, 140));
		// panel_Top5BanksOutput.add(panel_Top5RegulatroyLeverageRatio);

		this.panel_Top5AccumulatedCapitalSavings = new ChartPanel(
				this.getTop5AccumulatedCapitalSavingsChart());
		this.panel_Top5AccumulatedCapitalSavings.setLayout(new GridBagLayout());
		this.panel_Top5AccumulatedCapitalSavings.setBounds(new Rectangle(0, 0,
				140, 140));
		panel_Top5BanksOutput.add(panel_Top5AccumulatedCapitalSavings);

		this.panel_Major12AndOtherBanksOutput = new JPanel();
		tabbedPane_SimulationOutput.addTab(
				"Major 12 and Other Banks",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_Major12AndOtherBanksOutput,
				"Simulated And FDIC Top5 Banks Data");
		panel_Major12AndOtherBanksOutput.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_Major12RMBSExposures = new ChartPanel(
				this.getMajor12RMBSExposuresChart());
		this.panel_Major12RMBSExposures.setLayout(new GridBagLayout());
		this.panel_Major12RMBSExposures
				.setBounds(new Rectangle(0, 0, 140, 140));
		panel_Major12AndOtherBanksOutput.add(panel_Major12RMBSExposures);

		this.panel_OtherBanksRMBSExposures = new ChartPanel(
				this.getOtherBanksRMBSExposuresChart());
		this.panel_OtherBanksRMBSExposures.setLayout(new GridBagLayout());
		this.panel_OtherBanksRMBSExposures.setBounds(new Rectangle(0, 0, 140,
				140));
		panel_Major12AndOtherBanksOutput.add(panel_OtherBanksRMBSExposures);

		this.panel_Major12CurrentQtrBasisTradeIncome = new ChartPanel(
				this.getMajor12CurrentQtrBasisTradeIncomeChart());
		this.panel_Major12CurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_Major12CurrentQtrBasisTradeIncome.setBounds(new Rectangle(0,
				0, 140, 140));
		panel_Major12AndOtherBanksOutput
				.add(panel_Major12CurrentQtrBasisTradeIncome);

		this.panel_OtherBanksCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getOtherBanksCurrentQtrBasisTradeIncomeChart());
		this.panel_OtherBanksCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_OtherBanksCurrentQtrBasisTradeIncome
				.setBounds(new Rectangle(0, 0, 140, 140));
		panel_Major12AndOtherBanksOutput
				.add(panel_OtherBanksCurrentQtrBasisTradeIncome);

		// <<<<<<<<<<<<<<<<<<<<<<<INDIVIDUAL BANKS>>>>>>>>>>>>>>>>>>>>>>>>
		this.panel_BOFABONYOutput = new JPanel();
		tabbedPane_SimulationOutput.addTab(
				"BofA and BoNY",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_BOFABONYOutput,
				"Simulated And FDIC Individual Banks Data");
		panel_BOFABONYOutput.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_BofARMBSExposures = new ChartPanel(
				this.getBofARMBSExposuresChart());
		this.panel_BofARMBSExposures.setLayout(new GridBagLayout());
		this.panel_BofARMBSExposures.setBounds(new Rectangle(0, 0, 140, 140));
		panel_BOFABONYOutput.add(panel_BofARMBSExposures);

		this.panel_BoNYRMBSExposures = new ChartPanel(
				this.getBoNYRMBSExposuresChart());
		this.panel_BoNYRMBSExposures.setLayout(new GridBagLayout());
		this.panel_BoNYRMBSExposures.setBounds(new Rectangle(0, 0, 140, 140));
		panel_BOFABONYOutput.add(panel_BoNYRMBSExposures);

		this.panel_BofACurrentQtrBasisTradeIncome = new ChartPanel(
				this.getBofACurrentQtrBasisTradeIncomeChart());
		this.panel_BofACurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_BofACurrentQtrBasisTradeIncome.setBounds(new Rectangle(0, 0,
				140, 140));
		panel_BOFABONYOutput.add(panel_BofACurrentQtrBasisTradeIncome);

		this.panel_BoNYCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getBoNYCurrentQtrBasisTradeIncomeChart());
		this.panel_BoNYCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_BoNYCurrentQtrBasisTradeIncome.setBounds(new Rectangle(0, 0,
				140, 140));
		panel_BOFABONYOutput.add(panel_BoNYCurrentQtrBasisTradeIncome);

		// keybank and citi
		this.panel_KeyBankAndCitiBankOutput = new JPanel();
		tabbedPane_SimulationOutput.addTab(
				"Keybank and Citibank",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_KeyBankAndCitiBankOutput,
				"Simulated And FDIC Individual Banks Data");
		panel_KeyBankAndCitiBankOutput.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_KeyBankRMBSExposures = new ChartPanel(
				this.getKeybankRMBSExposuresChart());
		this.panel_KeyBankRMBSExposures.setLayout(new GridBagLayout());
		this.panel_KeyBankRMBSExposures
				.setBounds(new Rectangle(0, 0, 140, 140));
		panel_KeyBankAndCitiBankOutput.add(panel_KeyBankRMBSExposures);

		this.panel_CitiBankRMBSExposures = new ChartPanel(
				this.getCitibankRMBSExposuresChart());
		this.panel_CitiBankRMBSExposures.setLayout(new GridBagLayout());
		this.panel_CitiBankRMBSExposures
				.setBounds(new Rectangle(0, 0, 140, 140));
		panel_KeyBankAndCitiBankOutput.add(panel_CitiBankRMBSExposures);

		this.panel_KeyBankCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getKeybankCurrentQtrBasisTradeIncomeChart());
		this.panel_KeyBankCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_KeyBankCurrentQtrBasisTradeIncome.setBounds(new Rectangle(0,
				0, 140, 140));
		panel_KeyBankAndCitiBankOutput
				.add(panel_KeyBankCurrentQtrBasisTradeIncome);

		this.panel_CitiBankCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getCitibankCurrentQtrBasisTradeIncomeChart());
		this.panel_CitiBankCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_CitiBankCurrentQtrBasisTradeIncome.setBounds(new Rectangle(
				0, 0, 140, 140));
		panel_KeyBankAndCitiBankOutput
				.add(panel_CitiBankCurrentQtrBasisTradeIncome);

		// Wells Fargo and HSBC
		this.panel_WellsAndHSBCOutput = new JPanel();
		tabbedPane_SimulationOutput.addTab(
				"Wells Fargo and HSBC",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_WellsAndHSBCOutput,
				"Simulated And FDIC Individual Banks Data");
		panel_WellsAndHSBCOutput.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_WellsRMBSExposures = new ChartPanel(
				this.getWellsFargoRMBSExposuresChart());
		this.panel_WellsRMBSExposures.setLayout(new GridBagLayout());
		this.panel_WellsRMBSExposures.setBounds(new Rectangle(0, 0, 140, 140));
		panel_WellsAndHSBCOutput.add(panel_WellsRMBSExposures);

		this.panel_HSBCRMBSExposures = new ChartPanel(
				this.getHSBCRMBSExposuresChart());
		this.panel_HSBCRMBSExposures.setLayout(new GridBagLayout());
		this.panel_HSBCRMBSExposures.setBounds(new Rectangle(0, 0, 140, 140));
		panel_WellsAndHSBCOutput.add(panel_HSBCRMBSExposures);

		this.panel_WellsCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getWellsFargoCurrentQtrBasisTradeIncomeChart());
		this.panel_WellsCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_WellsCurrentQtrBasisTradeIncome.setBounds(new Rectangle(0,
				0, 140, 140));
		panel_WellsAndHSBCOutput.add(panel_WellsCurrentQtrBasisTradeIncome);

		this.panel_HSBCCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getHSBCCurrentQtrBasisTradeIncomeChart());
		this.panel_HSBCCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_HSBCCurrentQtrBasisTradeIncome.setBounds(new Rectangle(0, 0,
				140, 140));
		panel_WellsAndHSBCOutput.add(panel_HSBCCurrentQtrBasisTradeIncome);

		// Merril Lynch and JP Morgan
		this.panel_MerrilAndJPMorganOutput = new JPanel();
		tabbedPane_SimulationOutput.addTab(
				"ML and JPM",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_MerrilAndJPMorganOutput,
				"Simulated And FDIC Individual Banks Data");
		panel_MerrilAndJPMorganOutput.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_MerrilLynchRMBSExposures = new ChartPanel(
				this.getMerrillRMBSExposuresChart());
		this.panel_MerrilLynchRMBSExposures.setLayout(new GridBagLayout());
		this.panel_MerrilLynchRMBSExposures.setBounds(new Rectangle(0, 0, 140,
				140));
		panel_MerrilAndJPMorganOutput.add(panel_MerrilLynchRMBSExposures);

		this.panel_JPMorganRMBSExposures = new ChartPanel(
				this.getJPMorganRMBSExposuresChart());
		this.panel_JPMorganRMBSExposures.setLayout(new GridBagLayout());
		this.panel_JPMorganRMBSExposures
				.setBounds(new Rectangle(0, 0, 140, 140));
		panel_MerrilAndJPMorganOutput.add(panel_JPMorganRMBSExposures);

		this.panel_MerrilLynchCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getMerrillCurrentQtrBasisTradeIncomeChart());
		this.panel_MerrilLynchCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_MerrilLynchCurrentQtrBasisTradeIncome
				.setBounds(new Rectangle(0, 0, 140, 140));
		panel_MerrilAndJPMorganOutput
				.add(panel_MerrilLynchCurrentQtrBasisTradeIncome);

		this.panel_JPMorganCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getJPMorganCurrentQtrBasisTradeIncomeChart());
		this.panel_JPMorganCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_JPMorganCurrentQtrBasisTradeIncome.setBounds(new Rectangle(
				0, 0, 140, 140));
		panel_MerrilAndJPMorganOutput
				.add(panel_JPMorganCurrentQtrBasisTradeIncome);

		// Goldman and Morgan Stanley
		this.panel_GoldmanAndMorganStanleyOutput = new JPanel();
		tabbedPane_SimulationOutput.addTab(
				"GS and MS",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_GoldmanAndMorganStanleyOutput,
				"Simulated And FDIC Individual Banks Data");
		panel_GoldmanAndMorganStanleyOutput
				.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_GoldmanRMBSExposures = new ChartPanel(
				this.getGoldmanRMBSExposuresChart());
		this.panel_GoldmanRMBSExposures.setLayout(new GridBagLayout());
		this.panel_GoldmanRMBSExposures
				.setBounds(new Rectangle(0, 0, 140, 140));
		panel_GoldmanAndMorganStanleyOutput.add(panel_GoldmanRMBSExposures);

		this.panel_MorganStanleyRMBSExposures = new ChartPanel(
				this.getMorganStanleyRMBSExposuresChart());
		this.panel_MorganStanleyRMBSExposures.setLayout(new GridBagLayout());
		this.panel_MorganStanleyRMBSExposures.setBounds(new Rectangle(0, 0,
				140, 140));
		panel_GoldmanAndMorganStanleyOutput
				.add(panel_MorganStanleyRMBSExposures);

		this.panel_GoldmanCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getGoldmanCurrentQtrBasisTradeIncomeChart());
		this.panel_GoldmanCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_GoldmanCurrentQtrBasisTradeIncome.setBounds(new Rectangle(0,
				0, 140, 140));
		panel_GoldmanAndMorganStanleyOutput
				.add(panel_GoldmanCurrentQtrBasisTradeIncome);

		this.panel_MorganStanleyCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getMorganStanleyCurrentQtrBasisTradeIncomeChart());
		this.panel_MorganStanleyCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_MorganStanleyCurrentQtrBasisTradeIncome
				.setBounds(new Rectangle(0, 0, 140, 140));
		panel_GoldmanAndMorganStanleyOutput
				.add(panel_MorganStanleyCurrentQtrBasisTradeIncome);

		// Wachovia and Deutsche
		this.panel_WachoviaAndDeutscheOutput = new JPanel();
		tabbedPane_SimulationOutput.addTab(
				"Wachovia and Deutsche",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graphs.gif")),
				panel_WachoviaAndDeutscheOutput,
				"Simulated And FDIC Individual Banks Data");
		panel_WachoviaAndDeutscheOutput.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_WachoviaRMBSExposures = new ChartPanel(
				this.getWachoviaRMBSExposuresChart());
		this.panel_WachoviaRMBSExposures.setLayout(new GridBagLayout());
		this.panel_WachoviaRMBSExposures
				.setBounds(new Rectangle(0, 0, 140, 140));
		panel_WachoviaAndDeutscheOutput.add(panel_WachoviaRMBSExposures);

		this.panel_DeutscheRMBSExposures = new ChartPanel(
				this.getDeutscheRMBSExposuresChart());
		this.panel_DeutscheRMBSExposures.setLayout(new GridBagLayout());
		this.panel_DeutscheRMBSExposures
				.setBounds(new Rectangle(0, 0, 140, 140));
		panel_WachoviaAndDeutscheOutput.add(panel_DeutscheRMBSExposures);

		this.panel_WachoviaCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getWachoviaCurrentQtrBasisTradeIncomeChart());
		this.panel_WachoviaCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_WachoviaCurrentQtrBasisTradeIncome.setBounds(new Rectangle(
				0, 0, 140, 140));
		panel_WachoviaAndDeutscheOutput
				.add(panel_WachoviaCurrentQtrBasisTradeIncome);

		this.panel_DeutscheCurrentQtrBasisTradeIncome = new ChartPanel(
				this.getDeutscheCurrentQtrBasisTradeIncomeChart());
		this.panel_DeutscheCurrentQtrBasisTradeIncome
				.setLayout(new GridBagLayout());
		this.panel_DeutscheCurrentQtrBasisTradeIncome.setBounds(new Rectangle(
				0, 0, 140, 140));
		panel_WachoviaAndDeutscheOutput
				.add(panel_DeutscheCurrentQtrBasisTradeIncome);

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF BANK SIMULATION CHARTS
		// PANELS SETUP>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CDS AND BOND
		// DATA>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		tabbedPane_BasisAndSpreads = new JTabbedPane(JTabbedPane.BOTTOM);
		jPanel_SimulationResults.add(tabbedPane_BasisAndSpreads,
				"name_1370301031970850");

		this.panel_DefaultCDSandBondSpread = new JPanel();
		tabbedPane_BasisAndSpreads.addTab(
				"CDS and Bond Spreads",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graph.gif")),
				panel_DefaultCDSandBondSpread,
				"HBX.HE and Normura Pricing Pipeline MBS Spreads");
		panel_DefaultCDSandBondSpread.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_CDSandBondSpread = new ChartPanel(
				this.getDefaultCDSandBondSpreadChart());
		this.panel_CDSandBondSpread.setLayout(new GridBagLayout());
		this.panel_CDSandBondSpread.setBounds(new Rectangle(0, 0, 140, 140));
		panel_DefaultCDSandBondSpread.add(panel_CDSandBondSpread);

		this.panel_DefaultCDSBasis = new JPanel();
		tabbedPane_BasisAndSpreads.addTab(
				"CDS Basis",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graph.gif")),
				panel_DefaultCDSBasis, "CDS Basis");
		panel_DefaultCDSBasis.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_CDSBasis = new ChartPanel(this.getDefaultCDSBasisChart());
		this.panel_CDSBasis.setLayout(new GridBagLayout());
		this.panel_CDSBasis.setBounds(new Rectangle(0, 0, 140, 140));
		panel_DefaultCDSBasis.add(panel_CDSBasis);

		this.panel_DefaultCDSPrices = new JPanel();
		tabbedPane_BasisAndSpreads.addTab(
				"CDS Price",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graph.gif")),
				panel_DefaultCDSPrices, "HBX.HE Prices");
		panel_DefaultCDSPrices.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_CDSPrice = new ChartPanel(this.getDefaultCDSPriceChart());
		this.panel_CDSPrice.setLayout(new GridBagLayout());
		this.panel_CDSPrice.setBounds(new Rectangle(0, 0, 140, 140));
		panel_DefaultCDSPrices.add(panel_CDSPrice);

		this.panel_DefaultLIBORRates = new JPanel();
		tabbedPane_BasisAndSpreads.addTab(
				"LIBOR Rates",
				new ImageIcon(CDSBasisTradeJASApplicationFrame.class
						.getResource("/jas/images/graph.gif")),
				panel_DefaultLIBORRates, "US$ LIBOR Rates");
		panel_DefaultLIBORRates.setLayout(new GridLayout(2, 2, 0, 0));

		this.panel_LIBORRates = new ChartPanel(this.getDefaultLIBORRATEChart());
		this.panel_LIBORRates.setLayout(new GridBagLayout());
		this.panel_LIBORRates.setBounds(new Rectangle(0, 0, 140, 140));
		panel_DefaultLIBORRates.add(panel_LIBORRates);
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF CDS AND BOND DATA CHARTS
		// PANELS SETUP>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CONFIGURATION PANEL
		// ACTIONLISTENERS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		// bgBasel.add(label);
		setContentPane(jContentPane);

	}

	private void createNewModel() {
		/**
		 * This method is used to create new models when the reload button is
		 * clicked The method: 1: creates a new model instance 2: applied the
		 * current CDSBasisTradeJASApplicationFrame to the model 3: adds the
		 * model to the JAS SimEngine
		 * 
		 */
		// TODO Auto-generated method stub
		model = new CDSBasisTradeModel();
		setModelFrame();
		eng.addModel(this.model);
		this.repaint();
	}

	public void setModelFrame() {
		model.frame = this;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<THE RESULTS
	// PANEL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<BUTTONS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private void setModelParameters() {

		param = new ModelParameters();
		param.setDefualtModel(defaultModel);
		param.setUnwindingOfPositions(tradePositionUnwindsPermited);
		param.setModelRMBSDataType(dataAllRMBS);
		param.setBanksSubset(coreBanks);
		param.setBasisMaximisationStrategy(maximumEarningMethod - 1);
		// note that the setBasisMaximisationStrategy() method invoked subtracts
		// the 1 to have all values starting from 0. I
		// can't remember why I did it this way but it made sense back in 2009
		param.setCreditRiskMitigation(crm);
		param.setRegulatoryRegime(baselII);
		param.setVolatilityThreshold(vThreshold);
		param.setNumberOfSimulationQuarters(numOfQuarters);
		param.setSimulationStartYear(startyear);
		param.setSimulationStartQuarter(startQutr);
		param.setLeverageHaircut(haircut);
		param.setLeveragedTrading(leveragedTrades);
		param.setLeverageStartYear(leverageStartyear);
		param.setLeverageStartQuarter(leverageStartQutr);
		param.setMarkToMarketPrices(cdsPriceChangeDataChoiceSelection);

		param.setTradePositionUnwindsPermited(tradePositionUnwindsPermited);
		param.setCdsPriceChangeDataChoiceSelection(cdsPriceChangeDataChoiceSelection);
		param.setJumpCorrelation(jumpCorrelation);
		param.setNatureOfEconomicExpectations(natureOfEconomicExpectations);
		param.setHomogeneousAgents(homogeneousAgents);
		param.setAAATrancheProbSurvivalAfterT(AAATrancheProbSurvivalAfterT);
		param.setAATrancheProbSurvivalAfterT(AATrancheProbSurvivalAfterT);
		param.setATrancheProbSurvivalAfterT(ATrancheProbSurvivalAfterT);
		param.setBBBTrancheProbSurvivalAfterT(BBBTrancheProbSurvivalAfterT);
		param.setBBB3TrancheProbSurvivalAfterT(BBB3TrancheProbSurvivalAfterT);
		param.setAAATrancheProbSurvivalAfterTm1(AAATrancheProbSurvivalAfterTm1);
		param.setATrancheProbSurvivalAfterTm1(ATrancheProbSurvivalAfterTm1);
		param.setBBBTrancheProbSurvivalAfterTm1(BBBTrancheProbSurvivalAfterTm1);
		param.setAATrancheProbSurvivalAfterTm1(AATrancheProbSurvivalAfterTm1);
		param.setBBB3TrancheProbSurvivalAfterTm1(BBB3TrancheProbSurvivalAfterTm1);
		param.setAAATrancheCoupon(AAATrancheCoupon);
		param.setAATrancheCoupon(AATrancheCoupon);
		param.setATrancheCoupon(ATrancheCoupon);
		param.setBBBTrancheCoupon(BBBTrancheCoupon);
		param.setBBB3TrancheCoupon(BBB3TrancheCoupon);
		param.setDiscountFactor(discountFactor);
		param.setAnnuityFactor(annuityFactor);

	}

	public ModelParameters getModelParameters() {
		return this.param;
	}

	private void loadModelSettings_click() {
		// this.eng.buildModels();
		this.jButton_loadModelSettings.setEnabled(false);
		this.jButton_run.setEnabled(true);

	}

	private void resetModel_click() {
		// this.eng.buildModels();
		this.jButton_loadModelSettings.setEnabled(true);
		this.enviroment = null;
		tradingStrategy.setSelectedIndex(0);
		startYear.setSelectedItem(startYr[0]);
		startQuater.setSelectedItem(startQtr[0]);
		modelledBanksSelection.setSelectedItem(bankString[0]);
		volThresholdTF.setText("");
		haircutTF.setText("");
		numQuatersTF.setText("");
		this.defaultModel = false;
		// now clear the JButton Group selections
		bgCRM.clearSelection();
		bgBasel.clearSelection();
		bgModel.clearSelection();
		bgMBSDataChoice.clearSelection();
		bgModelWithLeverageChoice.clearSelection();
		// realTimeButton.setSelected(true);
		// this.getJButton_run().setEnabled(true);

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<METHODS FOR THE CONFIGURATION
	// PANEL>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private JPanel getJPanel_ModelConfig() {
		if (jPanel_ModelConfig == null) {
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.weighty = 1.0;
			gridBagConstraints1.gridx = 0;
			jPanel_ModelConfig = new JPanel();
			jPanel_ModelConfig.setLayout(new GridBagLayout());
			jPanel_ModelConfig
					.setBounds(new java.awt.Rectangle(0, 42, 266, 910));
			// jPanel_ModelConfig
			// .add(getJTabbedPane_Config(), gridBagConstraints1);
		}
		return jPanel_ModelConfig;
	}

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
			jMenuFile.add(getJMenuItemShowExposures());
			jMenuFile.add(getJMenuItemShowSpreads());
			jMenuFile.add(getJMenuItemPrintChartsToFile());
			jMenuFile.add(getJMenuItemSaveChartsToImageFile());
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
			jMenuItemAbout
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							JOptionPane
									.showMessageDialog(
											null,
											"CDS Basis/ CDS Carry Trade Model Simulation"
													+ "\n"
													+ "Oluwasegun Bewaji"
													+ "\n"
													+ "PhD Thesis Second Chapter Model"
													+ "\n"
													+ "Model Assumptions: "
													+ "\n"
													+ "Trading based on Normura CDO pricing pipeline"
													+ "\n"
													+ "Trading Agents respond only to movement in CDO and CDS Spreads and not to news events",
											"", JOptionPane.INFORMATION_MESSAGE);

						}
					});
		}
		return jMenuItemAbout;
	}

	/**
	 * The following jmenu item is used to hide the spreads charts from the
	 * jPanel_SimulationResults JPanel and show the basis income and balance
	 * sheet data of the banks
	 * 
	 * @return
	 */
	private JMenuItem getJMenuItemShowExposures() {
		// TODO Auto-generated method stub
		if (jMenuItemComparisonRun == null) {
			jMenuItemComparisonRun = new JMenuItem();
			jMenuItemComparisonRun.setText("Show Balancesheet Exposures");
			jMenuItemComparisonRun.setMnemonic(KeyEvent.VK_A);
			jMenuItemComparisonRun
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jPanel_SimulationResults
									.remove(tabbedPane_BasisAndSpreads);
							jPanel_SimulationResults
									.add(tabbedPane_SimulationOutput);
							// jParentPane.add(jPanel_SimulationResults);
							jPanel_SimulationResults.repaint();
							jPanel_SimulationResults.validate();

						}
					});

		}
		return jMenuItemComparisonRun;
	}

	/**
	 * This method initializes jMenuItemSingleRun
	 * 
	 * The following jmenu item is used to hide the basis income and balance
	 * sheet data of the banks from the jPanel_SimulationResults JPanel and show
	 * the spreads charts
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemShowSpreads() {
		// TODO Auto-generated method stub
		if (jMenuItemSingleRun == null) {
			jMenuItemSingleRun = new JMenuItem();
			jMenuItemSingleRun.setText("Show Spread Data");
			jMenuItemSingleRun.setMnemonic(KeyEvent.VK_A);
			jMenuItemSingleRun
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							jPanel_SimulationResults
									.remove(tabbedPane_SimulationOutput);
							jPanel_SimulationResults
									.add(tabbedPane_BasisAndSpreads);
							// jParentPane.add(jPanel_SimulationResults);tabbedPane_BasisAndSpreads
							jPanel_SimulationResults.repaint();
							jPanel_SimulationResults.validate();

						}
					});

		}
		return jMenuItemSingleRun;
	}

	/**
	 * This method initializes jMenuItemExit
	 * 
	 * @return javax.swing.JMenuItem
	 */
	private JMenuItem getJMenuItemExit() {
		if (jMenuItemExit == null) {
			jMenuItemExit = new JMenuItem();
			jMenuItemExit.setMnemonic(KeyEvent.VK_X);
			jMenuItemExit.setText("Exit");//
			jMenuItemExit
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							System.exit(0);
						}
					});
		}
		return jMenuItemExit;
	}// end of getJMenuItemExit

	private JMenuItem getJMenuItemPrintChartsToFile() {
		// TODO Auto-generated method stub
		if (jMenuItemPrintChartsToFile == null) {
			jMenuItemPrintChartsToFile = new JMenuItem();
			jMenuItemPrintChartsToFile.setText("Print Results to File");
			jMenuItemPrintChartsToFile.setMnemonic(KeyEvent.VK_A);
			jMenuItemPrintChartsToFile
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (model == null) {
								JOptionPane
										.showMessageDialog(
												null,
												"Model not yet loaded. Please configure the simulation ",
												"", JOptionPane.ERROR_MESSAGE);
							}
							// try {
							// // printCharts();
							// } catch (FileNotFoundException e1) {
							// // TODO Auto-generated catch block
							// // printChartsToImage
							// e1.printStackTrace();
							// }

						}
					});

		}
		return jMenuItemPrintChartsToFile;
	}

	private JMenuItem getJMenuItemSaveChartsToImageFile() {
		// TODO Auto-generated method stub
		if (jMenuItemSaveChartsToImageFile == null) {
			jMenuItemSaveChartsToImageFile = new JMenuItem();
			jMenuItemSaveChartsToImageFile.setText("Save Results to PNG Image");
			jMenuItemSaveChartsToImageFile.setMnemonic(KeyEvent.VK_A);
			jMenuItemSaveChartsToImageFile
					.addActionListener(new java.awt.event.ActionListener() {
						@Override
						public void actionPerformed(java.awt.event.ActionEvent e) {
							if (model == null) {
								JOptionPane
										.showMessageDialog(
												null,
												"Model not yet loaded. Please configure the simulation ",
												"", JOptionPane.ERROR_MESSAGE);
							}
							// try {
							// // printChartsToImage();
							// } catch (FileNotFoundException e1) {
							// // TODO Auto-generated catch block
							// e1.printStackTrace();
							// }

						}
					});

		}
		return jMenuItemSaveChartsToImageFile;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY
	// METHODS >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	public void clearAllActiveGraphicsPanels() {
		/**
		 * This method is used to clear all active graphics panels
		 */
		jPanel_SimulationResults.repaint();
		jPanel_SimulationResults.validate();
	}

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
		com.lowagie.text.Rectangle pageSize = new com.lowagie.text.Rectangle(
				width, height);
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

	// this method saves the charts as pdf files
	private void saveChartAsPNG(File file, JFreeChart chart, int width,
			int height) throws IOException {

		OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		writeChartAsPNG(out, chart, width, height);

	}

	// this method writes the charts in the saved pdf files
	private void writeChartAsPNG(OutputStream out, JFreeChart chart, int width,
			int height) {
		// TODO Auto-generated method stub
		com.lowagie.text.Rectangle pageSize = new com.lowagie.text.Rectangle(
				width, height);
		Document document = new Document(pageSize, 50, 50, 50, 50);
		try {
			BufferedImage chartImage = chart.createBufferedImage(width, height,
					null);
			ImageIO.write(chartImage, "png", out);
			document.addAuthor("Oluwasegun O. Bewaji: PhyperRutridge");
			document.addSubject("CDS Basis Trading Model");
			document.open();
		} catch (Exception de) {
			System.err.println(de.getMessage());
		}
		document.close();
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<UTILITY METHODS USED TO
	// CREATE THE CHARTS IN
	// JFREECHART.>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private void updateTimeSeries(Quarter date) {
		AllBanksRMBSExposuresSimulatedTS.add(date,
				AllBanksRMBSExposuresSimulated);
		AllBanksRMBSExposuresFDICDataTS
				.add(date, AllBanksRMBSExposuresFDICData);
		AllBanksMezzanineRMBSExposuresFDICDataTS.add(date,
				AllBanksMezzanineRMBSExposuresSimulated);
		AllBanksCurrentQtrCDSCarryTradeIncomeTS.add(date,
				AllBanksCurrentQtrCDSCarryTradeIncome);
		AllBanksACurrentQtrCapitalSavingsTS.add(date,
				AllBanksACurrentQtrCapitalSavings);
		AllBanksCurrentQtrCDSBasisIncomeTS.add(date,
				AllBanksCurrentQtrCDSBasisIncome);
		AllBanksAccumulatedCDSCarryTradeIncomeTS.add(date,
				AllBanksAccumulatedCDSCarryTradeIncome);
		AllBanksAccumulatedCapitalSavingsTS.add(date,
				AllBanksAccumulatedCapitalSavings);
		AllBanksAccumulatedCDSBasisIncomeTS.add(date,
				AllBanksAccumulatedCDSBasisIncome);
		AllBanksRegulatoryCapitalLeverageTakingTS.add(date,
				AllBanksRegulatoryCapitalLeverageTaking);

		// other banks
		OtherBanksRMBSExposuresSimulatedTS.add(date,
				OtherBanksRMBSExposuresSimulated);
		OtherBanksRMBSExposuresFDICDataTS.add(date,
				OtherBanksRMBSExposuresFDICData);
		OtherBanksMezzanineRMBSExposuresFDICDataTS.add(date,
				OtherBanksMezzanineRMBSExposuresSimulated);
		OtherBanksCurrentQtrCDSCarryTradeIncomeTS.add(date,
				OtherBanksCurrentQtrCDSCarryTradeIncome);
		OtherBanksACurrentQtrCapitalSavingsTS.add(date,
				OtherBanksACurrentQtrCapitalSavings);
		OtherBanksCurrentQtrCDSBasisIncomeTS.add(date,
				OtherBanksCurrentQtrCDSBasisIncome);

		// major 12 banks
		Major12RMBSExposuresSimulatedTS
				.add(date, Major12RMBSExposuresSimulated);
		Major12RMBSExposuresFDICDataTS.add(date, Major12RMBSExposuresFDICData);
		Major12MezzanineRMBSExposuresFDICDataTS.add(date,
				Major12MezzanineRMBSExposuresSimulated);
		Major12CurrentQtrCDSCarryTradeIncomeTS.add(date,
				Major12CurrentQtrCDSCarryTradeIncome);
		Major12ACurrentQtrCapitalSavingsTS.add(date,
				Major12ACurrentQtrCapitalSavings);
		Major12CurrentQtrCDSBasisIncomeTS.add(date,
				Major12CurrentQtrCDSBasisIncome);

		// top 5 banks
		top5RMBSExposuresTS.add(date, aggregateRMBSExposuresTop5BanksSimulated);
		top5RMBSExposuresFDICTS.add(date,
				aggregateRMBSExposuresTop5BanksFDICData);
		top5RMBSMezzanineTrancheExposuresTS.add(date,
				aggregateRMBSMezTrancheExposuresTop5BanksSimulated);
		top5RegulatoryCapitalLeverageTakingTS.add(date,
				top5RegulatoryCapitalLeverageTaking);
		top5AccumulatedCDSCarryTradeIncomeTS.add(date,
				top5AccumulatedCDSCarryTradeIncome);
		top5AccumulatedCapitalSavingsTS
				.add(date, top5AccumulatedCapitalSavings);
		top5AccumulatedCDSBasisIncomeTS
				.add(date, top5AccumulatedCDSBasisIncome);
		top5CurrentQtrCDSCarryTradeIncomeTS.add(date,
				top5CurrentQtrCDSCarryTradeIncome);
		top5CurrentQtrCapitalSavingsTS.add(date, top5CurrentQtrCapitalSavings);
		top5CurrentQtrCDSBasisIncomeTS.add(date, top5CurrentQtrCDSBasisIncome);

		// Individual Focus Banks

		BofACurrentQtrCDSCarryTradeIncomeTS.add(date,
				BofACurrentQtrCDSCarryTradeIncome);
		BofAACurrentQtrCapitalSavingsTS
				.add(date, BofAACurrentQtrCapitalSavings);
		BofACurrentQtrCDSBasisIncomeTS.add(date, BofACurrentQtrCDSBasisIncome);
		BofARMBSExposuresSimulatedTS.add(date, BofARMBSExposuresSimulated);
		BofARMBSExposuresFDICDataTS.add(date, BofARMBSExposuresFDICData);
		BofAMezzanineRMBSExposuresFDICDataTS.add(date,
				BofAMezzanineRMBSExposuresSimulated);

		BoNYCurrentQtrCDSCarryTradeIncomeTS.add(date,
				BoNYCurrentQtrCDSCarryTradeIncome);
		BoNYACurrentQtrCapitalSavingsTS
				.add(date, BoNYACurrentQtrCapitalSavings);
		BoNYCurrentQtrCDSBasisIncomeTS.add(date, BoNYCurrentQtrCDSBasisIncome);
		BoNYRMBSExposuresSimulatedTS.add(date, BoNYRMBSExposuresSimulated);
		BoNYRMBSExposuresFDICDataTS.add(date, BoNYRMBSExposuresFDICData);
		BoNYMezzanineRMBSExposuresFDICDataTS.add(date,
				BoNYMezzanineRMBSExposuresSimulated);

		CitibankCurrentQtrCDSCarryTradeIncomeTS.add(date,
				CitibankCurrentQtrCDSCarryTradeIncome);
		CitibankACurrentQtrCapitalSavingsTS.add(date,
				CitibankACurrentQtrCapitalSavings);
		CitibankCurrentQtrCDSBasisIncomeTS.add(date,
				CitibankCurrentQtrCDSBasisIncome);
		CitibankRMBSExposuresSimulatedTS.add(date,
				CitibankRMBSExposuresSimulated);
		CitibankRMBSExposuresFDICDataTS
				.add(date, CitibankRMBSExposuresFDICData);
		CitibankMezzanineRMBSExposuresFDICDataTS.add(date,
				CitibankMezzanineRMBSExposuresSimulated);

		DeutscheCurrentQtrCDSCarryTradeIncomeTS.add(date,
				DeutscheCurrentQtrCDSCarryTradeIncome);
		DeutscheACurrentQtrCapitalSavingsTS.add(date,
				DeutscheACurrentQtrCapitalSavings);
		DeutscheCurrentQtrCDSBasisIncomeTS.add(date,
				DeutscheCurrentQtrCDSBasisIncome);
		DeutscheRMBSExposuresSimulatedTS.add(date,
				DeutscheRMBSExposuresSimulated);
		DeutscheRMBSExposuresFDICDataTS
				.add(date, DeutscheRMBSExposuresFDICData);
		DeutscheMezzanineRMBSExposuresFDICDataTS.add(date,
				DeutscheMezzanineRMBSExposuresSimulated);

		GoldmanCurrentQtrCDSCarryTradeIncomeTS.add(date,
				GoldmanCurrentQtrCDSCarryTradeIncome);
		GoldmanACurrentQtrCapitalSavingsTS.add(date,
				GoldmanACurrentQtrCapitalSavings);
		GoldmanCurrentQtrCDSBasisIncomeTS.add(date,
				GoldmanCurrentQtrCDSBasisIncome);
		GoldmanRMBSExposuresSimulatedTS
				.add(date, GoldmanRMBSExposuresSimulated);
		GoldmanRMBSExposuresFDICDataTS.add(date, GoldmanRMBSExposuresFDICData);
		GoldmanMezzanineRMBSExposuresFDICDataTS.add(date,
				GoldmanMezzanineRMBSExposuresSimulated);

		HSBCCurrentQtrCDSCarryTradeIncomeTS.add(date,
				HSBCCurrentQtrCDSCarryTradeIncome);
		HSBCACurrentQtrCapitalSavingsTS
				.add(date, HSBCACurrentQtrCapitalSavings);
		HSBCCurrentQtrCDSBasisIncomeTS.add(date, HSBCCurrentQtrCDSBasisIncome);
		HSBCRMBSExposuresSimulatedTS.add(date, HSBCRMBSExposuresSimulated);
		HSBCRMBSExposuresFDICDataTS.add(date, HSBCRMBSExposuresFDICData);
		HSBCMezzanineRMBSExposuresFDICDataTS.add(date,
				HSBCMezzanineRMBSExposuresSimulated);

		JPMorganCurrentQtrCDSCarryTradeIncomeTS.add(date,
				JPMorganCurrentQtrCDSCarryTradeIncome);
		JPMorganACurrentQtrCapitalSavingsTS.add(date,
				JPMorganACurrentQtrCapitalSavings);
		JPMorganCurrentQtrCDSBasisIncomeTS.add(date,
				JPMorganCurrentQtrCDSBasisIncome);
		JPMorganRMBSExposuresSimulatedTS.add(date,
				JPMorganRMBSExposuresSimulated);
		JPMorganRMBSExposuresFDICDataTS
				.add(date, JPMorganRMBSExposuresFDICData);
		JPMorganMezzanineRMBSExposuresFDICDataTS.add(date,
				JPMorganMezzanineRMBSExposuresSimulated);

		KeybankCurrentQtrCDSCarryTradeIncomeTS.add(date,
				KeybankCurrentQtrCDSCarryTradeIncome);
		KeybankACurrentQtrCapitalSavingsTS.add(date,
				KeybankACurrentQtrCapitalSavings);
		KeybankCurrentQtrCDSBasisIncomeTS.add(date,
				KeybankCurrentQtrCDSBasisIncome);
		KeybankRMBSExposuresSimulatedTS
				.add(date, KeybankRMBSExposuresSimulated);
		KeybankRMBSExposuresFDICDataTS.add(date, KeybankRMBSExposuresFDICData);
		KeybankMezzanineRMBSExposuresFDICDataTS.add(date,
				KeybankMezzanineRMBSExposuresSimulated);

		MerrillCurrentQtrCDSCarryTradeIncomeTS.add(date,
				MerrillCurrentQtrCDSCarryTradeIncome);
		MerrillACurrentQtrCapitalSavingsTS.add(date,
				MerrillACurrentQtrCapitalSavings);
		MerrillCurrentQtrCDSBasisIncomeTS.add(date,
				MerrillCurrentQtrCDSBasisIncome);
		MerrillRMBSExposuresSimulatedTS
				.add(date, MerrillRMBSExposuresSimulated);
		MerrillRMBSExposuresFDICDataTS.add(date, MerrillRMBSExposuresFDICData);
		MerrillMezzanineRMBSExposuresFDICDataTS.add(date,
				MerrillMezzanineRMBSExposuresSimulated);

		MorganStanleyCurrentQtrCDSCarryTradeIncomeTS.add(date,
				MorganStanleyCurrentQtrCDSCarryTradeIncome);
		MorganStanleyACurrentQtrCapitalSavingsTS.add(date,
				MorganStanleyACurrentQtrCapitalSavings);
		MorganStanleyCurrentQtrCDSBasisIncomeTS.add(date,
				MorganStanleyCurrentQtrCDSBasisIncome);
		MorganStanleyRMBSExposuresSimulatedTS.add(date,
				MorganStanleyRMBSExposuresSimulated);
		MorganStanleyRMBSExposuresFDICDataTS.add(date,
				MorganStanleyRMBSExposuresFDICData);
		MorganStanleyMezzanineRMBSExposuresFDICDataTS.add(date,
				MorganStanleyMezzanineRMBSExposuresSimulated);

		WachoviaCurrentQtrCDSCarryTradeIncomeTS.add(date,
				WachoviaCurrentQtrCDSCarryTradeIncome);
		WachoviaACurrentQtrCapitalSavingsTS.add(date,
				WachoviaACurrentQtrCapitalSavings);
		WachoviaCurrentQtrCDSBasisIncomeTS.add(date,
				WachoviaCurrentQtrCDSBasisIncome);
		WachoviaRMBSExposuresSimulatedTS.add(date,
				WachoviaRMBSExposuresSimulated);
		WachoviaRMBSExposuresFDICDataTS
				.add(date, WachoviaRMBSExposuresFDICData);
		WachoviaMezzanineRMBSExposuresFDICDataTS.add(date,
				WachoviaMezzanineRMBSExposuresSimulated);

		WellsFargoCurrentQtrCDSCarryTradeIncomeTS.add(date,
				WellsFargoCurrentQtrCDSCarryTradeIncome);
		WellsFargoACurrentQtrCapitalSavingsTS.add(date,
				WellsFargoACurrentQtrCapitalSavings);
		WellsFargoCurrentQtrCDSBasisIncomeTS.add(date,
				WellsFargoCurrentQtrCDSBasisIncome);
		WellsFargoRMBSExposuresSimulatedTS.add(date,
				WellsFargoRMBSExposuresSimulated);
		WellsFargoRMBSExposuresFDICDataTS.add(date,
				WellsFargoRMBSExposuresFDICData);
		WellsFargoMezzanineRMBSExposuresFDICDataTS.add(date,
				WellsFargoMezzanineRMBSExposuresSimulated);

		// <<<<<<<<<<<<<<<<<<<<<LIBOR RATE>>>>>>>>>>>>>>>>>>>>>
		liborRateTS.add(date, defaultLIBORRate);
	}

	private void deleteTimeseriesDataPlaceHolders() {

		aggregateRMBSExposuresTop5BanksSimulated = 0;
		aggregateRMBSMezTrancheExposuresTop5BanksSimulated = 0;
		aggregateRMBSExposuresTop5BanksFDICData = 0;
		top5AccumulatedCDSCarryTradeIncome = 0;
		top5AccumulatedCapitalSavings = 0;
		top5AccumulatedCDSBasisIncome = 0;
		top5RegulatoryCapitalLeverageTaking = 0;
		top5CurrentQtrCDSCarryTradeIncome = 0;
		top5CurrentQtrCapitalSavings = 0;
		top5CurrentQtrCDSBasisIncome = 0;
		top5AggregatedExposuresProtectionBuyers = 0;
		top5AggregatedGrossSavingProtectionBuyers = 0;
		top5AggregatedReInvestmentExposuresProtectionBuyers = 0;

		AllBanksRMBSExposuresSimulated = 0;
		AllBanksRMBSExposuresFDICData = 0;
		AllBanksMezzanineRMBSExposuresSimulated = 0;
		AllBanksCurrentQtrCDSCarryTradeIncome = 0;
		AllBanksACurrentQtrCapitalSavings = 0;
		AllBanksCurrentQtrCDSBasisIncome = 0;
		AllBanksAccumulatedCDSCarryTradeIncome = 0;
		AllBanksAccumulatedCapitalSavings = 0;
		AllBanksAccumulatedCDSBasisIncome = 0;
		AllBanksRegulatoryCapitalLeverageTaking = 0;
		allBanksAggregatedExposuresProtectionBuyers = 0;
		allBanksAggregatedGrossSavingProtectionBuyers = 0;
		allBanksAggregatedReInvestmentExposuresProtectionBuyers = 0;

		OtherBanksRMBSExposuresSimulated = 0;
		OtherBanksRMBSExposuresFDICData = 0;
		OtherBanksMezzanineRMBSExposuresSimulated = 0;
		OtherBanksCurrentQtrCDSCarryTradeIncome = 0;
		OtherBanksACurrentQtrCapitalSavings = 0;
		OtherBanksCurrentQtrCDSBasisIncome = 0;

		Major12RMBSExposuresSimulated = 0;
		Major12RMBSExposuresFDICData = 0;
		Major12MezzanineRMBSExposuresSimulated = 0;
		Major12CurrentQtrCDSCarryTradeIncome = 0;
		Major12ACurrentQtrCapitalSavings = 0;
		Major12CurrentQtrCDSBasisIncome = 0;
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF
		// AGGREGATED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<INDIVIDUAL
		// BANKS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		aggregateRMBSExposuresTop5BanksSimulated = 0;
		aggregateRMBSMezTrancheExposuresTop5BanksSimulated = 0;
		aggregateRMBSExposuresTop5BanksFDICData = 0;
		top5AccumulatedCDSCarryTradeIncome = 0;
		top5AccumulatedCapitalSavings = 0;
		top5AccumulatedCDSBasisIncome = 0;
		top5RegulatoryCapitalLeverageTaking = 0;
		top5CurrentQtrCDSCarryTradeIncome = 0;
		top5CurrentQtrCapitalSavings = 0;
		top5CurrentQtrCDSBasisIncome = 0;
		top5AggregatedExposuresProtectionBuyers = 0;
		top5AggregatedGrossSavingProtectionBuyers = 0;
		top5AggregatedReInvestmentExposuresProtectionBuyers = 0;

		AllBanksRMBSExposuresSimulated = 0;
		AllBanksRMBSExposuresFDICData = 0;
		AllBanksMezzanineRMBSExposuresSimulated = 0;
		AllBanksCurrentQtrCDSCarryTradeIncome = 0;
		AllBanksACurrentQtrCapitalSavings = 0;
		AllBanksCurrentQtrCDSBasisIncome = 0;
		AllBanksAccumulatedCDSCarryTradeIncome = 0;
		AllBanksAccumulatedCapitalSavings = 0;
		AllBanksAccumulatedCDSBasisIncome = 0;
		AllBanksRegulatoryCapitalLeverageTaking = 0;
		allBanksAggregatedExposuresProtectionBuyers = 0;
		allBanksAggregatedGrossSavingProtectionBuyers = 0;
		allBanksAggregatedReInvestmentExposuresProtectionBuyers = 0;

		OtherBanksRMBSExposuresSimulated = 0;
		OtherBanksRMBSExposuresFDICData = 0;
		OtherBanksMezzanineRMBSExposuresSimulated = 0;
		OtherBanksCurrentQtrCDSCarryTradeIncome = 0;
		OtherBanksACurrentQtrCapitalSavings = 0;
		OtherBanksCurrentQtrCDSBasisIncome = 0;

		Major12RMBSExposuresSimulated = 0;
		Major12RMBSExposuresFDICData = 0;
		Major12MezzanineRMBSExposuresSimulated = 0;
		Major12CurrentQtrCDSCarryTradeIncome = 0;
		Major12ACurrentQtrCapitalSavings = 0;
		Major12CurrentQtrCDSBasisIncome = 0;
		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF
		// AGGREGATED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<INDIVIDUAL
		// BANKS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

		BofACurrentQtrCDSCarryTradeIncome = 0;
		BofAACurrentQtrCapitalSavings = 0;
		BofACurrentQtrCDSBasisIncome = 0;
		BofARMBSExposuresSimulated = 0;
		BofARMBSExposuresFDICData = 0;
		BofAMezzanineRMBSExposuresSimulated = 0;

		BoNYCurrentQtrCDSCarryTradeIncome = 0;
		BoNYACurrentQtrCapitalSavings = 0;
		BoNYCurrentQtrCDSBasisIncome = 0;
		BoNYRMBSExposuresSimulated = 0;
		BoNYRMBSExposuresFDICData = 0;
		BoNYMezzanineRMBSExposuresSimulated = 0;

		CitibankCurrentQtrCDSCarryTradeIncome = 0;
		CitibankACurrentQtrCapitalSavings = 0;
		CitibankCurrentQtrCDSBasisIncome = 0;
		CitibankRMBSExposuresSimulated = 0;
		CitibankRMBSExposuresFDICData = 0;
		CitibankMezzanineRMBSExposuresSimulated = 0;

		DeutscheCurrentQtrCDSCarryTradeIncome = 0;
		DeutscheACurrentQtrCapitalSavings = 0;
		DeutscheCurrentQtrCDSBasisIncome = 0;
		DeutscheRMBSExposuresSimulated = 0;
		DeutscheRMBSExposuresFDICData = 0;
		DeutscheMezzanineRMBSExposuresSimulated = 0;

		GoldmanCurrentQtrCDSCarryTradeIncome = 0;
		GoldmanACurrentQtrCapitalSavings = 0;
		GoldmanCurrentQtrCDSBasisIncome = 0;
		GoldmanRMBSExposuresSimulated = 0;
		GoldmanRMBSExposuresFDICData = 0;
		GoldmanMezzanineRMBSExposuresSimulated = 0;

		HSBCCurrentQtrCDSCarryTradeIncome = 0;
		HSBCACurrentQtrCapitalSavings = 0;
		HSBCCurrentQtrCDSBasisIncome = 0;
		HSBCRMBSExposuresSimulated = 0;
		HSBCRMBSExposuresFDICData = 0;
		HSBCMezzanineRMBSExposuresSimulated = 0;

		JPMorganCurrentQtrCDSCarryTradeIncome = 0;
		JPMorganACurrentQtrCapitalSavings = 0;
		JPMorganCurrentQtrCDSBasisIncome = 0;
		JPMorganRMBSExposuresSimulated = 0;
		JPMorganRMBSExposuresFDICData = 0;
		JPMorganMezzanineRMBSExposuresSimulated = 0;

		KeybankCurrentQtrCDSCarryTradeIncome = 0;
		KeybankACurrentQtrCapitalSavings = 0;
		KeybankCurrentQtrCDSBasisIncome = 0;
		KeybankRMBSExposuresSimulated = 0;
		KeybankRMBSExposuresFDICData = 0;
		KeybankMezzanineRMBSExposuresSimulated = 0;

		MerrillCurrentQtrCDSCarryTradeIncome = 0;
		MerrillACurrentQtrCapitalSavings = 0;
		MerrillCurrentQtrCDSBasisIncome = 0;
		MerrillRMBSExposuresSimulated = 0;
		MerrillRMBSExposuresFDICData = 0;
		MerrillMezzanineRMBSExposuresSimulated = 0;

		MorganStanleyCurrentQtrCDSCarryTradeIncome = 0;
		MorganStanleyACurrentQtrCapitalSavings = 0;
		MorganStanleyCurrentQtrCDSBasisIncome = 0;
		MorganStanleyRMBSExposuresSimulated = 0;
		MorganStanleyRMBSExposuresFDICData = 0;
		MorganStanleyMezzanineRMBSExposuresSimulated = 0;

		WachoviaCurrentQtrCDSCarryTradeIncome = 0;
		WachoviaACurrentQtrCapitalSavings = 0;
		WachoviaCurrentQtrCDSBasisIncome = 0;
		WachoviaRMBSExposuresSimulated = 0;
		WachoviaRMBSExposuresFDICData = 0;
		WachoviaMezzanineRMBSExposuresSimulated = 0;

		WellsFargoCurrentQtrCDSCarryTradeIncome = 0;
		WellsFargoACurrentQtrCapitalSavings = 0;
		WellsFargoCurrentQtrCDSBasisIncome = 0;
		WellsFargoRMBSExposuresSimulated = 0;
		WellsFargoRMBSExposuresFDICData = 0;
		WellsFargoMezzanineRMBSExposuresSimulated = 0;

		defaultLIBORRate = 0;
	}

	public void setDefault5TrancheSpreadTimeSeries(Quarter date) {

		this.cashSpreadsAAATS.add(date, defaultcashSpreadsAAA);
		this.cashSpreadsAATS.add(date, defaultcashSpreadsAA);
		this.cashSpreadsATS.add(date, defaultcashSpreadsA);
		this.cashSpreadsBBBTS.add(date, defaultcashSpreadsBBB);
		this.cashSpreadsBBB3TS.add(date, defaultcashSpreadsBBB3);

		this.creditDerivativesSpreadsAAATS.add(date,
				defaultcreditDerivativesSpreadsAAA);
		this.creditDerivativesSpreadsAATS.add(date,
				defaultcreditDerivativesSpreadsAA);
		this.creditDerivativesSpreadsATS.add(date,
				defaultcreditDerivativesSpreadsA);
		this.creditDerivativesSpreadsBBBTS.add(date,
				defaultcreditDerivativesSpreadsBBB);
		this.creditDerivativesSpreadsBBB3TS.add(date,
				defaultcreditDerivativesSpreadsBBB3);

		this.creditDerivativesPricesAAATS.add(date,
				defaultcreditDerivativesPricesAAA);
		this.creditDerivativesPricesAATS.add(date,
				defaultcreditDerivativesPricesAA);
		this.creditDerivativesPricesATS.add(date,
				defaultcreditDerivativesPricesA);
		this.creditDerivativesPricesBBBTS.add(date,
				defaultcreditDerivativesPricesBBB);
		this.creditDerivativesPricesBBB3TS.add(date,
				defaultcreditDerivativesPricesBBB3);

		this.CDSBasisAAATS.add(date, defaultCDSBasisAAA);
		this.CDSBasisAATS.add(date, defaultCDSBasisAA);
		this.CDSBasisATS.add(date, defaultCDSBasisA);
		this.CDSBasisBBBTS.add(date, defaultCDSBasisBBB);
		this.CDSBasisBBB3TS.add(date, defaultCDSBasisBBB3);

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<NEW CHARTS JAS
	// WAY>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Methods
	// Related to the
	// Charts>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<LIBOR RATE
	// CHART>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private JFreeChart getDefaultLIBORRATEChart() {
		// TODO Auto-generated method stub
		this.libroRateTC = new TimeSeriesCollection();

		this.liborRateTS = new TimeSeries("LIBOR Rate", Quarter.class);
		this.libroRateTC.addSeries(liborRateTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart("US$ LIBOR Rate",
				"Time Period", "Percentage Points %", libroRateTC, true, true,
				false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<END LIBOR RATE
	// CHART>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<CDS and BOND SPREAD
	// CHART>>>>>>>>>>>>>>>>>>>>>>>>>>
	private JFreeChart getDefaultCDSandBondSpreadChart() {
		// TODO Auto-generated method stub
		cashAndCreditDerivativesSpreadsTC = new TimeSeriesCollection();

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"ABX.HE and Nomura RMBS/CMO Pipeline Spreads", "Time Period",
				"Basis Points bp", cashAndCreditDerivativesSpreadsTC, true,
				true, false);

		cashSpreadsAAATS = new TimeSeries("Bond Spread", Quarter.class);
		cashSpreadsAATS = new TimeSeries("Bond Spread", Quarter.class);
		cashSpreadsATS = new TimeSeries("Bond Spread", Quarter.class);
		cashSpreadsBBBTS = new TimeSeries("Bond Spread", Quarter.class);
		cashSpreadsBBB3TS = new TimeSeries("Bond Spread", Quarter.class);

		creditDerivativesSpreadsAAATS = new TimeSeries("ABX.HE AAA Spread",
				Quarter.class);
		creditDerivativesSpreadsAATS = new TimeSeries("ABX.HE AA Spread",
				Quarter.class);
		creditDerivativesSpreadsATS = new TimeSeries("ABX.HE A Spread",
				Quarter.class);
		creditDerivativesSpreadsBBBTS = new TimeSeries("ABX.HE BBB Spread",
				Quarter.class);
		creditDerivativesSpreadsBBB3TS = new TimeSeries("ABX.HE -BBB Spread",
				Quarter.class);

		// <<<<<<<<<<<<<<<<<ADD Spreads to
		cashAndCreditDerivativesSpreadsTC.addSeries(this.cashSpreadsAAATS);
		cashAndCreditDerivativesSpreadsTC.addSeries(this.cashSpreadsAATS);
		cashAndCreditDerivativesSpreadsTC.addSeries(this.cashSpreadsATS);
		cashAndCreditDerivativesSpreadsTC.addSeries(this.cashSpreadsBBBTS);
		cashAndCreditDerivativesSpreadsTC.addSeries(this.cashSpreadsBBB3TS);

		cashAndCreditDerivativesSpreadsTC
				.addSeries(this.creditDerivativesSpreadsAAATS);
		cashAndCreditDerivativesSpreadsTC
				.addSeries(this.creditDerivativesSpreadsAATS);
		cashAndCreditDerivativesSpreadsTC
				.addSeries(this.creditDerivativesSpreadsATS);
		cashAndCreditDerivativesSpreadsTC
				.addSeries(this.creditDerivativesSpreadsBBBTS);
		cashAndCreditDerivativesSpreadsTC
				.addSeries(this.creditDerivativesSpreadsBBB3TS);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;

	}

	private JFreeChart getDefaultCDSPriceChart() {
		// TODO Auto-generated method stub
		cashAndCreditDerivativesPriceTC = new TimeSeriesCollection();

		JFreeChart chart = ChartFactory.createTimeSeriesChart("ABX.HE Prices",
				"Time Period", "Percentage Points %",
				cashAndCreditDerivativesPriceTC, true, true, false);

		creditDerivativesPricesAAATS = new TimeSeries("ABX.HE AAA Spread",
				Quarter.class);
		creditDerivativesPricesAATS = new TimeSeries("ABX.HE AA Spread",
				Quarter.class);
		creditDerivativesPricesATS = new TimeSeries("ABX.HE A Spread",
				Quarter.class);
		creditDerivativesPricesBBBTS = new TimeSeries("ABX.HE BBB Spread",
				Quarter.class);
		creditDerivativesPricesBBB3TS = new TimeSeries("ABX.HE -BBB Spread",
				Quarter.class);

		// <<<<<<<<<<<<<<<<<ADD Spreads to
		cashAndCreditDerivativesPriceTC
				.addSeries(this.creditDerivativesPricesAAATS);
		cashAndCreditDerivativesPriceTC
				.addSeries(this.creditDerivativesPricesAATS);
		cashAndCreditDerivativesPriceTC
				.addSeries(this.creditDerivativesPricesATS);
		cashAndCreditDerivativesPriceTC
				.addSeries(this.creditDerivativesPricesBBBTS);
		cashAndCreditDerivativesPriceTC
				.addSeries(this.creditDerivativesPricesBBB3TS);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;

	}

	private JFreeChart getDefaultCDSBasisChart() {
		// TODO Auto-generated method stub
		CDSBasisTC = new TimeSeriesCollection();

		JFreeChart chart = ChartFactory
				.createTimeSeriesChart("CDS Basis", "Time Period",
						"Basis Points bp", CDSBasisTC, true, true, false);

		this.CDSBasisAAATS = new TimeSeries("CDS Basis AAA Exposures",
				Quarter.class);
		this.CDSBasisAATS = new TimeSeries("CDS Basis AA Exposures",
				Quarter.class);
		this.CDSBasisATS = new TimeSeries("CDS Basis A Exposures",
				Quarter.class);
		this.CDSBasisBBBTS = new TimeSeries("CDS Basis BBB Exposures",
				Quarter.class);
		this.CDSBasisBBB3TS = new TimeSeries("CDS Basis -BBB Exposures",
				Quarter.class);

		// <<<<<<<<<<<<<<<<<ADD Spreads to
		CDSBasisTC.addSeries(CDSBasisAAATS);
		CDSBasisTC.addSeries(CDSBasisAATS);
		CDSBasisTC.addSeries(CDSBasisATS);
		CDSBasisTC.addSeries(CDSBasisBBBTS);
		CDSBasisTC.addSeries(CDSBasisBBB3TS);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;

	}

	private void createDefaultSpreadsTimeSeries() {

	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<Aggregated
	// Charts>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	private JFreeChart getTop5AccumulatedBasisTradeIncomeChart() {
		this.top5AccumulatedCDSCarryTradeIncomeTS = new TimeSeries(
				"Top 5 Banks Accumulated CDS Carry Trade Income", Quarter.class);
		this.top5AccumulatedCDSBasisIncomeTS = new TimeSeries(
				"Top 5 Banks Accumulated CDS Basis Income", Quarter.class);
		this.dataSetTop5AccumulatedBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetTop5AccumulatedBasisTradeIncome
				.addSeries(top5AccumulatedCDSCarryTradeIncomeTS);
		this.dataSetTop5AccumulatedBasisTradeIncome
				.addSeries(top5AccumulatedCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Top 5 Banks Aggregate CDS Carry Trade Income Accumulation",
				"Time Period", "US$thousands ",
				dataSetTop5AccumulatedBasisTradeIncome, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getTop5CurrentQtrBasisTradeIncomeChart() {
		this.top5CurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"Top 5 Banks: CDS Carry Trade Income", Quarter.class);
		this.top5CurrentQtrCapitalSavingsTS = new TimeSeries(
				"Top 5 Banks: Capital Savings", Quarter.class);
		this.top5CurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"Top 5 Banks: CDS Basis Income", Quarter.class);
		this.dataSetTop5CurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetTop5CurrentQtrBasisTradeIncome
				.addSeries(top5CurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetTop5CurrentQtrBasisTradeIncome
				.addSeries(top5CurrentQtrCapitalSavingsTS);
		this.dataSetTop5CurrentQtrBasisTradeIncome
				.addSeries(top5CurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Top 5 Banks Aggregate CDS Carry Trade Income Accumulation",
				"Time Period", "US$thousands ",
				dataSetTop5CurrentQtrBasisTradeIncome, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getTop5RMBSExposuresChart() {
		this.top5RMBSExposuresTS = new TimeSeries(
				"Top 5 Banks: RMBS Exposures", Quarter.class);
		this.top5RMBSExposuresFDICTS = new TimeSeries(
				"Top 5 Banks: RMBS Exposures FDIC Data", Quarter.class);
		this.top5RMBSMezzanineTrancheExposuresTS = new TimeSeries(
				"Top 5 Banks: RMBS Exposures Mezzinine Tranche", Quarter.class);
		this.dataSetTop5RMBSExposures = new TimeSeriesCollection();
		this.dataSetTop5RMBSExposures.addSeries(top5RMBSExposuresTS);
		this.dataSetTop5RMBSExposures.addSeries(top5RMBSExposuresFDICTS);
		this.dataSetTop5RMBSExposures
				.addSeries(top5RMBSMezzanineTrancheExposuresTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Top 5 Banks Aggregate RMBS Exposures", "Time Period",
				"US$thousands ", dataSetTop5RMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getTop5RegulatoryCapitalLeverageTakingChart() {
		this.top5RegulatoryCapitalLeverageTakingTS = new TimeSeries(
				"Top 5 Banks: Regulatory Capital Leverage Taking",
				Quarter.class);
		this.dataSetTop5RegulatoryCapitalLeverageTaking = new TimeSeriesCollection();
		this.dataSetTop5RegulatoryCapitalLeverageTaking
				.addSeries(top5RegulatoryCapitalLeverageTakingTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Top 5 Banks: Regulatory Capital Leverage Ratio",
				"Time Period", "Percentage Points % ",
				dataSetTop5RegulatoryCapitalLeverageTaking, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getTop5AccumulatedCapitalSavingsChart() {
		this.top5AccumulatedCapitalSavingsTS = new TimeSeries(
				"Top 5 Banks Cummulative Capital Savings", Quarter.class);
		this.dataSetTop5AccumulatedCapitalSavings = new TimeSeriesCollection();
		this.dataSetTop5AccumulatedCapitalSavings
				.addSeries(top5AccumulatedCapitalSavingsTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Top 5 Banks Aggregate Cummulative Capital Savings",
				"Time Period", "US$thousands ",
				dataSetTop5AccumulatedCapitalSavings, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getMajor12CurrentQtrBasisTradeIncomeChart() {
		this.Major12CurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"Major12 Banks: CDS Carry Trade Income", Quarter.class);
		this.Major12ACurrentQtrCapitalSavingsTS = new TimeSeries(
				"Major12 Banks: Capital Savings", Quarter.class);
		this.Major12CurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"Major12 Banks: CDS Basis Income ", Quarter.class);
		this.dataSetMajor12CurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetMajor12CurrentQtrBasisTradeIncome
				.addSeries(Major12CurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetMajor12CurrentQtrBasisTradeIncome
				.addSeries(Major12ACurrentQtrCapitalSavingsTS);
		this.dataSetMajor12CurrentQtrBasisTradeIncome
				.addSeries(Major12CurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Major12 Banks: CDS Carry Trade Income", "Time Period",
				"US$thousands ", dataSetMajor12CurrentQtrBasisTradeIncome,
				true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getMajor12RMBSExposuresChart() {
		this.Major12RMBSExposuresSimulatedTS = new TimeSeries(
				"Major 12 Banks: Simulated RMBS Exposures", Quarter.class);
		this.Major12RMBSExposuresFDICDataTS = new TimeSeries(
				"Major 12: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.Major12MezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"Major 12 Banks: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetMajor12RMBSExposures = new TimeSeriesCollection();
		this.dataSetMajor12RMBSExposures
				.addSeries(Major12RMBSExposuresSimulatedTS);
		this.dataSetMajor12RMBSExposures
				.addSeries(Major12RMBSExposuresFDICDataTS);
		this.dataSetMajor12RMBSExposures
				.addSeries(Major12MezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory
				.createTimeSeriesChart("Major 12 Banks: RMBS Exposures",
						"Time Period", "US$thousands ",
						dataSetMajor12RMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getOtherBanksCurrentQtrBasisTradeIncomeChart() {
		this.OtherBanksCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"Other Banks: CDS Carry Trade Income", Quarter.class);
		this.OtherBanksACurrentQtrCapitalSavingsTS = new TimeSeries(
				"Other Banks: Capital Savings", Quarter.class);
		this.OtherBanksCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"Other Banks: CDS Basis Income ", Quarter.class);
		this.dataSetOtherBanksCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetOtherBanksCurrentQtrBasisTradeIncome
				.addSeries(OtherBanksCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetOtherBanksCurrentQtrBasisTradeIncome
				.addSeries(OtherBanksACurrentQtrCapitalSavingsTS);
		this.dataSetOtherBanksCurrentQtrBasisTradeIncome
				.addSeries(OtherBanksCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Other Banks CDS Carry Trade Income", "Time Period",
				"US$thousands ", dataSetOtherBanksCurrentQtrBasisTradeIncome,
				true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getOtherBanksRMBSExposuresChart() {
		this.OtherBanksRMBSExposuresSimulatedTS = new TimeSeries(
				"Other Banks: Simulated RMBS Exposures", Quarter.class);
		this.OtherBanksRMBSExposuresFDICDataTS = new TimeSeries(
				"Other Banks: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.OtherBanksMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"Other Banks: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetOtherBanksRMBSExposures = new TimeSeriesCollection();
		this.dataSetOtherBanksRMBSExposures
				.addSeries(this.OtherBanksRMBSExposuresSimulatedTS);
		this.dataSetOtherBanksRMBSExposures
				.addSeries(this.OtherBanksRMBSExposuresFDICDataTS);
		this.dataSetOtherBanksRMBSExposures
				.addSeries(this.OtherBanksMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Other Banks: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetOtherBanksRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getAllBanksCurrentQtrBasisTradeIncomeChart() {
		this.AllBanksCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"All Banks: CDS Carry Trade Income", Quarter.class);
		this.AllBanksACurrentQtrCapitalSavingsTS = new TimeSeries(
				"All Banks: Capital Savings", Quarter.class);
		this.AllBanksCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"All Banks: CDS Basis Income ", Quarter.class);
		this.dataSetAllBanksCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetAllBanksCurrentQtrBasisTradeIncome
				.addSeries(AllBanksCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetAllBanksCurrentQtrBasisTradeIncome
				.addSeries(AllBanksACurrentQtrCapitalSavingsTS);
		this.dataSetAllBanksCurrentQtrBasisTradeIncome
				.addSeries(AllBanksCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"All Banks CDS Carry Trade Income", "Time Period",
				"US$thousands ", dataSetAllBanksCurrentQtrBasisTradeIncome,
				true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getAllBanksRMBSExposuresChart() {
		this.AllBanksRMBSExposuresSimulatedTS = new TimeSeries(
				"All Banks: Simulated RMBS Exposures", Quarter.class);
		this.AllBanksRMBSExposuresFDICDataTS = new TimeSeries(
				"All Banks: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.AllBanksMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"All Banks: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetAllBanksRMBSExposures = new TimeSeriesCollection();
		this.dataSetAllBanksRMBSExposures
				.addSeries(AllBanksRMBSExposuresSimulatedTS);
		this.dataSetAllBanksRMBSExposures
				.addSeries(AllBanksRMBSExposuresFDICDataTS);
		this.dataSetAllBanksRMBSExposures
				.addSeries(AllBanksMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"All Banks: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetAllBanksRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getAllBanksAccumulatedBasisTradeIncomeChart() {
		this.AllBanksAccumulatedCDSCarryTradeIncomeTS = new TimeSeries(
				"All Banks Cummulative CDS Carry Trade Income", Quarter.class);
		this.AllBanksAccumulatedCDSBasisIncomeTS = new TimeSeries(
				"All Banks Cummulative CDS Basis Income", Quarter.class);
		this.dataSetAllBanksAccumulatedBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetAllBanksAccumulatedBasisTradeIncome
				.addSeries(AllBanksAccumulatedCDSCarryTradeIncomeTS);
		this.dataSetAllBanksAccumulatedBasisTradeIncome
				.addSeries(AllBanksAccumulatedCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"All Banks Aggregate CDS Carry Trade Income Accumulation",
				"Time Period", "US$thousands ",
				dataSetAllBanksAccumulatedBasisTradeIncome, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getAllBanksAccumulatedCapitalSavingsChart() {
		this.AllBanksAccumulatedCapitalSavingsTS = new TimeSeries(
				"All Banks Cummulative Capital Savings", Quarter.class);
		this.dataSetAllBanksAccumulatedCapitalSavings = new TimeSeriesCollection();
		this.dataSetAllBanksAccumulatedCapitalSavings
				.addSeries(AllBanksAccumulatedCapitalSavingsTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"All Banks Aggregate Cummulative Capital Savings",
				"Time Period", "US$thousands ",
				dataSetAllBanksAccumulatedCapitalSavings, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getAllBanksRegulatoryCapitalLeverageTakingChart() {
		this.AllBanksRegulatoryCapitalLeverageTakingTS = new TimeSeries(
				"All Banks: Regulatory Capital Leverage Taking", Quarter.class);
		this.dataSetAllBanksRegulatoryCapitalLeverageTaking = new TimeSeriesCollection();
		this.dataSetAllBanksRegulatoryCapitalLeverageTaking
				.addSeries(AllBanksRegulatoryCapitalLeverageTakingTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"All Banks: Regulatory Capital Leverage Ratio", "Time Period",
				"Percentage Points % ",
				dataSetAllBanksRegulatoryCapitalLeverageTaking, true, true,
				false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF AGGREGATED
	// CHARTS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<CHARTING FOR
	// THE 12 MAJOR INDIVIDUAL
	// BANKS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

	private JFreeChart getBofACurrentQtrBasisTradeIncomeChart() {
		this.BofACurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"BofA: CDS Carry Trade Income", Quarter.class);
		this.BofAACurrentQtrCapitalSavingsTS = new TimeSeries(
				"BofA: Capital Savings", Quarter.class);
		this.BofACurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"BofA: CDS Basis Income ", Quarter.class);
		this.dataSetBofACurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetBofACurrentQtrBasisTradeIncome
				.addSeries(BofACurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetBofACurrentQtrBasisTradeIncome
				.addSeries(BofAACurrentQtrCapitalSavingsTS);
		this.dataSetBofACurrentQtrBasisTradeIncome
				.addSeries(BofACurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"BofA CDS Carry Trade Income", "Time Period", "US$thousands ",
				dataSetBofACurrentQtrBasisTradeIncome, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getBofARMBSExposuresChart() {
		this.BofARMBSExposuresSimulatedTS = new TimeSeries(
				"BofA: Simulated RMBS Exposures", Quarter.class);
		this.BofARMBSExposuresFDICDataTS = new TimeSeries(
				"BofA: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.BofAMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"BofA: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetBofARMBSExposures = new TimeSeriesCollection();
		this.dataSetBofARMBSExposures.addSeries(BofARMBSExposuresSimulatedTS);
		this.dataSetBofARMBSExposures.addSeries(BofARMBSExposuresFDICDataTS);
		this.dataSetBofARMBSExposures
				.addSeries(BofAMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"BofA: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetBofARMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getBoNYCurrentQtrBasisTradeIncomeChart() {
		this.BoNYCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"BoNY: CDS Carry Trade Income", Quarter.class);
		this.BoNYACurrentQtrCapitalSavingsTS = new TimeSeries(
				"BoNY: Capital Savings", Quarter.class);
		this.BoNYCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"BoNY: CDS Basis Income ", Quarter.class);
		this.dataSetBoNYCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetBoNYCurrentQtrBasisTradeIncome
				.addSeries(BoNYCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetBoNYCurrentQtrBasisTradeIncome
				.addSeries(BoNYACurrentQtrCapitalSavingsTS);
		this.dataSetBoNYCurrentQtrBasisTradeIncome
				.addSeries(BoNYCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"BoNY CDS Carry Trade Income", "Time Period", "US$thousands ",
				dataSetBoNYCurrentQtrBasisTradeIncome, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getBoNYRMBSExposuresChart() {
		this.BoNYRMBSExposuresSimulatedTS = new TimeSeries(
				"BoNY: Simulated RMBS Exposures", Quarter.class);
		this.BoNYRMBSExposuresFDICDataTS = new TimeSeries(
				"BoNY: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.BoNYMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"BoNY: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetBoNYRMBSExposures = new TimeSeriesCollection();
		this.dataSetBoNYRMBSExposures.addSeries(BoNYRMBSExposuresSimulatedTS);
		this.dataSetBoNYRMBSExposures.addSeries(BoNYRMBSExposuresFDICDataTS);
		this.dataSetBoNYRMBSExposures
				.addSeries(BoNYMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"BoNY: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetBoNYRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getCitibankCurrentQtrBasisTradeIncomeChart() {
		this.CitibankCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"Citibank: CDS Carry Trade Income", Quarter.class);
		this.CitibankACurrentQtrCapitalSavingsTS = new TimeSeries(
				"Citibank: Capital Savings", Quarter.class);
		this.CitibankCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"Citibank: CDS Basis Income ", Quarter.class);
		this.dataSetCitibankCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetCitibankCurrentQtrBasisTradeIncome
				.addSeries(CitibankCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetCitibankCurrentQtrBasisTradeIncome
				.addSeries(CitibankACurrentQtrCapitalSavingsTS);
		this.dataSetCitibankCurrentQtrBasisTradeIncome
				.addSeries(CitibankCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Citibank CDS Carry Trade Income", "Time Period",
				"US$thousands ", dataSetCitibankCurrentQtrBasisTradeIncome,
				true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getCitibankRMBSExposuresChart() {
		this.CitibankRMBSExposuresSimulatedTS = new TimeSeries(
				"Citibank: Simulated RMBS Exposures", Quarter.class);
		this.CitibankRMBSExposuresFDICDataTS = new TimeSeries(
				"Citibank: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.CitibankMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"Citibank: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetCitibankRMBSExposures = new TimeSeriesCollection();
		this.dataSetCitibankRMBSExposures
				.addSeries(CitibankRMBSExposuresSimulatedTS);
		this.dataSetCitibankRMBSExposures
				.addSeries(CitibankRMBSExposuresFDICDataTS);
		this.dataSetCitibankRMBSExposures
				.addSeries(CitibankMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Citibank: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetCitibankRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getDeutscheCurrentQtrBasisTradeIncomeChart() {
		this.DeutscheCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"Deutsche: CDS Carry Trade Income", Quarter.class);
		this.DeutscheACurrentQtrCapitalSavingsTS = new TimeSeries(
				"Deutsche: Capital Savings", Quarter.class);
		this.DeutscheCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"Deutsche: CDS Basis Income ", Quarter.class);
		this.dataSetDeutscheCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetDeutscheCurrentQtrBasisTradeIncome
				.addSeries(DeutscheCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetDeutscheCurrentQtrBasisTradeIncome
				.addSeries(DeutscheACurrentQtrCapitalSavingsTS);
		this.dataSetDeutscheCurrentQtrBasisTradeIncome
				.addSeries(DeutscheCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Deutsche CDS Carry Trade Income", "Time Period",
				"US$thousands ", dataSetDeutscheCurrentQtrBasisTradeIncome,
				true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getDeutscheRMBSExposuresChart() {
		this.DeutscheRMBSExposuresSimulatedTS = new TimeSeries(
				"Deutsche: Simulated RMBS Exposures", Quarter.class);
		this.DeutscheRMBSExposuresFDICDataTS = new TimeSeries(
				"Deutsche: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.DeutscheMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"Deutsche: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetDeutscheRMBSExposures = new TimeSeriesCollection();
		this.dataSetDeutscheRMBSExposures
				.addSeries(DeutscheRMBSExposuresSimulatedTS);
		this.dataSetDeutscheRMBSExposures
				.addSeries(DeutscheRMBSExposuresFDICDataTS);
		this.dataSetDeutscheRMBSExposures
				.addSeries(DeutscheMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Deutsche: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetDeutscheRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getGoldmanCurrentQtrBasisTradeIncomeChart() {
		this.GoldmanCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"Goldman: CDS Carry Trade Income", Quarter.class);
		this.GoldmanACurrentQtrCapitalSavingsTS = new TimeSeries(
				"Goldman: Capital Savings", Quarter.class);
		this.GoldmanCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"Goldman: CDS Basis Income ", Quarter.class);
		this.dataSetGoldmanCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetGoldmanCurrentQtrBasisTradeIncome
				.addSeries(GoldmanCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetGoldmanCurrentQtrBasisTradeIncome
				.addSeries(GoldmanACurrentQtrCapitalSavingsTS);
		this.dataSetGoldmanCurrentQtrBasisTradeIncome
				.addSeries(GoldmanCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Goldman CDS Carry Trade Income", "Time Period",
				"US$thousands ", dataSetGoldmanCurrentQtrBasisTradeIncome,
				false, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getGoldmanRMBSExposuresChart() {
		this.GoldmanRMBSExposuresSimulatedTS = new TimeSeries(
				"Goldman: Simulated RMBS Exposures", Quarter.class);
		this.GoldmanRMBSExposuresFDICDataTS = new TimeSeries(
				"Goldman: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.GoldmanMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"Goldman: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetGoldmanRMBSExposures = new TimeSeriesCollection();
		this.dataSetGoldmanRMBSExposures
				.addSeries(GoldmanRMBSExposuresSimulatedTS);
		this.dataSetGoldmanRMBSExposures
				.addSeries(GoldmanRMBSExposuresFDICDataTS);
		this.dataSetGoldmanRMBSExposures
				.addSeries(GoldmanMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Goldman: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetGoldmanRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getHSBCCurrentQtrBasisTradeIncomeChart() {
		this.HSBCCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"HSBC: CDS Carry Trade Income", Quarter.class);
		this.HSBCACurrentQtrCapitalSavingsTS = new TimeSeries(
				"HSBC: Capital Savings", Quarter.class);
		this.HSBCCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"HSBC: CDS Basis Income ", Quarter.class);
		this.dataSetHSBCCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetHSBCCurrentQtrBasisTradeIncome
				.addSeries(HSBCCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetHSBCCurrentQtrBasisTradeIncome
				.addSeries(HSBCACurrentQtrCapitalSavingsTS);
		this.dataSetHSBCCurrentQtrBasisTradeIncome
				.addSeries(HSBCCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"HSBC CDS Carry Trade Income", "Time Period", "US$thousands ",
				dataSetHSBCCurrentQtrBasisTradeIncome, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getHSBCRMBSExposuresChart() {
		this.HSBCRMBSExposuresSimulatedTS = new TimeSeries(
				"HSBC: Simulated RMBS Exposures", Quarter.class);
		this.HSBCRMBSExposuresFDICDataTS = new TimeSeries(
				"HSBC: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.HSBCMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"HSBC: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetHSBCRMBSExposures = new TimeSeriesCollection();
		this.dataSetHSBCRMBSExposures.addSeries(HSBCRMBSExposuresSimulatedTS);
		this.dataSetHSBCRMBSExposures.addSeries(HSBCRMBSExposuresFDICDataTS);
		this.dataSetHSBCRMBSExposures
				.addSeries(HSBCMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"HSBC: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetHSBCRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getJPMorganCurrentQtrBasisTradeIncomeChart() {
		this.JPMorganCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"JPMorgan: CDS Carry Trade Income", Quarter.class);
		this.JPMorganACurrentQtrCapitalSavingsTS = new TimeSeries(
				"JPMorgan: Capital Savings", Quarter.class);
		this.JPMorganCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"JPMorgan: CDS Basis Income ", Quarter.class);
		this.dataSetJPMorganCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetJPMorganCurrentQtrBasisTradeIncome
				.addSeries(JPMorganCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetJPMorganCurrentQtrBasisTradeIncome
				.addSeries(JPMorganACurrentQtrCapitalSavingsTS);
		this.dataSetJPMorganCurrentQtrBasisTradeIncome
				.addSeries(JPMorganCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"JPMorgan CDS Carry Trade Income", "Time Period",
				"US$thousands ", dataSetJPMorganCurrentQtrBasisTradeIncome,
				true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getJPMorganRMBSExposuresChart() {
		this.JPMorganRMBSExposuresSimulatedTS = new TimeSeries(
				"JPMorgan: Simulated RMBS Exposures", Quarter.class);
		this.JPMorganRMBSExposuresFDICDataTS = new TimeSeries(
				"JPMorgan: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.JPMorganMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"JPMorgan: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetJPMorganRMBSExposures = new TimeSeriesCollection();
		this.dataSetJPMorganRMBSExposures
				.addSeries(JPMorganRMBSExposuresSimulatedTS);
		this.dataSetJPMorganRMBSExposures
				.addSeries(JPMorganRMBSExposuresFDICDataTS);
		this.dataSetJPMorganRMBSExposures
				.addSeries(JPMorganMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"JPMorgan: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetJPMorganRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getKeybankCurrentQtrBasisTradeIncomeChart() {
		this.KeybankCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"Keybank: CDS Carry Trade Income", Quarter.class);
		this.KeybankACurrentQtrCapitalSavingsTS = new TimeSeries(
				"Keybank: Capital Savings", Quarter.class);
		this.KeybankCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"Keybank: CDS Basis Income ", Quarter.class);
		this.dataSetKeybankCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetKeybankCurrentQtrBasisTradeIncome
				.addSeries(KeybankCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetKeybankCurrentQtrBasisTradeIncome
				.addSeries(KeybankACurrentQtrCapitalSavingsTS);
		this.dataSetKeybankCurrentQtrBasisTradeIncome
				.addSeries(KeybankCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Keybank CDS Carry Trade Income", "Time Period",
				"US$thousands ", dataSetKeybankCurrentQtrBasisTradeIncome,
				true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getKeybankRMBSExposuresChart() {
		this.KeybankRMBSExposuresSimulatedTS = new TimeSeries(
				"Keybank: Simulated RMBS Exposures", Quarter.class);
		this.KeybankRMBSExposuresFDICDataTS = new TimeSeries(
				"Keybank: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.KeybankMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"Keybank: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetKeybankRMBSExposures = new TimeSeriesCollection();
		this.dataSetKeybankRMBSExposures
				.addSeries(KeybankRMBSExposuresSimulatedTS);
		this.dataSetKeybankRMBSExposures
				.addSeries(KeybankRMBSExposuresFDICDataTS);
		this.dataSetKeybankRMBSExposures
				.addSeries(KeybankMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Keybank: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetKeybankRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getMerrillCurrentQtrBasisTradeIncomeChart() {
		this.MerrillCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"Merrill: CDS Carry Trade Income", Quarter.class);
		this.MerrillACurrentQtrCapitalSavingsTS = new TimeSeries(
				"Merrill: Capital Savings", Quarter.class);
		this.MerrillCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"Merrill: CDS Basis Income ", Quarter.class);
		this.dataSetMerrillCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetMerrillCurrentQtrBasisTradeIncome
				.addSeries(MerrillCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetMerrillCurrentQtrBasisTradeIncome
				.addSeries(MerrillACurrentQtrCapitalSavingsTS);
		this.dataSetMerrillCurrentQtrBasisTradeIncome
				.addSeries(MerrillCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Merrill CDS Carry Trade Income", "Time Period",
				"US$thousands ", dataSetMerrillCurrentQtrBasisTradeIncome,
				false, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getMerrillRMBSExposuresChart() {
		this.MerrillRMBSExposuresSimulatedTS = new TimeSeries(
				"Merrill: Simulated RMBS Exposures", Quarter.class);
		this.MerrillRMBSExposuresFDICDataTS = new TimeSeries(
				"Merrill: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.MerrillMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"Merrill: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetMerrillRMBSExposures = new TimeSeriesCollection();
		this.dataSetMerrillRMBSExposures
				.addSeries(MerrillRMBSExposuresSimulatedTS);
		this.dataSetMerrillRMBSExposures
				.addSeries(MerrillRMBSExposuresFDICDataTS);
		this.dataSetMerrillRMBSExposures
				.addSeries(MerrillMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Merrill: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetMerrillRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getMorganStanleyCurrentQtrBasisTradeIncomeChart() {
		this.MorganStanleyCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"MorganStanley: CDS Carry Trade Income", Quarter.class);
		this.MorganStanleyACurrentQtrCapitalSavingsTS = new TimeSeries(
				"MorganStanley: Capital Savings", Quarter.class);
		this.MorganStanleyCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"MorganStanley: CDS Basis Income ", Quarter.class);
		this.dataSetMorganStanleyCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetMorganStanleyCurrentQtrBasisTradeIncome
				.addSeries(MorganStanleyCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetMorganStanleyCurrentQtrBasisTradeIncome
				.addSeries(MorganStanleyACurrentQtrCapitalSavingsTS);
		this.dataSetMorganStanleyCurrentQtrBasisTradeIncome
				.addSeries(MorganStanleyCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"MorganStanley CDS Carry Trade Income", "Time Period",
				"US$thousands ",
				dataSetMorganStanleyCurrentQtrBasisTradeIncome, true, true,
				false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getMorganStanleyRMBSExposuresChart() {
		this.MorganStanleyRMBSExposuresSimulatedTS = new TimeSeries(
				"MorganStanley: Simulated RMBS Exposures", Quarter.class);
		this.MorganStanleyRMBSExposuresFDICDataTS = new TimeSeries(
				"MorganStanley: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.MorganStanleyMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"MorganStanley: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetMorganStanleyRMBSExposures = new TimeSeriesCollection();
		this.dataSetMorganStanleyRMBSExposures
				.addSeries(MorganStanleyRMBSExposuresSimulatedTS);
		this.dataSetMorganStanleyRMBSExposures
				.addSeries(MorganStanleyRMBSExposuresFDICDataTS);
		this.dataSetMorganStanleyRMBSExposures
				.addSeries(MorganStanleyMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"MorganStanley: RMBS Exposures", "Time Period",
				"US$thousands ", dataSetMorganStanleyRMBSExposures, true, true,
				false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getWachoviaCurrentQtrBasisTradeIncomeChart() {
		this.WachoviaCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"Wachovia: CDS Carry Trade Income", Quarter.class);
		this.WachoviaACurrentQtrCapitalSavingsTS = new TimeSeries(
				"Wachovia: Capital Savings", Quarter.class);
		this.WachoviaCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"Wachovia: CDS Basis Income ", Quarter.class);
		this.dataSetWachoviaCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetWachoviaCurrentQtrBasisTradeIncome
				.addSeries(WachoviaCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetWachoviaCurrentQtrBasisTradeIncome
				.addSeries(WachoviaACurrentQtrCapitalSavingsTS);
		this.dataSetWachoviaCurrentQtrBasisTradeIncome
				.addSeries(WachoviaCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Wachovia CDS Carry Trade Income", "Time Period",
				"US$thousands ", dataSetWachoviaCurrentQtrBasisTradeIncome,
				true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getWachoviaRMBSExposuresChart() {
		this.WachoviaRMBSExposuresSimulatedTS = new TimeSeries(
				"Wachovia: Simulated RMBS Exposures", Quarter.class);
		this.WachoviaRMBSExposuresFDICDataTS = new TimeSeries(
				"Wachovia: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.WachoviaMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"Wachovia: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetWachoviaRMBSExposures = new TimeSeriesCollection();
		this.dataSetWachoviaRMBSExposures
				.addSeries(WachoviaRMBSExposuresSimulatedTS);
		this.dataSetWachoviaRMBSExposures
				.addSeries(WachoviaRMBSExposuresFDICDataTS);
		this.dataSetWachoviaRMBSExposures
				.addSeries(WachoviaMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Wachovia: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetWachoviaRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getWellsFargoCurrentQtrBasisTradeIncomeChart() {
		this.WellsFargoCurrentQtrCDSCarryTradeIncomeTS = new TimeSeries(
				"WellsFargo: CDS Carry Trade Income", Quarter.class);
		this.WellsFargoACurrentQtrCapitalSavingsTS = new TimeSeries(
				"WellsFargo: Capital Savings", Quarter.class);
		this.WellsFargoCurrentQtrCDSBasisIncomeTS = new TimeSeries(
				"WellsFargo: CDS Basis Income ", Quarter.class);
		this.dataSetWellsFargoCurrentQtrBasisTradeIncome = new TimeSeriesCollection();
		this.dataSetWellsFargoCurrentQtrBasisTradeIncome
				.addSeries(WellsFargoCurrentQtrCDSCarryTradeIncomeTS);
		this.dataSetWellsFargoCurrentQtrBasisTradeIncome
				.addSeries(WellsFargoACurrentQtrCapitalSavingsTS);
		this.dataSetWellsFargoCurrentQtrBasisTradeIncome
				.addSeries(WellsFargoCurrentQtrCDSBasisIncomeTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"WellsFargo CDS Carry Trade Income", "Time Period",
				"US$thousands ", dataSetWellsFargoCurrentQtrBasisTradeIncome,
				true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	private JFreeChart getWellsFargoRMBSExposuresChart() {
		this.WellsFargoRMBSExposuresSimulatedTS = new TimeSeries(
				"WellsFargo: Simulated RMBS Exposures", Quarter.class);
		this.WellsFargoRMBSExposuresFDICDataTS = new TimeSeries(
				"WellsFargo: Actual RMBS Exposures FDIC Data", Quarter.class);
		this.WellsFargoMezzanineRMBSExposuresFDICDataTS = new TimeSeries(
				"WellsFargo: Simulated Mezzanine Tranche RMBS Exposures",
				Quarter.class);
		this.dataSetWellsFargoRMBSExposures = new TimeSeriesCollection();
		this.dataSetWellsFargoRMBSExposures
				.addSeries(WellsFargoRMBSExposuresSimulatedTS);
		this.dataSetWellsFargoRMBSExposures
				.addSeries(WellsFargoRMBSExposuresFDICDataTS);
		this.dataSetWellsFargoRMBSExposures
				.addSeries(WellsFargoMezzanineRMBSExposuresFDICDataTS);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"WellsFargo: RMBS Exposures", "Time Period", "US$thousands ",
				dataSetWellsFargoRMBSExposures, true, true, false);
		chart.setBackgroundPaint(Color.white);

		XYPlot plot = chart.getXYPlot();

		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.black);
		plot.setRangeGridlinePaint(Color.black);
		plot.setAxisOffset(new RectangleInsets(7, 7, 7, 7));
		ValueAxis axis = plot.getDomainAxis();
		axis.setAutoRange(true);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis dAxis = (DateAxis) plot.getDomainAxis();
		// axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy"));
		dAxis.setDateFormatOverride(new QuarterDateFormat(
				TimeZone.getDefault(), quarters));
		DateTickUnit unit = new DateTickUnit(DateTickUnit.MONTH, 3,
				new QuarterDateFormat(TimeZone.getDefault(), quarters));
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(1028, 720));
		chartPanel.setMouseZoomable(true, false);

		return chart;
	}

	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF
	// CHARTING FOR THE MAJOR
	// BANKS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	public void updatedModelSimulationState() {

		/**
		 * THis method is used to update all the data and charts after each time
		 * step of the model
		 */

		int stYr = this.model.enviroment.getStartYear();
		int stQtr = this.model.enviroment.getStartQuarter();
		long time = Sim.getAbsoluteTime();

		int qtr = (((int) (time / 3)) + 1) - (((int) (time / 12)) * 4);
		int yr = (int) (time / 12) + stYr;

		// Quarter date = new Quarter(qtr, yr);

		Quarter date = new Quarter(
				((((int) (time / 3)) + 1) - (((int) (time / 12)) * 4)),
				(int) (time / 12) + stYr);

		/**
		 * This is used to update the time axis on the charts per quarter
		 */

		int dataItemIndex = ((int) time / 3) + (stYr - 2002) * 4 + (stQtr);

		int[] top5BanksIDRSSDList = new int[] { 413208, 476810, 480228, 852218,
				2182786 };

		int[] major12BanksIDRSSDList = new int[] { 214807, 280110, 413208,
				451965, 476810, 480228, 484422, 541101, 852218, 2182786,
				1225800, 1456501 };

		defaultCashSpreads = new ArrayList();
		defaultCreditDerivativesSpreads = new ArrayList();
		defaultCreditDerivativesPrices = new ArrayList();
		defaultCDSBasis = new ArrayList();

		if (time == 0 || (int) time % 3 == 0) {

			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<AGGREGATED DATA
			// COLLECTION FOR BANKS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

			for (int i = 0; i < model.getBankList().size(); i++) {
				if ((model.getBankList().get(i).getCdsProtectionBought() > 0)) {
					AllBanksRMBSExposuresSimulated += model.getBankList()
							.get(i).getTotalSubprimeRMBSExposure();
					AllBanksMezzanineRMBSExposuresSimulated += model
							.getBankList().get(i)
							.getTotalMezzanineTrancheExposure();
					AllBanksAccumulatedCDSCarryTradeIncome += (model
							.getBankList().get(i)
							.getTotalAccumulatedBasisIncome() + model
							.getBankList().get(i)
							.getTotalAccumulatedCapitalSavings());
					AllBanksAccumulatedCapitalSavings += model.getBankList()
							.get(i).getTotalAccumulatedCapitalSavings();
					AllBanksAccumulatedCDSBasisIncome += model.getBankList()
							.get(i).getTotalAccumulatedBasisIncome();
					AllBanksCurrentQtrCDSCarryTradeIncome += (model
							.getBankList().get(i).getCurrentQuaterBasisIncome() + model
							.getBankList().get(i).getBasisTradeCapitalSaving());
					AllBanksACurrentQtrCapitalSavings += model.getBankList()
							.get(i).getBasisTradeCapitalSaving();
					AllBanksCurrentQtrCDSBasisIncome += model.getBankList()
							.get(i).getCurrentQuaterBasisIncome();

					allBanksAggregatedExposuresProtectionBuyers += this.model
							.getBankList().get(i)
							.getTotalSubprimeRMBSExposure();
					allBanksAggregatedGrossSavingProtectionBuyers += this.model
							.getBankList().get(i).getBasisTradeTrancheSavings();
					allBanksAggregatedReInvestmentExposuresProtectionBuyers += this.model
							.getBankList().get(i).getSubprimeRMBSAssets();

				}
				if (((int) (time / 3)) == 0) {
					AllBanksRegulatoryCapitalLeverageTaking = ((allBanksAggregatedExposuresProtectionBuyers) / ((0.08 * allBanksAggregatedExposuresProtectionBuyers) - allBanksAggregatedGrossSavingProtectionBuyers));
				} else {
					AllBanksRegulatoryCapitalLeverageTaking = ((allBanksAggregatedReInvestmentExposuresProtectionBuyers) / ((0.08 * allBanksAggregatedReInvestmentExposuresProtectionBuyers) - allBanksAggregatedGrossSavingProtectionBuyers));
				}

			}

			for (int i = 0; i < model.getBankList().size(); i++) {
				for (int j = 0; j < top5BanksIDRSSDList.length; j++) {
					if ((model.getBankList().get(i).getRSSID() == top5BanksIDRSSDList[j])
							&& (model.getBankList().get(i)
									.getCdsProtectionBought() > 0)) {
						aggregateRMBSExposuresTop5BanksSimulated += model
								.getBankList().get(i)
								.getTotalSubprimeRMBSExposure();
						aggregateRMBSMezTrancheExposuresTop5BanksSimulated += model
								.getBankList().get(i)
								.getTotalMezzanineTrancheExposure();
						top5AccumulatedCDSCarryTradeIncome += (model
								.getBankList().get(i)
								.getTotalAccumulatedBasisIncome() + model
								.getBankList().get(i)
								.getTotalAccumulatedCapitalSavings());
						top5AccumulatedCapitalSavings += model.getBankList()
								.get(i).getTotalAccumulatedCapitalSavings();
						top5AccumulatedCDSBasisIncome += model.getBankList()
								.get(i).getTotalAccumulatedBasisIncome();
						top5CurrentQtrCDSCarryTradeIncome += (model
								.getBankList().get(i)
								.getCurrentQuaterBasisIncome() + model
								.getBankList().get(i)
								.getBasisTradeCapitalSaving());
						top5CurrentQtrCapitalSavings += model.getBankList()
								.get(i).getBasisTradeCapitalSaving();
						top5CurrentQtrCDSBasisIncome += model.getBankList()
								.get(i).getCurrentQuaterBasisIncome();

						top5AggregatedExposuresProtectionBuyers += this.model
								.getBankList().get(i)
								.getTotalSubprimeRMBSExposure();
						top5AggregatedGrossSavingProtectionBuyers += this.model
								.getBankList().get(i)
								.getBasisTradeTrancheSavings();
						top5AggregatedReInvestmentExposuresProtectionBuyers += this.model
								.getBankList().get(i).getSubprimeRMBSAssets();

					}
					if (((int) (time / 3)) == 0) {
						top5RegulatoryCapitalLeverageTaking = (top5AggregatedExposuresProtectionBuyers)
								/ ((0.08 * top5AggregatedExposuresProtectionBuyers) - top5AggregatedGrossSavingProtectionBuyers);
					} else {
						top5RegulatoryCapitalLeverageTaking = (top5AggregatedReInvestmentExposuresProtectionBuyers)
								/ ((0.08 * top5AggregatedReInvestmentExposuresProtectionBuyers) - top5AggregatedGrossSavingProtectionBuyers);
					}
				}
			}

			for (int i = 0; i < model.getBankList().size(); i++) {
				for (int j = 0; j < major12BanksIDRSSDList.length; j++) {
					if ((model.getBankList().get(i).getRSSID() == major12BanksIDRSSDList[j])
							&& (model.getBankList().get(i).getRSSID() == major12BanksIDRSSDList[j])
							&& (model.getBankList().get(i)
									.getCdsProtectionBought() > 0)) {
						Major12RMBSExposuresSimulated += model.getBankList()
								.get(i).getTotalSubprimeRMBSExposure();
						Major12MezzanineRMBSExposuresSimulated += model
								.getBankList().get(i)
								.getTotalMezzanineTrancheExposure();
						Major12CurrentQtrCDSCarryTradeIncome += (model
								.getBankList().get(i)
								.getCurrentQuaterBasisIncome() + model
								.getBankList().get(i)
								.getBasisTradeCapitalSaving());
						Major12ACurrentQtrCapitalSavings += model.getBankList()
								.get(i).getBasisTradeCapitalSaving();
						Major12CurrentQtrCDSBasisIncome += model.getBankList()
								.get(i).getCurrentQuaterBasisIncome();
					}

				}
			}

			for (int i = 0; i < model.getBankList().size(); i++) {
				if (model.getBankList().get(i).getRSSID() == 9999999) {
					OtherBanksRMBSExposuresSimulated += model.getBankList()
							.get(i).getTotalSubprimeRMBSExposure();
					OtherBanksMezzanineRMBSExposuresSimulated += model
							.getBankList().get(i)
							.getTotalMezzanineTrancheExposure();
					OtherBanksCurrentQtrCDSCarryTradeIncome += (model
							.getBankList().get(i).getCurrentQuaterBasisIncome() + model
							.getBankList().get(i).getBasisTradeCapitalSaving());
					OtherBanksACurrentQtrCapitalSavings += model.getBankList()
							.get(i).getBasisTradeCapitalSaving();
					OtherBanksCurrentQtrCDSBasisIncome += model.getBankList()
							.get(i).getCurrentQuaterBasisIncome();
				}
			}

			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF AGGREGATED
			// DATA
			// COLLECTION FOR BANKS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<AGGREGATED DATA
			// COLLECTION FOR TOP 5, MAJOR 12 AND ALL OTHER
			// BANKS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

			/**
			 * In the following sequence of for-loops and if-statements
			 * 
			 * to set the FDIC aggregated data first go through the bank list to
			 * pick out the IDRSSD of the banks determine if there are more than
			 * 5 banks in the lsit of banks if so then determine if the model
			 * uses All balance sheet RMBS or just the structured RMBS if All
			 * RMBS go through the Top12AndOthers All RMBS FDIC data set in the
			 * database manager class if Structured RMBS only then use the
			 * Top12AndOthers Structured RMBS data set in the database manager
			 * class
			 * 
			 * since the the FDIC data sets start from 2002Q1, to get the
			 * appropriate data point use the equation ((int) time/3) + (startYr
			 * - 2002)*4 + (startQtr - 1) therefore simulation time 0 with
			 * simulation start year of 2006Q1 will give and index value of 0 +
			 * 4*(2006-2002) + 1-1 = 16 Since the value at index 0 of each row
			 * of the 2-D array containing the data is each bank's IDRSSD, the
			 * value at index 16 is the value of RMBS at 2006Q1
			 * 
			 * to be set here are Major12RMBSExposuresFDICData +=
			 * model.dataManager.get[]; OtherBanksRMBSExposuresFDICData +=
			 * model.dataManager.get[]; AllBanksRMBSExposuresFDICData
			 * aggregateRMBSExposuresTop5BanksFDICData +=
			 * model.dataManager.get[];
			 * 
			 */
			int datasetLength = 13; // since the aggregates are being based on
									// the dataset specified in the for of the
									// major 12 banks and all others,
									// there should be a total of 13 banks' time
									// series data
			if (model.enviroment.isAllRMBSBoolean() == true) {
				datasetLength = model.dataManager
						.getTop12andOthersTotalRMBSData().length;
			} else {
				datasetLength = model.dataManager
						.getTop12andOthersStructuredRMBSData().length;
			}

			for (int b = 0; b < model.getBankList().size(); b++) {
				// for(int j = 0; j < major12BanksIDRSSDList.length; j++){
				if (model.getBankList().size() > 5
						&& model.enviroment.isAllRMBSBoolean() == true) {
					for (int k = 0; k < major12BanksIDRSSDList.length; k++) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(b).getRSSID() == major12BanksIDRSSDList[k])
									&& (model.getBankList().get(b).getRSSID() == model.dataManager
											.getTop12andOthersTotalRMBSData()[j][0])) {
								Major12RMBSExposuresFDICData += model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
								for (int m = 0; m < top5BanksIDRSSDList.length; m++) {
									if ((model.getBankList().get(b).getRSSID() == top5BanksIDRSSDList[m])
											&& (model.getBankList().get(b)
													.getRSSID() == model.dataManager
													.getTop12andOthersTotalRMBSData()[j][0])) {
										aggregateRMBSExposuresTop5BanksFDICData += model.dataManager
												.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
									}
								}
							}

						}
					}
					OtherBanksRMBSExposuresFDICData = model.dataManager
							.getTop12andOthersTotalRMBSData()[datasetLength - 1][dataItemIndex];
					AllBanksRMBSExposuresFDICData = OtherBanksRMBSExposuresFDICData
							+ Major12RMBSExposuresFDICData;
				} else if (model.getBankList().size() <= 5
						&& model.enviroment.isAllRMBSBoolean() == true) {
					for (int j = 0; j < model.dataManager
							.getTop12andOthersTotalRMBSData().length; j++) {
						if ((model.getBankList().get(b).getRSSID() == model.dataManager
								.getTop12andOthersTotalRMBSData()[j][0])) {
							aggregateRMBSExposuresTop5BanksFDICData += model.dataManager
									.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
						}
					}
					AllBanksRMBSExposuresFDICData = aggregateRMBSExposuresTop5BanksFDICData;
				} else if (model.getBankList().size() > 5
						&& model.enviroment.isAllRMBSBoolean() == false) {
					for (int k = 0; k < major12BanksIDRSSDList.length; k++) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(b).getRSSID() == major12BanksIDRSSDList[k])
									&& (model.getBankList().get(b).getRSSID() == model.dataManager
											.getTop12andOthersStructuredRMBSData()[j][0])) {
								Major12RMBSExposuresFDICData += model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
								for (int m = 0; m < top5BanksIDRSSDList.length; m++) {
									if ((model.getBankList().get(b).getRSSID() == top5BanksIDRSSDList[m])
											&& (model.getBankList().get(b)
													.getRSSID() == model.dataManager
													.getTop12andOthersStructuredRMBSData()[j][0])) {
										aggregateRMBSExposuresTop5BanksFDICData += model.dataManager
												.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
									}
								}
							}

						}
					}

					OtherBanksRMBSExposuresFDICData = model.dataManager
							.getTop12andOthersStructuredRMBSData()[datasetLength - 1][dataItemIndex];
					AllBanksRMBSExposuresFDICData = OtherBanksRMBSExposuresFDICData
							+ Major12RMBSExposuresFDICData;
				} else if (model.getBankList().size() <= 5
						&& model.enviroment.isAllRMBSBoolean() == false) {
					for (int j = 0; j < model.dataManager
							.getTop12andOthersStructuredRMBSData().length; j++) {
						if ((model.getBankList().get(b).getRSSID() == model.dataManager
								.getTop12andOthersStructuredRMBSData()[j][0])) {
							aggregateRMBSExposuresTop5BanksFDICData += model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
						}
					}
					AllBanksRMBSExposuresFDICData = aggregateRMBSExposuresTop5BanksFDICData;
				}
			}

			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF AGGREGATED
			// DATA
			// COLLECTION FOR TOP 5, MAJOR 12 AND ALL OTHER
			// BANKS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<START OF INDIVIDUAL
			// BANKS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

			/**
			 * Now to update the time series data values for each of the
			 * individual banks
			 * 
			 */

			if (model.getBankList().size() > 5
					&& model.enviroment.isAllRMBSBoolean() == true) {
				for (int bnk = 0; bnk < model.getBankList().size(); bnk++) {
					if (model.getBankList().get(bnk).getRSSID() == 480228) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								BofACurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								BofAACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								BofACurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								BofARMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								BofAMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								BofARMBSExposuresFDICData = model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 541101) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								BoNYCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								BoNYACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								BoNYCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								BoNYRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								BoNYMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								BoNYRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 476810) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								CitibankCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								CitibankACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								CitibankCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								CitibankRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								CitibankMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								CitibankRMBSExposuresFDICData = this.model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 214807) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								DeutscheCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								DeutscheACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								DeutscheCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								DeutscheRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								DeutscheMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								DeutscheRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 2182786) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								GoldmanCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								GoldmanACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getTotalAccumulatedCapitalSavings();
								GoldmanCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								GoldmanRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								GoldmanMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								GoldmanRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 413208) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								HSBCCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								HSBCACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								HSBCCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								HSBCRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								HSBCMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								HSBCRMBSExposuresFDICData = this.model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 852218) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								JPMorganCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								JPMorganACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								JPMorganCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								JPMorganRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								JPMorganMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								JPMorganRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 280110) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								KeybankCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								KeybankACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								KeybankCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								KeybankRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								KeybankMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								KeybankRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 1225800) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								MerrillCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								MerrillACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								MerrillCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								MerrillRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								MerrillMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								MerrillRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 1456501) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								MorganStanleyCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								MorganStanleyACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								MorganStanleyCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								MorganStanleyRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								MorganStanleyMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								MorganStanleyRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 484422) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								WachoviaCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								WachoviaACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								WachoviaCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								WachoviaRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								WachoviaMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								WachoviaRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 451965) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersTotalRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersTotalRMBSData()[j][0])) {
								WellsFargoCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								WellsFargoACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								WellsFargoCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								WellsFargoRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								WellsFargoMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								WellsFargoRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersTotalRMBSData()[j][dataItemIndex];
							}
						}

					}
				}
			}

			if (model.getBankList().size() > 5
					&& model.enviroment.isAllRMBSBoolean() == false) {
				for (int bnk = 0; bnk < model.getBankList().size(); bnk++) {
					if (model.getBankList().get(bnk).getRSSID() == 480228) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								BofACurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								BofAACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								BofACurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								BofARMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								BofAMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								BofARMBSExposuresFDICData = model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 541101) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								BoNYCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								BoNYACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								BoNYCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								BoNYRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								BoNYMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								BoNYRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 476810) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								CitibankCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								CitibankACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								CitibankCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								CitibankRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								CitibankMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								CitibankRMBSExposuresFDICData = this.model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 214807) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								DeutscheCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								DeutscheACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								DeutscheCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								DeutscheRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								DeutscheMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								DeutscheRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 2182786) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								GoldmanCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								GoldmanACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getTotalAccumulatedCapitalSavings();
								GoldmanCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								GoldmanRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								GoldmanMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								GoldmanRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 413208) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								HSBCCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								HSBCACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								HSBCCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								HSBCRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								HSBCMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								HSBCRMBSExposuresFDICData = this.model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 852218) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								JPMorganCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								JPMorganACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								JPMorganCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								JPMorganRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								JPMorganMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								JPMorganRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 280110) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								KeybankCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								KeybankACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								KeybankCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								KeybankRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								KeybankMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								KeybankRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 1225800) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								MerrillCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								MerrillACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								MerrillCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								MerrillRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								MerrillMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								MerrillRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 1456501) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								MorganStanleyCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								MorganStanleyACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								MorganStanleyCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								MorganStanleyRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								MorganStanleyMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								MorganStanleyRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 484422) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								WachoviaCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								WachoviaACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								WachoviaCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								WachoviaRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								WachoviaMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								WachoviaRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}

					if (model.getBankList().get(bnk).getRSSID() == 451965) {
						for (int j = 0; j < model.dataManager
								.getTop12andOthersStructuredRMBSData().length; j++) {
							if ((model.getBankList().get(bnk).getRSSID() == model.dataManager
									.getTop12andOthersStructuredRMBSData()[j][0])) {
								WellsFargoCurrentQtrCDSCarryTradeIncome = (model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome() + model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving());
								WellsFargoACurrentQtrCapitalSavings = model
										.getBankList().get(bnk)
										.getBasisTradeCapitalSaving();
								WellsFargoCurrentQtrCDSBasisIncome = model
										.getBankList().get(bnk)
										.getCurrentQuaterBasisIncome();
								WellsFargoRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalSubprimeRMBSExposure();
								WellsFargoMezzanineRMBSExposuresSimulated = model
										.getBankList().get(bnk)
										.getTotalMezzanineTrancheExposure();
								WellsFargoRMBSExposuresFDICData = model.dataManager
										.getTop12andOthersStructuredRMBSData()[j][dataItemIndex];
							}
						}

					}
				}
			}

			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<END OF INDIVIDUAL
			// BANKS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

			// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<START OF
			// SPREADS>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

			// cash spreads
			for (int i = 0; i < model.dataManager.getDefaultCashSpreads()
					.size(); i++) {
				double cSpread;
				if (((int) (time / 3)) == i) {
					for (int j = 0; j < model.dataManager
							.getDefaultCashSpreads().get(i).length; j++) {
						cSpread = model.dataManager.getDefaultCashSpreads()
								.get(i)[j];
						defaultCashSpreads.add(cSpread);
					}
				}
			}
			if (defaultCashSpreads.size() == 5) {
				for (int i = 0; i < defaultCashSpreads.size(); i++) {
					switch (i) {
					case 0:
						defaultcashSpreadsAAA = (Double) defaultCashSpreads
								.get(0);
						break;
					case 1:
						defaultcashSpreadsAA = (Double) defaultCashSpreads
								.get(1);
						break;
					case 2:
						defaultcashSpreadsA = (Double) defaultCashSpreads
								.get(2);
						break;
					case 3:
						defaultcashSpreadsBBB = (Double) defaultCashSpreads
								.get(3);
						break;
					case 4:
						defaultcashSpreadsBBB3 = (Double) defaultCashSpreads
								.get(4);
						break;
					}

				}

			}

			// Credit Derivatives spreads
			for (int i = 0; i < model.dataManager
					.getDefaultCreditDerivativesSpreads().size(); i++) {
				double cdSpread;
				double cdsPrice;
				if (((int) (time / 3)) == i) {
					for (int j = 0; j < model.dataManager
							.getDefaultCreditDerivativesSpreads().get(i)[0].length; j++) {
						cdSpread = model.dataManager
								.getDefaultCreditDerivativesSpreads().get(i)[0][j];
						cdsPrice = model.dataManager
								.getDefaultCreditDerivativesSpreads().get(i)[1][j];
						defaultCreditDerivativesSpreads.add(cdSpread);
						defaultCreditDerivativesPrices.add(cdsPrice);
					}
				}
			}
			if (defaultCreditDerivativesSpreads.size() == 5) {
				for (int j = 0; j < defaultCreditDerivativesSpreads.size(); j++) {
					switch (j) {
					case 0:
						defaultcreditDerivativesSpreadsAAA = (Double) defaultCreditDerivativesSpreads
								.get(0);
						defaultcreditDerivativesPricesAAA = (Double) defaultCreditDerivativesPrices
								.get(0);
						break;
					case 1:
						defaultcreditDerivativesSpreadsAA = (Double) defaultCreditDerivativesSpreads
								.get(1);
						defaultcreditDerivativesPricesAA = (Double) defaultCreditDerivativesPrices
								.get(1);
						break;
					case 2:
						defaultcreditDerivativesSpreadsA = (Double) defaultCreditDerivativesSpreads
								.get(2);
						defaultcreditDerivativesPricesA = (Double) defaultCreditDerivativesPrices
								.get(2);
						break;
					case 3:
						defaultcreditDerivativesSpreadsBBB = (Double) defaultCreditDerivativesSpreads
								.get(3);
						defaultcreditDerivativesPricesBBB = (Double) defaultCreditDerivativesPrices
								.get(3);
						break;
					case 4:
						defaultcreditDerivativesSpreadsBBB3 = (Double) defaultCreditDerivativesSpreads
								.get(4);
						defaultcreditDerivativesPricesBBB3 = (Double) defaultCreditDerivativesPrices
								.get(4);
						break;
					}

				}

			}

			for (int i = 0; i < defaultCashSpreads.size(); i++) {
				if (defaultCashSpreads.size() == defaultCreditDerivativesSpreads
						.size()) {
					double basis = (Double) defaultCreditDerivativesSpreads
							.get(i) - (Double) defaultCashSpreads.get(i);
					defaultCDSBasis.add(basis);
				}
			}

			if (defaultCreditDerivativesSpreads.size() == 5) {
				for (int x = 0; x < defaultCDSBasis.size(); x++) {
					switch (x) {
					case 0:
						defaultCDSBasisAAA = (Double) defaultCDSBasis.get(0);
						break;
					case 1:
						defaultCDSBasisAA = (Double) defaultCDSBasis.get(1);
						break;
					case 2:
						defaultCDSBasisA = (Double) defaultCDSBasis.get(2);
						break;
					case 3:
						defaultCDSBasisBBB = (Double) defaultCDSBasis.get(3);
						break;
					case 4:
						defaultCDSBasisBBB3 = (Double) defaultCDSBasis.get(4);
						break;
					}

				}
			}

			// libor
			defaultLIBORRate = model.dataManager.getDefaultLIBORRate()[((int) (time / 3))];

			updateTimeSeries(date);
			setDefault5TrancheSpreadTimeSeries(date);
		}

		// <<<<<<<<<<<<<<<<<<<<<<Aggregated Data Time
		// Series>>>>>>>>>>>>>>>>>>>>>>

		// all banks

		this.repaint();

		deleteTimeseriesDataPlaceHolders();

	}// End of method
}
