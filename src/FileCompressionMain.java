import java.awt.*;
import java.io.*;
import javax.swing.*;


public class FileCompressionMain {

    private JFrame frame;
    private JLabel fileLabel;
    private File selectedFile;
    Exception exception;

    public static void fileCompression(String inputFilePath,String parentDirPath) throws Exception{
        FileCompressionUtility h1 = new FileCompressionUtility(inputFilePath,parentDirPath);

        h1.readFile();
        h1.createHuffmanTree();
        h1.encodeTreeData();
        h1.encodeInputData();
    }

    public static void fileDecompression(String inputFilePath,String parentDirPath) throws Exception{
        FileDecompressionUtility fd1 = new FileDecompressionUtility(inputFilePath,parentDirPath);

        fd1.readEncodedFile();
        fd1.createHuffmanTreeFromCode();
        fd1.extractFileData();
    }


    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> new FileCompressionMain().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("File Processor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 200);
        frame.setLayout(new FlowLayout());

        JButton chooseButton = new JButton("Choose File");
        JButton encodeButton = new JButton("Encode");
        JButton decodeButton = new JButton("Decode");

        fileLabel = new JLabel("No file selected");
        fileLabel.setPreferredSize(new Dimension(450, 30));

        chooseButton.addActionListener(e -> chooseFile());
        encodeButton.addActionListener(e -> encodeFile());
        decodeButton.addActionListener(e -> decodeFile());

        frame.add(chooseButton);
        frame.add(fileLabel);
        frame.add(encodeButton);
        frame.add(decodeButton);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileLabel.setText("Selected File: " + selectedFile.getAbsolutePath());
        }
    }

    private void encodeFile() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(frame, "Please select a file first!", "No File", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String inputFilePath =  selectedFile.getAbsolutePath();
        String parentDirPath = selectedFile.getParent();

        if(!inputFilePath.substring(inputFilePath.length()-4,inputFilePath.length()).equals(".txt")){
            JOptionPane.showMessageDialog(frame, "Please choose valid Text file!", "", JOptionPane.INFORMATION_MESSAGE);
            return ;
        }

        final JDialog loadingDialog = new JDialog(frame, "Processing", true);
        JLabel loadingLabel = new JLabel("Compressing the  file, please wait...");
        loadingLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loadingDialog.add(loadingLabel);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(frame);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try{
                    fileCompression(inputFilePath,parentDirPath);
                }
                catch (Exception e){
                    exception = e;
                }
                return null;
            }
            @Override
            protected void done() {
                loadingDialog.dispose();

                if (exception != null) {
                    JOptionPane.showMessageDialog(frame,
                            "An error occurred during compression:\n" + exception.getMessage(),
                            "Compression Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "File compressed successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        };

        worker.execute();
        loadingDialog.setVisible(true);
    }

    private void decodeFile() {
        if (selectedFile == null) {
            JOptionPane.showMessageDialog(frame, "Please select a file first!", "No File", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String inputFilePath =  selectedFile.getAbsolutePath();
        String parentDirPath = selectedFile.getParent();

        if(!inputFilePath.substring(inputFilePath.length()-4,inputFilePath.length()).equals(".txt")){
            JOptionPane.showMessageDialog(frame, "Please choose valid Text file!", "", JOptionPane.INFORMATION_MESSAGE);
            return ;
        }

        final JDialog loadingDialog = new JDialog(frame, "Processing", true);
        JLabel loadingLabel = new JLabel("Retrieving the file data, please wait...");
        loadingLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loadingDialog.add(loadingLabel);
        loadingDialog.pack();
        loadingDialog.setLocationRelativeTo(frame);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                try{
                    fileDecompression(inputFilePath,parentDirPath);
                }
                catch (Exception e){
                    exception = e;
                }
                return null;
            }
            @Override
            protected void done() {
                loadingDialog.dispose();

                if (exception != null) {
                    JOptionPane.showMessageDialog(frame,
                            "An error occurred during decompression:\n" + exception.getMessage(),
                            "Decompression Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame,
                            "File decompressed successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };

        worker.execute();
        loadingDialog.setVisible(true);
    }

}