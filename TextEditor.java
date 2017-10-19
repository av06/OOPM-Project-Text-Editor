//package textedit;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.StyledEditorKit.*;
import javax.swing.text.StyledEditorKit.StyledTextAction;
import java.awt.event.*;
import java.awt.*;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.*;

class TextEditor {
    JTextPane ja;
    JFrame jr;
    JButton jb,bold,italic,underline;
    JButton jm;
    JMenuBar mbar;
    JMenu file,edit,fonts;
    JMenuItem save,open,noo,selectall,copy,paste,clear;
    JComboBox fontlist;
    JWindow blank;
    JFileChooser s;
    JScrollPane back,fontpane;
    StyledDocument style;
    Font f;
    GraphicsEnvironment ge;
    int b=0,it=0,u=0;
    TextEditor() {
        initializeGUI();
        ja.setFont(new Font("Arial",Font.PLAIN,14));
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                String k = ja.getText();
                s = new JFileChooser ();
                int retrival = s.showSaveDialog(jr);
                if (retrival==JFileChooser.APPROVE_OPTION) {
                    try {
                        FileWriter fw = new FileWriter(s.getSelectedFile());
                        fw.write(k);
                        fw.close();
                    } catch(IOException e) {
                        e.printStackTrace();
                }
            }
    }});

        noo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ja.setText("");
            }
        });

        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ja.setText("");
                s = new JFileChooser();
                int yn = s.showOpenDialog(jr);
                if (yn == JFileChooser.APPROVE_OPTION) {
                    String selected = s.getSelectedFile().toString();
                    if (selected.substring(selected.lastIndexOf(".")+1).equals("rtf")) {
                        try {
                            ja.setText("");
                            EditorKit kit = ja.getEditorKitForContentType("text/rtf");
                            kit.read(new FileReader(s.getSelectedFile()), ja.getDocument(), 1);

                        }
                        catch(FileNotFoundException e) {}
                        catch(IOException e) {}
                        catch(BadLocationException e) {}
                    }
                    else if (selected.substring(selected.lastIndexOf(".")+1).equals("txt")) {
                        try {
                            FileReader fr = new FileReader(s.getSelectedFile());
                            BufferedReader br = new BufferedReader(fr);
                            String line;
                            String k = "";
                            while ((line=br.readLine())!=null) {
                                k=k.concat(line + "\n");
                            }
                            ja.setText(k);
                            br.close();
                            fr.close();
                        }
                        catch(FileNotFoundException e) {}
                        catch(IOException e) {}
                    }
                    else ja.setText(selected);
                }
            }
        });

        selectall.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ja.selectAll();
            }
        });

        bold.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                    b++;
                    if (b%2!=0) {
                        bold.setForeground(Color.WHITE);
                        bold.setBackground(new Color(59,89,152));
                    }
                    else {
                        bold.setForeground(Color.BLACK);
                        bold.setBackground(Color.WHITE);
                    }
                ja.requestFocusInWindow();

            }
        });

        italic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                it++;
                if (it%2!=0) {
                    italic.setForeground(Color.WHITE);
                    italic.setBackground(new Color(59,89,152));
                }
                else {
                    italic.setForeground(Color.BLACK);
                    italic.setBackground(Color.WHITE);
                }
                ja.requestFocusInWindow();
            }
        });

        underline.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                u++;
                if (u%2!=0) {
                    underline.setForeground(Color.WHITE);
                    underline.setBackground(new Color(59,89,152));
                }
                else {
                    underline.setForeground(Color.BLACK);
                    underline.setBackground(Color.WHITE);
                }
                ja.requestFocusInWindow();
            }
        });

        clear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ja.setText("");
            }
        });

        clear.addKeyListener(new KeyListener() {
            public void keyPressed(KeyEvent ke) {
                 if (ke.getKeyCode()==KeyEvent.VK_F6) {
                    ja.setText("");
                    System.out.println("asdasd");
                }
                 System.out.println("asdasd");
            }
            public void keyReleased(KeyEvent ke) {}
            public void keyTyped(KeyEvent ke) {}
        });


        for (int i=0;i<ge.getAvailableFontFamilyNames().length;i++) {
            fontlist.addItem(ge.getAvailableFontFamilyNames()[i]);
        }

        fontlist.addActionListener(new ActionListener () {
            public void  actionPerformed(ActionEvent ae) {

                    String selected = (String)fontlist.getSelectedItem();
                    ja.setFont(Font.getFont(selected));

                ja.setText(fontlist.getSelectedItem().toString());
            }
        });

        addGUI();
        jr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jr.setVisible(true);
        jr.setSize(500,500);
    }

    public void initializeGUI() {
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        jr = new JFrame("TextEditor");
        jr.setLayout(new BorderLayout());
        try{
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            SwingUtilities.updateComponentTreeUI(jr);
        }catch(Exception e){

        }
        ja = new JTextPane();
        ja.setLayout(new BorderLayout());
        ja.setBounds(10, 10, 500, 400);


        back = new JScrollPane(ja);
        bold = new JButton(new BoldAction());
        bold.setText("B");
        italic = new JButton(new ItalicAction());
        italic.setText("I");
        underline = new JButton(new UnderlineAction());
        underline.setText("U");
        mbar = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        fonts = new JMenu("Fonts");
        noo = new JMenuItem("New");
        save = new JMenuItem("Save");
        open = new JMenuItem("Open");
        copy = new JMenuItem("Copy");
        paste = new JMenuItem("Paste");
        selectall = new JMenuItem("Select All");
        clear = new JMenuItem("Clear    F6");
        fontlist = new JComboBox();

        fontpane = new JScrollPane(fontlist);
    }

    public void addGUI() {

        file.add(noo);
        file.add(save);
        file.add(open);
        edit.add(selectall);
        edit.add(clear);
        edit.add(copy);
        edit.add(paste);
        fonts.add(fontpane);

        mbar.add(file);
        mbar.add(edit);
        mbar.add(bold);
        mbar.add(italic);
        mbar.add(underline);
        mbar.add(fonts);

        jr.setJMenuBar(mbar);
        ja.setBackground(Color.white);
        jr.setContentPane(back);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TextEditor();
        }
    });
    }
}
