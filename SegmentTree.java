
//                                              [1,12]
//                                                 |
//                            ——————————————————————————————————————————
//                           |                                          |
//                         [1,6]                                      [7,12]
//                           |                                          |
//                 ————————————————————————                ———————————————————————————
//                |                        |               |                         |
//              [1,3]                    [4,6]           [7,9]                    [10,12]
//                |                        |               |                         |
//         —————————————             —————————————    ————————————             ——————————————
//        |             |           |            |    |          |            |             |
//      [1,2]          [3]        [4,5]         [6] [7,8]       [9]        [10,11]         [12]
//        |                         |                 |                       |
//    ————————                 ——————————        ——————————              ——————————               
//   |        |               |          |      |          |            |          |
//  [1]      [2]             [4]        [5]    [7]        [8]          [10]       [11]


public class SegmentTree {
    private int[] tag;            //Lazy Tag下推标记，通过pushDown把标记下推延迟更新
    private int[] sum;            //定义线段树的区间和，线段树的本体
    private int[] nums;           //存储需要求区间和的数组

    public SegmentTree(int[] nums)
    {
        int n=nums.length;
        tag=new int[n<<2];
        sum=new int[n<<2];
        this.nums=nums;
        buildTree(nums,0,nums.length-1,0);
    }

    private void buildTree(int[] nums,int l,int r,int i)            //建立线段树，i表示当前的线段树根节点
    {
        if(l==r)
        {
            sum[i]=nums[l];
            return;
        }
        int m=(l+r)>>1;
        buildTree(nums,l,m,i<<1|1);
        buildTree(nums,m+1,r,(i+1)<<1);
        sum[i]=sum[i<<1|1]+sum[(i+1)<<1];           //更新父节点的sum信息

    }

    private void update(int L,int C,int l,int r,int i)        //nums[L]+=C，更新线段树
    {
        if(l==r)
        {
            sum[i]+=C;
            return;
        }
        int m=(l+r)>>1;
        pushDown(i,m-l+1,r-m);                     //下推一层lazy tag，更新sum[i]的子节点
        if(L<=m)                                   //判断L是在区间[l,m]还是[m+1,r]
            update(L,C,l,m,i<<1|1);
        else
            update(L,C,m+1,r,(i+1)<<1);
        sum[i]=sum[i<<1|1]+sum[(i+1)<<1];
    }

    private void pushDown(int k,int ln,int rn)    //ln表示sum[k]左子树的节点数，rn表示右子树的节点数
    {
        if(tag[k]!=0)
        {
            sum[k<<1|1]+=tag[k]*ln;              //更新sum[k]孩子节点
            sum[(k+1)<<1]+=tag[k]*rn;
            tag[k<<1|1]+=tag[k];                 //将lazy tag下推一层
            tag[(k + 1) << 1] += tag[k];
            tag[k]=0;                             //下推完成，清除lazy tag标记
        }
    }

    private void update(int L,int R,int C,int l,int r,int i)       //更新nums[L,R]+=C
    {
        if(L<=l&&r<=R)                           //当[L,R]区间覆盖[l,r]时无需再向下搜索，直接更新sum[i]，打上lazy tag(因为其子节点的sum还没有更新)
        {
            tag[i]+=C;
            sum[i]+=(r-l+1)*C;
            return;
        }

        int m=(l+r)>>1;
        pushDown(i,m-l+1,r-m);
        if(L<=m)                                //更新[L,R]与[l,m]的交集
            update(L,R,C,l,m,i<<1|1);
        if(R>m)
            update(L,R,C,m+1,r,(i+1)<<1);       //更新[L,R]与[m+1,r]的交集
        sum[i]=sum[i<<1|1]+sum[(i+1)<<1];
    }

    private int query(int L,int R,int l,int r,int i)     //查询区间[L,R]的和
    {
        if(L<=l&&r<=R)
            return sum[i];
        int m=(l+r)>>1;
        pushDown(i,m-l+1,r-m);                //查询的时候可能有的lazy tag没有下推，需要在搜索的同时进行下推
        int ans=0;
        if(L<=m)                              //查询[L,R]与[l,m]交集的和ans1
            ans+=query(L,R,l,m,i<<1|1);
        if(m<R)
            ans+=query(L,R,m+1,r,(i+1)<<1);   //查询[L,R]与[m+1,r]交集的和ans2
        return ans;                           //ans=ans1+ans2
    }

    public int query(int L,int R)            //查询[L,R]区间的和
    {
        return query(L,R,0,nums.length-1,0);
    }

    public void update(int L,int R,int C)    //更新nums[L,R]+=C
    {
        update(L,R,C,0,nums.length-1,0);
    }

    public void update(int L,int C)          //更新nums[L]+=C
    {
        update(L,C,0,nums.length-1,0);
    }


    public static void main(String[] args)
    {
        int[] a = {1, 2, 3, 4, 5, 6, 7, 8};
        SegmentTree tree = new SegmentTree(a);
        System.out.println(tree.query(0,3));
        tree.update(0,3,1);
        System.out.println(tree.query(0,3));
        tree.update(2,10);
        System.out.println(tree.query(0,3));
    }

}
