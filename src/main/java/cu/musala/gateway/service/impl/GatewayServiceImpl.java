package cu.musala.gateway.service.impl;

import cu.musala.gateway.dto.GatewayDto;
import cu.musala.gateway.exception.EntityNotFoundException;
import cu.musala.gateway.model.Device;
import cu.musala.gateway.model.Gateway;
import cu.musala.gateway.service.BaseService;
import cu.musala.gateway.service.GatewayService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class GatewayServiceImpl extends BaseService implements GatewayService {

    /**
     * @return
     */
    public Collection<GatewayDto> getAllGateways() {

        ArrayList<GatewayDto> result = new ArrayList<GatewayDto>();
        Iterable<Gateway> data = gatewayRepository.findAll();
        for (Gateway entity : data) {
            result.add(modelMapper.map(entity, GatewayDto.class));
        }
        return result;
    }

    /**
     * @param id
     * @return
     */
    public GatewayDto getGatewayById(Long id) {
        Gateway entity = gatewayRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Gateway not found.", id));
        GatewayDto dto = modelMapper.map(entity, GatewayDto.class);
        return dto;
    }

    /**
     * @param entity
     * @return
     */
    public GatewayDto save(Gateway entity) {

        gatewayRepository.save(entity);
        for (Device device : entity.getDevices()) {
            device.setGateway(entity);
        }
        deviceRepository.saveAll(entity.getDevices());

        return modelMapper.map(entity, GatewayDto.class);
    }
}
