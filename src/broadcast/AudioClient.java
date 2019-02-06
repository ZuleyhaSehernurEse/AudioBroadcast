package broadcast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioClient{
    static byte[] receiveData;
    static int port=33333;
    static boolean status = true;
    static SourceDataLine sourceDataLine;
    static AudioFormat format;

    public static void main(String[] args) throws IOException, LineUnavailableException
    {
    	DatagramSocket clientSocket= new DatagramSocket(port); 
    	receiveData = new byte[16384];
    	format = new AudioFormat(44100, 16, 2, true, false); 
    	DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
		sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo); 
		sourceDataLine.open(format);
	    sourceDataLine.start();
         
         DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
         
         while ( status == true) 
         {
			clientSocket.receive(receivePacket);
            receiveData=receivePacket.getData();
            sourceDataLine.write(receiveData, 0, receiveData.length);
         }
         sourceDataLine.drain();
         sourceDataLine.close();
         clientSocket.close();
         
     }
 }
 