#!/bin/bash

cliente=$1
senha=$2
id=$3

cert_name="${cliente}_${id}"

cd /usr/share/easy-rsa/
./easyrsa gen-req "$cert_name" nopass <<< ""
./easyrsa --batch --passin=pass:"$senha" sign-req client "$cert_name"

# Cria pasta da VPN
mkdir -p /etc/openvpn/client/"$cliente"/"$id"
mkdir -p /home/userlinux/"$cliente"

# Copia arquivos para pasta correta
cp pki/ca.crt                               /etc/openvpn/client/"$cliente"/"$id"/
cp pki/issued/"$cert_name".crt              /etc/openvpn/client/"$cliente"/"$id"/
cp pki/private/"$cert_name".key             /etc/openvpn/client/"$cliente"/"$id"/
cp pki/dh.pem                               /etc/openvpn/client/"$cliente"/"$id"/

# Cria o arquivo .ovpn corretamente na subpasta $id
cat <<EOF > /etc/openvpn/client/"$cliente"/"$id"/"$cert_name".ovpn
client
dev tun
proto udp
remote 192.168.1.12 1194
ca ca.crt
cert $cert_name.crt
key $cert_name.key

tls-client
resolv-retry infinite
nobind
persist-key
persist-tun
EOF

# Copia tudo para o diretório do usuário
cp -r /etc/openvpn/client/"$cliente"/"$id" /home/userlinux/"$cliente"/
chown -R userlinux:userlinux /home/userlinux/"$cliente"/"$id"

cd /home/userlinux/"$cliente"/"$id"
zip -r "$id".zip ./
chown userlinux:userlinux "$id".zip
