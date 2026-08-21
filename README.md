# Trading Orders Service

A Spring Boot microservice for managing trading orders with JWT authentication, database migration support, and comprehensive testing.

## 🚀 Quick Start

### Prerequisites
- Java 25
- Maven 3.9+
- PostgreSQL 15+ (for production)

### Build & Run
```bash
# Build the project
mvn clean install

# Run tests
mvn test

# Run the application
mvn spring-boot:run
```

The service will be available at `http://localhost:7080`

## 📁 Project Structure

```
src/main/java/com/jusmore/trading/platform/
├── TradingOrdersServiceApplication.java    # Main entry point
│
├── config/                                  # Spring configuration
│   ├── SecurityConfig.java                  # JWT security setup
│   └── JwtConfig.java                       # JWT claims converter
│
├── controller/                              # REST endpoints
├── service/                                 # Business logic
├── repository/                              # Data access (JPA)
├── domain/                                  # JPA entities
│   └── base/BaseEntity.java                 # Base entity class
│
├── dto/                                     # Data Transfer Objects
│   ├── request/                             # API request models
│   └── response/                            # API response models
│
├── mapper/                                  # MapStruct mappers
├── enums/                                   # Enumerations
│   ├── Currency.java
│   ├── OrderKind.java
│   ├── Status.java
│   ├── TradeSide.java
│   └── TradeType.java
│
└── exception/                               # Custom exceptions

src/main/resources/
├── application.properties                   # Main config
├── application-test.properties              # Test config
```

## 🏗️ Architecture

### Layered Architecture

1. **Controller Layer**: REST API endpoints, request/response handling
2. **Service Layer**: Business logic, transaction management
3. **Repository Layer**: Data access using Spring Data JPA
4. **Domain Layer**: JPA entities, database mapping
5. **DTO Layer**: API contracts, decoupled from domain

### Technology Stack

- **Framework**: Spring Boot 4.1.0
- **Security**: Spring Security with OAuth2 JWT
- **Database**: PostgreSQL (prod), H2 (tests)
- **Mapping**: MapStruct 1.5.5
- **Observability**: Spring Boot Actuator, OpenTelemetry
- **Cache**: Spring Data Redis
- **Build**: Maven

## 🔐 Security

The service uses JWT-based authentication via OAuth2 Resource Server:

- Stateless session management
- JWT token validation
- Custom claims extraction (roles, authorities)
- Public endpoints: `/actuator/health`, `/actuator/info`

## 🗄️ Database

### Production (PostgreSQL)
```properties
spring.datasource.url=jdbc:postgresql://localhost:49153/blogdb?currentSchema=portfolio
spring.datasource.username=jusmoredb
spring.datasource.password=jusmoredb
```

### Test (H2 In-Memory)
```properties
spring.datasource.url=jdbc:h2:mem:testdb;MODE=PostgreSQL
```


## 📊 Monitoring

### Actuator Endpoints
- Health: `GET /actuator/health`
- Info: `GET /actuator/info`
- Thread Dump: `GET /actuator/threaddump`
- Environment: `GET /actuator/env`

## 🧪 Testing

### Test Configuration
- Profile: `test`
- Database: H2 in-memory
- Security: Disabled (mock JwtDecoder)

### Run Tests
```bash
mvn test
```

## ⚙️ Configuration

### Virtual Threads
```properties
spring.threads.virtual.enabled=true
```
Enables Java virtual threads for improved concurrency.

### JPA
```properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### OAuth2 JWT
```properties
spring.security.oauth2.resourceserver.jwt.issuer-uri=https://your-oidc-provider.com/realms/your-realm
```

## 🛠️ Development Guidelines

### Adding a New Feature

1. **Create Entity** in `domain/`
```java
@Entity
@Getter @Setter
public class Order extends BaseEntity {
    private String symbol;
    private BigDecimal price;
    // ...
}
```

2. **Create DTOs** in `dto/request/` and `dto/response/`
```java
public record CreateOrderRequest(String symbol, BigDecimal price) {}
public record OrderResponse(Long id, String symbol, BigDecimal price) {}
```

3. **Create Mapper** in `mapper/`
```java
@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toEntity(CreateOrderRequest request);
    OrderResponse toResponse(Order entity);
}
```

4. **Create Repository** in `repository/`
```java
public interface OrderRepository extends JpaRepository<Order, Long> {}
```

5. **Create Service** in `service/` and implementation in `service/impl/`
```java
public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
}
```

6. **Create Controller** in `controller/`
```java
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(orderService.createOrder(request));
    }
}
```

## 📝 Best Practices

- ✅ Use DTOs for API contracts
- ✅ Never expose entities directly in controllers
- ✅ Keep services transactional and focused
- ✅ Write tests for all business logic
- ✅ Use MapStruct for entity-DTO mapping
- ✅ Use enums for fixed value sets
- ✅ Leverage BaseEntity for common fields

## 📚 Additional Resources

- [Spring Boot Documentation](https://docs.spring.io/spring-boot/4.1.0/reference/)
- [Spring Security OAuth2](https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html)
- [MapStruct Documentation](https://mapstruct.org/documentation/)

## 📄 License

[Add your license information here]

## 👥 Contributors

[Add contributor information here]
