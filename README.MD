# JPA - UNI7

Aplicação com exemplos de uso de Java Persistence API.

## Instruções para execução

### Servidor de Banco de Dados


#### Via linha de comando, na raiz do projeto:

``
java -cp "[CAMINHO_PRO_JAR_DO_HSQL]" org.hsqldb.server.Server
     --database.0 file:db/empresa --dbname.0 empresa
``

Substituir ``CAMINHO_PRO_JAR_DO_HSQL`` pelo caminho completo do jar do HSQLDb na sua máquina.

#### Via IDE - Eclipse, em "Run Configurations":

1. Main Class: org.hsqldb.server.Server
2. Arguments > Program Arguments: --database.0 file:db/empresa --dbname.0 empresa

