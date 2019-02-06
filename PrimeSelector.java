public class PrimeSelector {

    /**
     * 求1-n所有的素数，普通素数筛法（埃拉托斯特尼(Eratosthenes)筛法）
     * 时间复杂度为O(nlog(log(n)))
     * 空间复杂度为O(1)
     */
    public static void EratosthenesPrimeSelector(int n)
    {
        boolean[] not_prime=new boolean[n+1];
        not_prime[0]=not_prime[1]=true;
        for(int i=2;i*i<=n;i++)
        {
            if(!not_prime[i])
            {
                for(int j=2;j*i<=n;j++)         //如果i是素数则i的所有整数倍的数都不是素数
                    not_prime[i*j]=true;
            }
        }
        for(int i=1;i<=n;i++)
        {
            if(!not_prime[i])
                System.out.print(i+" ");
        }
        System.out.println();
    }

    /**
     * 求1-n所有的素数，欧拉筛法（线性筛法）
     * 时间复杂度为O(n)，空间复杂度为O(n)
     */
    public static void EulerPrimeSelector(int n)
    {
        boolean[] not_prime=new boolean[n+1];
        List<Integer> prime = new ArrayList<>();
        for(int i=2;i<=n;i++)
        {
            if(!not_prime[i])                        //将素数放到一个队列中
                prime.add(i);
            for(int j=0;j<prime.size()&&prime.get(j)*i<=n;j++)         //每次遍历素数队列，与i的乘积不是素数
            {
                not_prime[i*prime.get(j)]=true;
                if(i%prime.get(j)==0)               //素数之间是互素的，如果i能被一个素数整除说明i已经被筛过了，break循环开始下一轮筛选
                    break;
            }
        }
        for(int i=0;i<prime.size();i++)
            System.out.print(prime.get(i)+" ");
        System.out.println();
    }

    public static void main(String... a)
    {
        EratosthenesPrimeSelector(1000);
        EulerPrimeSelector(1000);
    }
}
