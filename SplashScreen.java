import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.io.File;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.imageio.ImageIO;
import java.awt.image.VolatileImage;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
//import java.awt.geom.Rectangle2D;
//import java.awt.FontMetrics;
//import java.awt.BasicStroke;
//import java.awt.Font;
//import java.awt.RenderingHints;

/*
    Project:    Modern 2D Java Game Engine
    Purpose:    Provide basics functionalities to write 2D games in Java in a more modern approach
    Author:     Mr. Joao P. B. Faria
    Date:       Octuber 2021
    WTCD:       This class, provides a selection screen, that could be hide forever, that allow the user to choose between window format (full, pseudo-full, windowed)
                and than, the syncronization method, frame cap, screen size & resolution.
*/
public class SplashScreen extends JFrame implements Runnable {

    private static final long serialVersionUID  = 1L;

    //this window properties
    private int positionX                       = 0;
    private int positionY                       = 0;
    private int windowWidth                     = 800;
    private int windowHeight                    = 600;
    private int w, h, x, y                      = 0;

    //desktop properties
    private int resolutionH                     = 0;
    private int resolutionW                     = 0;
    
    //the first 'canvas' & the backbuffer (for simple doublebuffer strategy)
    private JPanel canvas                       = null;
    private VolatileImage bufferImage           = null;
    private BufferedImage splashImage           = null;

    //some support and the graphical device itself
    private GraphicsEnvironment ge              = null;
    private GraphicsDevice dsd                  = null;
    private Graphics2D g2d                      = null;

    //this screen control logic parameter   
    private int selectedItem                    = 0;
    
    /*
        WTMD: some responsabilites here:
            1) load some parameters from config file (if exists)
            2) center the window in the screen
            3) add a keylistener
            4) initialize the canvas and retrieve the graphical device objects
    */
    public SplashScreen() {

        //////////////////////////////////////////////////////////////////////
        // ->>>  for the window
        //////////////////////////////////////////////////////////////////////

        //load or provide the default configuration file
        new ConfigurationFile().verifyTheConfigurationFile();

        //set some properties for this window
        Dimension basic = new Dimension(this.windowWidth, this.windowHeight);
        this.setPreferredSize(basic);
        this.setMinimumSize(basic);
        this.setUndecorated(true);

        //default operation on close (exit in this case)
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //recover the desktop resolution
        Dimension size = Toolkit.getDefaultToolkit(). getScreenSize();

        //and save this values
        this.resolutionH = (int)size.getHeight();
        this.resolutionW = (int)size.getWidth();

        //center the current window regards the desktop resolution
        this.positionX = (int)((size.getWidth() / 2) - (this.windowWidth / 2));
        this.positionY = (int)((size.getHeight() / 2) - (this.windowHeight / 2));
        this.setLocation(this.positionX, this.positionY);
        
        //add a keylistener
        this.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 39) {if (selectedItem < 2) { selectedItem++;paint(g2d);}}
                if (e.getKeyCode() == 37) {if (selectedItem > 0) {selectedItem--;paint(g2d);}}
                if (e.getKeyCode() == 27) {setVisible(false);System.exit(0);}
            }
        });

        //create the backbuffer from the size of screen resolution to avoid any resize process penalty
        this.ge             = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.dsd            = ge.getDefaultScreenDevice();
        this.bufferImage    = dsd.getDefaultConfiguration().createCompatibleVolatileImage(this.resolutionW, this.resolutionH);
        this.g2d            = (Graphics2D)bufferImage.getGraphics();
        
        //read the splash screen image
        try {
            this.splashImage = ImageIO.read(new File("images\\splash.png"));
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

        //////////////////////////////////////////////////////////////////////
        // ->>>  now, for the canvas
        //////////////////////////////////////////////////////////////////////
        this.w = this.splashImage.getWidth();
        this.h = this.splashImage.getHeight();
        this.x = (this.windowWidth - this.w) / 2;
        this.y = (this.windowHeight - this.h) / 2;

        //initialize the canvas
        this.canvas = new JPanel(null);
        this.canvas.setSize(windowWidth, windowHeight);
        this.canvas.setBackground(Color.BLACK);
        this.setVisible(true);
        this.canvas.setOpaque(true);
        
        //final parameters for the window
        this.add(canvas);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.requestFocus();
    }

    /*
        WTMD: Override the paint method, transfering the rendering control to draw.
    */
    @Override
    public void paint(Graphics g) {
        this.draw();
    }

    /*
        WTMD: This method draw the current screen, some steps described here:
            1) Clear the stage
            2) Print the main label
            3) Print the selection buttons
            4) Print the exit label
     */
    public void draw() {

        //update the window size variables if the user resize it.
        this.windowHeight = this.getHeight();
        this.windowWidth  = this.getWidth();

        if (this.g2d != null) {
            
            //clear the stage
            this.g2d.setBackground(new Color(9, 26, 52));
            this.g2d.clearRect(0, 0, this.resolutionW, this.resolutionH);

            //draw the splash image
            this.g2d.drawImage(this.splashImage, x, 0, w + x, h + y, //dest w1, h1, w2, h2
                                                 0, 0, w, h, //source w1, h1, w2, h2
                                                 null);
            
            /*
            this.g2d.setFont(new Font("Abadi", Font.PLAIN, 18));
            this.g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //Define some labels
            FontMetrics fm              = this.g2d.getFontMetrics();
            String labels[]             = new String[5];
            Rectangle2D labelbounds[]   = new Rectangle2D[5];
            labels[0]                   = "Please, select the screen mode!";
            labels[1]                   = "Windowed";
            labels[2]                   = "FullScreen Window-Borderless";
            labels[3]                   = "FullScreen (V-Synced)";
            labels[4]                   = "ESC to exit";
            labelbounds[0]              = fm.getStringBounds(labels[0], g2d);
            labelbounds[1]              = fm.getStringBounds(labels[1], g2d);
            labelbounds[2]              = fm.getStringBounds(labels[2], g2d);
            labelbounds[3]              = fm.getStringBounds(labels[3], g2d);

            //retrieve some common values in some variables
            int windowXCenter           = (int)(this.getWidth() / 2);
            int windowHeight            = (int)(this.getHeight());
            int buttonsPositionH        = (int)(windowHeight*.45);
            int buttonsHeight           = 30;
            Color orange                = new Color(255,102,0);

            //draw the initial label (at 1/4 of window height)
            this.g2d.setColor(Color.WHITE);
            this.g2d.drawString(labels[0], (int)(windowXCenter - (labelbounds[0].getCenterX())), (int)(windowHeight*.25));

            //space the buttons
            int offset = 70; //in pixel
            int freeSpace = (int)(this.windowWidth 
                                    - labelbounds[1].getWidth() - 10
                                    - labelbounds[2].getWidth() - 10
                                    - labelbounds[3].getWidth() - 10
                                    - offset    //to the left
                                    - offset);  //to the right
            int division = freeSpace / 2;

            //draw the first button
            int buttonsPositionX[] = new int[3];
            buttonsPositionX[0] = offset;
            this.g2d.setColor(orange);
            this.g2d.fillRect(buttonsPositionX[0], 
                              buttonsPositionH, 
                              (int)(labelbounds[1].getWidth() + 10), 
                              buttonsHeight);
            this.g2d.setColor(Color.BLACK);
            this.g2d.drawString(labels[1], 
                                buttonsPositionX[0] + 5,
                                (int)(buttonsPositionH + labelbounds[1].getHeight()));

            //draw the second button
            buttonsPositionX[1] = (buttonsPositionX[0] +  (int)(labelbounds[1].getWidth() + 10) + division);
            this.g2d.setColor(orange);
            this.g2d.fillRect(buttonsPositionX[1], 
                              buttonsPositionH, 
                              (int)(labelbounds[2].getWidth() + 10), 
                              buttonsHeight);
            this.g2d.setColor(Color.BLACK);
            this.g2d.drawString(labels[2], 
                                buttonsPositionX[1] + 5, 
                                (int)(buttonsPositionH + labelbounds[2].getHeight()));

            //draw the third button
            buttonsPositionX[2] = (buttonsPositionX[1] +  (int)(labelbounds[2].getWidth() + 10) + division);
            this.g2d.setColor(orange);
            this.g2d.fillRect(buttonsPositionX[2], 
                              buttonsPositionH, 
                              (int)(labelbounds[3].getWidth() + 10), 
                              buttonsHeight);
            this.g2d.setColor(Color.BLACK);
            this.g2d.drawString(labels[3], 
                                buttonsPositionX[2] + 5, 
                                (int)(buttonsPositionH + labelbounds[3].getHeight()));

            //draw the highlight
            this.g2d.setColor(Color.LIGHT_GRAY);
            this.g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
            int highlightW = (int)labelbounds[selectedItem + 1].getWidth() + 21;
            this.g2d.drawRect(buttonsPositionX[selectedItem] - 6, buttonsPositionH - 6, highlightW, buttonsHeight + 11);

            //draw the exit message
            this.g2d.setFont(new Font("Abadi", Font.PLAIN, 12));
            this.g2d.setColor(Color.WHITE);
            this.g2d.drawString(labels[4], 
                                (int)(this.windowWidth - (fm.getStringBounds(labels[4], g2d)).getWidth()),
                                (int)(this.windowHeight - 50));
            */

            //At least, copy the backbuffer to the canvas screen
            this.canvas.getGraphics().drawImage(this.bufferImage, 0, 0, this);
        }
    }

    @Override
    public void run() {
    }

    /*
        Description: main method
    */
    public static void main(String[] args) throws Exception {
        //enable openGL
        System.setProperty("sun.java2d.opengl", "True");

        //start the thread
        Thread thread = new Thread(new SplashScreen(), "engine");
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
    }


    /*
        Description: main method
    */
    /*public static void main(String [] args) {
        new Thread(new ConfigSplashScreen(), "configScreen").start();
    }*/

    /*
        This subclass is still under development...
    */
    @SuppressWarnings("unused")
    private class ConfigurationFile {

        //comentários para o arquivo
        private final String commentWindowMode  = "#define the window mode (windowed or fullscreen)";
        private final String commentWindowSize  = "#define the window preferred size";
        private final String comentResolution   = "#define the window resolution (when fullscreen)";
        private final String comentVsync        = "#define vsync-mode (true or false)";
        private final String commentFrameCap    = "#frame-cap (0 = unlimited, 30, 60, 90, 120)";
        private final String [] param           = {"window-mode", "window-size-w", "window-size-h", "resolution-w", "resolution-h", "enable-vsync", "frame-cap"};

        //parâmetros do arquivo
        private String windowMode               = "windowed";
        private int windowSizeW                 = 0;
        private int windowSizeH                 = 0;
        private int resolutionW                 = 0;
        private int resolutionH                 = 0;
        private boolean enableVsync             = false;
        private int frameCap                    = 0;
        private int defaultResolutionW          = 0;
        private int defaultResolutionH          = 0;        

        //Verifica se o arquivo existe
        private boolean fileExists              = false;

        /* Lê o arquivo de configurações */
        public void verifyTheConfigurationFile() {
            
            //Indica o local do arquivo de configuração
            File configfile = new File("config.ini");
            fileExists = configfile.exists();

            //Recupera a resolução padrão do monitor
            Dimension size = Toolkit.getDefaultToolkit(). getScreenSize();
            this.defaultResolutionW = (int)size.getWidth();
            this.defaultResolutionH = (int)size.getHeight();

            //Se existir, lê o arquivo
            if (fileExists) {
                Scanner scanner = null;
                try {
                    scanner = new Scanner(configfile);
                    String line, key, value = "";
                    while (scanner.hasNextLine()) {
                        line = scanner.nextLine();
                        line.trim();
                        if (line.length() > 0 && line.charAt(0) != '#') { //ignorar comentários
                            key = line.split(":")[0].trim();
                            value = line.split(":")[1].trim();
    
                            switch(key) {
                                case ("window-mode"):
                                    this.windowMode = value;
                                    break;
                                case ("window-size-w"):
                                    this.windowSizeW = Integer.parseInt(value);
                                    break;
                                case ("window-size-h"):
                                    this.windowSizeH = Integer.parseInt(value);
                                    break;
                                case ("resolution-w"):
                                    this.resolutionW = Integer.parseInt(value);
                                    break;
                                case ("resolution-h"):
                                    this.resolutionH = Integer.parseInt(value);
                                    break;
                                case ("enable-vsync"):
                                    this.enableVsync = Boolean.parseBoolean(value);
                                    break;
                                case ("frame-cap"):
                                    this.frameCap = Integer.parseInt(value);
                                    break;
                            }
                        }
                    }
                } catch (Exception e) {
                    System.err.println("Erro ao abrir o arquivo...");
                } finally {
                    if (scanner != null) {
                        scanner.close();
                    }
                }
            } else {
                this.createConfigFile();
            }
        }

        /* Cria o arquivo de configuração */
        private void createConfigFile() {
            //Indica o local do arquivo de configuração
            File configfile = new File("config.ini");
            try {
                configfile.createNewFile();    
            } catch (Exception e) {
                System.err.println("Impossível criar o arquivo de configuração...");
            }
            
            if (configfile.canWrite()) {
                String defaultFileConfig = "";
                BufferedWriter writer = null;
                
                try {
                    writer = new BufferedWriter(new FileWriter(configfile, true));
                    writer.append(this.commentWindowMode);
                    writer.append("\n");
                    writer.append(param[0] + ":windowed");
                    writer.append("\n\n");
                    writer.append(commentWindowSize);
                    writer.append("\n");
                    writer.append(param[1] + ":800");
                    writer.append("\n");
                    writer.append(param[2] + ":600");
                    writer.append("\n\n");
                    writer.append(comentResolution);
                    writer.append("\n");
                    writer.append(param[3] + ":" + defaultResolutionW);
                    writer.append("\n");
                    writer.append(param[4] + ":" + defaultResolutionH);
                    writer.append("\n\n");  
                    writer.append(comentVsync);
                    writer.append("\n");
                    writer.append(param[5] + ":false");
                    writer.append("\n\n");
                    writer.append(commentFrameCap);
                    writer.append("\n");
                    writer.append(param[6] + ":60");
                    writer.append("\n\n");

                    writer.append(defaultFileConfig);
                } catch (IOException e) {
                    System.err.println("Impossível preencher o arquivo de configuração...");
                } finally {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        System.err.println("Impossível fechar o BufferWriter...");
                        e.printStackTrace();
                    }
                }
            }
        }

        public String getWindowMode()   {   return windowMode;      }
        public int getWindowSizeW()     {   return windowSizeW;     }
        public int getWindowSizeH()     {   return windowSizeH;     }
        public int getResolutionW()     {   return resolutionW;     }
        public int getResolutionH()     {   return resolutionH;     }
        public boolean isEnableVsync()  {   return enableVsync;     }
        public int getFrameCap()        {   return frameCap;        }
    }
}