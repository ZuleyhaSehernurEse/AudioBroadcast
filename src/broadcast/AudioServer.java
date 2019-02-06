package broadcast;

import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;

public class AudioServer {
	static byte[] data;
	static int port=33333;
	static AudioInputStream audioInputStream;
    static AudioFormat format;
    static  DatagramPacket datagramPacket=null;
    
    public static void main(String args[]) throws Exception 
    {
    	System.out.println("Server started at port:"+port);

    	for(int musicCount=1;musicCount<4;musicCount++) {
    		DatagramSocket serverSocket = new DatagramSocket();
	        File file =new File("muzýk\\"+ musicCount+".wav");
	        audioInputStream = AudioSystem.getAudioInputStream(file);
	        AudioFormat format = audioInputStream.getFormat();
	        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
	        System.out.println(info);
	      
	        data = new byte[16384];
	        int Bytes=0;
	        while (Bytes !=-1) {
	        	
	        	Bytes =  audioInputStream.read(data, 0, data.length);
	            datagramPacket = new DatagramPacket (data,data.length,InetAddress.getByName("192.168.43.255"),port); 
	            serverSocket.send(datagramPacket);
	            Thread.sleep(90);
	        }
	        audioInputStream.close();
	        serverSocket.close();
	    }
   }
}