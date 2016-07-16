package com.cs521.team3.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class BookStoreApp {

	public JFrame frame;
	private JTextField txtBookStore;

	/**
	 * Launch the application.
	 */
	public static void loadUI() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookStoreApp window = new BookStoreApp();
					window.frame.setSize(450,300);
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
	public BookStoreApp() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JButton btnSearchBooks = new JButton("Search Books");
		btnSearchBooks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchPage searchPage = new SearchPage();
				searchPage.setSize(500,500);
				searchPage.frmSearchBooks.setVisible(true);
			}
		});
		btnSearchBooks.setBounds(10, 46, 200, 50);
		frame.getContentPane().add(btnSearchBooks);
		
		txtBookStore = new JTextField();
		txtBookStore.setBackground(Color.LIGHT_GRAY);
		txtBookStore.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 16));
		txtBookStore.setText("BOOK STORE");
		txtBookStore.setBounds(172, 11, 122, 20);
		txtBookStore.setEditable(false);
		frame.getContentPane().add(txtBookStore);
		txtBookStore.setColumns(10);
		
		JButton btnAdminFunctionality = new JButton("Admin Functionality");
		btnAdminFunctionality.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminUI adminUI = new AdminUI();
				adminUI.setSize(300,300);
				adminUI.frame.setVisible(true);
			}
		});
		btnAdminFunctionality.setBounds(10, 197, 200, 50);
		if(LoginPage.userName.equalsIgnoreCase("admin")){
			frame.getContentPane().add(btnAdminFunctionality);
		}
		
		JButton btnTrackOrder = new JButton("Track Order");
		btnTrackOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TrackOrderFrame trackOrderFrame = new TrackOrderFrame();
				trackOrderFrame.setSize(500, 500);
				trackOrderFrame.setVisible(true);
			}
		});
		btnTrackOrder.setBounds(10, 123, 200, 50);
		frame.getContentPane().add(btnTrackOrder);
		
		JButton btnNewButton = new JButton("Wish List");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				WishListUI wishList = new WishListUI();
				wishList.setSize(500, 500);
				wishList.setVisible(true);
			}
		});
		btnNewButton.setBounds(224, 46, 200, 50);
		frame.getContentPane().add(btnNewButton);
		
		JButton btnReviewAndRating = new JButton("Review and Rating");
		btnReviewAndRating.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReviewRatingUI reviewRating = new ReviewRatingUI();
				reviewRating.setSize(500, 500);
				reviewRating.setVisible(true);
			}
		});
		btnReviewAndRating.setBounds(224, 124, 200, 50);
		frame.getContentPane().add(btnReviewAndRating);
	}
}
