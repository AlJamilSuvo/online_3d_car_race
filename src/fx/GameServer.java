package fx;


import org.lwjgl.util.vector.Vector3f;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer{
	ServerSocket server;
	ServerRun serverRun;
	List<Client> clients=new ArrayList<>();
	List<Send> sends=new ArrayList<>();
	int nextId=0;
	long infoNo;
	Vector3f stratingPoint=new Vector3f();

	int nextPort=6201;
	GameServer() {
		;
		try {
			String checkPointFile = "res/track.details";
			try {
				BufferedReader reader = new BufferedReader(new FileReader(checkPointFile));
				String line;
				line = reader.readLine();


				float x1, y1, z;
				String[] s = line.split(" ");
				x1 = Float.parseFloat(s[1]);
				y1 = Float.parseFloat(s[2]);
				z = Float.parseFloat(s[3]);
				stratingPoint = new Vector3f(x1, y1, z);

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			server = new ServerSocket(6200);
			serverRun = new ServerRun();
		} catch (IOException e) {
		}
	}
	public void close(){
			serverRun.close();
			try {
				server.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	public void play(){
			serverRun.close();
			serverRun.stop();
			for (Send s:sends){
				s.playGame();
			}
			new Watcher();
	}



	class Watcher extends Thread{
		long st;
		long et;
		long stinfoNo;
		Watcher(){
			start();
		}
		public void run(){
			while(true){
				st=System.currentTimeMillis();
				stinfoNo=infoNo;
				Thread t=new Thread();
				try {
					t.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	class ServerRun extends Thread{
		
		private boolean isRunning=false;
		public ServerRun(){
			this.isRunning=true;
			start();
		}
		
		public void run(){
			while(isRunning){
				Socket sclient;
				try {
					sclient=server.accept();
					Client client=new Client(sclient,nextId,new Vector3f(stratingPoint.x-nextId*40,stratingPoint.y,stratingPoint.z),new Vector3f(0,180,0),nextPort);
					Send send=new Send(sclient,nextId);
					clients.add(client);
					sends.add(send);
					send.sendPort(nextPort);
					nextPort++;
					nextId++;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void close(){
			isRunning=false;
		}
		
		
	}
	class Send implements Runnable{
		Socket client;
		private boolean isRunning;
		DataOutputStream dout;
		private int id;
		Thread t;
		Send(Socket client,int id){
			this.client=client;
			this.id=id;
			try {
				dout=new DataOutputStream(client.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			isRunning=true;
		}
		public  void sendPort(int port){
            String str="port "+String.valueOf(port);
            try {
                dout.writeUTF(str);
                dout.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


		public void playGame(){
			for (Client c:clients){
				String str;
				if(c.getId()==id){
					str="player ";
				}
				else str="other ";
				str+=c.getX()+" ";
				str+=c.getY()+" ";
				str+=c.getZ()+" ";
				str+=c.getRx()+" ";
				str+=c.getRy()+" ";
				str+=c.getRz()+" ";
                str+=c.getIp()+" ";
                str+=c.getPort();
				try {
					dout.writeUTF(str);
					dout.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				dout.writeUTF("play");
				dout.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			isRunning=true;
			t=new Thread(this);
			t.start();
			
		}


		@Override
		public void run() {
			while (true){
				boolean b=true;
				for (Client c:clients) b=b&&c.isOK;
				if (b){
					try {
						System.out.println("SERVER SEND READY");
						dout.writeUTF("ready");
						dout.flush();
						Thread.sleep(1000);
						dout.writeUTF("ready 1");
						dout.flush();
						Thread.sleep(1000);
						dout.writeUTF("ready 2");
						dout.flush();
						Thread.sleep(1000);
						dout.writeUTF("ready 3");
						dout.flush();
						Thread.sleep(1000);
						dout.writeUTF("ready 4");
						dout.flush();
						Thread.sleep(1000);
						dout.writeUTF("Go");
						dout.flush();
						break;
					} catch (IOException e) {
						e.printStackTrace();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}
	}
	class Client implements Runnable{
		Socket client;
		private boolean isOK;
		DataInputStream din;
		int port;
		float x;
		float y;
		float z;
		float rx;
		float ry;
		float rz;
		int id;
		int carID;
		String ip;
		public Client(Socket client,int id,Vector3f pos,Vector3f rot,int port){
			this.client=client;
			this.id=id;
			x=pos.x;
			y=pos.y;
			z=pos.z;
			rx=rot.x;
			ry=rot.y;
			rx=rot.z;
			this.port=port;
			try {
				din=new DataInputStream(client.getInputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
			new Thread(this).start();
		}

		
		public Socket getClient(){
			return client;
		}

		public float getX() {
			return x;
		}

		public float getY() {
			return y;
		}

		public float getZ() {
			return z;
		}

		public float getRx() {
			return rx;
		}

		public float getRy() {
			return ry;
		}

		public float getRz() {
			return rz;
		}

		public long getId() { return id; }

        public int getPort() {
            return port;
        }

        public String getIp() {
            return ip;
        }

		@Override
		public void run() {
			while (true){
				try {
					String str=din.readUTF();
					if (str.startsWith("ip ")){
						ip=str.split(" ")[1];
					}
					else if(str.equals("ready")){
						System.out.println("Server Got Ready MSG");
						isOK=true;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
