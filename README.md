
# brokerage-firm

The **brokerage-firm** application provides RESTful services to handle customer requests.

### Overview
The application responds to customer requests by offering a set of REST-based services. 
You can log in to the **login** endpoint using your credentials, which will generate a token for you. 
This token is valid for 1 hour and can be used as a **Bearer Token** to access other available services.

### Available Services:

1. **`/login/`**
    - Log in with your credentials and receive a Bearer token. This token is valid for 1 hour.

2. **`/wallet/assets`**
    - With the admin token, you can view all user assets.
    - This endpoint is parameterized. (user id filter for only admin user)

3. **`/wallet/withdrawal`**
    - You can make withdrawals to your regex-compatible IBAN.

4. **`/wallet/deposits`**
    - You can deposit money into your account.

5. **`/orders/`**
    - This endpoint allows you to check the orders.
    - It is parameterized. (Date range for all, user id filter for only admin user)

6. **`/orders/create`**
    - You can create a new buy or sell order.

7. **`/orders/delete`**
    - You can cancel an existing order.

---

## Getting Started

### Prerequisites
Ensure the following tools are installed on your system:
- **Java 17**
- **Spring Boot 3.3.5**
- **Maven 3.8**
- **H2 db** (Database)

### Setup Steps
1. **Clone the Repository**
    ```bash
    git clone https://github.com/mustafakayass/brokerage-firm.git
    cd brokerage-firm
    ```

2. **Install Dependencies**
   Use Maven to install dependencies:
    ```bash
    mvn clean install
    ```

3. **Set Environment Variables**
   Create a `.env` file or configure `application.yml` in the root directory:
    ```yml
       datasource:
        url: jdbc:h2:mem:testdb
        username: sa
        password: password
    ```
---

## Running the Application

### Locally
Start the application using:
```bash
mvn spring-boot:run
```

## API Usage
Here are some example API calls.

### 1. Login service
- **URL:** `POST /api/v1/login`
- **Body:**
    ```json
    {
        "username": "test",
        "password": "test123"
    }
    ```
- **Response**
- response gives you a token that you can use it for 1 hour to login the other services.
- also if you are an admin user you can access and manipulate all user' data. 
- (default admin credentials: admin, admin123) 
- (default test credentials: test, test123)

### 2. Get Orders List
- **URL:** `GET /api/v1/orders`
- **Parameters:**
    - `startDate` (optional): Start date (format: `yyyyMMdd`) (ex. 20241111)
    - `endDate` (optional): End date (format: `yyyyMMdd`) (ex. 20241119)
    - `userId` (optional): End date (format: `int`) (ex. 1) 
Note: (userId designed only for admin, If the user enters a value other than their own id in this field, it will not affect the query)
- **Example Call:**
    ```bash
    curl -X GET "http://localhost:8080/api/v1/orders?startDate=20241111&endDate=20241119&userId=2" -H "accept: application/json"
    ```

### 3. Create a New Order
- **URL:** `POST /api/v1/orders`
- **Body:**
    ```json
    {
    "userId": "1",
    "price": "2500",
    "size": "4",
    "assetName": "XAU",
    "orderSide": "B"
    }
    ```
### 4. Delete an order
- **URL:** `POST /api/v1/orders/delete`
- **Body:**
    ```json
    {
    "userId": "2",
    "orderId":"2"
    }
    ```

### 5. View assets in the wallet
- **URL:** `GET /api/v1/wallet/assets`
- **Parameters:**
    - `userId` (optional): End date (format: `int`) (ex. 1)
      Note: (userId designed only for admin, If the user enters a value other than their own id in this field, it will not affect the query)
- **Example Call:**
    ```bash
    curl -X GET "http://localhost:8080/api/v1/wallet/assets?userId=1" -H "accept: application/json"
    ```

### 6. Withdrawal TRY to IBAN
- **URL:** `POST /api/v1/wallet/withdrawals`
- **Body:**
    ```json
    {
    "userId":"1",
    "amount":"1000",
    "iban": "TR320010009999901234567890"
    }
    ```

### 7. Deposit TRY to wallet
- **URL:** `POST /api/v1/orders/delete`
- **Body:**
    ```json
    {
    "userId":"2",
    "amount":"220"
    }
    ```

---

## Notes

- The application runs on port **8080**.
- H2 database console URL: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- H2 credentials are located in the `application.yml` file.


