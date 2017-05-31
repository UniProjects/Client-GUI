package org.popov.belezirev.gui.client;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientGui implements AutoCloseable {

	private String hostName;
	private int port;

	private Socket clientSocket;
	private PrintWriter serverWriter;
	private BufferedReader serverReader;

	private MessageProcessor processor;
	
	public ClientGui(String hostName, int port, MessageProcessor processor) {
		this.hostName = hostName;
		this.port = port;
		this.processor = processor;
	}

	public void initClient() throws UnknownHostException, IOException {
		clientSocket = new Socket(hostName, port);
		serverWriter = new PrintWriter(clientSocket.getOutputStream());
		serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	public void sendMessage(String message) {
		if (message != null) {
			serverWriter.println(message);
			serverWriter.flush();
		}
	}

	public String readMessageSynchronously() {
		try {
			return serverReader.readLine();
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	public void readMessageAsynchronously(){
		new Thread(() -> {
			while (true) {
				String readMessage = readMessageSynchronously();
				processor.processMessage(readMessage);
			}
		}).start();
	}

	@Override
	public void close() throws IOException {
		if (clientSocket != null) {
			closeQuietly(clientSocket);
		}
		if (serverWriter != null) {
			closeQuietly(serverWriter);
		}
		if (serverReader != null) {
			closeQuietly(serverReader);
		}
	}

	private void closeQuietly(Closeable closable) {
		try {
			closable.close();
		} catch (IOException e) {
		}
	}
}
