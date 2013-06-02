package com.example.wificonnecter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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

	public static final String ADDRESS_DEFAULT = "192.168.43.190";
	public static final int PORT_DEFAULT = 44300;
	// Java emulator test
	// public static final String ADDRESS_DEFAULT = "192.168.0.10";
	// public static final int PORT_DEFAULT = 5555;

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

		btnLeft.setOnTouchListener(onTouchListener);
		btnRight.setOnTouchListener(onTouchListener);
		btnForward.setOnTouchListener(onTouchListener);
		btnBack.setOnTouchListener(onTouchListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	};

	private OnTouchListener onTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				switch (v.getId()) {
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
		sendToCarBackground(ADDRESS_DEFAULT, PORT_DEFAULT, LEFT_OPT);
	}

	void moveRight() {
		sendToCarBackground(ADDRESS_DEFAULT, PORT_DEFAULT, RIGHT_OPT);
	}

	void moveForward() {
		sendToCarBackground(ADDRESS_DEFAULT, PORT_DEFAULT, FORWARD_OPT);
	}

	void moveBack() {
		sendToCarBackground(ADDRESS_DEFAULT, PORT_DEFAULT, BACK_OPT);
	}

	void stop() {
		sendToCarBackground(ADDRESS_DEFAULT, PORT_DEFAULT, STOP_OPT);
	}

	void sendToCar(final String address, final int port, final String cmd) {
		boolean connected = false;
		if (out != null) {
			try {
				System.out.println(cmd);
				writeToCar(cmd);
				connected = true;
			} catch (IOException e) {
				System.out.println("Failed connection, try reconnect.");
			}
		}
		if (!connected) {
			try {
				connectToCar(address, port);
				writeToCar(cmd);
			} catch (Exception e) {
				e.printStackTrace();
				handler.post(new Runnable(){
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, "Failed connection.", Toast.LENGTH_LONG)
						.show();
					}});				
			}
		}
	}
	
	private Handler handler = new Handler();

	void writeToCar(String cmd) throws IOException {
		out.write(cmd);
		out.flush();
	}

	private boolean sendingCommand = false;

	void sendToCarBackground(final String address, final int port,
			final String cmd) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				if (!sendingCommand) {
					sendingCommand = true;
					sendToCar(address, port, cmd);
					sendingCommand = false;
				}
			}

		}).start();
	}

	void connectToCar(String address, int port) throws Exception {
		socket = new Socket(address, port);
		out = new OutputStreamWriter(socket.getOutputStream());
	}
}
