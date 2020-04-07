package cu.musala.gateway.controller;

import cu.musala.gateway.service.DeviceService;
import cu.musala.gateway.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    protected GatewayService gatewayService;
    @Autowired
    protected DeviceService deviceService;
}
