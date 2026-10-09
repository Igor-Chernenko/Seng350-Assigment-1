public class Client
{
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.client",extraArgs))
        {
            //com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("SimplePrinter:default -p 10000");
            Demo.PrinterPrx twoway = Demo.PrinterPrx.checkedCast(
                communicator.propertyToProxy("Printer.Proxy")).ice_twoway().ice_secure(false);
            //Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(base);
            Demo.PrinterPrx printer = twoway;

            if(printer == null)
            {
                throw new Error("Invalid proxy");
            }

            java.util.Scanner scanner = new java.util.Scanner(System.in);

            String username = System.getProperty("user.name");
            String hostname;

            try
            {
                hostname = java.net.InetAddress.getLocalHost().getHostName();
            }
            catch(Exception e)
            {
                hostname = "unknown";
            }

            while(true)
            {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                if (message.isEmpty()){
                    continue;
                };

                if(message.equalsIgnoreCase("exit"))
                {
                    break;
                }

                // Method invocation start time
                long startTime = System.nanoTime();

                // Reply is the number of milliseconds the server spent processing. E.g., 23.013221
                String reply = printer.printString(username + ":" + hostname + ":" + message);

                for (int i=0; i<7; i++){
                    reply = printer.printString(username + ":" + hostname + ":" + message);
                }   // Call 7 more times, all with the same number

                // Responce receive time
                long endTime = System.nanoTime();

                String[] replyPart = reply.split(":");

                int finalVal = Integer.parseInt(replyPart[1]);

                double serverDelay = Double.valueOf(replyPart[0]);
                double totalClientDelay = (endTime - startTime) / 1_000_000.0;
                double networkDelay = (totalClientDelay - serverDelay);

                System.out.println(username + ":" + hostname + ":" + message);
                System.out.println("Server Service Execution Time: " + serverDelay);
                System.out.println("Client Invocation & Response Reception Time: " + totalClientDelay);
                System.out.println("Network and Middleware Transmission Time: " + networkDelay);
                System.out.println("Total End-to-End Elapsed Time: " + totalClientDelay);
                System.out.println(""); // newline
            }
            scanner.close();
        }
    }
}
