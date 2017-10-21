package textedit;
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
import javax.swing.text.rtf.RTFEditorKit;

class TextEdit {
    JTextPane ja;
    JFrame jr;
    JButton jb,bold,italic,underline;
    JButton jm;
    JMenuBar mbar;
    JMenu file,edit,fonts;
    JMenuItem save,open,noo,selectall,copy,paste,clear;
    JComboBox fontlist,fontsizes;
    JWindow blank;
    JDialog saveas;
    JFileChooser s;
    JScrollPane back,fontpane;
    StyledDocument style;
    Font f;
    GraphicsEnvironment ge;
    int b=0,it=0,u=0;
    TextEdit() {
        initializeGUI();
        ja.setFont(new Font("Arial",Font.PLAIN,14));
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                savedialog();
        }});
        
        noo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                JDialog jd = new JDialog();
                JButton yes = new JButton("Yes");
                yes.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        jd.setVisible(false);
                        ja.setText("");
                    }
                });
                JButton sv = new JButton("Save");
                sv.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        jd.setVisible(false);
                        savedialog();
                        ja.setText("");
                    }
                });
                JButton cancel = new JButton("Cancel");
                cancel.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent ae) {
                        jd.setVisible(false);
                    }
                });
                jd.setSize(300,100);
                jd.setLayout(new FlowLayout());
                jd.setBackground(Color.WHITE);
                jd.add(new JLabel("Are you sure? Unsaved progress will be lost"));
                jd.add(yes);
                jd.add(sv);
                jd.add(cancel);
                jd.setVisible(true);
                jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
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
        
        for (int i=0;i<ge.getAvailableFontFamilyNames().length;i++) {
            fontlist.addItem(ge.getAvailableFontFamilyNames()[i]);
        }
        fontlist.setFocusable(false);
        fontlist.addActionListener(new ActionListener () {
            public void  actionPerformed(ActionEvent ae) {
                    
                    String selected = (String)fontlist.getSelectedItem();
                    try {
                        if (ja.getSelectedText()!=null) {
                            //UNDER CONSTRUCTION
                            String k = ja.getText().substring(0,ja.getSelectionStart());
                            String toinsert = ja.getText().substring(ja.getSelectionStart()+1,ja.getSelectionEnd());
                            
                        }
                    } catch(NullPointerException e) {}
            }
        });
        
        
        
        addGUI();
        jr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jr.setVisible(true);
        jr.setSize(900, 500);
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
        fontsizes = new JComboBox();
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
        mbar.add(fontlist);
        mbar.add(fontsizes);
        jr.setJMenuBar(mbar);
        ja.setBackground(Color.white);
        jr.setContentPane(back);
    }
    
    public void savedialog() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Save as rtf?", "Save as...", JOptionPane.YES_NO_OPTION);
        if (dialogResult==JOptionPane.YES_OPTION) saveasrtf();
        else if (dialogResult==JOptionPane.NO_OPTION) saveastxt();
    }
    
    public void saveasrtf() {
                s = new JFileChooser();
                int retrival = s.showSaveDialog(jr);
                if (retrival==JFileChooser.APPROVE_OPTION) {
                   try {
                        OutputStream out = new FileOutputStream(new File(s.getSelectedFile().toString()));
                        RTFEditorKit rtf = new RTFEditorKit();
                        rtf.write(out, ja.getDocument(), 0, ja.getDocument().getLength());
                        out.close();
                   } catch(Exception e) {}
                }
    }
    
    public void saveastxt() {
                s = new JFileChooser();
                int yn = s.showSaveDialog(jr);
                if (yn==JFileChooser.APPROVE_OPTION) {
                String k = ja.getText();
                    try {
                            FileWriter fw = new FileWriter(s.getSelectedFile());
                            fw.write(k);
                            fw.close();
                        } catch(IOException e) {
                            e.printStackTrace();
                    }
                }
    }
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TextEdit();
        }
    });
    }
}
