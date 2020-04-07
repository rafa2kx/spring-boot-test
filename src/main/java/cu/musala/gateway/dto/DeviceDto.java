package cu.musala.gateway.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;

public class DeviceDto {
    private Long id;
    private Long uid;
    private String vendor;
    private Boolean onLine;
    private Long gatewayId;
    private Date createdDate;
    private Date updatedDate;

    @JsonIgnore
    private GatewayDto gateway;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public Boolean getOnLine() {
        return onLine;
    }

    public void setOnLine(Boolean onLine) {
        this.onLine = onLine;
    }

    public Long getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(Long gatewayId) {
        this.gatewayId = gatewayId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public GatewayDto getGateway() {
        return gateway;
    }

    public void setGateway(GatewayDto gateway) {
        this.gateway = gateway;
    }
}
