package ecran;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class EnvoiIm extends Thread {
    Socket socket;
    OutputStream os;
    boolean loop = true;

    public EnvoiIm(Socket socket) {
        this.socket = socket;

        start();
    }

    public void run() {
        Robot robot = null;
        Image image = null;
        Image cursor = null;
        Rectangle sizeScreen = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        try {
            os = socket.getOutputStream();
            robot = new Robot();

        } catch (IOException | AWTException e) {
            throw new RuntimeException(e);
        }
        while (loop) {
            try {
                image = robot.createScreenCapture(sizeScreen);
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                ImageIO.write((RenderedImage) image, "jpeg", byteArrayOutputStream);
                byte[] size = ByteBuffer.allocate(4).putInt(byteArrayOutputStream.size()).array();
                os.write(size);
                os.write(byteArrayOutputStream.toByteArray());
                os.flush();

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
