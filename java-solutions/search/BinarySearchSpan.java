package search;

public class BinarySearchSpan {
    // Pre:
    // l >= -1 && r <= a.length &&
    // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
    // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
    private static int recursiveBinSearch(int x, int[] a, int l, int r) {
        // l >= -1 && r <= a.length && a == a' &&
        // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
        // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
        int ret;
        // l >= -1 && r <= a.length && a == a' &&
        // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
        // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
        if (r - l == 1) {
            // l >= -1 && r <= a.length && a == a' &&
            // (r == l + 1) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
            // из этого следует l >= -1 && r <= a.length && a == a' &&
            // (forall i i >= 0 && i < r = l + 1 (т.е. i <= l) a[i] < x) &&
            // ((r == a.length) || (a[r] >= x) && (forall i i > r && i < a.length a[i] >= a[r]))
            ret = r;
            // a == a' &&  (forall i i >= 0 && i < ret = l + 1 (т.е. i <= l) a[i] < x) &&
            // ((ret == a.length) || (a[ret] >= x) && (forall i i > ret && i < a.length a[i] >= a[ret]))
        } else {
            // l >= -1 && r <= a.length && a == a' &&
            // (r - l >= 2) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
            int mid = l + (r - l) / 2;
            // l >= -1 && r <= a.length && a == a' &&
            // (r - l >= 2) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x) &&
            // (mid = l + (r - l) / 2 = (l + r) / 2 >= (l + l + 2) / 2 = l + 1 &&
            // mid = l + (r - l) / 2 = (l + r) / 2 <= (r - 2 + r) / 2 = r - 1 <=>
            // <=> l < mid < r)
            if (a[mid] < x) {
                // l >= -1 && r <= a.length && a == a' &&
                // (a[mid] < x) && (l < mid < r) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
                // из этого следует l >= -1 && r <= a.length && a == a' &&
                // (r > mid) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
                // (forall i i >= 0 && i <= mid a[i] <= a[mid] < x) && (forall i i < n && i >= r a[i] >= x)
                ret = recursiveBinSearch(x, a, mid, r);
                // (forall i i >= 0 && i < ret a[i] < x) && a == a' &&
                // ((ret == a.length) || (a[ret] >= x) && (forall i i > ret && i < a.length a[i] >= a[ret]))
            } else {
                // l >= -1 && r <= a.length && a == a' &&
                // (a[mid] >= x) && (l < mid < r) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j])
                // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
                // из этого следует l >= -1 && r <= a.length && a == a' &&
                // (mid > l) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= mid a[i] >= a[mid] >= x)
                ret = recursiveBinSearch(x, a, l, mid);
                // (forall i i >= 0 && i < ret a[i] < x) && a == a' &&
                // ((ret == a.length) || (a[ret] >= x) && (forall i i > ret && i < a.length a[i] >= a[ret]))
            }
        }
        // (forall i i >= 0 && i < ret a[i] < x) && a == a' &&
        // ((ret == a.length) || (a[ret] >= x) && (forall i i > ret && i < a.length a[i] >= a[ret]))
        return ret;
    }
    // Post:
    // (forall i i >= 0 && i < R a[i] < x) && a == a' &&
    // ((R == a.length) || (a[R] >= x) && (forall i i > R && i < a.length a[i] >= a[R]))

    // Pre:
    // l >= -1 && r <= a.length &&
    // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
    // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
    private static int iterativeBinSearch(int x, int[] a, int l, int r) {
        // Inv:
        // l >= -1 && r <= a.length && a == a' &&
        // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
        // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
        while (r - l > 1) {
            // l >= -1 && r <= a.length && a == a' &&
            // (r - l >= 2) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
            int mid = l + (r - l) / 2;
            // l >= -1 && r <= a.length && a == a' &&
            // (r - l >= 2) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x) &&
            // (mid = l + (r - l) / 2 = (l + r) / 2 >= (l + l + 2) / 2 = l + 1 &&
            // mid = l + (r - l) / 2 = (l + r) / 2 <= (r - 2 + r) / 2 = r - 1 <=>
            // <=> l < mid < r)
            if (a[mid] < x) {
                // l >= -1 && r <= a.length && a == a' &&
                // (a[mid] < x) && (l < mid < r) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
                // из этого следует l >= -1 && r <= a.length && a == a' &&
                // (r > mid) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
                // (forall i i >= 0 && i <= mid a[i] <= a[mid] < x) && (forall i i < n && i >= r a[i] >= x)
                l = mid;
                // l >= -1 && r <= a.length && a == a' &&
                // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
            } else {
                // l >= -1 && r <= a.length && a == a' &&
                // (a[mid] >= x) && (l < mid < r) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j])
                // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
                // из этого следует l >= -1 && r <= a.length && a == a' &&
                // (mid > l) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= mid a[i] >= a[mid] >= x)
                r = mid;
                // l >= -1 && r <= a.length && a == a' &&
                // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
                // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
            }
            // l >= -1 && r <= a.length && a == a' &&
            // (r > l) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
            // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
        }
        // l >= -1 && r <= a.length && a == a' &&
        // (r == l + 1) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) &&
        // (forall i i >= 0 && i <= l a[i] < x) && (forall i i < n && i >= r a[i] >= x)
        // из этого следует l >= -1 && r <= a.length && a == a' &&
        // (forall i i >= 0 && i < r = l + 1 (т.е. i <= l) a[i] < x) &&
        // ((r == a.length) || (a[r] >= x) && (forall i i > r && i < a.length a[i] >= a[r]))
        return r;
    }
    // Post:
    // (forall i i >= 0 && i < R a[i] < x) && a == a' &&
    // ((R == a.length) || (a[R] >= x) && (forall i i > R && i < a.length a[i] >= a[R]))

    // Pre: x - x из условия && a - массив из условия – неубывающий массив чисел типа int
    private static void recursiveBinSearchSpan(int x, int[] a) {
        // x - x из условия && a - массив из условия – неубывающий массив чисел типа int
        // из этого следует: -1 >= -1 && a.length <= a.length &&
        // (a.length > -1) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) (т.к. неубывающий) &&
        // (forall i i >= 0 && i <= -1 a[i] < x)  && (forall i i < a.length && i >= r a[i] >= x)
        int left_border = recursiveBinSearch(x, a, -1, a.length);
        // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
        // ((left_border == a.length) || (a[left_border] >= x)
        // && (for i i > left_border && i < a.length a[i] >= a[left_border]))
        int right_border;
        // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
        // ((left_border == a.length) || (a[left_border] >= x)
        // && (for i i > left_border && i < a.length a[i] >= a[left_border]))
        if (x == Integer.MAX_VALUE) {
            // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
            // ((left_border == a.length) || (a[left_border] >= x) &&
            // (for i i > left_border && i < a.length a[i] >= a[left_border])) && x == Integer.MAX_VALUE
            // Из этого следует: forall i i >= 0 i < n a[i] <= x
            right_border = a.length;
            // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
            // ((left_border == a.length) || (a[left_border] >= x) &&
            // (for i i > left_border && i < a.length a[i] >= a[left_border])) &&
            // (forall i i >= 0 i < right_border a[i] <= x) && (forall i i >= right_border i < n a[i] > x)

        } else {
            // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
            // ((left_border == a.length) || (a[left_border] >= x) &&
            // (for i i > left_border && i < a.length a[i] >= a[left_border])) && x != Integer.MAX_VALUE
            // Из этого следует:
            // из этого следует: -1 >= -1 && a.length <= a.length &&
            // (a.length > -1) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) (т.к. тот же неубывающий) &&
            // (forall i i >= 0 && i <= -1 a[i] < x + 1)  && (forall i i < a.length && i >= r a[i] >= x + 1)
            right_border = recursiveBinSearch(x + 1, a, -1, a.length);
            // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
            // ((left_border == a.length) || (a[left_border] >= x) &&
            // (for i i > left_border && i < a.length a[i] >= a[left_border])) &&
            // (forall i i >= 0 && i < right_border a[i] < x + 1 (т.е. a[i] <= x)) && a == a' &&
            // ((right_border == a.length) || (a[right_border] >= x + 1 (т.е. a[i] > x)) &&
            // (forall i i > right_border && i < a.length a[i] >= a[right_border] > x))
        }
        // a == a' && (forall i i >= 0 && i < left_border a[i] < x) &&
        // (forall i i >= left_border && i < a.length a[i] >= x) &&
        // (forall i i >= 0 && i < right_border a[i] <= x) &&
        // (forall i i >= right_border && i < a.length a[i] > x)
        // Из этого следует
        // (forall i i < left_border && i >= 0 a[i] < x) &&
        // (forall i i >= left_border && i < left_border + (right_border - left_border) = right_border a[i] == x
        // (т.к. a[i] >= x и a[i] <= x)) &&
        // (forall i i >= left_border + (right_border - left_border) = right_border && i < a.length a[i] > x) && a == a'
        System.out.println(left_border + " " + (right_border - left_border));
        // в стандартный поток вывода отправятся числа st = left_border и len = right_border - left_border, что
        // (forall i i < st && i >= 0 a[i] < x) && (forall i i >= st && i < st + len a[i] == x) &&
        // (forall i i >= st + len && i < a.length a[i] > x) && a == a'
    }
    // Post: в стандартный поток вывода отправятся числа st и len, что
    // (forall i i < st && i >= 0 a[i] < x) && (forall i i >= st && i < st + len a[i] == x) &&
    // (forall i i >= st + len && i < a.length a[i] > x) && a == a'

    // Pre: x - x из условия && a - массив из условия – неубывающий массив чисел типа int
    private static void iterativeBinSearchSpan(int x, int[] a) {
        // x - x из условия && a - массив из условия – неубывающий массив чисел типа int
        // из этого следует: -1 >= -1 && a.length <= a.length &&
        // (a.length > -1) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) (т.к. неубывающий) &&
        // (forall i i >= 0 && i <= -1 a[i] < x)  && (forall i i < a.length && i >= r a[i] >= x)
        int left_border = iterativeBinSearch(x, a, -1, a.length);
        // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
        // ((left_border == a.length) || (a[left_border] >= x)
        // && (for i i > left_border && i < a.length a[i] >= a[left_border]))
        int right_border;
        // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
        // ((left_border == a.length) || (a[left_border] >= x)
        // && (for i i > left_border && i < a.length a[i] >= a[left_border]))
        if (x == Integer.MAX_VALUE) {
            // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
            // ((left_border == a.length) || (a[left_border] >= x) &&
            // (for i i > left_border && i < a.length a[i] >= a[left_border])) && x == Integer.MAX_VALUE
            // Из этого следует: forall i i >= 0 i < n a[i] <= x
            right_border = a.length;
            // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
            // ((left_border == a.length) || (a[left_border] >= x) &&
            // (for i i > left_border && i < a.length a[i] >= a[left_border])) &&
            // (forall i i >= 0 i < right_border a[i] <= x) && (forall i i >= right_border i < n a[i] > x)
        } else {
            // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
            // ((left_border == a.length) || (a[left_border] >= x) &&
            // (for i i > left_border && i < a.length a[i] >= a[left_border])) && x != Integer.MAX_VALUE
            // Из этого следует:
            // из этого следует: -1 >= -1 && a.length <= a.length &&
            // (a.length > -1) && (forall i,j 0 <= i <= j < a.length a[i] <= a[j]) (т.к. тот же неубывающий) &&
            // (forall i i >= 0 && i <= -1 a[i] < x + 1)  && (forall i i < a.length && i >= r a[i] >= x + 1)
            right_border = iterativeBinSearch(x + 1, a, -1, a.length);
            // (forall i i >= 0 && i < left_border a[i] < x) && a == a' &&
            // ((left_border == a.length) || (a[left_border] >= x) &&
            // (for i i > left_border && i < a.length a[i] >= a[left_border])) &&
            // (forall i i >= 0 && i < right_border a[i] < x + 1 (т.е. a[i] <= x)) && a == a' &&
            // ((right_border == a.length) || (a[right_border] >= x + 1 (т.е. a[i] > x)) &&
            // (forall i i > right_border && i < a.length a[i] >= a[right_border] > x))
        }
        // a == a' && (forall i i >= 0 && i < left_border a[i] < x) &&
        // (forall i i >= left_border && i < a.length a[i] >= x) &&
        // (forall i i >= 0 && i < right_border a[i] <= x) &&
        // (forall i i >= right_border && i < a.length a[i] > x)
        // Из этого следует
        // (forall i i < left_border && i >= 0 a[i] < x) &&
        // (forall i i >= left_border && i < left_border + (right_border - left_border) = right_border a[i] == x
        // (т.к. a[i] >= x и a[i] <= x)) &&
        // (forall i i >= left_border + (right_border - left_border) = right_border && i < a.length a[i] > x) && a == a'
        System.out.println(left_border + " " + (right_border - left_border));
        // в стандартный поток вывода отправятся числа st = left_border и len = right_border - left_border, что
        // (forall i i < st && i >= 0 a[i] < x) && (forall i i >= st && i < st + len a[i] == x) &&
        // (forall i i >= st + len && i < a.length a[i] > x) && a == a'
    }
    // Post: в стандартный поток вывода отправятся числа st и len, что
    // (forall i i < st && i >= 0 a[i] < x) && (forall i i >= st && i < st + len a[i] == x) &&
    // (forall i i >= st + len && i < a.length a[i] > x) && a == a'

    // Pre:
    // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
    // args[0] – x из условия задачи && args[1:a.length] – неубывающий массив из условия задачи в формате String
    public static void main(String[] args) {
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // args[0] – x из условия задачи && args[1:a.length] – неубывающий массив из условия задачи в формате String
        int x = Integer.parseInt(args[0]);
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // x – x из условия задачи && args[1:a.length] – неубывающий массив из условия задачи в формате String
        int n = args.length - 1;
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // x – x из условия задачи && args[1:a.length] – неубывающий массив из условия задачи в формате String &&
        // n – длина массива из условия задачи
        int[] a = new int[n];
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // x – x из условия задачи && args[1:a.length] – неубывающий массив из условия задачи в формате String &&
        // n – длина массива из условия задачи && a – заполненный 0 массив чисел int длины n
        int i = 0;
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // x – x из условия задачи && args[1:a.length] – неубывающий массив из условия задачи в формате String &&
        // n – длина массива из условия задачи && a – заполненный 0 массив чисел int длины n && i == 0
        // Inv:
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
        // x – x из условия задачи && args[1:a.length] – неубывающий массив из условия задачи в формате String &&
        // n – длина массива из условия задачи && Префикс длины i массива из условия совпадает с массивом a
        while (i < n) {
            // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
            // x – x из условия задачи && args[1:a.length] – неубывающий массив из условия задачи в формате String &&
            // n – длина массива из условия задачи &&
            // (i < n) && Префикс длины i массива из условия совпадает с массивом a
            a[i] = Integer.parseInt(args[i + 1]);
            // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
            // x – x из условия задачи && args[1:a.length] – неубывающий массив из условия задачи в формате String &&
            // n – длина массива из условия задачи &&
            // (i < n) && Префикс длины i + 1 массива из условия совпадает с массивом a
            i++;
            // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 в строке &&
            // x – x из условия задачи && args[1:a.length] – неубывающий массив из условия задачи в формате String &&
            // n – длина массива из условия задачи &&
            // (i <= n) && Префикс длины i массива из условия совпадает с массивом a
        }
        // forall i i >= 0 && i < args.length a[i] – число в формате int c основанием системы счисления 10 &&
        // x – x из условия задачи && args[1:a.length] – неубывающий массив из условия задачи в формате String &&
        // n – длина массива из условия задачи &&
        // ((i == n) && Префикс длины i массива из условия совпадает с массивом a <=>
        // <=> Массив a свопадает с массивом из условия)
        iterativeBinSearchSpan(x, a);
        // в стандартный поток вывода отправятся числа st и len, что
        // (forall i i < st && i >= 0 a[i] < x) && (forall i i >= st && i < st + len a[i] == x) &&
        // (forall i i >= st + len && i < a.length a[i] > x) && a == a'
    }
    // Post: в стандартный поток вывода отправятся числа st и len, что
    // (forall i i < st && i >= 0 a[i] < x) && (forall i i >= st && i < st + len a[i] == x) &&
    // (forall i i >= st + len && i < a.length a[i] > x)
}
