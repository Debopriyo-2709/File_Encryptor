import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.SwingWorker;

public class image_operation {

    public static void operate(int key,boolean encrypt)
    {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

            if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();

            SwingWorker<Void,Void> worker = new SwingWorker<>() {
                @Override
                protected Void doInBackground() throws Exception {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        byte[] data = fis.readAllBytes();

                        for (int i = 0; i < data.length; i++) {
                            if (encrypt) {
                                data[i] = (byte) (data[i] ^ key); // Encrypt
                            } else {
                                data[i] = (byte) (data[i] ^ key); // Decrypt
                            }
                        }

                        try (FileOutputStream fos = new FileOutputStream(file)) {
                            fos.write(data);
                        }

                        String message = encrypt ? "Encryption" : "Decryption";
                        JOptionPane.showMessageDialog(null, message + " Done");
                    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
            worker.execute();
        }
    }

    public static void main(String[] args) {
        
        System.out.println("this is testing");

        JFrame f=new JFrame();
        f.setTitle("Image Operation(Encryption/Deccryption)");
        f.setSize(400,400);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font=new Font("Roboto",Font.BOLD,25);
        //creating button
        JButton encryptButton = new JButton("Encrypt Image");
        JButton decryptButton = new JButton("Decrypt Image");
        JTextField keyField = new JTextField(10);
        keyField.setFont(font);

        //creating text field

        JTextField textField=new JTextField(10);
        textField.setFont(font);

        
        encryptButton.addActionListener(e -> {
        System.out.println("Encrypt button clicked");
        String text = keyField.getText();
        int key = Integer.parseInt(text);
        operate(key, true);
        });

        decryptButton.addActionListener(e -> {
        System.out.println("Decrypt button clicked");
        String text = keyField.getText();
        int key = Integer.parseInt(text);
        operate(key, false);
        });

        f.setLayout(new FlowLayout());

        f.add(encryptButton);
        f.add(decryptButton);
        f.add(keyField);
        
        f.setVisible(true);

    }
}
