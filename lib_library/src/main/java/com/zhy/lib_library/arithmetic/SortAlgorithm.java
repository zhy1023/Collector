package com.zhy.lib_library.arithmetic;

/**
 * @author ； ZY
 * @date : 2020/8/6
 * @describe : 排序算法
 */

public class SortAlgorithm {

    /**
     * todo 冒泡排序
     *  * 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
     *  * 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
     *  * 针对所有的元素重复以上的步骤，除了最后一个。
     *  * 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
     */
    public static int[] sortByBubble(int[] arrays) {
        if (Utils.isEmptyArray(arrays)) return arrays;
        for (int i = 0; i < arrays.length; i++) {
            for (int j = 0; j < arrays.length - i - 1; j++) {
//                if (arrays[j] < arrays[j + 1]) { 倒序
                if (arrays[j] < arrays[j + 1]) {
                    swap(arrays, j, j + 1);
                }
            }
        }
        return arrays;
    }

    /**
     * todo 选择排序
     *  * 在未排序序列中找到最小元素，存放到排序序列的起始位置
     *  * 再从剩余未排序元素中继续寻找最小元素，然后放到排序序列末尾。
     *  * 以此类推，直到所有元素均排序完毕。
     *
     * @return
     */
    public static int[] sortBySelect(int[] arrays) {
        if (Utils.isEmptyArray(arrays)) return arrays;
        for (int i = 0; i < arrays.length; i++) {
            int minIndex = i;
            for (int j = 0; j < arrays.length; j++) {
                if (arrays[minIndex] < arrays[j]) {
                    minIndex = j;
                }
            }
            swap(arrays, i, minIndex);
        }
        return arrays;
    }

    /**
     * todo 插入排序
     */
    public static int[] sortByInsert(int[] arrays) {
        if (Utils.isEmptyArray(arrays)) return arrays;
        int current;
        for (int i = 0; i < arrays.length - 1; i++) {
            current = arrays[i + 1];
            int preIndex = i;
            while (preIndex >= 0 && current < arrays[preIndex]) {
                arrays[preIndex + 1] = arrays[preIndex];
                preIndex--;
            }
            arrays[preIndex + 1] = current;
        }
        return arrays;
    }


    /**
     * todo 快速排序
     * 通过一趟排序将待排序记录分割成独立的两部分，
     * 其中一部分记录的关键字均比另一部分关键字小，
     * 则分别对这两部分继续进行排序，直到整个序列有序。
     */
    public static int[] sortByQuick(int[] arrays) {
        if (Utils.isEmptyArray(arrays)) return arrays;
        quickSort(arrays, 0, arrays.length - 1);
        return arrays;
    }

    /**
     * @param numbers 带排序数组
     * @param low     开始位置
     * @param high    结束位置
     */
    public static void quickSort(int[] numbers, int low, int high) {
        if (low < high) {
            int middle = getMiddle(numbers, low, high); //将numbers数组进行一分为二
            quickSort(numbers, low, middle - 1);   //对低字段表进行递归排序
            quickSort(numbers, middle + 1, high); //对高字段表进行递归排序
        }
    }

    /**
     * 查找出中轴（默认是最低位low）的在numbers数组排序后所在位置
     *
     * @param numbers 带查找数组
     * @param low     开始位置
     * @param high    结束位置
     * @return 中轴所在位置
     */
    public static int getMiddle(int[] numbers, int low, int high) {
        int temp = numbers[low]; //数组的第一个作为中轴
        while (low < high) {
            while (low < high && numbers[high] >= temp) {
                high--;
            }
            numbers[low] = numbers[high];//比中轴小的记录移到低端
            while (low < high && numbers[low] < temp) {
                low++;
            }
            numbers[high] = numbers[low]; //比中轴大的记录移到高端
        }
        numbers[low] = temp; //中轴记录到尾
        return low; // 返回中轴的位置
    }


    /**
     * todo 归并排序
     * 归并（Merge）排序法是将两个（或两个以上）有序表合并成一个新的有序表，
     * 即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整体有序序列
     * <p>
     * todo 设r[i…n]由两个有序子表r[i…m]和r[m+1…n]组成，两个子表长度分别为n-i +1、n-m。
     *  1、j=m+1；k=i；i=i; //置两个子表的起始下标及辅助数组的起始下标
     *  2、若i>m 或j>n，转⑷ //其中一个子表已合并完，比较选取结束
     *  3、//选取r[i]和r[j]较小的存入辅助数组rf
     *          如果r[i]<r[j]，rf[k]=r[i]； i++； k++； 转⑵
     *          否则，rf[k]=r[j]； j++； k++； 转⑵
     *  4、//将尚未处理完的子表中元素存入rf
     *          如果i<=m，将r[i…m]存入rf[k…n] //前一子表非空
     *          如果j<=n ,  将r[j…n] 存入rf[k…n] //后一子表非空
     *  5、合并结束。
     */
    public static int[] sortByMerge(int[] arrays) {
        if (Utils.isEmptyArray(arrays)) return arrays;
        sortByMerge(arrays, 0, arrays.length - 1);
        return arrays;
    }

    /**
     * 归并排序
     * 简介:将两个（或两个以上）有序表合并成一个新的有序表 即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整体有序序列
     * 时间复杂度为O(nlogn)
     * 稳定排序方式
     *
     * @param nums 待排序数组
     * @return 输出有序数组
     */
    public static int[] sortByMerge(int[] nums, int low, int high) {
        int mid = (low + high) / 2;
        if (low < high) {
            // 左边
            sortByMerge(nums, low, mid);
            // 右边
            sortByMerge(nums, mid + 1, high);
            // 左右归并
            merge(nums, low, mid, high);
        }
        return nums;
    }

    /**
     * 将数组中low到high位置的数进行排序
     *
     * @param nums 待排序数组
     * @param low  待排的开始位置
     * @param mid  待排中间位置
     * @param high 待排结束位置
     */
    public static void merge(int[] nums, int low, int mid, int high) {
        int[] temp = new int[high - low + 1];
        int i = low;// 左指针
        int j = mid + 1;// 右指针
        int k = 0;

        // 把较小的数先移到新数组中
        while (i <= mid && j <= high) {
            if (nums[i] < nums[j]) {
                temp[k++] = nums[i++];
            } else {
                temp[k++] = nums[j++];
            }
        }

        // 把左边剩余的数移入数组
        while (i <= mid) {
            temp[k++] = nums[i++];
        }

        // 把右边边剩余的数移入数组
        while (j <= high) {
            temp[k++] = nums[j++];
        }

        // 把新数组中的数覆盖nums数组
        System.arraycopy(temp, 0, nums, low, temp.length);
    }


    /**
     * todo 堆排序
     * 　堆排序是一种树形选择排序，是对直接选择排序的有效改进。
     * 　　堆的定义下：具有n个元素的序列 （h1,h2,...,hn),当且仅当满足（hi>=h2i,hi>=2i+1）或（hi<=h2i,hi<=2i+1） (i=1,2,...,n/2)时称之为堆。
     *     在这里只讨论满足前者条件的堆。由堆的定义可以看出，堆顶元素（即第一个元素）必为最大项（大顶堆）。完全二 叉树可以很直观地表示堆的结构。堆顶为根，其它为左子树、右子树。
     * 　　思想:初始时把要排序的数的序列看作是一棵顺序存储的二叉树，调整它们的存储序，使之成为一个 堆，
     *      这时堆的根节点的数最大。然后将根节点与堆的最后一个节点交换。然后对前面(n-1)个数重新调整使之成为堆。
     *      依此类推，直到只有两个节点的堆，并对 它们作交换，最后得到有n个节点的有序序列。
     *      从算法描述来看，堆排序需要两个过程，一是建立堆，二是堆顶与堆的最后一个元素交换位置。
     *      所以堆排序有两个函数组成。一是建堆的渗透函数，二是反复调用渗透函数实现排序的函数。
     */
    public static int[] sortByHeap(int[] arrays) {
        if (Utils.isEmptyArray(arrays)) return arrays;
        int arrayLength = arrays.length;
        //循环建堆
        for (int i = 0; i < arrayLength - 1; i++) {
            //建堆
            buildMaxHeap(arrays, arrayLength - 1 - i);
            //交换堆顶和最后一个元素
            swap(arrays, 0, arrayLength - 1 - i);
        }

        return arrays;
    }

    //对data数组从0到lastIndex建大顶堆
    private static void buildMaxHeap(int[] data, int lastIndex) {
        //从lastIndex处节点（最后一个节点）的父节点开始
        for (int i = (lastIndex - 1) / 2; i >= 0; i--) {
            //k保存正在判断的节点
            int k = i;
            //如果当前k节点的子节点存在
            while (k * 2 + 1 <= lastIndex) {
                //k节点的左子节点的索引
                int biggerIndex = 2 * k + 1;
                //如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在
                if (biggerIndex < lastIndex) {
                    //若果右子节点的值较大
                    if (data[biggerIndex] < data[biggerIndex + 1]) {
                        //biggerIndex总是记录较大子节点的索引
                        biggerIndex++;
                    }
                }
                //如果k节点的值小于其较大的子节点的值
                if (data[k] < data[biggerIndex]) {
                    //交换他们
                    swap(data, k, biggerIndex);
                    //将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值
                    k = biggerIndex;
                } else {
                    break;
                }
            }
        }
    }

    /**** 交换数据*/
    private static void swap(int[] data, int i, int j) {
        int tmp = data[i];
        data[i] = data[j];
        data[j] = tmp;
    }
}
