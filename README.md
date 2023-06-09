# mile2

Wallet Management

1. Create Wallet: API which will create wallet for a user

● url:http://localhost:8080/wallet
● METHOD : POST
● input: phone number
● Authentication Token {{JWT}}
● Validations : phone number should exist , only one wallet for a user.
● After creation push event in kafka

2.API to transfer money from one wallet to another wallet (p2p).
 
url:http://localhost:8080/transaction
METHOD : POST input:{payer_phone_number,payee_phone_number,amount}
Validations : payer and payee both should exist, payer should have sufficient balance.
After transfer push event in kafka
 
3.Transaction Summary API

● url:http://localhost:8080/transaction?userId=<userId>
● METHOD: GET
● Validations: userId should exists
● Note : this api should return in a pagination way.
 
4.Transaction Status
  
● url:http://localhost:8080/transaction?txnId=<txnID>
● Method :GET
● Validation: TransactionId should exists
Expectations: =============
1. Flow Diagram (Visual Paradigm)
2. Schema Design
3. All Apis should have authentication {{JWT}}
4. Code with proper comment
5. Junit Test cases
6. Debugging through log4j
