public class PrinterI implements Demo.Printer
{
    public String printString(String s, com.zeroc.Ice.Current current)
    {
        // Input processing start time
        long startTime = System.nanoTime();

        int finalVal = 0;
        // fibonacci logic
        try {
		    String[] parts = s.split(":");
	    	int num = Integer.parseInt(parts[2]);
            System.out.print(parts[0] + ":" + parts[1] + ":");
    		finalVal = fib(num);
	    } catch (NumberFormatException nfe) {
	    	System.out.println(s);
    	}

        // Input processing end time
        long endTime = System.nanoTime();
        // Execution time in milliseconds
        double executionTimeMillis = (endTime - startTime) / 1_000_000.0;


        System.out.println("\nServer Service Execution Time: " + executionTimeMillis);

        return String.valueOf(executionTimeMillis) + ":" + String.valueOf(finalVal);

    }


    public static int fib(int n) {
        if (n <= 0){
            System.out.print(n);
        }

        int oldVal = 0;
        int newVal = 1;

        for(int i=0; i<n; i++){
            int temp = newVal;
            newVal = temp + oldVal;
            oldVal = temp;
            System.out.print(oldVal + " ");
        }

        return oldVal;
    }

}


