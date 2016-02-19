import java.util.*;
import java.util.concurrent.atomic.*;

class ParalellSort extends SequentialSort implements Runnable
{

	//minste av de 40 største tallene
	static AtomicInteger t;

	static Integer threadsFinished = 0;

	public static void Sort40(int[] a, int additionalCores)
	{
		insertSort(a, 0, 39);

		t = new AtomicInteger(a[39]);

		int n = a.length - 40;
		int nPerThread = n / additionalCores; 

		int start = 40;
		int end = start + nPerThread;

		ParalellSort[] tasks = new ParalellSort[additionalCores];
		Thread[] threads = new Thread[additionalCores];
		for(int i = 0; i < additionalCores; i++)
		{
			tasks[i] = new ParalellSort(i, a, start, end);
			start = end;
			end += nPerThread;
			if(a.length - end < nPerThread)
				end = a.length - 1;

			threads[i] = new Thread(tasks[i]);
		}

		double startTime = System.currentTimeMillis();

		for(int i = 0; i < additionalCores; i++)
		{
			threads[i].start();
		}


		Stack<Integer> candidates = new Stack<Integer>();

		boolean finished = false;
		while(!finished)
		{
			synchronized(threadsFinished)
			{
				finished = threadsFinished == additionalCores;
			}

			for(int i = 0; i < additionalCores; i++)
			{
				while(tasks[i].candidates.size > 0)
				{
					candidates.push(tasks[i].candidates.Pop());
				}
			}

			while(!candidates.empty())
			{
				int candidate = candidates.pop();
				if(candidate > t.get())
				{
					a[39] = candidate;
					simpleInsertSort(a, 0, 39);
					t.lazySet(a[39]);
					System.out.printf("t = %d\n", a[39]);
				}
				else
				{
					System.out.println(candidate);
				}
			}
		}

		System.out.println("finished thread main in " + (System.currentTimeMillis() - startTime));
	}

	public static long Time(int[] a, int additionalCores, int runs)
	{
		long[] times = new long[runs];
		long start, end;

		int[] aa  = new int[runs];
		System.arraycopy( a, 0, aa, 0, a.length );
		for(int i = 0; i < runs; i++)
		{
			start = System.nanoTime();
			Sort40(a, additionalCores);
			end = System.nanoTime();
			times[i] = end - start;
		}

		insertSort(times, 0, runs);
		return times[(times.length - 1) / 2];
	}

	public int ID;
	public AtomicStack<Integer> candidates;

	int[] a;
	int start, end;

	public ParalellSort(int ID, int[] a, int start, int end)
	{
		this.ID = ID;
		this.a = a;
		this.start = start;
		this.end = end;

		candidates = new AtomicStack<Integer>();
	}

	public void run()
	{
		double startTime = System.currentTimeMillis();

		for(int i = start; i < end; i++)
		{
			if(a[i] > t.get())
			{
				candidates.Push(a[i]);
			}
		}

		System.out.println("finished thread " + ID + " int " + (System.currentTimeMillis() - startTime));
		synchronized(threadsFinished)
		{
			threadsFinished++;
		}
	}
}