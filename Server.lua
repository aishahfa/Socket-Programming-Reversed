--include luasocket
local socket = require("socket")

--port number used
port = 80

server = assert(socket.bind('*', port))
io.write("Waiting at door "..port.."\n")

con = assert(server:accept())

io.write("Connection made : \n")

msg, err= con:receive()

while not err do
	--reversed message
	revmsg = string.reverse(msg)
	con:send(revmsg .. "\n")
	io.write("Sent from client: " .. msg .. "\n")
	io.write("Send to client: " .. revmsg .. "\n")
	msg, err= con:receive()
end


io.write("\n" .. err .. "\n")