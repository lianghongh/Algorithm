package hash_table;

/**
 * Created by lianghong on 2018/1/22
 *
 * Hash表，采用双重散列解决冲突，
 * 实现delete、insert和search功能
 */
public class HashTable {
    private int size;
    private HashNode[] data;
    private int remain;

    private class HashNode
    {
        public static final int NULL=0;              //标记状态，NULL表示未分配，DELETED表示已删除，FULL表示已分配
        public static final int DELETED=-1;
        public static final int FULL=1;
        public int k;
        public int flag;

        public HashNode(int k)
        {
            this.k=k;
            this.flag=NULL;
        }
    }

    public HashTable(int size)
    {
        this.size=size;
        remain=size;                                 //remain表示hash表剩余的空间
        data=new HashNode[size];
        for(int i=0;i<size;i++) {
            data[i]=new HashNode(0);
        }
    }

    private int hash(int k,int i)
    {
        //线性探查 hash(k,i)=(h(k)+i) mod size
        //二次探查 hash(k,i)=(h(k)+c1*i+c2*i*i) mod size   c1>0 && c2>0
        return (k%size+i*(1+k%998))%size;           //双重散列函数hash(k,i)=(h1(k)+i*h2(k)) mod size  其中h2要与size互素
    }

    public void insertNode(int k)
    {
        if (remain==0)
            return;
        int i=0;
        int key = hash(k, i);
        while (data[key].flag==HashNode.FULL)
            key = hash(k, ++i);
        data[key].k=k;
        data[key].flag=HashNode.FULL;
        remain--;
    }

    public void deleteNode(int k)
    {
        if (remain==size)
            return;
        int i = searchNode(k);
        if(i!=-1) {
            data[i].flag=HashNode.DELETED;
            remain++;
        }
    }

    public int searchNode(int k)
    {
        int i=0;
        int key = hash(k, i);
        while (i<size&&data[key].flag!=HashNode.NULL)
        {
            if (data[key].flag==HashNode.FULL&&data[key].k==k)
                return key;
            key = hash(k, ++i);
        }
        return -1;
    }

    public void show()
    {
        for(int i=0;i<size;i++)
        {
            if (data[i].flag==HashNode.FULL)
                System.out.print(data[i].k+" ");
            else if(data[i].flag==HashNode.DELETED)
                System.out.print("del ");
            else
                System.out.print("null ");
        }
        System.out.println();
    }
}
