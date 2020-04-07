CREATE TABLE gateways (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  serial_number varchar(50) NOT NULL,
  name varchar(50) NOT NULL,
  ipV4 varchar(15) DEFAULT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_serial_number (serial_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE devices (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  uid bigint(20) NOT NULL,
  vendor varchar(50) NOT NULL,
  on_line bit NOT NULL,
  gateway_id bigint(20) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_uid (uid),
  CONSTRAINT FK_devices_gateway FOREIGN KEY (gateway_id) REFERENCES gateways (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;