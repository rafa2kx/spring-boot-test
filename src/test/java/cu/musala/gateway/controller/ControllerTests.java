package cu.musala.gateway.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cu.musala.gateway.BaseTests;
import cu.musala.gateway.exception.EntityNotFoundException;
import cu.musala.gateway.model.Device;
import cu.musala.gateway.model.Gateway;
import cu.musala.gateway.repository.DeviceRepository;
import cu.musala.gateway.repository.GatewayRepository;
import cu.musala.gateway.service.DeviceService;
import cu.musala.gateway.service.GatewayService;
import cu.musala.gateway.service.impl.DeviceServiceImpl;
import cu.musala.gateway.service.impl.GatewayServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class ControllerTests extends BaseTests {

    @TestConfiguration
    static class GatewayServiceImplTestContextConfiguration {

        @Bean
        public GatewayService gatewayService() {
            return new GatewayServiceImpl();
        }

        @Bean
        public DeviceService deviceService() {
            return new DeviceServiceImpl();
        }

    }

    @Autowired
    private GatewayService gatewayService;
    @Autowired
    private DeviceService deviceService;

    @MockBean
    private GatewayRepository gatewayRepository;
    @MockBean
    private DeviceRepository deviceRepository;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public ModelMapper modelMapper;

    @Test
    public void whenPostValidNewGateway_thenCorrectResponse() throws Exception {

        Gateway gateway = generateGatewayWithDevices(10);
        when(gatewayRepository.save(gateway)).thenReturn(gateway);

        mockMvc.perform(post("/api/v1/gateways")
                .content(asJsonString(gateway))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(gateway.getId()))
                .andDo(print())
                .andDo(document("gateways/post",
                        requestFields(fieldWithPath("id").ignored())
                                .and(fieldWithPath("serialNumber").description("A unique serial number."))
                                .and(fieldWithPath("name").description("Human-readable name."))
                                .and(fieldWithPath("ipV4").description("IPv4 address."))
                                .and(fieldWithPath("createdDate").ignored())
                                .and(fieldWithPath("updatedDate").ignored())
                                .and(subsectionWithPath("devices").description("A List of associated devices"))
                                .and(fieldWithPath("devices[].id").ignored())
                                .and(fieldWithPath("devices[].uid").description("An UID for the device."))
                                .and(fieldWithPath("devices[].vendor").description("The device vendor."))
                                .and(fieldWithPath("devices[].onLine").description("Indicates if the device is online or not."))
                                .and(fieldWithPath("devices[].createdDate").ignored())
                                .and(fieldWithPath("devices[].updatedDate").ignored())
                ));
    }

    @Test
    public void whenPostInValidNewGateway_thenErrorResponse() throws Exception {

        Gateway gateway = generateGatewayWithDevices(10);
        gateway.setName(null);

        mockMvc.perform(post("/api/v1/gateways")
                .content(asJsonString(gateway))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid Parameters"))
                .andDo(print())
                .andDo(document("gateways/post/error",
                        responseFields(fieldWithPath("message").description("A message indicating that validations failed."))
                                .and(fieldWithPath("code").description("The HTTP Status code."))
                                .and(fieldWithPath("success").description("If the request was served successfully."))
                                .and(fieldWithPath("id").optional().type(Long.class).description("If an entity was not found, this indicates the bad identifier."))
                                .and(subsectionWithPath("errors").optional().description("A list of validation errors."))
                ));
    }

    @Test
    public void whenPostNewGatewayWithInvalidIP_thenErrorResponse() throws Exception {

        Gateway invalidGateway = generateGatewayWithDevices(10);
        invalidGateway.setIpV4("10.10.10.256");
        mockMvc.perform(post("/api/v1/gateways")
                .content(asJsonString(invalidGateway))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid Parameters"))
                .andDo(print())
                .andDo(document("gateways/post/error",
                        responseFields(fieldWithPath("message").description("A message describing the validation error."))
                                .and(fieldWithPath("code").description("The HTTP Status code."))
                                .and(fieldWithPath("success").description("If the request was served successfully."))
                                .and(fieldWithPath("id").optional().type(Long.class).description("If an entity was not found, this indicates the bad identifier."))
                                .and(subsectionWithPath("errors").optional().description("A list of validation errors."))
                ));
    }

    @Test
    public void whenPostValidNewDeviceToGateway_thenCorrectResponse() throws Exception {

        Gateway gateway = generateGatewayWithDevices(faker.number().numberBetween(0, 9));
        Device device = generateDevice();
        when(gatewayRepository.findById(gateway.getId()))
                .thenReturn(Optional.of(gateway));

        when(deviceRepository.save(device))
                .thenAnswer(invocationOnMock -> {
                    Long id = new Long(faker.number().randomNumber());
                    device.setGateway(gateway);
                    device.setId(id);
                    return device;
                });

        mockMvc.perform(post("/api/v1/devices/" + gateway.getId().toString())
                .content(asJsonString(device))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andDo(print())
                .andDo(document("devices/post",
                        requestFields(fieldWithPath("id").ignored())
                                .and(fieldWithPath("uid").description("An UID for the device."))
                                .and(fieldWithPath("vendor").description("The device vendor."))
                                .and(fieldWithPath("onLine").description("Indicates if the device is online or not."))
                                .and(fieldWithPath("createdDate").ignored())
                                .and(fieldWithPath("updatedDate").ignored())
                                .and(fieldWithPath("gateway").ignored())

                ));
    }

    @Test
    public void whenPostDeviceToGatewayWithDevicesOverLimit_thenErrorResponse() throws Exception {

        Gateway gateway = generateGatewayWithDevices(faker.number().numberBetween(10, 20));
        Device device = generateDevice();
        when(gatewayRepository.findById(gateway.getId()))
                .thenReturn(Optional.of(gateway));

        mockMvc.perform(post("/api/v1/devices/" + gateway.getId().toString())
                .content(asJsonString(device))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("No more than 10 devices are allowed per gateway"))
                .andDo(print())
                .andDo(document("devices/post/error",
                        responseFields(fieldWithPath("message").description("A message describing the validation error."))
                                .and(fieldWithPath("code").description("The HTTP Status code."))
                                .and(fieldWithPath("success").description("If the request was served successfully."))
                                .and(fieldWithPath("id").optional().description("If an entity was not found, this indicates the bad identifier."))
                ));
    }

    @Test
    public void whenDeleteDevice_thenCorrectResponse() throws Exception {

        Long deviceId = faker.number().randomNumber();
        Device device = generateDevice();
        device.setId(deviceId);
        when(deviceRepository.findById(deviceId))
                .thenReturn(Optional.of(device));

        mockMvc.perform(delete("/api/v1/devices/" + deviceId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(deviceId))
                .andDo(print())
                .andDo(document("devices/delete"));
    }

    @Test
    public void whenDeleteDevice_thenErrorResponse() throws Exception {

        Long deviceId = faker.number().randomNumber();
        Device device = generateDevice();
        device.setId(deviceId);
        when(deviceRepository.findById(deviceId))
                .thenThrow(new EntityNotFoundException("Invalid Device Id", deviceId));

        mockMvc.perform(delete("/api/v1/devices/" + deviceId.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(deviceId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Invalid Device Id"))
                .andDo(print())
                .andDo(document("devices/delete/error",
                        responseFields(fieldWithPath("message").description("A message describing the validation error."))
                                .and(fieldWithPath("code").description("The HTTP Status code."))
                                .and(fieldWithPath("success").description("If the request was served successfully."))
                                .and(fieldWithPath("id").optional().description("If an entity was not found, this indicates the bad identifier."))
                ));
    }


    @Test
    public void whenGetAllGateways_Response() throws Exception {

        ArrayList<Gateway> gateways = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            gateways.add(generateGatewayWithDevices(faker.number().numberBetween(0, 15)));
        }
        when(gatewayRepository.findAll())
                .thenReturn(gateways);

        mockMvc.perform(get("/api/v1/gateways"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("gateways/get",
                        responseFields(
                                fieldWithPath("[].id").description("The Id of the Gateway."))
                                .and(fieldWithPath("[].serialNumber").description("A unique serial number."))
                                .and(fieldWithPath("[].name").description("Human-readable name."))
                                .and(fieldWithPath("[].ipV4").description("IPv4 address."))
                                .and(fieldWithPath("[].createdDate").description("Date when the gateway was created.").type(Date.class))
                                .and(fieldWithPath("[].updatedDate").ignored())
                                .and(subsectionWithPath("[].devices").description("A List of associated devices"))
                                .and(fieldWithPath("[].devices[].id").description("The Device Id."))
                                .and(fieldWithPath("[].devices[].uid").description("An UID for the device."))
                                .and(fieldWithPath("[].devices[].vendor").description("The device vendor."))
                                .and(fieldWithPath("[].devices[].onLine").description("Indicates if the device is online or not."))
                                .and(fieldWithPath("[].devices[].createdDate").description("Date when the device was created."))
                                .and(fieldWithPath("[].devices[].updatedDate").ignored())
                ));

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
