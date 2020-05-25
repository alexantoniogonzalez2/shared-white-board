package remote;

import java.awt.*;
import java.rmi.RemoteException;

public interface RemoteListener {
    void temperatureChanged(Shape shape) throws RemoteException;
}
