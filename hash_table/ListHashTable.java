package hash_table;

/**
 * Created by lianghong on 2018/1/22.
 *
 * Hash表，采用双向链表法解决冲突
 */
public class ListHashTable {
    private int size;
    private Node[] table;

    private class Node {
        public int k;
        public Node prev, next;

        public Node(int k) {
            this.k = k;
            prev = next = null;
        }
    }

    public ListHashTable(int size) {
        this.size = size;
        table = new Node[size];
        for (int i = 0; i < size; i++)
            table[i] = new Node(i);
    }

    private int hash(int k) {
        //除法hash：hash(k)=k mod size
        return (int) (size * ((Math.sqrt(5) - 1) / 2 * k % 1));             //hash函数采用乘法哈希hash(k)=floor(m*(A*k mod 1))，A取黄金分割数
    }

    public void InsertNode(int k) {
        int i = hash(k);
        Node n = new Node(k);
        n.next = table[i].next;
        n.prev = table[i];
        table[i].next = n;
    }

    public void deleteNode(int k) {
        int i = hash(k);
        Node p = null;
        for (p = table[i].next; p != null && p.k != k; p = p.next) ;
        if (p != null) {
            p.prev.next = p.next;
            if (p.next != null)
                p.next.prev = p.prev;
        }
    }

    public int searchNode(int k) {
        int i = hash(k);
        for (Node p = table[i].next; p != null; p = p.next) {
            if (p.k == k)
                return i;
        }
        return -1;
    }

    public void show() {
        for (int i = 0; i < size; i++) {
            System.out.print(i + ": ");
            for (Node p = table[i].next; p != null; p = p.next)
                System.out.print(p.k + " ");
            System.out.println();
        }
    }
}
