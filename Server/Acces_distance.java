package ecran;

import listener.SendEvent;
import main.Main;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.net.Socket;

public class Acces_distance extends Thread {
    Socket socket;
    JFrame frame = new JFrame();
    JDesktopPane desktop = new JDesktopPane();
    JInternalFrame internalFrame = new JInternalFrame("Screen Of "+ Main.ip,true,true,true);
    Panel panel = new Panel();
    SendEvent se;

    public Acces_distance(Socket socket)  {
        this.socket = socket;
        start();
    }

    @Override
    public void run() {
        try {
            se = new SendEvent(socket);
            new GetSreen(panel, socket);
            frame.add(desktop, BorderLayout.CENTER);
            frame.setDefaultCloseOperation(3);
            frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);

            internalFrame.getContentPane().add(panel, BorderLayout.CENTER);
            internalFrame.setSize(100,100);
            desktop.add(internalFrame);

            panel.setSize(Toolkit.getDefaultToolkit().getScreenSize());
            internalFrame.setMaximum(true);
            desktop.setFocusable(false);
            internalFrame.setFocusable(false);
            panel.setFocusable(true);
            frame.setFocusable(false);

            panel.addKeyListener(se);
            panel.addMouseListener(se);
            panel.addMouseMotionListener(se);

            internalFrame.setVisible(true);
            frame.setVisible(true);
        } catch (PropertyVetoException | IOException e) {
            e.printStackTrace();
        }
    }
}
