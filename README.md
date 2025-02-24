# Cache Proxy

Cache Proxy é um servidor proxy simples em Java que armazena respostas em cache para melhorar a eficiência das requisições HTTP. Ele utiliza um mecanismo de cache compartilhado entre threads para reduzir o número de chamadas externas quando a mesma URL é solicitada repetidamente.

## Recursos

- Proxy HTTP simples
- Cache compartilhado entre threads usando `ConcurrentHashMap`
- Indica no cabeçalho da resposta se foi um cache HIT ou MISS
- Multithreaded utilizando `ExecutorService`

## Requisitos

- Java 17+
- Apache Maven

## Como Compilar e Executar

1. Clone este repositório:
   ```sh
   git clone https://github.com/andre-xizto/cache-proxy.git
   cd cache-proxy
   ```

2. Compile e empacote o projeto com Maven:
   ```sh
   mvn clean package
   ```

3. Execute o JAR gerado:
   ```sh
   java -jar target/cache-proxy-1.0-SNAPSHOT.jar 8080 https://dummyjson.com/products
   ```

    - O **primeiro argumento** é a porta do servidor.
    - O **segundo argumento** é a URL do site que será cacheado.

4. Agora, você pode fazer requisições ao proxy:
   ```sh
   curl -i http://localhost:8080
   ```

    - Se a resposta já estiver no cache, o cabeçalho `X-Cache: HIT` será retornado.
    - Caso contrário, a resposta virá da API original com `X-Cache: MISS` e será armazenada.

## Exemplo de Resposta

**Primeira requisição (MISS - vinda da API)**
```http
HTTP/1.1 200 OK
Content-Type: application/json
X-Cache: MISS

{ "id": 1, "title": "Produto Exemplo" }
```

**Segunda requisição (HIT - vinda do cache)**
```http
HTTP/1.1 200 OK
Content-Type: application/json
X-Cache: HIT

{ "id": 1, "title": "Produto Exemplo" }
```

## Contribuição

1. Fork este repositório.
2. Crie uma branch para sua feature (`git checkout -b minha-feature`).
3. Commit suas modificações (`git commit -m 'Adiciona nova feature'`).
4. Envie para o repositório remoto (`git push origin minha-feature`).
5. Abra um Pull Request.

## Licença

Este projeto é distribuído sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

---
Desenvolvido por [André Xizto](https://github.com/andre-xizto).

