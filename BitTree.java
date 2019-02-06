// 树状数组，高效求A[m,n]的数组和，原理是按照整数的二进制表示进行二进制区间分解


//      11=(1011)=(1000)+(10)+(1)    因此11可以被分解为3个区间，每个区间表示A数组元素的个数
//      A[1,11]=A[1,8]+A[9,10]+A[11]

// C是树状数组的本体，存储树状数组的二进制区间和，其中C[i]=A[i-2^k+1]+A[i-2^k+2]+...+A[i]
// 其中2^k表示i的二进制最低1bit位表示的整数，如6=(110)的最低1bit位表示的整数为(10)=2，这个值可以用lowbit(x)=x&-x进行计算
// 因此可以得到：C[i]=A[i-lowbit(i)+1]+A[i-lowbit(i)+2]+...+A[i]
// C[11]=A[11]
// C[10]=A[9]+A[10]
// C[9]=A[9]
// C[8]=A[1]+A[2]+...+A[8]
// C[7]=A[7]
// C[6]=A[5]+A[6]
// C[5]=A[5]
// C[4]=A[1]+A[2]+A[3]+A[4]
// C[3]=A[3]
// C[2]=A[1]+A[2]
// C[1]=A[1]


public class BitTree {

    private int size;
    private int[] C;          //C存储树状数组的分段和

    public BitTree(int size,int[] A)
    {
        this.size=size;
        C = new int[size + 1];
        for(int i=1;i<=size;i++)
            add(i,A[i]);               //通过Add操作初始化C
    }

    private int lowbit(int x)
    {
        return x&(-x);
    }

    public void add(int i,int k)
    {
        for(;i<=size;i+=(i&-i))
            C[i]+=k;
    }

    public int sum(int i)
    {
        if(i>size)
            i=size;
        if(i<1)
            return -1;
        int result=0;
        for(;i>0;i-=(i&-i))
            result+=C[i];
        return result;
    }

    public static void main(String... pp)
    {
        int[] A = {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        BitTree bitTree = new BitTree(10,A);
        System.out.println(bitTree.sum(7)-bitTree.sum(3));

    }
}
