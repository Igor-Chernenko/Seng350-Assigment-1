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

                if(message.equalsIgnoreCase("exit"))
                {
                    break;
                }

                printer.printString(username + ":" + hostname + ":" + message);
            }

            scanner.close();
        }
    }
}
