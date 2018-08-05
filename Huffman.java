package Algorithm;

import java.util.*;

public class Huffman {
    private TreeNode root;

    static class TreeNode implements Comparable<TreeNode>
    {
        public int val;
        public Character c;
        public TreeNode left,right;

        public TreeNode(int val){
            this.val=val;
            c=null;
        }

        public TreeNode(int val,Character c){
            this.val=val;
            this.c=c;
        }

        @Override
        public int compareTo(TreeNode o) {
            return val-o.val;
        }
    }

    public Huffman(String s)
    {
        root=buildHuffmanTree(s);
    }

    private void getPath(TreeNode root, List<Character> l, Map<Character,String> map)
    {
        if(root==null)
            return;
        if(root.left==null&&root.right==null)
        {
            StringBuilder builder = new StringBuilder();
            for(int i=0;i<l.size();i++)
                builder.append(l.get(i));
            map.put(root.c,builder.toString());
            return;
        }

        if(root.left!=null)
        {
            l.add('0');
            getPath(root.left,l,map);
            l.remove(l.size() - 1);
        }
        if(root.right!=null)
        {
            l.add('1');
            getPath(root.right,l,map);
            l.remove(l.size() - 1);
        }
    }

    private TreeNode buildHuffmanTree(String text)
    {
        PriorityQueue<TreeNode> q = new PriorityQueue<>();
        Map<Character, Integer> map = new HashMap<>();
        for(int i=0;i<text.length();i++)
        {
            if(!map.containsKey(text.charAt(i)))
                map.put(text.charAt(i),1);
            else
                map.put(text.charAt(i),map.get(text.charAt(i))+1);
        }
        Iterator<Character> it=map.keySet().iterator();
        while (it.hasNext())
        {
            Character cc=it.next();
            q.offer(new TreeNode(map.get(cc),cc));
        }
        while (q.size()>1)
        {
            TreeNode node1=q.poll();
            TreeNode node2=q.poll();
            TreeNode p=new TreeNode(node1.val+node2.val,'#');
            p.left=node1;
            p.right=node2;
            q.offer(p);
        }
        return q.poll();
    }

    public  String encode(String s)
    {
        List<Character> l = new LinkedList<>();
        Map<Character, String> mm = new HashMap<>();
        getPath(root,l,mm);
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<s.length();i++)
            builder.append(mm.get(s.charAt(i)));
        return builder.toString();
    }

    public String decode(String s)
    {
        TreeNode p=root;
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<s.length();i++)
        {
            if(s.charAt(i)=='0')
                p=p.left;
            else
                p=p.right;
            if(p.c!='#')
            {
                builder.append(p.c);
                p=root;
            }
        }
        return builder.toString();
    }

    public static void main(String... args)
    {
        String s="aabbbcxfghlttgnbvxgdddwjlmnbv";
        Huffman huffman = new Huffman(s);
        String code=huffman.encode(s);
        String d=huffman.decode(code);
        System.out.println(s+"---->"+code+"    length:"+code.length());
        System.out.println(code+"---->"+d+"     length:"+d.length()*8);
    }
}
