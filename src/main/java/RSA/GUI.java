package RSA;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

/**
 * Created by User on 12.02.2016.
 */

class AddContactFrame{

    private static final int AWT_BORDER=20;
    private static final int AWT_MIDDLE_BORDER=5;
    private static final int AWT_H_BUTTON=25;
    private static final int AWT_W_BUTTON=20*3;
    private static final int AWT_H_TEXT=20;
    private static final int AWT_W_TEXT=70;

    private static final int AWT_H_EDIT_ADD=5;
    private static final int AWT_H_EDIT=20;
    private static final int AWT_W_EDIT=150;

    private static final int AWT_TOP_BORDER=AWT_BORDER;
    private Dialog frame;
    private Boolean query;

    private TextArea txtLogin,txtM,txtE,txtIp,txtPort;
    private String login,E,M,ip,port;
    private Map<String,String> result;

    public static Map<String,String> Query(Frame owner)
    {
        final AddContactFrame queryFrame=new AddContactFrame(owner);

        return queryFrame.result;
    }
    public AddContactFrame(Frame owner)
    {
        this.query=query;
        frame=new Dialog(owner,"New contact",true);

        frame.setLayout(null);
        Font font=new Font("Serif", Font.PLAIN, 15);

        frame.setFont(font);

        int x=AWT_BORDER, y=AWT_BORDER+AWT_TOP_BORDER;
        {
            Label lbl;
            lbl=new Label("login:",Label.RIGHT);
            lbl.setLocation(x, y);
            y += AWT_BORDER + AWT_H_TEXT;
            lbl.setSize(AWT_W_TEXT,AWT_H_TEXT);
            frame.add(lbl);

            lbl=new Label("M:",Label.RIGHT);
            lbl.setLocation(x, y);
            y += AWT_BORDER + AWT_H_TEXT;
            lbl.setSize(AWT_W_TEXT,AWT_H_TEXT);
            frame.add(lbl);

            lbl=new Label("E:",Label.RIGHT);
            lbl.setLocation(x, y);
            y += AWT_BORDER + AWT_H_TEXT;
            lbl.setSize(AWT_W_TEXT,AWT_H_TEXT);
            frame.add(lbl);

            lbl=new Label("ip:",Label.RIGHT);
            lbl.setLocation(x, y);
            y += AWT_BORDER + AWT_H_TEXT;
            lbl.setSize(AWT_W_TEXT,AWT_H_TEXT);
            frame.add(lbl);

            lbl=new Label("port:",Label.RIGHT);
            lbl.setLocation(x, y);
            y += AWT_BORDER + AWT_H_TEXT;
            lbl.setSize(AWT_W_TEXT,AWT_H_TEXT);
            frame.add(lbl);

        }
        {
            int xEdit=AWT_BORDER+AWT_MIDDLE_BORDER+AWT_W_TEXT,yEdit=AWT_BORDER+AWT_TOP_BORDER-AWT_H_EDIT_ADD/2;
            TextArea txt;
            txtLogin=txt=new TextArea("",1,20,TextArea.SCROLLBARS_NONE);
            txt.setFont(new Font("Courier New",Font.PLAIN,15));
            txt.setLocation(xEdit,yEdit);
            yEdit+= AWT_H_EDIT+AWT_H_TEXT;
            txt.setSize(AWT_W_EDIT,AWT_H_EDIT+AWT_H_EDIT_ADD);
            frame.add(txt);

            txtM=txt=new TextArea("",1,20,TextArea.SCROLLBARS_NONE);
            txt.setFont(new Font("Courier New",Font.PLAIN,15));
            txt.setLocation(xEdit,yEdit);
            yEdit+= AWT_H_EDIT+AWT_H_TEXT;
            txt.setSize(AWT_W_EDIT,AWT_H_EDIT+AWT_H_EDIT_ADD);
            frame.add(txt);

            txtE=txt=new TextArea("",1,20,TextArea.SCROLLBARS_NONE);
            txt.setFont(new Font("Courier New",Font.PLAIN,15));
            txt.setLocation(xEdit,yEdit);
            yEdit+= AWT_H_EDIT+AWT_H_TEXT;
            txt.setSize(AWT_W_EDIT,AWT_H_EDIT+AWT_H_EDIT_ADD);
            frame.add(txt);

            txtIp=txt=new TextArea("",1,20,TextArea.SCROLLBARS_NONE);
            txt.setFont(new Font("Courier New",Font.PLAIN,15));
            txt.setLocation(xEdit,yEdit);
            yEdit+= AWT_H_EDIT+AWT_H_TEXT;
            txt.setSize(AWT_W_EDIT,AWT_H_EDIT+AWT_H_EDIT_ADD);
            frame.add(txt);

            txtPort=txt=new TextArea("",1,20,TextArea.SCROLLBARS_NONE);
            txt.setFont(new Font("Courier New",Font.PLAIN,15));
            txt.setLocation(xEdit,yEdit);
            yEdit+= AWT_H_EDIT+AWT_H_TEXT;
            txt.setSize(AWT_W_EDIT,AWT_H_EDIT+AWT_H_EDIT_ADD);
            frame.add(txt);

        }
        {
            int xBtn=x+(AWT_MIDDLE_BORDER+AWT_W_TEXT+AWT_W_EDIT)/2-AWT_BORDER/2-AWT_W_BUTTON,yBtn=y;
            Button btn;

            btn = new Button("Ok");
            btn.setLocation(xBtn, yBtn);
            xBtn += AWT_BORDER + AWT_W_BUTTON;
            btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
            frame.add(btn);
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    result=new HashMap<String, String>();
                    result.put("login",txtLogin.getText());
                    result.put("M",txtM.getText());
                    result.put("E",txtE.getText());
                    result.put("ip",txtIp.getText());
                    result.put("port",txtPort.getText());
                    frame.dispose();
                }
            });

            btn = new Button("Cansel");
            btn.setLocation(xBtn, yBtn);
            xBtn += AWT_BORDER + AWT_W_BUTTON;
            btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
            frame.add(btn);
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    result=new HashMap<String, String>();

                    frame.dispose();
                }
            });

        }
        y+=AWT_H_BUTTON+AWT_BORDER;
        frame.setSize(AWT_W_TEXT+2*AWT_BORDER+AWT_MIDDLE_BORDER+AWT_W_EDIT,y);
        frame.setMaximumSize(frame.getSize());
        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
    }
}

class AddContactFrameSwing{

    private static final int AWT_BORDER=20;
    private static final int AWT_MIDDLE_BORDER=5;
    private static final int AWT_H_BUTTON=25;
    private static final int AWT_W_BUTTON=20*3;
    private static final int AWT_H_TEXT=20;
    private static final int AWT_W_TEXT=70;

    private static final int AWT_H_EDIT_ADD=5;
    private static final int AWT_H_EDIT=20;
    private static final int AWT_W_EDIT=150;

    private static final int AWT_TOP_BORDER=AWT_BORDER;
    private JDialog frame;
    private Boolean query;

    private JTextField txtLogin,txtM,txtE,txtIp,txtPort;
    private String login,E,M,ip,port;
    private Map<String,String> result;

    public static Map<String,String> Query(Frame owner)
    {
        final AddContactFrameSwing queryFrame=new AddContactFrameSwing(owner);

        return queryFrame.result;
    }
    public AddContactFrameSwing(Frame owner)
    {
        frame=new JDialog(owner,"New contact",true);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JPanel panel;
        {
            JPanel pnlHor=new JPanel();
            BoxLayout pnlHorLayout=new BoxLayout(pnlHor,BoxLayout.X_AXIS);
            pnlHor.setLayout(pnlHorLayout);

            pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
            panel=new JPanel();
            {
                BoxLayout pnlVertLayout=new BoxLayout(panel,BoxLayout.Y_AXIS);
                panel.setLayout(pnlVertLayout);
                Font font = new Font("Serif", Font.PLAIN, 15);
                panel.setFont(font);
            }
            frame.setLayout(new FlowLayout());
            pnlHor.add(panel);
            pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
            frame.add(pnlHor);
        }
        panel.add(Box.createVerticalStrut(AWT_BORDER));
        {
            Box boxHor;
            JTextField txt;

            JLabel lbl = new JLabel("login:");

            txtLogin=txt=new JTextField("user",20);
            txt.setFont(new Font("Courier New",Font.PLAIN,15));
            txt.setPreferredSize(new Dimension(AWT_W_EDIT,AWT_H_EDIT+AWT_H_EDIT_ADD));
            txt.setMaximumSize(txt.getPreferredSize());
            boxHor=Box.createHorizontalBox(); boxHor.add(Box.createHorizontalGlue());
            boxHor.add(lbl); boxHor.add(Box.createHorizontalStrut(AWT_MIDDLE_BORDER)); boxHor.add(txt);
            panel.add(boxHor); panel.add(Box.createVerticalStrut(AWT_BORDER));

            lbl = new JLabel("M:",SwingConstants.RIGHT);

            txtM=txt=new JTextField("505328582712267379670617057031456412648133198298113795322055388046957411865388383684826599331989921538391578601261291782060429810000904121670209210618991442805378573315330642902868268518281829821088107828255901899669812392602881563355885322555356435347307075034083484667631848695799702679812829446350404849",20);
            txt.setFont(new Font("Courier New",Font.PLAIN,15));
            txt.setPreferredSize(new Dimension(AWT_W_EDIT,AWT_H_EDIT+AWT_H_EDIT_ADD));
            txt.setMaximumSize(txt.getPreferredSize());
            boxHor=Box.createHorizontalBox(); boxHor.add(Box.createHorizontalGlue());
            boxHor.add(lbl); boxHor.add(Box.createHorizontalStrut(AWT_MIDDLE_BORDER)); boxHor.add(txt);
            panel.add(boxHor); panel.add(Box.createVerticalStrut(AWT_BORDER));


            lbl = new JLabel("E:",SwingConstants.RIGHT);

            txtE=txt=new JTextField("19",20);
            txt.setFont(new Font("Courier New",Font.PLAIN,15));
            txt.setPreferredSize(new Dimension(AWT_W_EDIT,AWT_H_EDIT+AWT_H_EDIT_ADD));
            txt.setMaximumSize(txt.getPreferredSize());
            boxHor=Box.createHorizontalBox(); boxHor.add(Box.createHorizontalGlue());
            boxHor.add(lbl); boxHor.add(Box.createHorizontalStrut(AWT_MIDDLE_BORDER)); boxHor.add(txt);
            panel.add(boxHor); panel.add(Box.createVerticalStrut(AWT_BORDER));


            lbl = new JLabel("ip:",SwingConstants.RIGHT);

            txtIp=txt=new JTextField("127.0.0.1",20);
            txt.setFont(new Font("Courier New",Font.PLAIN,15));
            txt.setPreferredSize(new Dimension(AWT_W_EDIT,AWT_H_EDIT+AWT_H_EDIT_ADD));
            txt.setMaximumSize(txt.getPreferredSize());
            boxHor=Box.createHorizontalBox(); boxHor.add(Box.createHorizontalGlue());
            boxHor.add(lbl); boxHor.add(Box.createHorizontalStrut(AWT_MIDDLE_BORDER)); boxHor.add(txt);
            panel.add(boxHor); panel.add(Box.createVerticalStrut(AWT_BORDER));

            lbl = new JLabel("port:",SwingConstants.RIGHT);

            txtPort=txt=new JTextField("12345",20);
            txt.setFont(new Font("Courier New",Font.PLAIN,15));
            txt.setPreferredSize(new Dimension(AWT_W_EDIT,AWT_H_EDIT+AWT_H_EDIT_ADD));
            txt.setMaximumSize(txt.getPreferredSize());
            boxHor=Box.createHorizontalBox(); boxHor.add(Box.createHorizontalGlue());
            boxHor.add(lbl); boxHor.add(Box.createHorizontalStrut(AWT_MIDDLE_BORDER)); boxHor.add(txt);
            panel.add(boxHor);

        }

        panel.add(Box.createVerticalStrut(AWT_BORDER));
        {
            Box box=Box.createHorizontalBox();
            box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            JButton btn;

            btn = new JButton("Ok");
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    result=new HashMap<String, String>();
                    result.put("login",txtLogin.getText());
                    result.put("M",txtM.getText());
                    result.put("E",txtE.getText());
                    result.put("ip",txtIp.getText());
                    result.put("port",txtPort.getText());
                    frame.dispose();
                }
            });
            box.add(btn);
            box.add(Box.createHorizontalStrut(AWT_BORDER));

            btn = new JButton("Cansel");

            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    result=new HashMap<String, String>();

                    frame.dispose();
                }
            });
            box.add(btn);
            panel.add(box);
        }

        panel.add(Box.createVerticalStrut(AWT_BORDER));
        frame.pack();
        frame.setVisible(true);
    }
}

class QueryFrame{
    public static final int RESULT_UNKNOWN=0;
    public static final int RESULT_OK=1;
    public static final int RESULT_RESET=2;
    public static final int RESULT_NO=3;
    public enum CntButtons{one,two,three};
    private int result=RESULT_UNKNOWN;
    private int getResult(){return result;}
    private static final int AWT_BORDER=20;
    private static final int AWT_H_BUTTON=25;
    private static final int AWT_W_BUTTON=20*3;
    private static final int AWT_H_TEXT=20;
    private static final int AWT_W_TEXT_MIN=100;
    private int awt_w_text;
    private static final int AWT_TOP_BORDER=AWT_BORDER;
    private Dialog frame;
    private Boolean query;
    public static int Query(String msg,String title, Frame owner, CntButtons btns)
    {
        final QueryFrame queryFrame=new QueryFrame(msg,title,owner,btns);
        return queryFrame.getResult();
    }
    private QueryFrame(String msg,String title,Frame owner,CntButtons btns)
    {
        this.query=query;
        frame=new Dialog(owner,title,true);

        frame.setLayout(null);
        Font font=new Font("Serif", Font.PLAIN, 15);

        frame.setFont(font);

        int x=AWT_BORDER, y=AWT_BORDER+AWT_TOP_BORDER;
        {
            Label lbl=new Label(msg,Label.CENTER);
            lbl.setLocation(x, y);
            y += AWT_BORDER + AWT_H_TEXT;
            Rectangle2D r= font.getStringBounds(msg,new FontRenderContext(null, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT, RenderingHints.VALUE_FRACTIONALMETRICS_DEFAULT));
            awt_w_text=Double.valueOf(r.getWidth()).intValue();
            switch (btns)
            {
                case one:
                    if(awt_w_text<AWT_W_BUTTON) awt_w_text=AWT_W_BUTTON;
                    break;
                case two:
                    if(awt_w_text<AWT_W_BUTTON*2+AWT_BORDER) awt_w_text=AWT_W_BUTTON*2+AWT_BORDER;
                    break;
                case three:
                    if(awt_w_text<AWT_W_BUTTON*3+AWT_BORDER*2) awt_w_text=AWT_W_BUTTON*3+AWT_BORDER*2;
                    break;
            }
            if (awt_w_text<AWT_W_TEXT_MIN) awt_w_text=AWT_W_TEXT_MIN;
            lbl.setSize(awt_w_text,AWT_H_TEXT);
            frame.add(lbl);
        }
        {
            if(btns==CntButtons.three)
            {
                int xBtn=x+awt_w_text/2-AWT_BORDER-AWT_W_BUTTON-AWT_W_BUTTON/2,yBtn=y;
                Button btn;

                btn = new Button("Yes");
                btn.setLocation(xBtn, yBtn);
                xBtn += AWT_BORDER + AWT_W_BUTTON;
                btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                frame.add(btn);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_OK;
                        frame.dispose();
                    }
                });

                btn = new Button("No");
                btn.setLocation(xBtn, yBtn);
                xBtn += AWT_BORDER + AWT_W_BUTTON;
                btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                frame.add(btn);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_NO;
                        frame.dispose();
                    }
                });

                btn = new Button("Reset");
                btn.setLocation(xBtn, yBtn);
                xBtn += AWT_BORDER + AWT_W_BUTTON;
                btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                frame.add(btn);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_RESET;
                        frame.dispose();
                    }
                });

            }

            if(btns==CntButtons.two)
            {
                int xBtn=x+awt_w_text/2-AWT_BORDER/2-AWT_W_BUTTON,yBtn=y;
                Button btn;

                btn = new Button("Ok");
                btn.setLocation(xBtn, yBtn);
                xBtn += AWT_BORDER + AWT_W_BUTTON;
                btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                frame.add(btn);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_OK;
                        frame.dispose();
                    }
                });

                btn = new Button("Reset");
                btn.setLocation(xBtn, yBtn);
                xBtn += AWT_BORDER + AWT_W_BUTTON;
                btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                frame.add(btn);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_RESET;
                        frame.dispose();
                    }
                });

            }
            if(btns==CntButtons.one){
                int xBtn=x+awt_w_text/2-AWT_W_BUTTON/2,yBtn=y;
                Button btn;

                btn = new Button("Ok");
                btn.setLocation(xBtn, yBtn);
                xBtn += AWT_BORDER + AWT_W_BUTTON;
                btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                frame.add(btn);

                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_OK;
                        frame.dispose();
                    }
                });
            }
            y+=AWT_H_BUTTON+AWT_BORDER;

        }
        frame.setSize(awt_w_text+2*AWT_BORDER,y);
        frame.setMaximumSize(frame.getSize());
        frame.setMinimumSize(frame.getSize());
        frame.setVisible(true);
    }
}

class QueryFrameSwing{
    public static final int RESULT_UNKNOWN=0;
    public static final int RESULT_OK=1;
    public static final int RESULT_RESET=2;
    public static final int RESULT_NO=3;
    public enum CntButtons{one,two,three};
    private int result=RESULT_UNKNOWN;
    private int getResult(){return result;}
    private static final int AWT_BORDER=20;
    private static final int AWT_H_BUTTON=25;
    private static final int AWT_W_BUTTON=20*3;
    private static final int AWT_H_TEXT=20;
    private static final int AWT_W_TEXT_MIN=100;
    private int awt_w_text;
    private static final int AWT_TOP_BORDER=AWT_BORDER;
    private JDialog frame;
    private Boolean query;
    public static int Query(String msg,String title, Frame owner, CntButtons btns)
    {
        final QueryFrameSwing queryFrame=new QueryFrameSwing(msg,title,owner,btns);
        return queryFrame.getResult();
    }
    private QueryFrameSwing(String msg,String title,Frame owner,CntButtons btns)
    {
        frame=new JDialog(owner,title,true);
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        JPanel panel;
        {
            JPanel pnlHor=new JPanel();
            BoxLayout pnlHorLayout=new BoxLayout(pnlHor,BoxLayout.X_AXIS);
            pnlHor.setLayout(pnlHorLayout);

            pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
            panel=new JPanel();
            {
                BoxLayout pnlVertLayout=new BoxLayout(panel,BoxLayout.Y_AXIS);
                panel.setLayout(pnlVertLayout);
                Font font = new Font("Serif", Font.PLAIN, 15);
                panel.setFont(font);
            }
            frame.setLayout(new FlowLayout());
            pnlHor.add(panel);
            pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
            frame.add(pnlHor);
        }

        panel.add(Box.createVerticalStrut(AWT_BORDER));
        {
            JLabel lbl=new JLabel(msg);
            lbl.setAlignmentX(JComponent.CENTER_ALIGNMENT);
            panel.add(lbl);
        }
        panel.add(Box.createVerticalStrut(AWT_BORDER));
        {
            Dimension dimBtn=new Dimension(AWT_W_BUTTON,AWT_H_BUTTON);
            if(btns==CntButtons.three)
            {
                Box box=Box.createHorizontalBox();
                box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                JButton btn;

                btn = new JButton("Yes");
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_OK;
                        frame.dispose();
                    }
                });
                box.add(btn);
                box.add(Box.createHorizontalStrut(AWT_BORDER));

                btn = new JButton("No");
                btn.setSize(dimBtn);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_NO;
                        frame.dispose();
                    }
                });
                box.add(btn);
                box.add(Box.createHorizontalStrut(AWT_BORDER));

                btn = new JButton("Reset");
                btn.setSize(dimBtn);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_RESET;
                        frame.dispose();
                    }
                });
                box.add(btn);
                panel.add(box);

            }

            if(btns==CntButtons.two)
            {
                Box box=Box.createHorizontalBox();
                box.setAlignmentX(JComponent.CENTER_ALIGNMENT);
                JButton btn;

                btn = new JButton("Ok");
                btn.setSize(dimBtn);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_OK;
                        frame.dispose();
                    }
                });
                box.add(btn);
                box.add(Box.createHorizontalStrut(AWT_BORDER));

                btn = new JButton("Reset");
                btn.setSize(dimBtn);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_RESET;
                        frame.dispose();
                    }
                });
                box.add(btn);
                panel.add(box);
            }
            if(btns==CntButtons.one){
                JButton btn;
                btn = new JButton("Ok");
                btn.setSize(dimBtn);
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        result=RESULT_OK;
                        frame.dispose();
                    }
                });
                btn.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(btn);
            }

        }
        panel.add(Box.createVerticalStrut(AWT_BORDER));
        frame.pack();
        frame.setVisible(true);
    }
}

class MainFrameSwing extends JFrame{
    private static final int AWT_BORDER=20;
    private static final int AWT_H_BUTTON=25;
    private static final int AWT_W_BUTTON=20*3;
    private static final int AWT_H_TEXT=20;
    private static final int AWT_H_LIST=80;
    private static final int AWT_W_LIST=AWT_W_BUTTON*3+AWT_BORDER*2;
    private static final int AWT_H_EDIT=340;
    private static final int AWT_W_EDIT=400;
    private static final int AWT_H_MESSAGE=80;
    private static final int AWT_TOP_BORDER=AWT_BORDER;


    private static final int AWT_MIDDLE_BORDER=5;
    private static final int AWT_H_STEXT=20;
    private static final int AWT_W_STEXT=70;

    private static final int AWT_H_SEDIT_ADD=2;
    private static final int AWT_H_SEDIT=20;
    private static final int AWT_W_SEDIT=AWT_W_LIST-AWT_MIDDLE_BORDER-AWT_W_STEXT;

    protected JTextPane txtEdit;
    protected String[] data;
    protected JList<String> listContacts;
    private static MainFrameSwing mainFrame=null;
    private JPanel pnlContacts;
    private JPanel pnlConnection;
    private JPanel pnlServer;
    private JTextArea txtMessage;
    private JTextField txtLogin,txtM,txtE,txtD,txtIp,txtPort;
    private JButton btnConnect,btnRemove,btnStart,btnStop;
    private GUIMethods guiMethods;
    private UserContact currentUserContact;
    private MainFrameSwing()
    {
        super("ENIGMA");
        setFont(new Font("Serif", Font.PLAIN, 15));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        guiMethods=new GUIMethods();
        currentUserContact=null;

        JPanel globalPanel;
        {
            JPanel pnlHor=new JPanel();
            BoxLayout pnlHorLayout=new BoxLayout(pnlHor,BoxLayout.X_AXIS);
            pnlHor.setLayout(pnlHorLayout);

            pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
            globalPanel=new JPanel();
            {
                BoxLayout pnlVertLayout=new BoxLayout(globalPanel,BoxLayout.Y_AXIS);
                globalPanel.setLayout(pnlVertLayout);
                Font font = new Font("Serif", Font.PLAIN, 15);
                globalPanel.setFont(font);
            }
            this.setLayout(new FlowLayout());
            pnlHor.add(globalPanel);
            pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
            this.add(pnlHor);
        }

        {
            pnlContacts = new JPanel();
            pnlContacts.setFont(new Font("Serif", Font.PLAIN, 15));
            pnlContacts.setLocation(AWT_BORDER, AWT_BORDER + AWT_TOP_BORDER);
            pnlContacts.setBackground(new Color(200, 100, 200));
            pnlContacts.setForeground(new Color(0, 0, 0));

            JPanel panel;
            {
                JPanel pnlHor=new JPanel();
                pnlHor.setBackground(new Color(200, 100, 200));
                pnlHor.setForeground(new Color(0, 0, 0));
                BoxLayout pnlHorLayout=new BoxLayout(pnlHor,BoxLayout.X_AXIS);
                pnlHor.setLayout(pnlHorLayout);

                pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
                panel=new JPanel();
                panel.setBackground(new Color(200, 100, 200));
                panel.setForeground(new Color(0, 0, 0));
                {
                    BoxLayout pnlVertLayout=new BoxLayout(panel,BoxLayout.Y_AXIS);
                    panel.setLayout(pnlVertLayout);
                    Font font = new Font("Serif", Font.PLAIN, 15);
                    panel.setFont(font);
                }
                pnlContacts.setLayout(new FlowLayout());
                pnlHor.add(panel);
                pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
                pnlContacts.add(pnlHor);
            }

            panel.add(Box.createVerticalStrut(AWT_BORDER));


            {
                Box box=Box.createHorizontalBox();
                box.add(Box.createHorizontalGlue());
                JLabel lbl = new JLabel("ContactList");
                box.add(lbl);
                box.add(Box.createHorizontalGlue());
                panel.add(box);
            }

            panel.add(Box.createVerticalStrut(AWT_BORDER));

            {
                data=new String[]{"123","123","123"};
                JList<String> lst = new JList<String>(data);
                listContacts = lst;
                listContacts.addListSelectionListener(guiMethods.new ContactListSelectionChanged(this));
                lst.setAlignmentX(Component.CENTER_ALIGNMENT);
                lst.setPreferredSize(new Dimension(AWT_W_LIST,AWT_H_LIST));
                System.out.println("List size:");
                System.out.println(AWT_W_LIST);
                System.out.println(AWT_H_LIST);
                lst.setMinimumSize(lst.getPreferredSize());
                JPanel pnl=new JPanel();
                pnl.setLayout(new BorderLayout(0,0));
                JScrollPane scrollPane = new JScrollPane(lst,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
                //scrollPane.setMaximumSize(new Dimension(AWT_W_LIST,AWT_H_LIST));
                //scrollPane.setMinimumSize (new Dimension(AWT_W_LIST,AWT_H_LIST));
                pnl.add(scrollPane);
                panel.add(pnl);
            }

            panel.add(Box.createVerticalStrut(AWT_BORDER));

            {
                Box boxBtns=Box.createHorizontalBox();
                JButton btn = new JButton("Add");
                //btn.setPreferredSize(new Dimension(AWT_W_BUTTON, AWT_H_BUTTON));
                boxBtns.add(btn); boxBtns.add(Box.createHorizontalStrut(AWT_BORDER));
                btn.addActionListener(guiMethods.new BtnAddClient(this));

                btnRemove = btn = new JButton("Remove");
                //btn.setPreferredSize(new Dimension(AWT_W_BUTTON, AWT_H_BUTTON));
                boxBtns.add(btn); boxBtns.add(Box.createHorizontalStrut(AWT_BORDER));
                boxBtns.add(Box.createHorizontalGlue());
                btn.addActionListener(guiMethods.new BtnRemoveClient(this));

                btnConnect = btn = new JButton("Connect");
                //btn.setPreferredSize(new Dimension(AWT_W_BUTTON, AWT_H_BUTTON));
                boxBtns.add(btn);
                panel.add(boxBtns);
                btn.addActionListener(guiMethods.new BtnConnectClient(this));
            }
            panel.add(Box.createVerticalStrut(AWT_BORDER));

        }
        {
            pnlServer=new JPanel();
            pnlServer.setFont(new Font("Serif", Font.PLAIN, 15));
            pnlServer.setBackground(new Color(200, 200, 50));
            pnlServer.setForeground(new Color(0, 0, 0));
            pnlServer.setLocation(AWT_BORDER,AWT_BORDER*2+pnlContacts.getHeight()+AWT_TOP_BORDER);

            JPanel panel;
            {
                JPanel pnlHor=new JPanel();
                pnlHor.setBackground(new Color(200, 200, 50));
                pnlHor.setForeground(new Color(0, 0, 0));
                BoxLayout pnlHorLayout=new BoxLayout(pnlHor,BoxLayout.X_AXIS);
                pnlHor.setLayout(pnlHorLayout);

                pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
                panel=new JPanel();
                panel.setBackground(new Color(200, 200, 50));
                panel.setForeground(new Color(0, 0, 0));
                {
                    BoxLayout pnlVertLayout=new BoxLayout(panel,BoxLayout.Y_AXIS);
                    panel.setLayout(pnlVertLayout);
                    Font font = new Font("Serif", Font.PLAIN, 15);
                    panel.setFont(font);
                }
                pnlServer.setLayout(new FlowLayout());
                pnlHor.add(panel);
                pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
                pnlServer.add(pnlHor);
            }

            panel.add(Box.createVerticalStrut(AWT_BORDER));

            {
                Box box=Box.createHorizontalBox();
                box.add(Box.createHorizontalGlue());
                JLabel lbl = new JLabel("Server");
                box.add(lbl);
                box.add(Box.createHorizontalGlue());
                panel.add(box);
            }

            panel.add(Box.createVerticalStrut(AWT_BORDER));

            {
                Box boxHor;
                JTextField txt;

                JLabel lbl = new JLabel("login:");

                txtLogin=txt=new JTextField("user",20);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setPreferredSize(new Dimension(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD));
                txt.setMaximumSize(txt.getPreferredSize());
                boxHor=Box.createHorizontalBox(); boxHor.add(Box.createHorizontalGlue());
                boxHor.add(lbl); boxHor.add(Box.createHorizontalStrut(AWT_MIDDLE_BORDER)); boxHor.add(txt);
                panel.add(boxHor); panel.add(Box.createVerticalStrut(AWT_BORDER));

                lbl = new JLabel("M:",SwingConstants.RIGHT);

                txtM=txt=new JTextField("505328582712267379670617057031456412648133198298113795322055388046957411865388383684826599331989921538391578601261291782060429810000904121670209210618991442805378573315330642902868268518281829821088107828255901899669812392602881563355885322555356435347307075034083484667631848695799702679812829446350404849",20);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setPreferredSize(new Dimension(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD));
                txt.setMaximumSize(txt.getPreferredSize());
                boxHor=Box.createHorizontalBox(); boxHor.add(Box.createHorizontalGlue());
                boxHor.add(lbl); boxHor.add(Box.createHorizontalStrut(AWT_MIDDLE_BORDER)); boxHor.add(txt);
                panel.add(boxHor); panel.add(Box.createVerticalStrut(AWT_BORDER));


                lbl = new JLabel("E:",SwingConstants.RIGHT);

                txtE=txt=new JTextField("19",20);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setPreferredSize(new Dimension(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD));
                txt.setMaximumSize(txt.getPreferredSize());
                boxHor=Box.createHorizontalBox(); boxHor.add(Box.createHorizontalGlue());
                boxHor.add(lbl); boxHor.add(Box.createHorizontalStrut(AWT_MIDDLE_BORDER)); boxHor.add(txt);
                panel.add(boxHor); panel.add(Box.createVerticalStrut(AWT_BORDER));

                lbl = new JLabel("D:",SwingConstants.RIGHT);

                txtD=txt=new JTextField("79788723586147481000623745847072065154968399731281125577166640217940643978745534266025252526103671821851301884409677649756519217576060126152013648738834115569557723830211673224899100047246087323077845838488231251345580647327928968077861595525702625052750974624691780284383267722427455369306490531580110939",20);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setPreferredSize(new Dimension(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD));
                txt.setMaximumSize(txt.getPreferredSize());
                boxHor=Box.createHorizontalBox(); boxHor.add(Box.createHorizontalGlue());
                boxHor.add(lbl); boxHor.add(Box.createHorizontalStrut(AWT_MIDDLE_BORDER)); boxHor.add(txt);
                panel.add(boxHor); panel.add(Box.createVerticalStrut(AWT_BORDER));

                lbl = new JLabel("ip:",SwingConstants.RIGHT);

                txtIp=txt=new JTextField("127.0.0.1",20);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setPreferredSize(new Dimension(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD));
                txt.setMaximumSize(txt.getPreferredSize());
                boxHor=Box.createHorizontalBox(); boxHor.add(Box.createHorizontalGlue());
                boxHor.add(lbl); boxHor.add(Box.createHorizontalStrut(AWT_MIDDLE_BORDER)); boxHor.add(txt);
                panel.add(boxHor); panel.add(Box.createVerticalStrut(AWT_BORDER));

                lbl = new JLabel("port:",SwingConstants.RIGHT);

                txtPort=txt=new JTextField("12345",20);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setPreferredSize(new Dimension(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD));
                txt.setMaximumSize(txt.getPreferredSize());
                boxHor=Box.createHorizontalBox(); boxHor.add(Box.createHorizontalGlue());
                boxHor.add(lbl); boxHor.add(Box.createHorizontalStrut(AWT_MIDDLE_BORDER)); boxHor.add(txt);
                panel.add(boxHor); panel.add(Box.createVerticalStrut(AWT_BORDER));

            }

            {
                Box boxBtns=Box.createHorizontalBox();
                JButton btn;
                btnStart = btn = new JButton("Start");
                boxBtns.add(btn); boxBtns.add(Box.createHorizontalGlue());
                btn.addActionListener(guiMethods.new BtnStartServer(this));

                btnStop = btn = new JButton("Stop");
                boxBtns.add(btn);
                panel.add(boxBtns);
                btn.addActionListener(guiMethods.new BtnStopServer(this));

            }
            panel.add(Box.createVerticalStrut(AWT_BORDER));
        }
        {
            pnlConnection=new JPanel();
            pnlConnection.setFont(new Font("Serif", Font.PLAIN, 15));
            pnlConnection.setBackground(new Color(200, 100, 100));
            pnlConnection.setForeground(new Color(0, 0, 0));


            pnlConnection.setLocation(AWT_BORDER*2+pnlContacts.getWidth(),AWT_BORDER+AWT_TOP_BORDER);

            JPanel panel;
            {
                JPanel pnlHor=new JPanel();
                pnlHor.setBackground(new Color(200, 100, 100));
                pnlHor.setForeground(new Color(0, 0, 0));
                BoxLayout pnlHorLayout=new BoxLayout(pnlHor,BoxLayout.X_AXIS);
                pnlHor.setLayout(pnlHorLayout);

                pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
                panel=new JPanel();
                panel.setBackground(new Color(200, 100, 100));
                panel.setForeground(new Color(0, 0, 0));
                {
                    BoxLayout pnlVertLayout=new BoxLayout(panel,BoxLayout.Y_AXIS);
                    panel.setLayout(pnlVertLayout);
                    Font font = new Font("Serif", Font.PLAIN, 15);
                    panel.setFont(font);
                }
                pnlConnection.setLayout(new FlowLayout());
                pnlHor.add(panel);
                pnlHor.add(Box.createHorizontalStrut(AWT_BORDER));
                pnlConnection.add(pnlHor);
            }

            panel.add(Box.createVerticalStrut(AWT_BORDER));
            {
                Box box=Box.createHorizontalBox();
                box.add(Box.createHorizontalGlue());
                JLabel lbl = new JLabel("Connection");
                box.add(lbl);
                box.add(Box.createHorizontalGlue());
                panel.add(box);
            }
            panel.add(Box.createVerticalStrut(AWT_BORDER));
            {
                JTextPane txt=new JTextPane();
                txtEdit=txt;
                txt.setContentType("text/html");
                txt.setEditable(false);
                txt.setBackground(new Color(200,200,200));
                txt.setEditorKit(new HTMLEditorKit());
                txt.setAutoscrolls(true);
                txt.setLocation(0,0);
                txt.setPreferredSize(new Dimension(AWT_W_EDIT,AWT_H_EDIT));
                JScrollPane jsp=new JScrollPane(txt);
                jsp.setPreferredSize(new Dimension(AWT_W_EDIT,AWT_H_EDIT));
                System.out.print(txt.getHeight());

                DefaultCaret caret = (DefaultCaret)txt.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                txt.setText("<font color=\"ff0000\"> asf\ndf<br><br><br><br><br><br><br><br><br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>");
                panel.add(jsp);
            }
            panel.add(Box.createVerticalStrut(AWT_BORDER));

            {
                {
                    Box box = Box.createHorizontalBox();
                    JLabel lbl = new JLabel("Message");
                    box.add(lbl);
                    box.add(Box.createHorizontalGlue());
                    panel.add(box);
                }
                JTextArea txt;
                txtMessage = txt = new JTextArea("",4,40);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setPreferredSize(new Dimension(AWT_W_EDIT,AWT_H_MESSAGE));
                panel.add(txt);
                panel.add(Box.createVerticalStrut(AWT_BORDER));
                {
                    Box boxBtns=Box.createHorizontalBox();
                    JButton btn;
                    btn = new JButton("Sent");
                    boxBtns.add(btn);boxBtns.add(Box.createHorizontalGlue());
                    btn.addActionListener(guiMethods.new BtnSendClient(this));

                    btn = new JButton("Close");
                    boxBtns.add(btn);
                    btn.addActionListener(guiMethods.new BtnCloseClient(this));

                    panel.add(boxBtns);
                }
            }
            panel.add(Box.createVerticalStrut(AWT_BORDER));

        }

        {
            Box globalHorBox=Box.createHorizontalBox();

            Box leftVertBox=Box.createVerticalBox();

            leftVertBox.add(pnlContacts);
            leftVertBox.add(Box.createVerticalStrut(AWT_BORDER));
            leftVertBox.add(pnlServer);

            globalHorBox.add(leftVertBox);
            globalHorBox.add(Box.createHorizontalStrut(AWT_BORDER));
            globalHorBox.add(pnlConnection);

            globalPanel.add(Box.createVerticalStrut(AWT_BORDER));
            globalPanel.add(globalHorBox);
            globalPanel.add(Box.createVerticalStrut(AWT_BORDER));

        }
        {
            this.pack();
            this.setVisible(true);

        }
        updateContactList();
        setListContactsButtonsAndPanelConnection(true);
        enableComponents(pnlConnection,false);
        setServerButtons(true);
    }
    private void enableComponents(Container container, boolean enable) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(enable);
            if (component instanceof Container) {
                enableComponents((Container)component, enable);
            }
        }
    }
    private void updateContactList()
    {
        int selectedIndex=listContacts.getSelectedIndex();
        String[] m=UserContactList.get().getLoginList();
        if(m.length<=selectedIndex) selectedIndex=m.length-1;
        listContacts.setListData(m);
        listContacts.setSelectedIndex(selectedIndex);
    }
    private void setServerEditor(Boolean isEnabled)
    {
        txtLogin.setEnabled(isEnabled);
        txtM.setEnabled(isEnabled);
        txtE.setEnabled(isEnabled);
        txtD.setEnabled(isEnabled);
        txtIp.setEnabled(isEnabled);
        txtPort.setEnabled(isEnabled);
    }
    private void setServerButtons(Boolean isStop)
    {
            btnStop.setEnabled(!isStop);
            btnStart.setEnabled(isStop);
            setServerEditor(isStop);
    }
    private void setListContactsButtonsAndPanelConnection(Boolean isOpen)
    {
        btnRemove.setEnabled(!isOpen);
        btnConnect.setEnabled(!isOpen);
        enableComponents(pnlConnection,isOpen);
        updateMessageList();
    }
    public void updateMessageList()
    {
        String resMessages=new String();
        if(currentUserContact!=null)
        {
            User user=currentUserContact.getUser();
            User I=UserListener.get();
            Vector<Message> msgs=currentUserContact.getHistory();
            Iterator<Message> iterMsgs=msgs.iterator();
            while (iterMsgs.hasNext())
            {
                Message msg=iterMsgs.next();
                String strMsg=new String();
                if(msg.isMy())
                    strMsg+="<font color=\"aa0000\">"+I.getLogin()+":";
                else
                    strMsg+="<font color=\"0000aa\">"+user.getLogin()+":";
                strMsg+=msg.getDate()+">><br>"+msg.getContent()+"<br>";
                resMessages=resMessages.concat(strMsg);
            }
        }
        System.out.println("resMessages update");
        txtEdit.setText(resMessages);
    }
    public void updateConnection()
    {
        if(currentUserContact==null)
        {
            setListContactsButtonsAndPanelConnection(false);
        }else
        {
            setListContactsButtonsAndPanelConnection(currentUserContact.isOpen());
        }
        updateMessageList();

    }
    public static MainFrameSwing get(){if(mainFrame==null) mainFrame=new MainFrameSwing(); return mainFrame;}
    class GUIMethods
    {
        public class BtnAddClient implements ActionListener{
            private MainFrameSwing owner;
            public BtnAddClient(MainFrameSwing frame){owner=frame;}
            public void actionPerformed(ActionEvent e) {
                Map<String,String> res=AddContactFrameSwing.Query(owner);
                UserContact userContact=new UserContact(new User(res.get("ip"),Integer.parseInt(res.get("port")),res.get("login"),new BigInteger(res.get("M")),new BigInteger(res.get("E"))));
                UserContactList.get().add(userContact);
                owner.updateContactList();
                System.out.println(res);
            }
        }
        public class BtnRemoveClient implements ActionListener{
            private MainFrameSwing owner;
            public BtnRemoveClient(MainFrameSwing frame){owner=frame;}
            public void actionPerformed(ActionEvent e) {
                Integer selectedIndex=owner.listContacts.getSelectedIndex();
                System.out.println("remove client:"+selectedIndex);
                UserContactList.get().removeByIndex(selectedIndex);
                owner.updateContactList();
            }
        }
        public class ContactListSelectionChanged implements ListSelectionListener {
            private MainFrameSwing owner;
            public ContactListSelectionChanged(MainFrameSwing frame){owner=frame;}
            public void valueChanged(ListSelectionEvent e) {
                Integer index=owner.listContacts.getSelectedIndex();
                if(index>=0) {
                    UserContact oldCurrentUserContact=owner.currentUserContact;
                    System.out.println("selected:" + owner.listContacts.getSelectedIndex());
                    owner.currentUserContact = UserContactList.get().getByIndex(index);
                    Boolean isOpen = owner.currentUserContact.isOpen();
                    owner.setListContactsButtonsAndPanelConnection(isOpen);
                    if(oldCurrentUserContact!=owner.currentUserContact)
                        owner.updateMessageList();
                }else{
                    owner.currentUserContact=null;
                    Boolean isOpen = true;
                    owner.setListContactsButtonsAndPanelConnection(isOpen);
                    owner.enableComponents(owner.pnlConnection,false);
                }
            }

        }
        public class BtnConnectClient implements ActionListener {
            private MainFrameSwing owner;
            public BtnConnectClient(MainFrameSwing frame){owner=frame;}
            public void actionPerformed(ActionEvent e) {
                if(currentUserContact==null) return;
                {
                    User myInfo=UserListener.get();
                    myInfo.setLogin(txtLogin.getText());
                    myInfo.setIp(txtIp.getText());
                    myInfo.setPort(Integer.parseInt(txtPort.getText()));
                }
                try {
                    if(currentUserContact.isOpen())
                    {}//currentUserContact.close();
                    else
                        currentUserContact.open();
                }catch (Exception error)
                {
                    QueryFrameSwing.Query(error.getMessage()+"\n"+error.getStackTrace().toString(),"ERROR",owner, QueryFrameSwing.CntButtons.one);
                }
                setListContactsButtonsAndPanelConnection(currentUserContact.isOpen());
                updateMessageList();

            }
        }
        public class BtnCloseClient implements ActionListener {
            private MainFrameSwing owner;
            public BtnCloseClient(MainFrameSwing frame){owner=frame;}
            public void actionPerformed(ActionEvent e) {
                if(currentUserContact==null) return;
                try {
                    if(currentUserContact.isOpen())
                        currentUserContact.close();
                    else
                        ;//currentUserContact.open();
                }catch (Exception error)
                {
                    QueryFrameSwing.Query(error.getMessage()+"\n"+error.getStackTrace().toString(),"ERROR",owner, QueryFrameSwing.CntButtons.one);
                }
                setListContactsButtonsAndPanelConnection(currentUserContact.isOpen());
                updateMessageList();

            }
        }
        public class BtnStartServer implements ActionListener {
            private MainFrameSwing owner;
            public BtnStartServer(MainFrameSwing frame){owner=frame;}
            public void actionPerformed(ActionEvent e) {
                User user=UserListener.get();
                user.setLogin(txtLogin.getText());
                user.setIp(txtIp.getText());
                user.setShifrator(new UserShifrator(new BigInteger(txtM.getText()),new BigInteger(txtD.getText()),new BigInteger(txtE.getText())));
                user.setPort(Integer.parseInt(txtPort.getText()));
                try {
                    Listener.get().start(user);
                }catch (Exception error)
                {
                    QueryFrameSwing.Query(error.getMessage()+"\n"+error.getStackTrace().toString(),"ERROR",owner, QueryFrameSwing.CntButtons.one);
                }
                setServerButtons(!Listener.get().isStarted());
            }
        }
        public class BtnStopServer implements ActionListener {
            private MainFrameSwing owner;
            public BtnStopServer(MainFrameSwing frame){owner=frame;}
            public void actionPerformed(ActionEvent e) {
                try {
                    Listener.get().stop();
                }catch (Exception error)
                {
                    QueryFrameSwing.Query(error.getMessage()+"\n"+error.getStackTrace().toString(),"ERROR",owner, QueryFrameSwing.CntButtons.one);
                }
                setServerButtons(!Listener.get().isStarted());

            }
        }
        public class BtnSendClient implements ActionListener {
            private MainFrameSwing owner;
            public BtnSendClient(MainFrameSwing frame){owner=frame;}
            public void actionPerformed(ActionEvent e) {
                if(currentUserContact==null) return;
                try {
                    if(currentUserContact.isOpen())
                    {
                        currentUserContact.send(txtMessage.getText().replaceAll("\n","<br>"));
                        txtMessage.setText(new String());
                    }//currentUserContact.close();
                }catch (Exception error)
                {
                    QueryFrameSwing.Query(error.getMessage()+"\n"+error.getStackTrace().toString(),"ERROR",owner, QueryFrameSwing.CntButtons.one);
                }
                setListContactsButtonsAndPanelConnection(currentUserContact.isOpen());

            }
        }
    }
}

class MainFrame extends Frame{
    private static final int AWT_BORDER=20;
    private static final int AWT_H_BUTTON=25;
    private static final int AWT_W_BUTTON=20*3;
    private static final int AWT_H_TEXT=20;
    private static final int AWT_H_LIST=100;
    private static final int AWT_W_LIST=AWT_W_BUTTON*3+AWT_BORDER*2;
    private static final int AWT_H_EDIT=340;
    private static final int AWT_W_EDIT=400;
    private static final int AWT_H_MESSAGE=80;
    private static final int AWT_TOP_BORDER=AWT_BORDER;


    private static final int AWT_MIDDLE_BORDER=5;
    private static final int AWT_H_STEXT=20;
    private static final int AWT_W_STEXT=70;

    private static final int AWT_H_SEDIT_ADD=5;
    private static final int AWT_H_SEDIT=20;
    private static final int AWT_W_SEDIT=AWT_W_LIST-AWT_MIDDLE_BORDER-AWT_W_STEXT;

    protected JTextPane txtEdit;
    protected java.awt.List listContacts;
    private static MainFrame mainFrame=null;
    private Panel pnlContacts;
    private Panel pnlConnection;
    private Panel pnlServer;
    private MainFrame()
    {
        super("");
        setLayout(null);
        setFont(new Font("Serif", Font.PLAIN, 15));
        this.setTitle("ENIGMA");
        this.setVisible(true);
        {
            pnlContacts = new Panel();
            pnlContacts.setLayout(null);
            pnlContacts.setFont(new Font("Serif", Font.PLAIN, 15));
            pnlContacts.setLocation(AWT_BORDER, AWT_BORDER + AWT_TOP_BORDER);
            pnlContacts.setBackground(new Color(200, 100, 200));
            pnlContacts.setForeground(new Color(0, 0, 0));

            int x = AWT_BORDER, y = AWT_BORDER;

            {
                Label lbl = new Label("ContactList", Label.CENTER);
                lbl.setLocation(x, y);
                y += AWT_BORDER + AWT_H_TEXT;
                lbl.setSize(AWT_W_LIST, AWT_H_TEXT);
                pnlContacts.add(lbl);
            }

            {
                java.awt.List lst = new java.awt.List(3, false);
                lst.setLocation(x, y);
                y += AWT_BORDER + AWT_H_LIST;
                lst.setSize(AWT_W_LIST, AWT_H_LIST);
                lst.add("123");
                lst.add("123");
                lst.add("123");
                listContacts = lst;
                pnlContacts.add(lst);
            }

            {
                int xBtn = x, yBtn = y;
                Button btn = new Button("Add");
                btn.setLocation(xBtn, yBtn);
                xBtn += AWT_BORDER + AWT_W_BUTTON;
                btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                pnlContacts.add(btn);

                btn = new Button("Remove");
                btn.setLocation(xBtn, yBtn);
                xBtn += AWT_BORDER + AWT_W_BUTTON;
                btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                pnlContacts.add(btn);

                btn = new Button("Connect");
                btn.setLocation(xBtn, yBtn);
                xBtn += AWT_BORDER + AWT_W_BUTTON;
                btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                pnlContacts.add(btn);

                y += AWT_BORDER + AWT_H_BUTTON;
            }

            this.add(pnlContacts);
            pnlContacts.setSize(AWT_W_LIST + 2 * AWT_BORDER, y);
            pnlContacts.setVisible(true);
        }
        {
            pnlServer=new Panel();
            pnlServer.setLayout(null);
            pnlServer.setFont(new Font("Serif", Font.PLAIN, 15));
            pnlServer.setBackground(new Color(200, 200, 50));

            pnlServer.setLocation(AWT_BORDER,AWT_BORDER*2+pnlContacts.getHeight()+AWT_TOP_BORDER);

            int x=AWT_BORDER, y=AWT_BORDER;

            {
                Label lbl = new Label("Server", Label.CENTER);
                lbl.setLocation(x, y);
                y += AWT_BORDER + AWT_H_TEXT;
                lbl.setSize(AWT_W_LIST, AWT_H_TEXT);
                pnlServer.add(lbl);
            }

            {
                Label lbl = new Label("login:", Label.RIGHT);
                lbl.setLocation(x, y);
                lbl.setSize(AWT_W_STEXT, AWT_H_STEXT);
                pnlServer.add(lbl);

                TextArea txt;
                txt=new TextArea("",1,20,TextArea.SCROLLBARS_NONE);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setLocation(x+AWT_W_STEXT+AWT_MIDDLE_BORDER,y-AWT_H_SEDIT_ADD/2);
                txt.setSize(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD);
                pnlServer.add(txt);

                y += AWT_BORDER + AWT_H_TEXT;
            }
            {
                Label lbl = new Label("M:", Label.RIGHT);
                lbl.setLocation(x, y);
                lbl.setSize(AWT_W_STEXT, AWT_H_STEXT);
                pnlServer.add(lbl);

                TextArea txt;
                txt=new TextArea("",1,20,TextArea.SCROLLBARS_NONE);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setLocation(x+AWT_W_STEXT+AWT_MIDDLE_BORDER,y-AWT_H_SEDIT_ADD/2);
                txt.setSize(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD);
                pnlServer.add(txt);

                y += AWT_BORDER + AWT_H_TEXT;
            }
            {
                Label lbl = new Label("E:", Label.RIGHT);
                lbl.setLocation(x, y);
                lbl.setSize(AWT_W_STEXT, AWT_H_STEXT);
                pnlServer.add(lbl);

                TextArea txt;
                txt=new TextArea("",1,20,TextArea.SCROLLBARS_NONE);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setLocation(x+AWT_W_STEXT+AWT_MIDDLE_BORDER,y-AWT_H_SEDIT_ADD/2);
                txt.setSize(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD);
                pnlServer.add(txt);

                y += AWT_BORDER + AWT_H_TEXT;
            }
            {
                Label lbl = new Label("D:", Label.RIGHT);
                lbl.setLocation(x, y);
                lbl.setSize(AWT_W_STEXT, AWT_H_STEXT);
                pnlServer.add(lbl);

                TextArea txt;
                txt=new TextArea("",1,20,TextArea.SCROLLBARS_NONE);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setLocation(x+AWT_W_STEXT+AWT_MIDDLE_BORDER,y-AWT_H_SEDIT_ADD/2);
                txt.setSize(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD);
                pnlServer.add(txt);

                y += AWT_BORDER + AWT_H_TEXT;
            }
            {
                Label lbl = new Label("ip:", Label.RIGHT);
                lbl.setLocation(x, y);
                lbl.setSize(AWT_W_STEXT, AWT_H_STEXT);
                pnlServer.add(lbl);

                TextArea txt;
                txt=new TextArea("",1,20,TextArea.SCROLLBARS_NONE);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setLocation(x+AWT_W_STEXT+AWT_MIDDLE_BORDER,y-AWT_H_SEDIT_ADD/2);
                txt.setSize(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD);
                pnlServer.add(txt);

                y += AWT_BORDER + AWT_H_TEXT;
            }
            {
                Label lbl = new Label("port:", Label.RIGHT);
                lbl.setLocation(x, y);
                lbl.setSize(AWT_W_STEXT, AWT_H_STEXT);
                pnlServer.add(lbl);

                TextArea txt;
                txt=new TextArea("",1,20,TextArea.SCROLLBARS_NONE);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setLocation(x+AWT_W_STEXT+AWT_MIDDLE_BORDER,y-AWT_H_SEDIT_ADD/2);
                txt.setSize(AWT_W_SEDIT,AWT_H_SEDIT+AWT_H_SEDIT_ADD);
                pnlServer.add(txt);

                y += AWT_BORDER + AWT_H_TEXT;
            }

            {
                int xBtn=x+AWT_W_LIST/2-AWT_BORDER/2-AWT_W_BUTTON,yBtn=y;
                Button btn;
                btn = new Button("Start");
                btn.setLocation(xBtn, yBtn);
                xBtn += AWT_BORDER + AWT_W_BUTTON;
                btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                pnlServer.add(btn);


                btn = new Button("Stop");
                btn.setLocation(xBtn, yBtn);
                xBtn += AWT_BORDER + AWT_W_BUTTON;
                btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                pnlServer.add(btn);

                y+=AWT_H_BUTTON+AWT_BORDER;
            }
            this.add(pnlServer);
            pnlServer.setSize(AWT_W_LIST + 2 * AWT_BORDER, y);
            pnlServer.setVisible(true);
        }
        {
            pnlConnection=new Panel();
            pnlConnection.setLayout(null);
            pnlConnection.setFont(new Font("Serif", Font.PLAIN, 15));
            pnlConnection.setBackground(new Color(200, 100, 100));

            pnlConnection.setLocation(AWT_BORDER*2+pnlContacts.getWidth(),AWT_BORDER+AWT_TOP_BORDER);

            int x=AWT_BORDER,y=AWT_BORDER;

            {
                Label lbl = new Label("Connection", Label.CENTER);
                lbl.setLocation(x, y);
                y += AWT_BORDER + AWT_H_TEXT;
                lbl.setSize(AWT_W_EDIT, AWT_H_TEXT);
                pnlConnection.add(lbl);
            }
            {
                JTextPane txt=new JTextPane();
                txtEdit=txt;
                txt.setContentType("text/html");
                txt.setEditable(false);
                txt.setBackground(new Color(200,200,200));
                txt.setEditorKit(new HTMLEditorKit());
                txt.setAutoscrolls(true);
                txt.setLocation(0,0);
                txt.setSize(AWT_W_EDIT,AWT_H_EDIT);
                JScrollPane jsp=new JScrollPane(txt);
                jsp.setLocation(x,y);
                y+=AWT_H_EDIT+AWT_BORDER;
                jsp.setSize(AWT_W_EDIT,AWT_H_EDIT);
                System.out.print(txt.getHeight());
                //txt.setCaretPosition(1);
                //jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                //jsp.getVerticalScrollBar().set;
                DefaultCaret caret = (DefaultCaret)txt.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                txt.setText("<font color=\"ff0000\"> asf\ndf<br><br><br><br><br><br><br><br><br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>sdfgdgsd<br>");
                pnlConnection.add(jsp);
            }

            {
                Label lbl = new Label("Message", Label.LEFT);
                lbl.setLocation(x, y);
                y += AWT_H_TEXT;
                lbl.setSize(AWT_W_LIST, AWT_H_TEXT);
                pnlConnection.add(lbl);

                TextArea txt=new TextArea("",4,40,TextArea.SCROLLBARS_VERTICAL_ONLY);
                txt.setFont(new Font("Courier New",Font.PLAIN,15));
                txt.setLocation(x,y);
                y+= AWT_H_MESSAGE+AWT_H_TEXT;
                txt.setSize(AWT_W_EDIT,AWT_H_MESSAGE);
                pnlConnection.add(txt);

                {
                    int xBtn=x,yBtn=y;
                    Button btn;
                    btn = new Button("Sent");
                    btn.setLocation(xBtn, yBtn);
                    xBtn += AWT_BORDER + AWT_W_BUTTON;
                    btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                    pnlConnection.add(btn);


                    btn = new Button("Close");
                    btn.setLocation(AWT_BORDER+AWT_W_EDIT-AWT_W_BUTTON, yBtn);
                    xBtn += AWT_BORDER + AWT_W_BUTTON;
                    btn.setSize(AWT_W_BUTTON, AWT_H_BUTTON);
                    pnlConnection.add(btn);

                    y+=AWT_H_BUTTON+AWT_BORDER;
                }
            }

            this.add(pnlConnection);
            pnlConnection.setSize(AWT_W_EDIT+2*AWT_BORDER,pnlContacts.getHeight()+pnlServer.getHeight()+AWT_BORDER);
            pnlConnection.setVisible(true);
        }
        {
            int hConnection=pnlConnection.getHeight(),hContacts=pnlContacts.getHeight();
            int hTotal=hConnection>hContacts?hConnection:hContacts;
            this.setSize(AWT_BORDER * 3 + pnlConnection.getWidth() + pnlContacts.getWidth(), AWT_TOP_BORDER + AWT_BORDER * 2 + hTotal);

        }
    }
    public static MainFrame get(){if(mainFrame==null) mainFrame=new MainFrame(); return mainFrame;}
}


public class GUI {
}
