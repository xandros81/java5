

import org.usb4java.ConfigDescriptor;
import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceDescriptor;
import org.usb4java.DeviceList;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

/**
 * Simply lists all available USB devices.
 * 
 * @author Klaus Reimer <k@ailis.de>
 */
public class ListDevices
{
	/**
	 * Main method.
	 * 
	 * @param args
	 *            Command-line arguments (Ignored)
	 */
	public static void main(String[] args)
	{
		// Create the libusb context
		Context context = new Context();
//priva git
		// Initialize the libusb context
		int result = LibUsb.init(context);
		// int result1 = LibUsb.init(context);
		if (result < 0)
		{
			throw new LibUsbException("Unable to initialize libusb", result);
		}
		ConfigDescriptor cfg = new ConfigDescriptor();
		// Read the USB device list
		DeviceList list = new DeviceList();
		result = LibUsb.getDeviceList(context, list);
		if (result < 0)
		{
			throw new LibUsbException("Unable to get device list", result);
		}

		try
		{
			// Iterate over all devices and list them
			for (Device device: list)
			{
				int address = LibUsb.getDeviceAddress(device);
				int busNumber = LibUsb.getBusNumber(device);
				DeviceDescriptor descriptor = new DeviceDescriptor();
				
				result = LibUsb.getDeviceDescriptor(device, descriptor);



				if (result < 0)
				{
					throw new LibUsbException(
							"Unable to read device descriptor", result);
				}
				System.out.format(
						"Bus %03d, Device %03d: Vendor %04x, Product %04x%n" ,
						busNumber, address, descriptor.idVendor(),
						descriptor.idProduct());


				
				
				

			}


			for (Device device: list)
			{

			
				result = LibUsb.getActiveConfigDescriptor(device, cfg);    

				if (result < 0)
				{
					throw new LibUsbException(
							"Unable to read configuration descriptor", result);
				}
				
				System.out.format(cfg.toString());
				System.out.format(cfg.toString());

			}
		}
		finally
		{
			// Ensure the allocated device list is freed
			LibUsb.freeDeviceList(list, true);
			//LibUsb.freeConfigDescriptor(cfg);
		}

		// Deinitialize the libusb context
		LibUsb.exit(context);
	}
}