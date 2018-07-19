package Graph;

import java.util.*;

public class ShortPath {

    private int[][] weight;                   //定义有向无环图，表示边权值
    private int V;                            //顶点数
    private int E;                            //边数

    public ShortPath(int n)
    {
        if(n<=0)
            n=100;
        weight=new int[n][n];
        for(int i=0;i<n;i++)
        {
            for(int j=0;j<n;j++)
            {
                if(i!=j)
                    weight[i][j]=Integer.MAX_VALUE;
                else
                    weight[i][j]=0;
            }
        }
        V=n;
        E=0;
    }

    public void insert(int from,int to,int w)          //插入有向边
    {
        if(from<0||from>=V||to<0||to>=V)
            return;
        weight[from][to]=w;
        E++;
    }

    private static class Node                //定义Node，维护节点到源节点的距离以及父节点
    {
        public int dist;                   //表示到源节点的距离
        public int node;                   //节点索引
        public int parent;                 //所在最短路径树的父节点

        public Node(int node,int dist,int parent)
        {
            this.node=node;
            this.dist=dist;
            this.parent=parent;
        }
    }
// dijkstra最短路算法，平均时间复杂度为O(E+V^2)=O(V^2)
    public int dijkstra(int from, int to, List<Integer> path)
    {
        boolean[] isadd=new boolean[V];                              //是否被添加到最短路径树中
        Node[] nodes = new Node[V];
        PriorityQueue<Node> q=new PriorityQueue<>(new Comparator<Node>() {             //定义优先队列，选出距离源点最小的节点
            @Override
            public int compare(Node o1, Node o2) {
                return o1.dist-o2.dist;
            }
        });
        for(int i=0;i<V;i++)
        {
            nodes[i]=new Node(i,Integer.MAX_VALUE,-1);             //初始化优先队列
            q.offer(nodes[i]);
        }
        nodes[from].dist=0;                                         //源点到源点的距离为0，更新优先队列
        q.remove(nodes[from]);
        q.offer(nodes[from]);

        while (!q.isEmpty())
        {
            Node u=q.poll();
            isadd[u.node]=true;                                    //每次选出距离源点最近的点添加到最短路径树中
            for(int i=0;i<V;i++)                                   //RELAX松弛操作，主要要检查溢出！！！！
            {
                if(!isadd[i]&&u.dist!=Integer.MAX_VALUE&&weight[u.node][i]!=Integer.MAX_VALUE&&u.dist+weight[u.node][i]<nodes[i].dist)
                {
                    nodes[i].dist=u.dist+weight[u.node][i];
                    nodes[i].parent=u.node;
                    q.remove(nodes[i]);
                    q.offer(nodes[i]);
                }
            }
        }
        int i=to;
        while (i!=-1)                                             //按照最短路径树找到最短路径
        {
            if(nodes[i].dist<Integer.MAX_VALUE)
                path.add(i);
            i=nodes[i].parent;
        }
        Collections.reverse(path);
        return nodes[to].dist;
    }
//Floyd算法，平均时间复杂度为O(V^3)，空间复杂度为O(V^2)
    public int floyd(int from,int to,List<Integer> path)
    {
        int[][] dist = new int[V][V];                         //dist[i][j]表示i->j的距离
        int[][] path_node = new int[V][V];                    //k=path_node[i][j]表示路径i->j会经过k
        for(int i=0;i<V;i++)
        {
            for(int j=0;j<V;j++)
            {
                dist[i][j]=weight[i][j];
                path_node[i][j]=j;                            //初始化path_node和weight
            }
        }

        for(int k=0;k<V;k++)                                  //核心算法，计算任意两个节点之间的最短距离
        {
            for(int i=0;i<V;i++)
            {
                for(int j=0;j<V;j++)
                {
                    if(dist[i][k]!=Integer.MAX_VALUE&&dist[k][j]!=Integer.MAX_VALUE&&dist[i][k]+dist[k][j]<dist[i][j])
                    {
                        dist[i][j]=dist[i][k]+dist[k][j];
                        path_node[i][j] = path_node[i][k];
                    }
                }
            }
        }
        int i=from,j=to;                                     //根据path_node计算路径
        path.add(i);
        while ((i=path_node[i][j])!=j)
            path.add(i);
        path.add(j);
        return dist[from][to];
    }
}
