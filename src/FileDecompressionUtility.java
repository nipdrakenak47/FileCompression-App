import java.io.*;
import java.util.HashMap;

public class FileDecompressionUtility{
    HashMap<Character,String> map;
    String inputFilePath,decodedFilePath;
    Node root;
    long skip_byte;

    FileDecompressionUtility(String inputFilePath,String parentDirPath){
        map = null;
        root = null;
        skip_byte = 0;
        this.inputFilePath = inputFilePath;

        String baseName = "decoded_result";
        String extension = ".txt";
        File directory = new File(parentDirPath);

        File file = new File(directory, baseName + extension);
        int counter = 1;

        while (file.exists()) {
            file = new File(directory, baseName + "_" + counter + extension);
            counter++;
        }

        decodedFilePath = file.getAbsolutePath();
    }

    public void readEncodedFile() throws FileNotFoundException, IOException {
        this.map = new HashMap<>();
        FileInputStream f1 = new FileInputStream(inputFilePath);

        char ch ;
        byte[] b = new byte[128];
        int n = 0,temp;
        while(true){
            temp = f1.read();
            skip_byte++;

            if(temp==10){
                break;
            }
            n = n*10 + (temp-48);
        }

        for(int i=0;i<n;i++){
            ch = (char)f1.read();

            f1.read(b);
            StringBuffer sb = new StringBuffer("");

            int k=0;
            while(b[k]==48){
                k++;
            }
            k++;

            while(k<128){
                if(b[k]==49){
                    sb.append('1');
                }
                else{
                    sb.append('0');
                }
                k++;
            }
            map.put(ch,sb.toString());
            skip_byte+=129;
        }

        skip_byte++;
    }

    public void createHuffmanTreeFromCode(){

        //createHuffmanTreeFromCode();
        this.root = new Node(0);

        for(Character ch : map.keySet()){
            addHuffmanNode(0,ch,map.get(ch),root);
        }
    }

    public void addHuffmanNode(int i,char charData,String s,Node root){
        if(i==s.length()){
            root.ch = charData;
            return ;
        }

        if(s.charAt(i)=='0'){
            if(root.left==null){
                root.left = new Node(0);
            }
            addHuffmanNode(i+1,charData,s,root.left);
        }
        else{
            if(root.right==null){
                root.right = new Node(0);
            }
            addHuffmanNode(i+1,charData,s,root.right);
        }
    }

    public void extractFileData() throws Exception{

        FileInputStream f1 = new FileInputStream(inputFilePath);
        FileWriter f2 = new FileWriter(decodedFilePath,true);
        f1.skip(this.skip_byte);

        int r = f1.read()-48;
        int[] offset_curNum = new int[2];

        offset_curNum[0] = 6;
        offset_curNum[1] = f1.read();

        while(!(f1.available()==0 && offset_curNum[0]==6-r)){
            traverseDecodedTree(f1,f2,offset_curNum,this.root);
        }

        f1.close();
        f2.close();
    }

    public void traverseDecodedTree(FileInputStream f1,FileWriter f2,int[] offset_curNum,Node root) throws Exception{
        if(root.left==null && root.right==null){
            f2.write(root.ch);
            return ;
        }

        if((offset_curNum[1] & (1<<offset_curNum[0]))==0){
            if(offset_curNum[0]==0){
                offset_curNum[0] = 6;
                offset_curNum[1] = f1.read();
            }
            else{
                offset_curNum[0]--;
            }
            traverseDecodedTree(f1,f2,offset_curNum,root.left);
        }
        else{
            if(offset_curNum[0]==0){
                offset_curNum[0] = 6;
                offset_curNum[1] = f1.read();
            }
            else{
                offset_curNum[0]--;
            }
            traverseDecodedTree(f1,f2,offset_curNum,root.right);
        }
    }
}