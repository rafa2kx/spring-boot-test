package cu.musala.gateway.service;

import cu.musala.gateway.dto.DeviceDto;
import cu.musala.gateway.model.Device;

public interface DeviceService {

    DeviceDto save(Device newDevice, Long gatewayId);

    DeviceDto delete(Long deviceId);
}
