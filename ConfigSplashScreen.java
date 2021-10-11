import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import java.awt.RenderingHints;
import java.awt.image.VolatileImage;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.awt.geom.Rectangle2D;
import java.awt.FontMetrics;
import java.awt.BasicStroke;

public class ConfigSplashScreen extends JFrame implements Runnable {

    private static final long serialVersionUID  = 1L;
    private int positionX                       = 0;
    private int positionY                       = 0;
    private int windowWidth                     = 800;
    private int windowHeight                    = 600;
    private int resolutionH                     = 0;
    private int resolutionW                     = 0;
    private ConfigurationFile confFile          = null;
    private JPanel canvas                       = null;
    private GraphicsEnvironment ge              = null;
    private GraphicsDevice dsd                  = null;
    private Graphics2D g2d                      = null;
    private VolatileImage bufferImage           = null;
    private int selectedItem                    = 0;
    
    public ConfigSplashScreen() {
        //verifica se existe o arquivo de configuração, caso contrário, escreve com parâmetros default
        this.confFile = new ConfigurationFile();
        confFile.loadConfigFile();

        //inicializa a janela de "welcome"
        this.setPreferredSize(new Dimension(windowWidth, windowHeight));
        this.setMinimumSize(new Dimension(windowWidth, windowHeight));
        Dimension size = Toolkit.getDefaultToolkit(). getScreenSize();
        this.setUndecorated(true);

        //armazena a resolução atual do usuário
        this.resolutionH = (int)size.getHeight();
        this.resolutionW = (int)size.getWidth();

        //define a operação padrão ao fechar
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //define a posição da janela como centralizada
        this.positionX = (int)((size.getWidth()/2) - (windowWidth/2));
        this.positionY = (int)((size.getHeight()/2) - (windowHeight/2));
        this.setLocation(this.positionX, this.positionY);
        this.setVisible(true);
        this.setBackground(Color.BLACK);     

        //Define o canvas (layer 0)
        this.canvas = new JPanel();
        this.canvas.setSize(windowWidth, windowHeight);
        this.canvas.setBackground(Color.BLACK);
        this.setVisible(true);
        this.canvas.setOpaque(true);
        
        //Adciona o canvas
        this.add(canvas);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.requestFocus();

        //cria o buffer
        this.ge             = GraphicsEnvironment.getLocalGraphicsEnvironment();
        this.dsd            = ge.getDefaultScreenDevice();
        this.bufferImage    = dsd.getDefaultConfiguration().createCompatibleVolatileImage(this.resolutionW, this.resolutionH);
        this.g2d            = (Graphics2D)bufferImage.getGraphics();

        //adiciona o keylistener
        this.addKeyListener(new KeyAdapter() {
            /* Key released */
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 39) {if (selectedItem < 2) { selectedItem++;paint(g2d);}}
                if (e.getKeyCode() == 37) {if (selectedItem > 0) {selectedItem--;paint(g2d);}}
                
            }
        });
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.draw();
    }

    public void draw() {
        if (this.g2d != null) {
            //cria a tela inicial no buffer e renderiza        
            this.g2d.setBackground(Color.BLACK);
            this.g2d.clearRect(0, 0, this.resolutionW, this.resolutionH);
            this.g2d.setFont(new Font("Abadi", Font.PLAIN, 18));
            this.g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            FontMetrics fm              = this.g2d.getFontMetrics();
            String label1               = "Por favor, escolha o modo que deseja iniciar:";
            String label2               = "Windowed";
            String label3               = "FullScreen Window-Borderless";
            String label4               = "FullScreen (V-Synced)";
            
            int windowXCenter           = (int)(this.getWidth() / 2);
            int windowHeight            = (int)(this.getHeight());
            Rectangle2D label1bounds    = fm.getStringBounds(label1, g2d);
            Rectangle2D label2bounds    = fm.getStringBounds(label2, g2d);
            Rectangle2D label3bounds    = fm.getStringBounds(label3, g2d);
            Rectangle2D label4bounds    = fm.getStringBounds(label4, g2d);

            //desenha o título de seleção
            this.g2d.setColor(Color.WHITE);
            this.g2d.drawString(label1, (int)(windowXCenter - (label1bounds.getCenterX())), (int)(windowHeight*.30));

            int windowHeightButtons = (int)(windowHeight*.4);
            Color orange = new Color(255,102,0);

            this.g2d.setColor(orange);
            this.g2d.fillRect(20, windowHeightButtons, (int)(label2bounds.getWidth() + 10), 30);
            this.g2d.setColor(Color.BLACK);
            this.g2d.drawString(label2, 20 + 5, (int)(windowHeightButtons + label2bounds.getHeight()));

            this.g2d.setColor(orange);
            this.g2d.fillRect(120, windowHeightButtons, (int)(label3bounds.getWidth() + 10), 30);
            this.g2d.setColor(Color.BLACK);
            this.g2d.drawString(label3, 120 + 5, (int)(windowHeightButtons + label3bounds.getHeight()));

            this.g2d.setColor(orange);
            this.g2d.fillRect(420, windowHeightButtons, (int)(label4bounds.getWidth() + 10), 30);
            this.g2d.setColor(Color.BLACK);
            this.g2d.drawString(label4, 420 + 5, (int)(windowHeightButtons + label4bounds.getHeight()));

            this.g2d.setColor(Color.LIGHT_GRAY);
            this.g2d.setStroke(new BasicStroke(4.0f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));
            this.g2d.drawRect(14, windowHeightButtons - 6, (int)(label2bounds.getWidth() + 21), 41);




            //System.out.println("draw.....");

            //Copia o buffer para o panel
            this.canvas.getGraphics().drawImage(this.bufferImage, 0, 0, this);
        }
    }

    @Override
    public void run() {
        System.out.println("running...");
    }

    public static void main(String [] args) {
        new Thread(new ConfigSplashScreen(), "engine").start();
    }

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
        public void loadConfigFile() {
            
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