# SpringSecurity-Basics-InMemoryDB

A learning project for understanding Spring Boot Security - how authentication and authorization work in web applications.

## 📖 About This Project

This is my personal learning project where I explore Spring Security fundamentals. I created this to understand how to implement authentication, authorization, and session management in Spring Boot applications using in-memory database.

## 🎯 What I'm Learning

Through this project, I'm understanding:

- **Authentication** - Verifying user identity (who are you?)
- **Authorization** - Controlling access based on roles (what can you access?)
- **BCrypt Password Encoding** - Securing passwords instead of storing plain text
- **Session Management** - Managing user sessions and concurrent logins
- **Role-Based Access Control (RBAC)** - Different users, different permissions
- **Form-Based Login** - Implementing login/logout functionality

## ✨ Features Implemented

✅ BCrypt password encoding for secure password storage  
✅ In-memory user authentication (easily extendable to database)  
✅ Two user roles - USER and MANAGER  
✅ URL-based authorization rules  
✅ Form-based login with remember me functionality  
✅ Logout functionality  
✅ Custom access denied page  
✅ Session timeout configuration (30 minutes)  
✅ Concurrent session control (max 2 sessions per user)

## 🛠 Tech Stack

- Java 8
- Spring Boot 2.x
- Spring Security 5.x
- BCrypt Password Encoder
- Maven
- JSP (for views)

## 🚀 Getting Started

### Prerequisites

```bash
Java 8 or higher
Maven 3.6+
Your favorite IDE
```

### Installation

1. **Clone the repository**
```bash
git clone https://github.com/somguptaa/SpringSecurity-BootMVC2.x-InMemoryDB.git
cd SpringSecurity-BootMVC2.x-InMemoryDB
```

2. **Run the application**
```bash
mvn spring-boot:run
```

3. **Access the application**
```
http://localhost:8080
```

## 👤 Test Users

I configured two users for testing:

| Username | Password | Role | What They Can Access |
|----------|----------|------|---------------------|
| som | gupta | USER | Home, Offers, Check Balance |
| zakir | hyd | MANAGER | All pages including Approve Loan |

**Note:** Passwords are stored as BCrypt hashes in code, but you login with plain passwords.

## 🔐 Security Configuration

### URL Access Rules

```java
"/" and "/denied"     → Public (anyone can access)
"/offers"             → Authenticated users only
"/checkBalance"       → USER or MANAGER role required
"/approveloan"        → MANAGER role only
```

### Access Flow Example

```
User visits /approveloan
    ↓
Are they logged in? → No → Redirect to login
    ↓
Are they logged in? → Yes → Check role
    ↓
Has MANAGER role? → No → Show /denied page
    ↓
Has MANAGER role? → Yes → Show approve loan page
```

## 🔑 Password Encoding

### Why BCrypt?

- **Secure** - Cannot be reversed to get original password
- **Unique** - Same password generates different hash each time (salt)
- **Recommended** - Industry standard for password hashing

### Password Mappings

| User | Plain Password | BCrypt Hash |
|------|---------------|-------------|
| som | gupta | `$2a$10$HdiYik9N/S.GsTOZnlaAVelq8BRfMsteMzp3Clf4EVYMGu8eMbbgO` |
| zakir | hyd | `$2a$10$XCnGIGDSdnDLZNUv6SYH/OAnS0of7mcm2JYZp0O0vCmRV1WV1OWU6` |

### How to Generate BCrypt Hash

```java
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
String hash = encoder.encode("yourpassword");
System.out.println(hash);
```

Or use online tool: https://bcrypt-generator.com/

## 🧪 Testing the Application

### Test 1: Public Access
```
Visit: http://localhost:8080/
Expected: Home page loads ✅
```

### Test 2: Authentication
```
Visit: http://localhost:8080/offers
Expected: Redirects to login
Login: som / gupta
Expected: Access granted ✅
```

### Test 3: Role-Based Access (USER)
```
Login as: som (USER)
Visit: /checkBalance → ✅ Access granted
Visit: /approveloan → ❌ Access denied
```

### Test 4: Role-Based Access (MANAGER)
```
Login as: zakir (MANAGER)
Visit: /checkBalance → ✅ Access granted
Visit: /approveloan → ✅ Access granted
```

### Test 5: Remember Me
```
Login with "Remember Me" checked
Close browser
Reopen and visit site
Expected: Still logged in ✅
```

### Test 6: Session Limit
```
Login from Chrome → ✅ Success
Login from Firefox → ✅ Success
Login from Edge → ❌ Blocked (max 2 sessions)
```

## 📝 Key Concepts

### Authentication vs Authorization

**Authentication (Who are you?)**
- Verifies user credentials
- Creates session on success
- Example: som logs in with password

**Authorization (What can you access?)**
- Checks user's permissions
- Based on roles (USER, MANAGER)
- Example: Only MANAGER can approve loans

### Session Management

**Session Lifecycle:**
```
Login → Session Created (ID: ABC123)
    ↓
User Activity → Session Active
    ↓
30 min inactive → Session Expires
    ↓
Logout → Session Destroyed
```

**Concurrent Sessions:**
- Max 2 simultaneous logins per user
- 3rd login attempt is blocked
- Prevents account sharing

### Remember Me Feature

- Cookie lasts 48 hours
- User stays logged in after browser close
- Cleared only by manual logout

## 💡 What I Learned

### Before This Project
- Didn't understand authentication vs authorization
- Thought plain text passwords were okay
- No idea how sessions work
- Didn't know about role-based access

### After This Project
- ✅ Clear understanding of security fundamentals
- ✅ Know why BCrypt is essential
- ✅ Can implement role-based access control
- ✅ Understand session lifecycle
- ✅ Can configure login/logout flows
- ✅ Know how to handle access denied scenarios

## Issues I Faced

### Issue 1: Login Not Working
**Problem:** Used plain password with BCrypt encoder  
**Solution:** Generated BCrypt hash for passwords

### Issue 2: Access Denied Page Not Showing
**Problem:** Didn't configure exception handler  
**Solution:** Added `.exceptionHandling().accessDeniedPage("/denied")`

## 🔜 Next Steps

Planning to learn:
- [ ] Database-backed authentication (JPA/Hibernate)
- [ ] Custom login page design
- [ ] Method-level security (`@PreAuthorize`)
- [ ] JWT token authentication
- [ ] OAuth2 integration
- [ ] Password reset functionality
- [ ] Multi-factor authentication

## 📚 Resources

- [Spring Security Docs](https://docs.spring.io/spring-security/reference/)
- [BCrypt Generator](https://bcrypt-generator.com/)
- [Spring Boot Security Guide](https://spring.io/guides/gs/securing-web/)

## 🤝 Contributing

This is a learning project, but feedback and suggestions are always welcome!

Feel free to:
- Fork the repository
- Try different configurations
- Suggest improvements
- Report issues

## 📌 Note

This project uses `WebSecurityConfigurerAdapter` which is deprecated in Spring Security 5.7+. I'm using it for learning purposes to understand the basics. Future projects will use the modern `SecurityFilterChain` approach.

## 👤 Author

**Som Gupta**
- GitHub: [@somguptaa](https://github.com/somguptaa)

---

**Status:** 📚 Learning in Progress

If this project helps you understand Spring Security, give it a ⭐!

## 📄 License

This project is open source and available for learning purposes.
