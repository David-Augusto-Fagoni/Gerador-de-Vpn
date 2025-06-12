#/bin/bash
#

cliente=$1
senha=$2
id=$3
cert_name="${cliente}_${id}"

cd /usr/share/easy-rsa/
EASYRSA_PASSIN=pass:$senha ./easyrsa --batch revoke "$cert_name"
EASYRSA_PASSIN=pass:$senha ./easyrsa gen-crl


rm -f pki/issued/"$cert_name".crt
rm -f pki/private/"$cert_name".key
rm -f pki/reqs/"$cert_name".req

rm -rf /etc/openvpn/client/"$cliente"/"$id"
rm -rf /home/userlinux/"$cliente"/"$id"
