package ecran;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class GetSreen extends Thread{
    Socket socket;
    Panel panel;
    InputStream in;
    boolean loop = true;

    public GetSreen(Panel panel, Socket socket) throws IOException {
        this.socket = socket;
        this.panel = panel;
        in = socket.getInputStream();
        start();
    }

    public void run() {
        Image image = null;
        while (loop) {
            try {
                byte[] sizeAr = new byte[4];
                in.read(sizeAr);

                int size = ByteBuffer.wrap(sizeAr).asIntBuffer().get();
                byte[] imageAr = new byte[size];
                int totalRead = 0;
                int currentRead = 0;
                while (totalRead < size && (currentRead = in.read(imageAr, totalRead, size-totalRead)) > 0) {
                    totalRead += currentRead;
                }

                image = ImageIO.read(new ByteArrayInputStream(imageAr));

                Graphics g = panel.getGraphics();
                g.drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null),null);

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
