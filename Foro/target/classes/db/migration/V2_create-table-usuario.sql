CCREATE TABLE Usuario (
     id BIGINT AUTO_INCREMENT PRIMARY KEY,
     nombre VARCHAR(255) NOT NULL,
     email VARCHAR(255) NOT NULL UNIQUE,
     contraseña VARCHAR(255) NOT NULL
 );
