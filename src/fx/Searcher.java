package fx;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

/**
 * Created by Suvo on 18-Dec-15.
 */
public class Searcher {
    JoinGameController controller;
    String myIpAddress;

    public Searcher(JoinGameController controller) throws UnknownHostException {
        this.controller = controller;
        myIpAddress= InetAddress.getLocalHost().toString();
    }

    public void search() {
        String actualMyIp=myIpAddress.split("/")[1];
        String[] s=new String[4];
        StringTokenizer stringTokenizer=new StringTokenizer(actualMyIp,".");
        int i=0;
        while(stringTokenizer.hasMoreTokens()){
            s[i++]=stringTokenizer.nextToken();
        }
        int lastPart=Integer.parseInt(s[4]);
        lastPart=(int)(Math.floor((double)lastPart/10.0)*10);
        i=0;
        while (i <= 10) {
            int testLastPart=lastPart+i;
            String testIP=s[0]+"."+s[1]+"."+s[2]+"."+testLastPart;
            Socket socket= null;
            try {
                socket = new Socket(testIP,6199);
                socket.setSoTimeout(10000);
                if (socket!=null){
                    System.out.println("Found");
                }
            } catch (IOException e) {
                i++;
            }



        }

    }


}
