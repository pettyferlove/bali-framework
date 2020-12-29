# 证书生成命令
## KeyStore生成
```shell
$ keytool -genkeypair -alias oauth2-bali-key -keyalg RSA -keypass bali-auth -keystore bali-auth-server.keystore -storepass bali-auth
```

## 导出公钥
```shell
$ keytool -list -rfc --keystore bali-auth-server.keystore | openssl x509 -inform pem -pubkey -noout
```

## 导出私钥

* 得到PKCS12格式的证书
```shell
$ keytool -importkeystore -srckeystore bali-auth-server.keystore -destkeystore bali-auth-private-key.p12 -deststoretype pkcs12 -storepass bali-auth
```

* 转化成PEM格式的文件
```shell
$ openssl pkcs12 -in bali-auth-private-key.p12 -out bali-auth-private-key.pem
```