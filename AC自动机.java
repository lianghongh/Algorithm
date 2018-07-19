public class AC {

    class Node
    {
        public Node[] next;
        public int count;
        public Node fail;

        public Node()
        {
            count=0;
            next = new Node[26];
            fail=null;
        }
    }

    public void insert(Node root,String s)
    {
        Node p=root;
        if(p==null)
            return;
        for(int i=0;i<s.length();i++)
        {
            int index=s.charAt(i)-'a';
            if(p.next[index]==null)
                p.next[index]=new Node();
            p = p.next[index];
        }
        p.count++;
    }


    public void build_fail(Node root)
    {
        if(root==null)
            return;
        LinkedList<Node> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty())
        {
            Node p=q.poll();
            for(int i=0;i<26;i++)
            {
                if(p.next[i]!=null)
                {
                    if(p==root)
                        p.next[i].fail=root;
                    else
                    {
                        Node f=p.fail;
                        while (f!=null)
                        {
                            if (f.next[i]!=null)
                            {
                                p.next[i].fail=f.next[i];
                                break;
                            }
                            f=f.fail;
                        }
                        if(f==null)
                            p.next[i].fail=root;
                    }
                    q.offer(p.next[i]);
                }
            }
        }
    }

    public int search(Node root,String text)
    {
        if(root==null)
            return 0;
        int c=0;
        Node p=root;
        for(int i=0;i<text.length();i++)
        {
            int index=text.charAt(i)-'a';
            while (p!=root&&p.next[index]==null)
                p=p.fail;
            p=p.next[index];
            if(p==null)
                p=root;
            Node temp=p;
            while (temp!=root&&temp.count!=-1)
            {
                c+=temp.count;
                temp.count=-1;
                temp=temp.fail;
            }
        }

        return c;
    }

    public static void main(String[] args)
    {
        AC ac=new AC();
        Node root = ac.new Node();
        ac.insert(root,"say");
        ac.insert(root,"she");
        ac.insert(root,"shr");
        ac.insert(root,"he");
        ac.insert(root,"her");

        ac.build_fail(root);
        System.out.println(ac.search(root,"yasherhs"));
    }
}

