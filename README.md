# aerolineaDonBosco Application

This is a Java-based RESTful API for managing flights, users, and reservations. The project uses Spring Boot for backend development, including Spring Security for authentication and authorization.

Key Features
User Authentication: Supports user login and registration with JWT-based authentication.
Role Management: Users can be assigned roles like ADMIN and USER.
Flight Management: Handle flights, routes, and passenger details.
Reservations: Users can manage flight reservations.
CORS Configuration: Configured to allow front-end requests from specific origins.

# Project Structure

Controllers:

AuthController: Manages user authentication (login/register).
UserController: Handles user-related operations (CRUD for users, role management).

# Models:

User: Represents users with roles and credentials.
Flight, Passengers, Routes, Reservation: Entities related to flight booking and reservations.
Security:

WebSecurityConfig: Configures security filters and session management.
AuthTokenFilter: JWT token filtering for authentication.
JwtService: Generates and validates JWT tokens.
Repositories:

Interfaces for database operations, such as IUserRepository, IFlightRepository, etc.
DTOs (Data Transfer Objects):

LoginRequest, RegisterRequest, AuthResponse: Used for login, registration, and authentication responses.
API Endpoints:

POST /api/auth/login: Login to the system.
POST /api/auth/register: Register a new user.
GET /api/user/getUsername: Fetch user details by username.

# Dependencies

Spring Boot
Spring Security
JWT
Lombok
JPA (Hibernate)