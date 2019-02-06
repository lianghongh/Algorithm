#include<cstdio>
#include<cstdlib>
#include<ctime>

int solution=1;

bool isSafe(int board[],int x,int y)
{
    for(int i=0;i<y;i++)
    {
        if(board[i]==x||i-y==board[i]-x||i-y==x-board[i])
            return false;
    }
    return true;
}

void dfs(int board[],int length,int cur)
{
    if(cur==length)
    {
        printf("Solution #%d\n",solution++);
        for(int i=0;i<length;i++)
        {
            for(int j=0;j<length;j++)
            {
                if(i==board[j])
                    printf("Q ");
                else
                    printf("* ");
            }
            printf("\n");
        }
		printf("\n");
        return;
    }

    for(int i=0;i<length;i++)
    {
        if(isSafe(board,i,cur))
        {
            board[cur]=i;
            dfs(board,length,cur+1);
        }
    }
}

int main(int argc,char *argv[])
{
    if(argc!=2)
    {
        printf("Wrong Parameter! Exiting...\n");
        exit(1);
    }

    int n=atoi(argv[1]);
    int *p=new int[n];
	clock_t start=clock();
    dfs(p,n,0);
	clock_t end=clock();
	printf("Time cost: %.2f ms\n",double(end-start)/CLOCKS_PER_SEC*1000);
    return 0;
}
