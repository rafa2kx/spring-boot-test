package cu.musala.gateway.service;

import cu.musala.gateway.repository.DeviceRepository;
import cu.musala.gateway.repository.GatewayRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.SmartValidator;

@Service
public class BaseService {

    @Autowired
    protected GatewayRepository gatewayRepository;
    @Autowired
    protected DeviceRepository deviceRepository;
    @Autowired
    protected ModelMapper modelMapper;
    @Autowired
    protected SmartValidator validator;

}
