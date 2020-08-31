import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

public class FileChooserExample {

    public static void main(String[] args) {
        new FileChooserExample();
    }

    public FileChooserExample() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        private JButton open;
        private JTextArea textArea;
        private JFileChooser chooser;

        public TestPane() {
            setLayout(new BorderLayout());
            open = new JButton("Open");
            textArea = new JTextArea(20, 40);
            add(new JScrollPane(textArea));
            add(open, BorderLayout.SOUTH);

            open.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (chooser == null) {
                        chooser = new JFileChooser();
                        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        chooser.setAcceptAllFileFilterUsed(false);
                        chooser.addChoosableFileFilter(new FileFilter() {
                            @Override
                            public boolean accept(File f) {
                                return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                            }

                            @Override
                            public String getDescription() {
                                return "Text Files (*.txt)";
                            }
                        });
                    }

                    switch (chooser.showOpenDialog(TestPane.this)) {
                        case JFileChooser.APPROVE_OPTION:
                            try (BufferedReader br = new BufferedReader(new FileReader(chooser.getSelectedFile()))) {
                                textArea.setText(null);
                                String text = null;
                                while ((text = br.readLine()) != null) {
                                    textArea.append(text);
                                    textArea.append("\n");
                                }
                                textArea.setCaretPosition(0);
                            } catch (IOException exp) {
                                exp.printStackTrace();
                                JOptionPane.showMessageDialog(TestPane.this, "Failed to read file", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            break;
                    }
                }
            });
        }

    }

}