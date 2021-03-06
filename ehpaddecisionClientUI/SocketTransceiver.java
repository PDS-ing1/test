package fr.esipe.blondine;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 
 * @author PC-Armel
 *
 */
public abstract class SocketTransceiver implements Runnable {

	protected Socket socket;
	protected InetAddress addr;
	protected DataInputStream in;
	protected DataOutputStream out;
	private boolean runFlag;

	/**
	 * 
	 * @param socket
	 *            socket
	 */
	public SocketTransceiver(Socket socket) {
		this.socket = socket;
		this.addr = socket.getInetAddress();
	}

	/**
	 * 
	 * @return InetAddress
	 */
	public InetAddress getInetAddress() {
		return addr;
	}

	/**
	 * {@code onDisconnect()}
	 */
	public void start() {
		runFlag = true;
		new Thread(this).start();
	}

	/**
	 * {@code onDisconnect()}
	 */
	public void stop() {
		runFlag = false;
		try {
			socket.shutdownInput();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param s
	 * 
	 * @return true
	 */
	public boolean send(String s) {
		if (out != null) {
			try {
				out.writeUTF(s);
				out.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/***
	 * 
	 * @param rqst
	 * @return
	 */
	public boolean sendRequestUpdate(String rqst) {
		if (out != null) {
			try {
				out.writeUTF(rqst);
				out.flush();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param rqst
	 * @return
	 */
	public  ArrayList<Object> sendRequestExec(String rqst) {
		ArrayList<Object> dataList = new ArrayList<Object>();
		
		if (out != null) {
			try {
				out.writeUTF(rqst);
				out.flush();
				return null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataList;
	}

	/**
	 * Socket
	 */
	@Override
	public void run() {
		try {
			in = new DataInputStream(this.socket.getInputStream());
			out = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			runFlag = false;
		}
		while (runFlag) {
			try {
				final String s = in.readUTF();
				this.onReceive(addr, s);
			} catch (IOException e) {
				//
				runFlag = false;
			}
		}
		//
		try {
			in.close();
			out.close();
			socket.close();
			in = null;
			out = null;
			socket = null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.onDisconnect(addr);
	}

	/**
	 * 
	 * @param addr
	 * Socket
	 * @param s
	 * 
	 */
	public abstract void onReceive(InetAddress addr, String s);

	/**
	 * 
	 * @param addr
	 * 			Socket
	 */
	public abstract void onDisconnect(InetAddress addr);
}
