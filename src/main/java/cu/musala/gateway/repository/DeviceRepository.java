package cu.musala.gateway.repository;

import cu.musala.gateway.model.Device;
import cu.musala.gateway.model.Gateway;
import org.springframework.data.repository.CrudRepository;

public interface DeviceRepository extends CrudRepository<Device, Long> {
}
