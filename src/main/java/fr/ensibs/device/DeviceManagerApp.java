package fr.ensibs.device;

import java.io.Closeable;
import java.util.Scanner;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
* A server application that hosts and exports a {@link DeviceManager} servant.
*/
public class DeviceManagerApp
{

  /**
  * the name of the remote object exported in the name service
  */
  private static final String REMOTE_MANAGER = "MANAGER";

  /**
  * Print a usage message and exit. Used when the standalone
  * application is started with wrong command line arguments
  */
  private static void usage()
  {
    System.out.println("Usage: java DeviceManagerApp <server_port>");
    System.out.println("A server application that exports a device manager");
    System.exit(-1);
  }

  /**
  * The entry point for a standalone application. Creates a name service
  * and a device manager and export the device manager to the name service
  */
  public static void main(String[] args) throws Exception
  {
    if (args.length != 1 || args[0].equals("-h")) {

      usage();
    }

    int port = 0;
    try {
      port = Integer.parseInt(args[0]);
    } catch (NumberFormatException e) {
      usage();
    }

    // create the name service
    Registry registry = LocateRegistry.createRegistry(port);

    // create and export the remote object
    DeviceManager deviceManager = new RemoteDeviceManager();
    DeviceManager stub = (DeviceManager) UnicastRemoteObject.exportObject(deviceManager, 0);
    registry.rebind(REMOTE_MANAGER, stub);
  }

}
