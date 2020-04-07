package cu.musala.gateway.service.impl;

import cu.musala.gateway.dto.DeviceDto;
import cu.musala.gateway.exception.EntityNotFoundException;
import cu.musala.gateway.exception.ValidationException;
import cu.musala.gateway.model.Device;
import cu.musala.gateway.model.Gateway;
import cu.musala.gateway.service.BaseService;
import cu.musala.gateway.service.DeviceService;
import org.springframework.stereotype.Service;

@Service
public class DeviceServiceImpl extends BaseService implements DeviceService {

    public DeviceDto save(Device newDevice, Long gatewayId) {
        Gateway gateway = gatewayRepository.findById(gatewayId).orElseThrow(() -> new EntityNotFoundException("Invalid Gateway Id", gatewayId));

        if(gateway.getDevices().size() >= 10)
            throw new ValidationException("No more than 10 devices are allowed per gateway");

        newDevice.setGateway(gateway);
        newDevice = deviceRepository.save(newDevice);
        return modelMapper.map(newDevice, DeviceDto.class);
    }

    public DeviceDto delete(Long deviceId) {
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new EntityNotFoundException("Invalid Device Id", deviceId));
        deviceRepository.delete(device);
        return modelMapper.map(device, DeviceDto.class);
    }
}
