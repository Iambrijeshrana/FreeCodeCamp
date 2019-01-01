package com.enlightiks.FTPUI;

import javax.swing.JFrame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;

import java.awt.Color;

import javax.swing.JButton;
import com.enlightiks.utilitiy.loadproperties.LoadProperties;
import com.enlightiks.utility.download.FTPClientDownloadFiles;
import com.enlightiks.utility.download.FTPClientFiles;
import com.enlightiks.utility.download.FTPClientsName;
import com.enlightiks.utility.pojo.FTPModelUtility;
import com.enlightiks.utility.pojo.SortedListModel;
import com.enlightks.utility.interfaces.DirectoryNameInterface;
import com.enlightks.utility.interfaces.DownloadFilesInterface;
import com.enlightks.utility.interfaces.FileNameInterface;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Window.Type;
import javax.swing.ImageIcon;
import org.apache.log4j.Logger;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * FTPUtility Download Main Ui
 * To download file from ftp server
 * @author BRIJESH
 */

@SuppressWarnings({ "rawtypes", "serial", "static-access", "deprecation", "unchecked" })
public class FTPUI extends JPanel {
	private JButton addButton;
	private JButton addAll;
	private JButton removeButton;
	private JButton removeAll;
	private JButton btnChooseDir;
	private JButton downloadButton;
	public static JProgressBar progressBar;
	public static JTextField txtDownloadPath;
	private static JFrame frmFtpUtility;
	private static Logger log = null;
	private SortedListModel sourceListModel;
	private SortedListModel destListModel;
	private JList sourceList;
	private JList destList;
	private JComboBox search;
	private final String empty = "";
	private final String format = "All Format";
	private LoadProperties loadProperties;
	
	/**
	 * Launch the application.
	 * @wbp.parser.entryPoint
	 */
	
	public static void main(String[] args) {
		log = Logger.getLogger(FTPUI.class);
		log.info("Main Method started.");
		createFrame();
		log.info("Main Method complete.");
	}

	/**
	 * create frame
	 * To create frame (Main window).
	 */
	private static void createFrame() {
		try {
			log.info("createFrame() started....");
			FTPUI otp = new FTPUI();
			frmFtpUtility = new JFrame();
			frmFtpUtility.dispose();
			// to fix the size
			frmFtpUtility.setResizable(false);
			frmFtpUtility.setType(Type.UTILITY);
			// to set title
			frmFtpUtility.setTitle("FTP Download");
			// set size of window (frame)
			frmFtpUtility.setBounds(350, 350, 751, 530);
			// to close ui 
			frmFtpUtility.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frmFtpUtility.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
			// set jframe window in the middle.
			frmFtpUtility.setLocationByPlatform(true);
			// set frmae inside the center of window
			frmFtpUtility.getContentPane().add(otp, BorderLayout.CENTER);
			// to make window visible
			otp.frmFtpUtility.setVisible(true);
			log.info("createFrame() complete.");			
		} catch (Exception e) {
			log.error("createFrame() Exception is :" , e);
		}
	}

	public FTPUI() {
		super();
		sourceListModel = new SortedListModel();
		loadProperties = new LoadProperties();
		initialize();
		log.info("initialize() complete");
	}

	/**
	 * initialize
	 * 
	 * create ui components on the frame window.
	 */
	private void initialize() {
		log.info("initialize() started..");
		// to get image folder path
		String imagePath = loadProperties.getImagePath();
		
		setBorder(BorderFactory.createEtchedBorder());
		
		// create lable on frame window
		final JLabel fileType = new JLabel("File Type");
		fileType.setBounds(337, 79, 151, 14);
		add(fileType);
		
		// create comboBox to get file according to file format
		final JComboBox selectfileType = new JComboBox();
		selectfileType.setBounds(400, 76, 167, 22);
		selectfileType.addItem(format);
		
		selectfileType.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				log.info("select File format .");
				List<String> list = new ArrayList<String>();
				String item = (String) selectfileType.getSelectedItem();
				log.info(item+" file formate selected by user.");
				// if file formate selected by the user
				// count total number of item of File Type comboBox
				int count = selectfileType.getItemCount();
					if(count != 0){

					//Get all files of selected file format
					if(!item.equals(format)){
					for(String str : FTPModelUtility.getClientFileList()){
						if(str.endsWith("."+item)){
							list.add(str);
					    	}
				    	}
					}
					// if no file format is selected by the user
					else{
						// clear all files from 'Files Available in FTP' ListBox
						sourceListModel.clear();
						// Add Files to 'Files Available in FTP' ListBox
						for(String str : FTPModelUtility.getClientFileList()){
							sourceListModel.addAll(str);
						}
					}
					// add all files to the Source ListBox of selected file format
					if(list.size() != 0){
						// clear all files from 'Files Available in FTP' ListBox
						sourceListModel.clear();
						// Add Files to 'Files Available in FTP' ListBox
						for(String s : list){
							sourceListModel.addAll(s);
						}
					}
				
				}
			}
			
		});
		add(selectfileType);
		
		// To add Refresh Button to refresh the connection
		final JButton refresh = new JButton(empty);
		refresh.setIcon(new ImageIcon(imagePath+"//ref.png"));
		refresh.setBounds(626, 76, 24, 23);
		
		// To refresh the UI Connection
		refresh.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				log.info("refresh button clicked.");
				// set Select Client comboBox Item
				search.setSelectedItem("Select Client");
				// remove all item from File Type comboBox
				selectfileType.removeAllItems();
				// add 'All Formate' item to File Type ComboBox
				selectfileType.addItem(format);
				// set File Type ComboBox value
				selectfileType.setSelectedIndex(0);
				// clear 'Files Available in FTP' ListBox
				sourceListModel.clear();
				// set download path empty
				txtDownloadPath.setText(empty);
				
				// Disable all button on ui if 'Selected Files to Download' is empty 
				//except reset, refresh, add and addAll
				if (destListModel.getSize() == 0) {
					buttonDisable();
				} else {
					buttonEnable();
				}
				// disable add, addAll Button if 'Files Available in FTP' ListBox is empty
				if (sourceListModel.getSize() != 0) {
					addButtonEnable();
				} else {
					addButtonDisable();
				}
			}
		});
		add(refresh);
		
		//add label 'Select Client' for comboBox to select the client
		final JLabel selectClient = new JLabel("Select Client");
		selectClient.setBounds(23, 79, 151, 14);
		add(selectClient);

		// create coboBox to select client
		search = new JComboBox();
		search.setBounds(105, 76, 167, 22);
		search.addItem("Select Client");
		// get Client List
		final DirectoryNameInterface directoryNames = new FTPClientsName();
		List<String> getFTPClientName = new ArrayList<String>();
		getFTPClientName = directoryNames.getDirectoryNames();
		// add client to comboBox
		for (final String str : getFTPClientName) {
			search.addItem(str);
		}
		
		// get List of Item by selecting client from ComboBox
		search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.info("client name selected from the combobox.");
				// get selected comboBox value
				String item = (String) search.getSelectedItem();
				
				if (!item.equals("Select Client")) {
				//	final String directoryName = (String) search.getSelectedItem();
					FTPModelUtility.setDirectoryName(item);
					// clear 'Files Available in Ftp' ListBox
					sourceListModel.clear();
					List<String> destList = new ArrayList<String>();
					for (int k = 0; k <= destListModel.getSize() - 1; k++) {
						String str = (String) destListModel.getElementAt(k);
						destList.add(str);
					}
					FTPModelUtility.setDestList(destList);
					try {
						//clear file extension list
						FTPModelUtility.getFileExtList().clear();
						
						// Get file list of selected client
						final FileNameInterface fileName = new FTPClientFiles();
						List<String> clientFileList = new ArrayList<String>();
						clientFileList = fileName.getFilesName();
						
						FTPModelUtility.setClientFileList(clientFileList);
						clientFileList.removeAll(destList);
						Set<String> s2 = new HashSet<String>();
						if (clientFileList.size() != 0) {
							String[] queries2 = new String[clientFileList.size()];
							queries2 = clientFileList.toArray(queries2);
							// Get file name from Array one by one.
							for (String str : queries2) {
								sourceListModel.addAll(str);
							}
							// remove all item from File Type comboBox
							selectfileType.removeAllItems();
							log.info("all item removed from File Type ComboBox.");
							
							// Add items to File Type comboBox.
							s2=FTPModelUtility.getFileExtList();
							selectfileType.addItem(format);
							for(String sf : s2){
								selectfileType.addItem(sf);
							}
							log.info("item added to File Type ComboBox.");
							
							FTPModelUtility.getDestList().clear();
							sourceList.setModel(sourceListModel);
							addButtonEnable();
						} 
						
						// enable Download, Remove, RemoveAll and DownloadPath button if 'Selected Files to Download'
						// ListBox not empty
						if (destListModel.getSize() != 0)
							buttonEnable();
						else
							buttonDisable();
						
					} catch (Exception e) {
						log.error("search Listener Exception is : ", e);;
						e.printStackTrace();
					}
				}
			}
		});
		add(search);

		// create progressBar and set it min and max value
		progressBar = new JProgressBar(0, 100);
		progressBar.setStringPainted(true);
		progressBar.setBounds(20, 434, 710, 35);
		// make progressBar Invisible
		progressBar.setVisible(false);
		progressBar.setBackground(Color.white);
		add(progressBar);

		
		// create label for ListBox
		final JLabel listBoxLabel1 = new JLabel("Files Available in FTP");
		listBoxLabel1.setFont(new Font("", Font.PLAIN, 15));
		listBoxLabel1.setBounds(80, 105, 172, 30);
		add(listBoxLabel1);

		// create label for ListBox to download file
		final JLabel listBoxLabel2 = new JLabel("Files Selected to Download");
		listBoxLabel2.setFont(new Font("", Font.PLAIN, 15));
		listBoxLabel2.setBounds(504, 105, 202, 30);
		add(listBoxLabel2);

		// create add button 
		addButton = new JButton(">>");
		addButton.setBounds(338, 166, 55, 19);
		// disable button
		addButton.setEnabled(false);
		
		//to add selected item from Files Available in FTP ListBox to 'selected Files to Download' ListBox
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				log.info("add button clicked.");
				//get selected item from 'Files Available in FTP' ListBox
				final Object selected[] = sourceList.getSelectedValues();
				
				// add selected item to 'selected Files to Download' ListBox
				for (int i = 0; i <= selected.length-1; i++) {
					destListModel.addAll(selected[i]);
					System.out.println("selected[i] : "+selected[i]);
				}
				
				// clear selected item from 'Files Available in FTP' ListBox
				clearSourceSelected();
				
				if (destListModel.getSize() != 0) {
					buttonEnable();
				} else {
					buttonDisable();
				}
				
				// disable add, addAll button if 'Files Available in FTP' ListBox is empty
				if (sourceListModel.getSize() != 0) {
					addButtonEnable();
				} else {
					addButtonDisable();
				}
			}
		});
		add(addButton);

		// create addAll button
		addAll = new JButton(">>>");
		addAll.setBounds(338, 210, 55, 19);
		// disable button
		addAll.setEnabled(false);
		
		//to add all item from Files Available in FTP ListBox to 'selected Files to Download' ListBox
		addAll.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				log.info("addAll button clicked.");
				// get no. of item available in 'Files Available in FTP' ListBox
				final int size = sourceList.getModel().getSize();
				
				// add all item to 'selected files to download' ListBox
				for (int i = 0; i < size; i++) {
					final Object item = sourceList.getModel().getElementAt(i);
					destListModel.add(item);
				}
				
				//enable remove, removeAll, downloadPath button
				buttonEnable();
				// disable add, AddAll button
				addButtonDisable();
				// clear 'Files Available in FTP' ListBox
				sourceListModel.clear();
			}
		});
		add(addAll);

		setLayout(new BorderLayout());
		destListModel = new SortedListModel();
		destList = new JList(destListModel);
		
		// create remove button
		removeButton = new JButton("<<");
		removeButton.setBounds(338, 275, 55, 19);
		removeButton.setEnabled(false);
		
		//to add selected item from 'selected Files to Download' ListBox to Files Available in FTP ListBox
		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				log.info("remove button clicked.");
				List<String> list = new ArrayList<String>();
				// Get selected item from 'selected Files to Download' ListBox
				Object selected[] = destList.getSelectedValues();
				//get selected client value from 'Select Client' comboBox
				String client = (String) search.getSelectedItem();
				
				//add selected items of same client
				for(int j=0; j<=selected.length-1; j++){
					String s = (String) selected[j];
					String[] st = s.split("/");
					if(st[1].equals(client)){
						sourceListModel.add(selected[j]);
						list.add(s);
					}
				}
				
				// remove selected items from 'selected Files to download' ListBox
				for(Object obj : list){
					destListModel.removeElement(obj);
				}
				
				// popup message if all item not belongs to the same client
				if(list.size() == 0){
					log.info("All selected file not belongs to selected client");
					JOptionPane.showMessageDialog(null, "All Selected File Not belongs to Client : "+client,
							"Message", JOptionPane.INFORMATION_MESSAGE);
				}
				
				// disable remove, removeAll, downloadPath button
				if (destListModel.getSize() != 0) {
					buttonEnable();
				} else {
					buttonDisable();
				}
				
				//enable add, addAll button if 'selected Files to download' ListBox not empty
				if (sourceListModel.getSize() != 0) {
					addButtonEnable();
				}
			}
		});
		add(removeButton);

		// create removeAll button
		removeAll = new JButton("<<<");
		removeAll.setBounds(338, 320, 55, 19);
		// disable removeAll button
		removeAll.setEnabled(false);
		
		//to add all item from 'selected Files to Download' ListBox to Files Available in FTP ListBox
		removeAll.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				log.info("removeAll button clicked.");
				// get selected client
				String client = (String) search.getSelectedItem();
				// get all item from 'selected Files to Download' ListBox
				int size = destListModel.getSize();
				
				// add all item to 'selected files to download' ListBox
				List<String> list = new ArrayList<String>();
				for (int i = 0; i < size; i++) {
					Object item = destListModel.getElementAt(i);
					String str = (String) item;
					String str1[] = str.split("/");
					if(str1[1].equals(client)){
						sourceListModel.add(destListModel.getElementAt(i));
					    list.add(str);
					}
				}
				
				// remove item from 'selected Files to download' ListBox
				for(Object obj : list){
					destListModel.removeElement(obj);
				}
				
				// popup message if all item not belongs to the selected client
				if(list.size() == 0){
					log.info("No file available in the Seleted Files to Download ListBox.");
					JOptionPane.showMessageDialog(null, "All File Not belongs to Client : "+client,
							"Message", JOptionPane.INFORMATION_MESSAGE);
				}
				
				// disable remove, removeAll, downloadPath button if 'selected Files to download' ListBox is  empty
				if(destListModel.getSize() != 0){
					log.info("Seleted Files to Download ListBox Not empty.");
					buttonEnable();
					log.info("remove, removeAll, downloadPath button enable.");
				}else{
					log.info("Seleted Files to Download ListBox empty.");
					buttonDisable();
					log.info("remove, removeAll, downloadPath button disable.");
				}
				
				// enable add, addAll button 
				if(sourceListModel.getSize() != 0){
					log.info("Available Files in FTP ListBox Not empty.");
					addButtonEnable();
					log.info("add, addAll button enable.");
				}else{
					log.info("Available Files in FTP ListBox empty.");
					addButtonDisable();
					log.info("add, addAll button disable.");
				}
			}
		});
		add(removeAll);

		setLayout(new BorderLayout());
		//
		sourceList = new JList(sourceListModel);

		// create ListBox for 'Files Available in FTP' label
		final JScrollPane scrollPaneSourec = new JScrollPane();
		scrollPaneSourec.setBounds(23, 137, 250, 235);
		sourceList.setBorder(BorderFactory.createEtchedBorder());
		scrollPaneSourec.setViewportView(sourceList);
		add(scrollPaneSourec);

		// create ListBox for 'selected Files to download' label
		final JScrollPane scrollPaneDest = new JScrollPane();
		scrollPaneDest.setBounds(470, 137, 250, 235);
		destList.setBorder(BorderFactory.createEtchedBorder());
		scrollPaneDest.setViewportView(destList);
		add(scrollPaneDest);

		log.info("initialize the UI ....");

		// set frame window background color
		setBackground(Color.WHITE);
		setLayout(null);

		// create reset button
		final JButton reset = new JButton("Reset");
		reset.setBounds(355, 387,  80, 20);
		
		//To reset all FTP Connection
		reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				log.info("reset button clicked.");
				// set File Type comboBox 'All Format'
				selectfileType.setSelectedIndex(0);
				// set Select Client comboBox 'Select Client'
				search.setSelectedIndex(0);
				// remove all item from File Type comboBox
				selectfileType.removeAllItems();
				// set File Type comboBox 'All Format'
				selectfileType.addItem(format);
				//clear 'Files Available in FTP' ListBox 
				sourceListModel.clear();
				//clear 'Files Available in FTP' ListBox 
				destListModel.clear();
				
				//disable all button except reset, exit and refresh
				addButtonDisable();
				buttonDisable();
				
				FTPModelUtility.getDestList().clear();
			}
		});
		add(reset);
		
		// create download button
		downloadButton = new JButton("Download");
		downloadButton.setEnabled(false);
		downloadButton.setBounds(480, 387, 90, 20);
		
		// to download the file
		downloadButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				log.info("Download Button clicked.");
				// get seleted path by user to save the downloaded file
				final String fileDownloadPath = txtDownloadPath.getText();
				// set user selected path
				FTPModelUtility.setFtpFileDownloadPath(fileDownloadPath);
				
				// get list of file to download
				final List<String> list = new ArrayList<String>();
				final ListModel model = destList.getModel();
				for (int i = 0; i < model.getSize(); i++) {
					final String str = (String) model.getElementAt(i);
					list.add(str);
				}
				
				//set downloaded file list
				FTPModelUtility.setListfinal(list);
				refresh.setEnabled(false);
				// Visible progressBar on the UI
				progressBar.setVisible(true);
				
				
				// download file
				final DownloadFilesInterface clientDownloadFiles = new FTPClientDownloadFiles();
				clientDownloadFiles.downloadFiles();
				
				// popup message after downloading the file
				JOptionPane.showMessageDialog(null, "Downloading Complete",
						"Message", JOptionPane.INFORMATION_MESSAGE);
				// clear 'selected Files to Download' ListBox after downloading
				destListModel.clear();
				// clear list
				FTPModelUtility.getListfinal().clear();
				//set downloadPath empty
				txtDownloadPath.setText(empty);
				// make progressBar invisible from the ui
				progressBar.setVisible(false);
				// disable remove, removeAll, downloadPath button
				buttonDisable();
				refresh.setEnabled(true);
				
				/*try {
					LoadProperties loadProperties = new LoadProperties();
					final String to = loadProperties.getEmailTo();
					System.out.println("to value : "+to);
					final String subject = loadProperties.getSubject();
					System.out.println("subject : "+subject);
					final String body = loadProperties.getBody();
					MailServiceInterface mailService = new MailService();
					mailService.sentMail(to, subject, body);
				} catch (Exception ex) {
					log.info("sentMail : ", ex);
					ex.printStackTrace();
				}*/

			}
		});
		add(downloadButton);

		// create label for download path text field
		final JLabel downloadPath = new JLabel("Download Path");
		//downloadPath.setFont(new Font("Bold", Font.BOLD, 12));
		downloadPath.setBounds(20, 375, 200, 40);
		add(downloadPath);
		
		//create text field to see the user selected path
		txtDownloadPath = new JTextField();
		txtDownloadPath.setBounds(115, 387, 160, 21);
		txtDownloadPath.setEditable(false);
		add(txtDownloadPath);
		txtDownloadPath.setColumns(10);

		// create button to choose the path
		btnChooseDir = new JButton("...");
		// disable button
		btnChooseDir.setEnabled(false);
		btnChooseDir.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				chooseDirPath();
				if (!txtDownloadPath.getText().isEmpty()) {
					downloadButton.setEnabled(true);
				} else {
					downloadButton.setEnabled(false);
				}
			}
		});
		btnChooseDir.setBounds(280, 387, 24, 20);
		add(btnChooseDir);
		
		// create exit button 
		final JButton btnExit = new JButton("Exit");
		btnExit.setBounds(633, 387, 80, 20);
		//to close the ui
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent event) {
				log.info("exit button click to close the ui");
				log.info("popup window open");
				// popup message
				if (JOptionPane.showConfirmDialog(btnExit.getParent()
						.getParent(), "Do you want to Exit", "Confirm", 2, 3) == 0) {
					System.exit(0);
					log.info("Main ui closed");
				}
				log.info("popup window closed");
			}
		});
		add(btnExit);
		
		// add footer comment
		String footerLabel = loadProperties.getEnliLabel();
		final JLabel lblEnlightiksc = DefaultComponentFactory.getInstance()
				.createTitle(footerLabel);
		lblEnlightiksc.setBounds(260, 487, 752, 10);
		add(lblEnlightiksc);

		// to seprate the components on ui 
		final String lineImage = imagePath+"//line.gif";
		final JLabel separator1 = new JLabel(empty);
		separator1.setIcon(new ImageIcon(lineImage));
		separator1.setBounds(0, 60, 1000, 21);
		add(separator1);

		final JLabel separator2 = new JLabel(empty);
		separator2.setIcon(new ImageIcon(lineImage));
		separator2.setBounds(0, 97, 1000, 21);
		add(separator2);

		final JLabel separator3 = new JLabel(empty);
		separator3.setIcon(new ImageIcon(lineImage));
		separator3.setBounds(0, 368, 1000, 20);
		add(separator3);

		final JLabel separator4 = new JLabel(empty);
		separator4.setIcon(new ImageIcon(lineImage));
		separator4.setBounds(0, 463, 1000, 31);
		add(separator4);

		// add header image
		final JLabel lblNewLabel1 = new JLabel(empty);
		lblNewLabel1.setIcon(new ImageIcon(imagePath+"//icon1.png"));
		lblNewLabel1.setBounds(20, 0, 294, 69);
		add(lblNewLabel1);

		// add enlightiks logo
		final JLabel lblNewLabel = new JLabel(empty);
		
		lblNewLabel.setIcon(new ImageIcon(imagePath+"//enlightikslogo.jpg"));
		lblNewLabel.setBounds(484, 2, 292, 65);
		add(lblNewLabel);

		log.info("initialization of UI Completed ....");
	}

	/**
	 * to select the path by user to save the downloaded file
	 * 
	 */
	private void chooseDirPath() {
		try {
			log.info("Download Path Button clicked...");
			final JFileChooser jfc = new JFileChooser();
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			log.info("pop up window open");
			final int rval = jfc.showOpenDialog(this.frmFtpUtility);
			// set path to the text field
			if (rval == JFileChooser.APPROVE_OPTION) {
				txtDownloadPath.setText(jfc.getSelectedFile().getPath());
				log.info("popup window ok button clicked..");
				log.info("path selected.");
			}
			// set text field empty
			if (rval == JFileChooser.CANCEL_OPTION) {
				txtDownloadPath.setText(empty);
				log.info("popup window cancel button clicked..");
			}
		} catch (Exception e) {
			log.error("chooseDirPath", e);
		}
	}

	/**
	 * clearSourceSelected
	 * 
	 * to clear the selected item from 'Files Available in FTP' ListBox
	 */
	private void clearSourceSelected() {
		// get selected item
		final Object selected[] = sourceList.getSelectedValues();
		// remove selected item
		for (int i = selected.length - 1; i >= 0; --i) {
			sourceListModel.removeElement(selected[i]);
		}
		sourceList.getSelectionModel().clearSelection();
		log.info("selected item cleared from 'Files Available in FTP' List Box");
	}
	
	/**
	 * buttonEnable
	 * 
	 * to enable remove, removeAll, downloadPath button
	 */
	private void buttonEnable(){
		removeButton.setEnabled(true);
		removeAll.setEnabled(true);
		btnChooseDir.setEnabled(true);
		log.info("remove, removeAll, download and download path button enable.");
	}
	
	/**
	 * buttonDisable
	 * 
	 * to disable remove, removeAll, downloadPath button
	 */
	private void buttonDisable(){
		removeButton.setEnabled(false);
		removeAll.setEnabled(false);
		btnChooseDir.setEnabled(false);
		downloadButton.setEnabled(false);
		txtDownloadPath.setText(empty);
		log.info("remove, removeAll, download and download path button disable.");
	}
	
	/**
	 * addButtonEnable
	 * 
	 * to enable add, addAll button
	 */
	private void addButtonEnable(){
		addButton.setEnabled(true);
		addAll.setEnabled(true);
		log.info("add, addAll button enable.");
	}
	
	/**
	 * addButtonEnable
	 * 
	 * to disable add, addAll button
	 */
	private void addButtonDisable(){
		addButton.setEnabled(false);
		addAll.setEnabled(false);
		log.info("add, addAll button disable.");
	}
}