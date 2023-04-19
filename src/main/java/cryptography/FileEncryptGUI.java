package cryptography;

import java.awt.EventQueue;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JInternalFrame;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileSystemView;

public class FileEncryptGUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FileEncryptGUI window = new FileEncryptGUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FileEncryptGUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize(){
		frame = new JFrame();
		frame.setBackground(new Color(245, 245, 245));
		frame.getContentPane().setForeground(new Color(245, 245, 245));
		frame.getContentPane().setBackground(new Color(245, 245, 245));
		frame.setBounds(300, 300, 750, 402);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel1 = new JLabel("Select  ------> Encrypt -------> Upload");
		lblNewLabel1.setBounds(10, 21, 522, 31);
		lblNewLabel1.setForeground(new Color(0, 0, 128));
		lblNewLabel1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		frame.getContentPane().add(lblNewLabel1);
		
		textField = new JTextField();
		textField.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		textField.setToolTipText("Choose Your File");
		textField.setBounds(10, 63, 434, 41);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		

		JLabel lblNewLabel4 = new JLabel("");
		lblNewLabel4.setForeground(new Color(64, 0, 0));
		lblNewLabel4.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		lblNewLabel4.setBounds(10, 280, 522, 31);
		frame.getContentPane().add(lblNewLabel4);
		
		JButton btnNewButton_1 = new JButton("Choose File");
		btnNewButton_1.setBounds(454, 72, 132, 28);
		frame.getContentPane().add(btnNewButton_1);
		
		textField_1 = new JPasswordField();
		textField_1.setToolTipText("Enter Secret Key Here");
		textField_1.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 18));
		textField_1.setColumns(10);
		textField_1.setBounds(10, 126, 434, 41);
		frame.getContentPane().add(textField_1);
		
		JButton btnNewButton_1_1 = new JButton("Encrypt EHR");
		btnNewButton_1_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnNewButton_1_1.setBounds(454, 139, 132, 28);
		frame.getContentPane().add(btnNewButton_1_1);
		
		JButton btnNewButton_1_1_1 = new JButton("Upload to Cloud");
		btnNewButton_1_1_1.setBounds(10, 209, 143, 41);
		frame.getContentPane().add(btnNewButton_1_1_1);
		
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
				// invoke the showsOpenDialog function to show the save dialog
	            int r = fileChooser.showOpenDialog(null);
	 
	            // if the user selects a file
	            if (r == JFileChooser.APPROVE_OPTION)
	 
	            {
	                // set the label to the path of the selected file
	            	textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
	            }
	            // if the user cancelled the operation
	            else
	            	textField.setText("the user cancelled the operation");
	        
				
			}
		});
		
		btnNewButton_1_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				ZipFiles zf=new ZipFiles();
				try {
									
					zf.encryptedFile(textField_1.getText(), textField.getText(), textField.getText()+".enc");
					lblNewLabel4.setText("File is Encrypted Successfully !!");
					
				} catch (IOException | GeneralSecurityException e1) {
					// TODO Auto-generated catch block
					lblNewLabel4.setText("" + e1);
					e1.printStackTrace();
				}
			}
		});
		
		btnNewButton_1_1_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				DriveQuickUpload upload = new DriveQuickUpload();
				try {
					upload.upload(textField.getText()+".enc");
					lblNewLabel4.setText("Successfully uploaded to Cloud Server");
				} catch (GeneralSecurityException | InterruptedException | IOException e1) {
					// TODO Auto-generated catch block
					lblNewLabel4.setText("" + e1);
					e1.printStackTrace();
				}
				
			}
		});
		
	}
}
