# 证书生成命令
## KeyStore生成
```shell
$ keytool -genkeypair -alias oauth2-bali-key -keyalg RSA -keypass bali-auth -keystore bali-auth-server.keystore -storepass bali-auth
```