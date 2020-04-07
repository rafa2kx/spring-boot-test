package cu.musala.gateway.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "gateways")
public class Gateway {
    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Serial Number must not be blank")
    @Column(unique = true, nullable = false)
    @Size(max = 50)
    private String serialNumber;

    @NotBlank(message = "Name must not be blank")
    @Size(max = 50)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "IPV4 must not be blank")
    @Pattern(regexp = IPADDRESS_PATTERN, message = "IPV4 address is invalid")
    @Column(nullable = false)
    private String ipV4;

    @OneToMany(mappedBy = "gateway")
    @Size(max = 10, message = "No more than 10 devices are allowed per gateway")
    private List<Device> devices;

    @CreationTimestamp
    private Date createdDate;
    @UpdateTimestamp
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIpV4() {
        return ipV4;
    }

    public void setIpV4(String ipV4) {
        this.ipV4 = ipV4;
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

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Gateway)
            return ((Gateway) obj).getId() == id;
        return super.equals(obj);
    }
}
