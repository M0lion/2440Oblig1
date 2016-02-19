public class SequentialSort
{
	public static void Sort40(int[] a)
	{
		int t;
		insertSort(a, 0, 39);
		for(int i = 40; i < a.length; i++)
		{
			if(a[i] > a[39])
			{
				t = a[39];
				a[39] = a[i];
				a[i] = t;
				simpleInsertSort(a, 0, 39);
			}
		}
	}

	/** Denne sorterer a[v..h] i synkende rekkefølge med innstikk-algoritmen*/
	static void insertSort (int [] a, int v, int h) {
		int i , t ;
		for ( int k = v ; k < h ; k++) {
			 // invariant: a[v..k] er nå sortert synkende (største først)
			t = a [k+1] ;
			i = k ;

			while ( i >= v && a [i] < t ) 
			{
				a [i +1] = a [i] ;
				i--;
			}

			a[i+1] = t ;
		}
	}
	static void insertSort (long [] a, long v, long h) {
		long i , t ;
		for ( long k = v ; k < h ; k++) {
			 // invariant: a[v..k] er nå sortert synkende (største først)
			t = a [(int)(k+1)] ;
			i = k ;

			while ( i >= v && a [(int)i] < t ) 
			{
				a [(int)i+1] = a [(int)i] ;
				i--;
			}

			a[(int)i+1] = t ;
		}
	}

	static void simpleInsertSort(int[] a, int start, int i)
	{
		int t = a[i];
		i--;
		while(i > start && a[i] < t)
		{
			a[i+1] = a[i];
			i--;
		}

		a[i] = t;
	}

	public static long Time(int[] a, int runs)
	{
		long[] times = new long[runs];
		long start, end;

		int[] aa  = new int[runs];
		System.arraycopy( a, 0, aa, 0, a.length );
		for(int i = 0; i < runs; i++)
		{
			start = System.nanoTime();
			Sort40(a);
			end = System.nanoTime();
			times[i] = end - start;
		}

		insertSort(times, 0, runs);
		return times[(times.length - 1) / 2];
	}
}