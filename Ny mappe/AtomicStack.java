import java.util.*;

class AtomicStack<T>
{
	public volatile int size = 0;

	Stack<T> stack;

	public AtomicStack()
	{
		stack = new Stack<T>();
	}

	public void Push(T item)
	{
		synchronized(this)
		{
			stack.push(item);
			size++;
		}
	}

	public T Pop()
	{
		synchronized(this)
		{	
			size--;
			if(size < 0)
			{
				System.out.println("!!!!!!\n!!!!!   size < 0 !!!!!!\n!!!!!!!!!!!!!!!111");
			}
			return stack.pop();
		}
	}
}