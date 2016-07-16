package com.cs521.team3.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.cs521.team3.dbconnection.DBConnection;
import com.cs521.team3.model.ReviewRatingModel;
import com.cs521.team3.model.ReviewandRating;

public class ReviewRatingUI extends JFrame {

	private JPanel contentPane;
	private JTextField reviewTextField;
	private JTextField textField;
	private JTextField ratingTextField;
	private DBConnection dbconn;
	private JTable table;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReviewRatingUI frame = new ReviewRatingUI();
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
	public ReviewRatingUI() {
		try{
			dbconn=new DBConnection();
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Error:"+ex,"Error",JOptionPane.ERROR_MESSAGE);
		}
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setPreferredSize(new Dimension(100, 75));
		
		JLabel lblNewLabel = new JLabel("Enter Review");
		panel.add(lblNewLabel);
		
		reviewTextField = new JTextField();
		panel.add(reviewTextField);
		reviewTextField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Enter Rating");
		panel.add(lblNewLabel_1);
		
		ratingTextField = new JTextField();
		panel.add(ratingTextField);
		ratingTextField.setColumns(10);
		
		JButton btnDone = new JButton("DONE");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String review = reviewTextField.getText();
					int rating = Integer.parseInt(ratingTextField.getText());

					System.out.println(rating);
					System.out.println(review);
					if ((review != null && review.trim().length() > 0)&& rating > 0) {
					try{
						DBConnection dao = new DBConnection();
				         dao.insertReviewRating(review, rating);
				         
						}
					catch(SQLException ex){
						System.out.println(ex.getMessage());
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					
					try{
						List<ReviewandRating> reviewrating = null;
						System.out.println("table review rating");
						reviewrating = dbconn.getReviewRating();
						ReviewRatingModel mod = new ReviewRatingModel(reviewrating);
						table.setModel(mod);
						
						}catch(Exception ex){
							JOptionPane.showMessageDialog(ReviewRatingUI.this, "Error:"+ex,"Error",JOptionPane.ERROR_MESSAGE);

						}
					// create the model and update the "table"
					/*EmployeeTableModel model = new EmployeeTableModel(employees);
					
					table.setModel(model);*/
					}
				
					
				} catch (Exception exc) {
					JOptionPane.showMessageDialog(ReviewRatingUI.this, "Error: " + exc, "Error", JOptionPane.ERROR_MESSAGE); 
				}
				

			}
		});
		panel.add(btnDone);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}

}
