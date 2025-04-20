public class Node{
    Node left,right;
    char ch;
    int freq;
    String code;

    Node(int freq){
        this.freq = freq;
        left = null;
        right = null;
    }
    Node(char ch,int freq){
        this.ch = ch;
        this.freq = freq;
    }
}