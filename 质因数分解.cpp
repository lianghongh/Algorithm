#include<cstdio>
#include<cstdlib>
#include<ctime>
#define MAX 5000

typedef long long LL;
LL factor[MAX];
LL size=0;

LL gcd(LL a,LL b)         //欧几里得算法求出a,b的最大公约数
{
    while(b)
    {
        LL t=a;
        a=b;
        b=t%b;
    }
    return a;
}

LL Mod_Mul(LL a,LL b,LL n)      //快速求出(a*b)%n
{
    LL r=0;
    while(b)
    {
        if(b&1)
            r=(r+a)%n;
        a=(a+a)%n;
        b>>=1;
    }
    return r;
}

LL Mod_Exp(LL a,LL b,LL n)      //快速求出a^b%n
{
    LL r=1;
    while(b)
    {
        if(b&1)
            r=Mod_Mul(r,a,n);
        a=Mod_Mul(a,a,n);
        b>>=1;
    }
    return r;
}

bool Miller_Rabin(LL n)       //Miller_Rabin大数质数检测算法
{
    if(n==2)
        return true;
    if(n<2||!(n&1))
        return false;
    LL u=n-1,t=0;
    LL x,y;
    while((u&1)==0)
    {
        t++;
        u>>=1;
    }
    for(int i=0;i<10;i++)
    {
        x=Mod_Exp(rand()%(n-2)+2,u,n);
        for(LL j=0;j<t;j++)
        {
            y=Mod_Mul(x,x,n);
            if(y==1&&x!=1&&x!=n-1)
                return false;
            x=y;
        }
        if(x!=1)
            return false;
    }
    return true;
}

LL Pollard_Rho(LL n,LL c)            //Pollard_Rho算法快速求出n的一个因子
{
    LL i=1,k=2; 
    LL x=rand()%(n-1)+1,y;
    y=x;
    while(1)
    {
        ++i;
        x=(Mod_Mul(x,x,n)+c)%n;
        LL d=gcd((y-x+n)%n,n);
        if(d!=1&&d!=n)
            return d;
        if(x==y)
            return n;
        if(i==k)
        {
            y=x;
            k<<=1;
        }
    }
}

void find_factor(LL n)              //对n分解质因数
{
    if(n<=1)
        return;
    if(Miller_Rabin(n))
    {
        factor[size++]=n;
        return;
    }
    LL p=n;
    while(p>=n)
        p=Pollard_Rho(n,rand()%(n-1)+1);
    find_factor(p);
    find_factor(n/p);
}

int main()
{
    LL n;
    srand(int(time(0)));
    while(scanf("%lld",&n)&&n)
    {
        find_factor(n);
        for(LL i=0;i<size;i++)
            printf("%lld ",factor[i]);
        printf("\n");
        size=0;
    }
    return 0;
}
