package fx;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Suvo on 18-Dec-15.
 */
public class ClientFounder  implements Runnable{
    private ServerSocket serverSocket;
    private CreateGameController controller;
    private boolean isRunning;
    Thread t;

    public ClientFounder(CreateGameController controller) {
        this.controller = controller;
        t=new Thread(this);
        isRunning=true;
        try {
            serverSocket=new ServerSocket(6199);
        } catch (IOException e) {
            e.printStackTrace();
        }
        t.start();
    }

    public void stop(){
        isRunning=false;
    }

    @Override
    public void run() {
        while (isRunning){
            try {
                Socket client=serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
