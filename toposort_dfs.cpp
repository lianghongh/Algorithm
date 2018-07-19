#include <cstdio>
#include <vector>
#include <iostream>

class Node
{
public:
    int e;
    Node *next;

    Node(int e)
    {
        this->e=e;
        next= nullptr;
    }

    Node(){
        next= nullptr;
    }
};


class Graph
{
public:

    Node *nodes;
    int node_num;
    int arc_num;
    bool *onStack,*isVisited;
    int *parent;
    std::vector<int> cycle;
    std::vector<int> topo_s;
    bool hasCycle;

    Graph(int node_num){
        nodes=new Node[node_num];
        arc_num=0;
        this->node_num=node_num;
        onStack=new bool[node_num];
        isVisited=new bool[node_num];
        parent = new int[node_num];
        hasCycle= false;
        for(int i=0;i<node_num;i++)
        {
            onStack[i]=isVisited[i]=false;
            parent[i]=-1;
        }
    }

    void add_arc(int from,int to)
    {
        Node *p=new Node(to);
        p->next=nodes[from].next;
        nodes[from].next=p;
        arc_num++;
    }

    void dfs(int v)
    {
        isVisited[v]= true;
        onStack[v]= true;
        for(Node *p=nodes[v].next;p!= nullptr;p=p->next)
        {
            if(hasCycle)
                return;
            if(!isVisited[p->e])
            {
                parent[p->e]=v;
                dfs(p->e);
            }
            else if(onStack[p->e])
            {
                for(int q=v;q!=p->e;q=parent[q])
                    cycle.push_back(q);
                cycle.push_back(p->e);
                hasCycle= true;
                std::cout<<"has cycle!\n";
            }
        }
        onStack[v]= false;
        topo_s.push_back(v);
    }

    void toposort()
    {
        for (int i = 0; i < node_num; i++)
        {
            if(!isVisited[i])
                dfs(i);
        }
    }

    ~Graph()
    {
        for(int i=0;i<node_num;i++)
        {
            for(Node *p=nodes[i].next;p;)
            {
                nodes[i].next=p->next;
                delete p;
                p=nodes[i].next;
            }
        }
        delete[] nodes;
        delete[] onStack;
        delete[] isVisited;
        delete[] parent;
    }
};

int main()
{
    Graph g(13);
    g.add_arc(0, 6);
    g.add_arc(2, 0);
    g.add_arc(0, 1);
    g.add_arc(0, 5);
    g.add_arc(3, 5);
    g.add_arc(5, 4);
    g.add_arc(6, 4);
    g.add_arc(2, 3);
    g.add_arc(8, 7);
    g.add_arc(7, 6);
    g.add_arc(6, 9);
    g.add_arc(9, 10);
    g.add_arc(9, 11);
    g.add_arc(9, 12);
    g.add_arc(11, 12);
    g.add_arc(12, 8);
    g.toposort();

    if(g.hasCycle)
    {
        for(std::vector<int>::iterator i=g.cycle.begin();i!=g.cycle.end();i++)
            std::cout<<*i<<" ";
    }
    else
    {
        for(std::vector<int>::reverse_iterator i=g.topo_s.rbegin();i!=g.topo_s.rend();i++)
            std::cout<<*i<<" ";
    }


    return 0;
}