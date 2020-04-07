package cu.musala.gateway;

import com.github.javafaker.Faker;
import cu.musala.gateway.model.Device;
import cu.musala.gateway.model.Gateway;
import org.junit.Before;

import java.util.ArrayList;

public class BaseTests {

    protected Faker faker;

    @Before
    public void setUp() {

        faker = new Faker();
    }

    /**
     * @param devicesCount
     * @return
     */
    protected Gateway generateGatewayWithDevices(int devicesCount) {

        Gateway gateway = new Gateway();
        gateway.setName(faker.funnyName().name());
        gateway.setId(Long.valueOf(faker.number().randomNumber()));
        gateway.setIpV4(faker.internet().ipV4Address());
        gateway.setSerialNumber(String.valueOf(faker.number().randomNumber()));
        ArrayList<Device> devices = new ArrayList<Device>();

        for (int i = 0; i < devicesCount; i++) {
            Device device = new Device();
            device.setCreatedDate(faker.date().birthday());
            device.setOnLine(faker.bool().bool());
            device.setUid(faker.number().randomNumber());
            device.setVendor(faker.company().name());
            devices.add(device);
        }
        gateway.setDevices(devices);

        return gateway;
    }

    /**
     * @return
     */
    protected Device generateDevice() {

        Device device = new Device();
        device.setCreatedDate(faker.date().birthday());
        device.setOnLine(faker.bool().bool());
        device.setUid(faker.number().randomNumber());
        device.setVendor(faker.company().name());
        return device;
    }
}
