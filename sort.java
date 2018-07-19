package sort;

/**
 * Created by lianghong on 2018/1/23
 * 常用排序算法总结，包括插入排序(InsertionSort)，冒泡排序(BubbleSort)，选择排序(SelectionSort)，快速排序（QuickSort)，
 * 二路归并排序(MergeSort)，堆排序(HeapSort)，计数排序(CountingSort)，希尔排序（ShellSort)和桶排序(BucketSort)
 */
public class Sort {

    /**
     * 插入排序算法，每次取后面的元素往前插入完成排序，平均时间复杂度O(n^2)，最好时间复杂度为O(n)
     * @param a 输入的数组
     */
    public static void InsertionSort(int[] a)
    {
        for(int i=1;i<a.length;i++)
        {
            int j=i-1;
            int t=a[i];
            while (j>=0&&t<a[j])
            {
                a[j+1]=a[j];
                j--;
            }
            a[j+1]=t;
        }
    }

    /**
     * 冒泡排序算法，每次交换两个数的位置使较大的数往后移动（冒泡），最好最坏时间复杂度都是O(n^2)，最慢的排序算法之一
     * @param a 输入的数组
     */
    public static void BubbleSort(int[] a)
    {
        for(int i=a.length-1;i>0;i--)
        {
            for(int j=1;j<=i;j++)
            {
                if (a[j-1]>a[j]) {
                    int m=a[j-1];
                    a[j-1]=a[j];
                    a[j]=m;
                }
            }
        }
    }

    /**
     * 选择排序算法，每次从0-i选择一个最大的数，与a[i]交换。平均时间复杂度为O(n^2)
     * @param a 输入的数组
     */

    public static void SelectionSort(int[] a)
    {
        for(int i=a.length-1;i>0;i--)
        {
            int max_val=Integer.MIN_VALUE;
            int max=0;
            for(int j=0;j<=i;j++)
            {
                if (a[j]>max_val)
                {
                    max_val=a[j];
                    max=j;
                }
            }
            int m=a[max];
            a[max]=a[i];
            a[i]=m;
        }
    }

    /**
     * 快速排序算法，每次选出一个元素作为界限，把比它小的元素放到左边，比它大的放到右边
     * 平均时间复杂度为O(nlogn)，最坏情况为O(n^2)，
     * 平均空间复杂度为O(logn)，最坏情况为O(n)，是基于比较法排序中最快的排序算法之一
     * @param a 输入的数组
     */
    public static void QuickSort(int[] a)
    {
        quick_sort(a,0,a.length-1);
    }

    private static void quick_sort(int[] a,int l,int r)
    {
        if(l<r)
        {
            int m=partition(a,l,r);
            quick_sort(a, l, m - 1);
            quick_sort(a, m + 1, r);
        }
    }

    private static int partition(int[] a,int l,int r)
    {
        int i=l;
        int j=l;
        while (j<r)
        {
            if(a[j]<a[r])
            {
                int m=a[j];
                a[j]=a[i];
                a[i++]=m;
            }
            j++;
        }
        int m=a[i];
        a[i]=a[r];
        a[r]=m;
        return i;
    }

    /**
     * 归并排序算法，选出中间的一个数，分别对两边递归做归并排序，最后通过merge操作合并两个有序数组，从而完成排序
     * 最好和最坏时间复杂度都为O(nlogn)，空间复杂度为O(n)
     * @param a 输入的数组
     */
    public static void MergeSort(int[] a)
    {
        int[] temp = new int[a.length];
        MergeSort(a,temp,0,a.length-1);
    }

    private static void MergeSort(int[] a,int[] temp,int l,int r)
    {
        if (l<r)
        {
            int m=(l+r)/2;
            MergeSort(a,temp,l,m);
            MergeSort(a,temp,m+1,r);
            merge(a, temp,l, m, r);
        }
    }

    private static void merge(int[] a,int[] temp,int l,int m,int r)
    {
        int i,j,k;
        for(i=l,j=m+1,k=0;i<=m&&j<=r;k++)
        {
            if (a[i]<a[j])
                temp[k]=a[i++];
            else
                temp[k]=a[j++];
        }
        while (i<=m)
            temp[k++]=a[i++];
        while (j<=r)
            temp[k++]=a[j++];
        for(i=0;i<k;i++)
            a[i+l]=temp[i];
    }

    /**
     * 堆排序，使用最大堆(Maximum Heap)进行排序，其中a[0]是整个堆中的最大元素。通过每次与堆的最后一个元素交换，然后调整a[0]进行排序
     * 平均时间复杂度为O(nlogn)，空间复杂度为O(1)
     * @param a 输入的数组
     */
    public static void HeapSort(int[] a)           //这里的实现和一般书上的不一样，这里堆顶点是a[0]不是a[1]
    {
        for(int i=a.length/2;i>=0;i--)
            heap_adjust(a,a.length-1,i);
        int size=a.length-1;
        while (size>0)
        {
            int m=a[0];
            a[0]=a[size];
            a[size]=m;
            heap_adjust(a,--size,0);
        }
    }

    private static void heap_adjust(int[] a,int size,int k)
    {
        int max=k;
        if(2*k+1<=size&&a[max]<a[2*k+1])            //下标从0开始时，left_child下标为2*k+1，right_child为2*k+2
            max=2*k+1;
        if (2*k+2<=size&&a[max]<a[2*k+2])
            max=2*k+2;
        if (max!=k)
        {
            int m = a[max];
            a[max]=a[k];
            a[k]=m;
            heap_adjust(a, size,max);
        }
    }

    /**
     * 计数排序算法，按照输入数据的范围进行排序
     * 平均时间复杂度为O(n+e)，e为数字的范围
     * @param a 输入的数组
     * @param bound 输入的数的范围 bound >= max(a)
     */
    public static void CountingSort(int[] a,int bound)
    {
        int[] count = new int[bound + 1];
        int[] b = new int[a.length];
        for(int i=0;i<count.length;i++)
            count[i]=0;
        for(int i=0;i<a.length;i++)
            count[a[i]]++;
        for(int i=0;i<count.length-1;i++)
            count[i+1]+=count[i];
        for(int i=a.length-1;i>=0;i--)
        {
            b[count[a[i]]-1]=a[i];
            count[a[i]]--;
        }
        for(int i=0;i<a.length;i++)
            a[i]=b[i];
    }

    /**
     * 希尔排序算法，按照步长为d的插入排序对数组排序。每完成一次就减少d，直到d=1
     * 时间复杂度和选取的步长序列有关，一般业界认为平均复杂度为O(n^1.5)，选取的步长序列合理可以降低到O(n^1.2-n^1.3)
     * 步长的选取策略还是一个未能解决的学术问题
     * @param a 输入的数组
     * @param d 初始步长，这里采用每次对d减半的等比步长序列
     */
    public static void ShellSort(int[] a,int d)
    {
        while (d>=1)
        {
            for(int i=d;i<a.length;i++)
            {
                int j=i-d;
                int t=a[i];
                while (j>=0&&a[j]>t)
                {
                    a[j+d]=a[j];
                    j-=d;
                }
                a[j+d]=t;
            }
            d/=2;
        }
    }

    /**
     * 桶排序，输入的数据为[0,1)的数。通过一次hash映射把数字映射到0-9个桶中，然后对每个桶采用插入排序(InsertionSort)，到此时排序已经完成
     * 从0-9个桶中依次读入数据的顺序就是排序后的顺序，平均时间复杂度为O(n)
     * @param a 输入的数组，数组元素大小范围[0,1)
     */
    public static void BucketSort(double[] a)
    {

        class Bucket
        {
            double e;
            Bucket next;

            public Bucket(double e)
            {
                this.e=e;
                next=null;
            }
        }

        Bucket[] bucket=new Bucket[10];
        for(int i=0;i<10;i++)
            bucket[i]=new Bucket(0);
        for(int i=0;i<a.length;i++)
        {
            int index = (int) a[i] * 10;
            Bucket q=bucket[index];
            for(Bucket p=q.next;p!=null&&p.e<a[i];p=p.next)
                q=p;
            Bucket node = new Bucket(a[i]);
            node.next=q.next;
            q.next=node;
        }
        for(int i=0,k=0;i<10;i++)
        {
            for(Bucket p=bucket[i].next;p!=null;p=p.next)
                a[k++]=p.e;
        }
    }

}
