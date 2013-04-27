package com.rCARrry;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ControllEmulator extends JFrame {

	public static final String LEFT_OPT = "L";
	public static final String RIGHT_OPT = "R";
	public static final String FORWARD_OPT = "F";
	public static final String BACK_OPT = "B";
	public static final String STOP_OPT = "S";

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	
	private Socket socket = null;
	private OutputStreamWriter out = null;

	public static void main(String[] args) {
		ControllEmulator frame = new ControllEmulator(args[0], new Integer(args[1]));
		frame.setVisible(true);				
	}

	public ControllEmulator(String address, int port) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] {100, 100, 100};
		gbl_contentPane.rowHeights = new int[] {0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0};
		contentPane.setLayout(gbl_contentPane);
		
		JButton btnForward = new JButton("Forward");
		GridBagConstraints gbc_btnForward = new GridBagConstraints();
		gbc_btnForward.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnForward.insets = new Insets(0, 0, 5, 5);
		gbc_btnForward.gridx = 1;
		gbc_btnForward.gridy = 0;
		contentPane.add(btnForward, gbc_btnForward);
		
		JButton btnLeft = new JButton("Left");
		GridBagConstraints gbc_btnLeft = new GridBagConstraints();
		gbc_btnLeft.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLeft.insets = new Insets(0, 0, 5, 5);
		gbc_btnLeft.gridx = 0;
		gbc_btnLeft.gridy = 1;
		contentPane.add(btnLeft, gbc_btnLeft);
		
		JButton btnStop = new JButton("Stop");
		GridBagConstraints gbc_btnStop = new GridBagConstraints();
		gbc_btnStop.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnStop.insets = new Insets(0, 0, 5, 5);
		gbc_btnStop.gridx = 1;
		gbc_btnStop.gridy = 1;
		contentPane.add(btnStop, gbc_btnStop);
		
		JButton btnRight = new JButton("Right");
		GridBagConstraints gbc_btnRight = new GridBagConstraints();
		gbc_btnRight.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRight.insets = new Insets(0, 0, 5, 0);
		gbc_btnRight.gridx = 2;
		gbc_btnRight.gridy = 1;
		contentPane.add(btnRight, gbc_btnRight);
		
		JButton btnBack = new JButton("Back");
		GridBagConstraints gbc_btnBack = new GridBagConstraints();
		gbc_btnBack.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnBack.insets = new Insets(0, 0, 0, 5);
		gbc_btnBack.gridx = 1;
		gbc_btnBack.gridy = 2;
		contentPane.add(btnBack, gbc_btnBack);
		

		btnForward.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				moveForward();
			}
		});
		btnBack.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				moveBack();
			}
		});
		btnRight.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				moveRight();
			}
		});
		btnLeft.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				moveLeft();
			}
		});
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				stop();
			}
		});
		
		try {
			connectToCar(address, port);
		} catch(Exception exc) {			
			exc.printStackTrace();
			System.exit(1);
		}
		
	}

	void moveLeft() {
		sendToCar(LEFT_OPT);
	}
	void moveRight() {
		sendToCar(RIGHT_OPT);
	}
	void moveForward() {
		sendToCar(FORWARD_OPT);
	}
	void moveBack() {
		sendToCar(BACK_OPT);
	}
	void stop() {
		sendToCar(STOP_OPT);
	}
	
	void connectToCar(String address, int port) throws Exception {
		socket = new Socket(address, port);
		out = new OutputStreamWriter(socket.getOutputStream());
	}
	
	void sendToCar(String cmd) {
		try {
			out.write(cmd);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	
}
