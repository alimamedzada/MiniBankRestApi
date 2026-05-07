# 🏦 MiniBank — Full-Stack Banking Application

A full-stack banking application built with **Spring Boot REST API** (backend) and **Vanilla JS + Bootstrap** (frontend), featuring JWT authentication, Spring Security, and a complete transaction management system.

---

## 📌 Project Overview

MiniBank simulates a real-world internet banking system where customers can manage accounts, transfer money, and track transaction history. The project was developed iteratively — from a console application to a production-style REST API with a separate client application.

### Evolution of the Project

| Version | Repository | Technologies |
|---|---|---|
| v1 — Console App | `MiniBank` | Java SE, OOP |
| v2 — Web App (JDBC) | `MiniBankWebApp` | JDBC, Servlet/JSP |
| v3 — Spring MVC | `MiniBankWebAppByUsingSpringMVC` | Spring MVC, Thymeleaf |
| v4 — REST API | `MiniBankRestApi` | Spring Boot, Spring Security, JWT |
| v5 — REST Client | `MiniBankClient` | HTML, CSS, JavaScript, Bootstrap 5 |

---

## ✨ Features

- **JWT Authentication** — Stateless login with JSON Web Tokens
- **OAuth2 Integration** — Google login support
- **Account Management** — Create Checking and Savings accounts
- **Money Transfer** — Transfer between accounts with validation
- **Quick Transfer** — Fast transfer from dashboard
- **Transaction History** — Filter by keyword, date, and type (INCOMING / OUTGOING / INTERNAL)
- **Balance Tracking** — Real-time total balance across all accounts
- **Exception Handling** — Custom exceptions: `InsufficientBalanceException`, `InvalidAccountException`, `InvalidAmountException`
- **Unit Tests** — JUnit 5 + Mockito coverage for all service layers

---

## 🛠️ Tech Stack

### Backend
| Technology | Usage |
|---|---|
| Java 17 | Core language |
| Spring Boot | Application framework |
| Spring MVC | REST controllers |
| Spring Security | Authentication & authorization |
| Spring Data JPA | Database operations |
| JWT (jjwt) | Token-based authentication |
| OAuth2 | Google login |
| MySQL | Relational database |
| JUnit 5 + Mockito | Unit testing |
| Maven | Build tool |

### Frontend
| Technology | Usage |
|---|---|
| HTML5 / CSS3 | Page structure and styling |
| JavaScript (ES6+) | API calls, DOM manipulation |
| Bootstrap 5.3 | Responsive UI components |
| Bootstrap Icons | Icon library |
| Fetch API | HTTP requests to REST backend |

---

## 🏗️ Architecture

```
MiniBankRestApi/
└── src/main/java/com/company/
    ├── MiniBankByUsingSpring/          ← Core module (shared)
    │   ├── entity/                     ← JPA entities (Customers, Accounts, Transaction...)
    │   ├── repository/                 ← Spring Data JPA repositories
    │   ├── service/
    │   │   ├── inter/                  ← Service interfaces
    │   │   └── impl/                   ← Service implementations
    │   ├── exception/                  ← Custom exceptions
    │   └── util/                       ← Utility classes (IdentifierUtil...)
    │
    └── MiniBankWebAppByUsingRest/      ← REST API module
        ├── controller/                 ← REST controllers
        ├── dto/                        ← Data Transfer Objects
        ├── mapper/                     ← Entity ↔ DTO mappers
        └── security/                   ← JWT filter, JwtUtil, UserDetailsService

MiniBankClient/
└── MiniBankWebAppByUsingRestClient/
    ├── index.html
    ├── login.html
    ├── register.html
    ├── customer.html       ← Dashboard
    ├── accounts.html
    ├── account-detail.html
    ├── money-transfer.html
    ├── transaction-history.html
    ├── open-new-account.html
    └── assets/
        └── css/
```

---

## 🚀 Getting Started

### Prerequisites

- Java 17+
- MySQL 8+
- Maven 3.8+
- Any modern browser (for the client)

### 1. Clone the repositories

```bash
git clone https://github.com/alimamedzada/MiniBankRestApi.git
git clone https://github.com/alimamedzada/MiniBankClient.git
```

### 2. Configure the database

Create a MySQL database:

```sql
CREATE DATABASE minibank_db;
```

Update `application.properties` in the backend:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/minibank_db
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update

jwt.secret=your_jwt_secret_key
```

### 3. Run the backend

```bash
cd MiniBankRestApi/MiniBankByUsingRest
mvn spring-boot:run
```

The API will be available at: `http://localhost:8080/minibankrest`

### 4. Run the frontend

Open `MiniBankClient/MiniBankWebAppByUsingRestClient/index.html` directly in your browser, or serve it with a simple HTTP server:

```bash
cd MiniBankClient/MiniBankWebAppByUsingRestClient
npx serve .
```

---

## 📡 API Endpoints

### Authentication
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/authenticate` | Login — returns JWT token |

### Customer
| Method | Endpoint | Description |
|---|---|---|
| GET | `/customer` | Get current customer dashboard data |

### Accounts
| Method | Endpoint | Description |
|---|---|---|
| GET | `/accounts` | Get all accounts with total balance |
| GET | `/accounts/{id}` | Get account details by ID |
| POST | `/create-new-account` | Open a new Checking or Savings account |

### Transfers
| Method | Endpoint | Description |
|---|---|---|
| GET | `/money-transfer` | Get transfer page data (accounts + history) |
| POST | `/transfer-process` | Execute a money transfer |
| POST | `/quick-transfer-process` | Quick transfer from dashboard |

### Transactions
| Method | Endpoint | Description |
|---|---|---|
| GET | `/transaction-history` | Get filtered transaction history |

**Query params for `/transaction-history`:**
- `keyword` — search by IBAN or description
- `type` — `INCOMING`, `OUTGOING`, `INTERNAL`, or `ALL`
- `date` — filter by date (`yyyy-MM-dd`)

---

## 🧪 Tests

Unit tests are written with **JUnit 5** and **Mockito** for all three service layers.

```bash
mvn test
```

### Test Coverage

| Test Class | Methods Tested |
|---|---|
| `AccountServiceTest` | `findAccountById`, `updateAccount`, `findAllByCustomerId`, `deleteAccount`, `showAccounts`, `createAccount`, `deposit`, `withdraw`, `transfer`, `quickTransfer` |
| `CustomerServiceTest` | `addCustomer`, `updateCustomer`, `deleteCustomerById`, `getCustomerById`, `findCustomerByUsername`, `getAllCustomers`, `getTotalBalance` |
| `TransactionServiceTest` | `createTransaction`, `getTransactionsByAccountId`, `addTransaction`, `deleteTransaction`, `updateTransaction`, `getAllTransactions`, `getTransactionsByCustomerId`, `getRecentTransactionsByCustomerId` |

Notable testing techniques used:
- `Mockito.mockStatic()` for testing static utility methods
- Custom exception assertions (`InsufficientBalanceException`, `InvalidAccountException`, `InvalidAmountException`)
- Verify call counts with `Mockito.verify(..., times(n))`

---

## 🔐 Security

- Passwords are stored using **BCrypt** hashing
- All endpoints (except `/api/authenticate` and `/register`) require a valid **JWT Bearer token**
- Token is validated on every request via a custom JWT filter
- **OAuth2** (Google login) is also supported

---

## 📁 Related Repositories

| Repository | Description |
|---|---|
| [MiniBank](https://github.com/alimamedzada/MiniBank) | v1 — Console application |
| [MiniBankWebApp](https://github.com/alimamedzada/MiniBankWebApp) | v2 — JDBC + Servlet/JSP |
| [MiniBankWebAppByUsingSpringMVC](https://github.com/alimamedzada/MiniBankWebAppByUsingSpringMVC) | v3 — Spring MVC |
| [MiniBankRestApi](https://github.com/alimamedzada/MiniBankRestApi) | v4 — Spring Boot REST API ✅ |
| [MiniBankClient](https://github.com/alimamedzada/MiniBankClient) | v5 — JavaScript frontend client ✅ |

---

## 👤 Author

**Ali Mamedzada**
- GitHub: [@alimamedzada](https://github.com/alimamedzada)
- Email: alimamedzada@gmail.com

---

## 📄 License

This project is open source and available for educational purposes.
