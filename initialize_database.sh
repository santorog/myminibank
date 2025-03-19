curl -X POST "http://localhost:8080/accounts" -H "Content-Type: application/json" -d '{"name": "Bob"}'
curl -X POST "http://localhost:8080/accounts/1/deposit?amount=500"

curl -X POST "http://localhost:8080/accounts" -H "Content-Type: application/json" -d '{"name": "Alice"}'
curl -X POST "http://localhost:8080/accounts/2/deposit?amount=500"

