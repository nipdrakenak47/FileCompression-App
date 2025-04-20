import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;


public class FileCompressionUtility{
    Node[] charNodeArray;
    int total_char;
    String inputFilePath,encodedFilePath;

    FileCompressionUtility(String inputFilePath,String parentDirPath){
        this.inputFilePath = inputFilePath;
        this.charNodeArray = new Node[128];
        total_char = 0;

        String baseName = "output_huff";
        String extension = ".txt";
        File directory = new File(parentDirPath);

        File file = new File(directory, baseName + extension);
        int counter = 1;

        while (file.exists()) {
            file = new File(directory, baseName + "_" + counter + extension);
            counter++;
        }

        encodedFilePath = file.getAbsolutePath();
    }

    public void readFile() throws Exception {
        FileInputStream f1 = new FileInputStream(inputFilePath);

        int n = f1.available();
        for(int i=0;i<n;i++){
            int x = f1.read();
            if(charNodeArray[x]==null){
                charNodeArray[x] = new Node((char)(x),0);
            }
            charNodeArray[x].freq++;
        }
        f1.close();
    }

    public void createHuffmanTree(){
        PriorityQueue<Node> pq = new PriorityQueue<>(new MyComparator());

        for(int i=0;i<128;i++){
            if(charNodeArray[i]==null) {
                continue;
            }
            else{
                total_char++;
            }
            pq.add(charNodeArray[i]);
        }

        Node node1,node2;
        while(pq.size()!=1){
            node1 = pq.peek();
            pq.poll();
            node2 = pq.peek();
            pq.poll();

            Node temp = new Node(node1.freq+node2.freq);
            temp.left = node1;
            temp.right = node2;

            pq.add(temp);
        }

        Node rootNode = pq.peek();
        generateHuffmanCode(rootNode);
    }

    public void traverseTree(Node root,int[] arr,int i){
        if(root.left==null && root.right==null){
            StringBuffer code = new StringBuffer();
            for(int j=0;j<i;j++){
                code.append((char)('0'+arr[j]));
            }
            root.code = code.toString();
            return;
        }

        arr[i]=0;
        traverseTree(root.left,arr,i+1);

        arr[i]=1;
        traverseTree(root.right,arr,i+1);
    }

    public void generateHuffmanCode(Node root){
        int[] arr = new int[128];
        traverseTree(root,arr,0);
    }


    public void encodeTreeData() throws Exception {
        Node[] arr = charNodeArray;
        FileWriter f1 = new FileWriter(encodedFilePath,true);

        int temp = this.total_char;
        StringBuffer sb = new StringBuffer();
        while(temp!=0){
            sb.append((char)(temp%10 + 48));
            temp = temp/10;
        }
        for(int i=sb.length()-1;i>=0;i--){
            f1.write(sb.charAt(i));
        }
        f1.write(10);


        for(int i=0;i<128;i++){
            if(arr[i]==null){
                continue;
            }
            int n = arr[i].code.length();
            StringBuffer encodedCode = new StringBuffer();
            encodedCode.append((char)(i));

            for(int j=1;j<=(128-n-1);j++){
                encodedCode.append('0');
            }
            if(n!=128){
                encodedCode.append('1');
            }
            encodedCode.append(arr[i].code);

            f1.write(encodedCode.toString());
        }
        f1.write(10);
        f1.close();
    }

    public void encodeInputData() throws Exception{
        FileInputStream f1 = new FileInputStream(inputFilePath);
        FileWriter f2 = new FileWriter(encodedFilePath,true);

        int n = f1.available(),queue_size=0;
        Queue<Character> q = new LinkedList<>();
        int cur = 0;
        long sum=0;

        for(int i=0;i<128;i++){
            if(charNodeArray[i]!=null){
                sum += charNodeArray[i].code.length()*charNodeArray[i].freq;
            }
        }
        int r = (int)(sum%7);
        f2.write(r + 48);


        try{
            for(int i=0;i<n;i++){
                int x = f1.read();
                for(int j=0;j<charNodeArray[x].code.length();j++){
                    q.add(charNodeArray[x].code.charAt(j));
                    queue_size++;
                }

                while(queue_size>=7){
                    for(int j=1;j<=7;j++) {
                        cur = (cur << 1) + ((int) q.poll() - 48);
                    }
                    queue_size-=7;
                    f2.write(cur);
                    cur = 0;
                }
            }

            if(!q.isEmpty()){
                while(!q.isEmpty()){
                    cur = (cur << 1) + ((int) q.poll() - 48);
                }
                cur = cur<<(7-r);
                f2.write(cur);
            }
        }
        catch (IOException e){
            //
        }
        finally {
            try {
                f1.close();
                f2.close();
            } catch (IOException e) {
                //
            }
        }
    }
}

class MyComparator implements Comparator<Node> {
    public int compare(Node n1,Node n2){
        return n1.freq-n2.freq;
    }
}
