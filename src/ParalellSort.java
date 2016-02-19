import java.util.*;
import java.util.concurrent.atomic.*;

class ParalellSort extends SequentialSort implements Runnable
{
	public static void Sort(int[] a, int cores)
	{
		int nPerThread = a.length / cores; 

		int start = 0;
		int end = nPerThread;

		ParalellSort[] tasks = new ParalellSort[cores];
		Thread[] threads = new Thread[cores];
		for(int i = 0; i < cores; i++)
		{
			tasks[i] = new ParalellSort(i, a, start, end);
			start = end;
			end += nPerThread;
			if(a.length - end < nPerThread)
				end = a.length - 1;

			threads[i] = new Thread(tasks[i]);
		}

		for(int i = 1; i < cores; i++)
		{
			threads[i].start();
		}

		tasks[0].run();

		try
		{
			for(int i = 1; i < cores; i++)
			{
				threads[i].join();
			}
		}
		catch(InterruptedException e)
		{
			System.out.printf("%s\n", e.toString());
		}

		int writeHead = 40;
		int temp;
		for(int i = 1; i < cores; i++)
		{
			for(int j = 0; j < 40; j++)
			{
				temp = a[writeHead];
				a[writeHead] = a[j * i];
				a[j * i] = temp;
				writeHead++;
			}
		}

		long t = System.nanoTime();
		Sort40(a, 0, 40 * cores);
		System.out.printf("%fms\n", (System.nanoTime() - t)/1000000.f);

		//System.out.println("finished thread main in " + (System.currentTimeMillis() - startTime));
	}

	public static long Time(int[] a, int cores, int runs)
	{
		long[] times = new long[runs];
		long start, end;

		int[] aa  = new int[a.length];
		System.arraycopy( a, 0, aa, 0, a.length );
		for(int i = 0; i < runs; i++)
		{
			start = System.nanoTime();
			Sort(a, cores);
			end = System.nanoTime();
			times[i] = end - start;
		}

		insertSort(times, 0, runs - 1);
		return times[(times.length - 1) / 2];
	}

	public int ID;

	int[] a;
	int start, end;

	public ParalellSort(int ID, int[] a, int start, int end)
	{
		this.ID = ID;
		this.a = a;
		this.start = start;
		this.end = end;
	}

	public void run()
	{
		Sort40(a, start, end);
	}
}