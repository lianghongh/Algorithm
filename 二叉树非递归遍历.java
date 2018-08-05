class Tree
{
    static class TreeNode
    {
        public int e;
        public TreeNode left,right;

        public TreeNode(int e){
            this.e=e;
        }
    }

    public static void pre_iter(TreeNode root)             //非递归前序遍历
    {
        TreeNode p=root;
        Stack<TreeNode> q = new Stack<>();
        while (p!=null||!q.isEmpty())
        {
            while (p!=null)
            {
                System.out.print(p.e+" ");
                q.push(p);
                p=p.left;
            }
            p=q.pop().right;
        }
    }

    public static void in_iter(TreeNode root)        //非递归中序遍历
    {
        Stack<TreeNode> s = new Stack<>();
        TreeNode p=root;
        while (p!=null||!s.isEmpty())
        {
            while (p!=null)
            {
                s.push(p);
                p=p.left;
            }
            p=s.pop();
            System.out.print(p.e+" ");
            p=p.right;
        }
    }


    public static void post_iter(TreeNode root)         //非递归后序遍历
    {
        TreeNode pos=root;
        TreeNode p=root;
        Stack<TreeNode> s = new Stack<>();
        while (p!=null||!s.isEmpty())
        {
            while (p!=null)
            {
                s.push(p);
                p=p.left;
            }

            TreeNode top=s.peek();
            if(top.right==pos||top.right==null)
            {
                System.out.print(top.e+" ");
                pos=top;
                s.pop();
            }
            else
                p=top.right;
        }
    }
}