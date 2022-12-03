package command;

import java.awt.*;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class ExecutCommad extends Thread {

    Socket socketEvent;
    Robot robot;
    DataInputStream is;
    boolean loop = true;

    public ExecutCommad(Socket socket) throws AWTException, IOException {
        socketEvent = socket;
        robot = new Robot();
        is = new DataInputStream(socketEvent.getInputStream());

        start();
    }

    public void run() {
        while (loop) {
            try {
                int command = is.readInt();
                switch (command) {
                    case 1 -> robot.keyPress(is.readInt());
                    case 2 -> robot.mousePress(is.readInt());
                    case -1 -> robot.keyRelease(is.readInt());
                    case -2 -> robot.mouseRelease(is.readInt());
                    case 3 -> robot.mouseMove(is.read(), is.read());
                }
            } catch (IOException ignored) { }
        }
    }
}
