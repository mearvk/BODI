# BODI

1. Start out by making a HANDSHAKE with the SERVER from the Telnet CLIENT --> //handshake
2. Make a note of the HANDSHAKE result; it contains a SESSIONID --> //handshake //sessionid=999966131 //result=success //message=welcome //ttl=6000000
3. Make then a PUT request with the SERVER from the Telnet CLIENT --> //put //sessionid=999966131 //key=123 //value=345
4. Make a note of the PUT result; it contains a message of SUCCESS or NOT --> //put //sessionid=999966131 //result=success //message=context and key/value pair established //ttl=5778268 OR //other //result=null //message=unable to complete request //cause=unrecognized protocol
5. Make then a PULL request with the SERVER from the Telnet CLIENT --> //pull //sessionid=999966131 //key=123
6. Make a note of the PULL result; it contains a message of SUCCESS or NOT --> //pull //sessionid=999966131 //result=success //message=key/context pair found. //object=bloq.system.bodi.server.remote.Serializedcarrier@5b480cf9 //ttl=4165304 //value=345


