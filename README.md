# Bank Account Microservice

## 📋 Project Overview

This project implements a comprehensive **Bank Account Microservice** using Spring Boot, following modern microservice architecture patterns. The service provides multiple API interfaces (REST, GraphQL, and Spring Data REST) for managing bank accounts and customers.

## 🎯 Learning Objectives Achieved

This project demonstrates the implementation of all the required features from the tutorial videos:

1. ✅ **Spring Boot Project Setup** with Web, Spring Data JPA, H2, and Lombok
2. ✅ **JPA Entity Creation** (BankAccount and Customer)
3. ✅ **Repository Layer** with Spring Data JPA
4. ✅ **DAO Layer Testing**
5. ✅ **RESTful Web Service** for account management
6. ✅ **API Testing** with REST clients
7. ✅ **Swagger Documentation** generation
8. ✅ **Spring Data REST** with projections
9. ✅ **DTOs and Mappers** implementation
10. ✅ **Service Layer** (business logic)
11. ✅ **GraphQL Web Service** implementation

## 🏗️ Architecture

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   REST API      │    │   GraphQL API   │    │ Spring Data REST│
│   Controller    │    │   Controller    │    │   (Auto-gen)    │
└─────────┬───────┘    └─────────┬───────┘    └─────────┬───────┘
          │                      │                      │
          └──────────────────────┼──────────────────────┘
                                 │
                    ┌─────────────┴─────────────┐
                    │     Service Layer         │
                    │   (Business Logic)        │
                    └─────────────┬─────────────┘
                                  │
                    ┌─────────────┴─────────────┐
                    │     Repository Layer      │
                    │   (Data Access)           │
                    └─────────────┬─────────────┘
                                  │
                    ┌─────────────┴─────────────┐
                    │     Database (H2)         │
                    │   (In-Memory)             │
                    └───────────────────────────┘
```

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.7
- **Java Version**: 17
- **Database**: H2 (In-Memory)
- **ORM**: Spring Data JPA
- **API Documentation**: SpringDoc OpenAPI (Swagger)
- **GraphQL**: Spring GraphQL
- **Code Generation**: Lombok
- **Build Tool**: Maven

## 📦 Dependencies

```xml
- spring-boot-starter-web
- spring-boot-starter-data-jpa
- spring-boot-starter-data-rest
- spring-boot-starter-graphql
- h2database (runtime)
- lombok
- springdoc-openapi-starter-webmvc-ui
```

## 🗄️ Data Model

### Entities

#### BankAccount
```java
@Entity
public class BankAccount {
    @Id
    private String id;           // UUID
    private Date createdAt;      // Creation timestamp
    private Double balance;      // Account balance
    private String currency;     // Currency (MAD, USD, EUR)
    @Enumerated(EnumType.STRING)
    private AccountType type;    // CURRENT_ACCOUNT or SAVING_ACCOUNT
    @ManyToOne
    private Customer customer;   // Associated customer
}
```

#### Customer
```java
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "customer")
    private List<BankAccount> bankAccounts;
}
```

#### AccountType (Enum)
```java
public enum AccountType {
    CURRENT_ACCOUNT, SAVING_ACCOUNT
}
```

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher

### Installation & Running

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd bank-account-service
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Application: http://localhost:8081
   - H2 Console: http://localhost:8081/h2-console
   - Swagger UI: http://localhost:8081/swagger-ui.html
   - GraphiQL: http://localhost:8081/graphiql

## 📚 API Documentation

### 1. REST API Endpoints

#### Bank Account Management
```http
GET    /api/bankAccounts          # Get all bank accounts
GET    /api/bankAccounts/{id}     # Get account by ID
POST   /api/bankAccounts          # Create new account
PUT    /api/bankAccounts/{id}     # Update account
DELETE /api/bankAccounts/{id}     # Delete account
```

#### Example Requests

**Create Account:**
```http
POST /api/bankAccounts
Content-Type: application/json

{
    "balance": 5000.0,
    "currency": "MAD",
    "type": "CURRENT_ACCOUNT"
}
```

**Update Account:**
```http
PUT /api/bankAccounts/{id}
Content-Type: application/json

{
    "balance": 7500.0,
    "currency": "MAD",
    "type": "SAVING_ACCOUNT"
}
```

### 2. Spring Data REST API

#### Auto-generated Endpoints
```http
GET    /bankAccounts              # Get all accounts
GET    /bankAccounts/{id}         # Get account by ID
POST   /bankAccounts              # Create account
PUT    /bankAccounts/{id}         # Update account
DELETE /bankAccounts/{id}         # Delete account
GET    /bankAccounts/search/byType?t=CURRENT_ACCOUNT  # Find by type
```

#### Projections
```http
GET    /bankAccounts?projection=p1    # Get accounts with projection (id, type, balance only)
```

### 3. GraphQL API

#### Schema Definition
```graphql
type Query {
    accountsList: [BankAccount]
    bankAccountById(id: String): BankAccount
    customers: [Customer]
}

type Mutation {
    addAccount(bankAccount: BankAccountDTO): BankAccount
    updateAccount(id: String, bankAccount: BankAccountDTO): BankAccount
    deleteAccount(id: String): String
}

type BankAccount {
    id: String
    createdAt: Float
    balance: Float
    currency: String
    type: String
    customer: Customer
}

type Customer {
    id: ID
    name: String
    bankAccounts: [BankAccount]
}

input BankAccountDTO {
    balance: Float
    currency: String
    type: String
}
```

#### Example GraphQL Queries

**Get All Accounts:**
```graphql
query {
    accountsList {
        id
        balance
        currency
        type
        customer {
            id
            name
        }
    }
}
```

**Get Account by ID:**
```graphql
query {
    bankAccountById(id: "account-uuid") {
        id
        balance
        currency
        type
        createdAt
    }
}
```

**Create Account:**
```graphql
mutation {
    addAccount(bankAccount: {
        balance: 10000.0
        currency: "MAD"
        type: "CURRENT_ACCOUNT"
    }) {
        id
        balance
        currency
        type
    }
}
```

## 🧪 Testing the APIs

### Using Postman/Insomnia

1. **Import the collection** (create requests for all endpoints)
2. **Set base URL**: `http://localhost:8081`
3. **Test CRUD operations** on bank accounts

### Using GraphiQL

1. Navigate to: http://localhost:8081/graphiql
2. Use the interactive GraphQL interface
3. Test queries and mutations

### Using Swagger UI

1. Navigate to: http://localhost:8081/swagger-ui.html
2. Explore all available endpoints
3. Test APIs directly from the browser

## 🏛️ Project Structure

```
src/main/java/org/sid/bank_account_service/
├── BankAccountServiceApplication.java          # Main application class
├── dto/                                        # Data Transfer Objects
│   ├── BankAccountRequestDTO.java
│   └── BankAccountResponseDTO.java
├── entities/                                   # JPA Entities
│   ├── BankAccount.java
│   ├── Customer.java
│   └── AccountProjection.java                 # Spring Data REST projection
├── enums/                                      # Enumerations
│   └── AccountType.java
├── exceptions/                                 # Exception handling
│   └── CustomDataFetcherExceptionResolver.java
├── mappers/                                    # Entity-DTO mappers
│   └── AccountMapper.java
├── repositories/                               # Data access layer
│   ├── BankAccountRepository.java
│   └── CustomerRepository.java
├── service/                                    # Business logic layer
│   ├── AccountService.java
│   └── AccountServiceImpl.java
└── web/                                        # Web controllers
    ├── AccountRestController.java              # REST API
    └── BankAccountGraphQLController.java       # GraphQL API
```

## 🔧 Configuration

### Application Properties
```properties
spring.application.name=bank-account-service
spring.datasource.url=jdbc:h2:mem:account-db
spring.h2.console.enabled=true
server.port=8081
spring.graphql.graphiql.enabled=true
```

### Database Configuration
- **Database**: H2 In-Memory Database
- **Console**: Enabled at `/h2-console`
- **JDBC URL**: `jdbc:h2:mem:account-db`
- **Username**: `sa`
- **Password**: (empty)

## 📊 Sample Data

The application automatically creates sample data on startup:
- **4 Customers**: Soufiane, Mohamed, Hanae, Imane
- **40 Bank Accounts**: 10 accounts per customer with random balances and types

## 🎓 Key Learning Points Demonstrated

### 1. **Spring Boot Architecture**
- Proper layering (Controller → Service → Repository)
- Dependency injection and IoC container
- Auto-configuration and starter dependencies

### 2. **JPA and Spring Data**
- Entity relationships (@ManyToOne, @OneToMany)
- Repository pattern with custom queries
- Projections for selective data exposure

### 3. **REST API Design**
- RESTful endpoint design
- HTTP methods (GET, POST, PUT, DELETE)
- Request/Response DTOs
- Exception handling

### 4. **GraphQL Implementation**
- Schema definition
- Query and Mutation resolvers
- Type system and relationships
- GraphiQL interface

### 5. **API Documentation**
- Swagger/OpenAPI integration
- Interactive API documentation
- Request/response examples

### 6. **Data Transfer Objects (DTOs)**
- Separation of concerns
- Mapper pattern implementation
- Request vs Response DTOs

### 7. **Service Layer Pattern**
- Business logic encapsulation
- Transaction management
- Service interfaces and implementations

## 🚀 Advanced Features

### Spring Data REST Projections
- Custom data views using `@Projection`
- Selective field exposure
- URL-based projection selection

### GraphQL Exception Handling
- Custom exception resolver
- Proper error responses
- Field-level error handling

### Lombok Integration
- Reduced boilerplate code
- Builder pattern
- Data annotations

## 📝 Testing Recommendations

1. **Unit Tests**: Test service layer methods
2. **Integration Tests**: Test repository layer
3. **API Tests**: Test REST endpoints
4. **GraphQL Tests**: Test GraphQL queries/mutations
5. **Load Tests**: Test performance under load

## 🔮 Future Enhancements

- [ ] Add authentication and authorization
- [ ] Implement caching (Redis)
- [ ] Add message queues (RabbitMQ/Kafka)
- [ ] Implement circuit breakers
- [ ] Add monitoring and metrics
- [ ] Database migration scripts
- [ ] Docker containerization
- [ ] Kubernetes deployment

## 📞 Support

For questions or issues related to this project, please refer to:
- Spring Boot Documentation: https://spring.io/projects/spring-boot
- GraphQL Documentation: https://graphql.org/
- Spring Data REST Documentation: https://spring.io/projects/spring-data-rest

---

**Developed by**: KOUSTA Soufiane  
**class**: CCN3
