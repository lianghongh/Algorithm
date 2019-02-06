class Morris
{

    static class TreeNode
    {
        public int eval;
        public TreeNode left,right;

        public TreeNode(int eval)
        {
            this.eval=eval;
        }
    }

    public static void Morris_inorder(TreeNode root)          //morris中序遍历
    {
        TreeNode cur=root,prev=null;
        while (cur!=null)
        {
            if(cur.left==null)
            {
                System.out.print(cur.eval+" ");
                cur=cur.right;
            }
            else
            {
                prev=cur.left;
                while (prev.right!=null&&prev.right!=cur)
                    prev=prev.right;
                if(prev.right==null)
                {
                    prev.right=cur;
                    cur=cur.left;
                }
                else
                {
                    prev.right=null;
                    System.out.print(cur.eval+" ");
                    cur=cur.right;
                }
            }
        }
    }

    public static void Morris_preorder(TreeNode root)          //morris先序遍历
    {
        TreeNode cur=root,prev=null;
        while (cur!=null)
        {
            if(cur.left==null)
            {
                System.out.print(cur.eval+" ");
                cur=cur.right;
            }
            else
            {
                prev=cur.left;
                while (prev.right!=null&&prev.right!=cur)
                    prev=prev.right;

                if(prev.right==null)
                {
                    System.out.print(cur.eval+" ");
                    prev.right=cur;
                    cur=cur.left;
                }
                else
                {
                    prev.right=null;
                    cur=cur.right;
                }
            }
        }
    }

    public static void Morris_postorder(TreeNode root)      //morris后序遍历
    {
        TreeNode cur=new TreeNode(-1);
        cur.left=root;
        TreeNode prev=null;
        while(cur!=null)
        {
            if(cur.left==null)
                cur=cur.right;
            else
            {
                prev=cur.left;
                while(prev.right!=null&&prev.right!=cur)
                    prev=prev.right;
                if(prev.right==null)
                {
                    prev.right=cur;
                    cur=cur.left;
                }
                else
                {
                    prev.right=null;
                    Node pre=null;
                    Node t=cur.left;
                    while(t!=null)
                    {
                        Node q=t.right;
                        t.right=pre;
                        pre=t;
                        t=q;
                    }
                    t=pre;
                    pre=null;
                    while(t!=null)
                    {
                        System.out.print(t.eval+" ");
                        Node q=t.right;
                        t.right=pre;
                        pre=t;
                        t=q;
                    }
                    cur=cur.right;
                }
            }
        }
    }
    
}