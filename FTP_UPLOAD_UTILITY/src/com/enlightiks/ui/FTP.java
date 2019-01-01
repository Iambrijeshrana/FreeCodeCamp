package com.enlightiks.ui;

import javax.swing.JFrame;

import java.awt.GridLayout;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPasswordField;

import java.awt.Font;

import com.enlightiks.utilitiy.loadproperties.LoadProperties;
import com.enlightiks.utilitiy.properties.WriteProperties;
import com.enlightiks.utility.FTP.FTPUpload;
import com.jgoodies.forms.factories.DefaultComponentFactory;

import java.awt.Window.Type;

import javax.swing.ImageIcon;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.SwingConstants;

import org.apache.log4j.Logger;

/**
 * FTPUtility Upload Main Ui
 * 
 * @author Praveen
 */

public class FTP {

	private JFrame frmFtpUtility;
	private JTextField txtFtpServer;
	private JTextField txtFtpUserName;
	private JTextField txtClientName;
	private JTextField txtFilePattern;
	private JTextField txtUploadPath;
	private JTextField txtFTPPort;
	private JPasswordField txtFtpPwd;
	private JButton btnChooseDir;
	private JSpinner frequencySpinner;
	private static Logger log = null;
	private String empty="";
	private WriteProperties writeProperties;
	private LoadProperties loadProperties;
	
	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
				try {
					log = Logger.getLogger(FTP.class);
					log.info("Main Method started.");
			        log.info("Loaded Main Properties file");
			        LoadProperties loadProperties = new LoadProperties();
			        String reconfig = loadProperties.loadMainProperties();
			        log.info("Reconfigure Status  :"+reconfig);
					if(reconfig.equals("Y")){
						WriteProperties properties = new WriteProperties();
						properties.writeInMainProperertiesFile();
					    FTP window = new FTP();
					    window.frmFtpUtility.setVisible(true);
				   }else{
					try{
						//ftpUpload.ftpUploadFiles();
						FTPUpload.main(null);
						}
						catch(Exception exception)
						{
							log.error("FTP: main Exception is : ",exception);
						}
					}
				} catch (Exception e) {
					
					log.error("main exception is : ",e);
				}
	}
	
	public FTP() {
		writeProperties = new WriteProperties();
		loadProperties= new LoadProperties();
		log.info("initialize() calling.. ");
		initialize();
	}
	
	/**
	 * create frame
	 * To create frame (Main window).
	 * 
	 */
	private void initialize() {
		log.info("initialize() started... ");
		log.info("initialize the UI ....");
		
		frmFtpUtility = new JFrame();
		// to fix the size
		frmFtpUtility.setResizable(false);
		frmFtpUtility.setType(Type.UTILITY);
		// set tittle
		frmFtpUtility.setTitle("FTP Upload");
		// set size
		frmFtpUtility.setBounds(100, 100, 651, 485);
		// to close ui
		frmFtpUtility.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmFtpUtility.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		final JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		frmFtpUtility.getContentPane().add(panel);
		panel.setLayout(null);
		
		final JLabel lblConfigureFtpHeader = new JLabel("Configure FTP Details ");
		lblConfigureFtpHeader.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblConfigureFtpHeader.setBounds(21, 78, 172, 30);
		panel.add(lblConfigureFtpHeader);
		
		final JLabel lblFTPServerName = new JLabel("FTP Server Name");
		lblFTPServerName.setBounds(24, 119, 131, 14);
		panel.add(lblFTPServerName);
		
		final String lineImage = "images//line.gif";
		
		txtFtpServer = new JTextField();
		txtFtpServer.setBounds(170, 119, 126, 20);
		panel.add(txtFtpServer);
		txtFtpServer.setColumns(15);
		
		final JLabel lblFTPPort = new JLabel("FTP Port");
		lblFTPPort.setBounds(312, 119, 50, 14);
		panel.add(lblFTPPort);
		
		txtFTPPort = new JTextField();
		txtFTPPort.setBounds(393, 116, 35, 20);
		panel.add(txtFTPPort);
		txtFTPPort.setColumns(7);
		
		final JLabel lblUserName = new JLabel("User Name");
		lblUserName.setBounds(24, 144, 83, 14);
		panel.add(lblUserName);
		
		txtFtpUserName = new JTextField();
		txtFtpUserName.setBounds(170, 145, 126, 20);
		panel.add(txtFtpUserName);
		txtFtpUserName.setColumns(15);
		
		final JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(312, 144, 80, 14);
		panel.add(lblPassword);
		
		final JButton btnReset = new JButton("RESET");
		//To reset all FTP Connection
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.info("Reset button clicked. ");
				txtFtpServer.setText(empty);
				txtFtpPwd.setText(empty);
				txtFtpUserName.setText(empty);
				txtFTPPort.setText(empty);
				log.info("all field reset");
			}
		});
		btnReset.setBounds(489, 141, 89, 20);
		panel.add(btnReset);
		
		final JLabel lblClientName = new JLabel("ClientName");
		lblClientName.setBounds(23, 301, 83, 14);
		panel.add(lblClientName);
		
		txtClientName = new JTextField();
		txtClientName.setBounds(111, 298, 126, 20);
		panel.add(txtClientName);
		txtClientName.setColumns(10);
		
		final JLabel lblFilePattern = new JLabel("FilePattern");
		lblFilePattern.setBounds(290, 328, 67, 14);
		panel.add(lblFilePattern);
		
		txtFilePattern = new JTextField();
		txtFilePattern.setBounds(357, 325, 82, 20);
		panel.add(txtFilePattern);
		txtFilePattern.setColumns(10);
		
		final JLabel lblUploadPath = new JLabel("Upload Path");
		lblUploadPath.setBounds(23, 328, 83, 14);
		panel.add(lblUploadPath);
		
		txtUploadPath = new JTextField();
		txtUploadPath.setBounds(111, 325, 126, 20);
		panel.add(txtUploadPath);
		txtUploadPath.setColumns(10);
		
		// create reset button
		final JButton btnReset2 = new JButton("RESET");
		//To reset client name file pattern and file upload path
		btnReset2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtUploadPath.setText(empty);
				txtClientName.setText(empty);
				txtFilePattern.setText(empty);
			}
		});
		btnReset2.setBounds(463, 325, 89, 20);
		panel.add(btnReset2);
		
		//To upload the file on ftp server
		final JButton btnSaveConfig = new JButton("SUBMIT");
		btnSaveConfig.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.info("SUBMIT Button Triggered");
				if(emptyFieldValidation())
				{	
					// Get server name from ui
					final String ftpServerName=txtFtpServer.getText();
					// Get server port number from ui
					final String ftpPort=txtFTPPort.getText();
					// Get Ftp server User name from ui
					final String ftpUserName=txtFtpUserName.getText();
					// Get ftp server password
					final String ftpPasswd=String.valueOf(txtFtpPwd.getPassword());
					// pathe from where we upload the file
					final String ftpFileUploadPath=txtUploadPath.getText();
					// Get shedular in millsec. to set the time for thread
					final String ftpScheduler=frequencySpinner.getValue().toString();
					// directory name where we upload the file
					final String clientName=txtClientName.getText();
					// Get file pattern
					final String filePattern=txtFilePattern.getText();

					try {
						// pass the the value from ui to WriteProperties class to write the provided information in properties file
						log.info("writeInProperertiesFile() calling.. ");
						writeProperties.writeInProperertiesFile(ftpPort,
								ftpScheduler, clientName, ftpFileUploadPath,
								filePattern, ftpServerName, ftpUserName,
								ftpPasswd);
					} catch (Exception e1) {
						log.error("writeInProperertiesFile() Exception is : ", e1);
					}

					try{
						WriteProperties properties = new WriteProperties();
						properties.writeInMainProperertiesFile();
						frmFtpUtility.setVisible(false);
						//ftpUpload.ftpUploadFiles(); 
						FTPUpload.main(null);
					}
					catch(Exception exception)
					{
						log.error("initialize:actionPerformed : SUBMIT",exception);
					}
				}
				else
				{
					log.info(" No value enter by the user. ");
					JOptionPane.showMessageDialog(frmFtpUtility, "Fields Should not be Empty");
				}
				log.info(empty);
			}
		});
		btnSaveConfig.setBounds(196, 391, 89, 20);
		panel.add(btnSaveConfig);
		
		txtFtpPwd = new JPasswordField();
		txtFtpPwd.setBounds(393, 145, 83, 20);
		panel.add(txtFtpPwd);
		
		final JLabel separator4 = new JLabel(empty);
		separator4.setIcon(new ImageIcon(lineImage));
		separator4.setBounds(0, 427, 700, 2);
		panel.add(separator4);
		
		final JLabel separator = new JLabel(empty);
		separator.setIcon(new ImageIcon(lineImage));
		separator.setBounds(0, 247, 700, 2);
		panel.add(separator);
		
		final JLabel separator_1 = new JLabel(empty);
		separator_1.setIcon(new ImageIcon(lineImage));
		separator_1.setBounds(0, 378, 700, 2);
		panel.add(separator_1);
		
		// create exit button to close the main ui
		final JButton btnExit = new JButton("CANCEL");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.info("Cancel Button Triggered");
				if(JOptionPane.showConfirmDialog(btnExit.getParent().getParent(),"Do you want to Exit","Confirm",2,3)==0)
				{
					System.exit(0);
					log.info("Main UI Closed.");
				}
			}
		});
		btnExit.setBounds(313, 390, 89, 20);
		panel.add(btnExit);
		
		final JLabel lblFilePatternHeader = new JLabel("Configure File Upload Path ");
		lblFilePatternHeader.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFilePatternHeader.setBounds(23, 260, 200, 30);
		panel.add(lblFilePatternHeader);
		
		String footerLabel = loadProperties.getEnliLabel();
		final JLabel lblEnlightiksc = DefaultComponentFactory
				.getInstance().createTitle(footerLabel);
		lblEnlightiksc.setBounds(206, 433, 252, 14);
		panel.add(lblEnlightiksc);
		
		btnChooseDir = new JButton("...");
		btnChooseDir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chooseDirPath();
			}
		});
		btnChooseDir.setBounds(238, 325, 24, 20);
		panel.add(btnChooseDir);
		
		final JLabel lblSchedulerFrequency = new JLabel("Frequency(Milli Seconds)");
		lblSchedulerFrequency.setBounds(23, 219, 151, 14);
		panel.add(lblSchedulerFrequency);
		
		final SpinnerModel spinnermodel=new SpinnerNumberModel(0,0,60000,1000);
		frequencySpinner = new JSpinner(spinnermodel);
		frequencySpinner.setBounds(170, 216, 72, 20);
		panel.add(frequencySpinner);
		
		final JLabel separato = new JLabel(empty);
		separato.setIcon(new ImageIcon(lineImage));
		separato.setBounds(0, 70, 1000, 21);
		panel.add(separato);
		
		final JLabel separator_2 = new JLabel(empty);
		separator_2.setIcon(new ImageIcon(lineImage));
		separator_2.setBounds(0, 172, 700, 2);
		panel.add(separator_2);
		
		final JLabel lblConfigureScheduler = new JLabel("Configure Scheduler");
		lblConfigureScheduler.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblConfigureScheduler.setBounds(23, 185, 172, 30);
		panel.add(lblConfigureScheduler);
		
		// add header image
		final JLabel lblNewLabel1 = new JLabel(empty);
		lblNewLabel1.setIcon(new ImageIcon("images//icon1.png"));
		lblNewLabel1.setBounds(20, 0, 294, 69);
		panel.add(lblNewLabel1);
				
		final JLabel lblNewLabel = new JLabel(empty);
		lblNewLabel.setIcon(new ImageIcon("images//enlightikslogo.jpg"));
		lblNewLabel.setBounds(383, 0, 252, 80);
		panel.add(lblNewLabel);
		
		final String star = "*";
		final JLabel label = new JLabel(star);
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setForeground(Color.RED);
		label.setBounds(15, 143, 8, 14);
		panel.add(label);
		
		final JLabel label_1 = new JLabel(star);
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setForeground(Color.RED);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_1.setBounds(15, 119, 8, 14);
		panel.add(label_1);
		
		final JLabel label_2 = new JLabel(star);
		label_2.setHorizontalAlignment(SwingConstants.LEFT);
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_2.setBounds(301, 119, 8, 14);
		panel.add(label_2);
		
		final JLabel label_3 = new JLabel(star);
		label_3.setHorizontalAlignment(SwingConstants.LEFT);
		label_3.setForeground(Color.RED);
		label_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_3.setBounds(301, 144, 8, 14);
		panel.add(label_3);
		
		final JLabel label_4 = new JLabel(star);
		label_4.setHorizontalAlignment(SwingConstants.LEFT);
		label_4.setForeground(Color.RED);
		label_4.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_4.setBounds(15, 301, 8, 14);
		panel.add(label_4);
		
		final JLabel label_5 = new JLabel(star);
		label_5.setHorizontalAlignment(SwingConstants.LEFT);
		label_5.setForeground(Color.RED);
		label_5.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_5.setBounds(280, 328, 8, 14);
		panel.add(label_5);
		
		final JLabel label_6 = new JLabel(star);
		label_6.setHorizontalAlignment(SwingConstants.LEFT);
		label_6.setForeground(Color.RED);
		label_6.setFont(new Font("Tahoma", Font.BOLD, 12));
		label_6.setBounds(15, 327, 8, 14);
		panel.add(label_6);
		log.info("initialization of UI Completed ....");
	}

	/**
	 * chooseDirPath
	 * To chosse the path by user 
	 * from where we upload the file.
	 * 
	 */
	private void chooseDirPath()
	{
		try
		{
		JFileChooser jfc= new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int rval=jfc.showOpenDialog(this.frmFtpUtility);
		if(rval==JFileChooser.APPROVE_OPTION){
			txtUploadPath.setText(jfc.getSelectedFile().getPath());
		}
		if(rval==JFileChooser.CANCEL_OPTION){
			txtUploadPath.setText(empty);
		}
		}
		catch(Exception e)
		{
			log.error("chooseDirPath Exception is : ",e);
		}
	}
	
	/**
	 * for valdation
	 * check whether all the neccessary information provided or not.
	 * 
	 */
		private boolean emptyFieldValidation()
	{
		boolean flag=false;
	
		if( txtFtpServer.getText().length()>0 && txtFtpPwd.getPassword().length>0 && txtFTPPort.getText().length()>0  )
		{
			if( txtUploadPath.getText().length()>0 && txtFilePattern.getText().length()>0 && txtClientName.getText().length()>0  )
			{
					flag=true;
			}	
		}
		return flag;
	}
}
