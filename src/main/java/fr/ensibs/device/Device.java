package fr.ensibs.device;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
* A device managed by the system, characterized by an identifier, a name,
* and having a status which value changes along the system lifetime.
*/
public interface Device extends Remote
{

  /**
  * Give the device unique id, assigned by the system and that
  * nevers changes afterwards
  *
  * @return the device unique id
  */
  String getId() throws RemoteException;

  /**
  * Set the device unique id. This method should be invoked only
  * once, and provide a system-wide unique id
  *
  * @param id the unique id assigned to the device
  * @throws IllegalStateException if an id has already been
  * assigned to this device
  */
  void setId(String id) throws IllegalStateException, RemoteException;

  /**
  * Give the device "human-readable" name
  *
  * @return the device name
  */
  String getName() throws RemoteException;

  /**
  * Give the device instantaneous status
  *
  * @return the device status
  */
  Status getStatus() throws RemoteException;

  /**
  * Method invoked when the instantaneous status of a device has been updated
  *
  * @param deviceId the device id
  * @param status the new status of the device
  */
  void statusUpdated(String deviceId, Status status) throws RemoteException;
}
