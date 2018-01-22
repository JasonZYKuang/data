# data
## test1
* a
* b

## test2
```js
stage ('System integration tests') {
        echo "STARTING STAGE SYSTEM INTEGRATION TESTS..."
        //Setup Proxy
        withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: proxy_credentials, passwordVariable: 'PWD', usernameVariable: 'USR']]) {
          withEnv(["http_proxy=http://${env.USR}:${env.PWD}@uk-proxy-01.systems.uk.hsbc:80"]) {
            sh "(cd integration-tests && ${mvnCmd} clean test -DtestEnv=sit -Dorg.apache.commons.logging.Log=org.apache.commons.logging.impl.SimpleLog -Dorg.apache.commons.logging.simplelog.showdatetime=true -Dorg.apache.commons.logging.simplelog.log.org.apache.http=DEBUG)"
          }
        }
        echo "FINISHED STAGE SYSTEM INTEGRATION TESTS"
      }
```
