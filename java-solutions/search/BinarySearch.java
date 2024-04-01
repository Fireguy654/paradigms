package search;

public class BinarySearch {
    // Pre:
    // l >= -1 && r <= n &&
    // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
    // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
    private static int recursiveBinSearch(int x, int[] a, int l, int r) {
        // l >= -1 && r <= n && a == a' &&  
        // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
        // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
        int ret;
        // l >= -1 && r <= n && a == a' &&  
        // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
        // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
        if (r - l == 1) {
            // l >= -1 && r <= n && a == a' &&  
            // (r == l + 1) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
            // из этого следует l >= -1 && r <= n && a == a' &&  
            // (forall i i >= 0 && i < r = l + 1 (т.е. i <= l) a[i] > x) &&
            // ((a[r] <= x) || (r == a.length) && (forall i i > r && i < a.length a[i] <= a[r]))
            ret = r;
            // a == a' &&  (forall i i >= 0 && i < ret = l + 1 (т.е. i <= l) a[i] > x) &&
            // ((ret == a.length) || (a[ret] <= x) && (forall i i > ret && i < a.length a[i] <= a[ret]))
        } else {
            // l >= -1 && r <= n && a == a' &&  
            // (r - l >= 2) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
            int mid = (l + r) / 2;
            // l >= -1 && r <= n && a == a' &&  
            // (r - l >= 2) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x) &&
            // ((mid = (l + r) / 2 >= (l + l + 2) / 2 = l + 1 && mid = (l + r) / 2 <= (r - 2 + r) / 2 = r - 1 <=>
            // <=> l < mid < r)
            if (a[mid] > x) {
                // l >= -1 && r <= n && a == a' &&  
                // (a[mid] > x) && (l < mid < r) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
                // из этого следует l >= -1 && r <= n && a == a' &&  
                // (r > mid) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
                // (forall i i >= 0 && i <= mid a[i] >= a[mid] > x) && (forall i i < n && i >= r a[i] <= x)
                ret = recursiveBinSearch(x, a, mid, r);
                // (forall i i >= 0 && i < ret a[i] > x) && a == a' &&  
                // ((ret == a.length) || (a[ret] <= x) && (forall i i > ret && i < a.length a[i] <= a[ret]))
            } else {
                // l >= -1 && r <= n && a == a' &&  
                // (a[mid] <= x) && (l < mid < r) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j])
                // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
                // из этого следует l >= -1 && r <= n && a == a' &&  
                // (mid > l) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= mid a[i] <= a[mid] <= x)
                ret = recursiveBinSearch(x, a, l, mid);
                // (forall i i >= 0 && i < ret a[i] > x) && a == a' &&  
                // ((ret == a.length) || (a[ret] <= x) && (forall i i > ret && i < a.length a[i] <= a[ret]))
            }
        }
        // (forall i i >= 0 && i < ret a[i] > x) && a == a' &&  
        // ((ret == a.length) || (a[ret] <= x) && (forall i i > ret && i < a.length a[i] <= a[ret]))
        return ret;
    }
    // Post:
    // (forall i i >= 0 && i < R a[i] > x) && a == a' &&  
    // ((R == a.length) || (a[R] <= x) && (forall i i > R && i < a.length a[i] <= a[R]))

    // Pre:
    // l >= -1 && r <= n &&
    // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
    // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
    private static int iterativeBinSearch(int x, int[] a, int l, int r) {
        // Inv:
        // l >= -1 && r <= n && a == a' &&  
        // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
        // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
        while (r - l > 1) {
            // l >= -1 && r <= n && a == a' &&  
            // (r - l >= 2) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
            int mid = (l + r) / 2;
            // l >= -1 && r <= n && a == a' &&  
            // (r - l >= 2) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x) &&
            // ((mid = (l + r) / 2 >= (l + l + 2) / 2 = l + 1 && mid = (l + r) / 2 <= (r - 2 + r) / 2 = r - 1 <=>
            // <=> l < mid < r)
            if (a[mid] > x) {
                // l >= -1 && r <= n && a == a' &&  
                // (a[mid] > x) && (l < mid < r) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
                // из этого следует l >= -1 && r <= n && a == a' &&  
                // (r > mid) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
                // (forall i i >= 0 && i <= mid a[i] >= a[mid] > x) && (forall i i < n && i >= r a[i] <= x)
                l = mid;
                // l >= -1 && r <= n && a == a' && 
                // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
            } else {
                // l >= -1 && r <= n && a == a' && 
                // (a[mid] <= x) && (l < mid < r) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j])
                // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
                // из этого следует l >= -1 && r <= n && a == a' &&  
                // (mid > l) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= mid a[i] <= a[mid] <= x)
                r = mid;
                // l >= -1 && r <= n && a == a' && 
                // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
            }
            // l >= -1 && r <= n && a == a' && 
            // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
        }
        // l >= -1 && r <= n && a == a' && 
        // (r == l + 1) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j]) &&
        // (forall i i >= 0 && i <= l a[i] > x) && (forall i i < n && i >= r a[i] <= x)
        // из этого следует l >= -1 && r <= n && a == a' && 
        // (forall i i >= 0 && i < r = l + 1 (т.е. i <= l) a[i] > x) &&
        // ((r == a.length) || (a[r] <= x) && (forall i i > r && i < a.length a[i] <= a[r]))
        return r;
    }
    // Post:
    // (forall i i >= 0 && i < R a[i] > x) && a == a' &&  
    // ((R == a.length) || (a[R] <= x) && (forall i i > R && i < a.length a[i] <= a[R]))

    // Pre:
    // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
    // args[0] – x из условия задачи && args[1:a.length] – невозрастающий массив из условия задачи в формате String
    public static void main(String[] args) {
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // args[0] – x из условия задачи && args[1:a.length] – невозрастающий массив из условия задачи в формате String
        int x = Integer.parseInt(args[0]);
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // x – x из условия задачи && args[1:a.length] – невозрастающий массив из условия задачи в формате String
        int n = args.length - 1;
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // x – x из условия задачи && args[1:a.length] – невозрастающий массив из условия задачи в формате String &&
        // n – длина массива из условия задачи
        int[] a = new int[n];
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // x – x из условия задачи && args[1:a.length] – невозрастающий массив из условия задачи в формате String &&
        // n – длина массива из условия задачи && a – заполненный 0 массив чисел int длины n
        int i = 0;
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // x – x из условия задачи && args[1:a.length] – невозрастающий массив из условия задачи в формате String &&
        // n – длина массива из условия задачи && a – заполненный 0 массив чисел int длины n && i == 0
        // Inv:
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // x – x из условия задачи && args[1:a.length] – невозрастающий массив из условия задачи в формате String &&
        // n – длина массива из условия задачи && Префикс длины i массива из условия совпадает с массивом a
        while (i < n) {
            // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
            // x – x из условия задачи && args[1:a.length] – невозрастающий массив из условия задачи в формате String &&
            // n – длина массива из условия задачи &&
            // (i < n) && Префикс длины i массива из условия совпадает с массивом a
            a[i] = Integer.parseInt(args[i + 1]);
            // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
            // x – x из условия задачи && args[1:a.length] – невозрастающий массив из условия задачи в формате String &&
            // n – длина массива из условия задачи &&
            // (i < n) && Префикс длины i + 1 массива из условия совпадает с массивом a
            i++;
            // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
            // x – x из условия задачи && args[1:a.length] – невозрастающий массив из условия задачи в формате String &&
            // n – длина массива из условия задачи &&
            // (i <= n) && Префикс длины i массива из условия совпадает с массивом a
        }
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 &&
        // x – x из условия задачи && a – невозрастающий массив из условия задачи в формате Integer
        // && n – длина массива из условия задачи
        // (i == n) && Префикс длины i массива из условия совпадает с массивом a <=>
        // <=> Массив a свопадает с массивом из условия
        // Из этого следует -1 >= -1 && n <= n &&
        // (n > -1) && (forall i,j 0 <= i <= j < a.length a[i] >= a[j])(т.к. невозрастающий) &&
        // (forall i i >= 0 && i <= l a[i] > x)(т.к. такого i нет) &&
        // (forall i i < n && i >= r a[i] <= x)(т.к. такого i нет)
        System.out.println(iterativeBinSearch(x, a, -1, n));
        // В стандартный поток вывода отправляется индекс R такой, что
        // a == a' && (forall i i >= 0 && i < R a[i] > x) &&
        // ((R == a.length) || (a[R] <= x) && (forall i i > R && i < a.length a[i] <= a[R]))
        // Т.е. минимальное значение индекса массива, при котором a[i] <= x
    }
    // Post:
    // В стандартный поток вывода отправляется индекс R – минимальное значение индекса массива, при котором a[i] <= x
}
