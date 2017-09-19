package appletzz;
import java.io.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.event.*;
import java.awt.*;

class TextEditor {
    JEditorPane ja;
    JFrame jr;
    JButton jb,bold,italic,underline;
    JButton jm;
    JMenuBar mbar;
    JMenu file,edit;
    JMenuItem save,open,noo,selectall;
    JWindow blank;
    JFileChooser s;
    JScrollBar right,bottom;
    JScrollPane back;
    Font f;
    GraphicsEnvironment ge;
    int flag = 0;
    TextEditor() {
        
        ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        jr = new JFrame("TextEditor");
        jr.setLayout(new BorderLayout());
        try{
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(ClassNotFoundException e){}
        catch(InstantiationException e){}
        catch(IllegalAccessException e){}
        catch(UnsupportedLookAndFeelException e){}
        ja = new JEditorPane();
        ja.setLayout(new BorderLayout());
        ja.setBounds(10, 10, 500, 400);
        right = new JScrollBar(JScrollBar.VERTICAL);
        bottom = new JScrollBar(JScrollBar.HORIZONTAL);
        
        bold = new JButton("B");
        italic = new JButton("I");
        underline = new JButton("U");
        
        mbar = new JMenuBar();
        file = new JMenu("File");
        edit = new JMenu("Edit");
        noo = new JMenuItem("New");
        save = new JMenuItem("Save");
        open = new JMenuItem("Open");
        selectall = new JMenuItem("Select All");
        
        
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
                    try {
                        ja.read(new FileReader(s.getSelectedFile()),  null);
                    } 
                    catch(FileNotFoundException e) {} 
                    catch(IOException e) {}
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
                if (ja.getSelectedText() == null) {
                    f = ja.getFont();
                    ja.setFont(new Font(f.getFontName().toString(),Font.BOLD,f.getSize()));
                }
                else {
                    String k = ja.getSelectedText();
                    f=ja.getFont();
                    
                }
            }           
        });
        
        
        file.add(noo);
        file.add(save);
        file.add(open);
        edit.add(selectall);
        mbar.add(file);
        mbar.add(edit);
        mbar.add(bold);
        jr.setJMenuBar(mbar);
        ja.setBackground(Color.white);
        
        
        jr.add(ja,BorderLayout.CENTER);
       
        jr.add(right,BorderLayout.EAST);
        jr.add(bottom,BorderLayout.SOUTH);
        jr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jr.setVisible(true);
        jr.setSize(500,500);
    }
    
    private class SetSelectedTo implements CaretListener {
        int dot,mark;
        String k;
        public void caretUpdate(CaretEvent ce) {
            dot = ce.getDot();
            mark = ce.getMark();
            String k = ja.getSelectedText();
            settobold();
        }
        public void settobold() {
        }
}
    
    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new TextEditor();
        }
    });
    }
}
