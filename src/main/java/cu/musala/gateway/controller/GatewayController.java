package cu.musala.gateway.controller;

import cu.musala.gateway.dto.GatewayDto;
import cu.musala.gateway.model.Gateway;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@Validated
public class GatewayController extends BaseController{

    @GetMapping("api/v1/gateways")
    public Collection<GatewayDto> getGateways() {
        return gatewayService.getAllGateways();
    }

    @GetMapping("api/v1/gateways/{id}")
    public GatewayDto getGatewayById(@PathVariable Long id) {
        return gatewayService.getGatewayById(id);
    }

    @PostMapping("api/v1/gateways")
    @ResponseStatus(HttpStatus.CREATED)
    public GatewayDto newGateway(@Valid @RequestBody Gateway newGateway) {
        return gatewayService.save(newGateway);
    }

}
