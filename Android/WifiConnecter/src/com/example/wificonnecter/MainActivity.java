package com.example.wificonnecter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final String LEFT_OPT = "L";
	public static final String RIGHT_OPT = "R";
	public static final String FORWARD_OPT = "F";
	public static final String BACK_OPT = "B";
	public static final String STOP_OPT = "S";
	
	public static final String ADDRESS_DEFAULT = "192.168.0.190";
	public static final int PORT_DEFAULT = 44300;
	// Java emulator test
	//public static final String ADDRESS_DEFAULT = "192.168.0.10";
	//public static final int PORT_DEFAULT = 5555;
	
	private Button btnLeft;
	private Button btnRight;
	private Button btnForward;
	private Button btnBack;
	
	private Socket socket;
	private OutputStreamWriter out;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnLeft = (Button) findViewById(R.id.buttonLeft);
		btnRight = (Button) findViewById(R.id.buttonRight);
		btnForward = (Button) findViewById(R.id.buttonForward);
		btnBack = (Button) findViewById(R.id.buttonBack);
		
		/*
		btnLeft.setOnTouchListener(onTouchListener);
		btnRight.setOnTouchListener(onTouchListener);
		btnForward.setOnTouchListener(onTouchListener);
		btnBack.setOnTouchListener(onTouchListener);
		
		try {
			connectToCar(ADDRESS_DEFAULT, PORT_DEFAULT);
		} catch (Exception e) {
			Toast.makeText(this, "Failed connection.", Toast.LENGTH_LONG).show();			
			e.printStackTrace();
		}
		*/
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;	
	};
	
	private OnTouchListener onTouchListener = new OnTouchListener() {

	    @Override
	    public boolean onTouch(View v, MotionEvent event) {
	    	if(event.getAction() == MotionEvent.ACTION_DOWN) {
	    		switch(v.getId()){
	                case R.id.buttonLeft:
	                	moveLeft();
	               		break;
	                case R.id.buttonRight:
	                	moveRight();
	               		break;
	                case R.id.buttonForward:
	                	moveForward();
	               		break;
	                case R.id.buttonBack:
	                	moveBack();
	               		break;
	    		}
	        } else if (event.getAction() == MotionEvent.ACTION_UP) {
	            stop();
	        }
	    	return false;
	    }
	};
	
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
	
	void sendToCar(String cmd) {
		try {
			System.out.println(cmd);
			out.write(cmd);
			out.flush();
		} catch (IOException e) {
			Toast.makeText(this, "Failed connection.", Toast.LENGTH_LONG).show();	
			e.printStackTrace();
		}
	}

	void connectToCar(String address, int port) throws Exception {
		socket = new Socket(address, port);
		out = new OutputStreamWriter(socket.getOutputStream());
	}
	
}
