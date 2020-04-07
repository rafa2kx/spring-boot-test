package cu.musala.gateway.service;

import cu.musala.gateway.dto.GatewayDto;
import cu.musala.gateway.model.Gateway;
import java.util.Collection;

public interface GatewayService {

    Collection<GatewayDto> getAllGateways();

    GatewayDto getGatewayById(Long id);

    GatewayDto save(Gateway entity);
}
