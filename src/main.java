import java.util.*;

public class Main
{
	public static void main(String[] args)
	{
		Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
		int[] runs = {500, 1000, 10000, 100000, 1000000, 10000000, 100000000};
		long[] paralellTimes = new long[runs.length];
		long[] seqTimes = new long[runs.length];

		int[] list;//= randList(500000);

		//SequentialSort.Sort(list);
		//ParalellSort.Sort(list, 4);

		//System.out.println(Arrays.toString(list));
		/*
		System.out.println(list.length);
		for(int i = 0; i < list.length; i++)
		{
			if(list[i] > 0)
			{
				System.out.println(list[i]);
			}
		}*/

		System.out.printf("	N	SequentialTime 	ParalellTime 	speedup\n");
		for(int i = 0; i < runs.length; i++)
		{
			list = randList(runs[i]);

			paralellTimes[i] = ParalellSort.Time(list, 4, 9);
			seqTimes[i] = SequentialSort.Time(list, 9);

			if(checkList(list))
			{
				//System.out.println("AOK");
			}
			else
			{
				System.out.println("List Not Okay");
			}

			System.out.printf("%10d	%fms	%fms	%f\n", runs[i], (paralellTimes[i]/1000000.f), (seqTimes[i]/1000000.f), (seqTimes[i]/(float)paralellTimes[i]));
		}


	}

	static Random rand = new Random(42);//System.currentTimeMillis());
	static int[] randList(int lentgth)
	{
		int[] list = new int[lentgth];
		for(int i = 0; i < lentgth; i++)
		{
			list[i] = Math.abs(rand.nextInt() % 100);
		}

		for(int i = 0; i < 40; i++)
		{
			int s = Math.abs(rand.nextInt()) % (lentgth - 1);
			while(list[s] >= 100 || list[s] == 0)
				s = Math.abs(rand.nextInt()) % (lentgth - 1);

			list[s] *= 100;
			//System.out.println(list[s]);
		}

		return list;
	}

	public static void print40(int[] a, int start)
	{
		int end = start + 40;
		for(int i = start; i < end; i++)
		{
			System.out.printf("%d: %d\n", i, a[i]);
		}
	}

	static boolean checkList(int[] a)
	{
		int t = a[39];
		for(int i = 40; i < a.length; i++)
		{
			if(a[i] > t)
				return false;
		}

		return true;
	}
}