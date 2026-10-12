# Library Management System

A college-level full-stack Library Management System with two role-based dashboards:

- Admin / Librarian Dashboard
- Student / Member Dashboard

## Stack
- Backend: Java 17, Spring Boot 3.2.5, Spring Data JPA, Hibernate, Maven
- Frontend: HTML, CSS, JavaScript, Bootstrap 5
- Database: MySQL 8
- Deployment: Render (backend) + Vercel (frontend)

## Demo Accounts
**Admin**
- Email: `admin@library.com`
- Password: `admin123`

**Student**
- Email: `student@library.com`
- Password: `student123`

## Project Structure
```text
library-management-system/
├── backend/
│   ├── src/main/java/com/library/
│   ├── src/main/resources/
│   ├── pom.xml
│   └── Dockerfile
├── frontend/
│   ├── index.html
│   ├── css/style.css
│   ├── js/app.js
│   └── vercel.json
├── database/
│   ├── schema.sql
│   └── sample-data.sql
├── .gitignore
└── README.md
```

## Local Setup
1. Create a MySQL database named `library_db`.
2. Run `database/schema.sql`, then `database/sample-data.sql`.
3. Set environment variables:
   - `DB_URL=jdbc:mysql://localhost:3306/library_db`
   - `DB_USERNAME=root`
   - `DB_PASSWORD=your_password`
4. From `backend`, run:
   ```bash
   mvn spring-boot:run
   ```
5. Open `frontend/index.html` with a local static server.

## API
- `POST /api/auth/login`
- `GET /api/books`
- `POST /api/books`
- `PUT /api/books/{id}`
- `DELETE /api/books/{id}`
- `GET /api/members`
- `GET /api/issues`
- `POST /api/issues`
- `PUT /api/issues/{id}/return`
- `GET /api/reservations`
- `POST /api/reservations`
- `GET /api/notices`
- `POST /api/notices`
- `GET /api/dashboard/stats`

## Render
Deploy the `backend` directory using the included Dockerfile. Set:
`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`.

The backend listens on Render's `PORT` environment variable.

## Vercel
Deploy the `frontend` directory. In `frontend/js/app.js`, change `API_BASE_URL` to the deployed Render backend URL.

## GitHub
```bash
git init
git add .
git commit -m "Initial Library Management System"
git branch -M main
git remote add origin YOUR_GITHUB_REPOSITORY_URL
git push -u origin main
```

## Notes
This package is intentionally simple and viva-friendly. It uses REST APIs and role-based frontend routing, with backend role checks on protected write operations.
