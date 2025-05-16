-- Table for department
IF
NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'department')
BEGIN
CREATE TABLE department
(
    department_id   BIGINT IDENTITY(1,1) PRIMARY KEY,
    department_name VARCHAR(255) NOT NULL
);
END
GO
-- Table for employee
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'employee' AND xtype = 'U')
BEGIN
CREATE TABLE employee
(
    employee_id   BIGINT IDENTITY(1,1) PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    department_id BIGINT,
    FOREIGN KEY (department_id) REFERENCES department (department_id)
);
END
GO
-- Table for post
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'post' AND xtype = 'U')
BEGIN
CREATE TABLE post
(
    post_id     BIGINT IDENTITY(1,1) PRIMARY KEY,
    content     VARCHAR(255) NOT NULL,
    created_at  DATETIME DEFAULT GETDATE(),
    employee_id BIGINT,
    FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE
);
END
GO
-- Table for skill
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'project' AND xtype = 'U')
BEGIN
CREATE TABLE project
(
    project_id   BIGINT IDENTITY(1,1) PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL
);
END
GO
-- Table for employee_project
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'employee_project' AND xtype = 'U')
BEGIN
CREATE TABLE employee_project
(
    employee_id BIGINT,
    project_id  BIGINT,
    PRIMARY KEY (employee_id, project_id),
    FOREIGN KEY (employee_id) REFERENCES employee (employee_id),
    FOREIGN KEY (project_id) REFERENCES project (project_id)
);
END
GO
-- Table for address
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'address' AND xtype = 'U')
BEGIN
CREATE TABLE address
(
    address_id  BIGINT IDENTITY(1,1) PRIMARY KEY,
    street      VARCHAR(255) NOT NULL,
    city        VARCHAR(255) NOT NULL,
    employee_id BIGINT,
    FOREIGN KEY (employee_id) REFERENCES employee (employee_id) ON DELETE CASCADE
);
END
GO
-- Table for post_images
IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'post_images' AND xtype = 'U')
BEGIN
CREATE TABLE post_images
(
    id        BIGINT IDENTITY(1,1) PRIMARY KEY,
    post_id   BIGINT,
    image_url TEXT,
    FOREIGN KEY (post_id) REFERENCES post (post_id) ON DELETE CASCADE
);
END
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'role' AND xtype = 'U')
BEGIN
CREATE TABLE role (
                      role_id BIGINT IDENTITY (1,1) PRIMARY KEY,
                      name VARCHAR(255) NOT NULL
)
END
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'users' AND xtype = 'U')
BEGIN
CREATE TABLE users (
                       user_id BIGINT IDENTITY (1,1) PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       passwordCount int NOT NULL ,

)
END
GO

IF NOT EXISTS (SELECT * FROM sysobjects WHERE name = 'users_role' AND xtype = 'U')
BEGIN
CREATE TABLE users_role (
                            user_id BIGINT,
                            role_id BIGINT,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(user_id),
                            FOREIGN KEY (role_id) REFERENCES role(role_id)
)
END
GO




