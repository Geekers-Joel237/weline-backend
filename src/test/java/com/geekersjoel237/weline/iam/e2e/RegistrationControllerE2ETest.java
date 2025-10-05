//package com.geekersjoel237.weline.iam.e2e;
//
//import com.geekersjoel237.weline.iam.domain.enums.CustomerStatusEnum;
//import com.geekersjoel237.weline.iam.infrastructure.models.CustomerEntity;
//import com.geekersjoel237.weline.iam.infrastructure.persistence.JpaCustomerOtpRepository;
//import com.geekersjoel237.weline.iam.infrastructure.persistence.JpaCustomerRepository;
//import com.geekersjoel237.weline.iam.infrastructure.web.dto.LoginRequestDto;
//import com.geekersjoel237.weline.iam.infrastructure.web.dto.RegistrationRequestDto;
//import com.geekersjoel237.weline.iam.infrastructure.web.dto.VerifyOtpRequestDto;
//import com.geekersjoel237.weline.shared.infrastructure.web.ApiResponse;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.transaction.annotation.Transactional;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.UUID;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@Testcontainers
//@Transactional
//public class RegistrationControllerE2ETest {
//
//    @Container
//    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");
//
////    @Container
////    private static final RedisContainer redis = new RedisContainer(DockerImageName.parse("redis:7-alpine"));
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//    @Autowired
//    private JpaCustomerRepository customerRepository;
//    @Autowired
//    private JpaCustomerOtpRepository otpRepository;
//
//    @DynamicPropertySource // Injecte dynamiquement les propriétés des conteneurs dans le contexte Spring
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//        registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
//        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop"); // Crée le schéma pour les tests
////        registry.add("spring.redis.host", redis::getHost);
////        registry.add("spring.redis.port", () -> redis.getMappedPort(6379).toString());
//    }
//
//    // --- Scénarios d'Inscription ---
//    @Test
//    @DisplayName("Scenario 1: Should register a new customer successfully")
//    void shouldRegisterNewCustomerSuccessfully() {
//        var request = new RegistrationRequestDto("+237699887766", true, UUID.randomUUID().toString());
//
//        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
//                "/api/v1/auth/register", request, ApiResponse.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//        assertThat(response.getBody().message()).isEqualTo("Registration process attempted.");
//
//        assertThat(customerRepository.count()).isEqualTo(1);
//        assertThat(otpRepository.count()).isEqualTo(1);
//        var customer = customerRepository.findAll().getFirst();
//        assertThat(customer.getStatus()).isEqualTo(CustomerStatusEnum.PENDING_VERIFICATION);
//    }
//
//    @Test
//    @DisplayName("Scenario 3: Should fail registration if customer already exists")
//    void shouldFailRegistrationIfCustomerExists() {
//        // GIVEN: un client existe déjà
//        var existingCustomer = new CustomerEntity(UUID.randomUUID().toString(), "+237699887766", CustomerStatusEnum.VERIFIED);
//        customerRepository.save(existingCustomer);
//
//        var request = new RegistrationRequestDto("+237699887766", true, existingCustomer.getId());
//
//        // WHEN: on tente de le ré-inscrire
//        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
//                "/api/v1/auth/register", request, ApiResponse.class
//        );
//
//        // Une requête pour un utilisateur existant devrait retourner une erreur client
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
//        assertThat(response.getBody()).isNotNull();
//        assertThat(response.getBody().message()).isEqualTo("Phone number already exists");
//    }
//
//
//    // --- Scénarios de Validation d'OTP ---
//
//    @Test
//    @DisplayName("Scenario 4: Should verify OTP and return a JWT token")
//    void shouldVerifyOtpAndReturnToken() {
//        // GIVEN: un client s'est pré-inscrit
//        shouldRegisterNewCustomerSuccessfully();
//
//        // On doit récupérer l'OTP généré. Comme on ne mocke rien, on le lit en base de données.
//        var savedOtp = otpRepository.findAll().getFirst();
//        var otpCode = savedOtp.getOtpCode();
//
//        var request = new VerifyOtpRequestDto("+237699887766", otpCode);
//
//        // WHEN: on valide l'OTP
//        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
//                "/api/v1/auth/verify-otp", request, ApiResponse.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//        assertThat(response.getBody()).isNotNull();
//
//        var customer = customerRepository.findAll().getFirst();
//        assertThat(customer.getStatus()).isEqualTo(CustomerStatusEnum.VERIFIED);
//        assertThat(otpRepository.count()).isZero(); // L'OTP doit avoir été supprimé
//    }
//
//    // --- Scénarios de Connexion ---
//
//    @Test
//    @DisplayName("Scenario 8: Should fail login if customer does not exist")
//    void shouldFailLoginIfCustomerNotFound() {
//        var request = new LoginRequestDto("+237611223344"); // Numéro inconnu
//
//        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(
//                "/api/v1/auth/login", request, ApiResponse.class
//        );
//
//        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//    }
//}
