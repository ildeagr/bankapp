# 🏦 Bank Java Backend ☕️

<!-- ## 🌐 Background

You have just joined CaixaBank Tech, the technology arm of the CaixaBank group, to work on an exciting and innovative project. The team is currently composed of only one architect, who has brought you on board specifically to design the backend for a new extension of the CaixaBankNow and Imagin apps. This extension will allow clients to manage their transactions quickly, focusing on the stock market, while also integrating a robo-advisor system that will enable automatic stock trading.
Before the frontend development team gets involved, your task is to validate the feasibility of building this backend infrastructure. The aim is to ensure the system can support seamless transaction management and automate stock trading operations. Your expertise will be critical in shaping the core backend architecture that will serve as the foundation for future development.

## 📂 Repository Structure

```bash
hackathon-caixabank-backend-java-bankingapp/
├── docker-compose.yml
├── Dockerfile
├── mvnw
├── mvnw.cmd
├── pom.xml
├── README.md
└── src
    └── main
       ├── java
       │   └── com
       │       └── hackathon
       │           └── bankingapp
       │               ├── BankingappApplication.java
       │               ├── Config
       │               ├── Controllers
       │               ├── DTO
       │               ├── Entities
       │               ├── Exceptions
       │               ├── Repositories
       │               ├── Security
       │               ├── Services
       │               └── Utils
       └── resources
           └── application.properties
```
-->

## 🎯 Tasks 

0. **Task 0**: Dockerfile
1. **Task 1**: User Actions
2. **Task 2**: Password Reset and OTP
3. **Task 3**: PIN Creation and Management
4. **Task 4**: Account Transactions
5. **Task 5**: Market Operations
6. **Task 6**: Security
7. **Task 7**: Error Handling
8. **Task 8**: Subscriptions and Trading Bot


### 📑 Detailed information about tasks

#### Task 0: Dockerfile

The first thing to do is to configure the Dockerfile to be able to test the application in containers.

#### Task 1: User Actions

This task focuses on basic user-related actions such as registering a new user, logging in, retrieving user and account details, and logging out. For these actions, you will need to interact with several endpoints, some of which require authentication.

- **User Registration**: User Registration: The functionality is implemented to register a user by submitting the required information such as name, email, phone number and password. This registration will return the account number, which will be used for future operations.

    Request body:
    ```json
    {
        "name":"Nuwe Test",
        "password":"NuweTest1$",
        "email":"nuwe@nuwe.com",
        "address":"Main St",
        "phoneNumber":"666888116"
    }
    ```
    
    Response:
    ```json
    {
        "name": "Nuwe Test",
        "email": "nuwe@nuwe.com",
        "phoneNumber": "666888116",
        "address": "Main St",
        "accountNumber": "19b332",
        "hashedPassword": "$2a$10$vYWBxACqEIPeoT0O5b0faOHp4ITAHSBvoHDzBePW7tPqzpvqKLi6G"
    }
    ```
    The application automatically creates and assigns the UUID type account number to the created customer.
    
    Checks should include:
    - No empty fields.
    - The email format must be valid.
    - Password rules to be detailed later.
    - Check if the email or phoneNumber already exists.


- **User Login**: A login mechanism is implemented using an email or account number along with a password. After successful authentication, the system should return a JWT token, which will be used for all protected endpoints.
  
    Request body:
    ```json
    {
        "identifier":"nuwe@nuwe.com",
        "password":"NuweTest1$"
    }
    ```
    
    Response:
    ```json
    {
        "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxOWIzMzIiLCJpYXQiOjE3Mjk1NzEzNzUsImV4cCI6MTcyOTY1Nzc3NX0.6qLQi50B1StobsUusfxCSqLdKeKOYdBZ3qj5Lw5G9eAdqoV1Juz3jyh2xwWByG7iJtusrhYPb_I62ycptcH4MA"
    }
    ```

    If the identifier is invalid it returns the following with Status Code 400:

    ```
    User not found for the given identifier: nuwee@nuwe.com 
    ```

    If the password is invalid it returns the following with Status Code 401:

    ```
    Bad credentials
    ```

- **Get User Info**: Once logged in, use the JWT token to retrieve detailed user information (e.g., name, email, account number).
    Response:
    ```json
    {
        "name": "Nuwe Test",
        "email": "nuwee@nuwe.com",
        "phoneNumber": "666888116",
        "address": "Main St",
        "accountNumber": "19b332",
        "hashedPassword": "$2a$10$vYWBxACqEIPeoT0O5b0faOHp4ITAHSBvoHDzBePW7tPqzpvqKLi6G"
    }
    ```
- **Get Account Info**: Fetch account information such as the account balance. You must be logged in.
    Response:
    ```json
    {
        "accountNumber": "19b332",
        "balance": 0.0
    }
    ```
- **Logout**: Implement a logout system that invalidates the JWT token, ensuring that users cannot access protected endpoints anymore.

<!-- #### Task 2: Password Reset and OTP

This task involves implementing password reset functionality using One-Time Passwords (OTPs). It tests the ability to send OTPs via email, verify them, and reset the user’s password.

- **Send OTP**: Create a mechanism that sends an OTP to the user’s registered email for password reset purposes.
    Request body:
    ```json
    {
        "identifier":"nuwe@nuwe.com"
    }
    ```
    Response:
    ```json
    {
        "message": "OTP sent successfully to: nuwee@nuwe.com"
    }
    ```
    You must send an email to the user with the message:
    OTP:XXXXXX where X are numbers


- **Verify OTP**: Implement functionality to verify the OTP provided by the user. Upon successful verification, a reset token should be generated.
    Request body:
    ```json
    {
        "identifier":"nuwe@nuwe.com",
        "otp":"893392"
    }
    ```
    Response:
    ```json
    {
        "passwordResetToken": "9b1ae6c5-c247-434f-a8e7-893b026db107"
    }
    ```

- **Reset Password**: After verifying the OTP, the user can reset their password using the reset token.
    Request body:
    ```json
    {
        "identifier":"nuwe@nuwe.com",
        "resetToken": "9b1ae6c5-c247-434f-a8e7-893b026db107",
        "newPassword": "PassTest1$"
    }
    ```
    Response:
    ```json
    {
        "message": "Password reset successfully"
    }
    ```

- **Test New Password**: Ensure the new password works by logging in with the updated credentials.

#### Task 3: PIN Creation and Management

This task focuses on creating, updating, and verifying PINs for sensitive transactions. This PIN must be used for all transactions. These endpoints must require JWT auth.

- **Create PIN**: Implement a functionality to create a PIN associated with the user's account. This PIN will be used in transactions like deposit, withdrawal, and transfers.
    Request body:
    ```json
    {
        "pin":"1810",
        "password":"PassTest1$"
    }
    ```
    Response:
    ```json
    {
        "msg": "PIN created successfully"
    }
    ```

- **Update PIN**: Users should have the ability to update their existing PIN by providing their old PIN and account password.
    Request body:
    ```json
    {
        "oldPin":"1810",
        "password":"PassTest1$",
        "newPin": "1811"
    }
    ```
    Response:
    ```json
    {
        "msg": "PIN updated successfully"
    }
    ```

- **Create PIN for Other Accounts**: Test the creation of a PIN for another account to ensure functionality across multiple accounts.

#### Task 4: Account Transactions

This task involves implementing basic financial transactions such as deposits, withdrawals, and fund transfers. Additionally, it includes viewing transaction history.

For any transaction, it must be verified that there are sufficient funds. If there are not sufficient funds, the message must be displayed: Insufficient balance

All transactions should require a JWT.

- **Deposit Money**: Create functionality that allows users to deposit money into their account using the correct PIN.
    Request body:
    ```json
    {
        "pin":"1811",
        "amount":"100000.0"
    }
    ```
    Response:
    ```json
    {
        "msg": "Cash deposited successfully"
    }
    ```
    Check that the funds have been correctly added to the database.

- **Withdraw Money**: Implement a system where users can withdraw money from their account using their PIN.
    Request body:
    ```json
    {
        "amount":20000.0,
        "pin":"1811"
    }
    ```
    Response:
    ```json
    {
        "msg": "Cash withdrawn successfully"
    }
    ```
    Check that the funds have been correctly withdrawn to the database.

- **Fund Transfer**: Enable the transfer of funds from one account to another using account numbers and PIN verification.
    Request body:
    ```json
    {
        "amount": 1000.0,
        "pin": "1811",
        "targetAccountNumber": "ed9050"
    }
    ```
    Response:
    ```json
    {
        "msg": "Fund transferred successfully"
    }
    ```

- **Transaction History**: Implement a feature that allows users to view the complete history of transactions related to their account.
    Response:
    ```json
    [
        {
            "id": 3,
            "amount": 1000.0,
            "transactionType": "CASH_TRANSFER",
            "transactionDate": 1729573542375,
            "sourceAccountNumber": "19b332",
            "targetAccountNumber": "ed9050"
        },
        {
            "id": 2,
            "amount": 20000.0,
            "transactionType": "CASH_WITHDRAWAL",
            "transactionDate": 1729573356164,
            "sourceAccountNumber": "19b332",
            "targetAccountNumber": "N/A"
        },
        {
            "id": 1,
            "amount": 100000.0,
            "transactionType": "CASH_DEPOSIT",
            "transactionDate": 1729573225112,
            "sourceAccountNumber": "19b332",
            "targetAccountNumber": "N/A"
        }
    ]
    ```
    The types of transactions that must be supported by the app are:
    - CASH_WITHDRAWAL
    - CASH_DEPOSIT
    - CASH_TRANSFER
    - SUBSCRIPTION
    - ASSET_PURCHASE
    - ASSET_SELL

    The transaction date must also be recorded in the database.

#### Task 5: Market Operations

This task focuses on operations related to the stock market, including buying and selling assets, viewing real-time prices, and calculating net worth.

Endpoints that take actions on user accounts must require the PIN and the JWT. Endpoints that are merely informative will be public.

For both buying and selling, the values obtained in real time using the API provided must be taken.

For both buying and selling, it is necessary to carry out the necessary operations so that the assets obtained with the amount invested are kept in the account.

- **Buy Assets**: Implement functionality to allow users to buy assets (e.g., stocks) by specifying the asset symbol, the amount to invest, and the user's PIN.
    Request body:
    ```json
    {
        "assetSymbol": "GOLD",
        "pin": "1811",
        "amount": 1000.0
    }
    ```
    Response:
    ```
    Asset purchase successful.
    ```
    Response of user asset informational endpoint:
    ```json
    {
    "GOLD": 0.6829947955796576
    }
    ```
    In case of error or lack of funds, a status code 500 with message is returned:
    ```
    Internal error occurred while purchasing the asset.
    ```

    The purchase price should also be stored, so that a profit or loss can be calculated in case of a sale.

    An email with the subject `Investment Purchase Confirmation` should also be sent in the following format:

    ```
    Dear Nuwe Test,

    You have successfully purchased 0.14 units of GOLD for a total amount of $50.00.

    Current holdings of GOLD: 0.53 units

    Summary of current assets:
    - GOLD: 0.53 units purchased at $1160.70

    Account Balance: $63376.87
    Net Worth: $63560.59

    Thank you for using our investment services.

    Best Regards,
    Investment Management Team
    ```

- **Sell Assets**: Enable users to sell assets from their portfolio, specifying the quantity to sell and verifying with the user's PIN.
    Request body:
    ```json
    {
        "assetSymbol": "GOLD",
        "pin": "1811",
        "quantity": 0.3
    }
    ```
    Response:
    ```
    Asset sale successful.
    ```

    In case of error or lack of funds, a status code 500 with message is returned:
    ```
    Internal error occurred while selling the asset.
    ```
    Response of user asset informational endpoint:
    ```json
    {
    "GOLD": 0.3829947955796576
    }
    ```

    Using the stored purchase price, the performance of the transaction, profit or loss, must be calculated.

    An email with the subject `Investment Sale Confirmation` should also be sent in the following format:

    ```
    Dear Nuwe Test,

    You have successfully sold 0.30 units of GOLD.

    Total Gain/Loss: $87.63

    Remaining holdings of GOLD: 0.38 units

    Summary of current assets:
    - GOLD: 0.38 units purchased at $1464.14

    Account Balance: $78526.87
    Net Worth: $79199.50

    Thank you for using our investment services.

    Best Regards,
    Investment Management Team
    ```


- **Net Worth**: Provide users with an overview of their net worth by combining cash balance and asset holdings.
    Response:
    ```
    79061.08163071838
    ```
- **Real-time Market Prices**: Implement endpoints to fetch current market prices for individual assets and the entire available market. This information should be obtained from the indicated API.
    Response for all assets:
    ```json
    {
        "AAPL": 81.05,
        "GOOGL": 1082.33,
        "TSLA": 75.71,
        "AMZN": 119.0,
        "MSFT": 161.23,
        "NFLX": 427.81,
        "FB": 11.68,
        "BTC": 8304.25,
        "ETH": 91.54,
        "XRP": 4.26,
        "GOLD": 1162.48,
        "SILVER": 4.24
    }
    ```
    Response for individual asset:
    ```
    1175.95
    ```

#### Task 6: Security

This task checks the security of the API by verifying access control for public and private endpoints.

- **Public Endpoints**: Ensure that public endpoints like login and registration are accessible without authentication.
- **Private Endpoints Without Authentication**: Verify that private endpoints return a 401 or 403 error if accessed without authentication. "Access denied" message should be displayed.
- **Private Endpoints With Authentication**: Ensure that private endpoints are accessible with a valid JWT token and perform the intended actions.
- **Password Security**: The password must be stored hashed using BCrypt.

#### Task 7: Error Handling

This task ensures that the application handles errors gracefully and provides appropriate feedback to the user.

- **Duplicate Email or Phone Number**: Ensure that attempting to register a user with an existing email or phone number results in a 400 error and an appropriate message.
- **Invalid Login Credentials**: Test that invalid login attempts (e.g., wrong email or password) return a 401 status with a "Bad credentials" message.
- **Password Validation**: Implement strong password validation rules and return specific error messages for violations.
    Requests bodies and responses:
    ```json
    {
        "name":"Nuwe Test",
        "password":"nuwetest1$",
        "email":"nuwe@nuwe.com",
        "address":"Main St",
        "phoneNumber":"666888115"
    }

    Response: Password must contain at least one uppercase letter

    {
        "name":"Nuwe Test",
        "password":"Nuwetest",
        "email":"nuwe@nuwe.com",
        "address":"Main St",
        "phoneNumber":"666888115"
    }

    Response: Password must contain at least one digit and one special character

    {
        "name":"Nuwe Test",
        "password":"Nuwetest1",
        "email":"nuweeee@nuwe.com",
        "address":"Main St",
        "phoneNumber":"666888115"
    }

    Response: Password must contain at least one special character

    {
        "name":"Nuwe Test",
        "password":"Nuwetest1 ",
        "email":"nuweeee@nuwe.com",
        "address":"Main St",
        "phoneNumber":"666888115"
    }

    Response: Password cannot contain whitespace

    {
        "name":"Nuwe Test",
        "password":"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa1$",
        "email":"nuweeee@nuwe.com",
        "address":"Main St",
        "phoneNumber":"666888115"
    }

    Response: Password must be less than 128 characters long

    {
        "name":"Nuwe Test",
        "password":"Test1$",
        "email":"nuweeee@nuwe.com",
        "address":"Main St",
        "phoneNumber":"666888115"
    }

    Response: Password must be at least 8 characters long
    ```

- **Email Format Validation**: Implement strong email format validation rules.
    Request body:
    ```json
    {
        "name":"Nuwe Test",
        "password":"TestTest1$",
        "email":"nuwenuwe",
        "address":"Main St",
        "phoneNumber":"666888115"
    }
    ```
    Response:
    ```
    Invalid email: nuwenuwe
    ```
- **Insufficient money in account**: Any transaction for which there are insufficient funds should trigger the text: **Insufficient balance** with a status code 400.

### Task 8: Subscriptions and Trading Bot

This task focuses on advanced features such as creating automatic subscriptions and enabling a trading bot to handle investments.

- **Create Subscription**: Implement a feature that allows users to subscribe to periodic payments at a set interval. In this case the interval will be seconds, in order to be able to check the correct functioning of the application.
    Request body:
    ```json
    {
        "pin": "1811",
        "amount":"100",
        "intervalSeconds": 5
    }
    ```
    Response:
    ```
    Subscription created successfully.
    ```
    The proper functioning of the subscriptions depends on whether it is created correctly, executed at the indicated interval simulating a subscription or direct debit and the amount of money in the account decreases until there is nothing left.

    These transactions should also be saved as other transactions with the appropriate transaction type mentioned above.

- **Auto-Invest Bot**: Enable users to activate an auto-investment bot that automatically buys or sells assets based on market conditions (e.g., price fluctuations).
    Request body:
    ```json
    {
        "pin": "1811"
    }
    ```
    Response:
    ```
    Automatic investment enabled successfully.
    ```

    In this case, rules must be created to automatically buy or sell assets based on market fluctuations, i.e. the prices returned by the API in real time. It is recommended to use rules with small variations to test the correct functioning.

    You should use a time interval that does not compromise the performance of the application, for example 30 seconds. That is, every 30 seconds the bot checks if there has been any fluctuation in the market for the assets owned by the user. If so, it buys or sells depending on whether the asset falls or rises.

    Case study:

    The user has GOLD and has bought it at 1000. If the price drops to 800, he buys a small amount as it could appreciate in value. If the price rises to 1200, he sells a part of the assets to get profitability.
    
    This profitability must be calculated.
-->

## 💫 Guides

### Endpoints Table

| Endpoint                                    | Method | Params/Body                                                 | Requires Auth | Response Codes                              | Description                                                                                 |
|---------------------------------------------|--------|--------------------------------------------------------------|---------------|---------------------------------------------|---------------------------------------------------------------------------------------------|
| `/api/users/register`                       | POST   | `{ name, password, email, address, phoneNumber, countryCode }`| No            | 200, 400 ("Email already exists", "Phone number already exists") | Registers a new user.                                                                       |
| `/api/users/login`                          | POST   | `{ identifier, password }`                                   | No            | 200, 401 ("Bad credentials")                | Logs in the user and returns a JWT token.                                                   |
| `/api/auth/password-reset/send-otp`         | POST   | `{ identifier }`                                             | No            | 200, 400                                   | Sends an OTP for password reset.                                                            |
| `/api/auth/password-reset/verify-otp`       | POST   | `{ identifier, otp }`                                        | No            | 200, 400 ("Invalid OTP")                    | Verifies the OTP and returns a reset token.                                                 |
| `/api/auth/password-reset`                  | POST   | `{ identifier, resetToken, newPassword }`                     | No            | 200, 400 ("Invalid reset token")            | Resets the user's password.                                                                 |
| `/api/dashboard/user`                       | GET    | N/A                                                          | Yes           | 200, 401 ("Access Denied")                  | Retrieves the logged-in user's details.                                                     |
| `/api/dashboard/account`                    | GET    | N/A                                                          | Yes           | 200, 401 ("Access Denied")                  | Retrieves account information, including balance.                                           |
| `/api/account/deposit`                      | POST   | `{ amount, pin }`                                            | Yes           | 200, 401, 403 ("Invalid PIN"), 500          | Deposits a specific amount into the user's account.                                         |
| `/api/account/withdraw`                     | POST   | `{ amount, pin }`                                            | Yes           | 200, 401, 403 ("Invalid PIN"), 500          | Withdraws a specific amount from the user's account.                                        |
| `/api/account/fund-transfer`                | POST   | `{ targetAccountNumber, amount, pin }`                        | Yes           | 200, 401, 403 ("Invalid PIN"), 500          | Transfers funds to another account.                                                         |
| `/api/account/transactions`                 | GET    | N/A                                                          | Yes           | 200, 401                                   | Retrieves the user's transaction history.                                                   |
| `/api/account/buy-asset`                    | POST   | `{ assetSymbol, amount, pin }`                               | Yes           | 200, 401, 403 ("Invalid PIN"), 500          | Buys a specified asset for the user.                                                        |
| `/api/account/sell-asset`                   | POST   | `{ assetSymbol, quantity, pin }`                             | Yes           | 200, 401, 403 ("Invalid PIN"), 500          | Sells a specified asset for the user.                                                       |
| `/market/prices`                            | GET    | N/A                                                          | No            | 200, 500                                   | Retrieves current market prices for all assets.                                             |
| `/market/prices/{symbol}`                   | GET    | N/A                                                          | No            | 200, 500                                   | Retrieves the current market price for a specific asset.                                    |
| `/api/user-actions/subscribe`               | POST   | `{ amount, intervalSeconds, pin }`                           | Yes           | 200, 401, 403 ("Invalid PIN"), 500          | Creates a subscription for periodic payments.                                               |
| `/api/user-actions/enable-auto-invest`      | POST   | `{ pin }`                                                    | Yes           | 200, 400 ("PIN cannot be null or empty"), 401, 403 | Enables the auto-investment feature.                                                        |
| `/api/users/logout`                         | GET    | N/A                                                          | Yes           | 200, 401 ("Access Denied")                  | Logs out the user and invalidates the JWT token.                                            |

---
<!--
### API

https://faas-lon1-917a94a7.doserverless.co/api/v1/web/fn-e0f31110-7521-4cb9-86a2-645f66eefb63/default/market-prices-simulator

### More information

The [application.properties](src/main/resources/application.properties) file contains the configuration necessary for the correct functioning of the application.

## 📊 Evaluation

- **Task 1**: 200 points
- **Task 2**: 200 points
- **Task 3**: 100 points
- **Task 4**: 250 points
- **Task 5**: 250 points
- **Task 6**: 100 points
- **Task 7**: 100 points
- **Task 8**: 400 points
- **Code quality**: 400 points

-->













