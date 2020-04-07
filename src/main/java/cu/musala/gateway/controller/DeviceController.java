package cu.musala.gateway.controller;

import cu.musala.gateway.dto.DeviceDto;
import cu.musala.gateway.model.Device;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Validated
public class DeviceController extends BaseController {

    @PostMapping("api/v1/devices/{gatewayId}")
    @ResponseStatus(HttpStatus.CREATED)
    public DeviceDto newDevice(@Valid @RequestBody Device newDevice, @PathVariable Long gatewayId) {

        return deviceService.save(newDevice, gatewayId);
    }

    @DeleteMapping("api/v1/devices/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeviceDto deleteDevice(@PathVariable Long id) {
        return deviceService.delete(id);
    }
}
