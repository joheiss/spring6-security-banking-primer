docker run -p 3306:3306 --name usersdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=usersdb -d mysql

# keycloak
docker run -d -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:25.0.2 start-dev