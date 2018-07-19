// 求解最小生成树

package Graph;

import java.util.*;

public class MST {

    private double[][] weight;      //图是一个有权无向图
    private int E;     //边数
    private int V;    //顶点数

    public MST(int node)
    {
        if(node<=0)
            node = 100;
        weight=new double[node][node];
        for(int i=0;i<node;i++)
        {
            for(int j=0;j<node;j++)
            {
                if(i!=j)
                    weight[i][j]=Integer.MAX_VALUE;            //初始化，对于i==j的边权值初始化为0，其余的初始化为无穷大
            }
        }
        V=node;
        E=0;
    }


    public static class Union_Find                   //定义并查集
    {
        private int[] root;
        private int[] rank;
        public Union_Find(int n)
        {
            root = new int[n];
            for(int i=0;i<root.length;i++)
                root[i]=i;                           //MAKE_SET操作，初始化并查集
            rank = new int[n];                       //每个集合的秩初始化为0，若rank(i)<rank(j)，表示i所在集合的元素数小于j的
        }

        public int find(int x)
        {
            if(root[x]==x)
                return x;
            root[x]=find(root[x]);                  //路径压缩
            return root[x];
        }

        public void union(int x,int y)
        {
            int x_root=find(x);
            int y_root = find(y);
            if(x_root==y_root)
                return;
            if(rank[x_root]<rank[y_root])            //按秩合并，将秩小的集合合并到秩大的集合
                root[x_root]=y_root;
            else
            {
                if(rank[x_root]==rank[y_root])       //每当把较小的集合合并到较大的集合中，较大的集合的秩+1
                    rank[x_root]++;
                root[y_root] = x_root;
            }
        }
    }

    public static class Edge            //定义边from->to，权重为weight
    {
        public int from;
        public int to;
        public double weight;

        public Edge(int from,int to,double w){
            this.from=from;
            this.to=to;
            this.weight=w;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(from);
            builder.append(" ");
            builder.append(to);
            builder.append(" ");
            builder.append(weight);
            return builder.toString();
        }
    }

    public int getE() {
        return E;
    }

    public int getV() {
        return V;
    }

    public void insert(int from,int to,double w)           //把边插入到无向图中
    {
        if(from<0||from>=V||to<0||to>=V)
            return;
        weight[from][to]=w;
        weight[to][from]=w;
        E+=2;
    }

// Kruskal算法步骤：
// (1) 将图中的每一条边添加到一个集合中进行排序
// (2) 每次选择集合中边所在的顶点不在同一个并查集集合的边，且边的权值最小，将该边添加到MST集合中
// (3) 经过V-1次（V是顶点数）操作，得到MST集合
// 平均时间复杂度：O(Elog(V))

    public List<Edge> Kruskal()
    {
        List<Edge> r = new ArrayList<>();
        List<Edge> edge = new ArrayList<>();
        for(int i=0;i<V;i++)
        {
            for(int j=i+1;j<V;j++)
                edge.add(new Edge(i,j,weight[i][j]));
        }
        Collections.sort(edge, new Comparator<Edge>() {                //把边加入到list中按照权值从小到大排序
            @Override
            public int compare(Edge o1, Edge o2) {
                if(o1.weight<o2.weight)
                    return -1;
                else if(o1.weight>o2.weight)
                    return 1;
                else
                    return 0;
            }
        });

        Union_Find union_find = new Union_Find(V);
        while (r.size()<V-1)
        {
            for(int i=0;i<edge.size();i++)
            {
                int from=edge.get(i).from;
                int to=edge.get(i).to;
                if(union_find.find(from)!=union_find.find(to))          //每次把不在同一个集合中的最小边添加到MST中
                {
                    r.add(new Edge(from,to,weight[from][to]));
                    union_find.union(from,to);                          //添加后合并集合
                }
            }
        }
        return r;
    }

    private static class Node          //定义Node，node表示节点的索引，parent表示按照prim算法生成的MST的父节点
    {
        public int node;
        public int parent;
        public double key;             //key表示节点到已生成的MST集合的最小距离
        public Node(int node,double key){
            this.node=node;
            this.key=key;
        }
    }

// Prim算法求解最小生成树，步骤如下：
// (1) 为每个节点维护一个到MST集合的距离key，初始化节点，添加到优先队列中
// (2) 每次从优点队列中取出一个点（距离MST最小），更新该点的邻接点到MST的距离
// (3) 进行V-1次操作，直到优先队列为空，可以得到一颗MST
// 算法复杂度：O(Elog(E)+Vlog(V))=O(Elog(V))

    public List<Edge> Prim(int root)               //按照Prim算法从root节点生成MST
    {
        if(root<0||root>=V)
            return null;
        List<Edge> r = new ArrayList<>();
        PriorityQueue<Node> q = new PriorityQueue<>(new Comparator<Node>() {           //定义优先队列，对Node进行排序，选出距离MST集合最近的节点
            @Override
            public int compare(Node o1, Node o2) {
                if(o1.key<o2.key)
                    return -1;
                else if(o1.key>o2.key)
                    return 1;
                else
                    return 0;
            }
        });
        boolean[] isadd = new boolean[V];                    //标记是否已经添加到MST集合中
        Node[] nodes = new Node[V];
        for(int i=0;i<V;i++)
        {
            nodes[i]=new Node(i,Integer.MAX_VALUE);
            q.offer(nodes[i]);
        }
        nodes[root].key=0;
        q.remove(nodes[root]);                              //修改了root的key值，需要更新优先队列
        q.offer(nodes[root]);

        while (!q.isEmpty())
        {
            Node u=q.poll();                                //找出距离MST集合最小的节点
            isadd[u.node]=true;
            for(int i=0;i<V;i++)                            //将u添加到MST集合中，遍历邻接点，更新每个点到MST集合的距离
            {
                if(!isadd[i]&&weight[u.node][i]<nodes[i].key)
                {
                    nodes[i].key = weight[u.node][i];
                    nodes[i].parent=u.node;
                    q.remove(nodes[i]);
                    q.offer(nodes[i]);
                }
            }
            if(!q.isEmpty())                                //保存MST集合
                r.add(new Edge(q.peek().parent,q.peek().node,weight[q.peek().parent][q.peek().node]));
        }
        return r;
    }
}
