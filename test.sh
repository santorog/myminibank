curl -X POST "http://localhost:8080/accounts" -H "Content-Type: application/json" -d '{"name": "Bob"}'
curl -X POST "http://localhost:8080/accounts/1/deposit?amount=500"

curl -X POST "http://localhost:8080/accounts" -H "Content-Type: application/json" -d '{"name": "Alice"}'
curl -X POST "http://localhost:8080/accounts/2/deposit?amount=500"

curl -X POST "http://localhost:8080/accounts/1/transfer?to=2&amount=100.0&reference=happy%20birthday!"

curl -X GET "http://localhost:8080/transactions/1"
#curl -X GET "http://localhost:8080/transactions/2"