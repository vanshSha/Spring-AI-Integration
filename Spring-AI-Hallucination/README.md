Project Description

This project demonstrates techniques to reduce AI hallucinations in a Spring Boot application using Spring AI and a PGVector-based Retrieval-Augmented Generation (RAG) pipeline.

The system retrieves relevant context from a PostgreSQL vector database before generating responses, improving factual accuracy.

Tech Stack:
- Java: 17
- Spring AI (OpenAI): 2.0.0-M2

Dependencies:
- Spring Web
- Spring AI OpenAI
- Advisors Vector Store
- Vector Store PGVector
- PostgreSQL
- Lombok

Docker:
Image: pgvector/pgvector:pg16