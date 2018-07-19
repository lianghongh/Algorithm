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
    public int Dijkstra(int from, int to, List<Integer> path)
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
            path.add(i);
            i=nodes[i].parent;
        }
        Collections.reverse(path);
        return nodes[to].dist;
    }
//Floyd算法，平均时间复杂度为O(V^3)，空间复杂度为O(V^2)
    public int Floyd(int from,int to,List<Integer> path)
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

    private static class Edge                               //定义Bellman_ford的边集
    {
        public int from;
        public int to;
        public int w;

        public Edge(int from,int to,int w)
        {
            this.from=from;
            this.to=to;
            this.w=w;
        }
    }

//Bellman_Ford算法求解单点源最短路
//平均时间复杂度为O(EV)
    public int Bellman_Ford(int from,int to,List<Integer> path)
    {
        List<Edge> edges = new ArrayList<>();
        for(int i=0;i<V;i++)
        {
            for(int j=0;j<V;j++)
            {
                if(i!=j&&weight[i][j]!=Integer.MAX_VALUE)
                    edges.add(new Edge(i, j, weight[i][j]));
            }
        }
        int[] dist = new int[V];
        int[] parent = new int[V];
        Arrays.fill(dist,Integer.MAX_VALUE);                 //初始化边集和最短路径树的parent节点
        Arrays.fill(parent, -1);
        dist[from]=0;
        for(int i=1;i<V;i++)                                //进行|V|-1次循环
        {
            for(int j=0;j<edges.size();j++)                 //遍历所有的边，进行松弛
            {
                if(dist[edges.get(j).from]!=Integer.MAX_VALUE&&edges.get(j).w!=Integer.MAX_VALUE&&dist[edges.get(j).from]+edges.get(j).w<dist[edges.get(j).to])
                {
                    dist[edges.get(j).to]=dist[edges.get(j).from]+edges.get(j).w;
                    parent[edges.get(j).to]=edges.get(j).from;
                }
            }
        }

        boolean hasCycle=false;
        for(int i=0;i<edges.size();i++)                      //检查有没有负环
        {
            if(dist[edges.get(i).from]!=Integer.MAX_VALUE&&edges.get(i).w!=Integer.MAX_VALUE&&dist[edges.get(i).to]>dist[edges.get(i).from]+edges.get(i).w)
            {
                hasCycle=true;
                break;
            }
        }

        if(hasCycle)
        {
            System.out.println("has cycle!");
            System.exit(1);
        }

        int i=to;
        while (i!=-1)
        {
            path.add(i);
            i = parent[i];
        }
        Collections.reverse(path);
        return dist[to];
    }

    public static void main(String[] args)
    {
        ShortPath shortPath = new ShortPath(6);
        shortPath.insert(0, 4, 30);
        shortPath.insert(0,5,100);
        shortPath.insert(0,2,10);
        shortPath.insert(1,2,5);
        shortPath.insert(2,3,50);
        shortPath.insert(4,3,20);
        shortPath.insert(4,5,60);
        shortPath.insert(3, 5, 10);
        List<Integer> path1 = new ArrayList<>();
        List<Integer> path2 = new ArrayList<>();
        int from=1,to=5;
        int dis = shortPath.Dijkstra(from,to,path1);
        System.out.println(Arrays.toString(path1.toArray()));
        System.out.println("min_path:"+dis);
        int dis2=shortPath.Bellman_Ford(from,to,path2);
        System.out.println(Arrays.toString(path2.toArray()));
        System.out.println("min_path:"+dis2);
    }
}
