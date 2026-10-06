public class PrinterI implements Demo.Printer
{
    public void printString(String s, com.zeroc.Ice.Current current)
    {
        try {
		String[] parts = s.split(":");
		int num = Integer.parseInt(parts[2]);
		int finalVal = fib(num);
        System.out.println(finalVal);
	} catch (NumberFormatException nfe) {
		System.out.println("error");
	}
	System.out.println(s);
    }

    public static int fib(int n) {
        int oldVal = 0;
        int newVal = 1;

        for(int i=0; i<n; i++){
            int temp = newVal;
            newVal = temp + oldVal;
            oldVal = temp;
        }

        return oldVal;
    }

}


