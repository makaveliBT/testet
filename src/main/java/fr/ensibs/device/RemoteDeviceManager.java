package fr.ensibs.device;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.rmi.RemoteException;

/**
* A manager that registers devices in the system, and assign them
* unique identifiers and maintains informations about their names and
* status. This class is intended to be deplayed in a distributed application
* and can be accessed remotely
*/
public class RemoteDeviceManager implements DeviceManager
{

  /**
  * the next id that can be assigned to a device
  */
  private int nextId;

  /**
  * A map that associates device ids to devices
  */
  private Map<String,Device> devices = new HashMap<>();

  /**
  * Default constructor
  */
  public RemoteDeviceManager() throws RemoteException
  {
    super();
  }

  @Override
  public String register(Device device) throws RemoteException
  {
    String id = Integer.toString(nextId++);
    device.setId(id);
    this.devices.put(id, device);
    return id;
  }

  @Override
  public void unregister(String deviceId)
  {
    this.devices.remove(deviceId);
  }

  @Override
  public Set<String> getRegisteredIds() throws RemoteException
  {
    return this.devices.keySet();
  }

  @Override
  public Status getStatus(String deviceId) throws RemoteException
  {
    Device device = this.devices.get(deviceId);
    if (device != null) {
      return device.getStatus();
    }
    return null;
  }

  @Override
  public String getName(String deviceId) throws RemoteException
  {
    Device device = this.devices.get(deviceId);
    if (device != null) {
      return device.getName();
    }
    return null;
  }

  @Override
  public void statusUpdated(String deviceId) throws RemoteException
  {
    Status status = devices.get(deviceId).getStatus();
    for (Device device : devices.values()) {
      if (!device.getId().equals(deviceId)) {
        device.statusUpdated(deviceId, status);
      }
    }
  }

}
