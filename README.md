# Book store
<p>
  <img style="width: 200px" src="https://res.cloudinary.com/dkegqlchp/image/upload/v1707201483/logo_quahdr.png" alt="Book">
</p>
This project includes the following features:

## Features

* Advanced search
* View book details
* Cart and checkout
* Account management
* View purchase history
* Bookmark favorite books
* Edit customer information

### Management features

* Manage books
* Manage orders
* Manage authors
* Manage categories
* Manage users
* Manage banners
* Manage book sets
* Reporting and statistics

### Additional features

* Multilingual support

## Technologies

* Spring Boot 3
* Spring Security
* JSON Web Tokens (JWT)
* BCrypt
* Maven
* Swagger v3
* Redis
* Mail
* Validation
* Cloudinary

## Getting Started

To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+ To build and run the project, follow these steps:
* Clone the repository:
   ```bash
   git clone https://github.com/Tuyenxngoc/book360-backend.git
* Navigate to the project directory: cd book360-backend
* Add file config.properties: src/main/resources/config.properties
  ```bash
  # Cloudinary
  cloudinary.cloudName=yourCloudName
  cloudinary.apiKey=yourCloudKey
  cloudinary.apiSecret=yourCloudSecret
  # Admin info
  admin.username=tuyenngoc
  admin.password=admin
  admin.email=tuyenngoc@gmail.com
  admin.name=tuyenngoc
  admin.phoneNumber=0123456789
  # Gmail
  mail.username=yourEmail@gmail.com
  mail.password=yourPassword
  # Redis
  spring.data.redis.host=...
  spring.data.redis.port=...
  spring.data.redis.password=...
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run -> The application will be available at http://localhost:8080.
