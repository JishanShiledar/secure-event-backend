**🚀 Secure Event Processing Backend**



**A production-grade, multi-tenant backend system designed to securely ingest, process, and analyze high-volume event data for SaaS platforms.**

**Built with scalability, security, and observability in mind.**



**🧠 Problem This Project Solves**



**▶Modern SaaS applications need to:**

 **◉ Track user activities (login, logout, actions, clicks)**

 **◉ Secure APIs from abuse**

 **◉ Isolate customer data (multi-tenancy)**

 **◉ Provide admin-level analytics**

 **◉ Handle traffic spikes safely**



**This backend provides a centralized, secure event ingestion and monitoring platform that companies can integrate into their existing systems.**



**🛠️ Tech Stack**

 **◉ Java 17**

 **◉ Spring Boot**

 **◉ Spring Security (JWT)**

 **◉ PostgreSQL – persistent event storage**

 **◉ Redis – distributed rate limiting**

 **◉ Docker – local \& test infrastructure**

 **◉ JUnit 5 + Testcontainers – integration testing**

 **◉ REST APIs**



**🧩 Core Features**



**🔐 Authentication \& Authorization**

 **◉ Stateless JWT-based authentication**

 **◉ Role-based access control (ROLE\_USER, ROLE\_ADMIN)**

 **◉ Secure API access without session storage**



**🏢 Multi-Tenant Architecture**

 **◉Each request is scoped by companyId**

 **◉Tenant isolation enforced at:**

  **•API layer**

  **•Service layer**

  **•Database layer**

**◉Cross-tenant access allowed only for admin users**



**🚦 Rate Limiting \& Abuse Protection**

 **◉Redis-backed rate limiting**

 **◉Limits applied per:**

  **•Company**

  **•Endpoint**

  **•HTTP method**

  **•Role**

 **◉Environment-specific limits via configuration**

 **◉Proper 429 Too Many Requests responses**



**📊 Event Ingestion \& Monitoring**

 **◉APIs to ingest user activity events**

 **◉Paginated event retrieval**

 **◉Admin APIs for:**

  **•Cross-tenant event access**

  **•Company-wise analytics**

  **•Event counts**



**🧪 Testing \& Quality**

 **◉Integration tests using Testcontainers**

 **◉Real PostgreSQL \& Redis spun up in Docker during tests**

 **◉No mocks for infrastructure components**

 **◉Deterministic rate-limit testing via test profiles**



**🏗️ High-Level Architecture**



Client / Company Backend

&nbsp;       |

&nbsp;       |  (JWT Authenticated Requests)

&nbsp;       v

+-----------------------------+

|  Spring Boot REST API       |

|                             |

|  - Controllers              |

|  - Services                 |

|  - TenantContext            |

|  - JWT Filter               |

|  - Rate Limiting Filter     |

+-----------------------------+

&nbsp;       |

&nbsp;       |---------------------|

&nbsp;       |                     |

&nbsp;       v                     v

+----------------+     +----------------+

| PostgreSQL     |     | Redis          |

| (Event Data)   |     | (Rate Limits)  |

+----------------+     +----------------+



**🧠 Design Decisions (Senior-Level)**

 **◉Stateless Security**

   **JWT used instead of sessions to support horizontal scaling.**

 **◉TenantContext Pattern**

   **Ensures company isolation without duplicating code.**

 **◉Redis for Rate Limiting**

   **Chosen for atomic operations, TTL support, and distributed safety.**

 **◉Configuration-Driven Limits**

   **Rate limits externalized using @ConfigurationProperties for flexibility.**

 **◉Integration Tests over Unit Mocks**

   **Testcontainers used to validate real infrastructure behavior.**   



**🧪 Current Project Status**

 **✔ Entity Layer – Completed**

 **✔ Repository Layer – Completed**

 **✔ Service Layer – Completed**

 **✔ Controller Layer – Completed**



**✔ Authentication \& Authorization**

 **◉JWT authentication implemented**

 **◉Role-based access control**

 **◉Multi-tenant isolation enforced**



**✔ Security Enhancements**

 **◉Redis-based rate limiting**

 **◉Abuse protection for APIs**

 **◉Proper HTTP error handling**



**✔ Admin Monitoring APIs**

 **◉Paginated event retrieval**

 **◉Company-wise analytics**

 **◉Cross-tenant access for admins**



**🚧 Testing**

 **◉Integration testing setup completed**

 **◉Auth, event ingestion, and rate-limit tests implemented**

 **◉Testcontainers used for PostgreSQL \& Redis**



**🚧 Planned Enhancements**

 **◉Sliding-window rate limiting**

 **◉Metrics (Micrometer + Prometheus)**

 **◉OpenAPI / Swagger documentation**                                                         

 

**## ▶️ Running the Application Locally**



**### Prerequisites**

**- Java 17+**

**- Docker \& Docker Desktop**

**- Maven**



**### Steps**

**1. Clone the repository**

**2. Start Redis using Docker:**

   **docker run -d -p 6379:6379 redis:7**

**3. Configure PostgreSQL credentials in application.yml**

**4. Run the Spring Boot application**

**5. Access APIs via Postman or Swagger (planned)**



**## 🔗 API Overview**



**Authentication**

**- POST /api/auth/login**



**Event APIs**

**- POST /api/events**

**- GET /api/events (paginated)**



**Admin APIs**

**- GET /api/admin/events**

**- GET /api/admin/events/company/{companyId}**

**- GET /api/admin/events/count**



**## 🔐 Security Considerations**



**- Stateless JWT authentication prevents session fixation attacks**

**- Rate limiting protects against brute-force and abuse scenarios**

**- Tenant isolation ensures no cross-company data leakage**

**- Role-based access restricts admin-only operations**



**## 🧠 Technology Choices**



**- PostgreSQL chosen for strong consistency and transactional guarantees**

**- Redis used for rate limiting due to atomic operations and TTL support**

**- JWT preferred over sessions for horizontal scalability**

**- Testcontainers ensure tests validate real infrastructure behavior**



**## 🚀 Future Roadmap**



**- Sliding-window rate limiting for smoother traffic control**

**- Metrics \& monitoring with Prometheus and Grafana**

**- OpenAPI documentation for easier client integration**

**- Usage-based analytics and billing hooks for SaaS adoption**



**## 🤝 Contribution \& Learning**



**This project was built to explore real-world backend challenges such as**

**security, scalability, and multi-tenant architecture.**

**Feedback and improvements are welcome.**



**## 📌 Final Note**



**This project focuses on building a secure and scalable backend foundation**

**rather than a feature-heavy prototype. It emphasizes clean architecture,**

**defensive security practices, and production-readiness, making it suitable**

**as a base for real-world SaaS platforms.**

                        

